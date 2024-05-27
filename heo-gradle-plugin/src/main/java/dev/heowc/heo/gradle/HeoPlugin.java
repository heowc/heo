package dev.heowc.heo.gradle;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.JavaExec;

import javax.annotation.Nullable;

public class HeoPlugin implements Plugin<Project> {

    private static final String REPORT_PATH = "build/reports/heo";

    @Override
    public void apply(Project project) {
        final HeoConfig config = project.getExtensions().create("heo", HeoConfig.class);
        project.getTasks().register("heoReport", JavaExec.class, task -> {
            task.setGroup("heo");
            task.setDescription("Execute heo-cli");

            // Extract the JAR file from the resources to a temporary location
            final File tempJar = new File(System.getProperty("java.io.tmpdir"), "heo-cli.jar");
            try (InputStream jarStream = getClass().getClassLoader().getResourceAsStream("heo-cli.jar")) {
                if (jarStream == null) {
                    throw new RuntimeException("JAR file not found in resources");
                }
                Files.copy(jarStream, tempJar.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Failed to extract JAR file", e);
            }

            task.setMain("-jar");
            task.args(tempJar.getAbsolutePath());
            task.args(List.of("-d", determineDirectory(project, config.getDirectoryPath()),
                              "-p", determinePrefixPackage(project, config.getPrefixPackage()),
                              "-o", determineDestination(project, config.getDestination())));

            tempJar.deleteOnExit();
        });
    }

    private static String determineDirectory(Project project, @Nullable String directoryPath) {
        return StringUtils.isBlank(directoryPath) ? project.getProjectDir().getAbsolutePath() : directoryPath;
    }

    private static String determinePrefixPackage(Project project, @Nullable String prefixPackage) {
        return StringUtils.isBlank(prefixPackage) ? project.getGroup().toString() : prefixPackage;
    }

    private static String determineDestination(Project project, @Nullable String destination) {
        return StringUtils.isBlank(destination) ? Path.of(project.getProjectDir().getAbsolutePath(), REPORT_PATH, "index.png").toString() : destination;
    }

}