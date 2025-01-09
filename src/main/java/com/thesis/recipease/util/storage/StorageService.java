package com.thesis.recipease.util.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file, int recipeId);

    Path load(String filename);

    void deleteAll();

}
