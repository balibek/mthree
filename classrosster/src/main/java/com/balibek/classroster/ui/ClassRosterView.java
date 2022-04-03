
package com.balibek.classroster.ui;

import com.balibek.classroster.dto.Student;
import java.util.List;

/**
 *
 * @author bali_bek
 */
public class ClassRosterView {

    private UserIO io;

    public ClassRosterView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {

        io.print("Main Menu");
        io.print("1. List Student IDs");
        io.print("2. Create new Student");
        io.print("3. View a Student");
        io.print("4. Remove  Student");
        io.print("5. Exit");

        return io.readInt("Choose from Above", 1, 5);
    }

    public Student getNewStudentInfo() {
        String studentID = io.readString("Please enter Student ID");
        String firstName = io.readString("Please enter First Name");
        String lastName = io.readString("Please enter Last Name");
        String cohort = io.readString("Please enter Cohort");

        Student currentStudent = new Student(studentID);

        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        currentStudent.setCohort(cohort);

        return currentStudent;

    }

    public void displayCreateStudentBanner() {
        io.print("=====|| CREATE STUDENT ||=====");
    }

    public void displayCreateSuccessBanner() {
        io.print("Student successfully created.  Please hit enter to continue");
    }
    
    public void displayStudentList(List<Student> studentList) {
            for(Student cStudent: studentList) {
        String studentInfo =
                String.format("#%s : %s %s", 
                        cStudent.getStudentId(),
                        cStudent.getFirstName(),
                        cStudent.getLastName());
        io.print(studentInfo);
    }
            io.readString("Please hit enter to continue.");
    }
    
    public void displayAllStudentBanner() {
        io.print("=====|| DISPLAY ALL STUDENT||=====");
    }
    
    public void displayDisplayStudentBanner() {
        io.print("=====|| DISPLAY STUDENT||=====");
    }
    
    public String getStudentIDChoice() {
        return io.readString("Please enter the StudentID.");
    }
    
    public void displayStudent(Student student) {
        if(student != null) {
            io.print(student.getStudentId());
            io.print(student.getFirstName() + " " + student.getLastName());
            io.print(student.getCohort());
            io.print(" ");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to ontinue");
    }
    
    public void displayRemoveStudentBanner(){
        io.print("=====|| REMOVE STUDENT||=====");
    }
    
    public void displayRemoveResult(Student studentRecord) {
        if(studentRecord != null) {
            io.print("Student successfully removed.");
        } else {
            io.print("No such student.");
        }
        io.readString("Please hit enter to continue");
    }
    
    public void displayGoodByeBanner() {
        io.print("=====|| GOOD BYE ||=====");
    }
    
    public void displayUnknownBanner() {
        io.print("=====|| UNKNOWN COMMAND||=====");
    }
    
    public void displayErrorBanner(String Msg) {
        io.print("=====|| ERROR||=====");
        io.print(Msg);
    }
}
