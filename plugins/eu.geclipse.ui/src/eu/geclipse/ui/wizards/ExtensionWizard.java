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

import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import eu.geclipse.core.ExtensionManager;

/**
 * The <code>ExtensionWizard</code> implements a <code>Wizard</code> that
 * can deal with extensions of a given extension point. The first page of
 * this <code>Wizard</code> is represented by a
 * {@link ExtensionWizardFirstPage} that gives the user the possibility to
 * chose among the extensions of the defined extension point. The other pages
 * depend on that choice.
 * <p>
 * There are some requirements the extension point has to fulfill:
 * <ul>
 * <li>The extension point has to define a configuration element.
 * <li>This configuration element has to define an attribute called "name"
 * that is taken to refer to this extension.
 * <li>The element has furthermore to define an attribute called "wizard"
 * that corresponds to the id of manager element of the extension of the
 * eu.geclipse.ui.extensionWizard extension point.
 * </ul>
 * 
 * @author stuempert-m
 */

public class ExtensionWizard extends Wizard {
  
  /**
   * The id of the extension point.
   */
  private String extensionPointID;
  
  /**
   * The name of the configuration element.
   */
  private String configurationElementName;
  
  /**
   * The initialy chosen extension. May be null.
   */
  private String initiallySelectedExtension;
  
  /**
   * The first page of the wizard.
   */
  private ExtensionWizardFirstPage firstPage;
  
  /**
   * Construct a new <code>ExtensionWizard</code> for extensions of the
   * specified element of the specified extension point.
   *  
   * @param extensionPointID The id of the extension point.
   * @param configurationElementName The name of the configuration element.
   */
  public ExtensionWizard( final String extensionPointID,
                          final String configurationElementName ) {
    this( extensionPointID, configurationElementName, null );
  }
  
  /**
   * Construct a new <code>ExtensionWizard</code> for extensions of the
   * specified element of the specified extension point. This constructor
   * offers the possibility to specify an initially selected extension.
   * If this is not null the first wizard page will not be the page where
   * the extension can be selected but the first page specific for that
   * extension.
   * 
   * @param extensionPointID The id of the extension point.
   * @param configurationElementName The name of the configuration element.
   * @param initiallySelectedExtension The name of the initially selected
   * extension as given by the name attribute of the configuration element.
   */
  public ExtensionWizard( final String extensionPointID,
                          final String configurationElementName,
                          final String initiallySelectedExtension ) {
    
    this.extensionPointID = extensionPointID;
    this.configurationElementName = configurationElementName;
    this.initiallySelectedExtension = initiallySelectedExtension;
    
    ExtensionManager extManager = new ExtensionManager();
    IExtensionPoint extensionPoint
      = extManager.getExtensionPoint( extensionPointID );
    this.firstPage = new ExtensionWizardFirstPage( this.extensionPointID,
                                                   this.configurationElementName,
                                                   this.initiallySelectedExtension );
    setFirstPageTitle( extensionPoint.getLabel() );
    setFirstPageImage( null );
    setFirstPageDescription( Messages.getString( "ExtensionWizard.default_first_page_description" ) ); //$NON-NLS-1$
    setFirstPageComboLabel( Messages.getString( "ExtensionWizard.default_first_page_combo_label" ) ); //$NON-NLS-1$
    setInitialData( null );
    
  }
  
  /**
   * Set the title of the first page, i.e. the extension selection page.
   *  
   * @param title The title of the {@link ExtensionWizardFirstPage}.
   */
  public void setFirstPageTitle( final String title ) {
    this.firstPage.setTitle( title );
  }
  
  /**
   * Set the image of the first page, i.e. the extension selection page.
   *  
   * @param image The image of the {@link ExtensionWizardFirstPage}.
   */
  public void setFirstPageImage( final ImageDescriptor image ) {
    this.firstPage.setImageDescriptor( image );
  }
  
  /**
   * Set the description of the first page, i.e. the extension selection page.
   *  
   * @param description The description of the {@link ExtensionWizardFirstPage}.
   */
  public void setFirstPageDescription( final String description ) {
    this.firstPage.setDescription( description );
  }
  
  /**
   * Set the label of combo of the the first page that is used to chose an extension.
   *  
   * @param label The label of the combo of the {@link ExtensionWizardFirstPage}.
   */
  public void setFirstPageComboLabel( final String label ) {
    this.firstPage.setComboLabel( label );
  }
  
  public void setInitialData( final Object initialData ) {
    this.firstPage.setInitialData( initialData );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    addPage( this.firstPage );
    this.firstPage.addSecondaryPages( this );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getStartingPage()
   */
  @Override
  public IWizardPage getStartingPage() {
    return this.firstPage.getStartingPage();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
   */
  @Override
  public IWizardPage getNextPage( final IWizardPage page ) {
    return this.firstPage.getNextPage( page );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
   */
  @Override
  public IWizardPage getPreviousPage( final IWizardPage page ) {
    return this.firstPage.getPreviousPage( page );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    return this.firstPage.canFinish();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    return this.firstPage.performFinish();
  }
  
}
