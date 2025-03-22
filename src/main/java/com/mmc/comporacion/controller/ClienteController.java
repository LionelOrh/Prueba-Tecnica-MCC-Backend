package com.mmc.comporacion.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmc.comporacion.entity.Cliente;
import com.mmc.comporacion.service.ClienteService;
import com.mmc.comporacion.util.AppSettings;

@Controller
@RequestMapping("/cliente")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listar")
    @ResponseBody
    public ResponseEntity<Page<Cliente>> listaClientes(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "5") int size,
        @RequestParam(value = "nombre", required = false) String nombre) {

        // Llamamos al servicio para obtener la página de clientes paginados y filtrados
        Page<Cliente> clientes = clienteService.listarClientesPaginadosYFiltrados(page, size, nombre);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
	
	
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseBody
	public ResponseEntity<?> eliminarCliente(@PathVariable Long id){
		if(clienteService.buscarPorId(id).isPresent()) {
			clienteService.eliminarCliente(id);
			return ResponseEntity.ok("Cliente eliminado correctamente");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente no existe");
		}
		
	} 
	
	@PostMapping("/registrar")
	@ResponseBody
	public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
	    Cliente nuevoCliente = clienteService.registrarCliente(cliente);
	    return ResponseEntity.ok(nuevoCliente);
	}
	
	@PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteActualizado = clienteService.actualizarCliente(cliente, id);
        return clienteActualizado.isPresent()
            ? ResponseEntity.ok(clienteActualizado.get())
            : ResponseEntity.notFound().build();
    }
} 
