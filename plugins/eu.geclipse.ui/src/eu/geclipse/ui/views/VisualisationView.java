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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * @author sgirtel
 *
 */
public class VisualisationView extends ViewPart {

  protected boolean newTabOn = false;
  IAction checkBtnAction = null;
  private CTabFolder cTabFolder;
  private IGridVisualisation vtkPipeline = null;
  private int allowedNumOfTabs = 5;
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
//    hookContextMenu();
//    contributeToActionBars();
    createActions();
    createToolBar();
  }

  private void createActions() {

    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    Image image = imgReg.get( "toggleTabBtn" ); //$NON-NLS-1$
    ImageDescriptor toggleTabBtnImage = ImageDescriptor.createFromImage( image );

    this.checkBtnAction = 
        new org.eclipse.jface.action.Action( 
                   Messages.getString( "VisualisationView.switchToUpdatesIntoTheSameTab" ), //$NON-NLS-1$
                   IAction.AS_CHECK_BOX ) {
    
        @Override
        public boolean isChecked() {
          return VisualisationView.this.newTabOn;
        }
        
        @Override
        public void run() {
          if ( !VisualisationView.this.checkBtnAction.isChecked() ) {
            VisualisationView.this.newTabOn = true;
            VisualisationView.this.checkBtnAction
            .setToolTipText( Messages.getString( "VisualisationView.switchToNewTabWhenUpdating" ) ); //$NON-NLS-1$
            VisualisationView.this.checkBtnAction.setChecked( false );
          }
          else {
            VisualisationView.this.newTabOn = false;
            VisualisationView.this.checkBtnAction
            .setToolTipText( Messages.getString( "VisualisationView.switchToUpdatesIntoTheSameTab" ) ); //$NON-NLS-1$
            VisualisationView.this.checkBtnAction.setChecked( true );
          }
        }
      };
    this.checkBtnAction.setToolTipText( Messages.getString("VisualisationView.switchToUpdatesIntoTheSameTab") ); //$NON-NLS-1$
    this.checkBtnAction.setImageDescriptor( toggleTabBtnImage );

  }
  
  private void createToolBar() {
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
    if ( !this.checkBtnAction.isChecked() ) {
      if ( this.cTabFolder.getItemCount() < this.allowedNumOfTabs ) {
        tabItem = new CTabItem( this.cTabFolder, SWT.CLOSE );
        this.cTabFolder.setSelection( tabItem );
      }
      else {
        ProblemException pe = 
          new ProblemException( "eu.geclipse.ui.reachedMaximumAllowedOpenedTabsProblem",  //$NON-NLS-1$
                                Activator.PLUGIN_ID );
        ProblemDialog.openProblem( null, 
                                   Messages.getString( "VisualisationView.dialogTitle" ),  //$NON-NLS-1$
                                   Messages.getString( "VisualisationView.maxNumOfTabsOpenedErrorMessage" ), //$NON-NLS-1$
                                   pe );
      }
    }
    else {
      tabItem = this.cTabFolder.getSelection() != null 
      ? this.cTabFolder.getSelection() : new CTabItem( this.cTabFolder, SWT.CLOSE );
      this.cTabFolder.setSelection( tabItem );
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
