package com.lperilla.projects.basfchallenge.process;

import com.lperilla.projects.basfchallenge.exception.BasfException;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class XmlPartProcess implements PartProcess {
    @Override
    public void process(File directory, Part file) {
        try {
            log.info("Moving {} file in directory {}", file.getSubmittedFileName(), directory);
            File output = new File(directory, file.getSubmittedFileName());
            Files.copy(file.getInputStream(), output.toPath());
        } catch (IOException e) {
            throw new BasfException(String.format("Error moving %s file to directory %s", file.getSubmittedFileName(), directory), e);
        }
    }
}
