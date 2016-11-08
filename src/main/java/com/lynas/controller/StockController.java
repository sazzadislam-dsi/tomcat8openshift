package com.lynas.controller;

import com.lynas.Util;
import com.lynas.exception.AppExceptionHandler;
import com.lynas.model.Stock;
import com.lynas.model.json.StockJson;
import com.lynas.model.json.SuccessJson;
import com.lynas.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LynAs on 24-Mar-16
 */
@RestController
@RequestMapping("stock")
public class StockController extends AppExceptionHandler{

    private final Util util;
    private final StockService stockService;

    @Autowired
    public StockController(Util util, StockService stockService) {
        this.util = util;
        this.stockService = stockService;
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/book_id/{book_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> create(@RequestBody Stock stock,
                                    @PathVariable long book_id,
                                    @PathVariable long organization_id) {
        stock.setBook(util.getBookOrThrowError(book_id,organization_id));
        stock.setId(stockService.post(stock));
        return util.respOK(stock);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/list/book_id/{book_id}/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getStockList(
                                    @PathVariable long book_id,
                                    @PathVariable long organization_id) {
        util.getBookOrThrowError(book_id, organization_id);
        return util.respOK(stockService.getStockListByBookOrganization(book_id,organization_id));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> delete(@RequestBody StockJson stockJson) {
        util.getBookOrThrowError(stockJson.getBookId(), stockJson.getOrganizationId());
        stockService.customDelete(stockJson.getStockId());
        return util.respOK(new SuccessJson(true));
    }
}
