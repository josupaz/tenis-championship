package com.init.championship.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.championship.entities.Torneo;

public interface TorneoDAO extends JpaRepository<Torneo, Long> {

}
