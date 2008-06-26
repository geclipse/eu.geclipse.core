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

/**
 * This class creates a problem report for a specific {@link IProblem}.
 */
public class ProblemReport {
  
  private static final String PLUG_IN_HEADER = "Plug-In"; //$NON-NLS-1$
  
  private static final String CONTACT_ADDRESS_HEADER = "Contact Address"; //$NON-NLS-1$

  private static final String DESCRIPTION_HEADER = "Description"; //$NON-NLS-1$

  private static final String REASONS_HEADER = "Reasons"; //$NON-NLS-1$

  private static final String SOLUTIONS_HEADER = "Proposed Solutions"; //$NON-NLS-1$

  private static final String STACKTRACE_HEADER = "Stacktrace"; //$NON-NLS-1$
  
  private static final String HEADER_PREFIX = ":"; //$NON-NLS-1$
  
  private static final String FIELD_PREFIX = "\t"; //$NON-NLS-1$
  
  private static final String SOLUTION_FORMAT = "%s (ID=%s)"; //$NON-NLS-1$
  
  private static final String NA_STRING = "N/A"; //$NON-NLS-1$
  
  /**
   * The problem for which to create a report.
   */
  private IProblem problem;
  
  /**
   * Create a new problem report for the specified {@link IProblem}.
   * 
   * @param problem The problem for which to create the report.
   */
  public ProblemReport( final IProblem problem ) {
    this.problem = problem;
  }
  
  /**
   * Create and return the report.
   * 
   * @return The created report as {@link String}.
   */
  public String createReport() {
    
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter( sWriter );

    writeField( pWriter, PLUG_IN_HEADER, this.problem.getPluginID() );
    writeField( pWriter, CONTACT_ADDRESS_HEADER, this.problem.getMailTo() );
    writeField( pWriter, DESCRIPTION_HEADER, this.problem.getDescription() );
    writeField( pWriter, REASONS_HEADER, this.problem.getReasons() );
    writeField( pWriter, SOLUTIONS_HEADER, this.problem.getSolutions() );
    writeField( pWriter, STACKTRACE_HEADER, this.problem.getException() );
    
    pWriter.close();
    
    return sWriter.toString();
    
  }
  
  /**
   * Write the specified {@link Throwable} to the specified {@link PrintWriter}.
   * 
   * @param writer The {@link PrintWriter} where the field should be written to.
   * @param header The header used to specify the field.
   * @param t The {@link Throwable} to be written.
   */
  private void writeField( final PrintWriter writer,
                           final String header,
                           final Throwable t ) {
    if ( t == null ) {
      writeField( writer, header, NA_STRING );
    } else {
      StringWriter sWriter = new StringWriter();
      PrintWriter pWriter = new PrintWriter( sWriter );
      t.printStackTrace( pWriter );
      pWriter.close();
      writeField( writer, header, sWriter.toString() );
    }
  }
  
  /**
   * Write the specified {@link ISolution}s to the specified {@link PrintWriter}.
   * 
   * @param writer The {@link PrintWriter} where the field should be written to.
   * @param header The header used to specify the field.
   * @param solutions The {@link ISolution}s to be written.
   */
  @SuppressWarnings("null")
  private void writeField( final PrintWriter writer,
                           final String header,
                           final ISolution[] solutions ) {
    if ( ( solutions == null ) || ( solutions.length == 0 ) ) {
      writeField( writer, header, NA_STRING );
    } else {
      String[] sols = new String[ solutions.length ];
      for ( int i = 0 ; i < sols.length ; i++ ) {
        sols[i] = String.format( SOLUTION_FORMAT, solutions[i].getDescription(), solutions[i].getID() );
      }
      writeField( writer, header, sols );
    }
  }
  
  /**
   * Write the specified {@link String} to the specified {@link PrintWriter}.
   * 
   * @param writer The {@link PrintWriter} where the field should be written to.
   * @param header The header used to specify the field.
   * @param content The {@link String} to be written.
   */
  private void writeField( final PrintWriter writer,
                           final String header,
                           final String content ) {
    if ( ( content == null ) || ( content.length() == 0 ) ) {
      writeField( writer, header, NA_STRING );
    } else {
      writeField( writer, header, new String[] { content } );
    }
  }
  
  /**
   * Write the specified {@link String}s to the specified {@link PrintWriter}.
   * 
   * @param writer The {@link PrintWriter} where the field should be written to.
   * @param header The header used to specify the field.
   * @param content The {@link String}s to be written.
   */
  private void writeField( final PrintWriter writer,
                           final String header,
                           final String[] content ) {
    if ( ( content == null ) || ( content.length == 0 ) ) {
      writeField( writer, header, NA_STRING );
    } else {
      writer.println( header + HEADER_PREFIX );
      for ( String s : content ) {
        writer.println( FIELD_PREFIX + s );
      }
      writer.println();
    }
  }

}
