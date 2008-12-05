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

import org.eclipse.swt.widgets.Composite;





/**
 * @author sgirtel
 *
 */
public abstract class AbstractVisualisationWindow {

  /**
   * The file extension of the resource that this visualisation window knows how to render.
   */
  public static final String EXT_FILE_EXTENSION = "fileExt"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the name of the visualisation page implementation.
   */
  public static final String EXT_NAME = "name"; //$NON-NLS-1$

  /**
   *
   */
  public static final String EXT_SITE = "remote"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the class name of the AbstractVisualisationWindow
   * implementation.
   */
  public static final String EXT_VISUALISATION_PAGE_CLASS = "class"; //$NON-NLS-1$

  /**
   * The name of the element which contains the visualisation page specification.
   */
  public static final String EXT_VISUALISATION_PAGE_ELEMENT = "window"; //$NON-NLS-1$

  protected VisComposite viscomp = null;

  /**
   * Returns the name of the page's tab.
   *
   * @return the name of the page's tab.
   */
  public abstract String getTabName();


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
   * @param parent
   * @param style
   */
  public abstract void init( final Composite parent, final int style);

  /**
   * @return
   */
  public abstract boolean isRemoteSite();

  /**
   * Sets the name of the page's tab.
   *
   * @param name new name.
   */
  public abstract void setTabName( final String name );

  /**
   *
   */
  public abstract void stopClient();

}
