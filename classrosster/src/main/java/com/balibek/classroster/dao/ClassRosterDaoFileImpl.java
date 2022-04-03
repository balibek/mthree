package com.balibek.classroster.dao;

import com.balibek.classroster.dto.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bali_bek
 */
public class ClassRosterDaoFileImpl implements ClassRosterDao {

    private Map<String, Student> students = new HashMap<>();
    private final String ROSTER_FILE;
    public static final String DELIMITER = "::";
    
    public ClassRosterDaoFileImpl() {
        ROSTER_FILE = "roster.txt";
    }

    public ClassRosterDaoFileImpl(String rosterTextFile) {
        ROSTER_FILE = rosterTextFile;
    }

    @Override
    public Student addStudent(String studentId, Student student) throws ClassRosterPresistenceException {
        loadRoster();
        Student prevStudent = students.put(studentId, student);
        this.writeRoster();
        return prevStudent;
    }

    @Override
    public List<Student> getAllStudent() throws ClassRosterPresistenceException{
        loadRoster();
        return new ArrayList<Student>(students.values());
    }

    @Override
    public Student getStudent(String studentId) throws ClassRosterPresistenceException{
        return students.get(studentId);
    }

//    @Override
//    public int printMenuAndGetSelection() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    @Override
    public Student removeStudent(String studentId) throws ClassRosterPresistenceException {
        loadRoster();
        Student removeStudent = students.remove(studentId);
        writeRoster();
        return removeStudent;
    }

    private Student unmarshallStudent(String studentAsText) {

        String[] studentTokens = studentAsText.split(DELIMITER);

        String studentID = studentTokens[0];

        Student studentFromFile = new Student(studentID);

        studentFromFile.setFirstName(studentTokens[1]);

        studentFromFile.setLastName(studentTokens[2]);

        studentFromFile.setCohort(studentTokens[3]);

        return studentFromFile;
    }

    private void loadRoster() throws ClassRosterPresistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)));
        } catch (FileNotFoundException e) {
            throw new ClassRosterPresistenceException(
                    "-__- Could not load roster data into memory.", e);
        }

        String currentLine;

        Student currentStudent;

        while (scanner.hasNextLine()) {
            
            currentLine = scanner.nextLine();
            currentStudent = unmarshallStudent(currentLine);
            students.put(currentStudent.getStudentId(), currentStudent);

        }
        scanner.close();
    }
    
    private String marchallStudent(Student astudent) {
        String studAsText = astudent.getStudentId() + DELIMITER;
        studAsText += astudent.getFirstName() + DELIMITER;
        studAsText += astudent.getLastName() + DELIMITER;
        studAsText += astudent.getCohort() + DELIMITER;
        return studAsText;
    }
    
    private void writeRoster() throws ClassRosterPresistenceException {
        
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            try {
                throw new ClassRosterPresistenceException(
                        "Could not save student data.", e);
            } catch (ClassRosterPresistenceException ex) {
                Logger.getLogger(ClassRosterDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String studentAsText;
        List<Student> studentList = this.getAllStudent();
        
        for(Student cStudent : studentList) {
            studentAsText = marchallStudent(cStudent);
            
            out.println(studentAsText);
            
            out.flush();
        }
        out.close();
    }

}
