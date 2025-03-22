package com.mmc.comporacion.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.comporacion.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Page<Cliente> findByNombreStartingWithIgnoreCase(String nombre, Pageable pageable);
}
