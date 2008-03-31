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

import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.ILocalFolder;


public class WrapperInitObject {
  
  private List<IGridJobDescription> jobDescriptions = new ArrayList<IGridJobDescription>();
  
  private List<String> jobNames = new ArrayList<String>();
  
  private ILocalFolder destinationFolder;
  
  WrapperInitObject(final List<IGridJobDescription> jobDescriptions, final List<String> jobNames, final ILocalFolder destinationFolder){
    this.jobDescriptions = jobDescriptions;
    this.jobNames = jobNames;
    this.destinationFolder = destinationFolder;
  }

  
  public List<IGridJobDescription> getJobDescriptions() {
    return this.jobDescriptions;
  }

  
  public List<String> getJobNames() {
    return this.jobNames;
  }

  
  public ILocalFolder getDestinationFolder() {
    return this.destinationFolder;
  }
  
  
  
}
