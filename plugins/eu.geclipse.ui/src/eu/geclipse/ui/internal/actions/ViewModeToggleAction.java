/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.views.ElementManagerViewPart;

/**
 * Action for settings the view mode of an {@link ElementManagerViewPart}.
 */
public class ViewModeToggleAction extends Action {
  
  private ConfigurableContentProvider provider;
  
  /**
   * Construct a new <code>ViewModeToggleAction</code> for the specified
   * {@link ConfigurableContentProvider}.
   * 
   * @param provider The {@link ConfigurableContentProvider} this action
   * is dediacted to.
   */
  public ViewModeToggleAction( final ConfigurableContentProvider provider ) {
    
    super( Messages.getString("ViewModeToggleAction.title"), IAction.AS_CHECK_BOX ); //$NON-NLS-1$
  
    this.provider = provider;
    
    URL hierarchicalUrl
      = Activator.getDefault().getBundle().getEntry( ViewModeAction.VIEW_HIERARCHICAL_IMAGE );
    ImageDescriptor hierarchicalDescriptor
      = ImageDescriptor.createFromURL( hierarchicalUrl );
    setImageDescriptor( hierarchicalDescriptor );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    int mode
      = this.isChecked()
      ? ConfigurableContentProvider.MODE_HIERARCHICAL
      : ConfigurableContentProvider.MODE_FLAT;
    this.provider.setMode( mode );
  }
  
}
