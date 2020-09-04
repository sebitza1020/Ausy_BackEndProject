package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Service.DepartmentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Departments Management RESTful Services (Departments Controller)", value = "DepartmentsController",
    description = "Controller for Departments Management Service")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    @ApiOperation(value = "Creates a new department")
    @PostMapping("/addDepartment")
    public ResponseEntity<Departments> saveDepartment(@ApiParam("Department information for a new department to be created")
                                                          @RequestBody Departments departments) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "saveDepartment");
        Departments deptAdded = null;
        try {
            deptAdded = this.departmentsService.saveDepartment(departments);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(deptAdded);
    }

    @ApiOperation(value = "Retrieves a department")
    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Departments> findDepartmentById(@ApiParam("Department's id necessary to be retrieved")
                                                              @PathVariable int id) {
        Departments department = null;
        try {
            department = this.departmentsService.findDeptById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "findDepartment");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(department);
    }

    @ApiOperation(value = "Retrieves a list of departments")
    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Departments>> findAllDepartments() {
        List<Departments> departmentsList = null;
        try {
            departmentsList = this.departmentsService.findAllDepartments();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "findAllDepartments");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentsList);
    }

    @ApiOperation(value = "Deletes a department")
    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartmentById(@ApiParam("Department's id necessary to be deleted")
                                                           @PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteDepartment");
        try {
            this.departmentsService.deleteDeptById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Department not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Department deleted!");
    }

    @ApiOperation(value = "Updates the department's name")
    @PutMapping("/updateDepartment/{id}")
    public ResponseEntity<Departments> updateDepartment(@ApiParam("Department information necessary to be updated")
                                                            @RequestBody Departments departments,
                                                        @ApiParam("Department's id necessary to be updated")
                                                        @PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateDepartment");
        Departments departmentUpdated;
        try {
            departmentUpdated = this.departmentsService.updateDepartment(departments, id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(departmentUpdated);
    }
}
