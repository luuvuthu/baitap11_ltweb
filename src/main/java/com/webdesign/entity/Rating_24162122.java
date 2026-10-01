package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rating")
@IdClass(RatingId_24162122.class)
public class Rating_24162122 implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private Users_24162122 user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookid", referencedColumnName = "bookid")
    private Books_24162122 book;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review_text")
    private String reviewText;

    public Rating_24162122() {
    }

    public Users_24162122 getUser() { return user; }
    public void setUser(Users_24162122 user) { this.user = user; }

    public Books_24162122 getBook() { return book; }
    public void setBook(Books_24162122 book) { this.book = book; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}
