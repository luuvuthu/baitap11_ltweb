package com.webdesign.entity;

import java.io.Serializable;

public class RatingId_24162122 implements Serializable {

    private Integer user;
    private Integer book;

    public RatingId_24162122() {
    }

    public RatingId_24162122(Integer user, Integer book) {
        this.user = user;
        this.book = book;
    }

    public Integer getUser() { return user; }
    public void setUser(Integer user) { this.user = user; }

    public Integer getBook() { return book; }
    public void setBook(Integer book) { this.book = book; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingId_24162122 that = (RatingId_24162122) o;
        return user.equals(that.user) && book.equals(that.book);
    }

    @Override
    public int hashCode() {
        return 31 * user.hashCode() + book.hashCode();
    }
}
