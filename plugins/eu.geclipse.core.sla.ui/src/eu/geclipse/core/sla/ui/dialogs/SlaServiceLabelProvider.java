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
package eu.geclipse.core.sla.ui.dialogs;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * 
 */
public class SlaServiceLabelProvider extends LabelProvider {

  @Override
  public Image getImage( Object element ) {
    return null;
  }

  @Override
  public String getText( Object element ) {
    String huschel = ( String )element;
    return huschel;
  }
}
