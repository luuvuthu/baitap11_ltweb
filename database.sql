-- =============================================
-- Database: bookstore
-- DBMS: MySQL (user: root / password: 22090020)
-- Chạy: mysql -u root -p < database.sql
-- =============================================

DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bookstore;
SET NAMES utf8mb4;

-- Bảng users
CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(50)  NOT NULL UNIQUE,
    fullname    VARCHAR(50),
    phone       INT,
    passwd      VARCHAR(32)  NOT NULL,
    signup_date DATETIME,
    last_login  DATETIME,
    is_admin    TINYINT(1),  -- tương đương bit
    otp         VARCHAR(6),  -- mã OTP kích hoạt (NULL = đã kích hoạt)
    otp_expiry  DATETIME     -- thời hạn hiệu lực của OTP
);

-- Bảng author
CREATE TABLE author (
    author_id     INT AUTO_INCREMENT PRIMARY KEY,
    author_name   VARCHAR(100),
    date_of_birth DATE
);

-- Bảng books
CREATE TABLE books (
    bookid       INT AUTO_INCREMENT PRIMARY KEY,
    isbn         INT,
    title        VARCHAR(200),
    publisher    VARCHAR(100),
    price        DECIMAL(10,2),
    description  TEXT,
    publish_date DATE,
    cover_image  VARCHAR(100),
    quantity     INT
);

-- Bảng book_author (liên kết nhiều-nhiều books <-> author)
CREATE TABLE book_author (
    bookid    INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (bookid, author_id),
    FOREIGN KEY (bookid)    REFERENCES books(bookid)  ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE
);

-- Bảng rating (liên kết users <-> books)
CREATE TABLE rating (
    userid      INT NOT NULL,
    bookid      INT NOT NULL,
    rating      TINYINT UNSIGNED,
    review_text TEXT,
    PRIMARY KEY (userid, bookid),
    FOREIGN KEY (userid) REFERENCES users(id)   ON DELETE CASCADE,
    FOREIGN KEY (bookid) REFERENCES books(bookid) ON DELETE CASCADE
);

-- Bảng cart_items (giỏ hàng: 1 user có thể có nhiều sách, mỗi sách 1 dòng)
CREATE TABLE cart_items (
    userid   INT NOT NULL,
    bookid   INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (userid, bookid),
    FOREIGN KEY (userid) REFERENCES users(id)    ON DELETE CASCADE,
    FOREIGN KEY (bookid) REFERENCES books(bookid) ON DELETE CASCADE
);

-- Bảng orders (đơn hàng, thanh toán COD)
CREATE TABLE orders (
    orderid          INT AUTO_INCREMENT PRIMARY KEY,
    userid           INT NOT NULL,
    receiver_name    VARCHAR(50),
    receiver_phone   VARCHAR(20),
    receiver_address VARCHAR(255),
    total            DECIMAL(12,2),
    payment_method   VARCHAR(20) DEFAULT 'COD',
    status           VARCHAR(30) DEFAULT N'Đơn hàng mới',
    order_date       DATETIME,
    FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE
);

-- Bảng order_details (chi tiết đơn hàng: sách + số lượng + giá tại thời điểm đặt)
CREATE TABLE order_details (
    detailid INT AUTO_INCREMENT PRIMARY KEY,
    orderid  INT NOT NULL,
    bookid   INT NOT NULL,
    quantity INT NOT NULL,
    price    DECIMAL(10,2),
    FOREIGN KEY (orderid) REFERENCES orders(orderid) ON DELETE CASCADE,
    FOREIGN KEY (bookid)  REFERENCES books(bookid)
);

-- =============================================
-- Dữ liệu mẫu
-- =============================================
INSERT INTO users (email, fullname, phone, passwd, signup_date, is_admin) VALUES
('admin', N'Quản trị viên', 901234567, MD5('admin123'), NOW(), 1),
('LVT', N'Lưu Vũ Thu', 905112233, MD5('24162122'), NOW(), 0),
('tranthib@gmail.com', N'Trần Thị B', 907889900, MD5('123456'), NOW(), 0);

INSERT INTO author (author_name, date_of_birth) VALUES
(N'Nguyễn Nhật Ánh', '1975-05-10'),
('J.K. Rowling', '1965-07-31'),
(N'Tố Hữu', '1920-10-04');

