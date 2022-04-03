package com.balibek.dvdlibrary.controller;

import com.balibek.dvdlibrary.dao.DvdLibraryDao;
import com.balibek.dvdlibrary.dao.DvdLibraryDaoException;
import com.balibek.dvdlibrary.dao.DvdLibraryDaoFileImpl;
import com.balibek.dvdlibrary.dto.Dvd;
import com.balibek.dvdlibrary.ui.DvdLibraryView;
import com.balibek.dvdlibrary.ui.UserIo;
import com.balibek.dvdlibrary.ui.UserIoConsoleImpl;
import java.util.List;

public class DvdLibraryController {

    private DvdLibraryView view; //= new DvdLibraryView();
    private UserIo io = new UserIoConsoleImpl();
    private DvdLibraryDao dao; //= new DvdLibraryDaoFileImpl();

    public DvdLibraryController(DvdLibraryView view, DvdLibraryDao dao) {
        this.view = view;
        this.dao = dao;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        

        try {
            dao.loadDvd();
            while (keepGoing) {

                menuSelection = getMenuSelection(); 

                switch (menuSelection) {
                    case 1:
                        createDvd();
                        break;
                    case 2:
                        removeDvd();
                        break;
                    case 3:
                        editDvd();
                        break;
                    case 4:
                        listDvds();
                        break;
                    case 5:
                        viewDvd();
                        break;
                    case 6:
                        searchDvd();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
            dao.wtiteDvd();

        } catch (DvdLibraryDaoException e) {
            view.displayErrorBanner(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createDvd() throws DvdLibraryDaoException {

        boolean choice = true;

        do {
            view.displayCreateDvdBanner();
            Dvd newDvd = view.getNewDvdInfo();
            dao.addDvd(newDvd.getId(), newDvd);
            view.displayCreateSuccessBanner();
            String ch = view.getProccedChoice();

            if (ch.equals("Yes")) {
                createDvd();

            } else {
                choice = false;
            }
        } while (choice = false);
    }

    private void removeDvd() throws DvdLibraryDaoException {

        boolean choice = true;

        do {
            view.displayRemoveDvdBanner();
            String id = view.getDvdIdChoice();
            Dvd removedDvd = dao.removeDvd(id);
            view.displayRemoveResult(removedDvd);
            String ch = view.getProccedChoice();

            if (ch.equals("Yes")) {
                removeDvd();
            } else {
                choice = false;
            }
        } while (choice = false);
    }

    private void editDvd() throws DvdLibraryDaoException {
        boolean choice = true;

        do {
            view.displayEditDvdBanner();
            String id = view.getDvdIdChoice();
            Dvd eDvd = view.getEditDvdInfo();
            dao.editDvd(id, eDvd);
            String ch = view.getProccedChoice();
            if (ch.equals("Yes")) {
                editDvd();
            } else {
                choice = false;
            }
        } while (choice = false);

    }

    private void listDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> DvdList = dao.getAllDvds();
        view.displayDvdList(DvdList);
    }

    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String id = view.getDvdIdChoice();
        Dvd item = dao.getDvd(id);
        view.displayDvd(item);
    }

    private void searchDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String title = view.getDvdTitleChoice();
        List<Dvd> DvdList = dao.searchDvd(title);
        view.displaySearchResult(DvdList);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
