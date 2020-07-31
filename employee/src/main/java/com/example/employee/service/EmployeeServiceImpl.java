package com.example.employee.service;

import com.example.employee.dao.EmployeeDAO;
import com.example.employee.model.EmployeeModel;
import com.example.employee.requestdto.EmployeeRequestDto;
import com.example.employee.responsedto.EmployeeDetailsResponseDto;
import com.example.employee.responsedto.EmployeeResponse;
import com.example.employee.responsedto.EmployeeResponseDto;
import com.example.employee.responsedto.LoginResponseDto;
import com.github.gchudnov.squel.*;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.data.Row;
import org.sql2o.data.Table;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Value("${connection.url}")
	private String dbURL;

	@Value("${connection.userName}")
	private String dbUserName;

	@Value("${connection.password}")
	private String dbPassword;

    private Sql2o dbConnectionSql2o = null;


    @Override
    public boolean addEmployee(EmployeeRequestDto employeeModel) throws Exception {
        boolean isAdded = false;
        try {
            dbConnectionSql2o = new Sql2o(dbURL, dbUserName, dbPassword);
            try( Connection connection = dbConnectionSql2o.open()){
                Table table = getEmployeeDetails(employeeModel, connection);
               if(table.rows().size() > 0)  {
                   return false;
               }
               isAdded = insertEmployee(connection, employeeModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return isAdded;
    }

    @Override
    public EmployeeDetailsResponseDto getEmployeeList(EmployeeRequestDto employeeModel) throws Exception {
        EmployeeDetailsResponseDto empDetails = null;

        String empSelectQuery = Squel.select().from("EMPLOYEE").where("name = '" + employeeModel.getName() + "'" ).toString();

        dbConnectionSql2o = new Sql2o(dbURL, dbUserName, dbPassword);

        try( Connection connection = dbConnectionSql2o.open()){
            Query sqlEmpQuery = connection.createQuery( empSelectQuery);
            Table empTable = sqlEmpQuery.executeAndFetchTable();

            if(empTable.rows().size() > 0){

                String selectSessionQuery = Squel.select().from("EmployeeSession").where("empUniqueId ='" + empTable.rows().get(0).getString("empUniqueId") + "'" )
                        .where("ISALIVE = 1").toString();

                Query sqlSessQuery = connection.createQuery( empSelectQuery);
                Table sessTable = sqlEmpQuery.executeAndFetchTable();
                System.out.println("select  - > " + selectSessionQuery);

                if(sessTable != null && sessTable.rows().size() > 0) {
                    System.out.println("rows -- > " + sessTable.rows().toString());
                   // if(employeeModel.getSessionToken().equalsIgnoreCase(sessTable.rows().get(0).getString("sessionToken"))) {
                        String sqlQuery = Squel.select().from("EMPLOYEE").where("AGE BETWEEN " + employeeModel.getMinAge() + " and " + employeeModel.getMaxAge()).
                                where("gender = '" + employeeModel.getGender() + "'" ).toString();
                        System.out.println("select query -- > " + sqlQuery);

                        Query sql2oQuery = connection.createQuery( sqlQuery);
                        empTable = sql2oQuery.executeAndFetchTable();
                        List<EmployeeResponseDto> employeeResponseDto =  null;
                        if(empTable != null && empTable.rows().size() > 0) {
                            empDetails = new EmployeeDetailsResponseDto();
                            employeeResponseDto = new ArrayList<>();
                            EmployeeResponseDto response =  null;
                            for (Row row : empTable.rows()) {
                                response = new EmployeeResponseDto();
                                response.setAge(row.getInteger("age"));
                                response.setEmpUID(row.getString("empUniqueId"));
                                response.setGender(row.getString("gender"));
                                response.setName(row.getString("name"));
                                employeeResponseDto.add(response);
                            }
                            empDetails.setEmployeeResponseList(employeeResponseDto);
                        }
                   // }
                }

            }


        }
        return empDetails;

    }

    private boolean insertEmployee(Connection connection, EmployeeRequestDto employeeModel) {
        Query sql2oQuery = null;
        try {
            String empUniqueId = Generators.timeBasedGenerator().generate().toString();
            String sqlQuery = Squel.insert().into("EMPLOYEE").set("empUniqueId", empUniqueId).
                    set("name", employeeModel.getName()).set("age", employeeModel.getAge()).
                    set("gender", employeeModel.getGender()).toString();

            System.out.println("Insert query -- > " + sqlQuery);
            sql2oQuery = connection.createQuery( sqlQuery);
            sql2oQuery.executeUpdate();

            String sqlCredQuery = Squel.insert().into("Credentials").set("empUID", empUniqueId).
                    set("username", employeeModel.getName()).
                    set("password", employeeModel.getPassword()).toString();
            sql2oQuery = connection.createQuery( sqlCredQuery);
            sql2oQuery.executeUpdate();
        } catch (Exception e) {
            return  false;
        }
        return true;
    }

    private Table getEmployeeDetails(EmployeeRequestDto employeeModel, Connection connection) {
        String selectEmpQuery = Squel.select().from("EMPLOYEE").where("name = '" + employeeModel.getName() + "'").toString();
        Query sql2oQuery = connection.createQuery( selectEmpQuery);
        return sql2oQuery.executeAndFetchTable();
    }

    @Override
    public LoginResponseDto login(EmployeeRequestDto employeeModel) throws Exception {
        LoginResponseDto loginResponseDto = null;
        try{
            Query sql2oQuery = null;
            String sessionToken = "";
            dbConnectionSql2o = new Sql2o(dbURL, dbUserName, dbPassword);
            try( Connection connection = dbConnectionSql2o.open()){
                Table table = getEmployeeDetails(employeeModel, connection);
                if(table.rows().size() > 0) {
                    Row row = table.rows().get(0);
                    String empUID = row.getString("empUniqueId").toString();
                    String sqlCredQuery = Squel.select().from("Credentials").where("empUID = '" + empUID+ "'").toString();

                    System.out.println("sql cred query -- > " + sqlCredQuery);
                    Table credentialsTable = connection.createQuery(sqlCredQuery).executeAndFetchTable();

                    if(credentialsTable != null && credentialsTable.rows().size() > 0) {
                        Row credRow = credentialsTable.rows().get(0);
                        loginResponseDto = new LoginResponseDto();
                        System.out.println("user name -- " + credRow.getString("username") + "  " + employeeModel.getName());
                        System.out.println("password -- " + credRow.getString("password") + "  " + employeeModel.getPassword());
                        if(credRow.getString("password").equalsIgnoreCase(employeeModel.getPassword())){

                            System.out.println("inside login");
                            String sqlQuery = Squel.select().from("EmployeeSession").where("empUniqueId = '" + empUID + "'").where("ISALIVE = 1").toString();
                            Query credQuery = connection.createQuery(sqlQuery);
                            Table sessionTable = credQuery.executeAndFetchTable();

                            if(sessionTable != null && sessionTable.rows().size() > 0 ){
                                Row sessionRow = sessionTable.rows().get(0);
                                sessionToken = sessionRow.getString("sessionToken");
                            } else {
                                sessionToken = Generators.timeBasedGenerator().generate().toString();
                                String sqlSessionInsertQuery = Squel.insert().into("EmployeeSession").set("empUniqueId", row.getString("empUniqueId")).
                                        set("sessionToken", sessionToken).
                                        set("isAlive", 1).toString();
                                connection.createQuery( sqlSessionInsertQuery).executeUpdate();
                            }
                            loginResponseDto.setEmpId(row.getString("empUniqueId"));
                            loginResponseDto.setSessionToken(sessionToken);
                        }
                    }


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginResponseDto;
    }
}
