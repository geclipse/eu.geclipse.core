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
 *    Jie Tao - test class (Plug-in test)
 *****************************************************************************/

package eu.geclipse.core;

import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** this is a test class for examining the functionalty of methods in 
 * class ExtensionManager. 
 * @author tao-j
 *
 */

public class ExtensionManager_PDETest {

  ExtensionManager extensionmanager;
  
  /**setup
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception
  {
    // nothing to setup currently
  }

  /** test the method {@link ExtensionManager#ExtensionManager()}
   * 
   *
   */
  
  @Test
  public void testExtensionManager( )
  {
    this.extensionmanager = new ExtensionManager();
    Assert.assertNotNull( this.extensionmanager );
  }

  /** test the method {@link ExtensionManager#ExtensionManager(IExtensionRegistry)}
   * and {@link ExtensionManager#getRegistry()}
   *
   */
  
  @Test
  public void testExtensionManagerIExtensionRegistry()
  {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    IExtensionRegistry registry2;
    this.extensionmanager = new ExtensionManager( registry);
    Assert.assertNotNull( this.extensionmanager );
    registry2 = this.extensionmanager.getRegistry();
    Assert.assertEquals( registry, registry2 );
  }

  /** test the method {@link ExtensionManager#getExtensionPoint(String)}
   * by examining if the achieved point identifier is identical with the
   * given one.
   */
  
  @Test
  public void testGetExtensionPoint()
  {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.extensionmanager = new ExtensionManager( registry);
    String point_ID = "eu.geclipse.core.applicationDeployment"; //$NON-NLS-1$
    IExtensionPoint point = this.extensionmanager.getExtensionPoint( point_ID );
    Assert.assertNotNull( point );
    Assert.assertEquals( "eu.geclipse.core.applicationDeployment",point.getUniqueIdentifier() ); //$NON-NLS-1$
  }

  /** test the method {@link ExtensionManager#getExtensions(String)}
   * the number of extensions must correspond to the actual extensions
   *
   */
  
  @Test
  public void testGetExtensions()
  {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.extensionmanager = new ExtensionManager( registry);
    List< IExtension > extentions;
    String point_ID = "eu.geclipse.core.gridElementCreator"; //$NON-NLS-1$
    extentions = this.extensionmanager.getExtensions( point_ID );
    Assert.assertEquals( new Integer( 16 ),new Integer( extentions.size() ));
    Assert.assertEquals( "eu.geclipse.core.gridElementCreator",extentions.get( 0 ).getExtensionPointUniqueIdentifier() ); //$NON-NLS-1$
  }
  
  /** test the method {@link ExtensionManager#getConfigurationElements(String, String)} 
   * with the extension point eu.geclipse.core.authenticationTokenManagement
   * as example and token as a sample configuration element.
   */

  @Test
  public void testGetConfigurationElements()
  {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.extensionmanager = new ExtensionManager( registry);
    List< IConfigurationElement > elements;
    String point_ID = "eu.geclipse.core.authTokens"; //$NON-NLS-1$
    String element = "token"; //$NON-NLS-1$
    elements = this.extensionmanager.getConfigurationElements( point_ID,element );
    Assert.assertEquals("eu.geclipse.globus.auth.proxy",elements.get( 0 ).getAttribute( "id" )); //$NON-NLS-1$ //$NON-NLS-2$
    Assert.assertEquals("eu.geclipse.gria.auth.keyStore",elements.get( 1 ).getAttribute( "id" )); //$NON-NLS-1$ //$NON-NLS-2$
  }

  /** test the method {@link ExtensionManager#getExecutableExtensions(String, String, String)}
   * the sample extension point is applicationDeployment
   * the first executable of its extensions must be class JDLBasedApplicationDeployment.
   */
  
  @Test
  public void testGetExecutableExtensions()
  {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    this.extensionmanager = new ExtensionManager( registry);
    String point_ID = "eu.geclipse.core.applicationDeployment"; //$NON-NLS-1$
    String configelement = Extensions.APPLICATION_DEPLOYMENT_ELEMENT; 
    String property = Extensions.APPLICATION_DEPLOYMENT_EXECUTABLE;
    List< Object > executables;
    executables = this.extensionmanager.getExecutableExtensions( point_ID, configelement, property );
    Assert.assertNotNull( executables );
    Assert.assertEquals("eu.geclipse.glite.deployment.JDLBasedApplicationDeployment",executables.get( 0 ).getClass().getName()); //$NON-NLS-1$
  }
}
