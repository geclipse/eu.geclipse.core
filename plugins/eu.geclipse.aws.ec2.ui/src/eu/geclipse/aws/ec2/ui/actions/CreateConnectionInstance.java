/**
* Copyright (c) 2008 g-Eclipse Consortium 
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
*    Harald Kornmayer - initial API and implementation
*****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2Instance;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.actions.MountAction;

/**
* This {@link IObjectActionDelegate} is used to start a {@link Job} which is
* rebooting an instance.
* 
* @author Harald Kornmayer
*/
public class CreateConnectionInstance extends AbstractAWSProjectAction {

 /** The list of AMIs which are selected. */
 private ArrayList<EC2Instance> instanceList;

 /**
  * Instantiate and initialize the reboot object.
  */
 public CreateConnectionInstance() {
   this.instanceList = new ArrayList<EC2Instance>();
 }

 @Override
 public void run( final IAction action ) {
   if( action.isEnabled() ) {

     Job job = new Job( "Mount a Grid connection to the instance" )  { //$NON-NLS-1$

       @Override
       protected IStatus run( final IProgressMonitor monitor ) {
         try {
           if( CreateConnectionInstance.this.instanceList.size() == 0 ) {
             return Status.CANCEL_STATUS;
           }

           for( EC2Instance instance : CreateConnectionInstance.this.instanceList ) {
             
             // extract the hostname from the instance
             
             String hostname = instance.getHostName() ; 
              
             // for the Connection in the Workspace a IPath is needed              
             // 
             IGridProject project = instance.getProject() ; 
             
             IPath path = instance.getProject().getPath().append( "Connections" ).append(hostname) ;
             
             try {
              URI uri = new URI("sftp", "root", hostname, 22, "/", null, null);
              
              MountAction.createLocalMount( uri, path, monitor );
              System.out.println("URI: " + uri) ; 
              
            } catch( URISyntaxException e ) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } catch( CoreException e ) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } 
             
           }
           return Status.OK_STATUS;

         } finally {
           monitor.done();
         }
       }
     };
     job.setPriority( Job.SHORT );
     job.setUser( true );
     job.schedule();
   }

 }

 public void selectionChanged( final IAction action, final ISelection selection )
 {
   boolean enable = false;
   this.instanceList.clear();

   if( selection instanceof IStructuredSelection ) {
     IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
     for( Object element : structuredSelection.toList() ) {
       if( element instanceof EC2Instance ) {
         EC2Instance ec2Instance = ( EC2Instance )element;
         this.instanceList.add( ec2Instance );
       }
     }
   }
   if( this.instanceList.size() > 0 ) {
     enable = true;
   }
   action.setEnabled( enable );

 }

}

