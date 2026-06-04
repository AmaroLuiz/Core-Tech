package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.infraestructure.entity.Endereco;
import br.com.coretech.coretech_api.infraestructure.entity.Telefone;
import br.com.coretech.coretech_api.infraestructure.security.JwtUtil;
import br.com.coretech.coretech_api.service.UsuarioService;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import br.com.coretech.coretech_api.service.mapper.UsuarioConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlller {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UsuarioConverter usuarioConverter;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO,
                                                       @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioDTO, token));
    }


    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO,
                                                       @RequestParam("id") Long id,
                                                       @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(enderecoDTO, id, token));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO,
                                                      @RequestParam("id") Long id,
                                                      @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizarTelefone(telefoneDTO, id, token));
    }



}
