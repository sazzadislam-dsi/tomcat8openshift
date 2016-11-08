package com.lynas.controller;

import com.lynas.Util;
import com.lynas.exception.AppExceptionHandler;
import com.lynas.model.Book;
import com.lynas.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LynAs on 24-Mar-16
 */
@RestController
@RequestMapping("book")
public class BookController extends AppExceptionHandler {

    private final BookService bookService;
    private final Util util;

    @Autowired
    public BookController(Util util, BookService bookService) {
        this.util = util;
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> create(@RequestBody Book book, @PathVariable long organization_id) {
        book.setOrganization(util.getOrganizationOrThrowError(organization_id));
        book.setId(bookService.post(book));
        return util.respOK(book);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/organization_id/{organization_id}")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getBooksOfCurrentlyAuthenticatedUser(@PathVariable long organization_id) {
        return util.respOK(
                bookService.getBookListByOrganization(
                        util.getOrganizationOrThrowError(organization_id)));
    }
}
