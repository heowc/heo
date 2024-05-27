package dev.heowc.heo.core.loader.application;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import dev.heowc.heo.core.Module;
import dev.heowc.heo.core.loader.domain.ModuleLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ModuleLoaderService {

    private final Logger logger = LoggerFactory.getLogger(ModuleLoaderService.class);

    public List<Module> loads(String projectDirectory, String rootPackage) {
        try {
            logger.info("Loading " + rootPackage + " from " + projectDirectory);
            return new ModuleLoader(projectDirectory, rootPackage).loadModules();
        } catch (IOException e) {
            logger.error("Error while loading " + rootPackage + " from " + projectDirectory, e);
            throw new UncheckedIOException(e);
        }
    }
}
