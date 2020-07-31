package com.example.employee.controller;

import com.example.employee.model.EmployeeModel;
import com.example.employee.requestdto.EmployeeRequestDto;
import com.example.employee.responsedto.EmployeeDetailsResponseDto;
import com.example.employee.responsedto.EmployeeResponse;
import com.example.employee.responsedto.LoginResponseDto;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody EmployeeRequestDto employeeDetails) {
        EmployeeResponse response = null;
        try {
            if(validateAddEmpUserInputs(employeeDetails)){
                if (employeeService.addEmployee(employeeDetails)) {
                    response = generateResponseMessage("Success", "new employee added", "");
                    return new ResponseEntity<Object>(response, HttpStatus.OK);
                } else
                    response = generateResponseMessage("Failure", "adding of employee failed", "User with this  name is already existing");
            }

        } catch (Exception e) {
            response = generateResponseMessage("Failure", "adding of employee failed:" , e.getMessage());

        }
        return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(value = "/employee/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody EmployeeRequestDto employeeDetails) {
        EmployeeResponse response = null;
        try {
            if(validateLoginUserInputs(employeeDetails)){

                LoginResponseDto loginResponseDto = employeeService.login(employeeDetails);
                if (loginResponseDto != null) {
                    response = generateResponseMessage("Success", "session is created", loginResponseDto, null);
                    return new ResponseEntity<Object>(response, HttpStatus.OK);
                } else
                    response = generateResponseMessage("Failure", "adding of employee failed", "User with this  name is already existing");
            }
            response = generateResponseMessage("Failure", "Error while validating inputs", "");
        } catch (Exception e) {
            response = generateResponseMessage("Failure", "adding of employee failed:" , e.getMessage());

        }
        return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/employee/getemployee", method = RequestMethod.POST)
    public ResponseEntity<Object> getEmployeeList(@RequestBody EmployeeRequestDto employeeDetails) {
        EmployeeResponse response = null;
        try {
            if(validateUserInputs(employeeDetails)){
                EmployeeDetailsResponseDto employeeDetailsResponseDto = employeeService.getEmployeeList(employeeDetails);
                //if (employeeDetailsResponseDto != null) {
                    response = generateResponseMessage("Success", "Employee list by age and gender", null,  employeeDetailsResponseDto);
                    return new ResponseEntity<Object>(response, HttpStatus.OK);
                //} else
                //    response = generateResponseMessage("Succe", "Error while getting Employee list by age and gender", "");
            }
            response = generateResponseMessage("Failure", "Error while validating inputs", "");
        } catch (Exception e) {
            response = generateResponseMessage("Failure", "Error while getting Employee list by age and gender" , e.getMessage());

        }
        return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateUserInputs(EmployeeRequestDto employeeDetails) throws Exception {{
        if(employeeDetails !=  null) {
            if (employeeDetails.getName() == null || employeeDetails.getName().trim().isEmpty())
                throw new Exception("Please enter the username");
            if (employeeDetails.getSessionToken() == null || employeeDetails.getSessionToken().trim().isEmpty())
                throw new Exception("Please enter the sessiontoken");
            if (employeeDetails.getMinAge() < 15 )
                employeeDetails.setMinAge(15);
            if (employeeDetails.getMaxAge() < 0 ||  employeeDetails.getMaxAge() < 15)
                employeeDetails.setMaxAge(55);
            if(employeeDetails.getGender() == null || employeeDetails.getGender().trim().isEmpty()) {
                throw new Exception("Please enter the gender");
            }
            else
            {
                String gender = getEmployeeGender(employeeDetails.getGender());
                if(gender.isEmpty())
                    throw new Exception("Please enter the valid gender");
                employeeDetails.setGender(gender);
            }
        }
        return true;
    }
    }

    private boolean validateLoginUserInputs(EmployeeRequestDto employeeDetails) throws Exception {
            if(employeeDetails !=  null) {
                if (employeeDetails.getName() == null || employeeDetails.getName().trim().isEmpty())
                    throw new Exception("Please enter the username");
                if (employeeDetails.getPassword() == null || employeeDetails.getPassword().trim().isEmpty())
                    throw new Exception("Please enter the password");
            }
        return true;
    }


    private boolean validateAddEmpUserInputs(EmployeeRequestDto employeeDetails) throws Exception{
            if(employeeDetails !=  null){
                if(employeeDetails.getName() == null || employeeDetails.getName().trim().isEmpty())
                    throw new Exception("Please enter the username");
                if(employeeDetails.getPassword() == null || employeeDetails.getPassword().trim().isEmpty())
                    throw new Exception("Please enter the password");
                if( employeeDetails.getAge() < 15)
                    throw new Exception("Please enter the age");
                if(employeeDetails.getGender() == null || employeeDetails.getGender().trim().isEmpty()) {
                    throw new Exception("Please enter the gender");
                }
                else
                {
                    String gender = getEmployeeGender(employeeDetails.getGender());
                    if(gender.isEmpty())
                        throw new Exception("Please enter the valid gender");
                    employeeDetails.setGender(gender);
                }

            }

        return true;
    }

    private String getEmployeeGender(String gender) {
        char genderFirstChar = gender.charAt(0);

        switch (genderFirstChar){
            case 'm':
                return "MALE";
            case 'f':
                return "FEMALE";
        }
        return "";
    }

    // region Private methods
    private EmployeeResponse generateResponseMessage(String status, String message, String errorMessage) {
        EmployeeResponse responsePojo = new EmployeeResponse();
        responsePojo.setStatus(status);
        responsePojo.setStatusMessage(message);
        responsePojo.setErrorMesage(errorMessage);
        return responsePojo;
    }

    private EmployeeResponse generateResponseMessage(String status, String message, LoginResponseDto loginResponseDto, EmployeeDetailsResponseDto employeeDetailsResponseDto) {
        EmployeeResponse responsePojo = new EmployeeResponse();
        responsePojo.setStatus(status);
        responsePojo.setStatusMessage(message);
        responsePojo.setLoginResponseDto(loginResponseDto);
        responsePojo.setEmployeeDetailsResponse(employeeDetailsResponseDto);
        return responsePojo;
    }
}
