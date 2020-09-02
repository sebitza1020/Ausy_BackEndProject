package com.ausy_technologies.finalproject.Repository;

import com.ausy_technologies.finalproject.Model.DAO.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findById(int id);

    List<Employee> findAllByOrderByFirstName();

    List<Employee> findAllByOrderByLastName();

    List<Employee> findAllByOrderBySalary();
}
