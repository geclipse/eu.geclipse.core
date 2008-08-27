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
package eu.geclipse.jsdl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.internal.Activator;
import eu.geclipse.jsdl.internal.ParametricAssignment;


/**
 * This class gets parametric jsdl and generate a bunch of non-parametric jsdl
 */
public class ParametricJsdlGenerator {
  private static ParametricJsdlGenerator generator;
  private XPath xpathEngine;
  private XPathExpression xPathSweeps;
  private XPathExpression xPathAssignment;
  private XPathExpression xPathValues;
  private XPathExpression xPathParameters;
  
  private static class GenerationParams {
    JSDLJobDescription parametricJsdl;
    
    /**
     * Contains current values of iterations  during generation.
     * List have to be used because sweeps can be nested
     */
    List<Integer> iterationsStack;  // contains current values of iteration numbers during generation
    
    // TODO mariusz probably baseJsdl member can be removed
    Document baseJsdl;  // original jsdl without sweep nodes    
    IFolder targetFolder;
    List<JSDLJobDescription> generatedJsdlList;

    GenerationParams( final JSDLJobDescription parametricJsdl, final Document baseJsdl, final IFolder targetFolder, final List<JSDLJobDescription> generatedJsdlList ) {
      this.parametricJsdl = parametricJsdl;
      this.baseJsdl = baseJsdl;
      this.targetFolder = targetFolder;
      this.generatedJsdlList = generatedJsdlList;
      this.iterationsStack = new ArrayList<Integer>();
    }
  }
  
  /**
   * @return add instantion of generation
   */
  public static ParametricJsdlGenerator getInstance() {
    if( generator == null ) {
      generator = new ParametricJsdlGenerator();
    }
    return generator;
  }
  
  /**
   * Generate individual jsdl using parametric job extension. Generated jsld have no sweep extension
   * @param parametricJsdl
   * @param targetFolder
   * @return list of generated jsdl
   */
  public List<JSDLJobDescription> generateJsdls( final JSDLJobDescription parametricJsdl,
                             final IFolder targetFolder )
  {
    List<JSDLJobDescription> generatedJsdlList = new ArrayList<JSDLJobDescription>();
    
    // TODO mariusz handle progress monitor
    try {
       
      Document parametricXml = parametricJsdl.getXml();
      Document baseJsdl = removeSweepNodes( parametricXml );
      GenerationParams generationParams = new GenerationParams( parametricJsdl, baseJsdl,
                                                                targetFolder, generatedJsdlList );
      deleteTargetFolder( targetFolder );
      createTargetFolder( targetFolder );
      NodeList sweeps = findSweeps( parametricXml.getDocumentElement() );
      processSweeps( sweeps, baseJsdl, generationParams );
    } catch ( ProblemException exc ) {
      // TODO mariusz 
      Activator.logException( exc );
    }
    
    return generatedJsdlList;
  }
  
  private void deleteTargetFolder( final IFolder targetFolder ) {
    if ( targetFolder.exists() ) {
      // TODO mariusz add progress monitor
      try {
        targetFolder.delete( true, null );
      } catch ( CoreException exception ) {
        // TODO mariusz Auto-generated catch block
        Activator.logException( exception );
      }
    }
    
  }

