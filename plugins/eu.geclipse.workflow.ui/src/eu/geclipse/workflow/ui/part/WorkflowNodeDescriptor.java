/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import org.eclipse.emf.ecore.EObject;

/**
 * @generated
 */
public class WorkflowNodeDescriptor {

  /**
   * @generated
   */
  private EObject myModelElement;
  /**
   * @generated
   */
  private int myVisualID;
  /**
   * @generated
   */
  private String myType;

  /**
   * @generated
   */
  public WorkflowNodeDescriptor( EObject modelElement, int visualID ) {
    myModelElement = modelElement;
    myVisualID = visualID;
  }

  /**
   * @generated
   */
  public EObject getModelElement() {
    return myModelElement;
  }

  /**
   * @generated
   */
  public int getVisualID() {
    return myVisualID;
  }

  /**
   * @generated
   */
  public String getType() {
    if( myType == null ) {
      myType = WorkflowVisualIDRegistry.getType( getVisualID() );
    }
    return myType;
  }
}
