
package com.balibek.classrosster;

import com.balibek.classroster.controller.ClassRosterController;
import com.balibek.classroster.dao.*;
import com.balibek.classroster.service.ClassRosterServiceLayer;
import com.balibek.classroster.service.ClassRosterServiceLayerImpl;
import com.balibek.classroster.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author bali_bek
 */
public class App {

    public static void main(String[] args) {
        
//        UserIO myIO = new UserIOConsoleImpl();
//        ClassRosterView myView = new ClassRosterView(myIO);
//        ClassRosterDao myDAo = new ClassRosterDaoFileImpl();
//        ClassRosterAuditDao myAudit = new ClassRosterAuditDaoFileImpl();
//        ClassRosterServiceLayer myServ = new ClassRosterServiceLayerImpl(myDAo, myAudit);
//        ClassRosterController controller = new ClassRosterController(myView, myServ);
//        controller.run();

        ApplicationContext CTX = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        ClassRosterController controller = CTX.getBean("controller", ClassRosterController.class);
        controller.run();
    }

}
