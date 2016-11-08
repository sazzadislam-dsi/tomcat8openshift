package com.lynas.controller

import com.lynas.model.util.EndPoint
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.util.logging.Logger

/**
 * Created by LynAs on 13-Apr-16
 */
@RestController
class RootController constructor(
        val requestMappingHandlerMapping: RequestMappingHandlerMapping,
        var logger: Logger) {


    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/")
    fun root(): String {
        return "ok"
    }


    @RequestMapping(value = "endPoints", method = arrayOf(RequestMethod.GET))
    fun getEndPointsInView(): ResponseEntity<MutableSet<RequestMappingInfo>> {
        return ResponseEntity.ok(requestMappingHandlerMapping.handlerMethods.keys)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/availableEndpoints")
    fun availableEndpoints(): ResponseEntity<List<EndPoint>> {
        return ResponseEntity.ok(listOf(
                EndPoint("account", "POST", "/book_id/{book_id}/organization_id/{organization_id}", "Account"),
                EndPoint("account", "GET", "/list/book_id/{book_id}/organization_id/{organization_id}", ""),
                EndPoint("account", "DELETE", "", "AccountJson"),


                EndPoint("account_transaction", "POST", "/account_id/{account_id}/book_id/{book_id}/organization_id/{organization_id}", "AccountTransaction"),
                EndPoint("account_transaction", "GET", "/book_id/{book_id}/organization_id/{organization_id}/date/{date}", ""),
                EndPoint("account_transaction", "DELETE", "/account_transaction_id/{account_transaction_id}/organization_id/{organization_id}", ""),
                EndPoint("account_transaction", "PATCH", "/account_transaction_id/{account_transaction_id}/organization_id/{organization_id}/color/{color}", ""),
                EndPoint("account_transaction", "GET", "/account_id/{accountId}/book_id/{bookId}/organization_id/{organizationId}/year/{year}", ""),
                EndPoint("account_transaction", "GET", "balance_sheet/bk_id/{bookId}/org_id/{organizationId}/yr/{year}", ""),


                EndPoint("app_user", "POST", "", "AppUser"),
                EndPoint("app_user", "PATCH", "", "NewPassword"),


                EndPoint("auth", "POST", "", "AuthenticationRequest"),
                EndPoint("auth", "POST", "sign_up", "SignUpRequest"),


                EndPoint("book", "POST", "/organization_id/{organization_id}", "Book"),
                EndPoint("book", "GET", "/organization_id/{organization_id}", "Book"),


                EndPoint("organization", "POST", "/organization_id/{organization_id}", "Organization"),


                EndPoint("stock", "POST", "/book_id/{book_id}/organization_id/{organization_id}", "Stock"),
                EndPoint("stock", "GET", "/list/book_id/{book_id}/organization_id/{organization_id}", ""),
                EndPoint("stock", "DELETE", "", ""),


                EndPoint("stock_transaction", "POST", "/stock_id/{stock_id}/book_id/{book_id}/organization_id/{organization_id}", "StockTransaction"),
                EndPoint("stock_transaction", "GET", "/list/book_id/{book_id}/organization_id/{organization_id}/year/{year}", ""),
                EndPoint("stock_transaction", "DELETE", "", "StockTransactionJson")
        ))
    }
}
