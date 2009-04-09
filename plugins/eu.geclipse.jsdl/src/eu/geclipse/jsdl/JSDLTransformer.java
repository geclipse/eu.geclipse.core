/******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse consortium
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
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/

package eu.geclipse.jsdl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.reporting.ReportingPlugin;
import eu.geclipse.jsdl.internal.Activator;

public class JSDLTransformer {
  public static String transformJSDL( final InputStream jsdlStream, final String targetType,
                                      final Bundle bundle, final IPath xsltFile )
    throws ProblemException {
    InputStream xsltStream;
    Writer outputWriter;
    try {
      xsltStream = FileLocator.openStream( bundle, xsltFile, false );
      javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource( jsdlStream );
      javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource( xsltStream );
      outputWriter = new StringWriter();
      javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult( outputWriter );
      javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
      javax.xml.transform.Transformer trans = null;
      trans = transFact.newTransformer( xsltSource );
      trans.transform( xmlSource, result );
    } catch( TransformerConfigurationException e ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                  Messages.getString("JSDLTransformer.canNotGetXSLTTransformer"), //$NON-NLS-1$
                                  e, Activator.PLUGIN_ID );
    } catch( TransformerException e ) {
      ProblemException pex = new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                                   Messages.getString("JSDLTransformer.canNotTransformJSDL"), //$NON-NLS-1$
                                                   e, Activator.PLUGIN_ID );
      ISolution solution = ReportingPlugin.getReportingService()
        .createSolution( Messages.getString("JSDLTransformer.checkJSDLCorrect"), null ); //$NON-NLS-1$
      pex.getProblem().addSolution( solution );
      throw pex;
    } catch( IOException e ) {
      ProblemException problemException = new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                                                e, Activator.PLUGIN_ID );
      problemException.getProblem()
        .addReason( Messages.getString("JSDLTransformer.canNotFindXSLTFile") ); //$NON-NLS-1$
      throw problemException;
    }
    try {
      if (xsltStream != null) xsltStream.close();
    } catch( IOException e ) {
      IStatus status = new org.eclipse.core.runtime.Status( IStatus.ERROR,
                                                            Activator.PLUGIN_ID,
                                                            IStatus.OK,
                                                            Messages.getString("JSDLTransformer.errorClosingStreams"), //$NON-NLS-1$
                                                            e );
      Activator.logStatus( status );
    }
    return outputWriter.toString();
  }
}
