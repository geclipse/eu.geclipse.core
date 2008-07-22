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
package eu.geclipse.servicejob.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.servicejob.ui.wizard.ServiceJobWizard;



/**
 * Action for opening Operator's Job details view.
 * 
 * @author Katarzyna Bylec
 *
 */
public class OpenServiceJobDialogAction extends Action {
  
  @Override
  public void run() {
    ServiceJobWizard wizard = new ServiceJobWizard();
    WizardDialog dialog = new WizardDialog( Display.getCurrent().getActiveShell(), wizard );
    dialog.open();
  }
  
}
