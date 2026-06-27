package br.com.treina.recife.sgp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treina.recife.sgp.api.dto.CredenciaisDTO;
import br.com.treina.recife.sgp.api.dto.DadosUsuarioDTO;
import br.com.treina.recife.sgp.api.dto.UsuarioDTO;
import br.com.treina.recife.sgp.api.exception.RecursoNaoEncontradoException;
import br.com.treina.recife.sgp.api.model.Usuario;
import br.com.treina.recife.sgp.api.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(
            @Valid @RequestBody DadosUsuarioDTO usuario) {

        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCadastrado.toDTO());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterDadosPeloId(@PathVariable Long id) {

        UsuarioDTO usuario = usuarioService.obterDadosDoUsuario(id);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DadosUsuarioDTO dados) {

        return ResponseEntity.ok(
                usuarioService.atualizarUsuario(id, dados).toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        usuarioService.excluirUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscaPorCpf")
    public ResponseEntity<UsuarioDTO> consultarPeloCPF(
            @RequestParam String cpf) {

        UsuarioDTO usuario = usuarioService.buscarUsuarioPeloCPF(cpf);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/buscaPorCredenciais")
    public ResponseEntity<UsuarioDTO> consultarPelasCredenciais(
            @RequestBody CredenciaisDTO credenciais) {

        UsuarioDTO usuario = usuarioService.buscarUsuarioPeloEmailSenha(
                credenciais.email(),
                credenciais.senha());

        return ResponseEntity.ok(usuario);
    }
}