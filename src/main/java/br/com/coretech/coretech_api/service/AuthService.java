package br.com.coretech.coretech_api.service;

import br.com.coretech.coretech_api.infraestructure.security.JwtUtil;
import br.com.coretech.coretech_api.service.dto.LoginDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String login(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication);
    }

    public String getUsuarioAutenticadoEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new AccessDeniedException("Usuario não autenticado");
        }
        String email = authentication.getName();
        return email;
    }
}
