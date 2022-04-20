
package com.ebd.flooringmaster;

import com.ebd.flooringmaster.controller.FlooringMasterController;
import com.ebd.flooringmaster.dao.FlooringMasteryPersistenceException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Flooringmaster {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // checks all classes inside this package and its child packages
        applicationContext.scan("com.ebd.flooringmaster");
        // checks for any annotations in those classes
        // - anything with @Component is added to memory
        // - anything with @Autowired has dependencies injected
        applicationContext.refresh();

        // default id is class names but camelcase
        FlooringMasterController controller = applicationContext.getBean("flooringMasterController", FlooringMasterController.class);
        controller.run();
    }
}
