/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balibek.classroster.service;

import com.balibek.classroster.dao.ClassRosterAuditDao;
import com.balibek.classroster.dao.ClassRosterDao;
import com.balibek.classroster.dao.ClassRosterPresistenceException;
import com.balibek.classroster.dto.Student;
import java.util.List;

/**
 *
 * @author bali_bek
 */
public class ClassRosterServiceLayerImpl implements ClassRosterServiceLayer {

    ClassRosterDao dao;
    ClassRosterAuditDao AudiDao;

    public ClassRosterServiceLayerImpl(ClassRosterDao dao, ClassRosterAuditDao AudiDao) {
        this.dao = dao;
        this.AudiDao = AudiDao;
    }

    private void validateStudentData(Student student) throws
            ClassRosterDataValidationException {

        if (student.getFirstName() == null
                || student.getFirstName().trim().length() == 0
                || student.getLastName() == null
                || student.getLastName().trim().length() == 0
                || student.getCohort() == null
                || student.getCohort().trim().length() == 0) {

            throw new ClassRosterDataValidationException(
                    "ERROR: All fields [First Name, Last Name, Cohort] are required.");
        }
    }

    @Override
    public void createStudent(Student student) throws
            ClassRosterDuplicateIdException,
            ClassRosterDataValidationException,
            ClassRosterPresistenceException {
        if (dao.getStudent(student.getStudentId()) != null) {
            throw new ClassRosterDuplicateIdException("Error: Could not create stusent. Student Id " + student.getStudentId() + " already exists");
        }
        validateStudentData(student);
        dao.addStudent(student.getStudentId(), student);
        AudiDao.writeAuditEntry("Student " + student.getFirstName() + " CREATED.");
    }

    @Override
    public List<Student> getAllStudent() throws ClassRosterPresistenceException {
        return dao.getAllStudent();
    }

    @Override
    public Student getStudent(String studentID) throws ClassRosterPresistenceException {
        return dao.getStudent(studentID);
    }

    @Override
    public Student removeStudent(String studentID) throws ClassRosterPresistenceException {
        Student removeStudent = dao.removeStudent(studentID);

        if (removeStudent != null) {
            AudiDao.writeAuditEntry("Student " + removeStudent.getFirstName() + " Removed.");
        }
        return removeStudent;
    }

}