INSERT INTO books (isbn, title, publisher, price, description, publish_date, cover_image, quantity) VALUES
(893604123, N'Tôi thấy hoa vàng trên cỏ xanh', N'Nhã Nam', 89000, N'Truyện thiếu nhi của Nguyễn Nhật Ánh.', '2015-03-01', 'hoa_vang.jpg', 50),
(747532745, 'Harry Potter and the Philosopher''s Stone', 'Bloomsbury', 150000, 'The first Harry Potter book.', '1997-06-26', 'harry_potter.jpg', 30),
(60400111, N'Thu điếu', N'Kim Đồng', 60000, N'Tập thơ Tố Hữu.', '2000-01-01', 'tho_dieu.jpg', 20);

INSERT INTO book_author (bookid, author_id) VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO rating (userid, bookid, rating, review_text) VALUES
(2, 1, 5, N'Sách rất hay, đáng đọc.'),
(3, 2, 4, 'A magical story.'),
(2, 3, 4, N'Thơ giàu cảm xúc.');

-- =============================================
-- NÂNG CẤP CHO TÍNH NĂNG OTP (ĐĂNG KÝ + KÍCH HOẠT QUA EMAIL)
-- Schema bên trên (CREATE TABLE users) đã có sẵn 2 cột otp, otp_expiry.
-- Nếu database users đã tồn tại TRƯỚC khi có cập nhật này (chưa có cột otp),
-- chạy riêng 2 câu sau thay vì chạy lại toàn bộ file:
-- ALTER TABLE users ADD COLUMN otp VARCHAR(6);
-- ALTER TABLE users ADD COLUMN otp_expiry DATETIME;
-- otp = NULL  ->  tài khoản đã kích hoạt, được phép đăng nhập
-- otp != NULL ->  chưa kích hoạt, phải nhập OTP (hạn 5 phút, lưu ở otp_expiry)
-- =============================================

-- =============================================
-- Dữ liệu test phân trang (tổng 12 sách => 2 trang x 6 sách)
-- =============================================
INSERT INTO author (author_name, date_of_birth) VALUES
(N'Vũ Trọng Phụng', '1912-10-20'),
(N'Nam Cao', '1917-10-29'),
(N'Emily Brontë', '1818-07-30'),
(N'Antoine de Saint-Exupéry', '1900-06-29'),
(N'Dale Carnegie', '1888-11-24'),
(N'Paulo Coelho', '1947-08-24'),
(N'Rosie Nguyễn', '1988-09-10');

INSERT INTO books (isbn, title, publisher, price, description, publish_date, cover_image, quantity) VALUES
(893508999, N'Số đỏ', N'Kim Đồng', 72000, N'Tiểu thuyết trào phúng của Vũ Trọng Phụng.', '1936-10-05', 'so_do.jpg', 40),
(893523112, N'Chí Phèo', N'Đông Á', 65000, N'Truyện ngắn kinh điển của Nam Cao.', '1941-05-20', 'chi_pheo.jpg', 35),
(893497556, N'Đồi gió hú', N'Nhã Nam', 118000, N'Tiểu thuyết của Emily Brontë.', '1847-12-14', 'doi_gio_hu.jpg', 25),
(893521878, N'Hoàng tử bé', N'Nhã Nam', 88000, N'Truyện thiếu nhi kinh điển của Antoine de Saint-Exupéry.', '1943-04-06', 'hoang_tu_be.jpg', 60),
(893603441, N'Mắt biếc', N'Nhã Nam', 95000, N'Truyện tình của Nguyễn Nhật Ánh.', '2016-11-15', 'mat_biec.jpg', 45),
(893525664, N'Đắc nhân tâm', N'NXB Tổng hợp TP.HCM', 128000, N'Sách kỹ năng của Dale Carnegie.', '1936-10-12', 'dac_nhan_tam.jpg', 70),
(893527890, N'Nhà giả kim', N'NXB Tổng hợp TP.HCM', 79000, N'Tiểu thuyết của Paulo Coelho.', '1988-05-01', 'nha_gia_kim.jpg', 55),
(893501223, N'Tuổi trẻ đáng giá bao nhiêu', N'NXB Trẻ', 98000, N'Sách truyền cảm hứng của Rosie Nguyễn.', '2016-08-30', 'tuoi_tre.jpg', 30),
(893498771, N'Hoàng Lê nhất thống chí', N'NXB Văn học', 150000, N'Chương hồi tiểu thuyết lịch sử.', '1798-01-01', 'hoang_le.jpg', 18);

INSERT INTO book_author (bookid, author_id) VALUES
(4, 4), (5, 5), (6, 6), (7, 7), (8, 1), (9, 8), (10, 9), (11, 10), (12, 3);

