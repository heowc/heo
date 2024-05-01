package com.heowc.heo.core.analysis.domain;

import com.heowc.heo.core.Module;

public class DefaultGroupIdProvider implements GroupIdProvider {

    private static String extractPackagePath(String packagePath, String rootPackagePath) {
        final String removed = removeRootPackage(packagePath, rootPackagePath);
        final int index = removed.indexOf(".");
        if (index == -1) {
            return removed;
        }
        return removed.substring(0, index);
    }

    private static String removeRootPackage(String packagePath, String rootPackagePath) {
        final String replaced = packagePath.replace(rootPackagePath, "");
        if (replaced.startsWith(".")) {
            return replaced.replaceFirst(".", "");
        }
        return replaced;
    }

    @Override
    public String groupId(String rootPackage, Module module) {
        return extractPackagePath(module.getIdentity(), rootPackage);
    }

}
