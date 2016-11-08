package com.lynas.service;

import com.lynas.model.Book;
import com.lynas.model.Stock;
import com.lynas.model.StockTransaction;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface StockTransactionService {
    long post(StockTransaction stockTransaction);

    StockTransaction get(long id);

    StockTransaction patch(StockTransaction stockTransaction);

    boolean delete(long id);

    List<StockTransaction> getStockTransactionListByStock(Stock stock);

    List<StockTransaction> getStockTransactionListByBookYear(Book book, int year);

    StockTransaction getStockTransactionByStockTransactionIdOrganizationId(long stockTransactionId, long organizationId);
}
