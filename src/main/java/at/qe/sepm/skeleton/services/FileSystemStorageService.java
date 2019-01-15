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


/**
 * Service for storing, retrieving and deleting files
 *
 * @author Johannes Koch
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    /**
     * The {@link StorageProperties#location} is used as storage directory
     *
     * @param properties
     */
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /**
     * Stores a {@link UploadedFile} in the service
     *
     * @param uploadedFile file to store
     * @return filename of stored file, needed to retrieve file
     * @throws IOException
     */
    @Override
    public String store(UploadedFile uploadedFile) throws IOException {
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName());
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        Path file = Files.createTempFile(rootLocation, filename + "-", "." + extension);

        InputStream input = uploadedFile.getInputstream();
        Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
        return file.getFileName().toString();
    }

    /**
     * Retrieves a previously stored file
     *
     * @param filename to retrieve
     * @return {@link Path} to file
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * Deletes all files stored in the service
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /**
     * Initializes storage by creating storage directory
     *
     * @throws IOException
     */
    @Override
    public void init() throws IOException {
        Files.createDirectories(rootLocation);
    }

    /**
     * Deletes file from the service
     *
     * @param filename of file to be deleted
     * @throws IOException
     */
    @Override
    public void deleteFile(String filename) throws IOException {
        Files.delete(rootLocation.resolve(filename));
    }
}

