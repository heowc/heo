package dev.heowc.heo.gradle;

public class HeoConfig {

    private String directoryPath;
    private String prefixPackage;
    private String destination;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getPrefixPackage() {
        return prefixPackage;
    }

    public void setPrefixPackage(String prefixPackage) {
        this.prefixPackage = prefixPackage;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
