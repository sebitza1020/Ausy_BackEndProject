package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import com.ausy_technologies.finalproject.Repository.DepartmentsRepository;
import com.ausy_technologies.finalproject.Repository.EmployeeRepository;
import com.ausy_technologies.finalproject.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentsRepository departmentsRepository;

    @Autowired
    JobCategoriesRepository jobCategoriesRepository;

    public List<Employee> findAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.size() == 0) {
            throw new ErrorResponse("Employee list not found!", 404);
        } else {
            return employeeList;
        }
    }

    public Employee saveEmployee(Employee employee) {
        if (employee == null) {
            throw new ErrorResponse("Employee is null", 204);
        } else {
            return this.employeeRepository.save(employee);
        }
    }

    public Employee addEmployee(Employee employee, int departmentid, int jobcategoryid) {
        Departments department = null;
        JobCategories jobCategory = null;
        try {
            department = departmentsRepository.findById(departmentid).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e, "No value present for this department id", 404);
        }
        try {
            jobCategory = jobCategoriesRepository.findById(jobcategoryid).get();
        } catch (RuntimeException e) {
            throw new ErrorResponse(e, "No value present for this jobcategory id", 404);

        }
        employee.setDepartment(department);
        employee.setJobCategoryId(jobCategory);
        return employeeRepository.save(employee);

    }

    public Employee findEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new ErrorResponse("Employee not found!", 404);
        } else {
            return employee;
        }
    }

    public void deleteEmployeeById(int id) {
        Employee employee = null;
        try {
            employee = employeeRepository.findById(id);
        } catch (RuntimeException e) {
            throw new ErrorResponse(e.getMessage(), 404);
        }
        employeeRepository.delete(employee);
    }

    public Employee updateEmployee(Employee employee, int id) {
        Employee updatedEmployee = findEmployeeById(id);

        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());


        Departments department = departmentsRepository.findById(employee.getDepartment().getId()).get();
        JobCategories jobCategory = jobCategoriesRepository.findById(employee.getJobCategories().getId()).get();
        if (department == null || jobCategory == null) {
            throw new ErrorResponse("Department or Jobcategory is null", 404);
        }

        updatedEmployee.setDepartment(employee.getDepartment());
        updatedEmployee.setJobCategoryId(employee.getJobCategories());
        updatedEmployee.setActive(employee.isActive());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setBirthday(employee.getBirthday());
        updatedEmployee.setCP(employee.getCP());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setStartDate(employee.getStartDate());
        updatedEmployee.setEndDate(employee.getEndDate());
        updatedEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
        updatedEmployee.setManager(employee.isManager());
        updatedEmployee.setNoChildren(employee.getNoChildren());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setSocialSecurityNumber(employee.getSocialSecurityNumber());
        updatedEmployee.setStudies(employee.getStudies());
        updatedEmployee.setTelephone(employee.getTelephone());

        employeeRepository.save(updatedEmployee);
        return updatedEmployee;
    }

    public List<Employee> findEmployeeByDepartment(int departmentId) {
        List<Employee> employeeList = findAllEmployees();
        return employeeList.stream().filter(e -> e.getDepartment() != null).filter(e -> e.getDepartment().getId() == departmentId).collect(Collectors.toList());
    }

    public List<Employee> findEmployeeByJobId(int jobid) {
        List<Employee> employeeList = findAllEmployees();
        return employeeList.stream().filter(e -> e.getJobCategories() != null).filter(e -> e.getJobCategories().getId() == jobid).collect(Collectors.toList());
    }

    public List<Employee> findEmployeesByDepAndJob(int jobid, int depid) {
        List<Employee> employeeList = findAllEmployees();
        return employeeList.stream().filter(e -> e.getDepartment() != null).filter(e -> e.getDepartment().getId() == depid).filter(e -> e.getJobCategories() != null).filter(e -> e.getJobCategories().getId() == jobid).collect(Collectors.toList());
    }
}