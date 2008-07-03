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

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;


/**
 * @author nloulloud
 *
 */
public class FileTransferDropTargetListener extends AbstractTransferDropTargetListener {
  
  private WorkflowJsdlSetFactory factory = new WorkflowJsdlSetFactory();

  public FileTransferDropTargetListener( final EditPartViewer viewer, final Transfer xfer )  {
    super( viewer, xfer );
  }

  public FileTransferDropTargetListener(final EditPartViewer viewer) {
    super(viewer, FileTransfer.getInstance());
 }

  @Override
  protected void updateTargetRequest() {
    ((CreateRequest)getTargetRequest()).setLocation(getDropLocation());
  }
  
  @Override
  protected Request createTargetRequest() {
    CreateRequest request = new CreateRequest();
    request.setFactory(this.factory);
    return request;
 }
  
  @Override
  protected void handleDragOver() {
    getCurrentEvent().detail = DND.DROP_COPY;
    super.handleDragOver(); 
 }
  
  @Override
  protected void handleDrop() {
    String s = ((String[])getCurrentEvent().data)[0];
    String separator = System.getProperty("file.separator"); //$NON-NLS-1$
    s = s.substring(s.lastIndexOf(separator) + 1);
    this.factory.setText(s);
    super.handleDrop();
 }
  
}
