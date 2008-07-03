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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatusListener;
import eu.geclipse.ui.decorators.GridJobDecorator;
import eu.geclipse.ui.internal.actions.ActionGroupManager;
import eu.geclipse.ui.internal.actions.BuildActions;
import eu.geclipse.ui.internal.actions.DeployActions;
import eu.geclipse.ui.internal.actions.EditorActions;
import eu.geclipse.ui.internal.actions.MonitorActions;
import eu.geclipse.ui.internal.actions.MountActions;
import eu.geclipse.ui.internal.actions.NewWizardActions;
import eu.geclipse.ui.internal.actions.ProjectActions;
import eu.geclipse.ui.internal.actions.SimpleTestActions;
import eu.geclipse.ui.internal.actions.SubmitJobActions;
import eu.geclipse.ui.internal.actions.TransformActions;
import eu.geclipse.ui.internal.actions.UninstallActions;
import eu.geclipse.ui.internal.actions.ValidateActions;
import eu.geclipse.ui.internal.actions.VisualisationActions;
import eu.geclipse.ui.providers.GridModelContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

/**
 * The grid project view is the central view of the g-Eclipse
 * framework. With its help the user may manage his projects,
 * especially his grid projects that are contained in the grid model.
 * Therefore the root element of the grid project view is the
 * {@link eu.geclipse.core.model.IGridRoot} itself.
 */
public class GridProjectView
    extends TreeControlViewPart
    implements IGridJobStatusListener {
  
  private EditorActions editorActions;
  
  private IPartListener2 partListener;
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {
    
    super.createPartControl( parent );
    
    this.partListener = new GridProjectPartListener( this.editorActions );
    getSite().getPage().addPartListener( this.partListener );
    
    GridModel.getJobManager().addJobStatusListener( this );
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#dispose()
   */
  @Override
  public void dispose() {
    getSite().getPage().removePartListener( this.partListener );
    GridModel.getJobManager().removeJobStatusListener( this );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#isDragSource(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean isDragSource( final IGridElement element ) {
    return super.isDragSource( element );
  }
  
  public void statusChanged( final IGridJob job ) {
    GridJobDecorator decorator = GridJobDecorator.getDecorator();
    if ( decorator != null ) {
      decorator.refresh( job );
    }
  }
  
  public void statusUpdated( final IGridJob job ) {
    // react only if status changed. See statusChanged()    
  }

  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#contributeAdditionalActions(eu.geclipse.ui.internal.actions.ActionGroupManager)
   */
  @Override
  protected void contributeAdditionalActions( final ActionGroupManager groups ) {
    
    super.contributeAdditionalActions( groups );
    
    IWorkbenchPartSite site = getSite();
    IWorkbenchWindow window = site.getWorkbenchWindow();
    
    NewWizardActions newWizardActions = new NewWizardActions( window );
    groups.addGroup( newWizardActions );
    
    SubmitJobActions submitJobActions = new SubmitJobActions( site );
    groups.addGroup( submitJobActions );
    
    TransformActions transformActions = new TransformActions( site );
    groups.addGroup( transformActions );
    
    MountActions mountActions = new MountActions( site );
    groups.addGroup( mountActions );
    
    //DeployActions deployActions = new DeployActions( site );
    //groups.addGroup(  deployActions );
    
    //UninstallActions uninstallActions = new UninstallActions (site);
    //groups.addGroup( uninstallActions );
    
    //ValidateActions validateActions = new ValidateActions(site);
    //groups.addGroup( validateActions );
    
    ProjectActions projectActions = new ProjectActions( site );
    groups.addGroup( projectActions );
    
    BuildActions  buildActions = new BuildActions( site );
    groups.addGroup( buildActions );
    
    MonitorActions monitorActions = new MonitorActions ( site );
    groups.addGroup ( monitorActions);
    
    VisualisationActions visualizationActions = new VisualisationActions ( site );
    groups.addGroup ( visualizationActions );

    SimpleTestActions simpleTestActions = new SimpleTestActions( site );
    groups.addGroup( simpleTestActions );

    this.editorActions = new EditorActions( this );
    groups.addGroup( this.editorActions );
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createContentProvider()
   */
  @Override
  protected IContentProvider createContentProvider() {
    IContentProvider contentProvider = new GridModelContentProvider();
    return contentProvider;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    ILabelProvider provider = new GridModelLabelProvider();
    ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
    DecoratingLabelProvider result = new DecoratingLabelProvider( provider, decorator );
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createViewer(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected StructuredViewer createViewer( final Composite parent ) {
    StructuredViewer sViewer = new TreeViewer( parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );
    return sViewer;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#getRootElement()
   */
  @Override
  protected IGridElement getRootElement() {
    IGridElement rootElement = GridModel.getRoot();
    return rootElement;
  }

}
