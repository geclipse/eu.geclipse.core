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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.interfaces;

import java.io.InputStream;
import java.util.List;

import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;
import eu.geclipse.ui.wizards.wizardselection.IWizardSelectionNode;

/**
 * Plug-ins contributing to structural test wizard should provide wizard's
 * content through this interface.
 */
public interface IServiceJobWizardNode
  extends IWizardSelectionNode, IInitalizableWizard
{

  /**
   * Method to access file input stream for newly created structural test.
   * 
   * @return input stream which corresponds to XML content of structural test
   */
  public InputStream getTestInputData();

  /**
   * Method to access structural test's name which is created by this wizard.
   * 
   * @return name of the structural test
   */
  String getTestName();

  /**
   * Gets selected project for this test.
   * 
   * @return IVirtualOrganization selected in VO selection page.
   */
  IGridProject getSelectedProject();

  /**
   * Method to access ID of plug-in that extends the
   * eu.geclipse.servicejob.servicejobProvider extension point.
   * 
   * @return plug-in id in form of String
   */
  String getPluginID();

  /**
   * Method to access names of {@link IGridResource}s that will be objects of
   * this service jobs. Those names are serialized in .gtdl file. Please note
   * that after g-Eclipse's restart those names are the only "handlers" to which
   * contributor has access (names, not {@link IGridResource} objects) - so this
   * list should be constructed this way that names carry all the information
   * needed to perform service job by contributor's plug-in. This method is
   * called by main service job framework's plug-in when .gtdl file is being
   * created.
   * 
   * @return list of names representing resources that are object of this
   *         service job
   */
  List<String> getResourcesToTestNames();

  /**
   * Method to access instances of {@link IGridResource}s that were passed to
   * wizard when launching it e.g. from context menu. This method may return an
   * empty list or even <code>null</code>.
   * 
   * @return List of {@link IGridResource} obtained from selection from which
   *         wizard was launched. If wizard wasn't launched from selection, or
   *         this selection doesn't contain any {@link IGridResource} objects
   *         this list will be empty.
   */
  List<IGridResource> getPreselectedResources();
  // /**
  // * Implementation should return grid test description XML document that will
  // * be translated to JSDL and submitted as a test job on the grid.
  // *
  // * @return XML document
  // */
  // Document getDocument();
  //
  // String getTestName();
  //
  // public InputStream getStructuralTestInput();
  //  
  // public IGridProject getProject();
  //  
  //  
  // /**
  // * Gets selected resources.
  // * @return List<IGridResource>
  // */
  // public List<IGridResource> getResources();
  //  
}
