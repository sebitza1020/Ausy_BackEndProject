package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentsService {

    @Autowired
    DepartmentsRepository departmentsRepository;

    public Departments saveDepartment(Departments departments){
        if (departments.isValid()) {
            return departmentsRepository.save(departments);
        }
        else
        {
            throw new ErrorResponse("Department added is null !",400);
        }
    }

    public Departments findDeptById(int id) {
        Departments departments =this.departmentsRepository.findById(id).get();
        if(departments == null) {
            throw new ErrorResponse("Department not found!", 404);
        }
        else {
            return departments;
        }
    }

    public List<Departments> findAllDepartments(){
        return this.departmentsRepository.findAll();
    }

    public void deleteDeptById(int id){
        Departments departments = null;
        try {
            departments = departmentsRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new ErrorResponse(e.getMessage(),404);
        }
        departmentsRepository.delete(departments);

    }
}