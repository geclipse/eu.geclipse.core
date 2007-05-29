package eu.geclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.ui.providers.ProgressTreeNode;


public class GridConnectionFilter extends ViewerFilter {
  
  private static final String WILDCARD_FILTER = "*.*"; //$NON-NLS-1$
  
  private List< String > filters
    = new ArrayList< String >();
  
  private String activeFilter;
  
  public void addFileExtensionFilter( final String filter ) {
    if ( ! matches( filter, this.filters ) ) {
      this.filters.add( filter );
      if ( this.activeFilter == null ) {
        this.activeFilter = filter;
      }
    }
  }
  
  public void clearFileExtensionFilters() {
    this.filters.clear();
    this.activeFilter = null;
  }
  
  public void link( final StructuredViewer viewer,
                    final Combo combo ) {
    
    viewer.addFilter( this );
    combo.removeAll();
    
    if ( this.filters.isEmpty() ) {
      combo.add( WILDCARD_FILTER );
      combo.setText( WILDCARD_FILTER );
    } else {
      for ( String filter : this.filters ) {
        combo.add( "*." + filter );
        combo.setText( "*." + this.activeFilter );
      }
    }
    
    combo.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        String filter = combo.getText();
        setActiveFilter( combo.getText().substring( 2 ) );
        viewer.refresh();
      }
    } );
    
  }
  
  public void setActiveFilter( final String filter ) {
    if ( filter != null ) {
      addFileExtensionFilter( filter );
    }
    this.activeFilter = filter;
  }
  
  public void setFileExtensionFilter( final String filter ) {
    clearFileExtensionFilters();
    this.filters.add( filter );
  }
  
  @Override
  public boolean select( final Viewer viewer,
                         final Object parentElement,
                         final Object element ) {
    boolean result = false;
    if ( element instanceof IGridConnectionElement ) {
      result = select( ( IGridConnectionElement  ) element );
    } else if ( element instanceof IGridContainer ) {
      result = true;
    } else if ( element instanceof ProgressTreeNode ) {
      result = true;
    }
    return result;
  }
  
  protected boolean select( final IGridConnectionElement element ) {
    boolean result = this.filters.isEmpty();
    if ( this.activeFilter != null ) {
      if ( element.isFolder() ) {
        result = true;
      } else {
        IPath path = new Path( element.getName() );
        String extension = path.getFileExtension();
        if ( extension == null ) {
          result = false;
        } else {
          result = this.activeFilter.equals( "*" ) || matches( extension, this.activeFilter );
        }
      }
    }
    return result;
  }
  
  private boolean matches( final String extension,
                           final List< String > filterList ) {
    boolean result = false;
    for ( String filter : filterList ) {
      if ( matches( extension, filter ) ) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  private boolean matches( final String extension,
                           final String filter ) {
    return extension.equalsIgnoreCase( filter );
  }
  
}
