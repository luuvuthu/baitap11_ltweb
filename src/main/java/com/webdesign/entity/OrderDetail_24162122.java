package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/** Một dòng chi tiết trong đơn hàng. */
@Entity
@Table(name = "order_details")
public class OrderDetail_24162122 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailid")
    private Integer detailid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderid")
    private Orders_24162122 order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookid")
    private Books_24162122 book;

    @Column(name = "quantity")
    private Integer quantity;

    /** Giá bán tại thời điểm đặt hàng. */
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    public OrderDetail_24162122() {
    }

    public Integer getDetailid() { return detailid; }
    public void setDetailid(Integer detailid) { this.detailid = detailid; }

    public Orders_24162122 getOrder() { return order; }
    public void setOrder(Orders_24162122 order) { this.order = order; }

    public Books_24162122 getBook() { return book; }
    public void setBook(Books_24162122 book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getSubtotal() {
        if (price == null || quantity == null) return BigDecimal.ZERO;
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
