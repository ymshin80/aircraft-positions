package com.dev.aircraft_positions.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.dev.aircraft_positions.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {

}
