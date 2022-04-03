package com.balibek.vendingmachine;

import com.balibek.vendingmachine.controller.VendingMachineController;
import com.balibek.vendingmachine.dao.VendingMachineAuditDao;
import com.balibek.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.balibek.vendingmachine.dao.VendingMachineDao;
import com.balibek.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.balibek.vendingmachine.service.VendingMachineServiceLayer;
import com.balibek.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.balibek.vendingmachine.ui.UserIo;
import com.balibek.vendingmachine.ui.UserIoConsoleImpl;
import com.balibek.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {

//        UserIo myIo = new UserIoConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoImpl();
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
//        VendingMachineController controller = new VendingMachineController(myView, myService);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }

}
