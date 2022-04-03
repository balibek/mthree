  package com.balibek.dvdlibrary.dao;

import com.balibek.dvdlibrary.dto.Dvd;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DvdLibraryDaoFileImpl implements DvdLibraryDao {

    private Map<String, Dvd> items = new HashMap<>();
    private final String DVD_FILE; /// = "dvd.txt";
    public static final String DELIMITER = "::";

    public DvdLibraryDaoFileImpl() {
        this.DVD_FILE = "dvd.txt";
    }

    public DvdLibraryDaoFileImpl(String DVD_FILE) {
        this.DVD_FILE = DVD_FILE;
    }

    @Override
    public Dvd addDvd(String id, Dvd item) throws DvdLibraryDaoException {
        Dvd prevDvd = items.put(id, item);
        return prevDvd;
    }

    @Override
    public Dvd removeDvd(String id) throws DvdLibraryDaoException {
        Dvd removeDvd = items.remove(id);
        return removeDvd;
    }

    @Override
    public List<Dvd> getAllDvds() throws DvdLibraryDaoException {
        return new ArrayList<Dvd>(items.values());
    }

    @Override
    public Dvd getDvd(String id) throws DvdLibraryDaoException {
       
        return items.get(id);
    }

    @Override
    public Dvd editDvd(String id, Dvd item) throws DvdLibraryDaoException {
        Dvd editDvd = items.replace(id, item);
        return editDvd;
    }

    @Override
    public List<Dvd> searchDvd(String title) {

        List<Dvd> results = new ArrayList<>();
        items.forEach((key, value) -> {
            if (value.getTitle().equals(title)) {
                results.add(value);
            }

        });
        return results;

    }

    private Dvd unmarshallDvd(String DvdAsText) {

        String[] DvdTokens = DvdAsText.split(DELIMITER);

        String id = DvdTokens[0];

        Dvd DvdFromFile = new Dvd(id);

        DvdFromFile.setTitle(DvdTokens[1]);

        DvdFromFile.setReleaseDate(DvdTokens[2]);

        DvdFromFile.setMpaaRating(DvdTokens[3]);

        DvdFromFile.setDirectorsName(DvdTokens[4]);

        DvdFromFile.setStudio(DvdTokens[5]);

        DvdFromFile.setUserRatingNote(DvdTokens[6]);

        return DvdFromFile;
    }

    @Override
    public void loadDvd() throws DvdLibraryDaoException {

        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)));
        } catch (FileNotFoundException e) {
            throw new DvdLibraryDaoException(
                    "-__- Could not load dvd data into memory.", e);
        }

        String currentLine;

        Dvd currentDvd;

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentDvd = unmarshallDvd(currentLine);
            items.put(currentDvd.getId(), currentDvd);

        }
        scanner.close();
    }

    private String marchallDvd(Dvd aDvd) {
        String DvdAsText = aDvd.getId() + DELIMITER;
        DvdAsText += aDvd.getTitle() + DELIMITER;
        DvdAsText += aDvd.getReleaseDate() + DELIMITER;
        DvdAsText += aDvd.getStudio() + DELIMITER;
        DvdAsText += aDvd.getMpaaRating() + DELIMITER;
        DvdAsText += aDvd.getDirectorsName() + DELIMITER;
        DvdAsText += aDvd.getUserRatingNote() + DELIMITER;
        return DvdAsText;
    }


    @Override
    public void wtiteDvd() throws DvdLibraryDaoException {
        PrintWriter out; //= null;

        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException(
                    "Could not save DVD data.", e);
        }

        String DvdAsText;
        List<Dvd> DvdList = this.getAllDvds();

        for (Dvd currentDvd : DvdList) {
            DvdAsText = marchallDvd(currentDvd);

            out.println(DvdAsText);

            out.flush();
        }
        out.close();
    }

}
