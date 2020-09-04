package com.ausy_technologies.finalproject.Model.DAO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ApiModel(description = "This model is to create a employee.")
@Entity
@Table(name = "employees")
public class Employee {

    @ApiModelProperty(notes = "Auto generated unique id", required = true)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ApiModelProperty(notes = "Employee's first name", required = true)
    private String firstName;
    @ApiModelProperty(notes = "Employee's last name", required = true)
    private String lastName;

    @ApiModelProperty(notes = "Job category id which is a foreign key", required = true)
    @ManyToOne
    @JoinColumn(name = "jobCategoryId")
    private JobCategories jobCategory;

    @ApiModelProperty(notes = "Department id which is a foreign key", required = true)
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Departments department;


    @ApiModelProperty(notes = "A boolean which says if an employee is manager or not")
    private boolean isManager;
    @ApiModelProperty(notes = "The date when the employee started his duties", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @ApiModelProperty(notes = "The date when the employee ended his duties")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    @ApiModelProperty(notes = "A boolean which says if an employee is active or not")
    private boolean active;
    @ApiModelProperty(notes = "Employee's address", required = true)
    private String address;
    @ApiModelProperty(notes = "Employee's postal code", required = true)
    private String CP;
    @ApiModelProperty(notes = "Employee's telephone number", required = true)
    private String telephone;
    @ApiModelProperty(notes = "Employee's email address", required = true)
    private String email;
    @ApiModelProperty(notes = "Employee's birthday date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    @ApiModelProperty(notes = "Number of children the employee has")
    private int noChildren;
    @ApiModelProperty(notes = "Employee's salary", required = true)
    private Double salary;
    @ApiModelProperty(notes = "Employee's studies", required = true)
    private String studies;
    @ApiModelProperty(notes = "Employee's social security number (SSN)")
    private String socialSecurityNumber;
    @ApiModelProperty(notes = "A boolean which says if an employee has a driving license or not")
    private boolean hasDrivingLicense;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public JobCategories getJobCategories() {
        return jobCategory;
    }

    public void setJobCategoryId(JobCategories jobCategoryId) {
        this.jobCategory = jobCategoryId;
    }

    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobCategory=" + jobCategory +
                ", department=" + department +
                ", isManager=" + isManager +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", active=" + active +
                ", address='" + address + '\'' +
                ", CP='" + CP + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", noChildren=" + noChildren +
                ", salary=" + salary +
                ", studies='" + studies + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", hasDrivingLicense=" + hasDrivingLicense +
                '}';
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getNoChildren() {
        return noChildren;
    }

    public void setNoChildren(int noChildren) {
        this.noChildren = noChildren;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getStudies() {
        return studies;
    }

    public void setStudies(String studies) {
        this.studies = studies;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public boolean isHasDrivingLicense() {
        return hasDrivingLicense;
    }

    public void setHasDrivingLicense(boolean hasDrivingLicense) {
        this.hasDrivingLicense = hasDrivingLicense;
    }
}
