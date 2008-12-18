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
package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ParametricJobAdapter;
import eu.geclipse.jsdl.ui.internal.pages.sections.SweepIterationsSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.SweepOrderSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.SweepValuesSection;

public class NewParametricPage extends JsdlFormPage {

  protected static final String PAGE_ID = "PARAMETRIC_NEW"; //$NON-NLS-1$
  private Composite body;
  private JobDefinitionType jobDefinitionType;
  private SweepOrderSection sweepOrderSection;
  private ParametricJobAdapter adapter;
  private SweepValuesSection sweepValuesSection;
  private SweepIterationsSection sweepIterationsSection;
  private JSDLJobDescription jsdlJobDescription;

  public NewParametricPage( final FormEditor editor ) {
    super( editor, PAGE_ID, Messages.getString( "ParametersPage_PageTitle" ) ); //$NON-NLS-1$
    
  }

  @Override
  public void setActive( final boolean active ) {
    if( active ) {
      if( isContentRefreshed() ) {
        this.sweepOrderSection.setInput( this.jobDefinitionType );
      }
    }
  }

  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    ScrolledForm form = managedForm.getForm();
    
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( "Parameters Sweep" ); //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 1 ) );
    this.sweepOrderSection = new SweepOrderSection( this.body,
                                                    toolkit,
                                                    this.adapter );
    this.sweepOrderSection.setInput( this.jobDefinitionType );
    this.sweepOrderSection.addListener( this );
    this.sweepIterationsSection = new SweepIterationsSection(this.body, toolkit, this.adapter);
    this.sweepIterationsSection.setInput( this.jobDefinitionType, this.jsdlJobDescription );
    this.sweepIterationsSection.addListener( this );
    this.sweepIterationsSection.setParentPage(this);
//    this.sweepValuesSection = new SweepValuesSection( this.body, toolkit, adapter);
//    this.sweepValuesSection.setInput(jobDefinitionType);
  }

  public void setPageContent( final JobDefinitionType jobDefinitionRoot,
                              final boolean refreshStatus,
                              final JSDLJobDescription jsdlJobDescr)
  {
    this.adapter = new ParametricJobAdapter( jobDefinitionRoot, jsdlJobDescr );
    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
    }
    this.jobDefinitionType = jobDefinitionRoot;
    this.jsdlJobDescription = jsdlJobDescr;
  }
}
