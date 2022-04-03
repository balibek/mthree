package com.balibek.classroster.dao;

import com.balibek.classroster.dto.Student;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClassRosterDaoFileImplTest {

    ClassRosterDao testDao;

    public ClassRosterDaoFileImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        String testFile = "testRoster.txt";
        new FileWriter(testFile);
        testDao = new ClassRosterDaoFileImpl(testFile);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetStudent() throws Exception {
        //Arrange
        String studentID = "0001";
        Student student = new Student(studentID);
        student.setFirstName("Ada");
        student.setLastName("Lovelace");
        student.setCohort("Java-May-1845");

        //act
        testDao.addStudent(student.getStudentId(), student);//student.getStudentId(), student);

        Student retrivedStudent = testDao.getStudent(studentID);

        //check the data (assert)
        assertEquals("CheckThat ID",
                student.getStudentId(), retrivedStudent.getStudentId());

        assertEquals("CheckThat First Name",
                student.getFirstName(), retrivedStudent.getFirstName());

        assertEquals("CheckThat Last Name",
                student.getLastName(), retrivedStudent.getLastName());

        assertEquals("CheckThat Cohort",
                student.getCohort(), retrivedStudent.getCohort());

//        assertEquals("test", 1, 1);
    }
    
    @Test
    public void testAddGetAllStudent() throws Exception {
        //Arrange
        String studentID = "0001";
        Student student = new Student(studentID);
        student.setFirstName("Ada");
        student.setLastName("Lovelace");
        student.setCohort("Java-May-1845");
        
        String studentID2 = "0002";
        Student student2 = new Student(studentID2);
        student2.setFirstName("Charlses");
        student2.setLastName("Babbage");
        student2.setCohort(".Net-May-1845");
        
        //ACT: Add both to DAO
        testDao.addStudent(student.getStudentId(), student);
        testDao.addStudent(student2.getStudentId(), student2);
        
        List<Student> allStudents = testDao.getAllStudent();
        
        //Assert
        assertNotNull("List no Null", allStudents);
        assertEquals("We added two students", 2, allStudents.size());
        
        assertTrue("Ada should be in allstudent", allStudents.contains(student));
        assertTrue("Charles should be in allstudent", allStudents.contains(student2));
        
        
    }

//    @Test
//    public void testAddGetAllStudent() throws Exception {
//
//        //Arrange
//        String studentID = "0001";
//        Student student = new Student(studentID);
//        student.setFirstName("Ada");
//        student.setLastName("Lovelace");
//        student.setCohort("Java-May-1845");
//
//        String studentID2 = "0002";
//        Student student2 = new Student(studentID2);
//        //Student secondStudent = new Student("0002");
//        student2.setFirstName("Charles");
//        student2.setLastName("Babbage");
//        student2.setCohort(".Net-May-1845");
//
//        //Act: Add both
//        testDao.addStudent(student.getStudentId(), student);
//        testDao.addStudent(student2.getStudentId(), student2);
//
//        List<Student> allStudents = testDao.getAllStudent();
//
//        //Assert
//        assertNotNull("List no Null", allStudents);
//        assertEquals("We added two Students", 2, allStudents.size());
//
//        assertTrue("Ada should be in allStudents", testDao.getAllStudent().contains(student));
//        assertTrue("Charles should be in allStudents", allStudents.contains(student2));
//
//    }

    @Test
    public void testAddRemoveStudent() throws Exception {

        //Arrange
        String studentID = "0001";
        Student student = new Student(studentID);
        student.setFirstName("Ada");
        student.setLastName("Lovelace");
        student.setCohort("Java-May-1845");

        String studentID2 = "0002";
        Student student2 = new Student(studentID2);
        student2.setFirstName("Charles");
        student2.setLastName("Babbage");
        student2.setCohort(".Net-May-1845");

        //Act: Add both
        testDao.addStudent(student.getStudentId(), student);
        testDao.addStudent(student2.getStudentId(), student2);

        //remove
        Student RemovedStudent = testDao.removeStudent(student.getStudentId());

        //Assert
        assertEquals("86'd Ada", RemovedStudent, student);

        List<Student> allStudent = testDao.getAllStudent();

        assertNotNull("Still one student", allStudent);
        assertEquals("One = 1", 1, allStudent.size());

        assertFalse("Ada's gone", allStudent.contains(student));
        assertTrue("Charles is here", allStudent.contains(student2));

        //Act II
        RemovedStudent = testDao.removeStudent(student2.getStudentId());

        //Assert again
        assertEquals("86'd Charles", RemovedStudent, student2);

        allStudent = testDao.getAllStudent();

        assertTrue(allStudent.isEmpty());

        Student getStudent = testDao.getStudent("0001");
        assertNull(getStudent);
        getStudent = testDao.getStudent("0002");
        assertNull(getStudent);

    }

}
