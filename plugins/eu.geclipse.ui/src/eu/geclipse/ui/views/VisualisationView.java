/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium
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
package eu.geclipse.ui.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 * @author sgirtel
 *
 */
public class VisualisationView extends ViewPart {

  IAction checkBtnAction = null;
  private CTabFolder cTabFolder;
  private IGridVisualisation vtkPipeline = null;
  private int allowedNumOfTabs = 10;
//  private final VisViewDropDownAction fileDropDownAction = null;

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {

    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    gridData.verticalAlignment = GridData.FILL;
    this.cTabFolder = new CTabFolder( parent, SWT.FLAT | SWT.BOTTOM );
    this.cTabFolder.setLayoutData( gridData );
    this.cTabFolder.addFocusListener( new FocusAdapter() {

      @Override
      public void focusGained( final FocusEvent event ) {
        CTabFolder folder = ( CTabFolder )event.widget;
        CTabItem item = folder.getSelection();
        if( ( item != null ) && ( item.getControl() != null ) ) {
          item.getControl().setFocus();
        }
      }
    } );
    this.cTabFolder.addCTabFolder2Listener( new CTabFolder2Adapter() {
      @Override
      public void close( final CTabFolderEvent event ) {
        ((IVisualisationPage)((CTabItem)event.item).getControl()).stopClient();
      }
    } );

//    this.fileDropDownAction =
//      new VisViewDropDownAction( getSite().getWorkbenchWindow() );
//    this.multipleTabsCheckBtn = new Button( parent, SWT.CHECK );
//    this.multipleTabsCheckBtn
//    .setText( Messages.getString( "VisualisationView.allowUpdatesIntoTheSameTab" ) ); //$NON-NLS-1$
//    this.multipleTabsCheckBtn
//    .setToolTipText( Messages.getString( "VisualisationView.allowUpdatesIntoTheSameTabTooltip" ) ); //$NON-NLS-1$
//    this.multipleTabsCheckBtn.addSelectionListener( new SelectionAdapter() {
//    
//      @Override
//      public void widgetSelected( SelectionEvent e ) {
////        super.widgetSelected( e );
//        if ( VisualisationView.this.multipleTabsCheckBtn.isEnabled() ) {
//          VisualisationView.this.multipleTabsCheckBtn.setEnabled( false );
//        }
//        else {
//          VisualisationView.this.multipleTabsCheckBtn.setEnabled( true );
//        }
//      }
//    } );
    
//    hookContextMenu();
//    contributeToActionBars();
    createToolBar();
  }

private void createToolBar() {
  this.checkBtnAction = 
    new org.eclipse.jface.action.Action( 
               Messages.getString( "VisualisationView.allowUpdatesIntoTheSameTab" ), SWT.CHECK ) { //$NON-NLS-1$
    @Override
    public int getStyle() {
      return IAction.AS_CHECK_BOX;
    }
    @Override
    public void run() {
      if ( VisualisationView.this.checkBtnAction.isEnabled() ) {
        VisualisationView.this.checkBtnAction.setEnabled( false );
      }
      else {
        VisualisationView.this.checkBtnAction.setEnabled( true );
      }
    }
  };
  this.checkBtnAction
  .setToolTipText( Messages.getString( "VisualisationView.allowUpdatesIntoTheSameTabTooltip" ) ); //$NON-NLS-1$

  IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
  mgr.add( this.checkBtnAction );
}

//  @SuppressWarnings("unused")
//  private void contributeToActionBars() {
//    IActionBars bars = getViewSite().getActionBars();
//    fillLocalToolBar( bars.getToolBarManager() );
//  }
//
//  @SuppressWarnings("unused")
//  private void fillLocalToolBar( final IToolBarManager manager ) {
//    if( this.fileDropDownAction != null ) {
//      manager.add( this.fileDropDownAction );
//    }
//  }
//
//  @SuppressWarnings("unused")
//  private void hookContextMenu() {
//    MenuManager menuMgr = new MenuManager( "#PopupMenu" ); //$NON-NLS-1$
//    menuMgr.setRemoveAllWhenShown( true );
//    menuMgr.addMenuListener( new IMenuListener() {
//
//      public void menuAboutToShow( final IMenuManager manager ) {
//        VisualisationView.this.fillContextMenu( manager );
//      }
//    } );
//  }

//  void fillContextMenu( final IMenuManager manager ) {
//    if( this.fileDropDownAction != null ) {
//      manager.add( this.fileDropDownAction );
//    }
//    manager.add( new Separator( IWorkbenchActionConstants.MB_ADDITIONS ) );
//  }


  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    if( this.cTabFolder != null ) {
      this.cTabFolder.setFocus();
    }
  }

  /**
   * @return tab item
   */
  public CTabItem getCTabItem() {
    CTabItem cTabItem = this.cTabFolder
    != null ? getTab() : null;
    return cTabItem;
  }

  private CTabItem getTab() {
    CTabItem tabItem = null;
    if ( this.checkBtnAction.isEnabled() ) {
      if ( this.cTabFolder.getItemCount() < this.allowedNumOfTabs ) {
        tabItem = new CTabItem( this.cTabFolder, SWT.CLOSE );
        this.cTabFolder.setSelection( tabItem );
      }
      else {
//        TODO - "tell to user to close some tabs"
      }
    }
    else {
      tabItem = this.cTabFolder.getSelection();
    }
    return tabItem;
  }

  /**
   * @return folder of tabs
   */
  public CTabFolder getCTabFolder() {
    return this.cTabFolder;
  }

  /**
   * @param vtkPipeline
   */
  public void setPipeline( final IGridVisualisation vtkPipeline ) {
      this.vtkPipeline = vtkPipeline;
  }

  /**
   * Invokes the rendering process as described by the preset vtkPipeline.
   */
  public void render() {

    if ( this.vtkPipeline == null ) {
      return;
    }
    this.vtkPipeline.render();
  }
}
