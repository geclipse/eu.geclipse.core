package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class FeatureLabelProvider extends LabelProvider implements
    ITableLabelProvider {

  @Override
  public Image getColumnImage(Object element, int columnIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getColumnText(Object element, int columnIndex) {
    String text = null;
    if ( element instanceof EStructuralFeature ) {
      EStructuralFeature feature = ( EStructuralFeature ) element;
      switch ( columnIndex ) {
        case 0:
          feature.toString();
      }
    } else {
      text = element.toString();
    }
    return text;
  }

}
