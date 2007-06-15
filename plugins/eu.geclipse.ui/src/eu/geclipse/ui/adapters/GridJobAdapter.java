package eu.geclipse.ui.adapters;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.WorkbenchAdapter;

import eu.geclipse.ui.internal.Activator;

/**
 * An IWorkbenchAdapter that represents IFolders.
 */
public class GridJobAdapter extends WorkbenchAdapter {

  public ImageDescriptor getImageDescriptor(Object resource) {
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
