package com.toyhe.app.Hr.Controllers.Department;

import com.toyhe.app.Hr.Dtos.Requests.DepartmentRegisterRequest;
import com.toyhe.app.Hr.Dtos.Response.DepartmentResponse;
import com.toyhe.app.Hr.HrService.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/department")
public record DepartmentController(
        DepartmentService  departmentService
) {
    @PostMapping("/")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody DepartmentRegisterRequest request) {
        return departmentService.createDepartment(request);
    }
    @GetMapping("/")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartment()) ;
    }

    @GetMapping("/{departmentID}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable("departmentID") long departmentID) {
        return ResponseEntity.ok(DepartmentResponse.fromEntity(departmentService.getDepartment(departmentID))) ;
    }
}
