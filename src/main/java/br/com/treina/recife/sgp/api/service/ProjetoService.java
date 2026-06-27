package br.com.treina.recife.sgp.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treina.recife.sgp.api.dto.DadosProjetoDTO;
import br.com.treina.recife.sgp.api.model.Projeto;
import br.com.treina.recife.sgp.api.model.Usuario;
import br.com.treina.recife.sgp.api.repository.ProjetoRepository;
import br.com.treina.recife.sgp.api.repository.UsuarioRepository;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> obterDadosDoProjeto(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto salvar(DadosProjetoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.responsavelId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Projeto projeto = new Projeto();

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataConclusao(dto.dataConclusao());
        projeto.setStatus(dto.status());
        projeto.setResponsavel(usuario);

        return projetoRepository.save(projeto);
    }

    public Projeto atualizarProjeto(Long id, DadosProjetoDTO dto) {

        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Usuario responsavel = usuarioRepository.findById(dto.responsavelId())
                .orElseThrow(() -> new RuntimeException("Responsável não encontrado"));

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataConclusao(dto.dataConclusao());
        projeto.setStatus(dto.status());
        projeto.setResponsavel(responsavel);

        return projetoRepository.save(projeto);
    }

    public void excluirProjeto(Long id) {
        projetoRepository.deleteById(id);
    }

    public List<Projeto> listarProjetosDeUmUsuario(Long idUsuario) {
        return projetoRepository.findByResponsavel_Id(idUsuario);
    }
}
