package com.balibek.dvdlibrary.ui;

import com.balibek.dvdlibrary.dto.Dvd;
import java.util.List;

public class DvdLibraryView {

    private UserIo io; //= new UserIoConsoleImpl();

    public DvdLibraryView(UserIo io) {
        this.io = io;
    }
    

    public int printMenuAndGetSelection() {
        io.print("Main Menu ");
        io.print("1. Add a DVD");
        io.print("2. Remove a DVD");
        io.print("3. Edit DVD's information");
        io.print("4. List the DVDs in the collection");
        io.print("5. Display informatiom for a particular DVD");
        io.print("6. Search for a DVD by title");
        io.print("7. Exit");

        return io.readInt("Please select from the above choices.", 1, 7);
    }

    public Dvd getNewDvdInfo() {
        String id = io.readString("Please enter DVD number");
        String title = io.readString("Please enter Title");
        String releaseDate = io.readString("Please enter Release Date");
        String studio = io.readString("Please enter Studio");
        String mpaaRating = io.readString("Please enter MPAA rating");
        String directorsName = io.readString("Please enter Director's name");
        String userRatingNote = io.readString("Please enter User rating or note");

        Dvd currentDvd = new Dvd(id);
        currentDvd.setTitle(title);
        currentDvd.setReleaseDate(releaseDate);
        currentDvd.setStudio(studio);
        currentDvd.setMpaaRating(mpaaRating);
        currentDvd.setDirectorsName(directorsName);
        currentDvd.setUserRatingNote(userRatingNote);

        return currentDvd;
    }

    public void displayCreateDvdBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString(
                "DVD successfully created.  Please hit enter to continue");
    }

    public void displayRemoveDvdBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveResult(Dvd DvdRecord) {
        if (DvdRecord != null) {
            io.print("DVD successfully removed.");
        } else {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDvdList(List<Dvd> items) {
        for (Dvd item : items) {
            String DVDInfo
                    = String.format("#%s : %s, %s, %s, %s, %s, %s",
                            item.getId(),
                            item.getTitle(),
                            item.getReleaseDate(),
                            item.getDirectorsName(),
                            item.getStudio(),
                            item.getMpaaRating(),
                            item.getUserRatingNote());
            io.print(DVDInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displaySearchResult(List<Dvd> dvds) {
        if (dvds != null) {
            io.print("Found " + dvds.size() + " results");
            displayDvdList(dvds);
        } else {
            io.print("No such DVD.");
        }
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
    }

    public void displayDisplayDvdBanner() {
        io.print("=== Display DVD ===");
    }

    public String getDvdIdChoice() {
        return io.readString("Please enter the DVD number.");
    }

    public String getDvdTitleChoice() {
        return io.readString("Please enter DVD title for search.");
    }

    public void displayDvd(Dvd item) {
        if (item != null) {
            io.print(item.getId());
            io.print(item.getTitle());
            io.print(item.getReleaseDate());
            io.print(item.getMpaaRating());
            io.print(item.getStudio());
            io.print(item.getDirectorsName());
            io.print(item.getUserRatingNote());
            io.print("");
        } else {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayEditDvdBanner() {
        io.print("=== Edit DVD ===");
    }

    public Dvd getEditDvdInfo() {
        String id = io.readString("Please enter new DVD number");
        String title = io.readString("Please enter new Title");
        String releaseDate = io.readString("Please enter new Release Date");
        String studio = io.readString("Please enter new Studio");
        String mpaaRating = io.readString("Please enter new MPAA rating");
        String directorsName = io.readString("Please enter new Director's name");
        String userRatingNote = io.readString("Please enter new User rating or note");

        Dvd currentDvd = new Dvd(id);
        currentDvd.setTitle(title);
        currentDvd.setReleaseDate(releaseDate);
        currentDvd.setStudio(studio);
        currentDvd.setMpaaRating(mpaaRating);
        currentDvd.setDirectorsName(directorsName);
        currentDvd.setUserRatingNote(userRatingNote);

        return currentDvd;
    }

    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayErrorBanner(String Msg) {
        io.print("=====|| ERROR||=====");
        io.print(Msg);
    }
    
    public String getProccedChoice() {
        return io.readString("Do you want to proceed? Please enter Yes or No");
    }

}
