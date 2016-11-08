package com.lynas;

import com.lynas.exception.InvalidException;
import com.lynas.exception.NotFoundException;
import com.lynas.model.*;
import com.lynas.model.util.CompositeAccountTransaction;
import com.lynas.security.TokenUtils;
import com.lynas.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.lynas.AppConstant.CREDIT;
import static com.lynas.AppConstant.DEBIT;

/**
 * Created by LynAs on 23-Mar-16
 */
@Component
public class Util {
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    BookService bookService;
    @Autowired
    StockService stockService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountTransactionService accountTransactionService;
    @Autowired
    StockTransactionService stockTransactionService;


    public AppUser getAppUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(AppConstant.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(token);
        return appUserService.loadUserByUsername(username);
    }

    public Organization getLoggedInUserOrganization() {
        return appUserService.loadUserByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName())
                .getOrganization();
    }

    public List<String> getEntryTypeList() {
        return Arrays.asList("CREDIT", "DEBIT");
    }

    public List<String> getStockTransactionEntryTypeList() {
        return Arrays.asList("ADD", "SELL");
    }

    public List<String> getColorTypeList() {
        return Arrays.asList("white", "red", "green", "yellow", "brown", "blue", "purple", "orange");
    }

    public CompositeAccountTransaction getCompositeAccountTransactions(List<AccountTransaction> accountTransactionOfADate) {
        double debit = accountTransactionOfADate
                .stream()
                .filter(accountTransaction -> accountTransaction.getEntryType().equals(DEBIT))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(AccountTransaction::getAmount)
                .sum();
        double credit = accountTransactionOfADate
                .stream()
                .filter(accountTransaction -> accountTransaction.getEntryType().equals(CREDIT))
                .collect(Collectors.toList())
                .stream()
                .mapToDouble(AccountTransaction::getAmount)
                .sum();

        return new CompositeAccountTransaction(accountTransactionOfADate, debit, credit);

    }

    public Date getYearStartDate(int year) {
        try {
            return formatter.parse("01-01-" + year);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getYearEndDate(int year) {
        try {
            return formatter.parse("31-12-" + year);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBookOrThrowError(long book_id, long organization_id) {
        Book book = bookService.getBookByBookIdAndOrganizationId(book_id, organization_id);
        if (null == book || getLoggedInUserOrganization().getId() != organization_id) {
            Map errorObject = new HashMap();
            errorObject.put("object1", book_id);
            errorObject.put("object1", organization_id);
            throw new NotFoundException(errorObject, "Book Not found", null);
        }
        return book;
    }

    public Stock getStockOrThrowError(long stockId, long bookId, long organizationId) {
        Stock stock = stockService.getStockByStockIdBookIdOrganizationId(stockId, bookId, organizationId);
        if (null == stock || getLoggedInUserOrganization().getId() != organizationId) {
            Map errorObject = new HashMap();
            errorObject.put("value1", stockId);
            errorObject.put("value2", bookId);
            errorObject.put("value3", organizationId);
            throw new NotFoundException(errorObject, "Stock not found with given transaction", null);
        }
        return stock;
    }

    public StockTransaction getStockTransactionOrThrowError(long stockTransactionId, long organizationId) {
        StockTransaction stockTransaction = stockTransactionService
                .getStockTransactionByStockTransactionIdOrganizationId(stockTransactionId, organizationId);
        if (null == stockTransaction || getLoggedInUserOrganization().getId() != stockTransaction.getStock().getBook().getOrganization().getId()) {
            throw new NotFoundException(stockTransactionId, "Stock Transaction not found", null);
        }
        return stockTransaction;
    }

    public Organization getOrganizationOrThrowError(long organizationId) {
        Organization organization = organizationService.get(organizationId);
        if (null == organization || getLoggedInUserOrganization().getId() != organizationId) {
            throw new NotFoundException(organizationId, "Organization Not found with given id", null);
        }
        return organization;
    }

    public Account getAccountOrThrowError(long accountId, long bookId, long organizationId) {
        Account account = accountService.getAccountByAccountIdBookIdOrganizationId(accountId, bookId, organizationId);
        if (null == account || getLoggedInUserOrganization().getId() != organizationId) {
            throw new NotFoundException(organizationId, "Organization Not found with given id", null);
        }
        return account;
    }

    public void validateEntryType(String entryType) {
        if (!getEntryTypeList().contains(entryType)) {
            throw new InvalidException(
                    entryType,
                    "Entry Type not supported",
                    getEntryTypeList());
        }
    }

    public void validateColorType(String colorType) {
        if (!getColorTypeList().contains(colorType)) {
            throw new InvalidException(
                    colorType,
                    "Color Type not supported",
                    getColorTypeList());
        }
    }

    public AccountTransaction getAccountTransactionOrThrowError(
            long accountTransactionId, long organizationId) {
        AccountTransaction accountTransaction = accountTransactionService
                .getAccountTransactionByAccountTransactionIdOrganizationId(
                        accountTransactionId, organizationId);
        if (null == accountTransaction || getLoggedInUserOrganization().getId() != organizationId) {
            throw new NotFoundException(
                    organizationId, "AccountTransaction Not found with given id", null);
        }
        return accountTransaction;

    }

    public void validateStockEntryType(String entryType) {
        if (!getStockTransactionEntryTypeList().contains(entryType)) {
            throw new InvalidException(entryType, "entry type not allowed", getStockTransactionEntryTypeList());
        }

    }

    public Date dateEndTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        return cal.getTime();
    }


    /**
     *  use this method for response ok
     *  this wrapper is created to access it in aop for logging
     */
    public ResponseEntity<?> respOK(Object responseObject){
        return ResponseEntity.ok(responseObject);
    }
}
