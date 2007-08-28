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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.IApplicationDeployment;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.DeploymentChooserTreeContentProvider;
import eu.geclipse.ui.providers.DeploymentChooserTreeLabelProvider;

/**
 * @author Yifan Zhou
 */
public class DeploymentChooser extends WizardPage {

  /**
   * Create a tree for listing configuration elements of all extensions 
   * which implement the application deployment extension point.
   */
  private TreeViewer treeViewer;

  /**
   * The flag by which the "next" button could be enabled.  
   */
  private boolean flipToNextPageEnabled = false;

  /**
   * The execute extension. 
   */
  private IApplicationDeployment executeExt;

  public DeploymentChooser( final String pageName ) {
    super( pageName );
    this.setTitle( Messages.getString( "Deployment.deployment_chooser_title" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString( "Deployment.deployment_chooser_description" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Composite composite = new Composite( parent, SWT.NONE );
    composite.setLayout( new FillLayout( SWT.VERTICAL ) );
    this.treeViewer = new TreeViewer( composite, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL );
    this.setControl( composite );
  }

  @Override
  public void setVisible( final boolean visible ) {
    if ( visible ) {
      this.initContents();
      if ( this.treeViewer.getTree().getItemCount() == 0 ) {
        this.setErrorMessage( Messages.getString( "Deployment.deployment_chooser_no_extension" ) ); //$NON-NLS-1$
      }
    }
    super.setVisible( visible );
  }

  private void initContents() {
    this.treeViewer.setContentProvider( new DeploymentChooserTreeContentProvider() );
    this.treeViewer.setLabelProvider( new DeploymentChooserTreeLabelProvider() );
    ExtensionManager extManager = new ExtensionManager();
    IExtensionPoint extPoint = extManager.getExtensionPoint( Extensions.APPLICATION_DEPLOYMENT_POINT );
    this.treeViewer.setInput( extPoint );
    this.treeViewer.expandAll();
    this.treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        IStructuredSelection selection = ( IStructuredSelection ) event.getSelection();
        if ( selection != null && !selection.isEmpty() ) {
          IConfigurationElement element = ( IConfigurationElement ) selection.getFirstElement();
          String attribute 
            = element.getAttribute( DeploymentWizard.EXT_CATEGORY );
          if ( attribute != null && !attribute.equals( "" ) ) { //$NON-NLS-1$
            try {
              Object object 
                = element.createExecutableExtension( DeploymentWizard.EXT_CLASS );
              if ( object instanceof IApplicationDeployment ) {
                setExecuteExt( ( IApplicationDeployment ) object );
              }
            } catch ( CoreException e ) {
              Activator.logException( e );
            }
            setFlipToNextPageFlag( true );
          } else {
            setFlipToNextPageFlag( false );
          }
        } else {
          setFlipToNextPageFlag( false );
        }
        setPageComplete( canFlipToNextPage() );
      }
    });
  }

  @Override
  public boolean canFlipToNextPage() {
    return this.flipToNextPageEnabled;
  }

  @Override
  public boolean isPageComplete() {
    return false;
  }

  protected void setFlipToNextPageFlag( final boolean flag ) {
    this.flipToNextPageEnabled = flag;
  }

  protected void setExecuteExt( final IApplicationDeployment applicationDeployment ) {
    this.executeExt = applicationDeployment;
  }

  protected IApplicationDeployment getExecuteExt() {
    return this.executeExt;
  }

}