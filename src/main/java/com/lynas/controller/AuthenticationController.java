package com.lynas.controller;

import com.lynas.AppConstant;
import com.lynas.Util;
import com.lynas.model.AppUser;
import com.lynas.model.Organization;
import com.lynas.model.json.request.AuthenticationRequest;
import com.lynas.model.json.request.SignUpRequest;
import com.lynas.model.json.response.AuthenticationResponse;
import com.lynas.model.security.SpringSecurityUser;
import com.lynas.security.TokenUtils;
import com.lynas.service.AppUserService;
import com.lynas.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.lynas.AnnonymousFunctionKt.encryptPassword;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final AppUserService appUserService;
    private final OrganizationService organizationService;
    private final Util util;

    @Autowired
    public AuthenticationController(Util util, TokenUtils tokenUtils, OrganizationService organizationService, AppUserService appUserService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.util = util;
        this.tokenUtils = tokenUtils;
        this.organizationService = organizationService;
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticationRequest(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        // Perform the authentication
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-authentication so we can generate token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            String token = this.tokenUtils.generateToken(userDetails);

            // Return the token
            return util.respOK(new AuthenticationResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return util.respOK(new AuthenticationResponse(null));
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public ResponseEntity<?> authenticationRequest(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        SpringSecurityUser user = (SpringSecurityUser) this.userDetailsService.loadUserByUsername(username);
        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = this.tokenUtils.refreshToken(token);
            return util.respOK(new AuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


    /**
     * Create New AppUser with New Organization
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sign_up")
    public ResponseEntity<?> createSignUpInfo(@RequestBody SignUpRequest signUpRequest) {
        if (null != appUserService.loadUserByUsername(signUpRequest.getUserName())) {
            return util.respOK(null);
        }
        AppUser appUser = new AppUser(
                1L,
                signUpRequest.getUserName(),
                encryptPassword(signUpRequest.getPassword()),
                "ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_MANAGER",
                new Organization(
                        organizationService.post(new Organization(1, signUpRequest.getOrganizationName())),
                        signUpRequest.getOrganizationName()));
        appUser.setId(appUserService.post(appUser));
        return util.respOK(appUser);
    }

}
