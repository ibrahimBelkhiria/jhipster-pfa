package com.brahim.pfa.service.impl;

import com.brahim.pfa.service.PrestataireService;
import com.brahim.pfa.domain.Prestataire;
import com.brahim.pfa.repository.PrestataireRepository;
import com.brahim.pfa.repository.search.PrestataireSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Prestataire.
 */
@Service
@Transactional
public class PrestataireServiceImpl implements PrestataireService {

    private final Logger log = LoggerFactory.getLogger(PrestataireServiceImpl.class);

    private final PrestataireRepository prestataireRepository;

    private final PrestataireSearchRepository prestataireSearchRepository;

    public PrestataireServiceImpl(PrestataireRepository prestataireRepository, PrestataireSearchRepository prestataireSearchRepository) {
        this.prestataireRepository = prestataireRepository;
        this.prestataireSearchRepository = prestataireSearchRepository;
    }

    /**
     * Save a prestataire.
     *
     * @param prestataire the entity to save
     * @return the persisted entity
     */
    @Override
    public Prestataire save(Prestataire prestataire) {
        log.debug("Request to save Prestataire : {}", prestataire);
        Prestataire result = prestataireRepository.save(prestataire);
        prestataireSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the prestataires.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prestataire> findAll(Pageable pageable) {
        log.debug("Request to get all Prestataires");
        return prestataireRepository.findAll(pageable);
    }

    /**
     * Get one prestataire by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Prestataire findOne(Long id) {
        log.debug("Request to get Prestataire : {}", id);
        return prestataireRepository.findOne(id);
    }

    /**
     * Delete the prestataire by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prestataire : {}", id);
        prestataireRepository.delete(id);
        prestataireSearchRepository.delete(id);
    }

    /**
     * Search for the prestataire corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prestataire> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Prestataires for query {}", query);
        Page<Prestataire> result = prestataireSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
