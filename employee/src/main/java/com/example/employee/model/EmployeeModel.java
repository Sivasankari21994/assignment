package com.example.employee.model;

public class EmployeeModel {

    private String empUniqueId;
    private String name;
    private int age;
    private String gender;

    public String getEmpUniqueId() {
        return empUniqueId;
    }

    public void setEmpUniqueId(String empUniqueId) {
        this.empUniqueId = empUniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
