package br.com.treina.recife.sgp.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.treina.recife.sgp.api.dto.DadosTarefaDTO;
import br.com.treina.recife.sgp.api.model.Projeto;
import br.com.treina.recife.sgp.api.model.Tarefa;
import br.com.treina.recife.sgp.api.model.Usuario;
import br.com.treina.recife.sgp.api.repository.ProjetoRepository;
import br.com.treina.recife.sgp.api.repository.TarefaRepository;
import br.com.treina.recife.sgp.api.repository.UsuarioRepository;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> obterDadosDaTarefa(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa cadastrarTarefa(DadosTarefaDTO dto) {
        Tarefa tarefa = montarTarefa(dto, new Tarefa());
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Long id, DadosTarefaDTO dto) {

        Tarefa tarefa = tarefaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tarefa não encontrada"));

        tarefa = montarTarefa(dto, tarefa);

        return tarefaRepository.save(tarefa);
    }

    public void excluirTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    private Tarefa montarTarefa(DadosTarefaDTO dto, Tarefa tarefa) {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Projeto projeto = projetoRepository.findById(dto.projetoId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Projeto não encontrado"));

        tarefa.setTitulo(dto.titulo());
        tarefa.setDescricao(dto.descricao());
        tarefa.setDataCriacao(dto.dataCriacao());
        tarefa.setDataConclusao(dto.dataConclusao());
        tarefa.setPrioridade(dto.prioridade());
        tarefa.setStatus(dto.status());
        tarefa.setUsuario(usuario);
        tarefa.setProjeto(projeto);

        return tarefa;
    }
}