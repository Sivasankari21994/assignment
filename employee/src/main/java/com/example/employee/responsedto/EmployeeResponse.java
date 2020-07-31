package com.example.employee.responsedto;

public class EmployeeResponse {

    private String status;
    private String statusMessage;
    private String errorMesage;
    private LoginResponseDto loginResponseDto;
    private EmployeeDetailsResponseDto employeeDetailsResponse;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }

    public LoginResponseDto getLoginResponseDto() {
        return loginResponseDto;
    }

    public void setLoginResponseDto(LoginResponseDto loginResponseDto) {
        this.loginResponseDto = loginResponseDto;
    }

    public EmployeeDetailsResponseDto getEmployeeDetailsResponse() {
        return employeeDetailsResponse;
    }

    public void setEmployeeDetailsResponse(EmployeeDetailsResponseDto employeeDetailsResponse) {
        this.employeeDetailsResponse = employeeDetailsResponse;
    }
}
