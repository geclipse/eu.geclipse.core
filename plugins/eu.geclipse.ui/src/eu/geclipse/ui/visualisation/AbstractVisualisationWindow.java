/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Sylva Girtelschmid GUP, JKU - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.visualisation;

import java.awt.Canvas;
import java.awt.Component;

import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.IGridVisualisationWindow;

/**
 * @author sgirtel
 *
 */
public abstract class AbstractVisualisationWindow implements IGridVisualisationWindow {

  /**
   * Extension point which has to be implemented to provide customized visualisation windows for
   * visualisable elements (i.e. elements that extend the IGridVisualisation interface).
   */
  public static final String WINDOW_EXTENSION_POINT = "eu.geclipse.ui.visualisationWindow"; //$NON-NLS-1$

  /**
   * The file extension of the resource that this visualisation window knows how to render.
   */
  public static final String EXT_FILE_EXTENSION = "fileExtension"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the name of the visualisation window implementation.
   */
  public static final String EXT_NAME = "name"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the type of visualisation, e.g. local or remote.
   */
  public static final String EXT_TYPE = "type"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the class name of the AbstractVisualisationWindow
   * implementation.
   */
  public static final String EXT_VISUALISATION_WINDOW_CLASS = "class"; //$NON-NLS-1$

  /**
   * The name of the element which contains the visualisation window specification.
   */
  public static final String EXT_VISUALISATION_WIDNOW_ELEMENT = "window"; //$NON-NLS-1$


  protected VisComposite viscomp = null;

  /**
   * @return
   */
  public abstract boolean isRemoteSite();

  /**
   * @param parent
   * @param style
   */
  public void init( final Composite parent, final int style) {
    this.viscomp = new VisComposite( parent, style );
  }


  /**
   * @param canvas
   */
  public void addToAWTFrame( final Canvas canvas ) {
    this.viscomp.addToAWTFrame( canvas );
  }

  /**
   * @param component
   */
  public void addToAWTFrame( final Component component ) {
    this.viscomp.addToAWTFrame( component );
  }

  /**
   * @return
   */
  public Composite getVisComp() {
    return this.viscomp;
  }

  /**
   *
   */
  public void redraw() {
    this.viscomp.redraw();
  }

  /**
   * For remote visualisation using GVidClient, this method
   * should be overwritten calling the stop method of the client
   * joining the gvid thread and closing opened connection.
   * See example implementation in the GVidVisWindow class
   * in eu.geclipse.vtk.ui plugin.
   */
  public void stopClient() {
    //noop
  }

}
