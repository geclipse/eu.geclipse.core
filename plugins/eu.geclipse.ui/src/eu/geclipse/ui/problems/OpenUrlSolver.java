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
 *    Ariel Garcia      - extended for multiple URLs
 *****************************************************************************/

package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
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
  
  private static final String MULTIPLE_URL_SEPARATOR = ","; //$NON-NLS-1$
  
  private static final int BROWSER_OPEN_DELAY = 3000;

  private String[] urls;

  public void solve() throws InvocationTargetException {
    try {
      IWorkbenchBrowserSupport browserSupport
        = PlatformUI.getWorkbench().getBrowserSupport();
      IWebBrowser browser
        = browserSupport.createBrowser( IWorkbenchBrowserSupport.AS_EXTERNAL, BROWSER_ID, null, null );
      
      for ( String url : this.urls ) {
        URL u = new URL( url );
        browser.openURL( u );
        /*
         * Firefox (at least v2.x under Linux) doesn't open all the
         * URLs if the requests come with a short delay, several seconds
         * seem to be needed!
         */
        Thread.sleep( BROWSER_OPEN_DELAY );
      }
    } catch ( MalformedURLException urlExc ) {
      throw new InvocationTargetException( urlExc );
    } catch ( PartInitException piExc ) {
      throw new InvocationTargetException( piExc );
    } catch ( InterruptedException e ) {
      // Nothing to do
    }
  }

  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data)
      throws CoreException {
    
    String addresses = config.getAttribute( URL_ATTRIBUTE );
    if ( addresses != null ) {
      this.urls = addresses.split( MULTIPLE_URL_SEPARATOR );
    }
  }

}
