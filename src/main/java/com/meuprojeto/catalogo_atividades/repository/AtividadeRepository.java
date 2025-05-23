package com.meuprojeto.catalogo_atividades.repository;

import com.meuprojeto.catalogo_atividades.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    // Busca por título contendo (ignorando maiúsculas/minúsculas)
    List<Atividade> findByTituloContainingIgnoreCase(String titulo);

    // Busca por categoria
    List<Atividade> findByCategoriaId(Long categoriaId);

    @Query("SELECT a FROM Atividade a JOIN a.habilidades h WHERE h.id = :habilidadeId")
    List<Atividade> findByHabilidadeId(@Param("habilidadeId") String habilidadeId);


    // Outros filtros podem ser adicionados conforme a necessidade
}