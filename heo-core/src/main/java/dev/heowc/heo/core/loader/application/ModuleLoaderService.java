package dev.heowc.heo.core.loader.application;

import com.github.javaparser.utils.ParserCollectionStrategy;

import dev.heowc.heo.core.loader.ModuleLoaderConfig;

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
    private final ParserCollectionStrategy parserCollectionStrategy;

    public ModuleLoaderService(ParserCollectionStrategy parserCollectionStrategy) {
        this.parserCollectionStrategy = parserCollectionStrategy;
    }

    public List<Module> loads(ModuleLoaderConfig config) {
        try {
            logger.info("Loading " + config.rootPackage() + " from " + config.projectDirectory());
            return new ModuleLoader(config, parserCollectionStrategy).loadModules();
        } catch (IOException e) {
            logger.error("Error while loading " + config.rootPackage() + " from " + config.projectDirectory(), e);
            throw new UncheckedIOException(e);
        }
    }
}
