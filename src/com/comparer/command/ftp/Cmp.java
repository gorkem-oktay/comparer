/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comparer.command.ftp;

import com.comparer.Style;
import com.comparer.core.ExtraFile;
import com.comparer.core.FilePair;
import com.comparer.core.Result;
import com.comparer.core.Server;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author zafiru
 */
public class Cmp extends IFtpCommand {

    private ArrayList<Result> results;

    public Cmp(String command) {
        super(command);
        this.results = new ArrayList<>();
    }

    @Override
    public void process() {
        try {
            for(int i = 0; i < server1.pathes.size(); i++){
                Result result = new Result(i);
                FTPFile[] files1 = server1.getFiles(i);
                FTPFile[] files2 = server2.getFiles(i);

                for (FTPFile file1 : files1) {
                    boolean found = false;
                    for (FTPFile file2 : files2) {
                        if(file1.getName().equals(file2.getName())){
                            found = true;
                            if(file1.getSize() != file2.getSize()){
                                result.addChangedFile(file1, file2);
                            }
                            break;
                        }
                    }
                    if(!found){
                        result.addExtraFile(file1, 0);
                    }
                }

                for (FTPFile file2 : files2) {
                    boolean found = false;
                    for (FTPFile file1 : files1) {
                        if(file2.getName().equals(file1.getName())){
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        result.addExtraFile(file2, 1);
                    }
                }
                results.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server1.disconnect();
            server2.disconnect();
        }
    }

    @Override
    public void print(){
        if(options.contains("-of")){
            String view = "";
            for(Result result : results){
                view += prepareView(result);
            }
            try {
                File dir = new File("results");
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File("results/result.txt");
                FileUtils.writeStringToFile(file, view, Charset.defaultCharset());
                System.out.println("Results saved to file at " + file.getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("Couldn't write to file");
            }
        } else {
            for(Result result : results){
                System.out.println(Style.SEPERATOR);
                System.out.println("\nResults From Path: " + result.pathIndex);

                System.out.println(prepareView(result));
            }
        }
    }

    private void printSingle(Result result){
        if(result.changedFiles.isEmpty()){
            System.out.println("\nThere isn't any changed file");
        } else {
            System.out.println("\nChanged Files:");
            for(FilePair item : result.changedFiles){
                System.out.println(server1.name + ": " + item.server1File.getName() + ", " + item.server1File.getSize());
                System.out.println(server2.name + ": " + item.server2File.getName() + ", " + item.server2File.getSize());
                System.out.println("");
            }
        }

        if(result.extraFiles.isEmpty()){
            System.out.println("\nThere isn't any extra file from both servers");
        } else {
            ArrayList<ExtraFile> extraFiles1 = new ArrayList<>();
            ArrayList<ExtraFile> extraFiles2 = new ArrayList<>();

            for(ExtraFile file : result.extraFiles){
                if(file.server == 0){
                    extraFiles1.add(file);
                } else {
                    extraFiles2.add(file);
                }
            }

            for(int i = 0; i < 2; i++){
                ArrayList<ExtraFile> extras = i == 0 ? extraFiles1 : extraFiles2;
                Server server = i == 0 ? server1 : server2;

                if(extras.isEmpty()){
                    System.out.println("\nThere isn't any extra file from " + server.name);
                } else {
                    System.out.println("\nExtra files from " + server.name + ":");
                    for(ExtraFile file : extras){
                        System.out.println("File: " + file.file.getName());
                    }
                }
            }
        }
    }

    private String prepareView(Result result){
        String view = "\n" + Style.SEPERATOR;
        view += "\n\nResults From Path: " + result.pathIndex;
        if(result.changedFiles.isEmpty()){
            view += "\n\nThere isn't any changed file";
        } else {
            view += "\n\nChanged Files:";
            for(FilePair item : result.changedFiles){
                view += "\n";
                view += server1.name + ": " + item.server1File.getName() + ", " + item.server1File.getSize();
                view += "\n";
                view += server2.name + ": " + item.server2File.getName() + ", " + item.server2File.getSize();
                view += "\n";
            }
        }

        if(result.extraFiles.isEmpty()){
            view += "\n\nThere isn't any extra file from both servers";
        } else {
            ArrayList<ExtraFile> extraFiles1 = new ArrayList<>();
            ArrayList<ExtraFile> extraFiles2 = new ArrayList<>();

            for(ExtraFile file : result.extraFiles){
                if(file.server == 0){
                    extraFiles1.add(file);
                } else {
                    extraFiles2.add(file);
                }
            }

            for(int i = 0; i < 2; i++){
                ArrayList<ExtraFile> extras = i == 0 ? extraFiles1 : extraFiles2;
                Server server = i == 0 ? server1 : server2;

                if(extras.isEmpty()){
                    view += "\n\nThere isn't any extra file from " + server.name;
                } else {
                    view += "\n\nExtra files from " + server.name + ":";
                    for(ExtraFile file : extras){
                        view += "\n";
                        view += "File: " + file.file.getName();
                    }
                }
            }
        }
        return view;
    }
}
