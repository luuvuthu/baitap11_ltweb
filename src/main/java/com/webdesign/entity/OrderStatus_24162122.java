package com.webdesign.entity;

/**
 * Các trạng thái của đơn hàng.
 * Giá trị trùng khớp với cột status trong bảng orders (database.sql) —
 * muốn đổi trạng thái khi demo chỉ cần UPDATE trong MySQL:
 * UPDATE orders SET status = N'Đã xác nhận' WHERE orderid = 1;
 */
public final class OrderStatus_24162122 {

    public static final String NEW          = "Đơn hàng mới";
    public static final String CONFIRMED    = "Đã xác nhận";
    public static final String PREPARING    = "Chuẩn bị hàng";
    public static final String SHIPPING     = "Vận chuyển";
    public static final String DELIVERING   = "Giao hàng";
    public static final String DELIVERED    = "Đã giao";
    public static final String CANCELLED    = "Đơn hàng hủy";
    public static final String RETURNED     = "Đơn hàng hoàn";

    /** Danh sách tất cả trạng thái theo đúng thứ tự quy trình, để hiển thị bộ lọc. */
    public static final String[] ALL = {
            NEW, CONFIRMED, PREPARING, SHIPPING, DELIVERING, DELIVERED, CANCELLED, RETURNED
    };

    private OrderStatus_24162122() {
    }
}
