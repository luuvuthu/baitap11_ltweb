package com.webdesign.service.impl;

import com.webdesign.dao.IAuthorDAO_24162122;
import com.webdesign.dao.impl.AuthorDAOImpl_24162122;
import com.webdesign.entity.Author_24162122;
import com.webdesign.service.IAuthorService_24162122;

import java.util.List;

public class AuthorServiceImpl_24162122 implements IAuthorService_24162122 {

    private final IAuthorDAO_24162122 authorDAO = new AuthorDAOImpl_24162122();

    @Override
    public List<Author_24162122> getAllAuthors() {
        return authorDAO.findAll();
    }

    @Override
    public Author_24162122 getAuthor(Integer authorId) {
        return authorDAO.findById(authorId);
    }

    @Override
    public List<Author_24162122> getAuthorsPage(int page, int size) {
        return authorDAO.findPage(page, size);
    }

    @Override
    public long countAllAuthors() {
        return authorDAO.countAll();
    }

    @Override
    public java.util.Map<Integer, Long> countBooksPerAuthor() {
        return authorDAO.countBooksPerAuthor();
    }

    @Override
    public Author_24162122 addAuthor(Author_24162122 author) {
        return authorDAO.insert(author);
    }

    @Override
    public Author_24162122 updateAuthor(Author_24162122 author) {
        return authorDAO.update(author);
    }

    @Override
    public boolean deleteAuthor(Integer authorId) {
        return authorDAO.delete(authorId);
    }
}
