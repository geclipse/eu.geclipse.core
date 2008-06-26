/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *    Mathias Stuempert
 *****************************************************************************/

package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import eu.geclipse.core.reporting.IConfigurableSolver;

/**
 * Solver for opening the system browser for a specified URL.
 */
public class OpenUrlSolver implements IConfigurableSolver {
  
  private static final String BROWSER_ID = "URL_SOLUTION_BROWSER_ID"; //$NON-NLS-1$
  
  private static final String URL_ATTRIBUTE = "url"; //$NON-NLS-1$
  
  private String url;

  public void solve() throws InvocationTargetException {
    try {
      URL u
        = new URL( this.url );
      IWorkbenchBrowserSupport browserSupport
        = PlatformUI.getWorkbench().getBrowserSupport();
      IWebBrowser browser
        = browserSupport.createBrowser( IWorkbenchBrowserSupport.AS_EXTERNAL, BROWSER_ID, null, null );
      browser.openURL( u );
    } catch ( MalformedURLException urlExc ) {
      throw new InvocationTargetException( urlExc );
    } catch ( PartInitException piExc ) {
      throw new InvocationTargetException( piExc );
    }
  }

  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data)
      throws CoreException {
    this.url = config.getAttribute( URL_ATTRIBUTE );
  }

}
