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
import eu.geclipse.core.model.impl.ContainerMarker;
import eu.geclipse.ui.providers.ProgressTreeNode;

/**
 * Implementation of a {@link ViewerFilter} that may be used in conjunction
 * with viewers used to display Grid model elements. This filter allows to
 * set file extensions to be filtered. It is possible to define multiple
 * file extensions but only one of these extensions will be active and will
 * be used for filtering. The filter can be linked to a combo box that will
 * be used to display the list of available filters and to select the currently
 * active filter.
 * 
 * Note that file extensions have to be specified WITHOUT their leading period.
 */
public class GridConnectionFilter extends ViewerFilter {
  
  /**
   * Static field holding the prefix for all filters.
   */
  private static final String FILTER_PREFIX = "*."; //$NON-NLS-1$
  
  /**
   * Static field holding the filter that displays all files. 
   */
  private static final String WILDCARD_FILTER = "*"; //$NON-NLS-1$
  
  /**
   * the list of currently available filters.
   */
  private List< String > filters
    = new ArrayList< String >();
  
  /**
   * The currently active filter.
   */
  private String activeFilter;
  
  /**
   * Add the specified file extension to the list of the available
   * filters. If not active filter is yet defined this file extension
   * will be set as the currently active filter.
   * 
   * @param filter A new file extention filter. This has to be a
   * string containing the file extension WIHTOUT the leading period.
   */
  public void addFileExtensionFilter( final String filter ) {
    if ( ! matches( filter, this.filters ) ) {
      this.filters.add( filter );
      if ( this.activeFilter == null ) {
        this.activeFilter = filter;
      }
    }
  }
  
  /**
   * Clear the list of currently available filters.
   */
  public void clearFileExtensionFilters() {
    this.filters.clear();
    this.activeFilter = null;
  }
  
  /**
   * Link the specified {@link StructuredViewer} and {@link Combo} with
   * this filter. The {@link Combo} is used to display the list of all
   * available filters and to specified the currently active filter.
   * The viewer will be updated whenever a new active filter is selected.
   * Note that changes in the available filters are not forwarded to the
   * linked components. So make sure to set up all filters before linking
   * any components.
   * 
   * @param viewer The {@link StructuredViewer} to be linked to this
   * <code>GridConnectionFilter</code>.
   * @param combo The {@link Combo} to be linked to this
   * <code>GridConnectionFilter</code>.
   */
  public void link( final StructuredViewer viewer,
                    final Combo combo ) {
    
    viewer.addFilter( this );
    combo.removeAll();
    
    if ( this.filters.isEmpty() ) {
      combo.add( FILTER_PREFIX + WILDCARD_FILTER );
      combo.setText( FILTER_PREFIX + WILDCARD_FILTER );
    } else {
      for ( String filter : this.filters ) {
        combo.add( FILTER_PREFIX + filter );
      }
      combo.setText( FILTER_PREFIX + this.activeFilter );
    }
    
    combo.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        String filter = combo.getText();
        setActiveFilter( filter.substring( 2 ) );
        viewer.refresh();
      }
    } );
    
  }
  
  /**
   * Set the currently active filter, i.e. the filter that will be
   * applied to any associated {@link StructuredViewer}. If the specified
   * filter is not yet contained in the list of available filters it will
   * be added before setting it to be the active one.
   * 
   * @param filter The new currently active filter.
   */
  public void setActiveFilter( final String filter ) {
    if ( filter != null ) {
      addFileExtensionFilter( filter );
    }
    this.activeFilter = filter;
  }
  
  /**
   * Clear the list of currently available filters and set the
   * specified filter as the only available filter and therefore
   * also as the currently active filter.
   * 
   * @param filter The filter to be set.
   */
  public void setFileExtensionFilter( final String filter ) {
    clearFileExtensionFilters();
    addFileExtensionFilter( filter );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean select( final Viewer viewer,
                         final Object parentElement,
                         final Object element ) {
    boolean result = false;
    if ( element instanceof IGridConnectionElement ) {
      result = select( ( IGridConnectionElement  ) element );
    } else if ( ( element instanceof IGridContainer )
        || ( element instanceof ProgressTreeNode )
        || ( element instanceof ContainerMarker ) ) {
      result = true;
    }
    return result;
  }
  
  /**
   * Determine if the specified {@link IGridConnectionElement} should
   * be displayed due to the currently active filter. Connections that represent
   * folders are always displayed.
   *  
   * @param element The element to be tested against the currently active filter.
   * @return True if the element matches the active filter, no active filter
   * is currently defined or the element corresponds to a folder.
   */
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
          result
            = this.activeFilter.equals( WILDCARD_FILTER )
            || matches( extension, this.activeFilter );
        }
      }
    }
    return result;
  }
  
  /**
   * Determine if the specified file extension matches any filter
   * contained in the specified list.
   * 
   * @param extension The extension to be tested.
   * @param filterList A list of file extensions the specified
   * extension will be tested against.
   * @return True if the specified file extension matches any of
   * the specified filters.
   */
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
  
  /**
   * Determine if the specified file extension matches the
   * specified filter.
   * 
   * @param extension The extension to be tested.
   * @param filter the filter the specified file extension
   * is tested against.
   * @return True if extension and filter match each other.
   */
  private boolean matches( final String extension,
                           final String filter ) {
    return extension.equalsIgnoreCase( filter );
  }
  
}
