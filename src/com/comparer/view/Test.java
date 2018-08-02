package com.comparer.view;

import com.comparer.core.view.Component;
import com.comparer.core.view.Html;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void run() {
        while (true) {
            System.out.println("Test");
            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine();
            if (input.equals("exit")) {
                break;
            } else {
                try {
                    String sss = Html.get(Component.DATABASE_ROW);
                    System.out.println(sss);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
