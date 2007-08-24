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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.terminal.ITerminalListener;
import eu.geclipse.terminal.ITerminalPage;
import eu.geclipse.terminal.ITerminalView;
import eu.geclipse.terminal.internal.preferences.PreferenceConstants;

/**
 * View containing VT100 terminal emulators. Terminals can be added by creating
 * a terminal factory plugin.
 */
public class TerminalView extends ViewPart implements ITerminalView {
  CTabFolder cTabFolder = null;
  SelectionProviderIntermediate selectionProvider;
  private NewTerminalDropDownAction newTerminalAction;
    
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
    IActionBars actionBars = getViewSite().getActionBars();
    actionBars.setGlobalActionHandler( ActionFactory.PASTE.getId(), new Action() {
      @Override
      public void run() {
        CTabItem item = TerminalView.this.cTabFolder.getSelection();
        if( item != null && item.getControl() != null ) {
          ((TerminalPage)item.getControl()).paste();
        }
      }
    } );
    actionBars.setGlobalActionHandler( ActionFactory.COPY.getId(), new Action() {
      @Override
      public void run() {
        // TODO disable menu if selection is empty
        CTabItem item = TerminalView.this.cTabFolder.getSelection();
        if( item != null && item.getControl() != null ) {
          ((TerminalPage)item.getControl()).copy();
        }
      }
    } );
    this.selectionProvider = new SelectionProviderIntermediate();
    getSite().setSelectionProvider( this.selectionProvider );
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

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalView#addTerminal(java.io.InputStream,
   *      java.io.OutputStream)
   */
  public ITerminalPage addTerminal( final IBidirectionalConnection connection,
                                    final ITerminalListener termListener ) throws IOException {
    final CTabItem cTabItem = new CTabItem( this.cTabFolder, SWT.CLOSE );
    TerminalPage page = new TerminalPage( this.cTabFolder, SWT.NONE, cTabItem );
    page.setConnection( connection );
    page.addTerminalListener( termListener );
    page.addTerminalListener( new ITerminalListener() {
      /* (non-Javadoc)
       * @see eu.geclipse.terminal.ITerminalListener#terminated()
       */
      public void terminated() {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        boolean closeTabs = store.getBoolean( PreferenceConstants.P_CLOSE_TABS );
        if ( closeTabs ) { 
          Display.getDefault().syncExec( new Runnable() {
            /* (non-Javadoc)
             * @see java.lang.Runnable#run()
             */
            public void run() {
              cTabItem.dispose();
            }
          } );
        }
      }

      /* (non-Javadoc)
       * @see eu.geclipse.terminal.ITerminalListener#windowSizeChanged(int, int, int, int)
       */
      public void windowSizeChanged( final int cols, final int lines,
                                     final int pixels, final int pixels2 ) {
        // not needed
      }

      /* (non-Javadoc)
       * @see eu.geclipse.terminal.ITerminalListener#windowTitleChanged(java.lang.String)
       */
      public void windowTitleChanged( final String windowTitle ) {
        // not needed
      }
    } );
    cTabItem.setControl( page );
    this.cTabFolder.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        TerminalView.this.selectionProvider.setSelectionProviderDelegate( 
          ((TerminalPage)(((CTabFolder)event.getSource()).getSelection()).getControl()).getTerminal());
          
      }
    } );
    this.cTabFolder.setSelection( cTabItem );
    this.selectionProvider.setSelectionProviderDelegate( page.getTerminal() );
    return page;
  }
}
