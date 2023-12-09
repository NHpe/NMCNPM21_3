package com.group18.dormitory.Model;

public class UserInformation {
    private String id;
    private String fullName;
    private String birthday;
    private String gender;
    private String email;
    private String phoneNumber;
    private String citizenId;
    private String address;

    public UserInformation() {
    }

    public UserInformation(String _id, String _name, String _birthday, String _gender, String _email, String _phone, String _citizen, String _address) {
        this.id = _id;
        this.fullName = _name;
        this.birthday = _birthday;
        this.gender = _gender;
        this.email = _email;
        this.phoneNumber = _phone;
        this.citizenId = _citizen;
        this.address = _address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
