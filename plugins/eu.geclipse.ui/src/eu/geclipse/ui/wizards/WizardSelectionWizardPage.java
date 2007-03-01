/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

class WizardSelectionWizardPage<InitDataType> extends WizardPage {
  INewWizard<InitDataType> wizard;
  private WizardSelectionComposite<InitDataType> composite;
  private String extPointId;
  private String title;
  private String desc;

  WizardSelectionWizardPage( final String pageName,
                                    final String extensionPointId,
                                    final String title,
                                    final String desc ) {
    super( pageName );
    this.extPointId = extensionPointId;
    this.title = title;
    this.desc = desc;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    this.composite = new WizardSelectionComposite<InitDataType>( parent, SWT.NONE );
    setControl( this.composite );
    this.composite.addSelectionChangedListener( new ISelectionChangedListener() {
      @SuppressWarnings("unchecked")
      public void selectionChanged( final SelectionChangedEvent event ) {
        Object obj = ((StructuredSelection)event.getSelection()).getFirstElement();
        if ( obj instanceof INewWizard ) {
          WizardSelectionWizardPage.this.wizard = ( INewWizard<InitDataType> ) obj;
        }
        getWizard().getContainer().updateButtons();
      }
    } );
    this.composite.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetDefaultSelected( final SelectionEvent event ) {
        getWizard().getContainer().showPage( getNextPage() );
      }
    } ) ;
    this.composite.fillWizardList( this.extPointId );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#getTitle()
   */
  @Override
  public String getTitle() {
    return this.title; 
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#getDescription()
   */
  @Override
  public String getDescription() {
    return this.desc;
  }
  
  /**
   * Returns the wizard selected by the user.
   * @return the wizard which should be used for the following wizard pages.
   */
  public INewWizard<InitDataType> getNewTerminalWizard() {
    return this.wizard;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizardPage#canFlipToNextPage()
   */
  @Override
  public boolean canFlipToNextPage() {
    return this.wizard != null;
  }
}
