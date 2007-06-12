package eu.geclipse.jsdl.ui.internal.wizards;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;


/**
 * Label provider used in {@link MultipleArgumentList}
 * @author katis
 *
 */
public class StringLabelProvider extends LabelProvider implements ITableLabelProvider{

  public Image getColumnImage( final Object element, final int columnIndex ) {
    return null;
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    return element.toString();
  }
  
}