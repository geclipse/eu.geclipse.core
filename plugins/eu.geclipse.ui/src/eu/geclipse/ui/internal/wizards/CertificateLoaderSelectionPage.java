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

package eu.geclipse.ui.internal.wizards;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.ui.internal.Activator;

/**
 * This wizard page is used by the {@link CaCertificateImportWizard} in order to
 * chose an available VO loader.
 */
public class CertificateLoaderSelectionPage extends WizardPage {
  
  protected List list;
  
  private CaCertificateImportWizard.ImportMethod importMethod;
  
  private java.util.List< IConfigurationElement > loaders;
  
  /**
   * Construct a new <code>CertificateLoaderSelectionPage</code>
   * that shows all available loader for the selected import
   * method.
   * 
   * @param importMethod Either <code>LOCAL</code> or <code>REMOTE</code>.
   */
  public CertificateLoaderSelectionPage( final CaCertificateImportWizard.ImportMethod importMethod ) {
    super( "certLoaderPage", //$NON-NLS-1$
           Messages.getString("CertificateLoaderSelectionPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("CertificateLoaderSelectionPage.description") ); //$NON-NLS-1$
    this.importMethod = importMethod;
  }
  
  public int initCertificateLoaders() {
    ExtensionManager manager = new ExtensionManager();
    this.loaders = new ArrayList< IConfigurationElement >();
    this.loaders.addAll(
      manager.getConfigurationElements(
          Extensions.CA_CERT_LOADER_POINT, 
          Extensions.CA_CERT_LOADER_ELEMENT
      )
    );
    return this.loaders.size();
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NULL );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    mainComp.setLayoutData( gData );
    
    this.list = new List( mainComp, SWT.BORDER | SWT.SINGLE );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    this.list.setLayoutData( gData );
    
    this.list.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        setPageComplete( CertificateLoaderSelectionPage.this.list.getSelectionCount() > 0 );
      }
    } );
    this.list.addMouseListener( new MouseAdapter() {
      @SuppressWarnings("synthetic-access")
      @Override
      public void mouseDoubleClick( final MouseEvent e ) {
        setPageComplete( CertificateLoaderSelectionPage.this.list.getSelectionCount() > 0 );
        IWizardPage nextPage = CertificateLoaderSelectionPage.this.getNextPage();
        IWizardContainer container = CertificateLoaderSelectionPage.this.getContainer();
        container.showPage( nextPage );
      }
    } );
    
    setControl( mainComp );
    
    populateList();
    setPageComplete( false );
    
  }
  
  /**
   * Get the currently selected certificate loader or <code>null</code>
   * if no loader is selected.
   * 
   * @return The currently selected certificate loader.
   */
  public ICaCertificateLoader getSelectedLoader() {
    
    ICaCertificateLoader result = null;
    
    String[] selection = new String[] {
        this.loaders.get( 0 ).getAttribute( Extensions.CA_CERT_LOADER_NAME_ATTRIBUTE )
    };
    
    if ( this.list != null ) {
      selection = this.list.getSelection();
    }
    
    if ( ( selection != null ) && ( selection.length > 0 ) ) {
      
      for ( IConfigurationElement element : this.loaders ) {
        String name = element.getAttribute( Extensions.CA_CERT_LOADER_NAME_ATTRIBUTE );
        if ( selection[ 0 ].equals( name ) ) {
          try {
            result = ( ICaCertificateLoader ) element.createExecutableExtension( Extensions.CA_CERT_LOADER_CLASS_ATTRIBUTE );
          } catch ( CoreException cExc ) {
            Activator.logException( cExc );
          }
          break;
        }
      }
      
    }
    
    return result;
    
  }
  
  protected void populateList() {
    
    this.list.removeAll();
    
    if ( this.loaders == null ) {
      initCertificateLoaders();
    }

    for ( IConfigurationElement element : this.loaders ) {
      
      boolean methodSupported = false;
      
      if ( this.importMethod == CaCertificateImportWizard.ImportMethod.LOCAL ) {
        String fromLocal = element.getAttribute( Extensions.CA_CERT_LOADER_FROM_LOCAL_ATTRIBUTE );
        methodSupported = Boolean.parseBoolean( fromLocal );
      } else if ( this.importMethod == CaCertificateImportWizard.ImportMethod.REMOTE ) {
        String fromRemote = element.getAttribute( Extensions.CA_CERT_LOADER_FROM_REMOTE_ATTRIBUTE );
        methodSupported = Boolean.parseBoolean( fromRemote );
      }
      
      if ( methodSupported ) {
        String name = element.getAttribute( Extensions.CA_CERT_LOADER_NAME_ATTRIBUTE );
        this.list.add( name );
      }
      
    }
    
  }
  
}
