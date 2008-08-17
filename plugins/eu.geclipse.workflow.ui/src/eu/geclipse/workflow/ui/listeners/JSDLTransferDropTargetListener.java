/*******************************************************************************
 * Copyright (c) 2006 - 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - David Johnson - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Cursors;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import eu.geclipse.jsdl.JSDLJobDescription;

/**
 * @author david
 */
public class JSDLTransferDropTargetListener
  extends AbstractTransferDropTargetListener
{

  public JSDLTransferDropTargetListener( final EditPartViewer viewer ) {
    super( viewer, LocalSelectionTransfer.getInstance() );
  }

  @Override
  protected Request createTargetRequest() {
    DropObjectsRequest req = new DropObjectsRequest();
    return req;
  }

  protected final DropObjectsRequest getDropObjectsRequest() {
    return ( ( DropObjectsRequest ) getTargetRequest() );
  }

  protected List< JSDLJobDescription > getObjectsBeingDropped() {
    List< JSDLJobDescription > result = new ArrayList< JSDLJobDescription >();
    if ( getTransfer() instanceof LocalSelectionTransfer ) {
      LocalSelectionTransfer transfer = ( LocalSelectionTransfer ) getTransfer();
      IStructuredSelection selection = ( IStructuredSelection ) transfer.getSelection();
      List< ? > objects = selection.toList();
      for ( Object o : objects ) {
        if ( o instanceof JSDLJobDescription ) {
          result.add( ( JSDLJobDescription ) o );
        }
      }
    }
    return result;
  }

  @Override
  public void dragEnter( final DropTargetEvent event ) {
    super.dragEnter( event );
    handleDragEnter(); // called to properly initialize the effect
  }

  protected void handleDragEnter() {
    handleDragOver();
  }    

  @Override
  protected void handleDragOver() {
    // do nothing when we drag over!
  }

  @Override
  protected void handleDrop() {
    getViewer().setCursor( Cursors.WAIT );
    super.handleDrop();
    getViewer().setCursor(null);
    getViewer().select( getTargetEditPart() );
    getViewer().flush();
  }

  @Override
  protected void updateTargetRequest() {
    DropObjectsRequest request = getDropObjectsRequest();
    request.setLocation( getDropLocation() );
    request.setObjects( getObjectsBeingDropped() );
    request.setAllowedDetail( getCurrentEvent().operations );
  }

  protected boolean isDataTransfered() {
    return true;
  }

  protected Object getJavaObject( final TransferData data ) {
    return LocalSelectionTransfer.getInstance().nativeToJava( data );
  }

}