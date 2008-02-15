/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;

public class MailToSolution implements ISolution {
  
  private IProblem problem;
  
  public MailToSolution( final IProblem problem ) {
    this.problem = problem;
  }

  public String getDescription() {
    return "Send error report";
  }
  
  public String getID() {
    return null;
  }

  public boolean isActive() {
    return ( this.problem != null ) && ( this.problem.getMailTo() != null );
  }

  public void solve() {
    URL url = getMailToLink();
    if ( url != null ) {
      IWorkbenchBrowserSupport browserSupport
        = PlatformUI.getWorkbench().getBrowserSupport();
      try {
        IWebBrowser externalBrowser
          = browserSupport.getExternalBrowser();
        externalBrowser.openURL( url );
      } catch ( PartInitException piExc ) {
        Activator.logException( piExc );
      }
    }
  }
  
  private String getMailBody( final IProblem problem ) {
    
    StringBuffer buffer = new StringBuffer();
    
    buffer.append(
        "Description: " + problem.getDescription() + "%0A%0A" +
        "Plugin: " + problem.getPluginID() + "%0A%0A"
    );
    
    String[] reasons = problem.getReasons();
    if ( ( reasons != null ) && ( reasons.length > 0 ) ) {
      buffer.append( "Reasons:%0A" );
      for ( String reason : reasons ) {
        buffer.append( " - " + reason + "%0A" );
      }
      buffer.append( "%0A" );
    }
    
    ISolution[] solutions = problem.getSolutions();
    if ( ( solutions != null ) && ( solutions.length > 0 ) ) {
      buffer.append( "Solutions:%0A" );
      for ( ISolution solution : solutions ) {
        buffer.append( " - " + solution.getDescription() + "%0A" );
      }
      buffer.append( "%0A" );
    }
    
    Throwable exc = problem.getException();
    if ( exc != null ) {
      buffer.append( "Stacktrace:%0A" );
      StringWriter sWriter = new StringWriter();
      PrintWriter pWriter = new PrintWriter( sWriter );
      exc.printStackTrace( pWriter );
      pWriter.flush();
      buffer.append( sWriter.getBuffer() );
      pWriter.close();
    }
    
    String body = buffer.toString().replaceAll( "[\r\n]", "%0A" );
    
    return body;
    
  }
  
  private URL getMailToLink() {
    URL result = null;
    String mailTo = this.problem.getMailTo();
    try {
      result = new URL(
          // TODO check if the spaces etc do not need to be escaped!!
          "mailto:" + mailTo
          + "?subject=Problem Report: " + this.problem.getDescription()
          + "&body=" + getMailBody( this.problem )
        );
    } catch ( MalformedURLException murlExc ) {
      Activator.logException( murlExc );
    }
    return result;
  }

}
