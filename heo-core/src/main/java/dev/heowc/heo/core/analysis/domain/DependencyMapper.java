package dev.heowc.heo.core.analysis.domain;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParseStart;
import com.github.javaparser.Providers;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithName;

import dev.heowc.heo.annotation.DomainService;
import dev.heowc.heo.core.Module;

@DomainService
public class DependencyMapper {

    private final Logger logger = LoggerFactory.getLogger(DependencyMapper.class);
    private final JavaParser javaParser;

    public DependencyMapper(JavaParser javaParser) {
        this.javaParser = javaParser;
    }

    public DomainGraph mapDependencies(List<Module> modules, String rootPackage) {
        final DomainGraph graph = new DomainGraph();
        final GroupIdProvider groupIdProvider = new DefaultGroupIdProvider();
        final Map<String, Module> moduleGroup = modules.stream()
                                                       .collect(Collectors.toUnmodifiableMap(Module::getIdentity,
                                                                                             Function.identity()));
        final Map<String, Set<String>> result =
                modules.stream()
                       .map(it -> toDependentModule(rootPackage, it, moduleGroup, groupIdProvider))
                       .peek(it -> logger.debug("module={}, dependent={}",
                                                it.getKey(), it.getValue()))
                       .collect(Collectors.toUnmodifiableMap(Pair::getKey, Pair::getValue, merge()));
        graph.addVertex(result);
        graph.addEdge(result);
        return graph;
    }

    private static String toGroupId(GroupIdProvider groupIdProvider, String rootPackage, Module module) {
        return groupIdProvider.groupId(rootPackage, module);
    }

    private static BinaryOperator<Set<String>> merge() {
        return (deps, deps2) -> {
            final Set<String> merged = new HashSet<>(deps);
            merged.addAll(deps2);
            return merged;
        };
    }

    private Pair<String, Set<String>> toDependentModule(String rootPackage,
                                                        Module module,
                                                        Map<String, Module> moduleGroup,
                                                        GroupIdProvider groupIdProvider) {
        final String myGroupId = toGroupId(groupIdProvider, rootPackage, module);
        final DependentModule dependentModule = createDependentModule(module, moduleGroup, rootPackage);
        return ImmutablePair.of(myGroupId,
                                dependentModule.getDependencies()
                                               .stream()
                                               .map(it -> toGroupId(groupIdProvider, rootPackage, it))
                                               .filter(groupId -> !groupId.equals(myGroupId))
                                               .collect(Collectors.toUnmodifiableSet()));
    }

    private DependentModule createDependentModule(Module module, Map<String, Module> modules, String rootPackage) {
        return new DependentModule(module, parseImports(module).map(NodeWithName::getNameAsString)
                                                               .filter(it -> it.startsWith(rootPackage))
                                                               .map(modules::get)
                                                               .filter(Objects::nonNull)
                                                               .filter(it -> !module.equals(it))
                                                               .toList());
    }

    private Stream<ImportDeclaration> parseImports(Module module) {
        try {
            final ParseResult<ImportDeclaration> parsed =
                    javaParser.parse(ParseStart.IMPORT_DECLARATION,
                                     Providers.provider(module.getPath(),
                                                        javaParser.getParserConfiguration().getCharacterEncoding()));

            if (!parsed.isSuccessful()) {
                return Stream.empty();
            }
            return parsed.getResult().stream();
        } catch (IOException e) {
            return Stream.empty();
        }
    }
}
