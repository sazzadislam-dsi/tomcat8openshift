package com.lynas.service.impl;

import com.lynas.model.Book;
import com.lynas.model.Organization;
import com.lynas.service.BookService;
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

@Service("bookService")
public class BookServiceImpl implements BookService {


    private final SessionFactory sessionFactory;

    @Autowired
    public BookServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public long post(Book book) {
        return (long) sessionFactory.getCurrentSession().save(book);
    }

    @Transactional
    @Override
    public Book get(long id) {
        return sessionFactory.getCurrentSession().get(Book.class, id);
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public List<Book> getBookListByOrganization(Organization organization) {
        return sessionFactory
                .getCurrentSession()
                .createCriteria(Book.class)
                .add(Restrictions.eq("organization", organization))
                .addOrder(Order.asc("name"))
                .list();
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        sessionFactory.getCurrentSession().delete(get(id));
        return true;
    }

    @Transactional
    @Override
    public Book getBookByBookIdAndOrganization(long bookId, Organization organization) {
        return (Book) sessionFactory
                .getCurrentSession()
                .createCriteria(Book.class)
                .add(Restrictions.eq("organization", organization))
                .add(Restrictions.eq("id", bookId))
                .uniqueResult();
    }

    @Transactional
    @Override
    public Book getBookByBookIdAndOrganizationId(long bookId, long organization_id) {
        return (Book) sessionFactory
                .getCurrentSession()
                .createCriteria(Book.class)
                .createAlias("organization", "organization")
                .add(Restrictions.eq("id", bookId))
                .add(Restrictions.eq("organization.id", organization_id))
                .uniqueResult();
    }
}
