package com.binario.zeroGarage.repositories;

import com.binario.zeroGarage.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    // ¡No necesitas escribir código aquí para un insert básico!
}