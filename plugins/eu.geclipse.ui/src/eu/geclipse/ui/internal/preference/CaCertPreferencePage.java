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
 *    Ariel Garcia      - handle errors when downloading CAs
 *****************************************************************************/

package eu.geclipse.ui.internal.preference;

import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.core.auth.CaCertManager;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ISecurityManager;
import eu.geclipse.core.auth.ISecurityManagerListener;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.wizards.CaCertificateImportWizard;
import eu.geclipse.ui.internal.wizards.CertificateChooserPage;


/**
 * Preference page to import and manage CA certificates. 
 */
public class CaCertPreferencePage
    extends PreferencePage
    implements IWorkbenchPreferencePage, ISecurityManagerListener {
  
  /**
   * The list containing all currently available CA certificates.
   */
  protected List caList;
  
  /**
   * Button to delete the selected certificates.
   */
  protected Button deleteButton;
  
  /**
   * Button to add certificates from a local directory.
   */
  private Button addFromDirButton;
  
  /**
   * Button to add certificates from a remote repository.
   */
  private Button addFromRepoButton;
  
  /**
   * Construct a new CA certificate preference page.
   */
  public CaCertPreferencePage() {
    super();
    setDescription( Messages.getString( "CaCertPreferencePage.description" ) ); //$NON-NLS-1$
    CaCertManager.getManager().addListener( this );
  }
  
  public void contentChanged( final ISecurityManager manager ) {
    getControl().getDisplay().asyncExec( new Runnable() {
      public void run() {
        updateCaList();
      }
    } );
  }
  
  @Override
  public void dispose() {
    CaCertManager.getManager().removeListener( this );
    super.dispose();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    
    GridLayout layout= new GridLayout( 2, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout( layout );
    
    GridData gData;
    
    Label listLabel = new Label( mainComp, SWT.NONE );
    listLabel.setText( Messages.getString( "CaCertPreferencePage.list_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    listLabel.setLayoutData( gData );
    
    this.caList = new List( mainComp, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 100;
    gData.heightHint = 150;
    this.caList.setLayoutData( gData );
    
    Composite buttons = new Composite( mainComp, SWT.NULL );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    gData.horizontalSpan = 1;
    buttons.setLayoutData( gData );
    layout = new GridLayout( 1, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    buttons.setLayout( layout );
    
    this.addFromRepoButton = new Button( buttons, SWT.PUSH );
    this.addFromRepoButton.setText( Messages.getString( "CaCertPreferencePage.add_rep_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addFromRepoButton.setLayoutData( gData );
    
    this.addFromDirButton = new Button( buttons, SWT.PUSH );
    this.addFromDirButton.setText( Messages.getString( "CaCertPreferencePage.add_dir_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addFromDirButton.setLayoutData( gData );
    
    Label separator = new Label(buttons, SWT.NONE);
    separator.setVisible(false);
    gData = new GridData();
    gData.horizontalAlignment= GridData.FILL;
    gData.heightHint= 4;
    separator.setLayoutData(gData);

    this.deleteButton = new Button( buttons, SWT.PUSH );
    this.deleteButton.setText( Messages.getString( "CaCertPreferencePage.delete_button" ) ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.deleteButton.setLayoutData( gData );
    this.deleteButton.setEnabled( false );
    
    this.caList.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        int sCnt = CaCertPreferencePage.this.caList.getSelectionCount();
        CaCertPreferencePage.this.deleteButton.setEnabled( sCnt > 0 );
      }
    } );
    this.caList.addKeyListener( new KeyAdapter() {
      @Override
      public void keyReleased( final KeyEvent e ) {
        if ( e.keyCode == SWT.DEL ) {
          deleteSelected();
        }
      }
    } );
    this.addFromDirButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addFromDirectory();
      }
    } );
    this.addFromRepoButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        addFromRepository();
      }
    } );
    this.deleteButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        deleteSelected();
      }
    } );
    
    updateCaList();
    
    return mainComp;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }
  
  /**
   * Synchronize the UI list with the {@link CaCertManager}.
   */
  public void updateCaList() {
    this.caList.removeAll();
    CaCertManager manager = CaCertManager.getManager();
    ICaCertificate[] certs = manager.getCertificates();
    Arrays.sort( certs, new Comparator< ICaCertificate >() {
      public int compare( final ICaCertificate c1, final ICaCertificate c2 ) {
        return c1.getID().compareTo( c2.getID() );
      }
    } );
    for ( ICaCertificate cert : certs ) {
      this.caList.add( cert.getID() );
    }
    this.deleteButton.setEnabled( this.caList.getSelectionCount() > 0 );
  }
  
  /**
   * Triggered by the corresponding button in the UI. Adds certificates from
   * a local directory.
   */
  protected void addFromDirectory() {
    addCertificates( CaCertificateImportWizard.ImportMethod.LOCAL );
  }
  
  /**
   * Triggered by the corresponding button in the UI. Adds certificates from
   * a remote repository.
   */
  protected void addFromRepository() {
    addCertificates( CaCertificateImportWizard.ImportMethod.REMOTE );
  }
  
  /**
   * Delete the currently selected certificates from the certificate manager.
   * 
   * @see CaCertManager#deleteCertificate(String)
   */
  protected void deleteSelected() {
    final String[] selected = this.caList.getSelection();
    if ( selected.length > 0 ) {
      boolean confirm = MessageDialog.openConfirm( getShell(),
                                                   Messages.getString( "CaCertPreferencePage.confirm_delete_title" ), //$NON-NLS-1$
                                                   Messages.getString( "CaCertPreferencePage.confirm_delete_message" ) ); //$NON-NLS-1$
      if ( confirm ) {
        final CaCertManager manager = CaCertManager.getManager();
        manager.deleteCertificates( selected );
      }
    }
  }
  
  private void addCertificates( final CaCertificateImportWizard.ImportMethod importMethod ) {
    CaCertificateImportWizard wizard
      = new CaCertificateImportWizard( importMethod );
    WizardDialog dialog = new WizardDialog( getShell(), wizard );
    
    // Add a listener which moves back the wizard page if there is an error
    dialog.addPageChangedListener( new CertPageChangedListener() );
    dialog.open();
  }
  
  /**
   * This listener checks if the CA-certificates download was successful,
   * and jumps the wizard page back if not.
   * 
   * @see org.eclipse.jface.dialogs.IPageChangedListener
   */
  class CertPageChangedListener implements IPageChangedListener {
    
    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IPageChangedListener#pageChanged(org.eclipse.jface.dialogs.PageChangedEvent)
     */
    public void pageChanged( final PageChangedEvent event ) {
      
      Object cPage = event.getSelectedPage();
      CertificateChooserPage currentPage = null;
      
      if ( cPage instanceof CertificateChooserPage ) {
        currentPage = ( CertificateChooserPage ) cPage;
      } else {
        // Not on the CertificateChooserPage yet, do nothing
        return;
      }
      
      /*
       * Let the CertificateChooserPage fetch the certificates and decide
       * if the page should be jumped back or not.
       */
      if ( ! currentPage.loadCertificateList() ) {
        IWizardContainer container = currentPage.getWizard().getContainer();
        if ( container != null ) {
          IWizardPage previousPage = currentPage.getPreviousPage();
          IWizardPage prePreviousPage = previousPage.getPreviousPage();
          container.showPage( previousPage );
          previousPage.setPreviousPage( prePreviousPage );
        }
      }
    }
  }

}
