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

import java.net.URL;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;


/**
 * A GEF figure for an InputPort.
 */
public class InputPortFigure extends RectangleFigure {

  private boolean myUseLocalCoordinates = false;
  
  private Image img;
  
  public ImageDescriptor getImageDescriptor( final Object resource ) {
    URL imgUrl = WorkflowDiagramEditorPlugin.getDefault().getBundle().getEntry( "icons/obj16/InputPort.gif" ); //$NON-NLS-1$
    return ImageDescriptor.createFromURL( imgUrl );
  }

/*  public InputPortFigure() {    
    ImageDescriptor imgdesc = getImageDescriptor( null );
    img = imgdesc.createImage();
    this.add( img );
  }*/

  /**
   * Default Constructor
   */
  public InputPortFigure() {
    this.setBackgroundColor( ColorConstants.lightBlue );
    this.setSize( 10, 10 );
  }
  
  protected void setUseLocalCoordinates( boolean useLocalCoordinates ) {
    this.myUseLocalCoordinates = useLocalCoordinates;
  }

  @Override
  protected boolean useLocalCoordinates() {
    return this.myUseLocalCoordinates;
  }

}