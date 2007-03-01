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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * A Wizard for the selection and start of a wizard from wizards registered on 
 * a specified extension point.
 * @param <InitDataType> type of the initialization data to pass to the started
 *                       wizard.
 */
public class WizardSelectionWizard<InitDataType> extends Wizard {
  private WizardSelectionWizardPage<InitDataType> mainPage;
  private INewWizard<InitDataType> wizard;
  private InitDataType initData;
  private String extPointId;
  private String pageName;
  private String title;
  private String description;
  private String windowTitle;
  
  /**
   * Creates a wizard selection wizard which starts the wizard selected by the
   * user.
   * @param initData data to initialize the started wizard with.
   * @param extensionPointId the id of the extension point where the wizards to
   *                         select from are registered.
   * @param pageName name of the main page of the wizard selection wizard.
   * @param title title of the main page of the wizard selection wizard.
   * @param description description text of the main page of the wizard
   *                    selection wizard.
   * @param windowTitle title of the wizard window.
   * @param defaultPageImage ImageDescriptor for the default page image of the
   *                         wizard.
   */
  public WizardSelectionWizard( final InitDataType initData,
                                final String extensionPointId,
                                final String pageName,
                                final String title,
                                final String description,
                                final String windowTitle,
                                final ImageDescriptor defaultPageImage ) {
    this.initData = initData;
    this.extPointId = extensionPointId;
    this.pageName = pageName;
    this.title = title;
    this.description = description;
    this.windowTitle = windowTitle;
    if ( defaultPageImage != null ) setDefaultPageImageDescriptor( defaultPageImage );
    setForcePreviousAndNextButtons( true );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    this.mainPage = new WizardSelectionWizardPage<InitDataType>(
        this.pageName,
        this.extPointId,
        this.title,
        this.description );
    addPage( this.mainPage );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return this.windowTitle;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    boolean result;
    if (getContainer().getCurrentPage() == this.mainPage) {
      result = false;
    } else {
      result = this.wizard.canFinish();
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
   */
  @Override
  public IWizardPage getNextPage( final IWizardPage page ) {
    IWizardPage result = null;
    if ( page == this.mainPage ) {
      this.wizard = this.mainPage.getNewTerminalWizard();
      this.wizard.init( this.initData );
      if ( this.wizard.getPageCount() == 0 ) this.wizard.addPages();
    }
    if ( this.wizard != null ) {
      if ( page == this.mainPage ) {
        result = this.wizard.getStartingPage();
      } else {
        result = this.wizard.getNextPage( page );
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getPreviousPage(org.eclipse.jface.wizard.IWizardPage)
   */
  @Override
  public IWizardPage getPreviousPage( final IWizardPage page ) {
    IWizardPage result = null;
    if ( page != this.mainPage ) {
      if ( page == this.wizard.getStartingPage() ) {
        result = this.mainPage;
      } else {
        result = this.wizard.getPreviousPage( page );
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    return false;
  }
}
