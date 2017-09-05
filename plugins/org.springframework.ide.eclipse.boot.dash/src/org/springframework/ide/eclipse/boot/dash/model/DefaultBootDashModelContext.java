/*******************************************************************************
 * Copyright (c) 2015, 2016 Pivotal Software, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Pivotal Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.model;

import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.springframework.ide.eclipse.boot.core.BootPreferences;
import org.springframework.ide.eclipse.boot.core.cli.BootInstallManager;
import org.springframework.ide.eclipse.boot.dash.BootDashActivator;
import org.springframework.ide.eclipse.boot.dash.model.runtargettypes.RunTargetType;
import org.springframework.ide.eclipse.boot.pstore.IPropertyStore;
import org.springframework.ide.eclipse.boot.pstore.IScopedPropertyStore;
import org.springframework.ide.eclipse.boot.pstore.PropertyStores;
import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;

/**
 * @author Kris De Volder
 */
public class DefaultBootDashModelContext implements BootDashModelContext {

	private IScopedPropertyStore<IProject> projectProperties = PropertyStores.createForProjects();

	private IScopedPropertyStore<RunTargetType> runTargetProperties = PropertyStores.createForRunTargets();

	private SecuredCredentialsStore securedStore = PropertyStores.createSecuredCredentialsStore();

	private IPropertyStore viewProperties = PropertyStores.backedBy(BootDashActivator.getDefault().getPreferenceStore());

	private IPropertyStore privateProperties = PropertyStores.createPrivateStore(BootDashActivator.getDefault().getStateLocation().append("private.properties"));

	private BootInstallManager bootInstalls = BootInstallManager.getInstance();

	@Override
	public IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	@Override
	public IPath getStateLocation() {
		return BootDashActivator.getDefault().getStateLocation();
	}

	@Override
	public ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	@Override
	public IScopedPropertyStore<IProject> getProjectProperties() {
		return projectProperties;
	}

	@Override
	public IScopedPropertyStore<RunTargetType> getRunTargetProperties() {
		return runTargetProperties;
	}

	@Override
	public SecuredCredentialsStore getSecuredCredentialsStore() {
		return securedStore;
	}

	@Override
	public void log(Exception e) {
		BootDashActivator.log(e);
	}

	@Override
	public LiveExpression<Pattern> getBootProjectExclusion() {
		return BootPreferences.getInstance().getProjectExclusionExp();
	}

	@Override
	public IPropertyStore getViewProperties() {
		return viewProperties;
	}

	@Override
	public IPropertyStore getPrivatePropertyStore() {
		return privateProperties;
	}

	@Override
	public BootInstallManager getBootInstallManager() {
		return bootInstalls;
	}

}
