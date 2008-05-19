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
package eu.geclipse.ui.properties;

import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 */
public class PropertiesAdapterFactory_PDETest {
  private URI uri;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    try {
      this.uri = new URI( "ftp://hydra.gup.uni-linz.ac.at:2811/home/local/agrid/geclipse/" ); //$NON-NLS-1$
    } catch( URISyntaxException exception ) {
      fail( exception.getLocalizedMessage() );
    }    
  }

  /**
   * Test method for {@link eu.geclipse.ui.properties.PropertiesAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)}.
   */
  @Test
  public void testGetAdapter() {
    Object adapter = Platform.getAdapterManager().getAdapter( this.uri, IPropertySource.class );
    Assert.assertNotNull( adapter );
    Assert.assertTrue( adapter instanceof IPropertySource );
  }
  
  private IPropertySource getPropertySource() {
    IPropertySource source = null;
    Object adapterObject = Platform.getAdapterManager().getAdapter( this.uri, IPropertySource.class );
    
    Assert.assertTrue( adapterObject instanceof IPropertySource );
    
    if( adapterObject instanceof IPropertySource ) {
      source = ( IPropertySource ) adapterObject;
    }    
    return source;
  }

  /**
   * Test method for {@link IPropertySource#getEditableValue()}.
   */
  @Test
  public void testGetEditableValue() {
    IPropertySource propertySource = getPropertySource();
    
    if( propertySource != null ) {      
      Assert.assertEquals( propertySource.getEditableValue(), this.uri );
    }
  }
  
  /**
   * Test method for {@link IPropertySource#getPropertyDescriptors()}.
   */
  @Test
  public void testGetPropertyDescriptors() {    
    IPropertySource propertySource = getPropertySource();
    
    if( propertySource != null ) {
      IPropertyDescriptor[] descriptors = propertySource.getPropertyDescriptors();
      Assert.assertNotNull( descriptors );
      Assert.assertTrue( descriptors.length > 0 );
    }
  }  
  
  /**
   * Test method for {@link IPropertySource#getPropertyValue(java.lang.Object)}.
   */
  @Test
  public void testGetPropertyValue() {
    IPropertySource propertySource = getPropertySource();
    List<String> valuesList = new ArrayList<String>();
    
    if( propertySource != null ) {
      IPropertyDescriptor[] descriptors = propertySource.getPropertyDescriptors();
      
      for( IPropertyDescriptor currentDescriptor : Arrays.asList( descriptors ) ) {
        Object valueObject = propertySource.getPropertyValue( currentDescriptor.getId() );
        
        if( valueObject instanceof String ) {
          valuesList.add( (String) valueObject );
        }
      }
    }
    
    Assert.assertTrue( valuesList.contains( this.uri.getHost() ) );
    Assert.assertTrue( valuesList.contains( this.uri.getPath() ) );    
    Assert.assertTrue( valuesList.contains( this.uri.getScheme() ) );
  }  
}
