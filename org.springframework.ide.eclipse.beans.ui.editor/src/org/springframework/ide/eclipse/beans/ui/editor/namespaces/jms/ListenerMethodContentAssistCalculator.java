/*******************************************************************************
 * Copyright (c) 2005, 2007 Spring IDE Developers
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spring IDE Developers - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.beans.ui.editor.namespaces.jms;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IType;
import org.eclipse.wst.xml.ui.internal.contentassist.ContentAssistRequest;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.IContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.contentassist.MethodContentAssistCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.util.BeansEditorUtils;
import org.springframework.ide.eclipse.core.java.FlagsMethodFilter;
import org.springframework.ide.eclipse.core.java.IMethodFilter;
import org.springframework.ide.eclipse.core.java.JdtUtils;
import org.springframework.ide.eclipse.core.java.OrMethodFilter;

/**
 * {@link MethodContentAssistCalculator} extension that proposes content assist
 * proposals for the method attribute of the jms:listener element.
 * @author Christian Dupuis
 * @since 2.0.2
 */
@SuppressWarnings("restriction")
public class ListenerMethodContentAssistCalculator extends
		MethodContentAssistCalculator implements IContentAssistCalculator {

	private static IMethodFilter FILTER;

	static {
		OrMethodFilter filter = new OrMethodFilter();
		filter.addMethodFilter(new FlagsMethodFilter(FlagsMethodFilter.PUBLIC
				| FlagsMethodFilter.NOT_CONSTRUCTOR
				| FlagsMethodFilter.NOT_INTERFACE));
		filter.addMethodFilter(new FlagsMethodFilter(
				FlagsMethodFilter.PROTECTED | FlagsMethodFilter.NOT_CONSTRUCTOR
						| FlagsMethodFilter.NOT_INTERFACE));
		FILTER = filter;
	}

	public ListenerMethodContentAssistCalculator() {
		super(FILTER);
	}

	@Override
	protected IType calculateType(ContentAssistRequest request,
			String attributeName) {
		if (BeansEditorUtils.hasAttribute(request.getNode(), "ref")) {
			String ref = BeansEditorUtils.getAttribute(request.getNode(), "ref");
			if (ref != null) {
				IFile file = BeansEditorUtils.getFile(request);
				String className = BeansEditorUtils.getClassNameForBean(file,
						request.getNode().getOwnerDocument(), ref);
				return JdtUtils.getJavaType(file.getProject(), className);
			}
		}
		return null;
	}
}
