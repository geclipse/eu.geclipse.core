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
 *    Pawel Wolniewicz, PSNC
 *****************************************************************************/

package eu.geclipse.ui.decorators;

import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;

import eu.geclipse.core.jobs.GridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.internal.Activator;

/**
 * Addes an error decorator to RSL icon in case the file can not be parsed.
 */
public class GridJobDecorator implements ILightweightLabelDecorator {

  private static Hashtable<Integer, String> imageNames;
  
  private List<ILabelProviderListener> listeners = new LinkedList<ILabelProviderListener>();

  static {
  imageNames=new Hashtable<Integer, String>();
  imageNames.put( IGridJobStatus.UNKNOWN, "status_unknown.gif" ); 
  imageNames.put( IGridJobStatus.SUBMITTED, "status_submitted.gif" ); 
  imageNames.put( IGridJobStatus.WAITING, "status_waiting.gif" ); 
  imageNames.put( IGridJobStatus.RUNNING, "status_running.gif" ); 
  imageNames.put( IGridJobStatus.DONE, "status_done.gif" ); 
  imageNames.put( IGridJobStatus.ABORTED, "status_aborted.gif" ); 
  imageNames.put( IGridJobStatus.ABANDONED, "status_abandoned.gif" ); 
  }
  
  public void decorate( final Object element, final IDecoration decoration ) {
    if ( element instanceof GridJob ) {
      IGridJobStatus status = ((GridJob)element).getJobStatus();      
      ImageDescriptor decorator = getIcon( status.getType() );
      decoration.addOverlay( decorator, IDecoration.BOTTOM_LEFT );
    }

  }

  private ImageDescriptor getIcon( final int type ) {
    ImageDescriptor decorator=null;
    String fileName=imageNames.get( type );
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/ovr16/"+fileName ); //$NON-NLS-1$
    decorator= ImageDescriptor.createFromURL( imgUrl );
    return decorator;
  }

  public void addListener( final ILabelProviderListener listener ) {
    this.listeners.add( listener );
  }

  public void dispose() {
    // not needed
  }

  public boolean isLabelProperty( final Object element, final String property ) {
    return true;
  }

  public void removeListener( final ILabelProviderListener listener ) {
    this.listeners.remove( listener );
  }
  
  /**
   * Inform all listeners of a change.
   */
  public void updateListeners() {
    for ( ILabelProviderListener listener : this.listeners ) {
      listener.labelProviderChanged( new LabelProviderChangedEvent(this) );
    }
  }
}
