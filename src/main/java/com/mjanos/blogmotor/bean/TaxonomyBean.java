package com.mjanos.blogmotor.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Taxonomy;

/**
 * Handling taxonomies.
 * @author Janos_Gyula_Meszaros
 */
@Scope("session")
@Component("taxonomyBean")
public class TaxonomyBean {
    @Autowired
    @Qualifier("taxonomyGenericDAO")
    private GenericDAO<Taxonomy> dao;

    @Autowired
    private PostBean postBean;

    private Taxonomy newTaxonomy;
    private Map<String, Taxonomy> taxonomies;

    /**
     * Get all taxonomies.
     * @return map of taxonomies.
     */
    public Map<String, Taxonomy> getTaxonomies() {
        taxonomies = new HashMap<String, Taxonomy>();
        final List<Taxonomy> list = dao.getByCriteria();
        for (final Taxonomy tax : list) {
            taxonomies.put(tax.getName(), tax);
        }

        return taxonomies;
    }

    /**
     * Add new taxonomy.
     */
    public void addNew() {
        dao.persist(newTaxonomy);
        taxonomies.put(newTaxonomy.getName(), newTaxonomy);
        newTaxonomy = null;
    }

    /**
     * Delete selected taxonomy.
     */
    public void delete() {
        dao.delete(postBean.getNewPost().getTaxonomy());
    }

    /**
     * Return new taxonomy.
     * @return {@link Taxonomy}
     */
    public Taxonomy getNewTaxonomy() {
        if (newTaxonomy == null) {
            newTaxonomy = new Taxonomy();
        }
        return newTaxonomy;
    }

    public void setNewTaxonomy(final Taxonomy newTaxonomy) {
        this.newTaxonomy = newTaxonomy;
    }
}
