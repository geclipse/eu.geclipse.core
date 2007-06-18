package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FeatureContentProvider implements IStructuredContentProvider {

  public Object[] getElements(Object inputElement) {
    Object[] result = null;
    if ( inputElement instanceof EList ) {
      EList list = ( EList ) inputElement;
      result = list.toArray( new Object[ list.size() ] );
    }
    return result;
  }

  public void dispose() {
    // TODO Auto-generated method stub

  }

  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // TODO Auto-generated method stub

  }

}
