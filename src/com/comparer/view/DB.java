package com.comparer.view;

import com.comparer.command.CommandFactory;
import com.comparer.command.ICommand;

import java.util.Scanner;

public class DB {
    public static void run() {
        while (true) {
            System.out.println("\n\nDB Module");
            System.out.println(Style.SEPERATOR);
            System.out.println("cmp [-all|-table|-view] [-of]");
            System.out.println(Style.SEPERATOR);
            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                try {
                    ICommand command = CommandFactory.init(input, "db");
                    command.execute();
                } catch (UnsupportedOperationException ex) {
                    System.out.println("unsupported opreation");
                    System.out.println("operations: cmp, upload");
                }
            }
        }
    }
}