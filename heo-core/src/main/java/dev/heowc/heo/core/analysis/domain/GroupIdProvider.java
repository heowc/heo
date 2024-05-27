package dev.heowc.heo.core.analysis.domain;

import dev.heowc.heo.core.Module;

public interface GroupIdProvider {

    String groupId(String rootPackage, Module module);
}
