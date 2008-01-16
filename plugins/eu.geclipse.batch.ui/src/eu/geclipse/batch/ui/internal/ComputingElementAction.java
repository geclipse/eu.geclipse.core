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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPart;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.ui.internal.model.ComputingElement;
import eu.geclipse.batch.ui.internal.parts.BatchTreeEditPart;
import eu.geclipse.batch.ui.internal.parts.ComputingElementEditPart;
import eu.geclipse.batch.ui.wizards.AddQueueWizard;


/**
 * Class that handles the actions that can be applied to the Batch Computing Element.
 */
public class ComputingElementAction extends SelectionAction {
  /**
   * The identifier for the action
   */
  public static final String PROPERTY_COMPUTINGELEMENT_ACTION_NEWQUEUE = "ComputingElementAction.NewQueue"; //$NON-NLS-1$

  private IBatchService batchWrapper;

  /**
   * Construct a new <code>ComputingElementAction</code>.
   *
   * @param part The workbench where this action will be performed.
   * @param batchWrapper The wrapper to the batch service.
   */
  public ComputingElementAction( final IWorkbenchPart part, final IBatchService batchWrapper ) {
    super( part );

    this.batchWrapper = batchWrapper;

    this.setId( ComputingElementAction.PROPERTY_COMPUTINGELEMENT_ACTION_NEWQUEUE );
    this.setToolTipText( Messages.getString( "ComputingElementAction.Msg.NewQueueTips" ) ); //$NON-NLS-1$

    this.setImageDescriptor( Activator.getDefault().getImageRegistry().getDescriptor( Activator.IMG_NEWQUEUE ) );
    this.setText( Messages.getString( "ComputingElementAction.Msg.NewQueueText" ) ); //$NON-NLS-1$
  }


  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {

    for ( @SuppressWarnings("unused") Object o : getSelectedObjects() ) {
      AddQueueWizard wizard = new AddQueueWizard( this.batchWrapper );
      wizard.init( this.getWorkbenchPart().getSite().getWorkbenchWindow().getWorkbench(), ( IStructuredSelection )this.getSelection() );
     
      WizardDialog dialog = new WizardDialog ( this.getWorkbenchPart().getSite().getShell(), wizard );
      dialog.create();
      dialog.open();
    }
  }

  /**
   * Determines if the action is applicable. Only one CE can be selected at a 
   * time, for this action to be applicable. 
   * returns Returns <code>true</code> if the actions can be applied,
   * <code>false</code> otherwise.
   */
  @Override
  protected boolean calculateEnabled() {
    boolean result;

    result = !getSelectedObjects().isEmpty();

    if ( result ) {
      // Only one selected at a time
      if ( 1 > getSelectedObjects().size() ) {
        result = false;
      }
      else {
        for ( Object o : getSelectedObjects() ) {
          if ( o instanceof BatchTreeEditPart ) {
            final BatchTreeEditPart treeEditPart = (BatchTreeEditPart)o;
            Object oModel = treeEditPart.getModel();
            
            if ( ! ( oModel instanceof ComputingElement ) ) {
              result = false;
              break;
            }
          } else if ( ! ( o instanceof ComputingElementEditPart ) ) {
            result = false;
            break;
          }
        }
      }
    }
    return result;
  }
}
