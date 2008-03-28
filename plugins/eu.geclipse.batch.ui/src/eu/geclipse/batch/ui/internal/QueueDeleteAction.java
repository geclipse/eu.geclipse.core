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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IQueueInfo.QueueRunState;
import eu.geclipse.batch.IQueueInfo.QueueState;
import eu.geclipse.batch.ui.internal.model.Queue;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPart;
import eu.geclipse.batch.ui.internal.parts.QueueEditPart;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 *
 */
public class QueueDeleteAction extends SelectionAction {
  /**
   * The identifier for the action
   */
  public static final String PROPERTY_QUEUE_ACTION_DELETE = "QueueAction.DeleteQueue"; //$NON-NLS-1$

  private IBatchService batchWrapper;

  /**
   * Construct a new <code>QueueDeleteAction</code>.
   *
   * @param part The workbench where this action will be performed.
   * @param batchWrapper The wrapper to the batch service.
   */
  public QueueDeleteAction( final IWorkbenchPart part, final IBatchService batchWrapper ) {
    super( part );

    this.batchWrapper = batchWrapper;

    this.setId( QueueDeleteAction.PROPERTY_QUEUE_ACTION_DELETE );
    
    this.setToolTipText( Messages.getString( "QueueDeleteAction.Msg.DeleteTips" ) ); //$NON-NLS-1$
    this.setText( Messages.getString( "QueueDeleteAction.Msg.Delete" ) ); //$NON-NLS-1$
    this.setImageDescriptor( PlatformUI.getWorkbench()
                             .getSharedImages()
                             .getImageDescriptor( ISharedImages.IMG_TOOL_DELETE ) );    
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Queue q;
    
    // This is a none reversible action 
    boolean confirm = !MessageDialog.openConfirm( this.getWorkbenchPart().getSite().getShell(),
                                                  Messages.getString( "QueueDeleteAction.confirm_delete_title" ), //$NON-NLS-1$
                                                  Messages.getString( "QueueDeleteAction.confirm_delete_message" ) );  //$NON-NLS-1$
    if ( !confirm ) {
      for ( Object o : getSelectedObjects() ) {
        if ( o instanceof BatchTreeEditPart ) {
          final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
          q = ( Queue ) treeEditPart.getModel();
        } else if ( o instanceof QueueEditPart ) {
          QueueEditPart qEdit = ( QueueEditPart ) o;
          q = ( Queue ) qEdit.getModel();
        } else
          break;

        try {
          this.batchWrapper.delQueue( q.getQueneName() );
        } catch( ProblemException excp ) {
          // Action could not be performed
          ProblemDialog.openProblem( this.getWorkbenchPart().getSite().getShell(),
                                     Messages.getString( "QueueDeleteAction.error_manipulate_title" ),  //$NON-NLS-1$
                                     Messages.getString( "QueueDeleteAction.error_manipulate_message" ), //$NON-NLS-1$
                                     excp );      
        }
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

        if ( q.getState() != QueueState.D || q.getRunState() != QueueRunState.S || !q.isQueueEmpty() ) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
  
}
