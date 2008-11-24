/******************************************************************************
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.visualisation.AbstractGridVisualisationResource;
import eu.geclipse.ui.visualisation.AbstractVisualisationAction;


/**
 * @author sgirtel
 *
 */
public class VisualisationActions extends ActionGroup {

  /**
   * The workbench this action group belongs to.
   */
  private final IWorkbenchPartSite site;

  //have a list of visualisationActions and init them through extensions
  private final ArrayList<AbstractVisualisationAction> actions
  = new ArrayList<AbstractVisualisationAction>();

  /**
   * @param site
   */
  public VisualisationActions(final IWorkbenchPartSite site){
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    String fileExtension = ""; //$NON-NLS-1$

    //find classes implementing the visualisation action extension point
      CoreException exception = null;
      AbstractVisualisationAction actionImpl = null;
      String text = null;
      String tooltip = null;
      try {
        IExtensionPoint p = Platform.getExtensionRegistry()
          .getExtensionPoint( AbstractVisualisationAction.ACTION_EXTENSION_POINT );
        IExtension[] extensions = p.getExtensions();
        for( IExtension extension : extensions ) {
          IConfigurationElement[] elements = extension.getConfigurationElements();
          for( IConfigurationElement element : elements ) {
            if( AbstractVisualisationAction.EXT_ACTION_ELEMENT.equals( element.getName() )
//                && fileExtension.contains( element.getAttribute( AbstractVisualisationAction.EXT_ACTION_FILE_EXTENSION ) )
                ) {
              text = element.getAttribute( AbstractVisualisationAction.EXT_ACTION_TEXT );
              tooltip = element.getAttribute( AbstractVisualisationAction.EXT_ACTION_TOOLTIP );
              fileExtension = element.getAttribute( AbstractVisualisationAction.EXT_ACTION_FILE_EXTENSION );
              actionImpl =
              (AbstractVisualisationAction) element
              .createExecutableExtension( AbstractVisualisationAction.EXT_ACTION_CLASS );
              actionImpl.init( text, tooltip, fileExtension, site );
              selectionProvider.addSelectionChangedListener( actionImpl );
              this.actions.add( actionImpl );
            }
          }
        }
      } catch( CoreException coreException ) {
        exception = coreException;
        // Activator.logException( coreException );
      }
      if ( exception != null) {
        ProblemDialog.openProblem( Display.getCurrent().getActiveShell(),
                                   Messages.getString( "VisualisationActions.actionExtensionErrorTitle" ), //$NON-NLS-1$
                                   Messages.formatMessage( "VisualisationActions.cantInstanciateAction", fileExtension ), //$NON-NLS-1$
                                   exception );
      }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose()
  {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    for( AbstractVisualisationAction action : this.actions ) {
      selectionProvider.removeSelectionChangedListener( action );
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr )
  {
    super.fillContextMenu( mgr );
    for( AbstractVisualisationAction action : this.actions ) {
      if ( action.isEnabled() ) {
        try {
          Class<AbstractGridVisualisationResource> cl
          = ( Class<AbstractGridVisualisationResource> )
          (( IStructuredSelection )getContext().getSelection()).getFirstElement()
          .getClass();
          Method met = cl.getMethod( "getResourceFileExtension" ); //$NON-NLS-1$
          String resourceFileExt = ( String )met.invoke( null, null );
          if ( resourceFileExt.compareTo( action.getFileExt() ) == 0 ) {
            mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, action );
          }
        } catch( SecurityException e ) {
          Activator.logException( e );
        } catch( NoSuchMethodException e ) {
          Activator.logException( e );
        } catch( IllegalArgumentException e ) {
          Activator.logException( e );
        } catch( IllegalAccessException e ) {
          Activator.logException( e );
        } catch( InvocationTargetException e ) {
          Activator.logException( e );
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
   */
  @Override
  public void updateActionBars() {
    super.updateActionBars();

    IStructuredSelection selection = null;

    if( getContext() != null
        && getContext().getSelection() instanceof IStructuredSelection ) {
      selection = (IStructuredSelection)getContext().getSelection();
    }
    for( AbstractVisualisationAction action : this.actions ) {
      action.selectionChanged( selection );
    }
  }
}
