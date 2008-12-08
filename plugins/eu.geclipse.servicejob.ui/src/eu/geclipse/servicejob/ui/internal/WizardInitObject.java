/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
package eu.geclipse.servicejob.ui.internal;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.ui.wizards.IProjectSelectionProvider;

/**
 * Wrapper object for passing IGridProject and IGridResources list to services
 * jobs' wizards.
 * 
 * @author Katarzyna Bylec
 */
public class WizardInitObject {

  private String name;
  private IGridProject project;
  private List<IGridResource> resources = new ArrayList<IGridResource>();
  private IProjectSelectionProvider voProvider;

  /**
   * Creates instance of {@link WizardInitObject}. Be aware that resources list
   * may be empty, while project reference should never be <code>null</code>.
   * 
   * @param name String name of service job that will be used in all views
   *          showing this job as well as a name for GTDL file
   * @param project reference to {@link IGridProject} for which wizard is run
   * @param resources list of {@link IGridResource}s for which wizard is run
   */
  public WizardInitObject( final String name,
                           final IProjectSelectionProvider voProvider,
                           final List<IGridResource> resources )
  {
    this.name = name;
    this.voProvider = voProvider;
    this.resources = resources;
  }

  /**
   * Simple getter method.
   * 
   * @return value of project field.
   */
  public IGridProject getProject() {
    return this.project;
  }

  /**
   * Simple getter method.
   * 
   * @return value of resources' list field.
   */
  public List<IGridResource> getResources() {
    return this.resources;
  }

  public IProjectSelectionProvider getVOProvider() {
    return this.voProvider;
  }

  /**
   * Simple getter method.
   * 
   * @return name of service job. This value is used in all views showing
   *         service jobs as well as a name of GTDL file.
   */
  public String getName() {
    return this.name;
  }
}
