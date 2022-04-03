package com.balibek.classroster.dto;

import java.util.Objects;

/**
 *
 * @author bali_bek
 */
public class Student {

    private String firstName;
    private String lastName;
    private String studentId;
    private String cohort;

    public Student(String studentID) {
        this.studentId = studentID;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentID) {
        this.studentId = studentID;
    }

    public String getCohort() {
        return cohort;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCohort(String cohort) {
        this.cohort = cohort;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.firstName);
        hash = 23 * hash + Objects.hashCode(this.lastName);
        hash = 23 * hash + Objects.hashCode(this.studentId);
        hash = 23 * hash + Objects.hashCode(this.cohort);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.studentId, other.studentId)) {
            return false;
        }
        return Objects.equals(this.cohort, other.cohort);
    }

    

    

    @Override
    public String toString() {
        return "Student{" + "firstName=" + firstName + ", lastName=" + lastName + ", studentID=" + studentId + ", cohort=" + cohort + '}';
    }
    
}
