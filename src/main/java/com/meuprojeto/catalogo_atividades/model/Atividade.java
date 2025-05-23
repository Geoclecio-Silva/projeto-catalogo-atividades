package com.meuprojeto.catalogo_atividades.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @NotNull(message = "A data de realização é obrigatória")
    private LocalDate dataRealizacao;

    private String bannerUrl;

    @ManyToOne
    @NotNull(message = "Selecione uma categoria")
    private Categoria categoria;

    @ManyToOne
    @NotNull(message = "Selecione o responsável")
    private Usuario responsavel;

    @ManyToMany
    @JoinTable(
        name = "atividade_habilidade",
        joinColumns = @JoinColumn(name = "atividade_id"),
        inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    @NotEmpty(message = "Selecione pelo menos uma habilidade")
    private List<HabilidadeBNCC> habilidades;
}