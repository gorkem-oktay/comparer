/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer;

import com.comparer.command.CommandFactory;
import com.comparer.command.ICommand;
import java.util.Scanner;

/**
 *
 * @author zafiru
 */
class Ftp {
    static void run(){
        while(true){
            System.out.println("\n\nFTP Module");
            System.out.println(Style.SEPERATOR);
            System.out.println("cmp [-of]");
            System.out.println("upload [-all|-added|-changed] || <filename*>");
            System.out.println(Style.SEPERATOR);
            Scanner reader = new Scanner(System.in);
            String input = reader.nextLine();
            if(input.equals("exit")){
                break;
            } else {
                try{
                    ICommand command = CommandFactory.init(input);
                    command.execute();
                } catch(UnsupportedOperationException ex) {
                    System.out.println("unsupported opreation");
                    System.out.println("operations: cmp, upload");
                }
            }
        }
    }
}
