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

package eu.geclipse.gvid.internal.views;

import java.io.IOException;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.gvid.GVidPage;
import eu.geclipse.gvid.IGVidView;

/**
 * View for displaying the output of remote visualisation applications.
 */
public class GVidView extends ViewPart implements IGVidView {
  private CTabFolder cTabFolder;
  private NewGVidDropDownAction newGVidAction;

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalToolBar( bars.getToolBarManager() );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    manager.add( this.newGVidAction );

// TODO maybe move TCP implemenation into a seperate class or remove it
/*    manager.add( new Action() {
      @Override
      public String getToolTipText() {
        return "Create TCP/IP connection";
      }

      @Override
      public ImageDescriptor getImageDescriptor() {
        return PlatformUI.getWorkbench()
                         .getSharedImages()
                         .getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD );
      }

      @Override
      public void run() {
        try {
          InputDialog inputDialog = new InputDialog( null,
                                                     Messages.getString( "GVidView.gvid" ), //$NON-NLS-1$
                                                     Messages.getString( "GVidView.enterRemoteAddress" ), //$NON-NLS-1$
                                                     "", //$NON-NLS-1$
                                                     null );
          if ( inputDialog.open() == Window.OK ) {
            String host = inputDialog.getValue();
            int port = 2345;
             if( host.indexOf( ':' ) != -1 ) {
              port = new Integer( host.substring( host.indexOf( ':' ) + 1 ) ).intValue();
              host = host.substring( 0, host.indexOf( ':' ) );
            }
            final Socket socket = new Socket( host, port );
            final InputStream in = socket.getInputStream();
            final OutputStream out = socket.getOutputStream();

            IGVidPage page = addGVidPage( new IBidirectionalConnection() {
              public void close() {
                try {
                  socket.close();
                } catch( IOException ioException ) {
                  // ignore
                }
              }
              public InputStream getInputStream() {
                return in;
              }
              public OutputStream getOutputStream() {
                return out;
              }
            } );
            page.setTabName( host );
          }
        } catch( IOException ioException ) {
          Status status = new Status( IStatus.ERROR,
                                      Messages.getString( "GVidView.gvid" ), //$NON-NLS-1$
                                      0,
                                      Messages.getString( "GVidView.couldNotCreateConnection" ), //$NON-NLS-1$
                                      ioException );
          ErrorDialog.openError( Display.getCurrent().getActiveShell(),
                                 null,
                                 Messages.getString( "GVidView.couldNotCreateConnection" ), //$NON-NLS-1$
                                 status );
        }
      }
    } );*/
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl( final Composite parent ) {
    this.newGVidAction = new NewGVidDropDownAction( this );
    contributeToActionBars();
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    gridData.verticalAlignment = GridData.FILL;
    this.cTabFolder = new CTabFolder( parent, SWT.FLAT | SWT.BOTTOM );
    this.cTabFolder.setLayoutData( gridData );
    this.cTabFolder.addFocusListener( new FocusAdapter () {
      @Override
      public void focusGained( final FocusEvent event ) {
        CTabFolder folder = (CTabFolder) event.widget;
        CTabItem item = folder.getSelection();
        if( item != null && item.getControl() != null ) {
          item.getControl().setFocus();
        }
      }
    } );
    this.cTabFolder.addCTabFolder2Listener( new CTabFolder2Adapter() {
      @Override
      public void close( final CTabFolderEvent event ) {
        ((GVidPage)((CTabItem)event.item).getControl()).stopClient();
      }
    } );
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    if ( this.cTabFolder != null ) {
      this.cTabFolder.setFocus();
    }
  }

  public GVidPage addGVidPage( final IBidirectionalConnection connection ) throws IOException {
    CTabItem cTabItem = new CTabItem( this.cTabFolder, SWT.CLOSE );
    final GVidPage page = new GVidPage( this.cTabFolder, SWT.NONE, cTabItem );
    page.startClient( connection );
    cTabItem.setControl( page );
    this.cTabFolder.showItem( cTabItem );
    return page;
  }
}
