package com.cibertec.msreclamos.repositorio;

import com.cibertec.msreclamos.entidades.Reclamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
}
