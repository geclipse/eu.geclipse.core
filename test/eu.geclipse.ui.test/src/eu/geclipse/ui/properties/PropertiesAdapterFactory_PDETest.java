/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 * This test check how class eu.geclipse.ui.properties.PropertiesAdapterFactory implements interface IPropertySource
 */
public class PropertiesAdapterFactory_PDETest extends TestCase {
  private URI uri;  

  /**
   * @param name
   */
  public PropertiesAdapterFactory_PDETest( final String name ) {
    super( name );
    try {
      this.uri = new URI( "ftp://hydra.gup.uni-linz.ac.at:2811/home/local/agrid/geclipse/" ); //$NON-NLS-1$
    } catch( URISyntaxException exception ) {
      fail( exception.getLocalizedMessage() );
    }    
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    super.setUp(); 
  }
  
  private IPropertySource getPropertySource() {
    IPropertySource source = null;
    Object adapterObject = Platform.getAdapterManager().getAdapter( this.uri, IPropertySource.class );
    
    assertTrue( adapterObject != null );
    assertTrue( adapterObject instanceof IPropertySource );
    
    if( adapterObject instanceof IPropertySource ) {
      source = ( IPropertySource ) adapterObject;
    }    
    return source;
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link IPropertySource#getEditableValue()}.
   */
  public void testGetEditableValue() {
    IPropertySource propertySource = getPropertySource();
    
    if( propertySource != null ) {      
      assertEquals( propertySource.getEditableValue(), this.uri );
    }
  }

  /**
   * Test method for {@link IPropertySource#getPropertyDescriptors()}.
   */
  public void testGetPropertyDescriptors() {    
    IPropertySource propertySource = getPropertySource();
    
    if( propertySource != null ) {
      IPropertyDescriptor[] descriptors = propertySource.getPropertyDescriptors();
      assertNotNull( descriptors );
      assertTrue( descriptors.length > 0 );
    }
  }

  /**
   * Test method for {@link IPropertySource#getPropertyValue(java.lang.Object)}.
   */
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
    
    assertTrue( valuesList.contains( this.uri.getHost() ) );
    assertTrue( valuesList.contains( this.uri.getPath() ) );    
    assertTrue( valuesList.contains( this.uri.getScheme() ) );
  }
}
