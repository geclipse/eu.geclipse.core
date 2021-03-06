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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;

/**
 * @author nloulloud
 * 
 * This class is responsible for displaying the General Information section in the 
 * Job Definition Page of the JSDL editor. It provides widgets to manipulate the 
 * JobDefinitionType element specified in the "Job Structure Elements" section of 
 * the Job Submission Description Language (JSDL) Specification, Version 1.0.
 *
 */
public class JobDefinitionSection extends JsdlFormPageSection {
  
  protected JobDefinitionType jobDefinitionType;
  protected Text txtId = null;
  protected Label lblJobId = null;
  
  /**
   * Constructs a new <code> JobDefinitionSection </code> for the JSDLEditor. This section allows to manipulate
   * the <b>Job Definition </b> element through the Job Definition Page of the JSDL editor. 
   * 
   * @param parent The parent composite.
   * @param toolkit The parent Form Toolkit.
   */
  public JobDefinitionSection( final Composite parent, final FormToolkit toolkit ){
    
     createSection( parent, toolkit );
  }
  
  
  
  private void createSection( final Composite parent, final FormToolkit toolkit )  {
    
    String sectionTitle =  Messages.getString("OverviewPage_GeneralInfoTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString( "JobDefinitionPage_JobDefinitionDescr" );   //$NON-NLS-1$
        
    
    GridData gd;
    
    
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   2 );
  
    this.lblJobId = toolkit.createLabel( client,
                       Messages.getString( "JobDefinitionPage_JobDefinitionId" ) ); //$NON-NLS-1$
    
    this.txtId = toolkit.createText( client, "", SWT.NONE );     //$NON-NLS-1$
    this.txtId.addModifyListener( new ModifyListener(){

      public void modifyText( final ModifyEvent e ) {        
        
        if ( !JobDefinitionSection.this.txtId.getText().equals( EMPTY_STRING ) ){
                            
          JobDefinitionSection.this.jobDefinitionType.setId( JobDefinitionSection.this.txtId.getText() );
        }
        else{
          
          JobDefinitionSection.this.jobDefinitionType.setId( null );
        }
        contentChanged();
        
      }
      
    });
 
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.widthHint = 300;
    this.txtId.setLayoutData( gd );
    
    
    /* Paint Flat Borders */    
    toolkit.paintBordersFor( client );
    
  }
  
  
//  protected void contentChanged() {
//    
//    if ( this.isNotifyAllowed ) { 
//      
//      fireNotifyChanged( null);
//    }
//    
//  } // End void contentChanged()
  
  
  /**
   * @param jobDefinition The JSDL Job Definition element. 
   */
  public void setInput( final JobDefinitionType jobDefinition ) { 

    this.adapterRefreshed = true;
        
    if ( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
    }
    fillFields();
    
  }
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
 
    if (null != this.jobDefinitionType.getId()){
      this.txtId.setText( this.jobDefinitionType.getId() );
    }else{
      this.txtId.setText( EMPTY_STRING );
    }
    
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
       
} // End Class
