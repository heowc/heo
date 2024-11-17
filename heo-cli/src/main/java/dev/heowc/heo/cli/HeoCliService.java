package dev.heowc.heo.cli;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.heowc.heo.core.HeoException;
import dev.heowc.heo.core.Module;
import dev.heowc.heo.core.analysis.application.DependencyAnalysisService;
import dev.heowc.heo.core.analysis.domain.DependencyAnalysisResult;
import dev.heowc.heo.core.loader.ModuleLoaderConfig;
import dev.heowc.heo.core.loader.application.ModuleLoaderService;
import dev.heowc.heo.core.reporting.AnalysisReportService;
import dev.heowc.heo.core.visualization.ReportVisualizationService;

@Service
public class HeoCliService {

    private final ModuleLoaderService moduleLoaderService;
    private final DependencyAnalysisService dependencyAnalysisService;
    private final AnalysisReportService analysisReportService;
    private final ReportVisualizationService reportVisualizationService;

    public HeoCliService(ModuleLoaderService moduleLoaderService,
                         DependencyAnalysisService dependencyAnalysisService,
                         AnalysisReportService analysisReportService,
                         ReportVisualizationService reportVisualizationService) {
        this.moduleLoaderService = moduleLoaderService;
        this.dependencyAnalysisService = dependencyAnalysisService;
        this.analysisReportService = analysisReportService;
        this.reportVisualizationService = reportVisualizationService;
    }

    public void command(String directory, String rootPackage, String destination, HeoConfig heoConfig) {
        final List<Module> modules = moduleLoaderService.loads(createConfig(directory, rootPackage));
        final DependencyAnalysisResult result =
                dependencyAnalysisService.analyzeProjectDependencies(modules, rootPackage);
        final String report = analysisReportService.createReport(result);
        reportVisualizationService.createFile(report, destination);
        if (heoConfig.failureOnCycles() && result.hasCycle()) {
            throw new HeoException("Cycles occurred");
        }
    }

    private static ModuleLoaderConfig createConfig(String directory, String rootPackage) {
        return new ModuleLoaderConfig(directory, rootPackage);
    }
}
