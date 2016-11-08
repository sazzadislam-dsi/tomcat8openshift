package com.lynas.controller;

import com.lynas.Util;
import com.lynas.exception.AppExceptionHandler;
import com.lynas.model.Account;
import com.lynas.model.json.AccountJson;
import com.lynas.model.json.AccountUpdateJson;
import com.lynas.model.json.SuccessJson;
import com.lynas.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LynAs on 24-Mar-16
 */
@RestController
@RequestMapping("account")
public class AccountController extends AppExceptionHandler {

    @Autowired
    private Util util;
    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST, value = "/book_id/{book_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> create(@PathVariable long book_id, @PathVariable long organization_id, @RequestBody Account account) {
        account.setBook(util.getBookOrThrowError(book_id, organization_id));
        account.setId(accountService.post(account));
        return util.respOK(account);
    }

    @RequestMapping(value = "/list/book_id/{book_id}/organization_id/{organization_id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getAccountList(@PathVariable long book_id, @PathVariable long organization_id) {
        return util.respOK(accountService.getAccountListByBook(util.getBookOrThrowError(book_id, organization_id)));
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> deleteAccount(@RequestBody AccountJson accountJson) {
        util.getBookOrThrowError(accountJson.getBookId(), accountJson.getOrganizationId());
        accountService.customDelete(accountJson.getAccountId());
        System.out.println(accountJson.toString());
        return util.respOK(new SuccessJson(true));
    }


    @RequestMapping(method = RequestMethod.PATCH)
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> updateAccount(@RequestBody AccountUpdateJson accountUpdateJson) {
        util.getBookOrThrowError(accountUpdateJson.getBookId(), accountUpdateJson.getOrganizationId());
        Account account = accountService.get(accountUpdateJson.getAccountId());
        account.setName(accountUpdateJson.getAccountNewName());
        accountService.patch(account);
        return util.respOK(account);
    }


}
