package com.mjanos.blogmotor.bean.helper;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mjanos.blogmotor.model.Taxonomy;

@FacesConverter(value = "TaxonomyConverter", forClass = Taxonomy.class)
public class TaxonomyConverter implements Converter
{
    private static final Map<Long, Taxonomy> taxonomies = new HashMap<>();

    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, final String value)
    {
        return taxonomies.get(Long.parseLong(value));
    }

    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value)
    {
        final Taxonomy taxonomy = (Taxonomy) value;
        taxonomies.put(taxonomy.getId(), taxonomy);
        return String.valueOf(taxonomy.getId());
    }
}