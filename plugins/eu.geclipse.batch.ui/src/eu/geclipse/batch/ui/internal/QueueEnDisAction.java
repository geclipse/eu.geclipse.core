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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;
import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IQueueInfo.QueueState;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPart;
import eu.geclipse.batch.ui.internal.parts.QueueEditPart;
import eu.geclipse.ui.dialogs.NewProblemDialog;

/**
 * Class that handles the actions that can be applied to the Batch queues.
 */
public class QueueEnDisAction extends SelectionAction {
  /**
   * The identifier for the action
   */
  public static final String PROPERTY_QUEUE_ACTION_ENABLE_DISABLE = "QueueEnDisAction.EnableDisable"; //$NON-NLS-1$

  private IBatchService batchWrapper;
  private boolean enable;
  private ImageDescriptor imgEnable;
  private ImageDescriptor imgDrain;
  
  /**
   * Construct a new <code>QueueAction</code>.
   *
   * @param part The workbench where this action will be performed.
   * @param batchWrapper The wrapper to the batch service.
   */
  public QueueEnDisAction( final IWorkbenchPart part, final IBatchService batchWrapper ) {
    super( part );

    this.batchWrapper = batchWrapper;

    this.setId( QueueEnDisAction.PROPERTY_QUEUE_ACTION_ENABLE_DISABLE );
    this.setToolTipText( Messages.getString( "QueueEnDisAction.Msg.EnableDisableTips" ) ); //$NON-NLS-1$

    this.imgEnable = Activator.getDefault().getImageRegistry().getDescriptor( Activator.IMG_ENABLE ); 
    this.imgDrain = Activator.getDefault().getImageRegistry().getDescriptor( Activator.IMG_DRAIN ); 
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    String strName;
    Queue q;
    
    for ( Object o : getSelectedObjects() ) {
      if ( o instanceof BatchTreeEditPart ) {
        final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
        q = ( Queue ) treeEditPart.getModel();
      } else if ( o instanceof QueueEditPart ) {
        QueueEditPart qEdit = ( QueueEditPart ) o;
        q = ( Queue ) qEdit.getModel();
      } else
        break;
      
      strName = q.getQueneName();
      try {
        if ( this.enable )
          this.batchWrapper.enableQueue( strName );
        else
          this.batchWrapper.disableQueue( strName );
      } catch( BatchException excp ) {
        // Action could not be performed
        NewProblemDialog.openProblem( this.getWorkbenchPart().getSite().getShell(),
                                      Messages.getString( "QueueEnDisAction.error_manipulate_title" ),  //$NON-NLS-1$
                                      Messages.getString( "QueueEnDisAction.error_manipulate_message" ), //$NON-NLS-1$
                                      excp );      
      }
    }
  }

  /**
   * Determines if the action is applicable. If multiple objects are selected,
   * they must all be in the same state for the action to be applicable.
   * returns Returns <code>true</code> if the actions can be applied,
   * <code>false</code> otherwise.
   */
  @Override
  protected boolean calculateEnabled() {
    boolean result;
    boolean firstTime = true;
    boolean enableAll = true;
    Queue q = null; 

    result = !getSelectedObjects().isEmpty();

    if ( result ) {
      for ( Object o : getSelectedObjects() ) {
        if ( o instanceof BatchTreeEditPart ) {
          final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
          Object oModel = treeEditPart.getModel();
          
          if ( ! ( oModel instanceof Queue ) ) {
            result = false;
            break;
          }
          q = ( Queue ) oModel;
          
        } else if ( o instanceof QueueEditPart ) {
          final QueueEditPart qEdit = ( QueueEditPart ) o;
          q = ( Queue ) qEdit.getModel();
        } else {
          result = false;
          break;
        }

        if ( firstTime ) {
          if ( q.getState() == QueueState.E )
            enableAll = false;
          firstTime = false;
        } else {
          if ( ( enableAll && q.getState() == QueueState.E ) 
              || ( !enableAll && q.getState() == QueueState.D ) ) {
            result = false;
            break;
          }
        }
      }
      // Set the text in the context menu
      if ( enableAll ) {
        this.setText( Messages.getString( "QueueEnDisAction.Msg.EnableText" ) ); //$NON-NLS-1$
        this.setImageDescriptor( this.imgEnable );
        this.enable = true;
      }
      else {
        this.setText( Messages.getString( "QueueEnDisAction.Msg.DisableText" ) ); //$NON-NLS-1$
        this.setImageDescriptor( this.imgDrain );
        this.enable = false;
      }
    }
    return result;
  }
}


