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

import java.net.URI;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.ui.widgets.GridConnectionDefinitionComposite;

public class ConnectionDefinitionWizardPage extends WizardPage {
  
  private GridConnectionDefinitionComposite connectionDefinitionComp;
    
  public ConnectionDefinitionWizardPage() {
    super( Messages.getString("ConnectionDefinitionWizardPage.name"), //$NON-NLS-1$
           Messages.getString("ConnectionDefinitionWizardPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("ConnectionDefinitionWizardPage.description") ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    this.connectionDefinitionComp = new GridConnectionDefinitionComposite( parent, SWT.NULL );
    this.connectionDefinitionComp.addModifyListener( new ModifyListener () {
      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }
    } );
    setControl( this.connectionDefinitionComp );
  }
  
  public URI getURI() {
    return this.connectionDefinitionComp.getURI();
  }
  
  protected void validatePage() {
    URI uri = getURI();
    boolean valid = this.connectionDefinitionComp.isValid();
    setPageComplete( valid && ( uri != null ) );
    String errorMessage = this.connectionDefinitionComp.getErrorMessage();
    setErrorMessage( errorMessage );
  }
   
}
