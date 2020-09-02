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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(deptAdded);
    }

    @GetMapping("/findDepartmentBy/{id}")
    public ResponseEntity<Departments> findDepartmentById(@PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "findDepartment");
        Departments department = null;
        try {
            department = this.departmentsService.findDeptById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(department);
    }

    @GetMapping("/findAllDepartments")
    public ResponseEntity<List<Departments>> findAllDepartments() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "findAllDepartments");
        List<Departments> departmentsList;
        try {
            departmentsList = this.departmentsService.findAllDepartments();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(departmentsList);
    }

    @DeleteMapping("/deleteDepartment")
    public ResponseEntity<String> deleteDepartmentById(@RequestParam int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded", "deleteDepartment");
        try {
            this.departmentsService.deleteDeptById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body("No value present");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body("Department deleted!");
    }
}
