package com.brahim.pfa.service;

import com.brahim.pfa.domain.Prestataire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Prestataire.
 */
public interface PrestataireService {

    /**
     * Save a prestataire.
     *
     * @param prestataire the entity to save
     * @return the persisted entity
     */
    Prestataire save(Prestataire prestataire);

    /**
     * Get all the prestataires.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Prestataire> findAll(Pageable pageable);

    /**
     * Get the "id" prestataire.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Prestataire findOne(Long id);

    /**
     * Delete the "id" prestataire.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the prestataire corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Prestataire> search(String query, Pageable pageable);
}
