/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import eu.geclipse.core.ExtensionManager;

/**
 * This is the very first page of the {@link ExtensionWizard}. It gives the
 * user the possibility to chose an extension of the specified extension
 * point. If an initial selection is defined this page will not show up but
 * the first page specified to the specified selection will show up instead.
 * 
 * @author stuempert-m
 */
public class ExtensionWizardFirstPage extends WizardPage {
  
  /**
   * The initially selected extension.
   */
  private String initiallySelectedExtension;
  
  /**
   * The label of the combo.
   */
  private Label selectedExtensionLabel;
  
  /**
   * The text of the selectedExtensionLabel.
   */
  private String selectedExtensionLabelText;
  
  /**
   * The Combo that is used to select the extension.
   */
  private Combo selectedExtensionCombo;
  
  /**
   * The hashtable that holds the page managers of the extensions.
   */
  private Hashtable< String, IExtensionWizardPageManager > pageManagers;
  
  private Object initialData;
  
  /**
   * Construct a new <code>ExtensionWizardFirstPage</code> for the 
   * specified extension point.
   * 
   * @param extensionPointID The id of the extension point.
   * @param configurationElementName The name of the configuration element
   * of the specified extension point.
   * @param initiallySelectedExtension The initially selected extension or
   * null. If this is null this page will be the first page of the wizard,
   * otherwise the first page of the related <code>IExtensionWizardPageManager</code>
   * will be the first page of the wizard.
   */
  protected ExtensionWizardFirstPage( final String extensionPointID,
                                      final String configurationElementName,
                                      final String initiallySelectedExtension ) {
    
    super( "extensionWizardPage" ); //$NON-NLS-1$
    
    this.initiallySelectedExtension = initiallySelectedExtension;
    
    ExtensionManager extManager = new ExtensionManager();
    List< IConfigurationElement > cElements
      = extManager.getConfigurationElements( extensionPointID,
                                             configurationElementName );
    List< IConfigurationElement > pManagers
      = extManager.getConfigurationElements( eu.geclipse.ui.Extensions.EXTENSION_WIZARD_POINT,
                                             eu.geclipse.ui.Extensions.EXTENSION_WIZARD_PAGE_MANAGER_ELEMENT );
    
    this.pageManagers = new Hashtable< String, IExtensionWizardPageManager >();
    for ( IConfigurationElement cElement : cElements ) {
      
      String name = cElement.getAttribute( "name" ); //$NON-NLS-1$
      String wizard = cElement.getAttribute( "wizard" ); //$NON-NLS-1$
      if ( ( name == null ) || ( wizard == null ) ) {
        continue;
      }
      
      for ( IConfigurationElement pManager : pManagers ) {
        String id = pManager.getAttribute( "id" ); //$NON-NLS-1$
        if ( wizard.equals( id ) ) {
          try {
            IExtensionWizardPageManager manager
              = ( IExtensionWizardPageManager )pManager.createExecutableExtension( "class" ); //$NON-NLS-1$
            this.pageManagers.put( name, manager );
          } catch ( CoreException cExc ) {
            eu.geclipse.ui.internal.Activator.logException( cExc );
          } catch ( ClassCastException ccExc ) {
            eu.geclipse.ui.internal.Activator.logException( ccExc );
          }
        }
      }
      
    }
    
  }
  
  /**
   * Set the label of the extension combo.
   * 
   * @param label The new label of the extension combo.
   */
  public void setComboLabel( final String label ) {
    this.selectedExtensionLabelText = label;
  }
  
