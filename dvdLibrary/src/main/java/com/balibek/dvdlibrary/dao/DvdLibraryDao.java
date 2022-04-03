
package com.balibek.dvdlibrary.dao;

import com.balibek.dvdlibrary.dto.Dvd;
import java.util.List;

public interface DvdLibraryDao {
    
    Dvd addDvd(String id, Dvd item) throws DvdLibraryDaoException;
    
    Dvd removeDvd(String id) throws DvdLibraryDaoException;
    
    Dvd editDvd(String id, Dvd item) throws DvdLibraryDaoException;
    
    List<Dvd> getAllDvds() throws DvdLibraryDaoException;
    
    Dvd getDvd(String id) throws DvdLibraryDaoException;
    
    List<Dvd> searchDvd(String title) throws DvdLibraryDaoException;
    
    void wtiteDvd() throws DvdLibraryDaoException;
    
    void loadDvd() throws DvdLibraryDaoException;
    
}
