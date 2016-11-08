package com.lynas.service.impl;

import com.lynas.model.Organization;
import com.lynas.service.OrganizationService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */

@Service(value = "organizationService")
public class OrganizationServiceImpl implements OrganizationService {
    private final SessionFactory sessionFactory;

    @Autowired
    public OrganizationServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public long post(Organization organization) {
        return (long) sessionFactory.getCurrentSession().save(organization);
    }

    @Transactional
    @Override
    public Organization get(long id) {
        return sessionFactory.getCurrentSession().get(Organization.class, id);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Organization> getOrganizationList() {
        return sessionFactory.getCurrentSession().createCriteria(Organization.class).list();
    }
}
