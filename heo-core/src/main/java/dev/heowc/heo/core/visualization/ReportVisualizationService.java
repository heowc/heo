package dev.heowc.heo.core.visualization;

import java.io.File;
import java.io.IOException;

import guru.nidi.graphviz.engine.GraphvizJdkEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

@Service
public class ReportVisualizationService {

    private static final Logger logger = LoggerFactory.getLogger(ReportVisualizationService.class);

    public void createFile(String report, String destination) {
        try {
            final File file = new File(destination);
            Graphviz.fromString(report)
                    .engine(Engine.DOT)
                    .render(Format.PNG)
                    .toFile(file);
            logger.info("Report file created  (file://{})", file.toPath().toAbsolutePath());
        } catch (IOException e) {
            logger.error("Report file creation failed", e);
        }
    }
}
