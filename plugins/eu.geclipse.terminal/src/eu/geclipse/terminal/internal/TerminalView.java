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

package eu.geclipse.terminal.internal;

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
import eu.geclipse.terminal.ITerminalListener;
import eu.geclipse.terminal.ITerminalPage;
import eu.geclipse.terminal.ITerminalView;

/**
 * View containing VT100 terminal emulators. Terminals can be added by creating
 * a terminal factory plugin.
 */
public class TerminalView extends ViewPart implements ITerminalView {
  private NewTerminalDropDownAction newTerminalAction;
  private CTabFolder cTabFolder = null;

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl( final Composite parent ) {
    makeActions();
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
        ((TerminalPage)((CTabItem)event.item).getControl()).closeConnection();
      }
    } );
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalToolBar( bars.getToolBarManager() );
  }

  private void fillLocalToolBar( final IToolBarManager manager ) {
    manager.add( this.newTerminalAction );
  }

  private void makeActions() {
    this.newTerminalAction = new NewTerminalDropDownAction( this );
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    this.cTabFolder.setFocus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.terminal.views.ITerminalView#addTerminal(java.io.InputStream,
   *      java.io.OutputStream)
   */
  public ITerminalPage addTerminal( final IBidirectionalConnection connection,
                                    final ITerminalListener termListener ) throws IOException {
    CTabItem cTabItem = new CTabItem( this.cTabFolder, SWT.CLOSE );
    TerminalPage page = new TerminalPage( this.cTabFolder, SWT.NONE, cTabItem );
    page.setConnection( connection );
    page.setTerminalListener( termListener );
    cTabItem.setControl( page );
    this.cTabFolder.showItem( cTabItem );
    return page;
  }
}
