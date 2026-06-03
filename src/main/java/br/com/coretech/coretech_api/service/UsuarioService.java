package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Endereco;
import br.com.coretech.coretech_api.infraestructure.entity.Telefone;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.EnderecoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.TelefoneRepository;
import br.com.coretech.coretech_api.infraestructure.repository.UsuarioRepository;
import br.com.coretech.coretech_api.infraestructure.security.JwtUtil;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import br.com.coretech.coretech_api.service.mapper.UsuarioConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;



    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuarioEntity(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
              usuarioRepository.save(usuario)
        );

    }

    public void emailExiste(String email){
        try{
            boolean existe =  verificarEmail(email);
            if(existe){
                throw new ConflictExceptions("Email já casdastrado" + email);
            } else{

            }
        } catch (ConflictExceptions e){
            throw new RuntimeException("Email já cadastrado", e.getCause());
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



    public void deletaUsuarioPorEmail(String email){
            usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO, String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public Endereco atualizarEndereco(EnderecoDTO enderecoDTO, Long id, String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id)
        );

        if (!enderecoEntity.getUsuario().equals(usuario.getId()) ) {
            throw new ConflictExceptions("Id não pertence ao usuario" + id);
        }

            Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);
            return enderecoRepository.save(endereco);
    }

    public Telefone atualizarTelefone(TelefoneDTO telefoneDTO, Long id, String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)
        );

        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + id)
        );

        if (!telefoneEntity.getUsuario().equals(usuario.getId()) ) {
            throw new ConflictExceptions("Id não pertence ao usuario" + id);
        }

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);
        return telefoneRepository.save(telefone);
    }

}
