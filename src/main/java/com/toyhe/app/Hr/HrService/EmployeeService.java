package com.toyhe.app.Hr.HrService;

import com.toyhe.app.Auth.Dtos.Requests.NewInUserRequest;
import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Auth.Model.InUser;
import com.toyhe.app.Auth.Model.User;
import com.toyhe.app.Auth.Services.UserManagementService;
import com.toyhe.app.Hr.Dtos.Requests.EmployeeRequest;
import com.toyhe.app.Hr.Dtos.Response.EmployeeResponse;
import com.toyhe.app.Hr.Models.Employee;
import com.toyhe.app.Hr.Repository.EmployeeRepository;
import com.toyhe.app.Hr.Repository.PositionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public record EmployeeService(
       DepartmentService departmentService  ,
       PositionService positionService  ,
       EmployeeRepository employeeRepository ,
       UserManagementService userManagementService
        ) {

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .firstName(employeeRequest.firstName())
                .lastName(employeeRequest.lastName())
                .email(employeeRequest.email())
                .phone(employeeRequest.phone())
                .department(departmentService.getDepartment(employeeRequest.departmentID()))
                .position(positionService.getPosition(employeeRequest.positionID()))
                .build();
        return EmployeeResponse.fromEntity(employeeRepository.save(employee)) ;

    }

    public EmployeeResponse getEmployeeByID(int employeeID) {
        return  EmployeeResponse.fromEntity(getEmployee(employeeID)) ;

    }

    public  Employee getEmployee(int employeeID) {
        return employeeRepository.findById((long) employeeID).orElseThrow();
    }

    public ResponseEntity<NewAccountResponse> createUserFromEmployee (long employeeID) {
        Employee  employee  = getEmployee((int) employeeID) ;
        NewInUserRequest newInUserRequest  = new NewInUserRequest(
                employee.getFirstName()  ,
                employee.getLastName() ,
                null ,
                employee.getEmail(),
                employee.getFirstName(),
                new ArrayList<>() ,
                employee.getPhone() ,
                "" ,
                "" ,
                ""
        )  ;

        return  ResponseEntity.ok(userManagementService.createInUser(newInUserRequest));
    }
}
