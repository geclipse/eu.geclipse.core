package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;

public class GridConnectionView extends ElementManagerViewPart {

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#acceptDrop(eu.geclipse.core.model.IGridContainer, eu.geclipse.core.model.IGridElement[])
   */
  @Override
  public int acceptDrop( final IGridContainer target,
                         final IGridElement[] elements ) {
    int result = super.acceptDrop( target, elements );
    if ( result != DND.DROP_NONE ) {
      if ( target instanceof IGridConnectionElement ) {
        if ( !( ( IGridConnectionElement ) target ).isFolder() ) {
          result = DND.DROP_NONE;
        } else {
          for ( IGridElement element : elements ) {
            if ( element instanceof IGridConnection ) {
              result = DND.DROP_NONE;
              break;
            }
          }
        }
      } else {
        result = DND.DROP_NONE;
      }
    }
    return result;
  }
  
  @Override
  protected IGridElementManager getManager() {
    return GridModel.getConnectionManager();
  }
  
  @Override
  protected ConfigurableContentProvider createConfigurableContentProvider() {
    ConfigurableContentProvider provider
      = new ConnectionViewContentProvider();
    return provider;
  };
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new ConnectionViewLabelProvider();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.ElementManagerViewPart#createTreeColumns(org.eclipse.swt.widgets.Tree)
   */
  @Override
  protected boolean createTreeColumns( final Tree tree ) {
    
    super.createTreeColumns( tree );
    
    TreeColumn sizeColumn = new TreeColumn( tree, SWT.NONE );
    sizeColumn.setText( "Size" );
    sizeColumn.setAlignment( SWT.RIGHT );
    sizeColumn.setWidth( 100 );
    
    TreeColumn modColumn = new TreeColumn( tree, SWT.NONE );
    modColumn.setText( "Last Modification" );
    modColumn.setAlignment( SWT.CENTER );
    modColumn.setWidth( 200 );
    
    return true;
    
  }
  
  @Override
  public boolean isDragSource( final IGridElement element ) {
    return element instanceof IGridConnectionElement;
  }
  
}
