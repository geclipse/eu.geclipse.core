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
 *     
 *****************************************************************************/
package eu.geclipse.ui.views.filters;

import java.util.HashMap;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IMemento;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;

public class JobStatusFilter extends AbstractGridViewerFilter {
  private static final String MEMENTO_KEY_ENABLED = "Enabled";
  private static final String MEMENTO_TYPE_STATUS = "Status";  
  private static final String MEMENTO_KEY_STATUS_SHOW = "Show";
  
  private boolean enabled = true;
  private HashMap<Integer, Boolean> statusMap = new HashMap<Integer, Boolean>();
  
  public JobStatusFilter() {
  }
  
  public IGridFilter makeClone() throws CloneNotSupportedException {
    JobStatusFilter newFilter = (JobStatusFilter)super.clone();
    
    newFilter.statusMap = (HashMap<Integer, Boolean>)statusMap.clone();
    
    return newFilter;
  }
    
  public void setEnabled( boolean enabled ) {
    this.enabled = enabled;
  }
  
  public void setStatusState( int jobStatus, boolean showOnView ) {
    this.statusMap.put( Integer.valueOf( jobStatus ), Boolean.valueOf( showOnView ) );
  }

  @Override
  public boolean select( final Viewer viewer, final Object parentElement, final Object element )
  {
    boolean showOnView = true;
    
    if( element instanceof IGridJob ) {
      IGridJob job = (IGridJob) element; 
      
      if( this.enabled ) {
        int jobStatus = job.getJobStatus().getType();
        Boolean showOnViewBool = this.statusMap.get( Integer.valueOf( jobStatus ) );
        
        if( showOnViewBool == null ) {
          showOnView = getStatusState( IGridJobStatus.UNKNOWN );
        }
        else {
          showOnView = showOnViewBool.booleanValue();
        }
      }
    }
    
    return showOnView;
  }
  
  public boolean getStatusState( int jobStatus ) {
    Boolean showOnView = this.statusMap.get( Integer.valueOf( jobStatus ) );
    
    return showOnView == null || showOnView.booleanValue();
  }
  
  public void saveState( final IMemento filterMemento ) {    
    filterMemento.putInteger( MEMENTO_KEY_ENABLED, this.enabled ? 1 : 0 );

    for( Integer status : this.statusMap.keySet() ) {
      IMemento statusMemento = filterMemento.createChild( MEMENTO_TYPE_STATUS, status.toString() );
      
      statusMemento.putInteger( MEMENTO_KEY_STATUS_SHOW, getStatusState( status.intValue() ) ? 1 : 0 ); 
    }
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void readState( final IMemento filterMemento ) {
    this.enabled = readBoolean( filterMemento, MEMENTO_KEY_ENABLED );
    IMemento[] statusMementos = filterMemento.getChildren( MEMENTO_TYPE_STATUS );
    for( IMemento statusMemento : statusMementos ) {
      Integer statusValue = Integer.valueOf( statusMemento.getID() );
      Integer showOnView = statusMemento.getInteger( MEMENTO_KEY_STATUS_SHOW );
      if( statusValue != null
          && showOnView != null ) {
        setStatusState( statusValue.intValue(), showOnView.intValue() != 0 );
      }
    }    
  }

  static String getId()
  {
    return "JobStatusFilter";
  }

  public ViewerFilter getFilter() {
    return this;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.filters.IGridFilter#getFilterId()
   */
  public String getFilterId() {
    return getId();
  }

}
