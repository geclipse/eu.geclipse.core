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

package eu.geclipse.core.model.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;

/**
 * Abstract implementation of the {@link IGridJobCreator} interface
 * that delegates the {@link #canCreate(IGridJobDescription)} method to
 * an internal one and handles the storage of the argument of this
 * function.
 */
public abstract class AbstractGridJobCreator
    extends AbstractGridElementCreator
    implements IGridJobCreator {
  
  /**
   * The job description from the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   */
  private IGridJobDescription internalDescription;

  public boolean canCreate( final IGridJobDescription description ) {
    boolean result = internalCanCreate( description );
    if ( result ) {
      this.internalDescription = description;
    }
    return result;
  }
  
  /**
   * Get the job description from the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   * 
   * @return The argument of the last successful call of the
   * {@link #canCreate(IGridJobDescription)} method.
   */
  public IGridJobDescription getDescription() {
    return this.internalDescription;
  }
  
  /**
   * Internal method to determine if this creator is potentially 
   * able to create jobs from the specified description. This method
   * is called from {@link #canCreate(IGridJobDescription)} and has
   * not to care about storing the passed object since this is
   * handled by the {@link #canCreate(IGridJobDescription)} method.
   * Therefore this method may never be called directly.
   * 
   * @param description The description from which the job should be
   * created.
   * @return True if this creator is potentially able to create a
   * job from the specified description.
   */
  protected abstract boolean internalCanCreate( final IGridJobDescription description );

  
  
  /**
   * @param baseName
   * @param folder
   * @return
   */
  private IFile findJobFileName( final IGridJobDescription description, final IFolder folder )
  {
    String baseName = description.getPath().removeFileExtension().lastSegment();
    // IFolder folder = ( IFolder )parent.getResource();
    String name = baseName + ".job"; //$NON-NLS-1$
    IFile jobFile = folder.getFile( name );
    int jobNum = 0;
    while( jobFile.exists() ) {
      jobNum++;
      name = baseName + "[" + jobNum + "].job"; //$NON-NLS-1$
      jobFile = folder.getFile( name );
    }
    return jobFile;
  }

  /**
   * @param id
   * @param descFile
   * @return
   * @throws ParserConfigurationException
   * @throws SAXException
   * @throws IOException
   * @throws CoreException
   */
  private Document createJobDOMDocument( final IGridJobID id, final IFile descFile )
    throws ParserConfigurationException, SAXException, IOException,
    CoreException
  {
    Document document;
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document2 = builder.parse( descFile.getContents() );
    document = builder.newDocument();
    Element root = ( Element )document.createElement( "Job" );
    document.appendChild( root );
    root.setAttribute( "type", "gLite" );
    Element jobIDNode = document.createElement( "jobID" );
    root.appendChild( jobIDNode );
    jobIDNode.appendChild( document.createTextNode( id.getJobID() ) );
    Element jobStatusNode = document.createElement( "jobStatus" );
    root.appendChild( jobStatusNode );
    String jobDesc="";
    BufferedReader br =new BufferedReader(new InputStreamReader(descFile.getContents()));
    String line;
    while((line=br.readLine())!=null)
    {
      jobDesc+="\n"+line;
    }
    Node jobDescData=document.createCDATASection( jobDesc );
    Element jobDescNode = document.createElement( "jobDescription" );
    root.appendChild( jobDescNode );
    jobDescNode.appendChild( jobDescData );
//    Node tmp = document.importNode( document2.getDocumentElement(), true );
//    jobDescNode.appendChild( tmp );
    return document;
  }

  /**
   * @param document
   * @return
   * @throws TransformerFactoryConfigurationError
   * @throws TransformerConfigurationException
   * @throws TransformerException
   */
  private String transformDOMtoString( final Document document )
    throws TransformerFactoryConfigurationError,
    TransformerConfigurationException, TransformerException
  {
    TransformerFactory transFactory = TransformerFactory.newInstance();
    DOMSource source = new DOMSource( document );
    Transformer transformer = transFactory.newTransformer();
    transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
    transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount",
                                   "4" );
    StringWriter sw = new StringWriter();
    StreamResult result = new StreamResult( sw );
    transformer.transform( source, result );
    String xml = sw.toString();
    return xml;
  }

  
  public void create( final IGridContainer parent, final IGridJobID id )
  throws GridModelException
{
//  NewGliteJob job = null;
  IGridJobDescription description = getDescription();
  IFile jobFile = findJobFileName( description,
                                   ( IFolder )parent.getResource() );
  IFile descFile = ( IFile )description.getResource();
  try {
    Document document;
    document = createJobDOMDocument( id, descFile );
    String xml = transformDOMtoString( document );
    System.out.println( xml );
    byte[] byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
    ByteArrayInputStream baos = new ByteArrayInputStream( byteArray );
    jobFile.create( baos, true, null );
  } catch( CoreException cExc ) {
    //TODO pawelw throw new GridModelException( cExc );
  } catch( Exception cExc ) {
    IStatus status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                                  IStatus.OK,
                                                  "Problem while creating job file",
                                                  null );
    //TODO pawelw throw new GridModelException( status );
    // FIXME pawelw - exception
  }
}

  
}
