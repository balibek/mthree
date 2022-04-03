package com.balibek.classroster.controller;

import com.balibek.classroster.dao.ClassRosterPresistenceException;
import com.balibek.classroster.dao.ClassRosterDao;
import com.balibek.classroster.dao.ClassRosterDaoFileImpl;
import com.balibek.classroster.dto.Student;
import com.balibek.classroster.service.ClassRosterDataValidationException;
import com.balibek.classroster.service.ClassRosterServiceLayer;
import com.balibek.classroster.service.ClassRosterDuplicateIdException;
//import com.balibek.classroster.ui.UserIO;
//import com.balibek.classroster.ui.UserIOConsoleImpl;
import com.balibek.classroster.ui.ClassRosterView;
import java.util.List;

public class ClassRosterController {

//    private UserIO io = new UserIOConsoleImpl();
    private ClassRosterView view;
//    private ClassRosterDao dao;
    private ClassRosterServiceLayer service;

    public ClassRosterController(ClassRosterView view, ClassRosterServiceLayer service) {
        this.view = view;
//        this.dao = dao;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                menuSelection = view.printMenuAndGetSelection();

                switch (menuSelection) {
                    case 1:
                        displayStudts();
                        break;
                    case 2:
                        createStudent();
                        break;
                    case 3:
                        viewStudent();
                        break;
                    case 4:
                        removeStudent();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (ClassRosterPresistenceException e) {
            view.displayErrorBanner(e.getMessage());
        }
    }

    private int getMenuSelection() {

        return view.printMenuAndGetSelection();
    }
    

    private void createStudent() throws ClassRosterPresistenceException {
        view.displayCreateStudentBanner();
        boolean hasErrors = false;

        do {

            Student newStudent = view.getNewStudentInfo();
//        dao.addStudent(newStudent.getStudentID(), newStudent);
            try {
                service.createStudent(newStudent);
                view.displayCreateSuccessBanner();
                hasErrors = false;
            } catch (ClassRosterDuplicateIdException | ClassRosterDataValidationException e) {
                hasErrors = true;
                view.displayErrorBanner(e.getMessage());

            }
        } while (hasErrors);
    }

    private void displayStudts() throws ClassRosterPresistenceException {
        view.displayAllStudentBanner();
        List<Student> sList = service.getAllStudent(); //dao.getAllStudent();
        view.displayStudentList(sList);
    }

    private void viewStudent() throws ClassRosterPresistenceException {
        view.displayDisplayStudentBanner();
        String sID = view.getStudentIDChoice();
        Student stud = service.getStudent(sID); //dao.getStudent(sID);
        view.displayStudent(stud);
    }

    private void removeStudent() throws ClassRosterPresistenceException {
        view.displayRemoveStudentBanner();
        String sid = view.getStudentIDChoice();
        Student removStud = service.removeStudent(sid);//dao.removeStudent(sid);
        view.displayRemoveResult(removStud);
    }

    private void unknownCommand() {
        view.displayUnknownBanner();
    }

    private void exitMessage() {
        view.displayGoodByeBanner();
    }

}
