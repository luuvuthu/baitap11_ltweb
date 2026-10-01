package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/** Đơn hàng (thanh toán COD). */
@Entity
@Table(name = "orders")
public class Orders_24162122 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Integer orderid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private Users_24162122 user;

    @Column(name = "receiver_name", length = 50)
    private String receiverName;

    @Column(name = "receiver_phone", length = 20)
    private String receiverPhone;

    @Column(name = "receiver_address", length = 255)
    private String receiverAddress;

    @Column(name = "total", precision = 12, scale = 2)
    private BigDecimal total;

    /** Phương thức thanh toán: COD */
    @Column(name = "payment_method", length = 20)
    private String paymentMethod;

    /** Trạng thái: Chờ xử lý / Đang giao / Hoàn thành / Đã hủy */
    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderDetail_24162122> details = new ArrayList<>();

    public Orders_24162122() {
    }

    public Integer getOrderid() { return orderid; }
    public void setOrderid(Integer orderid) { this.orderid = orderid; }

    public Users_24162122 getUser() { return user; }
    public void setUser(Users_24162122 user) { this.user = user; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public List<OrderDetail_24162122> getDetails() { return details; }
    public void setDetails(List<OrderDetail_24162122> details) { this.details = details; }
}
