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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.jsdl.parametric.eclipse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.parametric.IGeneratedJsdl;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricJsdlException;

/**
 * Handler, which stores generated JSDLs in the workspace. This class is placed
 * in special package eu.geclipse.jsdl.parametric.eclipse, because it uses
 * eclipse and g-eclipse libraries (e.g. ProblemException).<br>
 * Please notice that package eu.geclipse.jsdl.parametric doesn't contain
 * dependencies to eclipse in order to be used as standalone library!
 */
public class ParametricJsdlSaver implements IParametricJsdlHandler {

  private JSDLJobDescription parametricJsdl;
  private IFolder targetFolder;
  private List<JSDLJobDescription> generatedJsdlList;
  private SubMonitor monitor;

  /**
   * @param parametricJsdl
   * @param targetFolder
   * @param subMonitor 
   */
  public ParametricJsdlSaver( final JSDLJobDescription parametricJsdl, final IFolder targetFolder, final SubMonitor subMonitor ) {
    super();
    this.parametricJsdl = parametricJsdl;
    this.targetFolder = targetFolder;
    this.monitor = subMonitor;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IParametricJsdlHandler#newJsdlGenerated(org.w3c.dom.Document, java.util.List)
   */
  public void newJsdlGenerated( final IGeneratedJsdl generatedJsdl ) throws ParametricJsdlException
  {
    try {
      saveJsdl( generatedJsdl );
    } catch( ProblemException exception ) {
      throw new ParametricJsdlException( Messages.ParametricJsdlSaver_errUnableToSaveGeneratedJsdl, exception );
    }
    this.monitor.worked( 1 );
  }
  
  private void saveJsdl( final IGeneratedJsdl generatedJsdl ) throws ProblemException
  {
    try {   
      
      IFile file = this.targetFolder.getFile( getFileName( generatedJsdl ) );
      DOMSource source = new DOMSource( generatedJsdl.getDocument() );      

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform( source, new StreamResult( outputStream ) );      
      outputStream.close();

      file.create( new ByteArrayInputStream( outputStream.toByteArray() ),
                   IResource.REPLACE,
                   null );
      
      IGridElement gridElement = GridModel.getRoot().findElement( file );
      
      Assert.isNotNull( gridElement );
      
      if ( gridElement != null ) {      
        this.generatedJsdlList.add( (JSDLJobDescription)gridElement );
      }
    } catch ( TransformerException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.saveGeneratedJsdlFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }
    catch ( FileNotFoundException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.saveGeneratedJsdlFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    } catch ( IOException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.saveGeneratedJsdlFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    } catch ( CoreException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.saveGeneratedJsdlFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }
  }

  private String getFileName( final IGeneratedJsdl generatedJsdl ) {
    return String.format( "%s%s.jsdl", new Path( this.parametricJsdl.getName() ).lastSegment(), generatedJsdl.getIterationName() );     //$NON-NLS-1$
  }

  public void generationFinished() throws ParametricJsdlException {
    this.monitor.done();
  }

  public void generationStarted( final int generatedJsdl, final List<String> paramNames ) throws ParametricJsdlException {
    try {
      this.monitor.subTask( Messages.ParametricJsdlSaver_taskGeneratingJsdl );
      this.generatedJsdlList = new ArrayList<JSDLJobDescription>();
      this.monitor.setWorkRemaining( generatedJsdl );
      deleteTargetFolder();
      createTargetFolder();
    } catch( ProblemException exception ) {
      throw new ParametricJsdlException( Messages.ParametricJsdlSaver_errUnableToPrepareFolder, exception );
    }
  }
  
  private void deleteTargetFolder() throws ProblemException {
    if ( this.targetFolder.exists() ) {
      try {
        this.targetFolder.delete( true, null );
      } catch ( CoreException exception ) {
        String msg = String.format( Messages.ParametricJsdlSaver_errCannotDeleteFolder, this.targetFolder.getLocation().toString() );
        throw new ProblemException( "eu.geclipse.jsdl.problem.deleteExistingFolderFailed", msg, exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
      }
    }
    
  }

  private void createTargetFolder() throws ProblemException {
    List<IFolder> parentList = new ArrayList<IFolder>();
    IContainer container = this.targetFolder;
        
    while ( container instanceof IFolder ) {
      parentList.add( (IFolder)container );
      container = container.getParent();
    }
    
    ListIterator<IFolder> iterator = parentList.listIterator( parentList.size() );
    
    while ( iterator.hasPrevious() ) {
      IFolder folder = iterator.previous();
      
      if ( !folder.exists() ) {
        try {
          folder.create( true, true, null );
        } catch ( CoreException exception ) {
          String msg = String.format( Messages.ParametricJsdlSaver_errCannotCreateFolder, folder.getLocation().toString() );
          throw new ProblemException( "eu.geclipse.jsdl.problem.createFolderFailed", msg, exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
        }
      }      
    }
  }
  
  /**
   * @return list of generated {@link JSDLJobDescription} objects
   */
  public List<JSDLJobDescription> getGeneratedJsdl() {
    return this.generatedJsdlList;
  }

  public boolean isCanceled() {
    return this.monitor.isCanceled();
  }

}
