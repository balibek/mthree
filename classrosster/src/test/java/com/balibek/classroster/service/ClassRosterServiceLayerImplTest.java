/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.balibek.classroster.service;

import com.balibek.classroster.controller.ClassRosterController;
import com.balibek.classroster.dao.*;
import com.balibek.classroster.dao.ClassRosterDao;
import com.balibek.classroster.dto.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bali_bek
 */
public class ClassRosterServiceLayerImplTest {

    private ClassRosterServiceLayer testServ;

    public ClassRosterServiceLayerImplTest() {
        ClassRosterDao dao = new ClassRosterDaoStubImpl();
        ClassRosterAuditDao aDao = new ClassRosterAuditDaoStubImpl();

        testServ = new ClassRosterServiceLayerImpl(dao, aDao);
//        ApplicationContext CTX = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        testServ = CTX.getBean("serviceLayer", ClassRosterServiceLayer.class);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValidStudent() {
        Student Caz = new Student("0002");
        Caz.setFirstName("Charles");
        Caz.setLastName("Roy");
        Caz.setCohort("Java 218");

        try {
            testServ.createStudent(Caz);
        } catch (ClassRosterDuplicateIdException
                | ClassRosterDataValidationException
                | ClassRosterPresistenceException ex) {
            fail("This was the Valid TEST!!!");
        }

        //If you have faild by this point... you pass by proxy
    }

    @Test
    public void testDupStudent() {
        Student Caz = new Student("42");
        Caz.setFirstName("Charles");
        Caz.setLastName("Roy");
        Caz.setCohort("Java 218");

        try {
            testServ.createStudent(Caz);
            fail("This was the Dupe TEST!!!");
        } catch (ClassRosterDuplicateIdException ex) {
            return;
        } catch (ClassRosterDataValidationException | ClassRosterPresistenceException ex) {
            fail("This was wrong Exception. Do Better.");
        }
    }

    @Test
    public void testCreateStudentInvalidData() {
        Student Caz = new Student("421");
        Caz.setFirstName("Charles");
        Caz.setLastName("Roy");
        Caz.setCohort("");

        try {
            testServ.createStudent(Caz);
            fail("This was wrong Exception. Do Better.");
        } catch (ClassRosterPresistenceException | ClassRosterDuplicateIdException ex) {
            fail("This was wrong Exception. Do Better.");
        } catch (ClassRosterDataValidationException ex) {
            return;
        }
    }

    @Test
    public void testGetAllStudent() throws Exception {
        Student onlyClone = new Student("42");
        onlyClone.setFirstName("Jack");
        onlyClone.setLastName("Rob");
        onlyClone.setCohort("Bball");

        assertEquals("One lonly student is 1", 1, testServ.getAllStudent().size());

        assertTrue(testServ.getAllStudent().contains(onlyClone));
    }

    @Test
    public void testGetStudent() throws Exception {
        Student onlyClone = new Student("42");
        onlyClone.setFirstName("Jack");
        onlyClone.setLastName("Rob");
        onlyClone.setCohort("Bball");

        Student jackie = testServ.getStudent("42");
        assertNotNull(jackie);
        assertEquals(onlyClone, jackie);

        Student NotInDao = testServ.getStudent("2400");
        assertNull(NotInDao);
    }

    @Test
    public void testRemoveStudent() throws Exception {
        Student onlyClone = new Student("42");
        onlyClone.setFirstName("Jack");
        onlyClone.setLastName("Rob");
        onlyClone.setCohort("Bball");

        Student jackie = testServ.removeStudent("42");
        assertNotNull(jackie);
        assertEquals(onlyClone, jackie);

        Student NotInDao = testServ.removeStudent("2400");
        assertNull(NotInDao);
    }

}
