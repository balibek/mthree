
package com.balibek.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Scanner;
//import org.springframework.stereotype.Component;

public class UserIoConsoleImpl implements UserIo {

    Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        boolean invalidInput = true;
        double num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Double.parseDouble(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Not Int... Try again");
            }
        }
        return num;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result;
        do {
            result = readDouble(prompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public float readFloat(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Not Int... Try again");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result;
        do {
            result = readInt(prompt);
        } while (result < min || result > max);

        return result;
    }

    @Override
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String readString(String prompt) {
        this.print(prompt);
        return console.nextLine();
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean invalidInput = true;
        BigDecimal num = BigDecimal.ZERO;
        while (invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = new BigDecimal(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error. Not format 0.00... Try again");
            }
        }
        return num;
    }

 

}
