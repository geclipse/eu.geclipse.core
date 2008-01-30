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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;

import eu.geclipse.batch.internal.Activator;
import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.DocumentRoot;
import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeValueType;
import eu.geclipse.batch.model.qdl.util.QdlResourceFactoryImpl;
import eu.geclipse.core.model.IGridBatchQueueDescription;
import eu.geclipse.core.model.impl.ResourceGridContainer;


/**
 * @author nloulloud
 *
 */
public class BatchQueueDescription extends ResourceGridContainer
  implements IGridBatchQueueDescription {

  protected DocumentRoot documentRoot = null;
  protected QueueType queueType = null;
  protected AllowedVirtualOrganizationsType allowedVOs = null;
  
  private Resource resourceT = null; 

  /**
   * @param file
   */
  public BatchQueueDescription( final IFile file ) {
    super( file );
    createRoot();
  }
   

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridBatchQueueDescription#getAllowedVirtualOrganizations()
   */
  
  /**
   * Returns a collection of strings containing the name of 
   * Virtual Organizations allowed to use the Queue.. 
   */
  public Collection<String> getAllowedVirtualOrganizations() {
    
    
    Collection<String> result = new ArrayList<String>();
    if (this.allowedVOs != null) {
      result = this.allowedVOs.getVOName();
    }
    
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridBatchQueueDescription#getQueueName()
   */
  public String getQueueName() {    
    return this.queueType.getQueueName();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridBatchQueueDescription#getQueueStatus()
   */
  
  /**
   * Returns ENABLED or DISABLED based on the Queue Status.
   */
  public String getQueueStatus() {
    String result = null;
    if (this.queueType.getQueueStatus() == QueueStatusEnumeration.ENABLED ){
      result = "ENABLED"; //$NON-NLS-1$
    }
    else{
      result = "DISABLED"; //$NON-NLS-1$
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridBatchQueueDescription#getQueueType()
   */
  /**
   * Returns EXECUTION if this is an Execution Queue.
   * Returns ROUTE if this is a Route Queue.
   */
  public String getQueueType() {
    
    String result = null;
    if (this.queueType.getQueueType() == QueueTypeEnumeration.EXECUTION ){
      result = "EXECUTION"; //$NON-NLS-1$
    }
    else{
      result = "ROUTE"; //$NON-NLS-1$
    }
    return result;
  }
  
  
  
  /**
   * 
   * @return the {@link RangeValueType} Max CPU Time object for the Queue.
   * This could be an UpperBoundedRange or LowerBoundedRange value.
   */
   public RangeValueType getQueueMaxCPUTime() {
     
     return this.queueType.getCPUTimeLimit();
   }
   
   
   /**
    * 
    * @return the {@link RangeValueType} Max Wall Time object for the Queue.
    * This could be an UpperBoundedRange or LowerBoundedRange value.
    */
    public RangeValueType getQueueMaxWallTime() {
      return this.queueType.getWallTimeLimit();
    }
  
  
  /**
   * 
   * @return the Max CPU Time Value for the Queue. Returns -1 if value is not set
   */
   public double getQueueMaxCPUTimeValue() {
   
     double result = -1;
   
     if( this.queueType.getCPUTimeLimit() != null) {
       result = this.queueType.getCPUTimeLimit().getUpperBoundedRange().getValue();
     }
     return result;
   }
 
 
  /**
   * 
   * @return the Max Wall Time Value for the Queue. Returns -1 if value is not set
   */
   public double getQueueMaxWallTimeValue() {
    
     double result = -1;
    
     if( this.queueType.getWallTimeLimit() != null) {
       result = this.queueType.getWallTimeLimit().getUpperBoundedRange().getValue();
     }
     return result;
   }
    
  
  /**
   * @param status Set's the Queue Status to be ENABLED or DISABLED.
   */
  public void isQueueEnabled(final boolean status) {
    if (status){
      this.queueType.setQueueStatus( QueueStatusEnumeration.ENABLED );
    }
    else{
      this.queueType.setQueueStatus( QueueStatusEnumeration.DISABLED );
    }
  }
  
  /**
   * @param enumeration Set's the Queue Type. QueueType is {@link QueueTypeEnumeration}
   */
  public void queueType(final QueueTypeEnumeration enumeration) {
    this.queueType.setQueueType( enumeration );
  }
  
  
  /**
   * @param collection Set's the Virtual Organizations that will be allowed to use the Queue.
   */
  public void setAllowedVirtualOrganizations(final Collection<String> collection) {
    this.allowedVOs = QdlFactory.eINSTANCE.createAllowedVirtualOrganizationsType();
    this.allowedVOs.getVOName().addAll( collection );
    this.queueType.setAllowedVirtualOrganizations( this.allowedVOs );
    
  }
  
  
  /**
   * @param queueName The Queue Name
   */
  public void setQueueName(final String queueName) {
    this.queueType.setQueueName( queueName );
  }
  
  
  /**
   * @param root The Qdl Document Root
   */
  public void setRoot(final DocumentRoot root) {
    this.documentRoot = root;
    
    if (root.getQueue() != null) {
      this.queueType = root.getQueue();
    }
    
  }
  
  
  /**
   * @return The Qdl Document Root
   */
  public DocumentRoot getRoot() {
    return this.documentRoot;
  }
  
  
  /**
   * Create the Qdl Root Element with all necessary children (QueueType).
   */
  public void createRoot() {
    this.documentRoot = QdlFactory.eINSTANCE.createDocumentRoot();
    this.queueType = QdlFactory.eINSTANCE.createQueueType();
    this.documentRoot.setQueue( this.queueType );
  }
  
  /**
   * @param file
   */
  public void loadModel( final IFile file ) {
    String filePath = file.getFullPath().toString();
    URI uri = URI.createPlatformResourceURI( filePath, false );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resourceA = resourceSet.createResource( uri );
    XMLMapImpl xmlmap = new XMLMapImpl();
    xmlmap.setNoNamespacePackage( QdlPackage.eINSTANCE );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_XML_MAP, xmlmap );
    options.put( XMLResource.OPTION_ENCODING, "UTF-8" ); //$NON-NLS-1$
    try {
      resourceA.load( options );
      this.documentRoot = ( DocumentRoot )resourceA.getContents().get( 0 );
    } catch( IOException ioEx ) {
      Activator.logException( ioEx );
    }
  }
  
  
  /**
   * @param qdlFile
   */
  public void save(final IFile qdlFile) {
    writeToFile(qdlFile);
  }
  
  
  /**
   *
   * @param qdlFile The Qdl File.
   * @return true if the model has been written correctly to the file.
   */
  public boolean writeToFile(final IFile qdlFile) {
    boolean ret = false;
  
    String filePath = qdlFile.getFullPath().toString();
    URI fileURI = URI.createPlatformResourceURI( filePath, false );
    // Create resource set.
    ResourceSet resourceSet = new ResourceSetImpl();
    Registry factoryRegistry = resourceSet.getResourceFactoryRegistry();
    Map<String, Object> map = factoryRegistry.getExtensionToFactoryMap();
    map.put( "qdl", new QdlResourceFactoryImpl() ); //$NON-NLS-1$
    this.resourceT = resourceSet.createResource( fileURI );
  
    if(this.documentRoot != null) {
      this.resourceT.getContents().add(this.documentRoot);
    }
    Map<String, String> options = new HashMap<String, String>();
    options.put( XMLResource.OPTION_ENCODING, "UTF-8" ); //$NON-NLS-1$

    try {
      this.resourceT.save(options);
    } catch( IOException ioEx ) {
      Activator.logException( ioEx );
    }
  
    return ret;
  }


  /**
   * @param path
   */
  public void load( final String path ) /*throws GridModelException*/ {
    String filePath = path;
    URI uri = URI.createPlatformResourceURI( filePath, false );
    ResourceSet resourceSet = new ResourceSetImpl();
    Resource resourceA = resourceSet.createResource( uri );
    XMLMapImpl xmlmap = new XMLMapImpl();
    xmlmap.setNoNamespacePackage( QdlPackage.eINSTANCE );
    Map<String, Object> options = new HashMap<String, Object>();
    options.put( XMLResource.OPTION_XML_MAP, xmlmap );
    options.put( XMLResource.OPTION_ENCODING, "UTF-8" ); //$NON-NLS-1$
    try {
      resourceA.load( options );
      this.documentRoot = ( DocumentRoot )resourceA.getContents().get( 0 );
      this.queueType = this.documentRoot.getQueue();
      this.allowedVOs = this.queueType.getAllowedVirtualOrganizations();
    } catch( IOException ioEx ) {
      Activator.logException( ioEx );
    }
  }
 
  
}
 