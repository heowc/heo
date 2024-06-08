package dev.heowc.heo.gradle;

public class HeoPluginConfig {

    private String directoryPath;
    private String prefixPackage;
    private String destination;
    private boolean failureOnCycles;

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

    public boolean isFailureOnCycles() {
        return failureOnCycles;
    }

    public void setFailureOnCycles(boolean failureOnCycles) {
        this.failureOnCycles = failureOnCycles;
    }
}
