package com.example.employee.responsedto;

import java.util.List;

public class EmployeeDetailsResponseDto {

    private List<EmployeeResponseDto> employeeResponseList;

    public List<EmployeeResponseDto> getEmployeeResponseList() {
        return employeeResponseList;
    }

    public void setEmployeeResponseList(List<EmployeeResponseDto> employeeResponseList) {
        this.employeeResponseList = employeeResponseList;
    }
}
