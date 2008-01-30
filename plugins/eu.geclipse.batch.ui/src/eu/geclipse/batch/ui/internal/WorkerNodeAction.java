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


import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState;
import eu.geclipse.batch.ui.internal.model.WorkerNode;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPart;
import eu.geclipse.batch.ui.internal.parts.WorkerNodeEditPart;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Action for enabling or disabling a worker node.
 */
public class WorkerNodeAction extends SelectionAction {
  /**
   * The identifier for the action
   */
  public static final String PROPERTY_WN_ACTION_CHANGESTATUS = "WorkerNodeAction.ChangeStatus"; //$NON-NLS-1$

  private IBatchService batchWrapper;
  private boolean enable;
  private ImageDescriptor imgEnable;

  /**
   * Construct a new <code>WorkerNodeAction</code>.
   *
   * @param part The workbench where this action will be performed.
   * @param batchWrapper The wrapper to the batch service.
   */
  public WorkerNodeAction( final IWorkbenchPart part, final IBatchService batchWrapper ) {
    super( part );

    this.batchWrapper = batchWrapper;

    this.setId( WorkerNodeAction.PROPERTY_WN_ACTION_CHANGESTATUS );
    this.setToolTipText( Messages.getString( "WorkerNodeAction.Msg.ChangeStatusTips" ) ); //$NON-NLS-1$
    this.imgEnable = Activator.getDefault().getImageRegistry().getDescriptor( Activator.IMG_ENABLE ); 
  }


  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    String strName;
    WorkerNode wn;
    
    for ( Object o : getSelectedObjects() ) {
      if ( o instanceof BatchTreeEditPart ) {
        final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
        wn = ( WorkerNode ) treeEditPart.getModel();
      } else if ( o instanceof WorkerNodeEditPart ) {
        WorkerNodeEditPart wnEdit = ( WorkerNodeEditPart ) o;
        wn = ( WorkerNode ) wnEdit.getModel();
      } else
        break;

      strName = wn.getFQDN();

      try {
        if ( this.enable )
          this.batchWrapper.enableWN( strName );
        else
          this.batchWrapper.disableWN( strName );
      } catch( ProblemException excp ) {
        // Action could not be performed
        ProblemDialog.openProblem( this.getWorkbenchPart().getSite().getShell(),
                                   Messages.getString( "WorkerNodeAction.error_manipulate_title" ),  //$NON-NLS-1$
                                   Messages.getString( "WorkerNodeAction.error_manipulate_message" ), //$NON-NLS-1$
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
    WorkerNode wn = null; 

    result = !getSelectedObjects().isEmpty();

    if ( result ) {
      for ( Object o : getSelectedObjects() ) {
        if ( o instanceof BatchTreeEditPart ) {
          final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
          Object oModel = treeEditPart.getModel();
          
          if ( ! ( oModel instanceof WorkerNode ) ) {
            result = false;
            break;
          }
          wn = ( WorkerNode ) oModel;
          
        } else if ( o instanceof WorkerNodeEditPart ) {
          final WorkerNodeEditPart wnEdit = ( WorkerNodeEditPart ) o;
          wn = ( WorkerNode ) wnEdit.getModel();
        } else {
          result = false;
          break;
        }

        if ( firstTime ) {
          if ( wn.getState() == WorkerNodeState.free 
              || wn.getState() == WorkerNodeState.job_exclusive 
              || wn.getState() == WorkerNodeState.busy )
            enableAll = false;
          firstTime = false;
        } else {
          if ( ( enableAll && ( wn.getState() == WorkerNodeState.free 
                                || wn.getState() == WorkerNodeState.job_exclusive  
                                || wn.getState() == WorkerNodeState.busy ) )  
              || ( !enableAll && ( wn.getState() == WorkerNodeState.down 
                                || wn.getState() == WorkerNodeState.offline ) ) ) { 
            result = false;
            break;
          }
        }
      }
      // Set the text in the context menu
      if ( enableAll ) {
        this.setText( Messages.getString( "WorkerNodeAction.Msg.EnableText" ) ); //$NON-NLS-1$
        this.setImageDescriptor( this.imgEnable );
        this.enable = true;
      }
      else {
        this.setText( Messages.getString( "WorkerNodeAction.Msg.DisableText" ) ); //$NON-NLS-1$
        this.setImageDescriptor( PlatformUI.getWorkbench()
                                 .getSharedImages()
                                 .getImageDescriptor( ISharedImages.IMG_TOOL_DELETE ) );
        this.enable = false;
      }
    }
    return result;
  }
}
