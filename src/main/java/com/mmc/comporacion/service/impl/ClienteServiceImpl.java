package com.mmc.comporacion.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mmc.comporacion.entity.Cliente;
import com.mmc.comporacion.repository.ClienteRepository;
import com.mmc.comporacion.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
    public Page<Cliente> listarClientesPaginadosYFiltrados(int page, int size, String nombre) {
        // Si el nombre no está vacío, filtramos por nombre que comienza con el patrón dado
        if (nombre != null && !nombre.isEmpty()) {
            return clienteRepository.findByNombreStartingWithIgnoreCase(nombre, PageRequest.of(page, size));
        } else {
            // Si no hay filtro, devolvemos todos los clientes con paginación
            return clienteRepository.findAll(PageRequest.of(page, size));
        }
    }

	@Override
	public Cliente registrarCliente(Cliente cliente) {
		cliente.setFechaRegistro(LocalDateTime.now());
		return clienteRepository.save(cliente);
	}

	@Override
	public Optional<Cliente> actualizarCliente(Cliente cliente, Long id) {
		// Verificamos la existencia de ese cliente
		if (!clienteRepository.existsById(id)) {
			throw new IllegalArgumentException("El cliente no existe para ser actualizado");
		}

		Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
		return clienteEncontrado.map(clienteExistente -> {
			clienteExistente.setNombre(cliente.getNombre());
			clienteExistente.setCelular(cliente.getCelular());
			clienteExistente.setEmail(cliente.getEmail());
			clienteExistente.setDireccion(cliente.getDireccion());
			clienteExistente.setFechaRegistro(LocalDateTime.now());
			
			return clienteRepository.save(clienteExistente);
		});

	}

	@Override
	public void eliminarCliente(Long id) {
		clienteRepository.deleteById(id);
	}

	@Override
	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}
}
