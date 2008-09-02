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
package eu.geclipse.ui.wizards.jobsubmission;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.IGridJobDescription;

/**
 * Wrapper class for all initialization data that has to be passed to
 * {@link JobSubmissionWizardBase} objects. This class contains
 * {@link IGridJobDescription}s to submit and list of names and location where
 * jobs should be created.
 */
public class WrapperInitObject {

  private List<IGridJobDescription> jobDescriptions = new ArrayList<IGridJobDescription>();
  private List<String> jobNames = new ArrayList<String>();
  private IResource destinationFolder;

  WrapperInitObject( final List<IGridJobDescription> jobDescriptions,
                     final List<String> jobNames,
                     final IResource destinationFolder )
  {
    this.jobDescriptions = jobDescriptions;
    this.jobNames = jobNames;
    this.destinationFolder = destinationFolder;
  }

  /**
   * Method to access list of job descriptions to submit.
   * 
   * @return list of job descriptions to submit
   */
  public List<IGridJobDescription> getJobDescriptions() {
    return this.jobDescriptions;
  }

  /**
   * Method to access list of names under which newly created jobs (after
   * submission) should be saved. This list is ordered in a way that it reflects
   * sequence of job descriptions objects (returned by
   * {@link WrapperInitObject#getJobDescriptions()} (it is guaranteed that 1st
   * name in this list is name for 1st on job description list, 2nd name is for
   * 2nd job description, and so on...)
   * 
   * @return list of names under which job descriptions at corresponding position in list
   */
  public List<String> getJobNames() {
    return this.jobNames;
  }

  public IResource getDestinationFolder() {
    return this.destinationFolder;
  }
}
