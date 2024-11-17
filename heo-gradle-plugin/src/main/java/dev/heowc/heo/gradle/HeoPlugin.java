package dev.heowc.heo.gradle;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.tasks.JavaExec;

public class HeoPlugin implements Plugin<Project> {

    private static final String REPORT_PATH = "build/reports/heo";
    private static final Pattern DOT_PATTERN = Pattern.compile(".");
    private static final Pattern EQ_PATTERN = Pattern.compile("=");

    @Override
    public void apply(Project project) {
        final HeoPluginConfig config = project.getExtensions().create("heo", HeoPluginConfig.class);
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
            task.args(arguments(project, config));
            task.environment(environments(project, config));
            tempJar.deleteOnExit();
        });
    }

    private static List<? extends Serializable> arguments(Project project, HeoPluginConfig config) {
        return List.of("-d", determineDirectory(project, config.getDirectoryPath()),
                       "-p", determinePrefixPackage(project, config.getPrefixPackage()),
                       "-o", determineDestination(project, config.getDestination()),
                       "--failure-on-cycles", String.valueOf(config.isFailureOnCycles()));
    }

    private static String determineDirectory(Project project, @Nullable String directoryPath) {
        return StringUtils.isBlank(directoryPath)
               ? project.getProjectDir().getAbsolutePath()
               : directoryPath;
    }

    private static String determinePrefixPackage(Project project, @Nullable String prefixPackage) {
        return StringUtils.isBlank(prefixPackage)
               ? project.getGroup().toString()
               : prefixPackage;
    }

    private static String determineDestination(Project project, @Nullable String destination) {
        return StringUtils.isBlank(destination)
               ? Path.of(project.getProjectDir().getAbsolutePath(), REPORT_PATH, "index.png").toString()
               : destination;
    }

    private static Map<String, String> environments(Project project, HeoPluginConfig config) {
        return logging(project, config.getLogging())
                .map(it -> DOT_PATTERN.matcher(it).replaceAll("_"))
                .map(String::toUpperCase)
                .map(it -> {
                    final String[] keyValue = EQ_PATTERN.split(it);
                    return Map.entry(keyValue[0], keyValue[1]);
                }).collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue));
    }

    private static Stream<String> logging(Project project, @Nullable List<String> logging) {
        if (project.getGradle().getStartParameter().getLogLevel() == LogLevel.DEBUG) {
            return Stream.of("-Dlogging.level.root=DEBUG");
        }
        return Stream.ofNullable(logging)
                     .filter(Objects::nonNull)
                     .flatMap(Collection::stream)
                     .filter(Objects::nonNull)
                     .map(it -> "-Dlogging.level." + it);
    }

}
