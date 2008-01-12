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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridResource;
import eu.geclipse.ui.ISimpleTestUIFactory;

import eu.geclipse.ui.dialogs.AbstractSimpleTestDialog;


/**
 * @author harald
 *
 */
public class SimpleTestAction extends SelectionListenerAction {

  private List< IGridResource > resources;
  
  private ISimpleTestUIFactory factory;

  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  protected SimpleTestAction( final IWorkbenchSite site, final ISimpleTestUIFactory factory ) {
    super( factory.getSupportedDescription().getSimpleTestTypeName() );
    
    this.site = site;
    this.factory = factory;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    if ( this.factory != null ) {
      AbstractSimpleTestDialog infoDialog = this.factory.getSimpleTestDialog( null, this.site.getShell() );
      infoDialog.open();
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.resources = new ArrayList< IGridResource >();
    boolean enabled = super.updateSelection( selection );
    Iterator< ? > iter = selection.iterator();
    while ( iter.hasNext() && enabled ) {
      Object element = iter.next();
      boolean isResource = isResourcePhysical( element );
      enabled &= isResource;
      if ( isResource ) {
        this.resources.add( ( IGridResource ) element );
      }
    }
    return enabled;
  }

  protected boolean isResourcePhysical( final Object element ) {
    boolean ret = false;
    
    if ( element instanceof eu.geclipse.core.model.IGridResource ) {
      ret = ( ( eu.geclipse.core.model.IGridResource ) element ).isVirtual();
    }
    
    return ret;
  }
}
