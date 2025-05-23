package com.meuprojeto.catalogo_atividades.repository;

import com.meuprojeto.catalogo_atividades.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}

