package org.food;

import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;


public class TestUtils {

    protected String getJsonAsString(String fileName) throws IOException {
        return  Files.readString(ResourceUtils.getFile(fileName).toPath());
    }
}

