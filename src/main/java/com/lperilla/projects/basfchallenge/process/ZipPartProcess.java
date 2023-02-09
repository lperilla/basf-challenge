package com.lperilla.projects.basfchallenge.process;

import com.lperilla.projects.basfchallenge.exception.BasfException;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipPartProcess implements PartProcess {
    @Override
    public void process(File directory, Part file) {
        log.info("Unzipping {} file in directory {}", file.getSubmittedFileName(), directory);
        try (ZipInputStream zis = new ZipInputStream(file.getInputStream())) {
            ZipEntry zipEntry = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            while (zipEntry != null) {
                File destFile = new File(directory, zipEntry.getName());
                try (FileOutputStream fos = new FileOutputStream(destFile)) {
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
        } catch (IOException e) {
            throw new BasfException(String.format("Error unzipping %s file in directory %s", file.getSubmittedFileName(), directory), e);
        }
    }
}
