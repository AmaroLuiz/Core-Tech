package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.UsuarioRepository;
import br.com.coretech.coretech_api.infraestructure.security.JwtUtil;
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

}
