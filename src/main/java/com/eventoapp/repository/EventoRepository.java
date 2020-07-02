package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String> {
    
	// metodo anotado para buscar um id especifico no banco 
	Evento findByCodigo(long codigo);	
}
