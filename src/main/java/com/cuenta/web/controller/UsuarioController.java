package com.cuenta.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cuenta.web.entity.Usuario;
import com.cuenta.web.service.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService user;
	
	@GetMapping("/listarUsuarios")
	public ResponseEntity<?> listaUsuarios(){
		HashMap<String, Object> respuesta = new HashMap<String, Object>();
		List<Usuario> lista = user.lista();
		try {
			if (lista.size() == 0) {
				respuesta.put("validacion: ", "No hay usuarios registrados");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
			}
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		} catch (Exception e) {
			respuesta.put("error: ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@PostMapping("/guardarUsuario")
	public ResponseEntity<?> guardar(@RequestBody HashMap<String, Object> request){
		HashMap<String, String> respuesta = new HashMap<String, String>();
		try {
			String nombre = (String) request.get("name");
			if (nombre == null || nombre.isBlank() || nombre.isEmpty()) {
				respuesta.put("validacion", "El nombre no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String apellido = (String) request.get("last");
			if (apellido == null || apellido.isBlank() || apellido.isEmpty()) {
				respuesta.put("validacion", "El apellido no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String password = (String) request.get("pass");
			if (password == null || password.isBlank() || password.isEmpty()) {
				respuesta.put("validacion", "El password no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String email = (String) request.get("correo");
			Optional<Usuario> emailEncontrado = user.emailEncontrado(email);
			if (email == null || email.isBlank() || email.isEmpty()) {
				respuesta.put("validacion", "El email no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			} else if (emailEncontrado.isPresent()) {
				respuesta.put("validacion", "Ese email ya esta registrado");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
			}
			
			Usuario usuario = new Usuario();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setEmail(email);
			usuario.setPassword(password);
			usuario.setEstado("Activo");
			
			user.guardarUsuario(usuario);
			respuesta.put("mensaje", "Usuario guardado con exito");
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} catch (Exception e) {
			respuesta.put("error: ", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> encontrarUsuario(@PathVariable Long id){
		HashMap<String, String> respuesta = new HashMap<String, String>();
		try {
			Optional<Usuario> ide = user.encontrarUsuario(id);
			if (ide.isEmpty()) {
				respuesta.put("validacion", "No se encontro el usuario");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			Usuario ideUser = ide.get();
			return ResponseEntity.status(HttpStatus.OK).body(ideUser);
		} catch (Exception e) {
			respuesta.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@PostMapping("/editarUser/{id}")
	public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody HashMap<String, Object> request){
		HashMap<String, String> respuesta = new HashMap<String, String>();
		try {
			Optional<Usuario> ide = user.encontrarUsuario(id);
			if (ide.isEmpty()) {
				respuesta.put("validacion", "No se encontro el usuario");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			
			Usuario ideUser = ide.get();
			
			String nombre = (String) request.get("name");
			if (nombre == null || nombre.isBlank() || nombre.isEmpty()) {
				respuesta.put("validacion", "El nombre no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String apellido = (String) request.get("last");
			if (apellido == null || apellido.isBlank() || apellido.isEmpty()) {
				respuesta.put("validacion", "El apellido no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String password = (String) request.get("pass");
			if (password == null || password.isBlank() || password.isEmpty()) {
				respuesta.put("validacion", "El password no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			String email = (String) request.get("correo");
			if (email == null || email.isBlank() || email.isEmpty()) {
				respuesta.put("validacion", "El email no debe estar vacio");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
			}
			
			ideUser.setNombre(nombre);
			ideUser.setApellido(apellido);
			ideUser.setEmail(email);
			ideUser.setPassword(password);
			user.editarUsuario(ideUser);
			respuesta.put("mensaje", "Usuario editado con exito");
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} catch (Exception e) {
			respuesta.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@PostMapping("/usuarioEliminar/{id}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
		HashMap<String, String> respuesta = new HashMap<String, String>();
		try {
			Optional<Usuario> ide = user.encontrarUsuario(id);
			if (ide.isEmpty()) {
				respuesta.put("validacion", "No se encontro el usuario");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
			}
			Usuario ideUser = ide.get();
			user.eliminarUsuario(ideUser);
			respuesta.put("mensaje", "usuario eliminado con exito");
			return ResponseEntity.status(HttpStatus.OK).body(respuesta);
		} catch (Exception e) {
			respuesta.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
}
