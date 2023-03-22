package com.fluxodecisao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fluxodecisao.model.Evento;

@Repository
public interface FluxoDecisaoRepository extends JpaRepository<Evento, Long>{

}
