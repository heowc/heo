package dev.heowc.heo.core;

import java.nio.file.Path;
import java.util.Objects;

public class Module {
    private final String identifier;
    private final Path path;

    protected Module(String identifier, Path path) {
        this.identifier = Objects.requireNonNull(identifier);
        this.path = Objects.requireNonNull(path);
    }

    public static Module of(String identity, Path path) {
        return new Module(identity, path);
    }

    public String getIdentity() {
        return identifier;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(identifier, module.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }

    @Override
    public String toString() {
        return "Module{" +
               "identifier='" + identifier + '\'' +
               ", path=" + path +
               '}';
    }
}
