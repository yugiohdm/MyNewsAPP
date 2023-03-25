package com.example.mynews.bean;


import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 邓旭波
 * @since 2023-03-04
 */
public class User implements Serializable {



    private Integer uid;

    private String username;

    private String password;

    private String type;

    private String phone;

    private String email;

    private String sex;

    private String location;

    private String occupation;

    private String birthday;

    private Integer concerns;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getConcerns() {
        return concerns;
    }

    public void setConcerns(Integer concerns) {
        this.concerns = concerns;
    }

    @Override
    public String toString() {
        return "User{" +
        "uid=" + uid +
        ", username=" + username +
        ", password=" + password +
        ", type=" + type +
        ", phone=" + phone +
        ", email=" + email +
        ", sex=" + sex +
        ", location=" + location +
        ", occupation=" + occupation +
        ", birthday=" + birthday +
        ", concerns=" + concerns +
        "}";
    }
}
