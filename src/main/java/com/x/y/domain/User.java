package com.x.y.domain;

import com.x.y.validate.annotation.Birthday;
import com.x.y.validate.annotation.CheckString;
import com.x.y.validate.annotation.MobilePhone;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "auth_user")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(unique = true)
    private String username;
    @CheckString
    private String password;
    private String sex;
    private String officePhone;
    @MobilePhone
    private String mobilePhone;
    private String address;
    private String realName;
    private String inputUser;
    @Birthday(message = "录入日期不能超过当前时间")
    private Date inputDate;
    private Date lastLoginTime;
    @Column(length = 64, unique = true)
    private String secretKey;
}