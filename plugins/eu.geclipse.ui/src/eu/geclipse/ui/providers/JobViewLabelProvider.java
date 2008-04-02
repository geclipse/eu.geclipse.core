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

package eu.geclipse.ui.providers;

import java.text.DateFormat;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.views.GridJobView;

/**
 * This is a specialized {@link GridModelLabelProvider} to be used
 * by the {@link GridJobView}. It additionally implements the
 * {@link ITableLabelProvider} to give the user more information
 * about his jobs.
 */
public class JobViewLabelProvider extends DecoratingLabelProvider
  implements ITableLabelProvider
{

  ITableLabelProvider tableLabelProvider;

  /**
   * 
   */
  public JobViewLabelProvider() {
    super( createPureLabelProvider(), PlatformUI.getWorkbench()
      .getDecoratorManager()
      .getLabelDecorator() );
    this.tableLabelProvider = ( ITableLabelProvider )getLabelProvider();
  }

  private static ILabelProvider createPureLabelProvider() {
    return new ElementManagerLabelProvider() {

      @Override
      protected String getColumnText( final IGridElement element,
                                      final int columnIndex )
      {
        String text = ""; //$NON-NLS-1$
        if( element instanceof IGridJob ) {
          IGridJob job = ( IGridJob )element;
          switch( columnIndex ) {            
            case 2: {
              IGridJobStatus status = job.getJobStatus();
              if( status != null ) {
                text = status.getName();
              }
              break;
            }
            case 3: {
              IGridJobStatus status = job.getJobStatus();
              if( status != null ) {
                text = status.getReason();
              }
              break;
            }
            case 4:
              if( job.getSubmissionTime() != null ) {
                text = DateFormat.getDateTimeInstance()
                  .format( job.getSubmissionTime() );              
              }
              
              break;
            case 5:
              if( job.getJobStatus() != null
                  && job.getJobStatus().getLastUpdateTime() != null )
              {
                text = DateFormat.getDateTimeInstance()
                  .format( job.getJobStatus().getLastUpdateTime() );
              }
            break;
            case 6:
              text = job.getID().getJobID();            
              break;
          }
        }
        return text;
      }
    };
  }

  public Image getColumnImage( final Object element, final int columnIndex ) {
    Image image = null;
    if( columnIndex == 0 ) {
      image = getImage( element );
    }
    return image;
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if( columnIndex == 0
        && element instanceof IGridJob ) {
      text = ((IGridJob)element).getJobName();      
    } else {
      text = this.tableLabelProvider.getColumnText( element, columnIndex );
    }
    
    return text;
  }
}
