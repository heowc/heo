package com.heowc.heo.core.loader.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.ParserCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;

import com.heowc.heo.core.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleLoader {

    private static final Logger logger = LoggerFactory.getLogger(ModuleLoader.class);

    private final Path projectPath;
    private final String rootPackage;

    public ModuleLoader(String projectDirectory, String rootPackage) {
        this.projectPath = Path.of(projectDirectory);
        this.rootPackage = rootPackage;
    }

    public List<Module> loadModules() throws IOException {
        final ProjectRoot projectRoot = new ParserCollectionStrategy().collect(projectPath);
        return projectRoot.getSourceRoots()
                          .stream()
                          .filter(ModuleLoader::ignoreNonMainSourceRoot)
                          .peek(it -> logger.info("Detected module (file://{})", it.getRoot()))
                          .flatMap(this::tryParseSources)
                          .filter(it -> ignoreFile(it.getStorage().orElseThrow().getPath()))
                          .map(ModuleLoader::extractModule)
                          .toList();
    }

    private static boolean ignoreNonMainSourceRoot(SourceRoot sourceRoot) {
        final String rootPath = sourceRoot.getRoot().toString();
        return !rootPath.contains("/generated/") && !rootPath.contains("/test/");
    }

    private static boolean ignoreFile(Path path) {
        return !path.toString().endsWith("package-info.java");
    }

    private static Module extractModule(CompilationUnit unit) {
        return Module.of(String.format("%s.%s",
                                       unit.getPackageDeclaration()
                                           .orElseThrow()
                                           .getName()
                                           .asString(),
                                       unit.getPrimaryTypeName().orElseThrow()),
                         unit.getStorage().orElseThrow().getPath());
    }

    private Stream<CompilationUnit> tryParseSources(SourceRoot sourceRoot) {
        try {
            return sourceRoot.tryToParse(rootPackage)
                             .stream()
                             .filter(ParseResult::isSuccessful)
                             .map(it -> it.getResult().orElseThrow());
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
