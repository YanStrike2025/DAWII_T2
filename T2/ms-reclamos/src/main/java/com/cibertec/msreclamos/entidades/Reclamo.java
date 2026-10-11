package com.cibertec.msreclamos.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reclamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reclamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numreclamo;

	@Column(nullable = false)
	private LocalDateTime fechareclamo;

	@Column(nullable = false, length = 120)
	private String nomcliente;

	@Column(nullable = false, length = 4)
	private String ultimos4tarjeta;

	@Column(nullable = false, length = 255)
	private String motivo;

	@Column(nullable = false, length = 120)
	private String analista;

	@Column(nullable = false)
	private Integer estado;

	@Column(nullable = false, length = 255)
	private String observacion;
}
