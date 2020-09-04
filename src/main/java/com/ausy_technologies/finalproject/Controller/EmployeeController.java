package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Mapper.EmployeeMapper;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import com.ausy_technologies.finalproject.Model.DTO.EmployeeDTO;
import com.ausy_technologies.finalproject.Service.DepartmentsService;
import com.ausy_technologies.finalproject.Service.EmployeeService;
import com.ausy_technologies.finalproject.Service.JobCategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Api(tags = "Employees Management RESTful Services (Employee Controller)", value = "EmployeeController",
        description = "Controller for Employee Management Service")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapper employeeMapper;

    @ApiOperation(value = "Add employee")
    @PostMapping(value = "/addEmployee")
    public ResponseEntity<Employee> addEmployee(@ApiParam("Employee information for a new employee to be created")
                                                    @RequestBody Employee employee){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "saveEmployee");
        Employee employeeAdded = null;
        try {
            employeeAdded = this.employeeService.saveEmployee(employee);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(employeeAdded);
    }

    @ApiOperation(value = "Add employee by departmentId and jobCategoryId")
    @PostMapping("addEmployee/{department}/{jobcategory}")
    public ResponseEntity<Employee> addEmployee(@ApiParam("Employee information for a new employee to be created")
                                                    @RequestBody Employee employee ,
                                                @ApiParam("Department's id for a new employee to be assigned")
                                                @PathVariable int department,
                                                @ApiParam("Job category's id for a new employee to be assigned")
                                                @PathVariable int jobcategory){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","addEmployeeDepJob");
        Employee employeeResp = null;
        try {
            employeeResp = employeeService.addEmployee(employee,department,jobcategory);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).body(employeeResp);

    }

    @ApiOperation(value = "Retrieve all employees")
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllEmployees");
        List<Employee> employeeList;
        try {
            employeeList = this.employeeService.findAllEmployees();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeList);
    }

    @ApiOperation(value = "Update employee by employeeId, departmentId and jobCategoryId")
    @PutMapping("/updateEmployee/{employeeId}/{departmentId}/{jobCategoryId}")
    public ResponseEntity<Employee> updateEmployee(@ApiParam("Employee's new information necessary to be updated")
                                                       @RequestBody Employee employee,
                                                   @ApiParam("Employee's id necessary to be updated")
                                                   @PathVariable int employeeId,
                                                   @ApiParam("Department's id necessary to be updated")
                                                   @PathVariable int departmentId,
                                                   @ApiParam("Job category's id necessary to be updated")
                                                       @PathVariable int jobCategoryId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateEmployees");
        Employee employeeUpdated;
        try {
            employeeUpdated = this.employeeService.updateEmployee(employee, employeeId, departmentId, jobCategoryId);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(employeeUpdated);
    }

    @ApiOperation(value = "Retrieve an employee by its id")
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<Employee> findEmployee(@ApiParam("Employee's id necessary to be retrieved")
                                                     @PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getEmployee");
        Employee employee;
        try {
            employee = this.employeeService.findEmployeeById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employee);
    }

    @ApiOperation(value = "Retrieve employee DTO by its id")
    @GetMapping("/getEmployeeDTO/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeDTO(@ApiParam("Employee's DTO id necessary to be retrieved")
                                                          @PathVariable int id){
        EmployeeDTO employeeDTO = null;
        Employee employee = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeeDTO");
        try {
            employee = employeeService.findEmployeeById(id);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTO = employeeMapper.convertEmployeeToDto(employee);
        return ResponseEntity.status(HttpStatus.FOUND).headers(httpHeaders).body(employeeDTO);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO")
    @GetMapping("/getAllEmployeesDTO")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesDTO(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getAllEmployeesDTO");

        try {
            employeeList = employeeService.findAllEmployees();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO by department's id")
    @GetMapping("/getEmployeesByDepartment/{departmentid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDep(@ApiParam("Department's id necessary to retrieve an Employee DTO list")
                                                                  @PathVariable int departmentid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDep");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByDepartment(departmentid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO by jobCategory's id")
    @GetMapping("/getEmployeesByJob/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByJob(@ApiParam("Job category's id necessary to retrieve an Employee DTO list")
                                                                  @PathVariable int jobid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByJob");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeeByJobId(jobid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO by department's id and jobCategory's id")
    @GetMapping("/getEmployeesDTOByDepartmentAndJob/{departmentid}/{jobid}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeeByDepandJob(@ApiParam("Department's id necessary to retrieve an Employee DTO list")
                                                                        @PathVariable int departmentid,
                                                                    @ApiParam("Job category's id necessary to retrieve an Employee DTO list")
                                                                    @PathVariable int jobid){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response","getEmployeesByDepartmentAndJobId");
        List<Employee> employeeList;
        List<EmployeeDTO> employeeDTOList;

        try {
            employeeList = employeeService.findEmployeesByDepAndJob(jobid,departmentid);
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body(null);
        }
        employeeDTOList = employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Delete an employee")
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@ApiParam("Employee's id necessary to be deleted")
                                                     @PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteEmployee");
        try {
            this.employeeService.deleteEmployeeById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Employee not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Employee deleted!");
    }

    @ApiOperation(value = "Retrieve a list of employees DTO and orders by first name")
    @GetMapping("/getEmployeesOrderByFirstName")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOrderByFirstName(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getEmployeesOrderByFirstName");

        try {
            employeeList = employeeService.findAllEmployeesOrderByFirstName();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO and orders by last name")
    @GetMapping("/getEmployeesOrderByLastName")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOrderByLastName(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getEmployeesOrderByLastName");

        try {
            employeeList = employeeService.findAllEmployeesOrderByLastName();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }

    @ApiOperation(value = "Retrieve a list of employees DTO and orders by salary")
    @GetMapping("/getEmployeesOrderBySalary")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesOrderBySalary(){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        List<Employee> employeeList = null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Resolve","getEmployeesOrderBySalary");

        try {
            employeeList = employeeService.findAllEmployeesOrderBySalary();
        }catch (ErrorResponse e){
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
        }
        employeeList.stream().map(e -> employeeMapper.convertEmployeeToDto(e)).forEach(employeeDTOList::add);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(employeeDTOList);
    }
}
