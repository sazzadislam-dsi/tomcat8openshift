package com.lynas.service;

import com.lynas.model.Organization;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface OrganizationService {
    long post(Organization organization);

    Organization get(long id);

    List<Organization> getOrganizationList();
}
