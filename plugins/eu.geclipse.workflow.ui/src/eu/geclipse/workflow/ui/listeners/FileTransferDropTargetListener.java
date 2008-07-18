/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.workflow.ui.listeners;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import eu.geclipse.core.model.IGridElement;


/**
 * @author nloulloud
 *
 */
public class FileTransferDropTargetListener extends DiagramDropTargetListener {

  /**
   * @param viewer
   */
  public FileTransferDropTargetListener( final EditPartViewer viewer ) {
    super( viewer,  LocalSelectionTransfer.getInstance());
  }
 

  @Override
  protected List<IGridElement> getObjectsBeingDropped() {
    List<IGridElement> result = new ArrayList<IGridElement>();   
    if (getTransfer() instanceof LocalSelectionTransfer) {
      LocalSelectionTransfer transfer = (LocalSelectionTransfer) getTransfer();
      IStructuredSelection selection = (IStructuredSelection) transfer.getSelection();
      Object object = selection.getFirstElement();
      if(object instanceof IGridElement) {
        result.add( (IGridElement) object );
      }
    }
    return result;
  }


  protected Object getJavaObject(final TransferData data) {
    return LocalSelectionTransfer.getInstance().nativeToJava(data);
  }
  
    
}
