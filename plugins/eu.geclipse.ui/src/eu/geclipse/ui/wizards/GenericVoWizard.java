/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.wizards;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IVoManager;
import eu.geclipse.core.model.impl.GenericVirtualOrganization;
import eu.geclipse.core.model.impl.GenericVoCreator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.IInitializableWizard;


public class GenericVoWizard
    extends Wizard
    implements IInitializableWizard {
  
  private GenericVirtualOrganization initialVo;
  
  private GenericVoWizardPage voPage;
  
  private VoServiceSelectionPage servicePage;
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    
    this.voPage = new GenericVoWizardPage();
    this.servicePage = new VoServiceSelectionPage();
    
    if ( this.initialVo != null ) {
      this.voPage.setInitialVo( this.initialVo );
      this.servicePage.setInitialVo( this.initialVo );
    }
    
    addPage( this.voPage );
    addPage( this.servicePage );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return "Generic VO";
  }
  
  public boolean init( final Object initialData ) {
    boolean result = false;
    if ( initialData instanceof GenericVirtualOrganization ) {
      this.initialVo = ( GenericVirtualOrganization ) initialData;
      result = true;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    
    GenericVoCreator creator = new GenericVoCreator();
    
    IStatus result = this.voPage.apply( creator );
    
    if ( result.isOK() ) {
      result = this.servicePage.apply( creator );
    }
    
    if ( result.isOK() ) {
      result = createVo( creator );
    }
    
    if ( ! result.isOK() ) {
      ( ( WizardPage ) getContainer().getCurrentPage() ).setErrorMessage( result.getMessage() );
    }
    
    return result.isOK();
    
  }
  
  private IStatus createVo( final GenericVoCreator creator ) {
    
    IStatus result = Status.OK_STATUS;
    
    GenericVirtualOrganization vo = null;
    IVoManager manager = GridModel.getVoManager();
    
    try {
      if ( this.initialVo != null ) {
        creator.apply( this.initialVo );
      } else {
        vo = ( GenericVirtualOrganization ) manager.create( creator );
      }
    } catch ( ProblemException pExc ) {
      result = new Status( IStatus.ERROR, Activator.PLUGIN_ID, pExc.getLocalizedMessage(), pExc );
    }
    
    if ( ! result.isOK() && ( vo != null ) ) {
      try {
        manager.delete( vo );
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
    }
    
    return result;
    
  }
  
}
