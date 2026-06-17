package br.com.treina.recife.sgp.api.dto;

import java.time.LocalDate;

import br.com.treina.recife.sgp.api.enums.StatusProjeto;
import br.com.treina.recife.sgp.api.model.Projeto;
import br.com.treina.recife.sgp.api.model.Usuario;
import br.com.treina.recife.sgp.api.repository.UsuarioRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosProjetoDTO(
    @NotBlank String nome,
    String descricao,
    @NotNull LocalDate dataInicio,
    @NotNull LocalDate dataConclusao,
    @NotNull StatusProjeto status,
    @NotNull Long responsavelId
) {
    public Projeto toModel(UsuarioRepository usuarioRepository) {

        Usuario usuario = usuarioRepository.findById(responsavelId)
            .orElseThrow(() -> new RuntimeException("Usuário responsável não encontrado"));

        Projeto projeto = new Projeto();

        projeto.setNome(nome);
        projeto.setDescricao(descricao);
        projeto.setDataInicio(dataInicio);
        projeto.setDataConclusao(dataConclusao);
        projeto.setStatus(status);
        projeto.setResponsavel(usuario);

        return projeto;
    }
}
