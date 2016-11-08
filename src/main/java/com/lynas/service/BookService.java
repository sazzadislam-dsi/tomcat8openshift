package com.lynas.service;

import com.lynas.model.Book;
import com.lynas.model.Organization;

import java.util.List;

/**
 * Created by LynAs on 22-Mar-16
 */
public interface BookService {
    long post(Book book);

    Book get(long id);

    List<Book> getBookListByOrganization(Organization organization);

    boolean delete(long book);

    Book getBookByBookIdAndOrganization(long bookId, Organization organization);

    Book getBookByBookIdAndOrganizationId(long bookId, long organization_id);
}
