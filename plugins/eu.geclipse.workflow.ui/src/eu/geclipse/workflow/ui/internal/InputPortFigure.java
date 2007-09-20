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
package eu.geclipse.workflow.ui.internal;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.RectangleFigure;


/**
 * @generated
 */
public class InputPortFigure extends RectangleFigure {

  /**
   * @generated
   */
  public InputPortFigure() {
    this.setBackgroundColor( ColorConstants.lightBlue );
    this.setSize( 1, 1 );
  }
  /**
   * @generated
   */
  private boolean myUseLocalCoordinates = false;

  /**
   * @generated
   */
  @Override
  protected boolean useLocalCoordinates() {
    return myUseLocalCoordinates;
  }

  /**
   * @generated
   */
  protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
    myUseLocalCoordinates = useLocalCoordinates;
  }
}