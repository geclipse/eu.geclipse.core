/*****************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec
 *          - Mateusz Pabis
 *           
 *****************************************************************************/
package eu.geclipse.ui.views.gexplorer;

import java.util.ArrayList;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.core.connection.ConnectionManager;
import eu.geclipse.ui.internal.connection.FileSystemsProvider;
import eu.geclipse.ui.wizards.connection.ConnectionWizard;

/**
 * Class to provide Eclipse's view for gExplorer. This class intergrates
 * gExplorer's functionality with Eclipse's UI.
 * 
 * @author katis
 */
public class GExplorerView extends ViewPart implements IContentChangeListener {

  protected StructuredViewer viewer;

  /**
   * Method to access this view's viewer
   * 
   * @return structured viewer of this view
   */
  public StructuredViewer getViewer() {
    return this.viewer;
  }

  /**
   * Method to set this view's viewer
   * 
   * @param viewer structured viewer to set
   */
  public void setViewer( final StructuredViewer viewer ) {
    this.viewer = viewer;
  }

  @Override
  public void createPartControl( final Composite parent )
  {
    ConnectionManager.getManager().addContentChangeListener( this );
    addActionToMenu();
    this.viewer = new TreeViewer( parent, SWT.VIRTUAL
                                          | SWT.MULTI
                                          | SWT.V_SCROLL
                                          | SWT.H_SCROLL );
    this.viewer.setContentProvider( new TreeNodeContentProvider() );
    this.viewer.setLabelProvider( new GExplorerLabelProvider() );
    this.viewer.setComparator( new GExplorerComparator() );
    this.refresh();
  }

  @Override
  public void setFocus()
  {
    // nothing to do here
  }

  private void addActionToMenu() {
    IActionBars bars = getViewSite().getActionBars();
    bars.getToolBarManager().add( new NewConnectionWizardAction() );
  }

  /**
   * Method to refresh this view
   */
  public void refresh() {
    ArrayList<ResourceNode> nodes = new ArrayList<ResourceNode>();
    nodes = FileSystemsProvider.getFileSystems();
    if( this.viewer.getInput() == null ) {
      ResourceNode[] nodesArray = new ResourceNode[ nodes.size() ];
      nodesArray = nodes.toArray( nodesArray );
      this.viewer.setInput( nodesArray );
    } else {
      ResourceNode[] nodesArray = new ResourceNode[ nodes.size() ];
      nodesArray = nodes.toArray( nodesArray );
      this.viewer.setInput( nodesArray );
    }
    this.viewer.refresh();
  }
  class NewConnectionWizardAction extends Action {

    /**
     * Action to fire when button in the gExploer is pressed
     */
    public NewConnectionWizardAction() {
      setImageDescriptor( PlatformUI.getWorkbench()
                          .getSharedImages()
                          .getImageDescriptor( ISharedImages.IMG_TOOL_NEW_WIZARD ) );
      this.setText( Messages.getString( "GExplorerView.new_wizard_action_text" ) ); //$NON-NLS-1$
    }

    @Override
    public void run()
    {
      // IPath projectPath = new Path( "Filesystems/.filesystems" );
      // IProject project = null;
      // try {
      // project = ( IProject )ResourcesPlugin.getWorkspace()
      // .getRoot()
      // .members()[ 0 ];
      // } catch( CoreException coreEx ) {
      // eu.geclipse.ui.internal.Activator.logException( coreEx );
      // }
      // .getProject( "pierwssy" );
      // IFile file = project.getFile( projectPath );
      WizardDialog wizardDialog = new WizardDialog( Display.getCurrent()
        .getActiveShell(), new ConnectionWizard() );
      wizardDialog.open();
      GExplorerView.this.refresh();
    }

    @Override
    public String getId()
    {
      return "new action"; //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled()
    {
      return true;
    }
  }

  public void contentChanged( final IContentChangeNotifier source ) {
    this.refresh();
  }
}
