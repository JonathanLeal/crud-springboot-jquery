package com.cuenta.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuenta.web.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public Optional<Usuario> findByEmail(String email);
}
