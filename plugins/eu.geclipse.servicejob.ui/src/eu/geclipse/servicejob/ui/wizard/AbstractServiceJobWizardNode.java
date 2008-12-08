/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium 
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
 *    Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.servicejob.ui.wizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.servicejob.ui.interfaces.IServiceJobWizardNode;
import eu.geclipse.servicejob.ui.internal.WizardInitObject;
import eu.geclipse.ui.wizards.IProjectSelectionProvider;

/**
 * Abstract class of the ITestWizardNode. It should be used instead of
 * ITestWizardNode if there should be created file containing informations about
 * the test during finishing wizard.
 * <p>
 */
public abstract class AbstractServiceJobWizardNode extends Wizard 
implements IServiceJobWizardNode, IProjectSelectionProvider
{

  protected IProjectSelectionProvider projectProvider;
  protected IGridProject project;
  protected List<IGridResource> resources;
  private String name;

  @Override
  public String getWindowTitle() {
    return getName();
  }

  @SuppressWarnings("unchecked")
  public boolean init( final Object data ) {
    boolean result = false;
    if( data instanceof WizardInitObject ) {
      WizardInitObject initObject = ( WizardInitObject )data;
      this.resources = initObject.getResources();
//      this.project = initObject.getProject();
      this.projectProvider = initObject.getVOProvider();
      this.name = initObject.getName();
      result = true;
    }
    return result;
  }

  @Override
  public boolean performFinish() {
    return true;
  }


  public Image getIcon() {
    return null;
  }

  public Point getExtent() {
    return null;
  }

  public IGridProject getSelectedProject() {
    return this.projectProvider.getGridProject();
  }

  public List<IGridResource> getPreselectedResources() {
    return this.resources;
  }

  public String getTestName() {
    return this.name;
  }

  
  public IGridProject getGridProject(){
    return getSelectedProject();
  }
  
  
  
}
