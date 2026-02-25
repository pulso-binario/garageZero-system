package com.binario.zeroGarage.runners;

import com.binario.zeroGarage.models.entities.Cliente;
import com.binario.zeroGarage.repositories.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TallerRunner implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    // Inyectamos el repositorio
    public TallerRunner(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Iniciando prueba de base de datos ---");

        // 1. Creamos el objeto con los datos del nuevo cliente
        Cliente nuevoCliente = new Cliente(
                null,
                "Pérez",
                "Gómez",
                "5512345678",
                "juan.perez@email.com"
        );

        // 2. Ejecutamos el INSERT en Postgres
        clienteRepository.save(nuevoCliente);

        System.out.println("--- ¡Cliente insertado con éxito! ---");
    }
}
