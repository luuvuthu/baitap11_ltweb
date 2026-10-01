# Bookstore — Bài tập quá trình (Đề 1)

Sinh viên: **24162122** — Môn: Lập trình Web (JSP/Servlet + JPA/Hibernate + MySQL + Tomcat 10)

Ứng dụng bán sách trực tuyến với trang khách hàng, trang quản trị, đăng ký có kích hoạt bằng mã OTP gửi qua email, giỏ hàng và thanh toán đơn hàng bằng COD.

---

## 1. Yêu cầu môi trường

- **Java JDK 17+** (khuyến nghị bản đã cài trên máy: JDK 26)
- **Apache Maven 3.9+** (dùng plugin Cargo để tự tải và chạy Tomcat 10.1)
- **MySQL 8.x** đang chạy ở `localhost:3306`
- **Python 3** (chỉ cần cho chế độ SMTP ảo khi test chức năng OTP)

## 2. Cài đặt & chạy

### Bước 1 — Tạo database

Chạy file `database.sql` (tạo database `bookstore` + dữ liệu mẫu, trong đó có sẵn tài khoản admin):

```
mysql -u root -p < database.sql
```

Thông tin kết nối đã cấu hình trong `src/main/resources/META-INF/persistence.xml`:
user `root`, password `22090020`, database `bookstore`. Nếu máy bạn khác thì sửa lại file này.

### Bước 2 — Chạy web app

```
mvn cargo:run
```

Mở trình duyệt: **http://localhost:8080/**

