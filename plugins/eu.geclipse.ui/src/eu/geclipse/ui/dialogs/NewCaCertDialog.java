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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.dialogs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Dialog that is used by the CA certificate preference page in
 * order to import CA certificates. Currently this dialog supports
 * two ways of importing certificates, i.e. from a local directory or
 * from a remote repository. 
 */
public class NewCaCertDialog extends IconAndMessageDialog {
  
  /**
   * Source field determining that the dialog should be used to load
   * CA certificates from a local directory.
   */
  public static final int FROM_DIRECTORY = 1;
  
  /**
   * Source field determining that the dialog should be used to load
   * CA certificates from a remote repository.
   */
  public static final int FROM_REPOSITORY = 2;
  
  /**
   * The default remote repository from which to import the certificates.
   */
  private static final String DEFAULT_REPOSITORY = "http://www.eugridpma.org/distribution/igtf/current/accredited/tgz"; //$NON-NLS-1$
  
  /**
   * Combo where either the local directory of the remote
   * repository location may be edited.
   */
  protected StoredCombo resultCombo;
  
  /**
   * The source of the import, either {@link #FROM_DIRECTORY}
   * of {@link #FROM_REPOSITORY}.
   */
  private int source;
  
  /**
   * The result of this dialog. Either a local file or the URL
   * of a remote repository.
   */
  private String result;

  /**
   * Construct a new <code>NewCaCertDialog</code> that will try to import
   * the certificates from either a local directory (if {@link #FROM_DIRECTORY}
   * is specified as source) or from a remote repository (if
   * {@link #FROM_REPOSITORY} is specified as source).
   * 
   * @param source The source from which to import the certificates,
   * i.e. either {@link #FROM_DIRECTORY} or {@link #FROM_REPOSITORY}.
   * @param parentShell The parent {@link Shell} of the dialog.
   */
  public NewCaCertDialog( final int source, final Shell parentShell ) {
    super( parentShell );
    this.source = source;
    switch ( source ) {
      case FROM_DIRECTORY:
        super.message = Messages.getString( "NewCaCertDialog.from_dir_message" ); //$NON-NLS-1$
        break;
      case FROM_REPOSITORY:
        super.message = Messages.getString( "NewCaCertDialog.from_rep_message" ); //$NON-NLS-1$
        break;
    }
  }
  
  /**
   * Get the result of the dialog, i.e. either a local file (if {@link #FROM_DIRECTORY}
   * was specified as source) or the URL of a remote repository (if
   * {@link #FROM_REPOSITORY} was specified as source).
   * 
   * @return The result of this dialog or <code>null</code> if the dialog was
   * not closed with status <code>OK</code>.
   */
  public String getResult() {
    return this.result;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#okPressed()
   */
  @Override
  public void okPressed() {
    this.result = this.resultCombo.getText();
    super.okPressed();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    createMessageArea(parent);
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.horizontalSpan = 2;
    mainComp.setLayoutData( gData );
    
    switch ( this.source ) {
      case FROM_REPOSITORY:
        initFromRepository( mainComp );
        break;
      case FROM_DIRECTORY:
        initFromDirectory( mainComp );
        break;
    }
    
    return mainComp;
    
  }
  
  /**
   * Initializes this dialog for fetching certificates from a remote
   * repository.
   * 
   * @param comp The composite holding the controls of this dialog.
   */
  protected void initFromRepository( final Composite comp ) {
    
    GridData gData;
    IPreferenceStore prefStore
      = eu.geclipse.ui.internal.Activator.getDefault().getPreferenceStore();
    
    this.resultCombo = new StoredCombo( comp, SWT.BORDER ) {
      @Override
      protected boolean isValidItem( final String item ) {
        boolean isValid = true;
        try {
          new URL( item );
        } catch ( MalformedURLException muExc ) {
          isValid = false;
        }
        return isValid;
      }
    };
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.resultCombo.setLayoutData( gData );
    this.resultCombo.setPreferences( prefStore, "ca_from_repo" ); //$NON-NLS-1$
    if ( this.resultCombo.getText().length() == 0 ) {
      this.resultCombo.setText( DEFAULT_REPOSITORY );
    }
    
  }
  
  /**
   * Initializes this dialog for fetching certificates from a local
   * directory.
   * 
   * @param comp The composite holding the controls of this dialog.
   */
  protected void initFromDirectory( final Composite comp ) {
    
    GridData gData;
    IPreferenceStore prefStore
      = eu.geclipse.ui.internal.Activator.getDefault().getPreferenceStore();
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    
    this.resultCombo = new StoredCombo( comp, SWT.BORDER ) {
      @Override
      protected boolean isValidItem( final String item ) {
        File file = new File( item );
        return file.exists();
      }
    };
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.resultCombo.setLayoutData( gData );
    this.resultCombo.setPreferences( prefStore, "ca_from_dir" ); //$NON-NLS-1$SS
    
    Button dirButton = new Button( comp, SWT.PUSH );
    dirButton.setImage( fileImage );
    gData = new GridData();
    dirButton.setLayoutData( gData );
    
    dirButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        DirectoryDialog dialog = new DirectoryDialog( NewCaCertDialog.this.getShell() );
        dialog.setMessage( Messages.getString( "NewCaCertDialog.from_dir_dialog_message" ) ); //$NON-NLS-1$
        String dirname = dialog.open();
        if ( dirname != null ) {
          NewCaCertDialog.this.resultCombo.setText( dirname );
        }
      }
    } );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
   */
  @Override
  protected Image getImage() {
    return getQuestionImage();
  }
  
  /**
   * Show a dialog for selecting a file.
   * 
   * @param initial The initial path to be set on the dialog.
   * @return The chosen file.
   */
  protected String showFileDialog( final String initial ) {
    String[] filterExtensions = { "*.pem", "*.*" }; //$NON-NLS-1$ //$NON-NLS-2$
    String[] filterNames = { "PEM-Files", "All Files" }; //$NON-NLS-1$ //$NON-NLS-2$
    FileDialog fileDialog = new FileDialog( getShell(), SWT.OPEN | SWT.SINGLE );
    fileDialog.setFilterExtensions( filterExtensions );
    fileDialog.setFilterNames( filterNames );
    fileDialog.setFileName( initial );
    String selected = fileDialog.open();
    return selected;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    switch ( this.source ) {
      case FROM_DIRECTORY:
        newShell.setText( Messages.getString("NewCaCertDialog.windowTitleDirectory") ); //$NON-NLS-1$
        break;
      case FROM_REPOSITORY:
        newShell.setText( Messages.getString("NewCaCertDialog.windowTitleRepository") ); //$NON-NLS-1$
        break;
    }
  }
  
}
