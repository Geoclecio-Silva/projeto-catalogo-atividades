package com.meuprojeto.catalogo_atividades.service;

import com.meuprojeto.catalogo_atividades.model.Usuario;
import com.meuprojeto.catalogo_atividades.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return new User(
            usuario.getEmail(),
            usuario.getSenha(),
            List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().toUpperCase()))
        );
    }
}
