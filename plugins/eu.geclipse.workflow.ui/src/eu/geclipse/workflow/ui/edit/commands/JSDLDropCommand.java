/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - David Johnson - initial API and implementation
 ******************************************************************************/

package eu.geclipse.workflow.ui.edit.commands;

import java.net.URI;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * @author david
 */
public class JSDLDropCommand extends AbstractTransactionalCommand {

  private EObject domainElement;
  private JSDLJobDescription jsdl;
  private String[] dirs;
  private IFileStore wfRootFileStore;

  public JSDLDropCommand( EObject domainElt, JSDLJobDescription jsdl ) {
    super( TransactionUtil.getEditingDomain( domainElt ),
           Messages.getString("JSDLDropCommand_constructorMessage"), //$NON-NLS-1$
           null );
    this.domainElement = domainElt;
    this.jsdl = jsdl;
  }

  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor arg0,
                                               IAdaptable arg1 )
    throws ExecutionException
  {
    if ( this.domainElement instanceof IWorkflowJob ) {
      // must make sure it's in .workflow folder, and/or copy if necessary.
      IWorkflowJob wfJob = ( IWorkflowJob )this.domainElement;
      URI jsdlPathUri = null;
      jsdlPathUri = URIUtil.toURI( this.jsdl.getResource().getLocation().toString());
      IFile jsdlSource = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( jsdlPathUri )[ 0 ];
      String jobDescriptionInJSDL = jsdlSource.getLocation().toString();
      ResourceSet resourceSet = TransactionUtil.getEditingDomain( this.domainElement )
        .getResourceSet();
      Resource res = resourceSet.getResources().get( 0 );
      org.eclipse.emf.common.util.URI wfRootUri = res.getURI();
      String wfRootPath = wfRootUri.path();
      this.dirs = wfRootPath.split( "/" ); //$NON-NLS-1$
      String projectName = this.dirs[ 2 ];
      this.wfRootFileStore = GridModel.getRoot().getFileStore().getChild( projectName );
      int numDirs = this.dirs.length;
      java.net.URI targetUri = null;
      
      IFileStore fs = this.wfRootFileStore;
      for ( int i = 3; i < numDirs - 1; i++ ) {
        String dir = this.dirs[ i ];
        dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
        fs = fs.getChild( dir );
      }
      fs = fs.getChild( jsdlSource.getName() );
      targetUri =  fs.toURI();
      IFile jsdlTarget = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( targetUri )[ 0 ];
      // now check if it exists in workflow directory, and copy it from
      // source if not
      try {
        // no need to copy if the file selected is in .workflow folder
        // anyway
        if ( ! jsdlTarget.equals( jsdlSource ) ) {
          // deal with existing files somehow? change target to append [n]
          // to filename
          int numTarget = findJsdlTarget( jsdlTarget );
          if ( numTarget > 0 ) {
            String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
            String newTargetName = targetNamePart
                                   + numTarget
                                   + ".jsdl"; //$NON-NLS-1$
         // make new jsdlTarget
            numDirs = this.dirs.length;
            targetUri = null;
            
            fs = this.wfRootFileStore;
            for ( int i = 3; i < numDirs - 1; i++ ) {
              String dir = this.dirs[ i ];
              dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
              fs = fs.getChild( dir );
            }
            fs = fs.getChild( newTargetName );
            targetUri =  fs.toURI();
            jsdlTarget = ResourcesPlugin.getWorkspace()
              .getRoot()
              .findFilesForLocationURI( targetUri )[ 0 ];
          }
          jsdlSource.copy( jsdlTarget.getFullPath(), true, null );
          jobDescriptionInJSDL = jsdlTarget.getLocation().toString();
        }
      } catch ( CoreException ex ) {
        // ignore for now. naughty!
      }
      wfJob.setJobDescription( jobDescriptionInJSDL );
      wfJob.setName( jsdlTarget.getName().substring( 0, jsdlTarget.getName().indexOf( "." + jsdlTarget.getFileExtension() ) ) ); //$NON-NLS-1$
    }
    return CommandResult.newOKCommandResult();
  }

  private int findJsdlTarget( IFile jsdlTarget ) {
    // search through the .workflow directory for the target name and target[n]
    // names to work out how many exist
    int numTarget = 0;
    while ( jsdlTarget.exists() ) {
      String targetNamePart = jsdlTarget.getName().split( "\\." )[ 0 ]; //$NON-NLS-1$
      String newTargetName = ""; //$NON-NLS-1$
      if ( targetNamePart.endsWith( numTarget + "" ) ) { //$NON-NLS-1$
        targetNamePart = targetNamePart.substring( 0,
                           ( targetNamePart.length() - (
                             + numTarget + "" ).length() ) ); //$NON-NLS-1$
      }
      newTargetName = targetNamePart + ++numTarget + ".jsdl"; //$NON-NLS-1$
      // make new jsdlTarget
      int numDirs = this.dirs.length;
      java.net.URI targetUri = null;
      
      IFileStore fs = this.wfRootFileStore;
      for ( int i = 3; i < numDirs - 1; i++ ) {
        String dir = this.dirs[ i ];
        dir = dir.replace( "%20", " " ); //$NON-NLS-1$ //$NON-NLS-2$
        fs = fs.getChild( dir );
      }
      fs = fs.getChild( newTargetName );
      targetUri =  fs.toURI();
      jsdlTarget = ResourcesPlugin.getWorkspace()
        .getRoot()
        .findFilesForLocationURI( targetUri )[ 0 ];
    }
    return numTarget;
  }
}