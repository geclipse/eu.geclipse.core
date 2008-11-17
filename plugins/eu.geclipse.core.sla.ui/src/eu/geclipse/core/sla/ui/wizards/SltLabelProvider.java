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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.wizards;

import org.eclipse.jface.viewers.LabelProvider;

import eu.geclipse.core.sla.model.SltContainer;

/**
 * @author korn
 *
 */
public class SltLabelProvider extends LabelProvider {

  @Override
  public String getText( Object element ) {
    SltContainer slt = ( SltContainer )element;
    // TODO Auto-generated method stub
    return slt.getName() + " (" + slt.getRanking() + ")";  //$NON-NLS-1$//$NON-NLS-2$
  }
}
