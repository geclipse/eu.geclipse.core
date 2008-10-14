/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.gvid;

import java.awt.Frame;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.gvid.internal.GVidClient;
import eu.geclipse.gvid.internal.GVidStatsEvent;
import eu.geclipse.gvid.internal.views.Messages;

public class GVidPage extends Composite implements IGVidStatsListener {
  IBidirectionalConnection connection;
  GVidClient gvidClient;
  Thread gvidThread;
  Label statsLabel;
  private final Frame awtFrame;
  private Composite SWT_AWT_container;
  private final CTabItem tabItem;

  public GVidPage( final Composite parent, final int style,  final CTabItem cTabItem ) {
    super( parent, style );
    this.tabItem = cTabItem;
    initialize();
    this.awtFrame = SWT_AWT.new_Frame( this.SWT_AWT_container );
  }

  public String getTabName() {
    return this.tabItem.getText();
  }

  public Composite getVisComp() {
    // TODO Auto-generated method stub
    return null;
  }

  public void init( final Composite parent, final int style ) {
    // TODO Auto-generated method stub

  }

  private void initialize() {
    GridLayout gridLayout = new GridLayout();
    gridLayout.horizontalSpacing = 0;
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 1;
    gridLayout.verticalSpacing = 0;
    GridData gridData1 = new GridData();
    gridData1.horizontalAlignment = GridData.FILL;
    gridData1.grabExcessHorizontalSpace = true;
    gridData1.grabExcessVerticalSpace = true;
    gridData1.verticalAlignment = GridData.FILL;
    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = GridData.CENTER;
    gridData.horizontalAlignment = GridData.FILL;
    setLayout(gridLayout);
    GridLayout layout = new GridLayout( 1, false );
    layout.marginWidth = 0;
    layout.marginHeight = 0;

    this.statsLabel = new Label( this, SWT.NONE );
    this.statsLabel.setLayoutData(gridData);
    this.SWT_AWT_container = new Composite( this, SWT.EMBEDDED );
    this.SWT_AWT_container.setLayout(new FillLayout());
    this.SWT_AWT_container.setLayoutData(gridData1);
  }

  public boolean isRemoteSite() {
    return true;
  }

  public void setTabName( final String name ) {
    this.tabItem.setText( name );
  }

  public void startClient( final IBidirectionalConnection conn ) throws IOException {
    this.connection = conn;
    this.gvidClient = new GVidClient( this.connection.getInputStream(),
                                      this.connection.getOutputStream() );
    this.gvidClient.addStatsListener( this );
    this.awtFrame.add( this.gvidClient );
    this.awtFrame.validate();
    this.gvidThread = new Thread( this.gvidClient,
                                  "GVid client thread" ); //$NON-NLS-1$
    this.gvidThread.start();
  }

  public void statsUpdated( final GVidStatsEvent event ) {
    final String transferStats =
      Messages.formatMessage( "GVidView.statsLine", //$NON-NLS-1$
                              Integer.valueOf( event.getFps() ),
                              Double.valueOf( ( int )( event.getSendSpeed() / 102.4 ) / 10.0 ),
                              Double.valueOf( ( int )( event.getRecvSpeed() / 102.4 ) / 10.0 ) );
    Display.getDefault().syncExec(new Runnable() {
      public void run () {
        GVidPage.this.statsLabel.setText( transferStats );
      }
    } );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.IVisualisationWindow#stopClient()
   */
  public void stopClient() {
    if( this.gvidClient != null ) {
      this.gvidClient.stop();
      try {
        this.gvidThread.join();
      } catch( InterruptedException exception ) {
        // ignore
      }
    }
    if( this.connection != null ) {
      this.connection.close();
    }
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
