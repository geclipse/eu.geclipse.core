/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium
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
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *      - Nikolaos Tsioutsias
 *****************************************************************************/

package eu.geclipse.info.glue;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.info.model.IGridSupportedService;

/**
 * 
 * @author George Tsouloupas
 *
 */
public class GlueService extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;
 

  /**
   * 
   */
  public String uniqueId; // PK

  /**
   * 
   */
  public String name;

  /**
   * 
   */
  public String type;

  /**
   * 
   */
  public String version;

  /**
   * 
   */
  public String endpoint;

  /**
   * 
   */
  public String wsdl;

  /**
   * 
   */
  public String semantics;

  /**
   * 
   */
  public String uri;

  /**
   * 
   */
  public String status;

  /**
   * 
   */
  public GlueSite glueSite; // GlueSite_UniqueId

  /**
   * 
   */
  public ArrayList< GlueService > glueServiceList = new ArrayList< GlueService >();
  
  /**
   * 
   */
  public ArrayList< GlueServiceAccessControlRule > glueServiceAccessControlRuleList = 
    new ArrayList< GlueServiceAccessControlRule >();

  /**
   * 
   */
  public ArrayList< GlueServiceAssociation > glueServiceAssociationList = 
    new ArrayList< GlueServiceAssociation >();

  /**
   * 
   */
  //public ArrayList< GlueServiceAssociation > glueServiceAssociationList1 = 
  // new ArrayList< GlueServiceAssociation >();

  /**
   * 
   */
  public ArrayList< GlueServiceData > glueServiceDataList = new ArrayList< GlueServiceData >();

  /**
   * 
   */
  public ArrayList< GlueServiceOwner > glueServiceOwnerList = new ArrayList< GlueServiceOwner >();

  /**
   * 
   */
  public ArrayList< GlueServiceStatus > glueServiceStatusList = new ArrayList< GlueServiceStatus >();

  protected boolean isSupported = false;
  
  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.uniqueId;
  }

  /**
   * Set this.uniqueId
   * @param id
   */
  public void setID( final String id ) {
    this.uniqueId = id;
  }
  
  /**
   * Checks if the current service is supported by g-eclipse 
   * @return true if it is supported and false otherwise.
   */
  public boolean isSupported() {
    setIsSupported();
    
    return this.isSupported;
  }
  
  /**
   * Sets whether this service is supported by geclipse.
   */
  public void setIsSupported()
  {
    boolean result = false;
    
    // Get the supported services
    ArrayList<IGridSupportedService> supportedServicesArray = new ArrayList<IGridSupportedService>();
    IGridSupportedService supportedService = null;
    IExtensionRegistry myRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = myRegistry.getExtensionPoint( "eu.geclipse.info.supportedServices"); //$NON-NLS-1$
    IExtension[] extensions = extensionPoint.getExtensions();
    
    for (int i = 0; i < extensions.length; i++)
    {
      IExtension extension = extensions[i];
 
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for( IConfigurationElement element : elements ) {
        
        try {
          supportedService = (IGridSupportedService) element.createExecutableExtension( "class" ); //$NON-NLS-1$
          if (supportedService != null)
          {
            supportedServicesArray.add( supportedService );
          }
        } catch( CoreException e ) {
          // do nothing
        }
      }
    }
    
    for (int i=0; i<supportedServicesArray.size(); i++)
    {
      if (this.type.equalsIgnoreCase( supportedServicesArray.get( i ).getType() ))
      {
        if (supportedServicesArray.get( i ).getVersion() == null)
          result = true;
        else {
          for (int j=0; j<supportedServicesArray.get( i ).getVersion().length; j++)
          {
            if ( supportedServicesArray.get( i ).getVersion()[j].equalsIgnoreCase( this.version ))
              result = true;
          }
        }
          
      }
    }

    this.isSupported = result;
  }
}