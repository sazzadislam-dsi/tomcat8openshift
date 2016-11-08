package com.lynas.service.impl;

import com.lynas.model.Account;
import com.lynas.model.Book;
import com.lynas.service.AccountService;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccountServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public long post(Account account) {
        return (long) sessionFactory.getCurrentSession().save(account);
    }

    @Transactional
    @Override
    public Account get(long id) {
        return sessionFactory.getCurrentSession().get(Account.class, id);
    }

    @Transactional
    @Override
    public Account patch(Account account) {
        sessionFactory.getCurrentSession().update(account);
        return get(account.getId());
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        sessionFactory.getCurrentSession().delete(get(id));
        return true;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<Account> getAccountListByBook(Book book) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .add(Restrictions.eq("book", book))
                .addOrder(Order.asc("name"))
                .list();
    }

    @Transactional
    @Override
    public Account getAccountByAccountIdBookIdOrganizationId(long accountId, long bookId, long organizationId) {
        return (Account) sessionFactory.getCurrentSession()
                .createCriteria(Account.class)
                .createAlias("book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("id", accountId))
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .uniqueResult();
    }

    @Transactional
    @Override
    public void customDelete(long accountId) {
        sessionFactory
                .getCurrentSession()
                .createSQLQuery("DELETE FROM AccountTransaction WHERE account_id =" + accountId).executeUpdate();

        sessionFactory
                .getCurrentSession()
                .createSQLQuery("DELETE FROM Account WHERE id=" + accountId).executeUpdate();

    }
}
