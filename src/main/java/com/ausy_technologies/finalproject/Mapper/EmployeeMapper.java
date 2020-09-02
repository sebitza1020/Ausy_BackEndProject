package com.ausy_technologies.finalproject.Mapper;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Employee;
import com.ausy_technologies.finalproject.Model.DTO.EmployeeDTO;
import com.ausy_technologies.finalproject.Repository.DepartmentsRepository;
import com.ausy_technologies.finalproject.Repository.EmployeeRepository;
import com.ausy_technologies.finalproject.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentsRepository departmentRepository;

    @Autowired
    JobCategoriesRepository jobCategoryRepository;

    public EmployeeDTO convertEmployeeToDto(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        if(employee != null) {
            employeeDTO.setFirstname(employee.getFirstName());
            employeeDTO.setLastname(employee.getLastName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setTelephone(employee.getTelephone());
            try {
                employeeDTO.setDepartment(employee.getDepartment().getName());
            }catch (NullPointerException e){
                ErrorResponse.LogError(new ErrorResponse(e,"Null department.",206));
                employeeDTO.setDepartment(null);
            }
            try {
                employeeDTO.setJobCategory(employee.getJobCategories().getName());
            }catch (NullPointerException e){
                ErrorResponse.LogError(new ErrorResponse(e,"Null jobcategory.",206));
                employeeDTO.setJobCategory(null);
            }
        }
        else {
            throw new ErrorResponse("Can not convert a null to EmployeeDTO",400);
        }
        return employeeDTO;
    }
}
