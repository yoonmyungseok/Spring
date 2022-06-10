package com.kh.spring.member.model.vo;

import java.sql.Date;

public class Member {
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String gender;
    private int age;
    private String phone;
    private String address;
    private Date enrollDate;
    private Date modifyDate;
    private String status;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return this.userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEnrollDate() {
        return this.enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member() {
    }

    public Member(String userId, String userPwd, String userName, String email, String gender, int age, String phone,
            String address, Date enrollDate, Date modifyDate, String status) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.modifyDate = modifyDate;
        this.status = status;
    }

	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", email=" + email
				+ ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", address=" + address + ", enrollDate="
				+ enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}

}
