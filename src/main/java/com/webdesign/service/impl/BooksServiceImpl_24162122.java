package com.webdesign.service.impl;

import com.webdesign.dao.IBooksDAO_24162122;
import com.webdesign.dao.impl.BooksDAOImpl_24162122;
import com.webdesign.entity.Books_24162122;
import com.webdesign.service.IBooksService_24162122;

import java.util.List;

public class BooksServiceImpl_24162122 implements IBooksService_24162122 {

    private final IBooksDAO_24162122 booksDAO = new BooksDAOImpl_24162122();

    @Override
    public List<Books_24162122> getAllBooks() {
        return booksDAO.findAll();
    }

    @Override
    public List<Books_24162122> getBooksPage(int page, int size) {
        return booksDAO.findPage(page, size);
    }

    @Override
    public long countAllBooks() {
        return booksDAO.countAll();
    }

    @Override
    public Books_24162122 getBookDetail(Integer bookid) {
        return booksDAO.findByIdWithAuthors(bookid);
    }

    @Override
    public Books_24162122 getBook(Integer bookid) {
        return booksDAO.findById(bookid);
    }

    @Override
    public Books_24162122 addBook(Books_24162122 book) {
        return booksDAO.insert(book);
    }

    @Override
    public Books_24162122 updateBook(Books_24162122 book) {
        return booksDAO.update(book);
    }

    @Override
    public boolean deleteBook(Integer bookid) {
        return booksDAO.delete(bookid);
    }
}
