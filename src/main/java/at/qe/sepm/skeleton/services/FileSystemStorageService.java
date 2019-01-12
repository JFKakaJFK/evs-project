package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.configs.StorageProperties;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(UploadedFile uploadedFile) throws IOException {
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(rootLocation, filename + "-", "." + extension);

        InputStream input = uploadedFile.getInputstream();
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        return file.getFileName().toString();
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Files.delete(rootLocation.resolve(filename));
    }
}

