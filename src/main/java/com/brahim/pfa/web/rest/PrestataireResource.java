package com.brahim.pfa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.brahim.pfa.domain.Prestataire;
import com.brahim.pfa.service.PrestataireService;
import com.brahim.pfa.web.rest.errors.BadRequestAlertException;
import com.brahim.pfa.web.rest.util.HeaderUtil;
import com.brahim.pfa.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Prestataire.
 */
@RestController
@RequestMapping("/api")
public class PrestataireResource {

    private final Logger log = LoggerFactory.getLogger(PrestataireResource.class);

    private static final String ENTITY_NAME = "prestataire";

    private final PrestataireService prestataireService;

    public PrestataireResource(PrestataireService prestataireService) {
        this.prestataireService = prestataireService;
    }

    /**
     * POST  /prestataires : Create a new prestataire.
     *
     * @param prestataire the prestataire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestataire, or with status 400 (Bad Request) if the prestataire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestataires")
    @Timed
    public ResponseEntity<Prestataire> createPrestataire(@Valid @RequestBody Prestataire prestataire) throws URISyntaxException {
        log.debug("REST request to save Prestataire : {}", prestataire);
        if (prestataire.getId() != null) {
            throw new BadRequestAlertException("A new prestataire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prestataire result = prestataireService.save(prestataire);
        return ResponseEntity.created(new URI("/api/prestataires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestataires : Updates an existing prestataire.
     *
     * @param prestataire the prestataire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestataire,
     * or with status 400 (Bad Request) if the prestataire is not valid,
     * or with status 500 (Internal Server Error) if the prestataire couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestataires")
    @Timed
    public ResponseEntity<Prestataire> updatePrestataire(@Valid @RequestBody Prestataire prestataire) throws URISyntaxException {
        log.debug("REST request to update Prestataire : {}", prestataire);
        if (prestataire.getId() == null) {
            return createPrestataire(prestataire);
        }
        Prestataire result = prestataireService.save(prestataire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestataire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestataires : get all the prestataires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of prestataires in body
     */
    @GetMapping("/prestataires")
    @Timed
    public ResponseEntity<List<Prestataire>> getAllPrestataires(Pageable pageable) {
        log.debug("REST request to get a page of Prestataires");
        Page<Prestataire> page = prestataireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prestataires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prestataires/:id : get the "id" prestataire.
     *
     * @param id the id of the prestataire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestataire, or with status 404 (Not Found)
     */
    @GetMapping("/prestataires/{id}")
    @Timed
    public ResponseEntity<Prestataire> getPrestataire(@PathVariable Long id) {
        log.debug("REST request to get Prestataire : {}", id);
        Prestataire prestataire = prestataireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prestataire));
    }

    /**
     * DELETE  /prestataires/:id : delete the "id" prestataire.
     *
     * @param id the id of the prestataire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestataires/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestataire(@PathVariable Long id) {
        log.debug("REST request to delete Prestataire : {}", id);
        prestataireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/prestataires?query=:query : search for the prestataire corresponding
     * to the query.
     *
     * @param query the query of the prestataire search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/prestataires")
    @Timed
    public ResponseEntity<List<Prestataire>> searchPrestataires(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Prestataires for query {}", query);
        Page<Prestataire> page = prestataireService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/prestataires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