INSERT INTO rating (userid, bookid, rating, review_text) VALUES
(2, 4, 4, N'Trào phúng rất duyên.'),
(3, 5, 5, N'Kinh điển đáng đọc.'),
(2, 6, 3, N'Khá hay.'),
(3, 7, 5, N'Câu chuyện diệu kỳ.'),
(2, 8, 5, N'Ấm áp, sâu lắng.'),
(3, 9, 4, N'Hữu ích.'),
(2, 10, 4, N'Hấp dẫn.'),
(3, 11, 4, N'Đáng đọc.'),
(2, 12, 3, N'Giàu tri thức lịch sử.');

-- =============================================
-- Dữ liệu mẫu cho chức năng GIỎ HÀNG + THANH TOÁN COD
-- =============================================

-- Giỏ hàng mẫu của user LVT (id=2)
INSERT INTO cart_items (userid, bookid, quantity) VALUES
(2, 1, 2),
(2, 7, 1);

-- Đơn hàng mẫu của user LVT (id=2) — mỗi đơn 1 trạng thái khác nhau
-- để demo chức năng LỊCH SỬ ĐẶT HÀNG LỌC THEO TRẠNG THÁI.
-- 8 trạng thái hợp lệ của cột status:
--   N'Đơn hàng mới', N'Đã xác nhận', N'Chuẩn bị hàng', N'Vận chuyển',
--   N'Giao hàng', N'Đã giao', N'Đơn hàng hủy', N'Đơn hàng hoàn'
INSERT INTO orders (userid, receiver_name, receiver_phone, receiver_address, total, payment_method, status, order_date) VALUES
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 89000,  'COD', N'Đơn hàng mới',   NOW()),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 150000, 'COD', N'Đã xác nhận',    DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 72000,  'COD', N'Chuẩn bị hàng',  DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 65000,  'COD', N'Vận chuyển',     DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 118000, 'COD', N'Giao hàng',      DATE_SUB(NOW(), INTERVAL 4 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 177000, 'COD', N'Đã giao',        DATE_SUB(NOW(), INTERVAL 6 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 88000,  'COD', N'Đơn hàng hủy',   DATE_SUB(NOW(), INTERVAL 7 DAY)),
(2, N'Lưu Vũ Thu', '0905112233', N'273 An Dương Vương, Quận 5, TP.HCM', 95000,  'COD', N'Đơn hàng hoàn',  DATE_SUB(NOW(), INTERVAL 8 DAY));

-- Chi tiết đơn hàng mẫu (số lượng x giá khớp với total của từng đơn)
INSERT INTO order_details (orderid, bookid, quantity, price) VALUES
(1, 1, 1, 89000),                -- 89.000  = 1 x 89.000
(2, 2, 1, 150000),               -- 150.000 = 1 x 150.000
(3, 4, 1, 72000),                -- 72.000  = 1 x 72.000
(4, 5, 1, 65000),                -- 65.000  = 1 x 65.000
(5, 6, 1, 118000),               -- 118.000 = 1 x 118.000
(6, 1, 1, 89000), (6, 7, 1, 88000),  -- 177.000 = 89.000 + 88.000
(7, 7, 1, 88000),                -- 88.000  = 1 x 88.000
(8, 8, 1, 95000);                -- 95.000  = 1 x 95.000

-- =============================================
-- HƯỚNG DẪN DEMO: QUAN SÁT TRẠNG THÁI ĐƠN HÀNG THAY ĐỔI
-- Trạng thái đơn KHÔNG đổi trên giao diện — sửa trực tiếp trong database
-- rồi bấm F5 trang "Đơn hàng của tôi" (/orders) để quan sát.
-- Ví dụ:
--   UPDATE orders SET status = N'Đã xác nhận'   WHERE orderid = 1;
--   UPDATE orders SET status = N'Vận chuyển'    WHERE orderid = 1;
--   UPDATE orders SET status = N'Đã giao'       WHERE orderid = 1;
--   UPDATE orders SET status = N'Đơn hàng hủy'  WHERE orderid = 1;
-- Sau khi UPDATE, đơn sẽ tự chuyển sang đúng nhóm chip lọc tương ứng.
-- =============================================

-- =============================================
-- NÂNG CẤP CHO GIỎ HÀNG + THANH TOÁN COD
-- Nếu database đã tồn tại TRƯỚC khi có cập nhật này (chưa có 3 bảng
-- cart_items / orders / order_details), chạy riêng 3 câu CREATE TABLE
-- tương ứng ở trên thay vì chạy lại toàn bộ file.
-- =============================================
