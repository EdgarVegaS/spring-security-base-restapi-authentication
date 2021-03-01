package com.tibianos.tibianosfanpage.models.request;

public class UserDetailRequestModel {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDetailRequestModel() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDetailRequestModel [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
                + ", password=" + password + "]";
    }

}
