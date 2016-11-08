package com.lynas.service;

import com.lynas.model.Account;
import com.lynas.model.AccountTransaction;
import com.lynas.model.Book;
import com.lynas.model.util.BalanceSheet;

import java.util.Date;
import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface AccountTransactionService {
    long post(AccountTransaction accountTransaction);

    AccountTransaction get(long id);

    AccountTransaction patch(AccountTransaction accountTransaction);

    boolean delete(AccountTransaction accountTransaction);

    List<AccountTransaction> getAccountTransactionsByAccount(Account account);

    List<AccountTransaction> getAccountTransactionOfADate(Date date, Book book);

    AccountTransaction getAccountTransactionByIdAndBookId(long accountTransactionId, long bookId);

    List<AccountTransaction> getAccountTransactionListByAccountIdBookIdOrganizationIdYear(
            long accountId, long bookId, long organizationId, int year);

    List<AccountTransaction> getAccountTransactionListByAccountIdBookIdOrganizationIdEntryDateYear
            (long bookId, long organizationId, Date entryDate);

    List<AccountTransaction> getAccountTransactionListByBookIdOrganizationIdYear
            (long bookId, long organizationId, int year);

    AccountTransaction getAccountTransactionByAccountTransactionIdAccountIdBookIdOrganizationId(
            long accountTransactionId,long accountId, long bookId, long organizationId);

    AccountTransaction getAccountTransactionByAccountTransactionIdOrganizationId(
            long accountTransactionId, long organizationId);

    List<BalanceSheet> getBalanceSheetOfYear(int year, long bookId, long organizationId);
}
