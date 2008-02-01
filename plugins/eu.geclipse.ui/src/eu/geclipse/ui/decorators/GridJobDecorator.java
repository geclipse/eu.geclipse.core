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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.jobs.GridJob;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.internal.Activator;

/**
 * Adds an error decorator to RSL icons in case the file can not be parsed.
 */
public class GridJobDecorator
    extends LabelProvider
    implements ILightweightLabelDecorator {
  
  private static final String ID
    = "eu.geclipse.ui.gridJobDecorator"; //$NON-NLS-1$
  
  private static final String STATUS_UNKNOWN_IMG
    = "status_unknown.gif"; //$NON-NLS-1$
  
  private static final String STATUS_SUBMITTED_IMG
    = "status_submitted.gif"; //$NON-NLS-1$
  
  private static final String STATUS_WAITING_IMG
    = "status_waiting.gif"; //$NON-NLS-1$
  
  private static final String STATUS_RUNNING_IMG
    = "status_running.gif"; //$NON-NLS-1$
  
  private static final String STATUS_DONE_IMG
    = "status_done.gif"; //$NON-NLS-1$
  
  private static final String STATUS_ABORTED_IMG
    = "status_aborted.gif"; //$NON-NLS-1$
  
  private static final String STATUS_ABANDONED_IMG
    = "status_abandoned.gif"; //$NON-NLS-1$

  private static Hashtable<Integer, String> imageNames;
  
  static {
    imageNames = new Hashtable< Integer, String >();
    imageNames.put( Integer.valueOf( IGridJobStatus.UNKNOWN ), STATUS_UNKNOWN_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.SUBMITTED ), STATUS_SUBMITTED_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.WAITING ), STATUS_WAITING_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.RUNNING ), STATUS_RUNNING_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.DONE ), STATUS_DONE_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.ABORTED ), STATUS_ABORTED_IMG ); 
    imageNames.put( Integer.valueOf( IGridJobStatus.PURGED ), STATUS_ABANDONED_IMG ); 
  }
  
  public static GridJobDecorator getDecorator() {
    
    GridJobDecorator result = null;
    IDecoratorManager decoratorManager = PlatformUI.getWorkbench().getDecoratorManager();
    
    if ( decoratorManager.getEnabled( ID ) ) {
      result = 
        ( GridJobDecorator ) decoratorManager.getBaseLabelProvider( ID );
    }
    
    return result;
  }
  
  public void decorate( final Object element, final IDecoration decoration ) {
    if ( element instanceof GridJob ) {
      IGridJobStatus status = ( ( GridJob ) element ).getJobStatus();      
      ImageDescriptor decorator = getIcon( status.getType() );
      decoration.addOverlay( decorator, IDecoration.BOTTOM_LEFT );
    }
  }
  
  public void refresh( final IGridElement toUpdate ) {
    LabelProviderChangedEvent event
      = toUpdate == null 
        ? new LabelProviderChangedEvent( getDecorator() )
        : new LabelProviderChangedEvent( getDecorator(), toUpdate );
    fireLabelProviderChanged( event );
  }
  
  public void refresh( final IGridElement[] toUpdate ) {
    LabelProviderChangedEvent event
      = toUpdate == null || toUpdate.length == 0
        ? new LabelProviderChangedEvent( getDecorator() )
        : new LabelProviderChangedEvent( getDecorator(), toUpdate );
    fireLabelProviderChanged( event );
  }

  private ImageDescriptor getIcon( final int type ) {
    ImageDescriptor decorator = null;
    String fileName = imageNames.get( Integer.valueOf( type ) );
    if( fileName == null ) {
      fileName = imageNames.get( Integer.valueOf( STATUS_UNKNOWN_IMG ) );
    }
    URL imgUrl = Activator.getDefault().getBundle()
                   .getEntry( "icons/ovr16/" + fileName ); //$NON-NLS-1$
    decorator = ImageDescriptor.createFromURL( imgUrl );
    return decorator;
  }

}
