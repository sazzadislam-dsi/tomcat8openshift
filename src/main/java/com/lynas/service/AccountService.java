package com.lynas.service;

import com.lynas.model.Account;
import com.lynas.model.Book;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface AccountService {
    long post(Account account);

    Account get(long id);

    Account patch(Account account);

    boolean delete(long id);

    List<Account> getAccountListByBook(Book book);

    Account getAccountByAccountIdBookIdOrganizationId(long accountId, long bookId, long organizationId);

    void customDelete(long accountId);
}
