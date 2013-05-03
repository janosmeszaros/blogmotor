package com.mjanos.blogmotor.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Taxonomy;

@Scope("session")
@Component("taxonomyBean")
public class TaxonomyBean {
    private static final Logger LOG = LoggerFactory.getLogger(TaxonomyBean.class);
    @Autowired
    @Qualifier("taxonomyGenericDAO")
    private GenericDAO<Taxonomy> dao;

    @Autowired
    private PostBean postBean;

    private boolean addNew;
    private Taxonomy newTaxonomy;
    private Map<String, Taxonomy> taxonomies;

    public Map<String, Taxonomy> getTaxonomies() {
        taxonomies = new HashMap<String, Taxonomy>();
        final List<Taxonomy> list = dao.getByCriteria();
        for (final Taxonomy tax : list) {
            taxonomies.put(tax.getName(), tax);
        }

        return taxonomies;
    }

    public void addNew() {
        dao.persist(newTaxonomy);
        taxonomies.put(newTaxonomy.getName(), newTaxonomy);
        newTaxonomy = null;
    }

    public void delete() {
        dao.delete(postBean.getNewPost().getTaxonomy());
    }

    public Taxonomy getNewTaxonomy() {
        if (newTaxonomy == null) {
            newTaxonomy = new Taxonomy();
        }
        return newTaxonomy;
    }

    public void setNewTaxonomy(final Taxonomy newTaxonomy) {
        this.newTaxonomy = newTaxonomy;
    }

    public boolean isAddNew() {
        return addNew;
    }

    public void setAddNew(final boolean addNew) {
        LOG.debug("AddNew setted to " + addNew);
        this.addNew = addNew;
    }

}
