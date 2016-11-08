package com.lynas.service.impl;

import com.lynas.Util;
import com.lynas.model.Book;
import com.lynas.model.Stock;
import com.lynas.model.StockTransaction;
import com.lynas.service.StockTransactionService;
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

@Service(value = "stockTransactionService")
public class StockTransactionServiceImpl implements StockTransactionService {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    Util util;



    @Transactional
    @Override
    public long post(StockTransaction stockTransaction) {
        return (long) sessionFactory.getCurrentSession().save(stockTransaction);
    }

    @Transactional
    @Override
    public StockTransaction get(long id) {
        return sessionFactory.getCurrentSession().get(StockTransaction.class, id);
    }

    @Transactional
    @Override
    public StockTransaction patch(StockTransaction stockTransaction) {
        sessionFactory.getCurrentSession().update(stockTransaction);
        return get(stockTransaction.getId());
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
    public List<StockTransaction> getStockTransactionListByStock(Stock stock) {
        return sessionFactory.getCurrentSession()
                .createCriteria(StockTransaction.class)
                .add(Restrictions.eq("stock", stock))
                .addOrder(Order.desc("entryDate"))
                .list();
    }

    @Transactional
    @Override
    public List<StockTransaction> getStockTransactionListByBookYear(Book book, int year) {
        return sessionFactory.getCurrentSession()
                .createCriteria(StockTransaction.class)
                .createAlias("stock","stock")
                .add(Restrictions.eq("stock.book", book))
                .add(Restrictions.between("entryDate", util.getYearStartDate(year),util.getYearEndDate(year)))
                .addOrder(Order.asc("entryDate"))
                .list();
    }

    @Transactional
    @Override
    public StockTransaction getStockTransactionByStockTransactionIdOrganizationId(long stockTransactionId, long organizationId) {
        return (StockTransaction) sessionFactory.getCurrentSession()
                .createCriteria(StockTransaction.class)
                .createAlias("stock", "stock")
                .createAlias("stock.book", "book")
                .createAlias("book.organization", "organization")
                .add(Restrictions.eq("id", stockTransactionId))
                .add(Restrictions.eq("organization.id", organizationId))
                .uniqueResult();
    }


}
