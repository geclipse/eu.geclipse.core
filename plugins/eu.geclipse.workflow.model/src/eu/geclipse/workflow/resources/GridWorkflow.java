/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *     FZK (http://www.fzk.de)
 *     - Mathias Stumpert - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

import eu.geclipse.core.model.IGridWorkflow;
import eu.geclipse.core.model.IGridWorkflowJob;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.workflow.IWorkflowJob;
import eu.geclipse.workflow.IWorkflowNode;
import eu.geclipse.workflow.impl.WorkflowImpl;
import eu.geclipse.workflow.internal.Activator;

/**
 * Grid element for workflow description
 */
public class GridWorkflow
    extends ResourceGridContainer
    implements IGridWorkflow {  

  private WorkflowImpl rootImpl;
  private List<IGridWorkflowJob> jobs;

  protected GridWorkflow( final IResource resource ) {
    super( resource );
  }

  public String getDescription() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getExecutable() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<String> getExecutableArguments() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdErrorFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdErrorUri() throws ProblemException {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdInputFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdInputUri() throws ProblemException {
    // TODO Auto-generated method stub
    return null;
  }

  public String getStdOutputFileName() {
    // TODO Auto-generated method stub
    return null;
  }

  public URI getStdOutputUri() throws ProblemException {
    // TODO Auto-generated method stub
    return null;
  }
  
  public List<IGridWorkflowJob> getChildrenJobs() {
    if( this.jobs == null ) {
      WorkflowImpl root = getRoot();
      
      if( root != null ) {
        this.jobs = new ArrayList<IGridWorkflowJob>();
        
        for( IWorkflowNode node : root.getNodes() ) {
          if( node instanceof IWorkflowJob ) {
            this.jobs.add( new GridWorkflowJob( ( IWorkflowJob )node ) );        
          }        
        }
      }
    }
    
    return this.jobs;
  }
  
  private WorkflowImpl getRoot() {
    if( this.rootImpl == null ) {
      loadModel( (IFile)getResource() );
    }
    
    return this.rootImpl;
  }
 
  private void loadModel( final IFile file ) {
    String filePath = file.getFullPath().toString();
    org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createPlatformResourceURI( filePath, false );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resource = resourceSet.createResource( uri );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_ENCODING, "UTF8" ); //$NON-NLS-1$
    try {
      resource.load( options );
      this.rootImpl = (WorkflowImpl)resource.getContents().get( 0 );
    } catch( IOException ioEx ) {
      Activator.logException( ioEx );
    }
  }
}