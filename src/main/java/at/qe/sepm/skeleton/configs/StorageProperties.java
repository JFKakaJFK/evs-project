package at.qe.sepm.skeleton.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /*
    // PRIMEFACES VERSION
    // this would be a path which would be accessible to the primefaces download
    // where the stream is provided by the external context (getResourceAsStream)
    private String location = "src/main/webapp/upload-dir";
    */

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
