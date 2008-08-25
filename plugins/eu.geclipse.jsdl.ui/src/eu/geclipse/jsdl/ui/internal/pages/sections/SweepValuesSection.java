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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ParametricJobAdapter;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;


public class SweepValuesSection extends JsdlFormPageSection {
  
  
  private ParametricJobAdapter adapter;
  private List<SweepType> sweepType;
  private JobDefinitionType jobDefinitionType;
  private JobDescriptionType jobDescriptionType;
  private List<SweepType> inerSweepList;
  private Combo sweepListCombo;
  private Shell shell;

  public SweepValuesSection(final Composite parent,
                            final FormToolkit toolkit,
                            final ParametricJobAdapter adapter){
    this.adapter = adapter;
    createSection( parent, toolkit );
  }

  private void createSection( final Composite parent, final FormToolkit toolkit ) {
    this.shell = parent.getShell();
    String sectionTitle = "Sweep values";
    String sectionDescription = "Specify the values of parameters";
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   1 );
    this.sweepListCombo = new Combo(client, SWT.NONE);
  }

  public void setInput( JobDefinitionType jobDefinition ) {
    this.adapterRefreshed = true;
    this.sweepType = new ArrayList<SweepType>();
    if( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
      TreeIterator<EObject> iterator = this.jobDefinitionType.eAllContents();
      while( iterator.hasNext() ) {
        EObject testType = iterator.next();
        if( testType instanceof JobDescriptionType ) {
          this.jobDescriptionType = ( JobDescriptionType )testType;
        } else if( testType instanceof SweepType
                   && !( testType.eContainer() instanceof SweepType ) )
        {
          SweepType type = ( ( SweepType )testType );
          type.eAdapters().add( this );
          this.sweepType.add( type );
        }
      }
      // check all root sweeps' children
      this.inerSweepList = new ArrayList<SweepType>();
      if( !this.sweepType.isEmpty() ) {
        for( SweepType type : this.sweepType ) {
          TreeIterator<EObject> it = type.eAllContents();
          while( it.hasNext() ) {
            EObject testObject = it.next();
            if( testObject instanceof SweepType ) {
              this.inerSweepList.add( ( SweepType )testObject );
            }
          }
        }
        this.inerSweepList.addAll( this.sweepType );
      }
      fillFields();
    }
    
  }
  
  private void fillFields() {
    if( this.sweepListCombo != null ) {
      this.sweepListCombo.removeAll();
      for (String name: getInerSweepNames()){
        this.sweepListCombo.add( name );
      }
    }
  }
  
  private List<String> getInerSweepNames() {
    List<String> result = new ArrayList<String>();
    for( SweepType sweep : this.inerSweepList ) {
      EList list = sweep.getAssignment();
      for( int i = 0; i < list.size(); i++ ) {
        Object el = list.get( i );
        if( el instanceof AssignmentType ) {
          AssignmentType assignment = ( AssignmentType )el;
          EList paramList = assignment.getParameter();
          for( int j = 0; j < paramList.size(); j++ ) {
            result.add( ( String )paramList.get( j ) );
          }
        }
      }
    }
    return result;
  }
  
}
