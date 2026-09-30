/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.plugins.jdeps;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.maven.api.PathScope;
import org.apache.maven.api.ProjectScope;
import org.apache.maven.api.plugin.annotations.Mojo;
import org.apache.maven.api.plugin.annotations.Parameter;

/**
 * Check if test classes depend on internal JDK classes.
 *
 * @author Robert Scholte
 */
@Mojo(name = "test-jdkinternals", defaultPhase = "process-test-classes")
public class TestJDKInternalsMojo extends AbstractJDepsMojo {
    /**
     * Indicates whether the build will continue even if there are jdeps warnings.
     */
    @Parameter(defaultValue = "true", property = "jdeps.test.failOnWarning")
    private boolean failOnWarning;

    @Override
    protected boolean isFailOnWarning() {
        return failOnWarning;
    }

    @Override
    protected Path getClassesDirectory() {
        return getProject().getOutputDirectory(ProjectScope.TEST);
    }

    @Override
    protected Collection<Path> getClassPath() {
        Set<Path> classPath = new LinkedHashSet<>();
        classPath.add(getClassesDirectory());
        classPath.add(getProject().getOutputDirectory(ProjectScope.MAIN));
        classPath.addAll(getResolvedPaths());
        return classPath;
    }

    @Override
    protected PathScope getPathScope() {
        return PathScope.TEST_RUNTIME;
    }
}
