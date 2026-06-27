package br.com.treina.recife.sgp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treina.recife.sgp.api.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    boolean existsByResponsavelId(Long id);

    List<Projeto> findByResponsavel_Id(Long idUsuario);
}
