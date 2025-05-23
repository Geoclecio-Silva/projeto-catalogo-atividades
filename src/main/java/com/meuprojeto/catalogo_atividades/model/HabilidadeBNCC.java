package com.meuprojeto.catalogo_atividades.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadeBNCC {

    @Id
    private String id; // Ex: "EF05CI03"

    private String descricao;
}