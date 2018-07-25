/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer;

import java.util.Scanner;

/**
 *
 * @author zafiru
 */
public class Main {

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\nWelcome to Comparer");
            System.out.println(Style.SEPERATOR);
            System.out.println("ftp");
            System.out.println("db");
            System.out.println(Style.SEPERATOR);

            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine();

            if (input.equals("ftp")) {
                Ftp.run();
            } else if (input.equals("db")) {

            } else if (input.equals("exit")) {
                break;
            } else {
                System.out.println("unsupported operation");
                System.out.println("operations: ftp, db");
            }
        }
    }
}
