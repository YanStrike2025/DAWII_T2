package com.cibertec.msreclamos.negocio;

import com.cibertec.msreclamos.dto.ReclamoActualizarRequest;
import com.cibertec.msreclamos.dto.ReclamoRequest;
import com.cibertec.msreclamos.dto.ReclamoResponse;
import com.cibertec.msreclamos.entidades.Reclamo;
import com.cibertec.msreclamos.repositorio.ReclamoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class ReclamoService {

	public static final String ANALISTA_POR_DEFECTO = "No asignado";
	public static final String OBSERVACION_POR_DEFECTO = "por evaluar";
	public static final int ESTADO_PENDIENTE = 1;
	public static final int ESTADO_PROCEDENTE = 2;
	public static final int ESTADO_IMPROCEDENTE = 3;

	private final ReclamoRepository reclamoRepository;

	public ReclamoService(ReclamoRepository reclamoRepository) {
		this.reclamoRepository = reclamoRepository;
	}

	public ReclamoResponse createReclamo(ReclamoRequest request) {
		if (isBlank(request.nomcliente()) || isBlank(request.ultimos4tarjeta()) || isBlank(request.motivo())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"nomcliente, ultimos4tarjeta y motivo son obligatorios");
		}
		if (!request.ultimos4tarjeta().matches("\\d{4}")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"ultimos4tarjeta debe tener exactamente 4 digitos");
		}

		Reclamo reclamo = Reclamo.builder()
				.fechareclamo(LocalDateTime.now())
				.nomcliente(request.nomcliente())
				.ultimos4tarjeta(request.ultimos4tarjeta())
				.motivo(request.motivo())
				.analista(ANALISTA_POR_DEFECTO)
				.estado(ESTADO_PENDIENTE)
				.observacion(OBSERVACION_POR_DEFECTO)
				.build();

		return mapToResponse(reclamoRepository.save(reclamo));
	}

	public ReclamoResponse getReclamoByNumero(Long numreclamo) {
		return mapToResponse(findOrThrow(numreclamo));
	}

	public ReclamoResponse updateReclamo(Long numreclamo, ReclamoActualizarRequest request) {
		Reclamo reclamo = findOrThrow(numreclamo);

		if (request.estado() != null) {
			if (request.estado() < ESTADO_PENDIENTE || request.estado() > ESTADO_IMPROCEDENTE) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"estado debe ser 1 (pendiente), 2 (procedente) o 3 (improcedente)");
			}
			reclamo.setEstado(request.estado());
		}
		if (!isBlank(request.analista())) {
			reclamo.setAnalista(request.analista());
		}
		if (!isBlank(request.observacion())) {
			reclamo.setObservacion(request.observacion());
		}

		return mapToResponse(reclamoRepository.save(reclamo));
	}

	private Reclamo findOrThrow(Long numreclamo) {
		return reclamoRepository.findById(numreclamo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Reclamo " + numreclamo + " no encontrado"));
	}

	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}

	private ReclamoResponse mapToResponse(Reclamo reclamo) {
		return new ReclamoResponse(
				reclamo.getNumreclamo(),
				reclamo.getFechareclamo(),
				reclamo.getNomcliente(),
				reclamo.getUltimos4tarjeta(),
				reclamo.getMotivo(),
				reclamo.getAnalista(),
				reclamo.getEstado(),
				reclamo.getObservacion()
		);
	}
}
