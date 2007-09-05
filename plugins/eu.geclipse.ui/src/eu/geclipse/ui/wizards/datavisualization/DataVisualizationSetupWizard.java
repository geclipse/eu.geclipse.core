/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.wizards.datavisualization;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.jobsubmission.Messages;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;


public class DataVisualizationSetupWizard extends Wizard implements IInitalizableWizard {

  private String currentSelection;
  private DataVisualizationSetupWizardPage vizSetupPage;
  
  public DataVisualizationSetupWizard() {
    this.currentSelection = "test";
    setNeedsProgressMonitor( true );
    setForcePreviousAndNextButtons( true );
    setWindowTitle( Messages.getString("DataVisualizationSetupWizard.title") ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/visulization_wiz.png" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  @Override
  public void addPages() {
    this.vizSetupPage = new DataVisualizationSetupWizardPage( Messages.getString("DataVisualizationSetupWizard"),
                                                              Messages.getString("DataVisualizationSetupWizard.title"), null );
    this.vizSetupPage.setInitData( this.currentSelection );
    addPage( this.vizSetupPage );
  }
  
  @Override
  public boolean performFinish() {
    // TODO Auto-generated method stub
    return true;
  }

  public boolean init( Object data ) {
    // TODO Auto-generated method stub
    return false;
  }
}
