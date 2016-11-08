package com.lynas.controller;

import com.lynas.Util;
import com.lynas.model.AppUser;
import com.lynas.model.json.request.NewPassword;
import com.lynas.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.lynas.AnnonymousFunctionKt.encryptPassword;

/**
 * Created by LynAs on 22-Mar-16
 */
@RestController
@RequestMapping("app_user")
public class AppUserController {

    private final AppUserService appUserService;
    private final Util util;

    @Autowired
    public AppUserController(AppUserService appUserService, Util util) {
        this.appUserService = appUserService;
        this.util = util;
    }

    /**
     * Create New AppUser existing Organization
     */
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody AppUser appUser) {
        if (null != appUserService.loadUserByUsername(appUser.getUsername())) {
            return util.respOK(null);
        }
        appUser.setOrganization(util.getAppUserFromToken(request).getOrganization());
        appUser.setId(appUserService.post(appUser));
        return util.respOK(appUser);
    }


    /**
     * Update user password
     */
    @RequestMapping(method = RequestMethod.PATCH)
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> updatePassword(HttpServletRequest request, @RequestBody NewPassword newPassword) {
        AppUser appUserFromToken = util.getAppUserFromToken(request);
        appUserFromToken.setPassword(encryptPassword(newPassword.getPassword()));
        appUserService.patch(appUserFromToken);
        return util.respOK(appUserFromToken);
    }


}
