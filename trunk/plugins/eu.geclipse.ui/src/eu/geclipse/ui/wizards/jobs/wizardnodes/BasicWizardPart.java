/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobs.wizardnodes;

import java.util.ArrayList;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Point;
import eu.geclipse.ui.wizards.jobs.NewJobWizard;

/**
 * Class that is a {@link IWizardNode} holding itself as a Wizard. This Wizard
 * is a basic (allways present) part of {@link NewJobWizard}
 */
public class BasicWizardPart extends Wizard implements IWizardNode {

  private ArrayList<WizardPage> internalPages = new ArrayList<WizardPage>();
  private IWizard parentWizard;
  private boolean isCreated = false;

  /**
   * Method to create an instance of this class
   * 
   * @param pages pages for {@link BasicWizardPart} to hold
   * @param wizard parent wizard in which {@link BasicWizardPart} is used
   */
  public BasicWizardPart( final ArrayList<WizardPage> pages,
                          final IWizard wizard )
  {
    this.internalPages = pages;
    this.parentWizard = wizard;
    this.addPages();
    this.isCreated = true;
  }

  @Override
  public boolean performFinish()
  {
    return this.parentWizard.performFinish();
  }

  public Point getExtent() {
    // TODO Auto-generated method stub
    return null;
  }

  public IWizard getWizard() {
    return this;
  }

  public boolean isContentCreated() {
    return this.isCreated;
  }

  @Override
  public void addPages()
  {
    for( WizardPage page : this.internalPages ) {
      addPage( page );
    }
  }
}
