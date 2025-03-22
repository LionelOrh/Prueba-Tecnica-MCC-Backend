package com.mmc.comporacion.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.mmc.comporacion.entity.Cliente;

public interface ClienteService {
	Page<Cliente> listarClientesPaginadosYFiltrados(int page, int size, String nombre);
	Cliente registrarCliente(Cliente cliente);
	Optional<Cliente> actualizarCliente(Cliente cliente, Long id);
	Optional<Cliente> buscarPorId(Long id);
	void eliminarCliente(Long id);
	
}
