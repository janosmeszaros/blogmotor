package com.mjanos.blogmotor.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Taxonomy;

@Component("taxonomyBean")
public class TaxonomyBean {
    private Map<String, Taxonomy> taxonomies;

    @Autowired
    @Qualifier("taxonomyGenericDAO")
    private GenericDAO<Taxonomy> dao;

    public Map<String, Taxonomy> getTaxonomies() {
        taxonomies = new HashMap<String, Taxonomy>();
        final List<Taxonomy> list = dao.getByCriteria();
        for (final Taxonomy tax : list) {
            taxonomies.put(tax.getName(), tax);
        }

        return taxonomies;
    }
}
