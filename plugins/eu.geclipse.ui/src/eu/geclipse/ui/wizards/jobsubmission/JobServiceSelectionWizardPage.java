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
 *     Pawel Wolniewicz - PSNC
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobsubmission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridProject;

public class JobServiceSelectionWizardPage extends WizardPage {

  IGridProject project;
  private List<IGridJobDescription> jobDescriptions;
  private List<IGridJobService> jobServices;

  private org.eclipse.swt.widgets.List list;

  public JobServiceSelectionWizardPage( final String pageName,
                                    final List<IGridJobDescription> _jobDescriptions)
  {
    super( pageName );
    super.setTitle( "Submit job description" );
    super.setDescription( "Choose service for submitting jobs" );
    setMessage( "Retrieving list of services, please wait a while...", WizardPage.WARNING );
    this.jobDescriptions = _jobDescriptions;
  }

  public void setServices(final List<IGridJobService> _jobServices) {
    setMessage( null );
    this.jobServices = _jobServices;
    
    Map< String, Integer > repeatedEntries = new HashMap< String, Integer >( 5 );

    for ( Iterator<IGridJobService> i = this.jobServices.iterator(); i.hasNext(); ) {
      
      IGridJobService service = i.next();
      String name = service.getName();
      Integer count = repeatedEntries.get( name );
      
      // No repeated entry yet
      if ( count == null ) {
        int index = this.list.indexOf( name );
        if ( index != -1 ) {
          // First repetition found
          repeatedEntries.put( name, 1 );
          
          // Now we update the older entry
          String oldUri = this.jobServices.get( index ).getURI().toString();
          this.list.setItem( index, name + '[' + oldUri + ']' );
          
          // And we create an appropriate name for the new entry
          name += '[' + service.getURI().toString() + ']';
        }
      } else {
        // Repeated entries present, increase the counter
        repeatedEntries.put( name, count + 1 );
        
        // And modify the label adequately
        name += '[' + service.getURI().toString() + ']';
      }
      this.list.add( name );
    }
  }
  
  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 2;
    gd.heightHint = 300;
    gd.widthHint = 250;
    this.list =  new org.eclipse.swt.widgets.List( mainComp, SWT.BORDER
                               | SWT.SINGLE
                               | SWT.H_SCROLL
                               | SWT.V_SCROLL );
    
    this.list.setLayoutData( gd );
    this.list.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        JobServiceSelectionWizardPage.this.updateButtons();
      }
    } );
    setControl( mainComp );

  }

  protected void updateButtons() {
    setPageComplete( list.getSelectionCount() > 0 );
    this.getContainer().updateButtons();
  }

  public IGridJobService getSubmissionService() {
    IGridJobService service = null;
    int index = this.list.getSelectionIndex();
    if (index != -1) {
      service = this.jobServices.get( index );
    }
    return service;
  }

//  @Override
//  public boolean canFlipToNextPage() {
//    boolean result = false;
//    if( !this.jobNameText.getText().equals( "" ) //$NON-NLS-1$
//        && ( this.getDestinationFolder() != null ) )
//    {
//      result = true;
//    }
//    if( result ) {
//      if( getWizard() instanceof JobCreatorSelectionWizard ) {
//        ( ( JobCreatorSelectionWizard )getWizard() ).changeInitData();
//      }
//    }
//    return result;
//  }

}