  private void createTargetFolder( final IFolder targetFolder ) {
    List<IFolder> parentList = new ArrayList<IFolder>();
    IContainer container = targetFolder;
        
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
   * @param parametricJsdl
   * @return temporary folder, in which jsdl can be generated
   */
  public IFolder getDefaultTargetFolder( final JSDLJobDescription parametricJsdl ) {
    IFolder targetFolder = null;
    try {
      IGridPreferences hiddenProject = GridModel.getPreferences();
      IFolder temporaryFolder = hiddenProject.getTemporaryFolder();
      String name = parametricJsdl.getName();
      
      targetFolder = temporaryFolder.getFolder( name );
      
    } catch ( ProblemException exception ) {
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    } catch ( CoreException exception ) {
      // TODO mariusz Auto-generated catch block
      Activator.logException( exception );
    }
    
    return targetFolder;    
  }

  private NodeList findSweeps( final Node node ) throws ProblemException
  {
    try {
      if( this.xPathSweeps == null ) {
          this.xPathSweeps = getXPathEngine().compile( "./sweep:Sweep" ); //$NON-NLS-1$
      }
      
      return (NodeList)this.xPathSweeps.evaluate( node, XPathConstants.NODESET );      
      
    } catch( XPathExpressionException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.createXPathQueryFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$

    }   
  }
  
  private NodeList findAssignments( final Element sweepElement ) throws ProblemException
  {
    try {
      if( this.xPathAssignment == null ) {
        this.xPathAssignment = getXPathEngine().compile( "./sweep:Assignment" ); //$NON-NLS-1$
      }
      
      return (NodeList)this.xPathAssignment.evaluate( sweepElement, XPathConstants.NODESET );      
      
    } catch( XPathExpressionException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.createXPathQueryFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$

    }   
  }
  
  private NodeList findValues( final Element assignmentElement ) throws ProblemException
  {
    try {
      if( this.xPathValues == null ) {
        this.xPathValues = getXPathEngine().compile( "./sweepfunc:Values/sweepfunc:Value" ); //$NON-NLS-1$
      }
      
      return (NodeList)this.xPathValues.evaluate( assignmentElement, XPathConstants.NODESET );      
      
    } catch( XPathExpressionException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.createXPathQueryFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }   
  }  
  
  private NodeList findParameters( final Element assignmentElement ) throws ProblemException
  {
    try {
      if( this.xPathParameters == null ) {
        this.xPathParameters = getXPathEngine().compile( "./sweep:Parameter" ); //$NON-NLS-1$
      }
      
      return (NodeList)this.xPathParameters.evaluate( assignmentElement, XPathConstants.NODESET );      
      
    } catch( XPathExpressionException exception ) {
      throw new ProblemException( "eu.geclipse.jsdl.problem.createXPathQueryFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }   
  }

  private void processSweeps( final NodeList sweeps, final Document processedJsdl, final GenerationParams generationParams ) throws ProblemException {
    List<ParametricAssignment> assignmentList = new ArrayList<ParametricAssignment>();
    
    generationParams.iterationsStack.add( new Integer( 0 ) );
    
    for( int index = 0; index < sweeps.getLength(); index++ ) {      
      Node sweepItem = sweeps.item( index );
      
      if( sweepItem instanceof Element ) {
        Element currentSweep = ( Element )sweepItem;
        
        processAssignments( findAssignments( currentSweep ), assignmentList );
        NodeList childSweeps = findSweeps( sweepItem );
        
        // TODO mariusz Throw exception when maxIterations is 0 (not values defined)
        int maxIterations = countIterations( assignmentList );
        
        for( int iteration = 0; iteration < maxIterations; iteration++ ) {
          // we will modify copy of jsdl
          Document currentJsdl = ( Document )processedJsdl.cloneNode( true );
          subtituteParams( assignmentList, iteration, currentJsdl );
          
          if( childSweeps.getLength() > 0 ) {
            processSweeps( childSweeps, currentJsdl, generationParams );
          } else {
            saveJsdl( currentJsdl, generationParams );
          }
          
          increaseCurrentIteration( generationParams.iterationsStack );
        }
        
        
      } // TODO mariusz throw exc: expected sweep with children
    }
    
    generationParams.iterationsStack.remove( generationParams.iterationsStack.size() - 1 );
  }

  private void increaseCurrentIteration( final List<Integer> iterationsStack ) {
    int lastIndex = iterationsStack.size() - 1;
    Integer iteration = iterationsStack.get( lastIndex );
    iterationsStack.set( lastIndex, Integer.valueOf( iteration.intValue() + 1 ) );    
  }

  private void saveJsdl( final Document currentJsdl,
                         final GenerationParams generationParams )
  {
    try {
      String filename = getJsdlFileName( generationParams );
      
      IFile file = generationParams.targetFolder.getFile( filename );
      
      DOMSource source = new DOMSource( currentJsdl );

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
        generationParams.generatedJsdlList.add( (JSDLJobDescription)gridElement );
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

  private String getJsdlFileName( final GenerationParams generationParams ) {
    Path jsdlName = new Path( generationParams.parametricJsdl.getName() );
    StringBuilder builder = new StringBuilder( jsdlName.removeFileExtension().toString() );
    
    for( Integer iteration : generationParams.iterationsStack ) {
      builder.append( String.format( "[%03d]", iteration ) ); //$NON-NLS-1$
    }
    
    builder.append( ".jsdl" ); //$NON-NLS-1$
    
    return builder.toString();
  }

  private void subtituteParams( final List<ParametricAssignment> assignmentList,
                                final int iteration,
                                final Document currentJsdl )
  {
    for( ParametricAssignment assignment : assignmentList ) {
      if( iteration < assignment.getValuesCount() ) {
        assignment.substituteParams( currentJsdl, iteration );
      }      
    }
  }


  private int countIterations( final List<ParametricAssignment> assignmentList ) {
    int maxValuesCount = 0;
    for( ParametricAssignment parametricAssignment : assignmentList ) {
      if( maxValuesCount < parametricAssignment.getValuesCount() ) {
        maxValuesCount = parametricAssignment.getValuesCount();
      }
    }
    return maxValuesCount;
  }

  private void processAssignments( final NodeList assignmentsList,
                                   final List<ParametricAssignment> assignmentList ) throws ProblemException
  {
    for( int index = 0; index < assignmentsList.getLength(); index++ ) {
      Node item = assignmentsList.item( index );
      
      if( item instanceof Element ) {
        Element assignment = ( Element )item;
        
        NodeList values = findValues( assignment );
        NodeList parameters = findParameters( assignment );
        
        assignmentList.add( new ParametricAssignment( getXPathEngine(), parameters, values ) );
                
      } // TODO mariusz throw exception: children expected
    }    
  }

  private Document removeSweepNodes( final Document parametricJsdlDocument ) {
    Document changedDocument = ( Document )parametricJsdlDocument.cloneNode( true );
    NodeList nodes = changedDocument.getElementsByTagName( "sweep:Sweep" ); //$NON-NLS-1$
    for( int index = 0; index < nodes.getLength(); index++ ) {
      Node node = nodes.item( index );
      changedDocument.getDocumentElement().removeChild( node );
    }
    return changedDocument;
  }

  // TODO mariusz remove that method:
  private void printXml( final Document document ) {
    try {
    document.normalizeDocument();    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.transform( new DOMSource( document ), new StreamResult( outputStream ) );
    System.out.println( outputStream.toString() );
    } catch ( Exception e) {
      Activator.logException( e );
    }
    
  }

  
  private XPath getXPathEngine() {
    if( this.xpathEngine == null ) {
      this.xpathEngine = XPathFactory.newInstance().newXPath();
      this.xpathEngine.setNamespaceContext( getNamespaceContext() );
    }
    return this.xpathEngine;    
  }
  
  
  private NamespaceContext getNamespaceContext() {
    
    return new NamespaceContext() {
// TODO mariusz parse source jsdl document add here all prefixes and namespaces (user may use these prefixes)

      public String getNamespaceURI( final String prefix ) {
        String namespace = XMLConstants.NULL_NS_URI;
        if ("jsdl".equals(prefix)) {  //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl"; //$NON-NLS-1$
        }
        else if( "jsdl-posix".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"; //$NON-NLS-1$
        }
        else if( "sweep".equals( prefix ) ) {  //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep" ; //$NON-NLS-1$
        }
        else if( "sweepfunc".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep/functions" ; //$NON-NLS-1$
        }
        else if ("xml".equals(prefix)) { //$NON-NLS-1$
          namespace = XMLConstants.XML_NS_URI;
        }        
        return namespace;
      }

      public String getPrefix( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }

      @SuppressWarnings("unchecked")
      public Iterator getPrefixes( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }};
  }


}
