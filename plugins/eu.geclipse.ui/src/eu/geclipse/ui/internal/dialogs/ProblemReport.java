/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.internal.dialogs;

import java.io.PrintWriter;
import java.io.StringWriter;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;

public class ProblemReport {
  
  private IProblem problem;
  
  public ProblemReport( final IProblem problem ) {
    this.problem = problem;
  }
  
  public String createReport() {
    
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter( sWriter );

    writeField( pWriter, "Plug-In", problem.getPluginID() );
    writeField( pWriter, "Contact Address", problem.getMailTo() );
    writeField( pWriter, "Description", problem.getDescription() );
    writeField( pWriter, "Reasons", problem.getReasons() );
    writeField( pWriter, "Proposed Solutions", problem.getSolutions() );
    writeField( pWriter, "Stacktrace", problem.getException() );
    
    pWriter.close();
    
    return sWriter.toString();
    
  }
  
  private void writeField( final PrintWriter writer,
                           final String header,
                           final Throwable t ) {
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter( sWriter );
    t.printStackTrace( pWriter );
    pWriter.close();
    writeField( writer, header, sWriter.toString() );
  }
  
  private void writeField( final PrintWriter writer,
                           final String header,
                           final ISolution[] solutions ) {
    String[] sols = new String[ solutions == null ? 0 : solutions.length ];
    for ( int i = 0 ; i < sols.length ; i++ ) {
      sols[i] = String.format( "%s (ID=%s)", solutions[i].getDescription(), solutions[i].getID() );
    }
    writeField( writer, header, sols ); 
  }
  
  private void writeField( final PrintWriter writer,
                           final String header,
                           final String content ) {
    writeField( writer, header, new String[] { content } );
  }
  
  private void writeField( final PrintWriter writer,
                           final String header,
                           final String[] content ) {
    writer.println( header + ":" );
    for ( String s : content ) {
      writer.println( "\t" + s );
    }
    writer.println();
  }

}
