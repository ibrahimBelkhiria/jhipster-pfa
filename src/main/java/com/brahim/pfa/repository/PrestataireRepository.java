package com.brahim.pfa.repository;

import com.brahim.pfa.domain.Prestataire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Prestataire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestataireRepository extends JpaRepository<Prestataire, Long> {

}
