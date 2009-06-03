/*****************************************************************************
 * Copyright (c) 2006-2009 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.model.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.ProjectVo;
import eu.geclipse.core.internal.model.VoManager;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IStorableElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Abstract implementation of the
 * {@link eu.geclipse.core.model.IVirtualOrganization} interface.
 */
public abstract class AbstractVirtualOrganization
    extends AbstractGridContainer
    implements IVirtualOrganization {
  
  /**
   * Create a new VO.
   */
  protected AbstractVirtualOrganization() {
    super();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return ( element instanceof IGridService )
      || ( element instanceof IGridApplicationManager );
  }
  
  @Override
  public void dispose() {
    IFileStore fileStore = getFileStore();
    try {
      fileStore.delete( EFS.NONE, null );
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
  }
  
  public IGridApplicationManager getApplicationManager() {
    return null;
  }
  
  public IGridResource[] getAvailableResources( final IGridResourceCategory category,
                                                final boolean exclusive,
                                                final IProgressMonitor monitor )
    throws ProblemException
  {
    IGridResource[] resources = null;
    if( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_JOB_SERVICES ) ) )
    {
      resources = getJobSubmissionServices( monitor );
    } else if( category.equals( GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_SERVICES ) ) )
    {
      resources = getServices( monitor );
    } else {
      IGridInfoService infoService = getInfoService();
      if( infoService != null ) {
        resources = infoService.fetchResources( this,
                                                this,
                                                category,
                                                false,
                                                null,
                                                monitor );
      }
    }
    Set<IGridResource> resourcesSet = new HashSet<IGridResource>();
