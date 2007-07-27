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
 *    Martin Polak GUP, JKU - initial implementation
 *****************************************************************************/
package eu.geclipse.ui.views;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.monitoring.GridProcessMonitor;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.Messages;
import eu.geclipse.ui.providers.ProcessViewContentprovider;
import eu.geclipse.ui.providers.ProcessViewLabelProvider;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class ProcessStatView extends ViewPart implements IContentChangeListener {
 
  TreeViewer viewer;
  
  Action updateaction;
  Action doubleClickAction;
  Action dropJobAction;

  HashSet<GridProcessMonitor> remoteMonitors; 
  
  private DrillDownAdapter drillDownAdapter;
 
  /**
   * The constructor.
   */
  public ProcessStatView() {
    this.remoteMonitors = new HashSet<GridProcessMonitor>();
    
    
  }

  /**
   * This is a callback that will allow us
   * to create the viewer and initialize it.
   */
  @Override
  public void createPartControl(final Composite parent) {
    this.viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    this.drillDownAdapter = new DrillDownAdapter(this.viewer);
    this.viewer.setContentProvider(new ProcessViewContentprovider());
    this.viewer.setLabelProvider( new ProcessViewLabelProvider() );
    
    this.viewer.setSorter(new ViewerSorter());  //TODO
    
    makeActions();
    hookContextMenu();
    hookDoubleClickAction();
    contributeToActionBars();
  }

  private void hookContextMenu() {
    MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
    menuMgr.setRemoveAllWhenShown(true);
    menuMgr.addMenuListener(new IMenuListener() {
      public void menuAboutToShow(final IMenuManager manager) {
        ProcessStatView.this.fillContextMenu(manager);
      }
    });
    Menu menu = menuMgr.createContextMenu(this.viewer.getControl());
    this.viewer.getControl().setMenu(menu);
    getSite().registerContextMenu(menuMgr, this.viewer);
  }

  private void contributeToActionBars() {
    IActionBars bars = getViewSite().getActionBars();
    fillLocalPullDown(bars.getMenuManager());
    fillLocalToolBar(bars.getToolBarManager());
  }

  private void fillLocalPullDown(final IMenuManager manager) {
    manager.add(this.updateaction);
    manager.add(new Separator());
    
  }

  void fillContextMenu(final IMenuManager manager) {
    manager.add(this.updateaction);
    manager.add(new Separator());
    this.drillDownAdapter.addNavigationActions(manager);
    // Other plug-ins can contribute there actions here
    manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
  }

 
  
  private void fillLocalToolBar(final IToolBarManager manager) {
    manager.add(this.updateaction);
    manager.add(new Separator());
    this.drillDownAdapter.addNavigationActions(manager);
  }

  private void makeActions() {
    this.updateaction = new Action() {
      @Override
      public void run() {
        //showMessage("Fetching Processlist"); //$NON-NLS-1$
  
        ProcessStatView.this.viewer.refresh();

      }
    };
    this.updateaction.setText(Messages.getString("ProcessStatView.update"));  //$NON-NLS-1$
    this.updateaction.setToolTipText(Messages.getString("ProcessStatView.updatestat"));  //$NON-NLS-1$
    this.updateaction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
                                         getImageDescriptor(ISharedImages.IMG_TOOL_REDO));

       
    
    this.doubleClickAction = new Action() {
      @Override
      public void run() {
        ISelection selection = ProcessStatView.this.viewer.getSelection();
        Object obj = ((IStructuredSelection)selection).getFirstElement();
        int newpid;
        try {
          newpid = Integer.parseInt( obj.toString() );
        } catch( NumberFormatException e ) {
          newpid = -1;
        }
        
        ProcessStatView.this.viewer.refresh();
      }
    };
  }
  
public void addSite(URI newsite) {
  if (newsite != null){
    this.remoteMonitors.add( new GridProcessMonitor(newsite));
    this.viewer.setInput(this.remoteMonitors.toArray());
  }
  
}

public void removeSite (URI selected){
  if (selected != null){
    this.remoteMonitors.remove( new GridProcessMonitor(selected) );
  }
}

protected void initializeBrowser() {
    
  this.viewer.setInput( null );
}
  

  private void hookDoubleClickAction() {
    this.viewer.addDoubleClickListener(new IDoubleClickListener() {
      public void doubleClick(final DoubleClickEvent event) {
        ProcessStatView.this.doubleClickAction.run();
      }
    });
  }
 
  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    this.viewer.getControl().setFocus();
  }

  public void contentChanged( final IContentChangeNotifier source ) {
    this.viewer.refresh();
  }
}