package dev.heowc.heo.core.analysis.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.heowc.heo.core.Module;
import dev.heowc.heo.core.analysis.domain.DependencyAnalysisResult;
import dev.heowc.heo.core.analysis.domain.DependencyMapper;
import dev.heowc.heo.core.analysis.domain.DomainGraph;

@Service
public class DependencyAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(DependencyAnalysisService.class);

    private final DependencyMapper dependencyMapper;

    public DependencyAnalysisService(DependencyMapper dependencyMapper) {
        this.dependencyMapper = dependencyMapper;
    }

    public DependencyAnalysisResult analyzeProjectDependencies(List<Module> modules, String rootPackage) {
        logger.info("Analysing project dependencies for modules. size={}", modules.size());
        final DomainGraph domainGraph = dependencyMapper.mapDependencies(modules, rootPackage);
        return new DependencyAnalysisResult(domainGraph);
    }
}
