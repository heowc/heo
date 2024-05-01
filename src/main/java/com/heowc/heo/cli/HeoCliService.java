package com.heowc.heo.cli;

import java.util.List;

import org.springframework.stereotype.Service;

import com.heowc.heo.core.Module;
import com.heowc.heo.core.analysis.application.DependencyAnalysisService;
import com.heowc.heo.core.analysis.domain.DependencyAnalysisResult;
import com.heowc.heo.core.loader.application.ModuleLoaderService;
import com.heowc.heo.core.reporting.AnalysisReportService;
import com.heowc.heo.core.visualization.ReportVisualizationService;

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

    public void command(String directory, String rootPackage, String destination) {
        final List<Module> modules = moduleLoaderService.loads(directory, rootPackage);
        final DependencyAnalysisResult result =
                dependencyAnalysisService.analyzeProjectDependencies(modules, rootPackage);
        final String report = analysisReportService.createReport(result);
        reportVisualizationService.createFile(report, destination);
    }
}
