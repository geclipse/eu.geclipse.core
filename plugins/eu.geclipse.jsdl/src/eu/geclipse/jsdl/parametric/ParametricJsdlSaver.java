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
package eu.geclipse.jsdl.parametric;

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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.internal.Activator;


/**
 * Handler, which stores generated JSDLs in the workspace
 */
public class ParametricJsdlSaver implements IParametricJsdlHandler {

  private JSDLJobDescription parametricJsdl;
  private IFolder targetFolder;
  private List<JSDLJobDescription> generatedJsdlList;

  /**
   * @param parametricJsdl
   * @param targetFolder
   */
  public ParametricJsdlSaver( final JSDLJobDescription parametricJsdl, final IFolder targetFolder ) {
    super();
    this.parametricJsdl = parametricJsdl;
    this.targetFolder = targetFolder;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IParametricJsdlHandler#newJsdlGenerated(org.w3c.dom.Document, java.util.List)
   */
  public void newJsdlGenerated( final IGeneratedJsdl generatedJsdl,
                                final IProgressMonitor monitor )
  {
    saveJsdl( generatedJsdl );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.jsdl.parametric.IParametricJsdlHandler#paramSubstituted(java.lang.String, java.lang.String)
   */
  public void paramSubstituted( final String paramName, final String newValue, final IProgressMonitor newParam ) {
    // TODO mariusz Auto-generated method stub
  }
  
  private void saveJsdl( final IGeneratedJsdl generatedJsdl )
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
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    }
    catch ( FileNotFoundException exception ) {
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    } catch ( IOException exception ) {
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    } catch ( CoreException exception ) {
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    }
  }

  private String getFileName( final IGeneratedJsdl generatedJsdl ) {
    return String.format( "%s%s.jsdl", new Path( this.parametricJsdl.getName() ).lastSegment(), generatedJsdl.getIterationName() );     //$NON-NLS-1$
  }

  public void generationFinished() throws ProblemException {
    // TODO mariusz Auto-generated method stub
    
  }

  public void generationStarted( final int generatedJsdl, final List<String> paramNames ) throws ProblemException {
    this.generatedJsdlList = new ArrayList<JSDLJobDescription>();
    
    deleteTargetFolder();
    createTargetFolder();      
  }
  
  private void deleteTargetFolder() {
    if ( this.targetFolder.exists() ) {
      // TODO mariusz add progress monitor
      try {
        this.targetFolder.delete( true, null );
      } catch ( CoreException exception ) {
        // TODO mariusz Auto-generated catch block
        Activator.logException( exception );
      }
    }
    
  }

  private void createTargetFolder() {
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
          // TODO mariusz Auto-generated catch block
          Activator.logException( exception );
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

}
