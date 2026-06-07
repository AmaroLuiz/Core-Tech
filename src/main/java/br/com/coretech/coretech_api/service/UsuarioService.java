package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Endereco;
import br.com.coretech.coretech_api.infraestructure.entity.Telefone;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.EmailAlreadyExistsException;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.exceptions.UserOwnershipException;
import br.com.coretech.coretech_api.infraestructure.repository.EnderecoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.TelefoneRepository;
import br.com.coretech.coretech_api.infraestructure.repository.UsuarioRepository;
import br.com.coretech.coretech_api.infraestructure.security.JwtUtil;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.LoginDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import br.com.coretech.coretech_api.service.mapper.UsuarioConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;



@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final AuthService authService;



//    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
//        emailExiste(usuarioDTO.getEmail());
//        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
//        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
//
//        usuario.getEnderecos().forEach(endereco -> endereco.setUsuario(usuario));
//        usuario.getTelefones().forEach(telefone -> telefone.setUsuario(usuario));
//
//        return usuarioConverter.paraUsuarioDTO(
//              usuarioRepository.save(usuario)
//        );
//
//    }

    @Transactional
    public LoginDTO salvaUsuario(LoginDTO loginDTO){
        emailExiste(loginDTO.getEmail());

        loginDTO.setSenha(passwordEncoder.encode(loginDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuarioLogin(loginDTO);

        return usuarioConverter.paraLoginDTO(usuarioRepository.save(usuario));

    }

    @Transactional
    public TelefoneDTO salvaTelefone(TelefoneDTO telefoneDTO){
        String email = authService.getUsuarioAutenticadoEmail();

        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO);

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado")
        );

        telefone.setUsuario(usuario);

        return usuarioConverter.paraTelfoneDTO(telefoneRepository.save(telefone));

    }

    @Transactional
    public EnderecoDTO salvaEndereco(EnderecoDTO enderecoDTO){
        String email = authService.getUsuarioAutenticadoEmail();

        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO);

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado")
        );

        endereco.setUsuario(usuario);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }

    public void emailExiste(String email){
        try{
            boolean existe =  verificarEmail(email);
            if(existe){
                throw new EmailAlreadyExistsException("Email já casdastrado" + email);
            } else{

            }
        } catch (EmailAlreadyExistsException e){
            throw new EmailAlreadyExistsException("Email já cadastrado", e.getCause());
        }
    }
    public boolean verificarEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }
    
    
    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                        .orElseThrow(
                    () ->  new ResourceNotFoundException("Email não encontrado" +  email))
        );
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado"+ email);
        }
    }


    @Transactional
    public void deletaUsuarioPorEmail(String email){
            usuarioRepository.deleteByEmail(email);
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO) {

        String email = authService.getUsuarioAutenticadoEmail();

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    @Transactional
    public EnderecoDTO atualizarEndereco(EnderecoDTO enderecoDTO, Long id) {

        String email = authService.getUsuarioAutenticadoEmail();

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id)
        );

        if (!enderecoEntity.getUsuario().getId().equals(usuario.getId()) ) {
            throw new UserOwnershipException("Id não pertence ao usuario" + id);
        }

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    @Transactional
    public TelefoneDTO atualizarTelefone(TelefoneDTO telefoneDTO, Long id) {

        String email = authService.getUsuarioAutenticadoEmail();

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id)
        );

        if (!telefoneEntity.getUsuario().getId().equals(usuario.getId()) ) {
            throw new UserOwnershipException("Id não pertence ao usuario" + id);
        }

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);

        return usuarioConverter.paraTelfoneDTO(telefoneRepository.save(telefone));
    }





}