  public void setInitialData( final Object data ) {
    this.initialData = data;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false) );
    
    this.selectedExtensionLabel = new Label( mainComp, SWT.LEFT );
    this.selectedExtensionLabel.setText( this.selectedExtensionLabelText );
    gData = new GridData();
    this.selectedExtensionLabel.setLayoutData( gData );
    
    this.selectedExtensionCombo = new Combo( mainComp, SWT.DROP_DOWN | SWT.READ_ONLY );
    gData = new GridData();
    gData.grabExcessHorizontalSpace = true;
    this.selectedExtensionCombo.setLayoutData( gData );
    
    String[] keys = this.pageManagers.keySet().toArray(new String[0]);
    Arrays.sort( keys );
    for ( String key : keys ) {
      this.selectedExtensionCombo.add( key );
    }
    if ( this.initiallySelectedExtension != null ) {
      this.selectedExtensionCombo.setText( this.initiallySelectedExtension );
    } else if ( this.selectedExtensionCombo.getItemCount() != 0 ) {
      this.selectedExtensionCombo.select( 0 );
    }
    
    setControl( mainComp );
    
  }
  
  /**
   * Add all secondary pages to the specified wizard. Secondary pages are
   * all pages that are provided by the {@link IExtensionWizardPageManager}s.
   * 
   * @param wizard The wizard to add the pages.
   */
  public void addSecondaryPages( final ExtensionWizard wizard ) {
    Enumeration< String > keys = this.pageManagers.keys();
    while ( keys.hasMoreElements() ) {
      String key = keys.nextElement();
      IExtensionWizardPageManager manager = this.pageManagers.get( key );
      List< IWizardPage > pages = manager.getPages();
      if ( pages != null ) {
        for ( IWizardPage page : pages ) {
          wizard.addPage( page );
        }
      }
      boolean canManage = manager.setInitialData( this.initialData );
      if ( canManage ) {
        this.initiallySelectedExtension = key;
      }
    }
  }
  
  /**
   * Get the starting page for the underlying wizard.
   * 
   * @return If no initial extension was specified returns this. Otherwise
   * the first page of the related {@link IExtensionWizardPageManager} will
   * be returned.
   */
  public IWizardPage getStartingPage() {
    IWizardPage startingPage = this;
    if ( this.initiallySelectedExtension != null ) {
      List< IWizardPage > pages
        = getPages( this.initiallySelectedExtension );
      if ( ( pages != null ) && ( pages.size() > 0 ) ) {
        startingPage = pages.get( 0 );
      }
    }
    return startingPage;
  }
  
  /**
   * Get the next page for the wizard.
   * 
   * @param page The currently displayed page.
   * @return The page that follows the current page or null.
   */
  public IWizardPage getNextPage( final IWizardPage page ) {
    IWizardPage nextPage = null;
    List< IWizardPage > pages = getPages( null );
    if ( pages != null ) {
      if ( page == this ) {
        nextPage = pages.get( 0 );
      } else {
        for ( int i = 0 ; i < pages.size() ; i++ ) {
          if ( pages.get( i ) == page ) {
            if ( i < pages.size()-1 ) {
              nextPage = pages.get( i+1 );
            }
            break;
          }
        }
      }
    }
    return nextPage;
  }
  
  /**
   * Get the previous page for the wizard.
   * 
   * @param page The currently displayed page.
   * @return The page that prcedes the current page or null.
   */
  public IWizardPage getPreviousPage( final IWizardPage page ) {
    IWizardPage prevPage = null;
    List< IWizardPage > pages = getPages( null );
    if ( ( pages != null ) && ( page != this ) ) {
      for ( int i = 0 ; i < pages.size() ; i++ ) {
        if ( pages.get( i ) == page ) {
          if ( i > 0 ) {
            prevPage = pages.get(i-1);
          } else if ( this.initialData == null ) {
            prevPage = this;
          }
          break;
        }
      }
    }
    return prevPage;
  }
  
  /**
   * Determines if the underlying wizard can finish due to the current page.
   * 
   * @return True if the wizard can be finished.
   */
  public boolean canFinish() {
    boolean result = false;
    IExtensionWizardPageManager manager = getCurrentPageManager();
    if ( manager != null ) {
      IWizardPage currentPage
        = this.getContainer().getCurrentPage();
      result = manager.canFinish( currentPage );
    }
    return result;
  }
  
  /**
   * Performs a finish.
   * 
   * @return True if the operation was successful.
   */
  public boolean performFinish() {
    boolean result = false;
    IExtensionWizardPageManager manager = getCurrentPageManager();
    if ( manager != null ) {
      IWizardContainer container = getContainer();
      result = manager.performFinish( container );
    }
    return result;
  }
  
  /**
   * Get the wizard pages for the specified selected extension.
   * 
   * @param selection The extension. If this is null the current selection
   * is taken.
   * @return The wizard pages as returned by the
   * {@link IExtensionWizardPageManager}.
   */
  private List< IWizardPage > getPages( final String selection ) {
    List< IWizardPage > pages = null;
    IExtensionWizardPageManager manager = selection == null
      ? getCurrentPageManager()
      : this.pageManagers.get( selection );
    if ( manager != null ) {
      pages = manager.getPages();
    }
    return pages;
  }
  
  /**
   * Get the {@link IExtensionWizardPageManager} that corresponds to
   * the current selection.
   * 
   * @return The {@link IExtensionWizardPageManager} that is currently
   * selected.
   */
  private IExtensionWizardPageManager getCurrentPageManager() {
    String top = this.selectedExtensionCombo.getText();
    return this.pageManagers.get( top );
  }

}
