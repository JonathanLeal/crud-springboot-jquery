package com.cuenta.web.service;

import java.util.List;
import java.util.Optional;

import com.cuenta.web.entity.Usuario;

public interface UsuarioService {
	public List<Usuario> lista();
	public void guardarUsuario(Usuario usuario);
	public Optional<Usuario> emailEncontrado(String email);
	public Optional<Usuario> encontrarUsuario(Long id);
	public void editarUsuario(Usuario usuario);
	public void eliminarUsuario(Usuario usuario);
}
