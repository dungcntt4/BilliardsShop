package org.example.projectbilliardsshop.model;

import org.example.projectbilliardsshop.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Tên người dùng không được để trống")
    @Pattern(regexp = "^[\\p{L}\\s]*$", message = "Tên người dùng không được chứa ký tự đặc biệt")
    private String fullname;

    @NotEmpty(message = "Địa chỉ không được để trống")
    @Pattern(regexp = "^[\\p{L}\\s]*$", message = "Địa chỉ không được chứa ký tự đặc biệt")
    private String address;

    @NotEmpty(message = "Email không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email không đúng định dạng")
    private String email;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không đúng định dạng")
    private String phonenumber;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Mật khẩu phải chứa ít nhất 8 ký tự, 1 chữ hoa, 1 chữ thường và 1 số")
    private String password;

    @Enumerated(EnumType.STRING) // Lưu giá trị enum dưới dạng chuỗi trong database
    @Column(nullable = false)
    private Role role;

    // Constructors
    public User() {
    }

    public User(String fullname, String address, String email, String phonenumber, String password, Role role) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.role = role;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}