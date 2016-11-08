package com.lynas.service;

import com.lynas.model.Book;
import com.lynas.model.Stock;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface StockService {
    long post(Stock stock);

    Stock get(long id);

    Stock patch(Stock stock);

    boolean delete(long id);

    List<Stock> getStockListByBook(Book book);

    Stock getStockByStockIdBookIdOrganizationId(long stockId, long bookId, long organizationId);

    List<Stock> getStockListByBookOrganization(long bookId, long organizationId);

    void customDelete(long stockId);

}
