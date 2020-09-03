package com.ausy_technologies.finalproject.Service;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import com.ausy_technologies.finalproject.Repository.JobCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobCategoriesService {


    @Autowired
    JobCategoriesRepository jobCategoriesRepository;

    public JobCategories saveJobCategory(JobCategories jobCategories){

        if(jobCategories.isValid()) {
            return this.jobCategoriesRepository.save(jobCategories);
        }
        else {
            throw new ErrorResponse("Job category is null",204);
        }
    }

    public List<JobCategories> findAllJobCategories(){
        List<JobCategories> jobCategoriesList =  jobCategoriesRepository.findAll();
        if(jobCategoriesList.size() == 0){
            throw new ErrorResponse("jobcategories table is null !",404);
        }
        else {
            return jobCategoriesList;
        }
    }

    public void deleteJobById(int id){
        JobCategories jobCategories = null;
        try {
            jobCategories = jobCategoriesRepository.findById(id);
        }catch (RuntimeException e){
            throw new ErrorResponse("Job category not found!",404);
        }
        jobCategoriesRepository.delete(jobCategories);
    }

    public JobCategories findJobCategoryById(int id) {
        JobCategories jobCategories = jobCategoriesRepository.findById(id);
        if(jobCategories == null) {
            throw new ErrorResponse("Job category not found!", 404);
        }
        else {
            return jobCategories;
        }
    }

    public JobCategories updateJobCategory(JobCategories jobCategories, int id) {
        JobCategories updatedJobCategory = findJobCategoryById(id);
        if (updatedJobCategory.isValid()) {
            updatedJobCategory.setName(jobCategories.getName());
        }
        else {
            throw new ErrorResponse("Job category not found!", 404);
        }

        jobCategoriesRepository.save(updatedJobCategory);
        return updatedJobCategory;
    }
}
