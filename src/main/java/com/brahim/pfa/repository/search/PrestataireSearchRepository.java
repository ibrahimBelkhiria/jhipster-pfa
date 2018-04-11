package com.brahim.pfa.repository.search;

import com.brahim.pfa.domain.Prestataire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Prestataire entity.
 */
public interface PrestataireSearchRepository extends ElasticsearchRepository<Prestataire, Long> {
}
