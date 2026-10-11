package com.cibertec.msresolucion.negocio;

import com.cibertec.msresolucion.client.ReclamoClient;
import com.cibertec.msresolucion.dto.ReclamoActualizarRequest;
import com.cibertec.msresolucion.dto.ReclamoResponse;
import com.cibertec.msresolucion.dto.ResolucionRequest;
import com.cibertec.msresolucion.dto.ResolucionResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResolucionService {

	private static final int ESTADO_PROCEDENTE = 2;
	private static final int ESTADO_IMPROCEDENTE = 3;

	private static final String OBSERVACION_PROCEDENTE =
			"Reclamo procedente: se reconoce el consumo desconocido y se procede con la devolucion ";
	private static final String OBSERVACION_IMPROCEDENTE =
			"Reclamo improcedente: el consumo fue validado como realizado por el titular ";

	private final ReclamoClient reclamoClient;

	public ResolucionService(ReclamoClient reclamoClient) {
		this.reclamoClient = reclamoClient;
	}

	public ResolucionResponse resolverProcedente(Long numreclamo, ResolucionRequest request) {
		return resolver(numreclamo, request, ESTADO_PROCEDENTE, OBSERVACION_PROCEDENTE,
				"Reclamo " + numreclamo + " resuelto como PROCEDENTE");
	}

	public ResolucionResponse resolverImprocedente(Long numreclamo, ResolucionRequest request) {
		return resolver(numreclamo, request, ESTADO_IMPROCEDENTE, OBSERVACION_IMPROCEDENTE,
				"Reclamo " + numreclamo + " resuelto como IMPROCEDENTE");
	}

	private ResolucionResponse resolver(
			Long numreclamo,
			ResolucionRequest request,
			int estado,
			String observacion,
			String mensajeExito
	) {
		if (request == null || request.analista() == null || request.analista().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"El Nombre del analista es OBLIGATORIO");
		}

		obtenerReclamo(numreclamo);

		ReclamoResponse actualizado = actualizarReclamo(numreclamo,
				new ReclamoActualizarRequest(estado, request.analista(), observacion));

		return new ResolucionResponse(mensajeExito, actualizado);
	}

	private ReclamoResponse obtenerReclamo(Long numreclamo) {
		try {
			return reclamoClient.getReclamoByNumero(numreclamo);
		} catch (FeignException.NotFound e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"El reclamo " + numreclamo + " NO existe. No se realizo la resolucion");
		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ms-reclamos no disponible");
		}
	}

	private ReclamoResponse actualizarReclamo(Long numreclamo, ReclamoActualizarRequest request) {
		try {
			return reclamoClient.updateReclamo(numreclamo, request);

		} catch (FeignException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"No se pudo actualizar el reclamo " + numreclamo + " en ms-reclamos");
		}
	}
}
