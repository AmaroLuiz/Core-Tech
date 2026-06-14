package br.com.coretech.coretech_api.controller;


import br.com.coretech.coretech_api.service.AuthService;
import br.com.coretech.coretech_api.service.UsuarioService;
import br.com.coretech.coretech_api.service.dto.EnderecoDTO;
import br.com.coretech.coretech_api.service.dto.LoginDTO;
import br.com.coretech.coretech_api.service.dto.TelefoneDTO;
import br.com.coretech.coretech_api.service.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioControlller {

    private final UsuarioService usuarioService;
    private final AuthService authService;


    @PostMapping("/criar")
    public ResponseEntity<LoginDTO> salvaUsuario(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(loginDTO));
    }

    @PostMapping("/criar/telefone")
    public ResponseEntity<TelefoneDTO> salvaTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) {
        return ResponseEntity.ok(usuarioService.salvaTelefone(telefoneDTO));
    }

    @PostMapping("/criar/endereco")
    public ResponseEntity<EnderecoDTO> salvaEndereco(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.salvaEndereco(enderecoDTO));
    }


    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login( @Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioDTO));
    }


    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                       @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizarEndereco(enderecoDTO, id));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                      @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizarTelefone(telefoneDTO, id));
    }


}
