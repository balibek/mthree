 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.balibek.classroster.service;

import com.balibek.classroster.dao.ClassRosterPresistenceException;
import com.balibek.classroster.dto.Student;
import java.util.List;

/**
 *
 * @author bali_bek
 */
public interface ClassRosterServiceLayer {
    
    void createStudent(Student student) throws 
            ClassRosterDuplicateIdException,
            ClassRosterDataValidationException,
            ClassRosterPresistenceException;
    
    List<Student> getAllStudent() throws
            ClassRosterPresistenceException;
    
    Student getStudent(String studentID) throws 
            ClassRosterPresistenceException;
    
    Student removeStudent(String studentID) throws 
            ClassRosterPresistenceException;
     
}
