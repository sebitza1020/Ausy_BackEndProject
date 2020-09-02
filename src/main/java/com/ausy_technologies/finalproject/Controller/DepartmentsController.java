package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

    @Autowired
    private DepartmentsService departmentsService;

    @PostMapping("/addDepartment")
    public ResponseEntity<Departments> saveDepartment(@RequestBody Departments departments) {
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

    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Departments> findDepartmentById(@PathVariable int id) {
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

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<String> deleteDepartmentById(@PathVariable int id) {
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
}
