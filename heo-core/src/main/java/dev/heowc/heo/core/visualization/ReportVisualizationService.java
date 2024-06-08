package dev.heowc.heo.core.visualization;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import guru.nidi.graphviz.engine.GraphvizEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import guru.nidi.graphviz.engine.Engine;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

@Service
public class ReportVisualizationService {

    private static final Logger logger = LoggerFactory.getLogger(ReportVisualizationService.class);

    static {
        logger.info("Available engines: {}", detectAvailableEngines());
    }

    private static List<GraphvizEngine> detectAvailableEngines() {
        try {
            final Graphviz graphviz = Graphviz.fromString("");
            graphviz.useEngine(List.of());
            final Field field = Graphviz.class.getDeclaredField("availableEngines");
            field.setAccessible(true);
            return  (List<GraphvizEngine>) field.get(graphviz);
        } catch (Throwable e) {
            logger.debug("Could not detect available engines", e);
        }
        return List.of();
    }

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
