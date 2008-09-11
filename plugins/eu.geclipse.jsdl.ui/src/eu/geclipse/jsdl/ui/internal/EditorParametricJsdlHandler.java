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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.ui.internal.pages.sections.SweepIterationsSection;

public class EditorParametricJsdlHandler implements IParametricJsdlHandler {

  Map<String, Properties> valuesMap = new TreeMap<String, Properties>();
  // private int maxIterations;
  // private int currentIterationNo = 0;
  private String placeHolder = "sth";
  private SweepIterationsSection section;
  private int maxParams = 0;
  private String maxParamsId;
  private int tempMaxParams = 0;

  public EditorParametricJsdlHandler( final SweepIterationsSection section ) {
    this.section = section;
  }

  public void generationFinished() throws ProblemException {
  }

  public void generationStarted( final int generatedJsdls )
    throws ProblemException
  {
    // this.maxIterations = generatedJsdls;
  }

  public void newJsdlGenerated( final Document generatedJsdl,
                                final List<Integer> iterationsStack,
                                final IProgressMonitor monitor )
    throws ProblemException
  {
    String currentIterationID = "";
    for( Integer itId : iterationsStack ) {
      currentIterationID = currentIterationID + "[" + itId.toString() + "]";
    }
    this.valuesMap.put( currentIterationID,
                        this.valuesMap.get( this.placeHolder ) );
    this.valuesMap.remove( this.placeHolder );
    if( this.tempMaxParams < this.maxParams ) {
      Properties currentProperties = this.valuesMap.get( currentIterationID );
      for( String param : this.valuesMap.get( this.maxParamsId )
        .stringPropertyNames() )
      {
        if( !currentProperties.containsKey( param ) ) {
          currentProperties.put( param, this.valuesMap.get( this.maxParamsId )
            .get( param ) );
        }
      }
    } else {
      this.maxParams = this.tempMaxParams;
      this.maxParamsId = currentIterationID;
    }
    PlatformUI.getWorkbench().getDisplay().syncExec( new Runnable(){

      public void run() {
        EditorParametricJsdlHandler.this.section.iterationGenerated( EditorParametricJsdlHandler.this.valuesMap );
      }
      
    });
    
    this.section.iterationGenerated( this.valuesMap );
    this.tempMaxParams = 0;
  }

  public void paramSubstituted( final String paramName,
                                final String newValue,
                                final IProgressMonitor monitor )
    throws ProblemException
  {
    this.tempMaxParams++;
    if( this.valuesMap.get( this.placeHolder ) == null ) {
      this.valuesMap.put( this.placeHolder, new Properties() );
    }
    if( newValue == null ) {
      this.valuesMap.get( this.placeHolder ).put( paramName, "NULL" );
    } else {
      this.valuesMap.get( this.placeHolder ).put( paramName, newValue );
    }
  }
}
