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
import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class is simply an SWT Composite enhanced by the org.eclipse.swt.awt.SWT_AWT
 * bridge class, which makes it possible to embed AWT components in SWT and vice versa.
 * VisComposite contains SWT Composite and an an AWT Frame. After initialization of the
 * SWT Composite, the AWT Frame gets initialized through the bridge class, using the
 * SWT Composite as its parent. The subclasses of AbstractVisualisationWindow can then
 * make use of the addToAWTFrame methods to readily assign their graphical components
 * or a heavy weight component like the Canvas to this frame.
 *
 * @author sgirtel
 */
public class VisComposite extends Composite {

  private Label statsLabel = null;
  private final Frame awtFrame;
  private Composite SWT_AWT_composite;

  /**
   * @param vis
   * @param style
   */
  public VisComposite( final Composite vis,
                       final int style ) {
    super( vis, style );//SWT.DOUBLE_BUFFERED
    initSwtAwtComposite();
    // create a new frame and add it to the composite created with embedded
    // style
    this.awtFrame = SWT_AWT.new_Frame( this.SWT_AWT_composite );
  }

  private void initSwtAwtComposite() {
    GridLayout gridLayout = new GridLayout( 2, false );
    setLayout( gridLayout );
    GridData gridData1 = new GridData();
    gridData1.grabExcessHorizontalSpace = true;
    gridData1.grabExcessVerticalSpace = true;
    gridData1.horizontalAlignment = SWT.FILL;
    gridData1.verticalAlignment = SWT.FILL;
    this.SWT_AWT_composite = new Composite( this, SWT.EMBEDDED );
    this.SWT_AWT_composite.setLayout( new FillLayout() );
    this.SWT_AWT_composite.setLayoutData( gridData1 );

    gridLayout = new GridLayout();
    gridLayout.horizontalSpacing = 0;
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 1;
    gridLayout.verticalSpacing = 0;
    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = GridData.CENTER;
    gridData.horizontalAlignment = GridData.FILL;
    setLayout(gridLayout);
    GridLayout layout = new GridLayout( 1, false );
    layout.marginWidth = 0;
    layout.marginHeight = 0;

    this.setStatsLabel( new Label( this, SWT.NONE ) );
    this.getStatsLabel().setLayoutData(gridData);

  }

  /**
   * @param panel
   */
  public void addToAWTFrame( final Canvas panel ) {
    panel.setSize( this.awtFrame.getSize() );
    this.awtFrame.add( panel );
    this.awtFrame.pack();
    this.awtFrame.validate();
  }

  /**
   * @param panel
   */
  public void addToAWTFrame( final Component panel ) {
    this.awtFrame.add( panel );
    this.awtFrame.pack();
    this.awtFrame.validate();
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Widget#dispose()
   */
  @Override
  public void dispose() {
    super.dispose();
  }

  /**
   * @param statsLabel
   */
  public void setStatsLabel( final Label statsLabel ) {
    this.statsLabel = statsLabel;
  }

  /**
   * @return
   */
  public Label getStatsLabel() {
    return this.statsLabel;
  }

}