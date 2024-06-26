package dev.heowc.heo.core.loader.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import dev.heowc.heo.core.Module;

class ModuleLoaderVariousPathTest {

    private final String rootDir = System.getProperty("rootDir");

    @Test
    void absolutePath() throws IOException {
        final Path absolutePath = Path.of(rootDir, "heo-core").toAbsolutePath();

        final List<Module> modules = new ModuleLoader(absolutePath.toString(),
                                                      "dev.heowc.heo.core").loadModules();

        assertThat(modules).isNotEmpty();
    }

    @Test
    void relativePath() throws IOException {
        final Path relativePath = Path.of("");

        final List<Module> modules = new ModuleLoader(relativePath.toString(),
                                                      "dev.heowc.heo.core").loadModules();

        assertThat(modules).isNotEmpty();
    }
}