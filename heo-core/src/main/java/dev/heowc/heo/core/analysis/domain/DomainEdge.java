package dev.heowc.heo.core.analysis.domain;

import org.jgrapht.graph.DefaultEdge;

public class DomainEdge extends DefaultEdge {

    private final String target;
    private final String source;

    public DomainEdge(String target, String source) {
        this.target = target;
        this.source = source;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getSource() {
        return source;
    }
}
