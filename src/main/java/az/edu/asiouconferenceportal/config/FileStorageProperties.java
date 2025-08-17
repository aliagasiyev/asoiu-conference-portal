package az.edu.asiouconferenceportal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.files")
public class FileStorageProperties {

	private String storageDir = "./storage";

	public String getStorageDir() { return storageDir; }

	public void setStorageDir(String storageDir) { this.storageDir = storageDir; }
}
