/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.actions;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.sla.Activator;
import eu.geclipse.core.sla.Extensions;
import eu.geclipse.core.sla.ISLAProblems;
import eu.geclipse.core.sla.ISLAService;
import eu.geclipse.core.sla.preferences.PreferenceConstants;
import eu.geclipse.core.sla.ui.Messages;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * @author korn
 */
public class PublishServiceLevelTemplate
  implements IWorkbenchWindowActionDelegate
{

  public void dispose() {
    // TODO Auto-generated method stub
  }

  public void init( final IWorkbenchWindow window ) {
    //
  }

  public void run( final IAction action ) {
    
    // get the pRegistryURL
    Preferences prefs = eu.geclipse.core.sla.Activator.getDefault()
    .getPluginPreferences();
    String registryURI = prefs.getString( PreferenceConstants.pRegistryURI );
   
    if (registryURI.equals( "" )) { //$NON-NLS-1$
      ProblemDialog.openProblem( null,
                                 Messages.getString( "PublishServiceLevelTemplate_DialogTitle" ), //$NON-NLS-1$
                                 Messages.getString( "PublishServiceLevelTemplate_DialogMessage" ), //$NON-NLS-1$
                                 new ProblemException( ISLAProblems.MISSING_PROVIDER_URI,
                                                       Activator.PLUGIN_ID ));
      return ; 
    }
      
    
    try {
      // get the service implementation
      ISLAService service = Extensions.getSlaServiceImpl();
      // use preferences to find the registry endpoint
     // get the selected file name
      ISelection selection = PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow()
        .getSelectionService()
        .getSelection();
      IStructuredSelection structured = ( IStructuredSelection )selection;
      // if the selection is a slt file, get its
      Object object = structured.getFirstElement();
      IFile file = null ; 
      if( object instanceof IGridElement ) {
        IGridElement localfile = ( IGridElement )object;
        IResource res = localfile.getResource();
        file = ( IFile )res;
        
       } else if( object instanceof IFile ) {
        file = ( IFile )object;
       }
      // All data are available now --> publish the SLA by using the
      // right service
      this.publishSLAtoRegistry( file, registryURI, service );
    
    } catch( ProblemException e ) {
      ProblemDialog.openProblem( null,
                                 Messages.getString( "PublishServiceLevelTemplate_DialogTitle" ), //$NON-NLS-1$
                                 Messages.getString( "PublishServiceLevelTemplate_DialogMessage" ), //$NON-NLS-1$
                                 e );
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    // TODO Auto-generated method stub
  }

  private void publishSLAtoRegistry( final IFile file,
                                     final String registryURI,
                                     final ISLAService service )
  {
    // start the wizard or dialog
    // TODO: we have to design the implementations in a way, that the core
    // part is independent from the UI part
    // set the right endpoint
    try {
      service.setRegistryURI( registryURI );
      // publish the document
      service.publishSLT( file );
    } catch( URISyntaxException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private ISLAService getSLAserviceFromString( final String result ) {
    ISLAService slaService = null;
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IConfigurationElement[] extensions = reg.getConfigurationElementsFor( Extensions.SLA_SERVICE_POINT );
    for( int i = 0; i < extensions.length; i++ ) {
      IConfigurationElement element = extensions[ i ];
      String name = element.getAttribute( Extensions.SLA_SERVICE_NAME_ATTRIBUTE );
      if( result.equals( name ) ) {
        try {
          slaService = ( ISLAService )element.createExecutableExtension( Extensions.SLA_SERVICE_PROVIDER_EXECUTABLE );
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return slaService;
  }
}
