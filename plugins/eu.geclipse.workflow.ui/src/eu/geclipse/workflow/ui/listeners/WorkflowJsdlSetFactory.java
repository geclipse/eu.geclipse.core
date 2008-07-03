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

import org.eclipse.gef.requests.CreationFactory;

import eu.geclipse.workflow.ui.internal.WorkflowJobFigure;


/**
 * @author nloulloud
 *
 */
public class WorkflowJsdlSetFactory implements CreationFactory {
  
  
  private String text = ""; //$NON-NLS-1$

  /* (non-Javadoc)
   * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
   */
  public Object getNewObject() {
    
//    WorkflowJobFigure figure = new WorkflowJobFigure("TEXT FOR DRAG_N_DROP");
    System.out.println("TEST DND");
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
   */
  public Object getObjectType() {
    // TODO Auto-generated method stub
    return String.class;
  }
  
  public void setText( final String s){
    this.text = s;
  }
}
