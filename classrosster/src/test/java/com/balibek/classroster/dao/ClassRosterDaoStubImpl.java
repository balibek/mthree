/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balibek.classroster.dao;

import com.balibek.classroster.dto.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bali_bek
 */
public class ClassRosterDaoStubImpl implements ClassRosterDao {
    
    public Student onlyStudent;

    public ClassRosterDaoStubImpl() {
        onlyStudent = new Student("42");
        onlyStudent.setFirstName("Jack");
        onlyStudent.setLastName("Rob");
        onlyStudent.setCohort("Bball");
    }

    public ClassRosterDaoStubImpl(Student onlyStudent) {
        this.onlyStudent = onlyStudent;
    }
    

    @Override
    public Student addStudent(String studentID, Student student) throws ClassRosterPresistenceException {
        if(studentID.equals(onlyStudent.getStudentId())) {
            return onlyStudent;
        } else {
            return null;
        }
    }

    @Override
    public List<Student> getAllStudent() throws ClassRosterPresistenceException {
        List<Student> studentList = new ArrayList<>();
        studentList.add(onlyStudent);
        return studentList;
    }

    @Override
    public Student getStudent(String studentID) throws ClassRosterPresistenceException {
        if(studentID.equals(onlyStudent.getStudentId()))
            return  onlyStudent;
        return null;
    }

    @Override
    public Student removeStudent(String studentID) throws ClassRosterPresistenceException {
        if(studentID.equals(onlyStudent.getStudentId()))
            return  onlyStudent;
        return null;
    }
    
}
