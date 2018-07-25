package com.comparer.command.ftp;

import com.comparer.core.UploadedFile;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;

public class Upload extends IFtpCommand {

    private ArrayList<UploadedFile> uploadedFiles;

    public Upload(String command) {
        super(command);
        this.uploadedFiles = new ArrayList<>();
    }

    @Override
    public void process() {
        if(!parameters.contains("all") && !parameters.contains("added") && !parameters.contains("changed")){
            throw new UnsupportedOperationException("Not supported yet.");
        }
        try {
            FTPFile[] files1 = server1.getFiles(0);
            FTPFile[] files2 = server2.getFiles(0);

            for (FTPFile file1 : files1) {
                boolean found = false;
                for (FTPFile file2 : files2) {
                    if (file1.getName().equals(file2.getName())) {
                        found = true;
                        if(parameters.contains("changed") || parameters.contains("all")){
                            if (file1.getSize() != file2.getSize()) {
                                server1.uploadFile(file1, server2);
                                uploadedFiles.add(new UploadedFile(file1, "changed"));
                            }
                        }
                    }
                }
                if (!found) {
                    if(parameters.contains("all") || parameters.contains("added")){
                        server1.uploadFile(file1, server2);
                        uploadedFiles.add(new UploadedFile(file1, "added"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server1.disconnect();
            server2.disconnect();
        }
    }

    @Override
    public void print() {
        if(uploadedFiles.isEmpty()){
            System.out.println("No file found to upload");
        } else {
            System.out.println("Uploaded Files:");
            for (UploadedFile file : uploadedFiles) {
                System.out.println(file.file.getName() + ", " + file.type);
            }
        }
    }
}
