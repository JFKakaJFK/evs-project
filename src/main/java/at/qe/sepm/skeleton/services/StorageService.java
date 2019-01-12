package at.qe.sepm.skeleton.services;

import org.primefaces.model.UploadedFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {

    void init() throws IOException;

    String store(UploadedFile file) throws IOException;

    Path load(String filename);

    void deleteAll();

    void deleteFile(String filename) throws IOException;
}