/*    if (resources != null) resourcesSet.addAll( Arrays.asList( resources ) );
    //TODO add local resources, how to filter this by categories?
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridResource ) {
        resourcesSet.add( ( IGridResource )child );
      }
    }
    resources = resourcesSet.toArray( new IGridResource[resourcesSet.size()] );*/
    return resources;
  }
  
  public IGridComputing[] getComputing( final IProgressMonitor monitor ) throws ProblemException {
    IGridComputing[] computing = null;
    IGridInfoService infoService = getInfoService();
    IGridResource[] myComputingResources = null;
    if ( infoService != null ) {
      myComputingResources = infoService.fetchResources( this,
                                                         this,
                                                         GridResourceCategoryFactory
                                                           .getCategory( GridResourceCategoryFactory.ID_COMPUTING ),
                                                         false,
                                                         IGridComputing.class,
                                                         monitor );
      computing = new IGridComputing[ myComputingResources.length ];
      System.arraycopy( myComputingResources, 0, computing, 0, myComputingResources.length );
    }
    else
      computing = new IGridComputing[ 0 ];
    
    return computing;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    IFileStore fileStore = VoManager.getVoManagerStore().getChild( getName() );
    IFileInfo fileInfo = fileStore.fetchInfo();
    if ( !fileInfo.exists() ) {
      try {
        fileStore.mkdir( EFS.NONE, null );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    return fileStore;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getInfoService()
   */
  public IGridInfoService getInfoService()
      throws ProblemException {
    IGridInfoService infoService = null;
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridInfoService ) {
        infoService = ( IGridInfoService ) child;
        break;
      }
    }
    return infoService;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getParent()
   */
  public IGridContainer getParent() {
    return VoManager.getManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getPath()
   */
  public IPath getPath() {
    IPath path = new Path( VoManager.NAME );
    return path.append( getName() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getServices()
   */
  public IGridService[] getServices( final IProgressMonitor monitor )
      throws ProblemException {
    IGridResource[] resources = null;
    IGridInfoService infoService = getInfoService();
    
    if ( infoService != null ) {
      resources = infoService.fetchResources( this,
                                              this,
                                              GridResourceCategoryFactory
                                              .getCategory( GridResourceCategoryFactory.ID_SERVICES ),
                                              false,
                                              IGridService.class,
                                              monitor );
    }
    //add local services
    Set<IGridResource> resourcesSet = new HashSet<IGridResource>();
    if (resources != null) resourcesSet.addAll( Arrays.asList( resources ) );
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridService ) {
        resourcesSet.add( ( IGridResource )child );
      }
    }
    return resourcesSet.toArray( new IGridService[resourcesSet.size()] );    
  }
  
  public IGridStorage[] getStorage( final IProgressMonitor monitor ) throws ProblemException {
    IGridStorage[] storage = null;
    IGridResource[] myGridStorage = null;
    IGridInfoService infoService = getInfoService();
    
    if ( infoService != null ) {
      myGridStorage = infoService.fetchResources( this,
                                                  this,
                                                  GridResourceCategoryFactory
                                                    .getCategory( GridResourceCategoryFactory.ID_STORAGE ),
                                                  false,
                                                  IGridStorage.class,
                                                  monitor );
      storage = new IGridStorage[ myGridStorage.length ];
      System.arraycopy( myGridStorage, 0, storage, 0, myGridStorage.length );
    }
    else 
      storage = new IGridStorage[ 0 ];
    
    return storage;
  }
  
  public IGridResourceCategory[] getSupportedCategories() {
    return ProjectVo.standardCategories;
  }
  
  public IGridJobService[] getJobSubmissionServices( final IProgressMonitor monitor ) throws ProblemException {
    IGridResource[] resources = null;
    
    IGridInfoService infoService = getInfoService();
    if ( infoService != null ) {
      resources = infoService.fetchResources( this,
                                              this,
                                              GridResourceCategoryFactory
                                              .getCategory( GridResourceCategoryFactory.ID_JOB_SERVICES ),
                                              false,
                                              IGridJobService.class,
                                              monitor );
    }
    //add local services
    Set<IGridResource> resourcesSet = new HashSet<IGridResource>();
    if (resources != null) resourcesSet.addAll( Arrays.asList( resources ) );
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IGridJobService ) {
        resourcesSet.add( ( IGridResource )child );
      }
    }
    return resourcesSet.toArray( new IGridJobService[resourcesSet.size()] );    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#load()
   */
  public void load() throws ProblemException {
    deleteAll();
    IFileStore fileStore = getFileStore();
    try {
      IFileStore[] childStores = fileStore.childStores( EFS.NONE, null );
      for ( IFileStore child : childStores ) {
        IGridElement element = loadChild( child.getName() );
        if ( element != null ) {
          addElement( element );
        }
      }
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_LOAD_FAILED,
                                  cExc,
                                  Activator.PLUGIN_ID );
    }
  }
  
  public void refreshResources( final IGridResourceCategory category,
                                final IProgressMonitor monitor )
      throws ProblemException {
    // TODO mathias may this stay to be a noop?
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#save()
   */
  public void save() throws ProblemException {
    
    IGridElement[] children = getChildren( null );
    for ( IGridElement child : children ) {
      if ( child instanceof IStorableElement ) {
        ( ( IStorableElement ) child ).save();
      } else {
        saveChild( child );
      }
    }
    
    // Below is experimental code for saving VOs in XML format
    /*
    try {
      
      IFileStore fileStore = getFileStore();
      IFileStore voIdFile = fileStore.getChild( ".id" );
      OutputStream voIdOStream = voIdFile.openOutputStream( EFS.NONE, null );
      voIdOStream.write( getClass().getName().getBytes() );
      voIdOStream.close();
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating( true );
      factory.setNamespaceAware( true );
      
      DocumentBuilder builder = factory.newDocumentBuilder();
      
      Document document = builder.newDocument();
      
      Element voNode = document.createElement( "vo" );
      voNode.setAttribute( "name", getName() );
      voNode.setAttribute( "id", getId() );
      document.appendChild( voNode );
      
      Hashtable< String, String > props = getStaticProperties();
      if ( ( props != null ) && ! props.isEmpty() ) {
        Element propsNode = document.createElement( "properties" );
        voNode.appendChild( propsNode );
        for ( String key : props.keySet() ) {
          String value = props.get( key );
          Element keyNode = document.createElement( "property" );
          keyNode.setAttribute( "key", key );
          keyNode.setAttribute( "value", value );
          propsNode.appendChild( keyNode );
        }
      }
      
      List< IGridService > services = getStaticServices();
      if ( ( services != null ) && ! services.isEmpty() ) {
        Element servicesNode = document.createElement( "services" );
        voNode.appendChild( servicesNode );
        for ( IGridService service : services ) {
          Element serviceNode = document.createElement( "service" );
          serviceNode.setAttribute( "type", service.getClass().getName() );
          serviceNode.setAttribute( "endpoint", service.getURI().toString() );
          servicesNode.appendChild( serviceNode );
        }
      }
      
      Element specElement = document.createElement( "specific" );
      if ( createSpecificPart( specElement ) ) {
        voNode.appendChild( specElement );
      }

      File file = fileStore.getChild( "vo.xml" ).toLocalFile( EFS.NONE, null );
      FileOutputStream foStream = new FileOutputStream( file );
      
      DOMSource domSource = new DOMSource( document );
      StreamResult streamResult = new StreamResult( foStream );
      
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
      transformer.transform( domSource, streamResult );
      
    } catch ( ParserConfigurationException pcExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, pcExc, Activator.PLUGIN_ID );
    } catch ( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, cExc, Activator.PLUGIN_ID );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, ioExc, Activator.PLUGIN_ID );
    } catch ( TransformerConfigurationException tcExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, tcExc, Activator.PLUGIN_ID );
    } catch ( TransformerException tExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED, tExc, Activator.PLUGIN_ID );
      
    }
    */
  }
  
  protected IGridService[] filterServices( final IGridService[] services,
                                           final Class< ? extends IGridService > type,
                                           final boolean remove ) {
    
    List< IGridService > resultList = new ArrayList< IGridService >();
    
    for ( IGridService service : services ) {
      boolean isType = type.isAssignableFrom( service.getClass() );
      if ( ( remove && ! isType ) || ( ! remove && isType ) ) {
        resultList.add( service );
      }
    }
    
    return resultList.toArray( new IGridService[ resultList.size() ] );
    
  }
  
  /*
  protected boolean createSpecificPart( final Element specElement ) {
    return false;
  }
  
  protected Hashtable< String, String > getStaticProperties() {
    return null;
  }
  
  protected List< IGridService > getStaticServices() {
    return null;
  }
  */
  
  /**
   * Load a child with the given name.
   * 
   * @param childName The child's name.
   * @return The element that was loaded or null if no such element
   * could be loaded.
   */
  protected abstract IGridElement loadChild( final String childName );
  
  protected void saveChild( @SuppressWarnings("unused")
                            final IGridElement child ) {
    // empty implementation
  }
  
}
