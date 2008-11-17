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

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author korn
 */
public class SlaServiceContentProvider implements IStructuredContentProvider {

  public Object[] getElements( Object inputElement ) {
    // TODO Auto-generated method stub
    List<String> liste = eu.geclipse.core.sla.Extensions.getRegisteredSlaServiceNames();
    return liste.toArray();
  }

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    // TODO Auto-generated method stub
  }
}
