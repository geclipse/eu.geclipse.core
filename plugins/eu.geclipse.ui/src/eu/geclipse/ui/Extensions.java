/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import eu.geclipse.core.ExtensionManager;
import eu.geclipse.ui.wizards.connection.managers.AbstractConnectionWizardManager;

/**
 * This is a helper class that holds static fields and methods to
 * easily access extension of the g-Eclipse ui extension points.
 * 
 * @author stuempert-m
 */
public class Extensions {
  
  /**
   * The ID of the authentication token ui extension point.
   */
  public static final String AUTH_TOKEN_UI_POINT
    = "eu.geclipse.ui.authTokenUI"; //$NON-NLS-1$
  
  /**
   * The ID of the authentication token ui factory element contained
   * in the authentication token ui extension point. 
   */
  public static final String AUTH_TOKEN_FACTORY_ELEMENT
    = "factory"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the authentication token ui factory
   * configuration element.
   */
  public static final String AUTH_TOKEN_FACTORY_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * The ID of the connection wizard extension point.
   */
  public static final String CONNECTION_WIZARD_POINT
    = "eu.geclipse.ui.connectionWizard"; //$NON-NLS-1$
  
  /**
   * The ID of the wizard pages manager element contained
   * in the connection wizard extension point. 
   */
  public static final String CONNECTION_WIZARD_PAGES_MANAGER_ELEMENT
    = "wizardPagesManager"; //$NON-NLS-1$
  
  /**
   * The ID of the combo wizard extension point.
   */
  public static final String EXTENSION_WIZARD_POINT
    = "eu.geclipse.ui.extensionWizard"; //$NON-NLS-1$
  
  /**
   * The ID of the wizard page manager element contained
   * in the combo wizard extension point. 
   */
  public static final String EXTENSION_WIZARD_PAGE_MANAGER_ELEMENT
    = "manager"; //$NON-NLS-1$
  
  /**
   * Get a list of all currently registered authentication token
   * ui factories.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the authentication token ui
   * configuration elements.
   */
  static public List< IAuthTokenUIFactory > getRegisteredAuthTokenUIFactories() {
    List< IAuthTokenUIFactory > resultList
      = new ArrayList< IAuthTokenUIFactory >();
    ExtensionManager browser = new ExtensionManager();
    List< Object > objectList
      = browser.getExecutableExtensions( AUTH_TOKEN_UI_POINT,
                                         AUTH_TOKEN_FACTORY_ELEMENT,
                                         AUTH_TOKEN_FACTORY_EXECUTABLE );
    for ( Object o : objectList ) {
      if ( o instanceof IAuthTokenUIFactory ) {
        resultList.add( ( IAuthTokenUIFactory ) o );
      }
    }
    return resultList;
  }
  
  static public Hashtable< String, AbstractConnectionWizardManager > getRegisteredConnectionWizardManagers() {
    Hashtable< String, AbstractConnectionWizardManager > resultList
      = new Hashtable< String, AbstractConnectionWizardManager >();
    ExtensionManager extManager = new ExtensionManager();
    List<IConfigurationElement> connectionElements
      = extManager.getConfigurationElements( eu.geclipse.core.Extensions.CONNECTION_MANAGEMENT_POINT,
                                             eu.geclipse.core.Extensions.CONNECTION_PROTOCOL_ELEMENT );
    List<IConfigurationElement> wizardElements
      = extManager.getConfigurationElements( CONNECTION_WIZARD_POINT,
                                             CONNECTION_WIZARD_PAGES_MANAGER_ELEMENT );
    for ( IConfigurationElement cElement : connectionElements ) {
      String name = cElement.getAttribute( "name" ); //$NON-NLS-1$
      String wizardID = cElement.getAttribute( "wizardID" ); //$NON-NLS-1$
      AbstractConnectionWizardManager wManager = null;
      for ( IConfigurationElement wElement : wizardElements ) {
        String id = wElement.getAttribute( "id" ); //$NON-NLS-1$
        if ( id.equals( wizardID ) ) {
          try {
            wManager = ( AbstractConnectionWizardManager ) wElement.createExecutableExtension( "class" ); //$NON-NLS-1$
          } catch( CoreException cExc ) {
            eu.geclipse.ui.internal.Activator.logException( cExc );
          }
          break;
        }
      }
      resultList.put( name, wManager );
    }
    
    return resultList;
  }
  
}
