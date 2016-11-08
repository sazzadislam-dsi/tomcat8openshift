package com.lynas.service.impl;

import com.lynas.model.Book;
import com.lynas.model.Stock;
import com.lynas.service.StockService;
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

@Service(value = "stockService")
public class StockServiceImpl implements StockService {

    private final SessionFactory sessionFactory;

    @Autowired
    public StockServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public long post(Stock stock) {
        return (long) sessionFactory.getCurrentSession().save(stock);
    }

    @Transactional
    @Override
    public Stock get(long id) {
        return sessionFactory.getCurrentSession().get(Stock.class, id);
    }

    @Transactional
    @Override
    public Stock patch(Stock stock) {
        sessionFactory.getCurrentSession().update(stock);
        return get(stock.getId());
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
    public List<Stock> getStockListByBook(Book book) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Stock.class)
                .add(Restrictions.eq("book", book))
                .addOrder(Order.asc("name"))
                .list();
    }

    @Transactional
    @Override
    public Stock getStockByStockIdBookIdOrganizationId(long stockId, long bookId, long organizationId) {
        return (Stock) sessionFactory.getCurrentSession()
                .createCriteria(Stock.class)
                .createAlias("book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("id", stockId))
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .uniqueResult();

    }

    @Transactional
    @Override
    public List<Stock> getStockListByBookOrganization(long bookId, long organizationId) {
        return sessionFactory.getCurrentSession()
                .createCriteria(Stock.class)
                .createAlias("book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("book.id", bookId))
                .add(Restrictions.eq("organization.id", organizationId))
                .addOrder(Order.asc("name"))
                .list();
    }

    @Transactional
    @Override
    public void customDelete(long stockId) {
        sessionFactory
                .getCurrentSession()
                .createSQLQuery("DELETE FROM StockTransaction WHERE stock_id =" + stockId).executeUpdate();

        sessionFactory
                .getCurrentSession()
                .createSQLQuery("DELETE FROM Stock WHERE id=" + stockId).executeUpdate();

    }
}
