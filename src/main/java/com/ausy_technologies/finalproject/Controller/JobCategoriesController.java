package com.ausy_technologies.finalproject.Controller;

import com.ausy_technologies.finalproject.Error.ErrorResponse;
import com.ausy_technologies.finalproject.Model.DAO.Departments;
import com.ausy_technologies.finalproject.Model.DAO.JobCategories;
import com.ausy_technologies.finalproject.Service.JobCategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Job Categories Management RESTful Services (Job Categories Controller)", value = "JobCategoriesController",
        description = "Controller for Job Categories Management Service")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/jobcategories")
public class JobCategoriesController {

    @Autowired
    private JobCategoriesService jobCategoriesService;

    @ApiOperation(value = "Creates a new job category")
    @PostMapping("/addJobCategory")
    public ResponseEntity<JobCategories> saveJob(@ApiParam("Job category information for a new job category to be created")
                                                     @RequestBody JobCategories jobCategories) {
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

    @ApiOperation(value = "Retrieves a job category")
    @GetMapping("/getJobCategoryBy/{id}")
    public ResponseEntity<JobCategories> findJobCategoryById(@ApiParam("Job catgeory's id necessary to be retrieved")
                                                                 @PathVariable int id) {
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

    @ApiOperation(value = "Retrieves a list of job categories")
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

    @ApiOperation(value = "Deletes a job category")
    @DeleteMapping("/deleteJobCategory/{id}")
    public ResponseEntity<String> deleteJobCategoriesById(@ApiParam("Department's id necessary to be deleted")
                                                              @PathVariable int id) {
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

    @ApiOperation(value = "Updates the job category's name")
    @PutMapping("/updateJobCategory/{id}")
    public ResponseEntity<JobCategories> updateJobCategory(@ApiParam("Job category's new information necessary to be updated")
                                                               @RequestBody JobCategories jobCategories,
                                                           @ApiParam("Job category's id necessary to be updated")
                                                           @PathVariable int id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Response", "updateJobCategory");
        JobCategories jobCategoryUpdated;
        try {
            jobCategoryUpdated = this.jobCategoriesService.updateJobCategory(jobCategories, id);
        } catch (ErrorResponse e) {
            ErrorResponse.LogError(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(httpHeaders).body(null);
        }
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).headers(httpHeaders).body(jobCategoryUpdated);
    }
}
