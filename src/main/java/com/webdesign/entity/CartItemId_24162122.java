package com.webdesign.entity;

import java.io.Serializable;

/** Khóa chính ghép của bảng cart_items (userid + bookid). */
public class CartItemId_24162122 implements Serializable {

    private Integer userid;
    private Integer bookid;

    public CartItemId_24162122() {
    }

    public CartItemId_24162122(Integer userid, Integer bookid) {
        this.userid = userid;
        this.bookid = bookid;
    }

    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public Integer getBookid() { return bookid; }
    public void setBookid(Integer bookid) { this.bookid = bookid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemId_24162122)) return false;
        CartItemId_24162122 that = (CartItemId_24162122) o;
        return java.util.Objects.equals(userid, that.userid)
                && java.util.Objects.equals(bookid, that.bookid);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(userid, bookid);
    }
}
