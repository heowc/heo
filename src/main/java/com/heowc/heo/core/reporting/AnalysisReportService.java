package com.heowc.heo.core.reporting;

import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.springframework.stereotype.Service;

import com.heowc.heo.core.analysis.domain.DependencyAnalysisResult;
import com.heowc.heo.core.analysis.domain.DomainEdge;

@Service
public class AnalysisReportService {

    private static String createDotFormatted(DependencyAnalysisResult result) {
        final DOTExporter<String, DomainEdge> exporter = new DOTExporter<>(s -> s);

        final Set<String> cycled = result.getCycleDetector().findCycles();
        exporter.setVertexAttributeProvider(v -> {
            final Map<String, Attribute> attributes = new LinkedHashMap<>();
            attributes.put("shape", DefaultAttribute.createAttribute("folder"));
            if (cycled.contains(v)) {
                attributes.put("color", DefaultAttribute.createAttribute("red"));
            }
            return attributes;
        });

        exporter.setEdgeAttributeProvider(edge -> {
            final Map<String, Attribute> attributes = new LinkedHashMap<>();
            if (cycled.contains(edge.getTarget()) && cycled.contains(edge.getSource())) {
                attributes.put("color", DefaultAttribute.createAttribute("red"));
            }
            return attributes;
        });
        final Writer writer = new StringWriter();
        exporter.exportGraph(result.getDomainGraph(), writer);
        return writer.toString();
    }

    public String createReport(DependencyAnalysisResult result) {
        return createDotFormatted(result);
    }
}
