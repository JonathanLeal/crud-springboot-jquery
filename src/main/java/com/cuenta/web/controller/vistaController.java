package com.cuenta.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class vistaController {
	@GetMapping("/mi-vista")
    public String mostrarVista() {
        return "index"; // Nombre del archivo HTML sin la extensión
    }
}
