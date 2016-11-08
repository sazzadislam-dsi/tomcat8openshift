package com.lynas.controller;

import com.lynas.Util;
import com.lynas.exception.AppExceptionHandler;
import com.lynas.exception.InvalidException;
import com.lynas.model.AccountTransaction;
import com.lynas.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

/**
 * Created by LynAs on 24-Mar-16
 */
@RestController
@RequestMapping("account_transaction")
public class AccountTransactionController extends AppExceptionHandler {

    private final AccountTransactionService accountTransactionService;
    private final Util util;

    @Autowired
    public AccountTransactionController(Util util, AccountTransactionService accountTransactionService) {
        this.util = util;
        this.accountTransactionService = accountTransactionService;
    }


    /** Add new Account Transaction */
    @RequestMapping(method = RequestMethod.POST,
            value = "/account_id/{account_id}/book_id/{book_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<AccountTransaction> create(
            @RequestBody AccountTransaction accountTransaction,
            UriComponentsBuilder ucb,
            @PathVariable long account_id,
            @PathVariable long book_id,
            @PathVariable long organization_id) {
        accountTransaction.setAccount(util.getAccountOrThrowError(account_id, book_id, organization_id));
        // validate entryType
        util.validateEntryType(accountTransaction.getEntryType());
        // validate color type
        util.validateColorType(accountTransaction.getColorType());
        accountTransaction.setId(accountTransactionService.post(accountTransaction));
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = ucb
                .path("/account_transaction/")
                .path(String.valueOf(accountTransaction.getId()))
                .build()
                .toUri();
        headers.setLocation(locationUri);
        return new ResponseEntity<>(accountTransaction, headers, HttpStatus.CREATED);
    }

    /** Get Account Transaction of a day */
    @RequestMapping(method = RequestMethod.GET,
            value = "/book_id/{book_id}/organization_id/{organization_id}/date/{date}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getAccountTransactionOfADay(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                                     @PathVariable long book_id,
                                                     @PathVariable long organization_id) {
        return util.respOK(
                accountTransactionService
                        .getAccountTransactionOfADate(
                                date,
                                util.getBookOrThrowError(book_id, organization_id)));
    }

    /** Delete a Account Transaction */
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/account_transaction_id/{account_transaction_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> deleteAccountTransaction(@PathVariable long account_transaction_id,
                                                      @PathVariable long organization_id) {
        return util.respOK(
                accountTransactionService.delete(
                        util.getAccountTransactionOrThrowError(
                                account_transaction_id, organization_id)));
    }


    /** Update Account Transaction color */
    @RequestMapping(method = RequestMethod.PATCH,
            value = "/account_transaction_id/{account_transaction_id}/organization_id/{organization_id}/color/{color}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> patchAccountTransaction(
            @PathVariable long account_transaction_id,
            @PathVariable long organization_id,
            @PathVariable String color) {
        util.validateColorType(color);
        AccountTransaction accountTransaction = util.getAccountTransactionOrThrowError(account_transaction_id, organization_id);
        accountTransaction.setColorType(color);
        accountTransactionService.patch(accountTransaction);
        return util.respOK(accountTransaction);
    }

    /** Account Transaction of a account*/
    @RequestMapping(method = RequestMethod.GET,
            value = "/account_id/{accountId}/book_id/{bookId}/organization_id/{organizationId}/year/{year}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getAccountTransactionOfAnAccount(
            @PathVariable long accountId,
            @PathVariable long bookId,
            @PathVariable long organizationId,
            @PathVariable int year) {
        if (organizationId != util.getLoggedInUserOrganization().getId()) {
            throw new InvalidException(organizationId, "Unauthorized organization id", null);
        }
        return util.respOK(accountTransactionService
                .getAccountTransactionListByAccountIdBookIdOrganizationIdYear(accountId, bookId, organizationId, year));
    }


    /** Balance sheet of a year */
    @RequestMapping(method = RequestMethod.GET,
            value = "balance_sheet/bk_id/{bookId}/org_id/{organizationId}/yr/{year}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> balanceSheetFormatted(
            @PathVariable long bookId,
            @PathVariable long organizationId,
            @PathVariable int year) {
        util.getBookOrThrowError(bookId, organizationId);
        return util.respOK(accountTransactionService
                .getBalanceSheetOfYear(year, bookId, organizationId));
    }

}