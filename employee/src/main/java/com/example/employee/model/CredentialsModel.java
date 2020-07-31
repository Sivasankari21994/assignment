package com.example.employee.model;

public class CredentialsModel {

    private int id;
    private String empUID;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpUID() {
        return empUID;
    }

    public void setEmpUID(String empUID) {
        this.empUID = empUID;
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
}
