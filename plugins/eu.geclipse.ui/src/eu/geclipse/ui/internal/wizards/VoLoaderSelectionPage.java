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
import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IVoLoader;
import eu.geclipse.ui.internal.Activator;

/**
 * This wizard page is used by the {@link VoImportWizard} in order to
 * chose an available VO loader.
 */
public class VoLoaderSelectionPage extends WizardPage {
  
  protected List list;
  
  private java.util.List< IConfigurationElement > loaders;
  
  private Hashtable< String, IVoLoader > formerlySelectedLoaders
    = new Hashtable< String, IVoLoader >();
  
  /**
   * Standard constructor.
   */
  public VoLoaderSelectionPage() {
    super( "voLoaderPage", //$NON-NLS-1$
           Messages.getString("VoLoaderSelectionPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("VoLoaderSelectionPage.description") ); //$NON-NLS-1$
  }
  
  public int initVoLoaderList() {
    ExtensionManager manager = new ExtensionManager();
    this.loaders = new ArrayList< IConfigurationElement >();
    this.loaders.addAll(
      manager.getConfigurationElements(
          Extensions.VO_LOADER_POINT, 
          Extensions.VO_LOADER_ELEMENT
      )
    );
    return this.loaders.size();
  }

  public java.util.List< IConfigurationElement > getAvailableLoaders() {
    if ( this.loaders == null ) {
      initVoLoaderList();
    }
    return this.loaders;
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
        setPageComplete( VoLoaderSelectionPage.this.list.getSelectionCount() > 0 );
      }
    } );
    
    setControl( mainComp );
    
    populateList();
    setPageComplete( false );
    
  }
  
  /**
   * Get the currently selected VO loader or <code>null</code>
   * if no loader is selected.
   * 
   * @return The currently selected VO loader.
   */
  public IVoLoader getSelectedLoader() {
    
    IVoLoader result = null;
    
    String[] selection = null;
    if ( this.loaders.size() > 0 ) {
      selection = new String[] {
          this.loaders.get( 0 ).getAttribute( Extensions.VO_LOADER_NAME_ATTRIBUTE )
      };
    }
    
    if ( this.list != null ) { 
      selection = this.list.getSelection();
    }
    
    if ( ( selection != null ) && ( selection.length > 0 ) ) {
      
      result = this.formerlySelectedLoaders.get( selection[ 0 ] );
      
      if ( result == null ) {
      
        for ( IConfigurationElement element : this.loaders ) {
          String name = element.getAttribute( Extensions.VO_LOADER_NAME_ATTRIBUTE );
          if ( selection[ 0 ].equals( name ) ) {
            try {
              result = ( IVoLoader ) element.createExecutableExtension( Extensions.VO_LOADER_CLASS_ATTRIBUTE );
              this.formerlySelectedLoaders.put( selection[ 0 ], result );
            } catch ( CoreException cExc ) {
              Activator.logException( cExc );
            }
            break;
          }
        }
        
      }
      
    }
    
    return result;
    
  }
  
  protected void populateList() {
    
    this.list.removeAll();
  
    if ( this.loaders == null ) {
      initVoLoaderList();
    }

    for ( IConfigurationElement element : this.loaders ) {
      String name = element.getAttribute( Extensions.VO_LOADER_NAME_ATTRIBUTE );
      this.list.add( name );
    }

  }

}
