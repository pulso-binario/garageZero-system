package com.binario.zeroGarage.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Porque usamos SERIAL en Postgres
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nombres", nullable = false, length = 150)
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    // Constructores vacíos y con parámetros
    public Cliente() {}

    public Cliente(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
    }
}
