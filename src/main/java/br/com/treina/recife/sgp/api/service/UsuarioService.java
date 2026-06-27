package br.com.treina.recife.sgp.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.treina.recife.sgp.api.dto.DadosUsuarioDTO;
import br.com.treina.recife.sgp.api.dto.UsuarioDTO;
import br.com.treina.recife.sgp.api.exception.RecursoNaoEncontradoException;
import br.com.treina.recife.sgp.api.exception.RegraDeNegocioException;
import br.com.treina.recife.sgp.api.model.Usuario;
import br.com.treina.recife.sgp.api.repository.ProjetoRepository;
import br.com.treina.recife.sgp.api.repository.TarefaRepository;
import br.com.treina.recife.sgp.api.repository.UsuarioRepository;

 @Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;


    // LISTAR
    public List<UsuarioDTO> listarUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioDTO> dtos = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            dtos.add(usuario.toDTO());
        }

        return dtos;
    }

    // BUSCAR POR ID
    public UsuarioDTO obterDadosDoUsuario(Long idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado"));

        return usuario.toDTO();
    }

    // CADASTRAR
    public Usuario cadastrarUsuario(DadosUsuarioDTO usuario) {

        if (usuarioRepository.findByCpf(usuario.cpf()).isPresent()) {
            throw new RegraDeNegocioException("CPF já cadastrado");
        }

        return usuarioRepository.save(usuario.toModel());
    }

    // ATUALIZAR
    @Transactional
public Usuario atualizarUsuario(Long id, DadosUsuarioDTO dados) {

    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() ->
                    new RecursoNaoEncontradoException("Usuário não encontrado"));

    usuario.setNome(dados.nome());
    usuario.setEmail(dados.email());
    usuario.setCpf(dados.cpf());
    usuario.setDataNascimento(dados.dataNascimento());
    usuario.setStatus(dados.status());
    usuario.setSenha(dados.senha());

    return usuarioRepository.save(usuario);
}

    // EXCLUIR
   public void excluirUsuario(Long id) {

    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() ->
                    new RecursoNaoEncontradoException(
                            "Usuário não encontrado"));

    if (projetoRepository.existsByResponsavelId(id)) {
        throw new RegraDeNegocioException(
                "Não é possível excluir o usuário porque ele é responsável por um ou mais projetos.");
    }

    usuarioRepository.delete(usuario);
}

    // CPF
    public UsuarioDTO buscarUsuarioPeloCPF(String cpf) {

        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Usuário não encontrado"));

        return usuario.toDTO();
    }

    // LOGIN
    public UsuarioDTO buscarUsuarioPeloEmailSenha(String email, String senha) {

        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Email ou senha inválidos"));

        return usuario.toDTO();
    }
}
