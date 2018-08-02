package com.comparer.command.ftp;

import com.comparer.core.ftp.UploadedFile;
import com.jcraft.jsch.JSchException;
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
    public void disconnectFromSSH() {}

    @Override
    public void connectToSSH() throws JSchException {}

    @Override
    public void process() {
        if (parameters.size() == 0 && options.size() == 0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        try {
            FTPFile[] files1 = server1.getFiles(0);
            FTPFile[] files2 = server2.getFiles(0);

            for (FTPFile file1 : files1) {
                if (parameters.size() > 0) {
                    int index = parameters.indexOf(file1.getName());
                    if (index != -1) {
                        server1.uploadFile(file1, server2);
                        uploadedFiles.add(new UploadedFile(file1, "uploaded"));
                        parameters.remove(index);
                        continue;
                    }
                }
                boolean found = false;
                for (FTPFile file2 : files2) {
                    if (file1.getName().equals(file2.getName())) {
                        found = true;
                        if (options.contains("-changed") || options.contains("-all")) {
                            if (file1.getSize() != file2.getSize()) {
                                server1.uploadFile(file1, server2);
                                uploadedFiles.add(new UploadedFile(file1, "changed"));
                            }
                        }
                    }
                }
                if (!found) {
                    if (options.contains("-all") || options.contains("-added")) {
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
        if (uploadedFiles.isEmpty()) {
            System.out.println("No file found to upload");
        } else {
            System.out.println("Uploaded Files:");
            for (UploadedFile file : uploadedFiles) {
                System.out.println(file.file.getName() + ", " + file.type);
            }
        }
    }
}
