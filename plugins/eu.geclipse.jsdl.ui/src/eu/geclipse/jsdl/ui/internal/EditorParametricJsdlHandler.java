/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Katarzyna Bylec - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.parametric.IGeneratedJsdl;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.ui.internal.pages.sections.SweepIterationsSection;

/**
 *
 */
public class EditorParametricJsdlHandler implements IParametricJsdlHandler, IStructuredContentProvider {
  private List<List<String>> content = Collections.emptyList();
  private SweepIterationsSection section;

  /**
   * @param section
   */
  public EditorParametricJsdlHandler( final SweepIterationsSection section ) {
    this.section = section;    
  }

  public void generationFinished() throws ProblemException {
    // empty implementation
  }

  public void generationStarted( final int generatedJsdls, final List<String> paramNames )
    throws ProblemException
  {
    this.content = new ArrayList<List<String>>( generatedJsdls );
  }

  public void newJsdlGenerated( final IGeneratedJsdl generatedJsdl,
                                final IProgressMonitor monitor )
    throws ProblemException
  {
    List<String> columnNames = this.section.getLabelProvider().getColumnNames();
    List<String> values = null;
    
    for( String paramName : columnNames ) {
      if( values == null ) {
        // first column is iteration
        values = new ArrayList<String>( columnNames.size() );
        values.add( generatedJsdl.getIterationName() );
      } else {
        values.add( generatedJsdl.getParamValue( paramName ) );
      }
    }
    
    this.content.add( values );
    this.section.iterationGenerated( values );
  }

  public Object[] getElements( final Object inputElement ) {
    return this.content.toArray();
  }

  public void dispose() {
    // empty implementation
  }

  public void inputChanged( final Viewer viewer, final Object oldInput, final Object newInput ) {
    // empty implementation
  }
}
