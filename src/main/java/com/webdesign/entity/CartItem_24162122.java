package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/** Một dòng trong giỏ hàng: user + sách + số lượng. */
@Entity
@Table(name = "cart_items")
@IdClass(CartItemId_24162122.class)
public class CartItem_24162122 implements Serializable {

    @Id
    @Column(name = "userid")
    private Integer userid;

    @Id
    @Column(name = "bookid")
    private Integer bookid;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private Users_24162122 user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookid", insertable = false, updatable = false)
    private Books_24162122 book;

    public CartItem_24162122() {
    }

    public CartItem_24162122(Integer userid, Integer bookid, Integer quantity) {
        this.userid = userid;
        this.bookid = bookid;
        this.quantity = quantity;
    }

    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public Integer getBookid() { return bookid; }
    public void setBookid(Integer bookid) { this.bookid = bookid; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Users_24162122 getUser() { return user; }
    public void setUser(Users_24162122 user) { this.user = user; }

    public Books_24162122 getBook() { return book; }
    public void setBook(Books_24162122 book) { this.book = book; }

    /** Thành tiền của dòng này. */
    public java.math.BigDecimal getSubtotal() {
        if (book == null || book.getPrice() == null || quantity == null) return java.math.BigDecimal.ZERO;
        return book.getPrice().multiply(java.math.BigDecimal.valueOf(quantity));
    }
}
