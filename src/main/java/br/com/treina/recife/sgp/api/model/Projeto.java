package br.com.treina.recife.sgp.api.model;

import java.time.LocalDate;

import br.com.treina.recife.sgp.api.enums.StatusProjeto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_PROJETOS")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private LocalDate dataInicio;

    private LocalDate dataConclusao;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    @ManyToOne
    @JoinColumn(name = "usuario_resp_id")
    private Usuario responsavel;

}
