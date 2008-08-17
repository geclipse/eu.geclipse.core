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
 *    Ariel Garcia      - modified to work for any Throwable
 *****************************************************************************/

package eu.geclipse.ui.internal.dialogs;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This class creates a problem report for a given {@link Throwable}.
 */
public class ProblemReport {
  
  private static final String PLUG_IN_HEADER = "Plug-In"; //$NON-NLS-1$
  
  private static final String CONTACT_ADDRESS_HEADER = "Contact Address"; //$NON-NLS-1$

  private static final String DESCRIPTION_HEADER = "Description"; //$NON-NLS-1$

  private static final String REASONS_HEADER = "Reasons"; //$NON-NLS-1$

  private static final String SOLUTIONS_HEADER = "Proposed Solutions"; //$NON-NLS-1$

  private static final String STACKTRACE_HEADER = "Stacktrace"; //$NON-NLS-1$

  private static final String SEVERITY_HEADER = "Severity"; //$NON-NLS-1$
  
  private static final String THROWABLE_DESCRIPTION_TEXT = "Caught an exception: "; //$NON-NLS-1$
  
  private static final String HEADER_PREFIX = ":"; //$NON-NLS-1$
  
  private static final String FIELD_PREFIX = "\t"; //$NON-NLS-1$
  
  private static final String SOLUTION_FORMAT = "%s (ID=%s)"; //$NON-NLS-1$
  
  private static final String NA_STRING = "N/A"; //$NON-NLS-1$
  
  /**
   * The throwable for which to create a report.
   */
  private Throwable exc;
  
  /**
   * Create a new problem report for the given {@link Throwable}.
   * 
   * @param throwable The throwable for which to create the report.
   */
  public ProblemReport( final Throwable throwable ) {
    this.exc = throwable;
  }
  
  /**
   * Create and return the report.
   * 
   * @return The created report as {@link String}.
   */
  public String createReport() {
    
    IProblem problem = null;
    IStatus status = null;
    
    if ( this.exc instanceof ProblemException ) {
      problem = ( ( ProblemException ) this.exc ).getProblem();
    } else if ( this.exc instanceof CoreException ) {
      status = ( ( CoreException ) this.exc ).getStatus();
    }
    
    StringWriter sWriter = new StringWriter();
    PrintWriter pWriter = new PrintWriter( sWriter );

    if ( problem != null ) {
      // A ProblemException
      writeField( pWriter, PLUG_IN_HEADER, problem.getPluginID() );
      writeField( pWriter, CONTACT_ADDRESS_HEADER, problem.getMailTo() );
      writeField( pWriter, DESCRIPTION_HEADER, problem.getDescription() );
      writeField( pWriter, REASONS_HEADER, problem.getReasons() );
      writeField( pWriter, SOLUTIONS_HEADER, problem.getSolutions() );
    } else if ( status != null ) {
      // A CoreException
      writeField( pWriter, PLUG_IN_HEADER, status.getPlugin() );
      writeField( pWriter, DESCRIPTION_HEADER, status.getMessage() );
      writeField( pWriter, SEVERITY_HEADER, severity( status.getSeverity() ) );
    } else {
      // Any other Throwable
      writeField( pWriter,
                  DESCRIPTION_HEADER,
                  THROWABLE_DESCRIPTION_TEXT + this.exc.getClass().getName() );
    }
    writeField( pWriter, STACKTRACE_HEADER, this.exc );
    
    pWriter.close();
    
    return sWriter.toString();
    
  }
  
  /**
   * Converts the integer severity value carried by a CoreException to a string.
   * 
   * @param severity one of {@link IStatus.OK}, {@link IStatus.INFO},
   *        {@link IStatus.WARNING}, {@link IStatus.ERROR}, {@link IStatus.CANCEL}.
   * @return a string describing the severity.
   */
  private String severity( final int severity ) {
    String sSeverity = NA_STRING;
    
    switch ( severity ) {
      case IStatus.OK :
        sSeverity = "OK"; //$NON-NLS-1$
        break;
      case IStatus.INFO :
        sSeverity = "INFO"; //$NON-NLS-1$
        break;
      case IStatus.WARNING :
        sSeverity = "WARNING"; //$NON-NLS-1$
        break;
      case IStatus.ERROR :
        sSeverity = "ERROR"; //$NON-NLS-1$
        break;
      case IStatus.CANCEL :
        sSeverity = "CANCEL"; //$NON-NLS-1$
        break;
      default:
        sSeverity = NA_STRING;
    }
    
    return sSeverity;
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
