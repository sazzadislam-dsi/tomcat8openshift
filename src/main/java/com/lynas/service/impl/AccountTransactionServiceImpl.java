package com.lynas.service.impl;

import com.lynas.Util;
import com.lynas.model.Account;
import com.lynas.model.AccountTransaction;
import com.lynas.model.Book;
import com.lynas.model.util.BalanceSheet;
import com.lynas.service.AccountTransactionService;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */

@Service(value = "accountTransactionService")
public class AccountTransactionServiceImpl implements AccountTransactionService {
    private final SessionFactory sessionFactory;
    private final Util util;

    @Autowired
    public AccountTransactionServiceImpl(SessionFactory sessionFactory, Util util) {
        this.sessionFactory = sessionFactory;
        this.util = util;
    }

    @Transactional
    @Override
    public long post(AccountTransaction accountTransaction) {
        return (long) sessionFactory.getCurrentSession().save(accountTransaction);
    }

    @Transactional
    @Override
    public AccountTransaction get(long id) {
        return sessionFactory.getCurrentSession().get(AccountTransaction.class, id);
    }

    @Transactional
    @Override
    public AccountTransaction patch(AccountTransaction accountTransaction) {
        sessionFactory.getCurrentSession().update(accountTransaction);
        return get(accountTransaction.getId());
    }

    @Transactional
    @Override
    public boolean delete(AccountTransaction accountTransaction) {
        sessionFactory.getCurrentSession().delete(accountTransaction);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AccountTransaction> getAccountTransactionsByAccount(Account account) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("account", account))
                .addOrder(Order.desc("entryDate"))
                .list();

    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AccountTransaction> getAccountTransactionOfADate(Date startTime, Book book) {
        return sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .add(Restrictions.eq("account.book", book))
                .add(Restrictions.between("entryDate", startTime, util.dateEndTime(startTime)))
                .list();
    }


    @Transactional
    @Override
    public AccountTransaction getAccountTransactionByIdAndBookId(long accountTransactionId, long bookId) {
        return (AccountTransaction) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .add(Restrictions.eq("account.book.id", bookId))
                .add(Restrictions.eq("id", accountTransactionId))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AccountTransaction> getAccountTransactionListByAccountIdBookIdOrganizationIdYear
            (long accountId, long bookId, long organizationId, int year) {
        return (List<AccountTransaction>) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .createAlias("account.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("account.id", accountId))
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .add(Restrictions.between("entryDate", util.getYearStartDate(year), util.getYearEndDate(year)))
                .list();
    }


    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AccountTransaction> getAccountTransactionListByAccountIdBookIdOrganizationIdEntryDateYear
            (long bookId, long organizationId, Date entryDate) {

        return (List<AccountTransaction>) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .createAlias("account.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .add(Restrictions.between("entryDate", entryDate, util.dateEndTime(entryDate)))
                .list();
    }


    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<AccountTransaction> getAccountTransactionListByBookIdOrganizationIdYear
            (long bookId, long organizationId, int year) {
        return (List<AccountTransaction>) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .createAlias("account.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .add(Restrictions.between("entryDate", util.getYearStartDate(year), util.getYearEndDate(year)))
                .list();
    }

    @Transactional
    @Override
    public AccountTransaction getAccountTransactionByAccountTransactionIdAccountIdBookIdOrganizationId(
            long accountTransactionId, long accountId, long bookId, long organizationId) {
        return (AccountTransaction) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .createAlias("account.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("id", accountTransactionId))
                .add(Restrictions.eq("account.id", accountId))
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .uniqueResult();

    }

    @Transactional
    @Override
    public AccountTransaction getAccountTransactionByAccountTransactionIdOrganizationId(
            long accountTransactionId, long organizationId) {
        return (AccountTransaction) sessionFactory.getCurrentSession()
                .createCriteria(AccountTransaction.class)
                .createAlias("account", "account")
                .createAlias("account.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("id", accountTransactionId))
                .add(Restrictions.eq("organization.id", organizationId))
                .uniqueResult();

    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<BalanceSheet> getBalanceSheetOfYear(int year, long bookId, long organizationId) {
        return sessionFactory.getCurrentSession()
                .createSQLQuery(" SELECT " +
                        "  a.name as name , " +
                        "  sum(CASE WHEN act.entryType = 'CREDIT' " +
                        "    THEN act.amount " +
                        "      ELSE act.amount * -1 END) AS totals " +
                        " FROM AccountTransaction act " +
                        "  INNER JOIN Account a ON act.account_id = a.id " +
                        "  INNER JOIN Book b ON a.book_id = b.id " +
                        "  INNER JOIN Organization o ON b.organization_id = o.id " +
                        " WHERE act.entryDate BETWEEN '"+year+"-01-01 00:00:00' AND '"+year+"-12-31 23:59:59' " +
                        "  AND b.id =  " + bookId +
                        "  AND o.id =  " + organizationId +
                        " GROUP BY a.id; ")
                .setResultTransformer(Transformers.aliasToBean(BalanceSheet.class))
                .list();
    }

}
