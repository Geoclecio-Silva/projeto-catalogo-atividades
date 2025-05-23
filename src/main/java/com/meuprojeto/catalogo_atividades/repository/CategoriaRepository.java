package com.meuprojeto.catalogo_atividades.repository;

import com.meuprojeto.catalogo_atividades.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}