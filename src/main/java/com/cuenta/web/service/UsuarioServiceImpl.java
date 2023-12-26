package com.cuenta.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cuenta.web.entity.Usuario;
import com.cuenta.web.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository user;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> lista() {
		return user.findAll();
	}

	@Override
	@Transactional
	public void guardarUsuario(Usuario usuario) {
		user.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> emailEncontrado(String email) {
		Optional<Usuario> emailEncontrado = user.findByEmail(email);
		return emailEncontrado;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Usuario> encontrarUsuario(Long id) {
		Optional<Usuario> ide = user.findById(id);
		return ide;
	}

	@Override
	@Transactional
	public void editarUsuario(Usuario usuario) {
		Optional<Usuario> ide = user.findById(usuario.getId());
		Usuario ideEncontrado = ide.get();
		user.save(ideEncontrado);
	}

	@Override
	@Transactional
	public void eliminarUsuario(Usuario usuario) {
		Optional<Usuario> ide = user.findById(usuario.getId());
		Usuario ideEncontrado = ide.get();
		user.delete(ideEncontrado);
	}
}
