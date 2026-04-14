package com.binario.zerogarage.runners;

import com.binario.zerogarage.models.entities.Cliente;
import com.binario.zerogarage.repositories.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TallerRunner implements CommandLineRunner {

    Logger logger = Logger.getLogger(getClass().getName());
    private final ClienteRepository clienteRepository;

    // Inyectamos el repositorio
    public TallerRunner(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       logger.info("--- Iniciando prueba de base de datos ---");

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

        logger.info("--- ¡Cliente insertado con éxito! ---");
    }
}
