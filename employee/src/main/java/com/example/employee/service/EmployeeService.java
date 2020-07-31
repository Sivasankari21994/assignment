package com.example.employee.service;

import com.example.employee.model.EmployeeModel;
import com.example.employee.requestdto.EmployeeRequestDto;
import com.example.employee.responsedto.EmployeeDetailsResponseDto;
import com.example.employee.responsedto.LoginResponseDto;


public interface EmployeeService {

    public boolean addEmployee(EmployeeRequestDto employeeModel) throws Exception;

    public EmployeeDetailsResponseDto getEmployeeList(EmployeeRequestDto employeeModel) throws Exception;

    public LoginResponseDto login(EmployeeRequestDto employeeModel)throws Exception;


}
