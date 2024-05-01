package com.heowc.heo.core.analysis.domain;

import com.heowc.heo.core.Module;

public interface GroupIdProvider {

    String groupId(String rootPackage, Module module);
}
