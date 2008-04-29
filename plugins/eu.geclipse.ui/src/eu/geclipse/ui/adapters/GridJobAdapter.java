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
 *     Mariusz Wojtysiak - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.adapters;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.WorkbenchAdapter;

import eu.geclipse.ui.internal.Activator;

/**
 * An IWorkbenchAdapter that represents IFolders.
 */
public class GridJobAdapter extends WorkbenchAdapter {

  @Override
  public ImageDescriptor getImageDescriptor( final Object resource ) {
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/obj16/job_folder.gif" ); //$NON-NLS-1$
   return ImageDescriptor.createFromURL( imgUrl );
//        return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
//                ISharedImages.IMG_OPEN_MARKER);
    }

  
//    public Object[] getChildren(Object o) {
//        try {
//            return ((IContainer) o).members();
//        } catch (CoreException e) {
//            return NO_CHILDREN;
//        }
//    }

//  public Object[] getChildren(Object o);
//  public ImageDescriptor getImageDescriptor(Object object);
//  public String getLabel(Object o);
//  public Object getParent(Object o);

}
