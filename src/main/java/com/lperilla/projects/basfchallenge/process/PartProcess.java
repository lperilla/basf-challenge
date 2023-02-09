package com.lperilla.projects.basfchallenge.process;

import jakarta.servlet.http.Part;

import java.io.File;

public interface PartProcess {

    void process(File directory, Part part);

}
