package eu.geclipse.core.model.impl;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.internal.model.ResourceCategory;
import eu.geclipse.core.model.IGridResourceCategory;

public class GridResourceCategoryFactory {
  
  public static final String ID_APPLICATIONS = "eu.geclipse.core.model.category.applications";
  
  public static final String ID_COMPUTING = "eu.geclipse.core.model.category.computing";
  
  public static final String ID_SERVICES = "eu.geclipse.core.model.category.services";
  
  public static final String ID_DATA_SERVICES = "eu.geclipse.core.model.category.services.data";
  
  public static final String ID_INFO_SERVICES = "eu.geclipse.core.model.category.services.info";
  
  public static final String ID_JOB_SERVICES = "eu.geclipse.core.model.category.services.jobs";
  
  public static final String ID_OTHER_SERVICES = "eu.geclipse.core.model.category.services.others";
  
  public static final String ID_STORAGE = "eu.geclipse.core.model.category.storage";
  
  private static Hashtable< String, IGridResourceCategory > categories
    = new Hashtable< String, IGridResourceCategory >();
  
  private static ExtensionManager manager
    = new ExtensionManager();

  public static IGridResourceCategory getCategory( final String id ) {
    
    IGridResourceCategory result = categories.get( id );
    
    if ( result == null ) {
      List< IConfigurationElement > elements
        = manager.getConfigurationElements( "eu.geclipse.core.gridResourceCategory", "category" );
      for ( IConfigurationElement element : elements ) {
        String extid = element.getAttribute( "id" );
        if ( id.equals( extid ) ) {
          String name = element.getAttribute( "name" );
          String parent = element.getAttribute( "parent" );
          IGridResourceCategory parentCategory = null;
          if ( parent != null ) {
            parentCategory = getCategory( parent );
          }
          result = new ResourceCategory( name, parentCategory );
          categories.put( id, result );
        }
      }
    }
    
    return result;
    
  }

}
