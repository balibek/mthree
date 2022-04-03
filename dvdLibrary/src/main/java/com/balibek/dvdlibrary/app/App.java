package com.balibek.dvdlibrary.app;

import com.balibek.dvdlibrary.controller.DvdLibraryController;
import com.balibek.dvdlibrary.dao.DvdLibraryDao;
import com.balibek.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import com.balibek.dvdlibrary.ui.DvdLibraryView;
import com.balibek.dvdlibrary.ui.UserIo;
import com.balibek.dvdlibrary.ui.UserIoConsoleImpl;

public class App {

    public static void main(String[] args) {
        
        UserIo myIo = new UserIoConsoleImpl();
        DvdLibraryView myView = new DvdLibraryView(myIo);
        DvdLibraryDao myDao = new DvdLibraryDaoFileImpl();
        DvdLibraryController controller = new DvdLibraryController(myView, myDao);
        controller.run();
    }

}
