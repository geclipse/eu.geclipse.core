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

package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import eu.geclipse.ui.internal.Activator;

class ExtPointWizardSelectionNode implements IWizardSelectionNode {
  private IWizard wizard;
  private IConfigurationElement element;
  private String name;
  private String id;
  private Image icon;
    
  ExtPointWizardSelectionNode( final IConfigurationElement element,
                       final String id, 
                       final String name,
                       final Image icon ) {
    this.element = element;
    this.name = name;
    this.id = id;
    this.icon = icon;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.wizards.IWizardSelectionNode#getName()
   */
  public String getName() {
    return this.name;
  }
    
  /* (non-Javadoc)
   * @see eu.geclipse.ui.wizards.IWizardSelectionNode#getIcon()
   */
  public Image getIcon() {
    return this.icon;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizardNode#dispose()
   */
  public void dispose() {
    if ( this.icon != null) this.icon.dispose();
    if ( this.wizard != null ) this.wizard.dispose();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
   */
  public Point getExtent() {
    // TODO get right extent if wizard is already created
    return new Point(-1, -1);
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
   */
  public IWizard getWizard() {
    if ( this.wizard == null ) {
      try {
        Object obj = this.element.createExecutableExtension(
                              ExtPointWizardSelectionListPage.EXT_CLASS );
        if ( obj instanceof IWizard ) {
          this.wizard = ( IWizard ) obj;
        }
      } catch( CoreException coreEx ) {
        Activator.logException( coreEx );
      }
    }
    return this.wizard;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
   */
  public boolean isContentCreated() {
    return this.wizard != null;
  }
  
  String getId() {
    return this.id;
  }
}
