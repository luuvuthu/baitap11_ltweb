package com.webdesign.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "users")
public class Users_24162122 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "fullname", length = 50)
    private String fullname;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "passwd", length = 32, nullable = false)
    private String passwd;

    @Column(name = "signup_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signupDate;

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "otp_expiry")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpExpiry;

    public Users_24162122() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }

    public Date getSignupDate() { return signupDate; }
    public void setSignupDate(Date signupDate) { this.signupDate = signupDate; }

    public Date getLastLogin() { return lastLogin; }
    public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public Date getOtpExpiry() { return otpExpiry; }
    public void setOtpExpiry(Date otpExpiry) { this.otpExpiry = otpExpiry; }

    /** Tài khoản đã kích hoạt khi otp đã được xoá (NULL). */
    public boolean isActivated() { return otp == null; }

    public boolean isAdmin() {
        return Boolean.TRUE.equals(isAdmin);
    }
}
