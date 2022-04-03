/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.balibek.classroster.service;

/**
 *
 * @author bali_bek
 */
public class ClassRosterDuplicateIdException extends Exception {
    
    public ClassRosterDuplicateIdException(String message) {
        super(message);
    }

    public ClassRosterDuplicateIdException(String message,
            Throwable cause) {
        super(message, cause);
    } 
}
