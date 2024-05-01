package com.heowc.heo.core.analysis.domain;

import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;

public class DomainGraph extends DirectedMultigraph<String, DomainEdge> {
    public DomainGraph() {
        super(DomainEdge.class);
    }

    public void addVertex(Map<String, Set<String>> packages) {
        packages.keySet().forEach(this::addVertex);
    }

    public void addEdge(Map<String, Set<String>> packages) {
        packages.forEach((target, sources) -> {
            sources.forEach(source -> addEdge(target, source, new DomainEdge(target, source)));
        });
    }
}
