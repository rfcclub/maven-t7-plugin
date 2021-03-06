/**
 * Copyright (C) 2010-2012 Joerg Bellmann <joerg.bellmann@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.t7mp;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.googlecode.t7mp.steps.Context;

/**
 * Runs a Tomcat 7 (or 6) instance in the same process as the maven build.
 * 
 * Multiple {@link BootstrapHolder} not possible. Tomcat registers Classloader
 * at the MBeanServer.
 * 
 * @goal run
 * @requiresDependencyResolution test
 * 
 * 
 */
public class RunMojo extends AbstractT7TomcatMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        Context context = buildParentContext();
        PreConditions.checkConfiguredTomcatVersion(context.getLog(), context.getConfiguration().getTomcatVersion());

        DefaultMavenPluginContext mavenPluginContext = new DefaultMavenPluginContext(context, this);

        try {
            BootstrapHolder holder = new BootstrapHolder();
            holder.startBootstrapInstance(mavenPluginContext);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
        if (mavenPluginContext.getConfiguration().isTomcatSetAwait()) {
            new ExecutionLock().lock();
        }
    }

}
