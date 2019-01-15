package at.qe.sepm.skeleton.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Here the upload directory is defined
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files, should be outside of the expanded
     * WAR file for production, see https://stackoverflow.com/questions/8885201/uploaded-image-only-available-after-refreshing-the-page
     */
    private String location = "upload-dir"; // should be changed to eg /var/webapp/uploads before deploy

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
