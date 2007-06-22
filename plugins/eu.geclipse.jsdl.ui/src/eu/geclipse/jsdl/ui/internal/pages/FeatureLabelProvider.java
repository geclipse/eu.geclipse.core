package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;

public class FeatureLabelProvider extends LabelProvider implements
    ITableLabelProvider {

  public Image getColumnImage(Object element, int columnIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  public String getColumnText(Object element, int columnIndex) {
    String text = null;
      
    if ( element instanceof ArgumentType ) {
      
      ArgumentType argumentType = ( ArgumentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = argumentType.getFilesystemName();
          break;
        case 1:
          text = argumentType.getValue();
          break;
          default:
          break;
      }
    } 
    else if (element instanceof EnvironmentType) {
      EnvironmentType environmentType = ( EnvironmentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = environmentType.getName();
          break;
        case 1:
          text = environmentType.getFilesystemName();
          break;
        case 2:
          text = environmentType.getValue();
          break;
          default:
          break;
      }
    }
    return text;
  }

}
