package com.toyhe.app.Hr.Controllers.Employee;

import com.toyhe.app.Auth.Dtos.Responses.NewAccountResponse;
import com.toyhe.app.Hr.Dtos.Requests.EmployeeRequest;
import com.toyhe.app.Hr.Dtos.Response.EmployeeResponse;
import com.toyhe.app.Hr.HrService.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employee")
public record EmployeeController(
        EmployeeService employeeService
) {
    @PostMapping("/")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }

    @PostMapping("/createUser")
    public ResponseEntity<NewAccountResponse> createUserFromEmployee(@RequestBody int employeeId) {
        return employeeService.createUserFromEmployee(employeeId) ;
    }
}
