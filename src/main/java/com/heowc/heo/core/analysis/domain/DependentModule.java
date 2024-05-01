package com.heowc.heo.core.analysis.domain;

import java.util.List;
import java.util.Objects;

import com.heowc.heo.core.Module;

public class DependentModule {

    private final Module module;
    private final List<Module> dependencies;

    public DependentModule(Module module, List<Module> dependencies) {
        this.module = Objects.requireNonNull(module);
        this.dependencies = List.copyOf(dependencies);
    }

    public Module getModule() {
        return module;
    }

    public List<Module> getDependencies() {
        return dependencies;
    }
}
