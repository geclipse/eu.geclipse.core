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

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Point;
import org.xml.sax.SAXException;
import eu.geclipse.ui.internal.wizards.jobs.ApplicationSpecificPageFactory;
import eu.geclipse.ui.wizards.jobs.NewJobWizard;

/**
 * Class that is a {@link IWizardNode} holding itself as a Wizard. This Wizard
 * is a specific (not allways present) part of {@link NewJobWizard}
 */
public class SpecificWizardPart extends Wizard implements IWizardNode {

  private IWizardNode nextWizardIner;
  private IWizard parentWizard;
  private String extensionPointId;
  private boolean isCreated;
  private List<WizardPage> pages;

  /**
   * Creates an instance of {@link SpecificWizardPart}
   * 
   * @param nextWizard wizard that will be shown after this wizard (as a
   *          subwizard of {@link NewJobWizard})
   * @param parentWizard wizard containing this wizard (usually
   *          {@link NewJobWizard})
   * @param extensionPointIdName id of extension point that defines pages of
   *          this wizard
   */
  public SpecificWizardPart( final IWizardNode nextWizard,
                             final IWizard parentWizard,
                             final String extensionPointIdName )
    throws SAXException, ParserConfigurationException, IOException
  {
    super();
    this.nextWizardIner = nextWizard;
    this.parentWizard = parentWizard;
    this.extensionPointId = extensionPointIdName;
    this.pages = ApplicationSpecificPageFactory.getPagesFromXML( this.extensionPointId,
                                                                 this.nextWizardIner );
    this.addPages();
    this.isCreated = true;
  }

  @Override
  public boolean performFinish()
  {
    return this.parentWizard.performFinish();
  }

  public Point getExtent() {
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
    for( WizardPage asp : this.pages ) {
      addPage( asp );
    }
  }
 
}