> Chức năng OTP đang dùng **SMTP ảo** — xem chi tiết ở mục [Chức năng gửi mail OTP](#6-chức-năng-gửi-mail-otp--ghi-chú-cho-giảng-viên) bên dưới, cần chạy thêm 1 terminal nhỏ trước khi đăng ký.

## 3. Tài khoản có sẵn

| Loại tài khoản | Đăng nhập                              | Mật khẩu   | Ghi chú                                               |
| -------------- | -------------------------------------- | ---------- | ----------------------------------------------------- |
| Admin          | `admin`                                | `admin123` | Vào được trang quản trị `/admin`                      |
| Khách hàng     | Tài khoản test mẫu: `LVT` / `24162122` |
| Khách hàng     | (tự đăng ký qua trang Đăng ký)         |            | Tài khoản test mẫu: `tester.otp@gmail.com` / `123456` |

> Đăng nhập dùng **email làm tên đăng nhập** (đúng theo đề — bảng users không có cột username riêng).

## 4. Các chức năng chính

### Khách hàng

| Trang                 | URL                  | Mô tả                                                                       |
| --------------------- | -------------------- | --------------------------------------------------------------------------- |
| Trang chủ             | `/` , `/home`        | Sách nổi bật, giới thiệu                                                    |
| Đăng ký               | `/register`          | Nhập họ tên, email, SĐT, mật khẩu; xác nhận mật khẩu                        |
| Xác thực OTP          | `/verify`            | Nhập mã OTP 6 số (hạn 5 phút) để kích hoạt tài khoản; `/resend` gửi lại OTP |
| Đăng nhập / Đăng xuất | `/login` , `/logout` | Sai thông tin / chưa kích hoạt đều có thông báo riêng                       |
| Danh mục sách         | `/products`          | Xem tất cả sách, có nút thêm nhanh vào giỏ                                 |
| Chi tiết sách         | `/book?id=...`       | Thông tin sách, tác giả, đánh giá, chọn số lượng thêm vào giỏ              |
| Giỏ hàng              | `/cart`              | Xem giỏ, **sửa số lượng** (giới hạn 1..tồn kho), **xóa** từng dòng, xóa toàn bộ |
| Thêm vào giỏ          | `/cart/add` (POST)   | Thêm sách vào giỏ (đã có thì cộng dồn số lượng), không vượt quá tồn kho    |
| Cập nhật số lượng     | `/cart/update` (POST)| Thay đổi số lượng; vượt tồn kho sẽ bị từ chối kèm thông báo                |
| Xóa khỏi giỏ          | `/cart/remove` , `/cart/clear` (POST) | Xóa 1 dòng / xóa toàn bộ giỏ hàng                        |
| Thanh toán (COD)      | `/checkout`          | Nhập người nhận, SĐT, địa chỉ → tạo đơn hàng **thanh toán khi nhận hàng**  |
| Đơn hàng của tôi      | `/orders` , `/orders?status=...` | Lịch sử đặt hàng **lọc theo trạng thái**: Đơn hàng mới, Đã xác nhận, Chuẩn bị hàng, Vận chuyển, Giao hàng, Đã giao, Đơn hàng hủy, Đơn hàng hoàn |

### Quản trị (cần đăng nhập bằng tài khoản admin)

| Trang             | URL                                                                                     | Mô tả                                           |
| ----------------- | --------------------------------------------------------------------------------------- | ----------------------------------------------- |
| Dashboard         | `/admin`                                                                                | Thống kê tổng quan                              |
| Quản lý sách      | `/admin/books`                                                                          | Danh sách sách                                  |
| Thêm/sửa sách     | `/admin/book-form` , `/admin/book-save`                                                 | Nhập ISBN, tên, giá, mô tả, ảnh bìa, tác giả... |
| Xem chi tiết sách | `/admin/book-view?id=...`                                                               | Xem đầy đủ thông tin 1 sách                     |
| Xóa sách          | `/admin/book-delete?id=...`                                                             | Xóa sách                                        |
| Quản lý tác giả   | `/admin/authors` , `/admin/author-form` , `/admin/author-save` , `/admin/author-delete` | Thêm/sửa/xóa tác giả                            |

## 5. Cấu trúc mã nguồn

```
src/main/java/com/webdesign/
├── config/          JPAUtil (khởi tạo EntityManagerFactory)
├── controller/      Các servlet (HomeController, LoginController,
│                    RegisterController, ProductController,
│                    CartController, CheckoutController,
│                    AdminController, AdminBookAuthorController)
├── dao/             Interface + impl (JPA/Hibernate)
├── entity/          Các entity @Entity ánh xạ bảng trong MySQL
│                    (Cart, Orders, OrderDetail cho giỏ hàng + đơn hàng)
├── service/         Interface + impl (logic nghiệp vụ, gửi mail OTP,
│                    giỏ hàng, thanh toán COD)
src/main/resources/META-INF/persistence.xml   Cấu hình JPA + kết nối MySQL
src/main/webapp/   JSP, CSS, ảnh bìa sách (cart.jsp, checkout.jsp, orders.jsp)
tools/fake_smtp.py SMTP ảo cho chế độ dev
database.sql       Script tạo database + dữ liệu mẫu (gồm bảng cart_items,
                   orders, order_details cho giỏ hàng + đơn hàng COD)
```

## 6. Chức năng gửi mail OTP — ghi chú cho giảng viên

Hiện tại `EmailServiceImpl_24162122.java` đang trỏ tới một **SMTP ảo chạy trên chính máy** (`127.0.0.1:1025`) thay vì Gmail thật, để demo đầy đủ chức năng OTP mà không cần tài khoản Gmail / App Password.

**Cách demo khi chấm bài:**

1. Mở terminal 1, chạy SMTP ảo (giữ cửa sổ này mở):
   ```
   python tools/fake_smtp.py
   ```
   Mọi email "gửi đi" sẽ được ghi vào file **`tools/fake_mail.txt`**.
2. Mở terminal 2, chạy web app:
   ```
   mvn cargo:run
   ```
3. Trên web: đăng ký tài khoản mới bằng email bất kỳ (không cần email thật) → mở file `tools/fake_mail.txt` → lấy mã OTP 6 số → nhập vào trang **Xác thực**.

**Nếu muốn chuyển sang Gmail thật** (ví dụ demo gửi vào hộp thư thật): sửa 4 hằng số ở đầu `EmailServiceImpl_24162122.java`:

```java
private static final String SMTP_HOST     = "smtp.gmail.com";
private static final String SMTP_PORT     = "587";
private static final String FROM_EMAIL    = "<email Gmail của bạn>";
private static final String FROM_PASSWORD = "<App Password 16 ký tự>";
```

App Password được tạo tại: Google Account → Security → bật 2-Step Verification → App passwords.

> Lưu ý thêm: nếu SMTP ảo chưa chạy mà vẫn đăng ký thì tài khoản vẫn được tạo, mã OTP được in ra console của Tomcat (dòng bắt đầu bằng `[DEV]`) — đây là phương án dự phòng.

## 7. Chức năng giỏ hàng + thanh toán COD (mới)

Giỏ hàng được lưu **trong database** (bảng `cart_items`), gắn với tài khoản đăng nhập — đăng xuất rồi đăng nhập lại giỏ hàng vẫn còn.

**Luồng sử dụng:**

1. Ở trang **Sản phẩm** hoặc **Chi tiết sách**, bấm 🛒 *Thêm vào giỏ* (chọn số lượng ở trang chi tiết).
2. Vào **🛒 Giỏ hàng** trên thanh điều hướng: sửa số lượng (input giới hạn `1..tồn kho`, vượt tồn kho bị từ chối kèm thông báo), xóa từng dòng, hoặc xóa toàn bộ giỏ.
3. Bấm **Thanh toán (COD)** → nhập người nhận / SĐT (9-11 số) / địa chỉ → **Đặt hàng (COD)**.
4. Hệ thống tạo đơn hàng trong bảng `orders` + `order_details`, **trừ tồn kho** sách, **xóa giỏ hàng**, rồi chuyển tới trang **Đơn hàng của tôi** (`/orders`) hiển thị mã đơn và trạng thái `Đơn hàng mới` (COD = thanh toán tiền mặt khi nhận hàng).

**Lịch sử đặt hàng — lọc theo trạng thái (mới):**

Trang `/orders` có dãy chip lọc: **Tất cả** + 8 trạng thái theo quy trình:
`Đơn hàng mới → Đã xác nhận → Chuẩn bị hàng → Vận chuyển → Giao hàng → Đã giao` (và `Đơn hàng hủy`, `Đơn hàng hoàn`).

- Bấm 1 chip là trang tự lọc lại qua `/orders?status=<trạng thái>` (JPQL `WHERE status = ?`), đếm số đơn hiển thị ngay trên trang.
- Đơn hàng mới tạo mặc định ở trạng thái **Đơn hàng mới** (class `OrderStatus_24162122` giữ 8 hằng số, khớp chuẩn giá trị trong DB).
- **Demo quan sát trạng thái thay đổi:** trạng thái KHÔNG đổi trên giao diện — sửa trực tiếp trong MySQL rồi F5 trang `/orders`:
  ```sql
  UPDATE orders SET status = N'Đã xác nhận'  WHERE orderid = 1;
  UPDATE orders SET status = N'Vận chuyển'   WHERE orderid = 1;
  UPDATE orders SET status = N'Đơn hàng hủy' WHERE orderid = 1;
  ```
  `database.sql` đã cài sẵn 8 đơn hàng mẫu của tài khoản `LVT`, mỗi đơn 1 trạng thái khác nhau để test bộ lọc ngay.

**Các trường hợp đã xử lý:** thêm sách trùng thì cộng dồn số lượng; số lượng yêu cầu vượt tồn kho (khi thêm, khi sửa, và kiểm tra lại toàn bộ giỏ trước khi thanh toán); sách hết hàng không cho thêm; giỏ trống không vào được trang thanh toán; tạo đơn + chi tiết + trừ kho trong **một transaction** (lỗi thì rollback, không mất dữ liệu).

> Nếu database cũ chưa có 3 bảng `cart_items`, `orders`, `order_details` — chạy lại `database.sql` hoặc chạy riêng 3 câu `CREATE TABLE` tương ứng trong file này.

## 8. Một số lưu ý kỹ thuật

- Mật khẩu lưu trong DB dạng **MD5** (khớp với hàm `MD5()` của MySQL trong `database.sql`).
- Tài khoản sau khi đăng ký ở trạng thái **chưa kích hoạt** (cột `otp` khác NULL) và không đăng nhập được cho tới khi nhập đúng OTP.
- OTP có hiệu lực **5 phút**; nhập sai có thể bấm gửi lại để nhận mã mới.
- Cột `phone` trong bảng `users` là kiểu số nguyên (`INT`).
- Đơn hàng lưu **giá bán tại thời điểm đặt** (cột `price` trong `order_details`) nên không bị ảnh hưởng khi admin đổi giá sách sau này.
- Nếu Tomcat báo lỗi do cổng 8080 bị chiếm, tắt tiến trình Java cũ hoặc đổi `cargo.servlet.port` trong `pom.xml`.
