package com.lynas.controller;

import com.lynas.Util;
import com.lynas.exception.AppExceptionHandler;
import com.lynas.model.StockTransaction;
import com.lynas.model.json.StockTransactionJson;
import com.lynas.model.json.SuccessJson;
import com.lynas.service.StockTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by LynAs on 24-Mar-16
 */
@RestController
@RequestMapping("stock_transaction")
public class StockTransactionController extends AppExceptionHandler {

    private final Util util;
    private final StockTransactionService stockTransactionService;

    @Autowired
    public StockTransactionController(Util util, StockTransactionService stockTransactionService) {
        this.util = util;
        this.stockTransactionService = stockTransactionService;
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/stock_id/{stock_id}/book_id/{book_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> create(@RequestBody StockTransaction stockTransaction,
                                    @PathVariable long stock_id,
                                    @PathVariable long book_id,
                                    @PathVariable long organization_id) {
        util.validateStockEntryType(stockTransaction.getEntryType());
        stockTransaction.setStock(util.getStockOrThrowError(stock_id, book_id, organization_id));
        stockTransaction.setId(stockTransactionService.post(stockTransaction));
        return util.respOK(stockTransaction);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/list/book_id/{book_id}/organization_id/{organization_id}/year/{year}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getListOfYear(@PathVariable long book_id,
                                           @PathVariable long organization_id,
                                           @PathVariable int year) {

        List<StockTransaction> stockTransactionListByBookYear = stockTransactionService.getStockTransactionListByBookYear(
                util.getBookOrThrowError(book_id, organization_id), year);
        System.out.println(stockTransactionListByBookYear);
        return util.respOK(stockTransactionListByBookYear);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/list/book_id/{book_id}/organization_id/{organization_id}/stock_id/{stock_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getStockDetailsOfAStock(@PathVariable long book_id,
                                                     @PathVariable long organization_id,
                                                     @PathVariable long stock_id) {
        return util.respOK(
                stockTransactionService.getStockTransactionListByStock(
                        util.getStockOrThrowError(stock_id, book_id, organization_id)));
    }


    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> deleteStockTransaction(@RequestBody StockTransactionJson stockTransactionJson) {
        System.out.println("hrere");
        return util.respOK(
                new SuccessJson(stockTransactionService.delete(
                        util.getStockTransactionOrThrowError(
                                stockTransactionJson.getStockTransactionId(),
                                stockTransactionJson.getOrganizationId())
                                .getId()))
        );
    }


}












