package com.lynas.controller;

import com.lynas.Util;
import com.lynas.model.Organization;
import com.lynas.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LynAs on 22-Mar-16
 */
@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationService organizationService;
    private final Util util;

    @Autowired
    public OrganizationController(OrganizationService organizationService, Util util) {
        this.organizationService = organizationService;
        this.util = util;
    }

    /** Create New Organization */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody Organization organization){
        organization.setId(organizationService.post(organization));
        return util.respOK(organization);
    }
}
