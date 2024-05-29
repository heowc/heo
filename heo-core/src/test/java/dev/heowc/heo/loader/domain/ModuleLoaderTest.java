package dev.heowc.heo.loader.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import dev.heowc.heo.core.loader.domain.ModuleLoader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dev.heowc.heo.core.Module;

class ModuleLoaderTest {

    @TempDir
    private Path tempDir;

    @Test
    void test() throws IOException {
        final Path project = mkdir(tempDir, "test-project");
        final Path mainSourceRoot = mkdir(project, "src/main/java");
        final Path rootPackage = mkdir(mainSourceRoot, "foo/bar");
        final Path testJava = rootPackage.resolve("Test.java");
        Files.writeString(testJava, """
                package foo.bar;

                public class Test {
                }
                """);
        System.out.println(Files.exists(testJava));
        final String path = project.toString();
        final List<Module> modules = new ModuleLoader(path, "foo.bar").loadModules();
        assertThat(modules).hasSize(1);
        assertThat(modules).singleElement().satisfies(it -> {
            assertThat(it.getIdentity()).isEqualTo("foo.bar.Test");
            assertThat(it.getPath()).isEqualTo(testJava);
        });
    }

    private Path mkdir(Path dir, String path) {
        final Path subDir = dir.resolve(path);
        subDir.toFile().mkdirs();
        return subDir;
    }
}