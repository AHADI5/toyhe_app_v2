package com.toyhe.app.Hr.HrService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.toyhe.app.Hr.Dtos.Requests.DepartmentRegisterRequest;
import com.toyhe.app.Hr.Dtos.Response.DepartmentResponse;
import com.toyhe.app.Hr.Models.Department;
import com.toyhe.app.Hr.Models.Employee;
import com.toyhe.app.Hr.Repository.DepartmentRepository;
import com.toyhe.app.Hr.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public record   DepartmentService(
        DepartmentRepository departmentRepository ,
        EmployeeRepository employeeRepository ,
        ObjectMapper objectMapper

) {
    //TODO : create department
    public ResponseEntity<DepartmentResponse> createDepartment(DepartmentRegisterRequest  registerRequest) {
        //Finding the manager ID

        Department department = Department.builder()
                .departmentName(registerRequest.departmentName())
                .manager(getEmployee(registerRequest.managerID()))
                .parentDepartment(getDepartment(registerRequest.parentDepartmentID()))
                .DepartmentChildren(registerRequest.childrenDepartmentIDs().isEmpty() ? null :getDepartments(registerRequest.childrenDepartmentIDs()) )

                .build();
        return ResponseEntity.ok(DepartmentResponse.fromEntity(departmentRepository.save(department))) ;
    }

    public List<DepartmentResponse> getAllDepartment() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse> departmentResponses = new ArrayList<>();
        for (Department department : departments) {
            DepartmentResponse departmentResponse = DepartmentResponse.fromEntity(department);
            departmentResponses.add(departmentResponse);
        }
        return departmentResponses;
    }
    //TODO : update department
    public DepartmentResponse updateDepartment(long departmentID, JsonPatch registerRequest) throws JsonPatchException, JsonProcessingException {
        Department department = getDepartment(departmentID);
        Department departmentPatched = applyPatchToDepartment(registerRequest  , department) ;
        return DepartmentResponse.fromEntity(departmentRepository.save(departmentPatched));

    }

    private Department applyPatchToDepartment(JsonPatch patch, Department targetDepartment)
            throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetDepartment, JsonNode.class));
        return objectMapper.treeToValue(patched, Department.class);
    }

    public Department getDepartment(long departmentID) {
        return departmentRepository.findById(departmentID).orElse(null) ;
    }
    public List<Department> getDepartments(List<Long> departmentIDs) {
        return departmentRepository.findAllById(departmentIDs);
    }

    public Employee getEmployee(long employeeID) {
        return employeeRepository.findById(employeeID).orElse(null);
    }



}
