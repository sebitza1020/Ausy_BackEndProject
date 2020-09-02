package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import com.ausy_technologies.finalproject.Service.JobCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobcategories")
public class JobCategoriesController {

    @Autowired
    private JobCategoriesService jobCategoriesService;

    @PostMapping("/addJobCategory")
    public ResponseEntity<JobCategories> saveJob(@RequestBody JobCategories jobCategories) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "saveJobCategory");
        JobCategories jobAdded = null;
        try {
            jobAdded = this.jobCategoriesService.saveJobCategory(jobCategories);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(jobAdded);
    }

    @GetMapping("/getJobCategoryBy/{id}")
    public ResponseEntity<JobCategories> findJobCategoryById(@PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getJobCategory");
        JobCategories job;
        try {
            job = this.jobCategoriesService.findJobCategoryById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(job);
    }

    @GetMapping("/getAllJobCategories")
    public ResponseEntity<List<JobCategories>> findAllJobCategories() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "getAllJobCategories");
        List<JobCategories> jobCategoriesList;
        try {
            jobCategoriesList = this.jobCategoriesService.findAllJobCategories();
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(jobCategoriesList);
    }

    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategoriesById(@PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "deleteJobCategory");
        try {
            this.jobCategoriesService.deleteJobById(id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(httpHeaders).body("Job category not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body("Job category deleted!");
    }
}
