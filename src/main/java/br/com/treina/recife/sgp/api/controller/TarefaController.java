package br.com.treina.recife.sgp.api.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.treina.recife.sgp.api.dto.DadosTarefaDTO;
import br.com.treina.recife.sgp.api.model.Tarefa;
import br.com.treina.recife.sgp.api.service.TarefaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "http://localhost:5173")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    // CREATE
    @PostMapping
    public ResponseEntity<Tarefa> cadastrar(@Valid @RequestBody DadosTarefaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tarefaService.cadastrarTarefa(dto));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(
            @PathVariable Long id,
            @RequestBody DadosTarefaDTO dto) {

        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, dto));
    }

    // LIST
    @GetMapping
    public ResponseEntity<List<Tarefa>> listar() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<@Nullable Object> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}