/**
 * 
 */
package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import eu.geclipse.core.monitoring.GridProcess;
import eu.geclipse.core.monitoring.GridProcessMonitor;


/**
 * @author picard
 *
 */
public class ProcessViewContentprovider  implements ITreeContentProvider {

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
   */
  public Object[] getChildren( Object parentElement ) {
    Object[] result = new Object[0];
    if (parentElement instanceof GridProcessMonitor[]){
      result = (GridProcessMonitor[])parentElement;
    } else if (parentElement instanceof GridProcessMonitor){
      GridProcessMonitor mon = (GridProcessMonitor)parentElement;
      result = mon.getProcessList().toArray();
    } else if (parentElement instanceof GridProcess){
      GridProcess proc = (GridProcess)parentElement;
      result = proc.getStat().keySet().toArray();
      
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  public Object getParent( Object element ) {
   
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
   */
  public boolean hasChildren( Object element ) {
    boolean result = false;
    if (element instanceof GridProcessMonitor){
      result = true;                        // TODO really check if we could read processes
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  public Object[] getElements( Object inputElement ) {
    return this.getChildren( inputElement );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */

  public void inputChanged( Viewer viewer, Object oldInput, Object newInput ) {
    // nothing to do here
  }

  public void dispose() {
    // Nothing to do here
 
  }
}
