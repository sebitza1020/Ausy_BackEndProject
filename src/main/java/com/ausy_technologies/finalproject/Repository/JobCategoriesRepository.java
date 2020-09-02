package com.ausy_technologies.finalproject.Repository;

import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobCategoriesRepository extends JpaRepository<JobCategories, Integer> {

    @Override
    List<JobCategories> findAll();

    JobCategories findById(int id);
}
