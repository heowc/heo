package dev.heowc.heo.core.analysis.domain;

import org.jgrapht.alg.cycle.CycleDetector;

public class DependencyAnalysisResult {

    private final DomainGraph domainGraph;
    private final CycleDetector<String, DomainEdge> cycleDetector;

    public DependencyAnalysisResult(DomainGraph domainGraph) {
        this.domainGraph = domainGraph;
        this.cycleDetector = new CycleDetector<>(domainGraph);
    }

    public DomainGraph getDomainGraph() {
        return domainGraph;
    }

    public CycleDetector<String, DomainEdge> getCycleDetector() {
        return cycleDetector;
    }

    public boolean hasCycle() {
        return cycleDetector.detectCycles();
    }
}
