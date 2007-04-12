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
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.impl.JSDLJobDescription;


/**
 * Properties for {@link IGridJobDescription}
 */
public class GridJobDescSource extends AbstractPropertySource<IGridJobDescription> {
  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param source Object, for which properties will be shown
   */
  public GridJobDescSource( final IGridJobDescription source ) {
    super( source );
    
    if( source instanceof JSDLJobDescription ) {
      addChildSource( new JsdlJobDescSource( (JSDLJobDescription)source ) );
    }
  }
  
  @Override
  public IPropertyDescriptor[] getStaticDescriptors() {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(), getPropertySourceClass() );    
    }
    
    return staticDescriptors;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridJobDescSource.class;
  }
  
  static private List<IProperty<IGridJobDescription>> createProperties() {
    List<IProperty<IGridJobDescription>> propertiesList = new ArrayList<IProperty<IGridJobDescription>>( 3 );
    propertiesList.add( createDescription() );
    propertiesList.add( createPropertyExecutable() );
    propertiesList.add( createPropertyExecutableArg() );
    return propertiesList;
  }
  
  static private IProperty<IGridJobDescription> createDescription() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyDescription" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryDescription" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription source )
      {
        return source.getDescription();
      }
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyExecutable() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyExecutable" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryDescription" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription source )
      {
        return source.getExecutable();
      }
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyExecutableArg() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyExecArgs" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryDescription" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription jobDescription )
      {
        String argString = null;
        if( jobDescription.getExecutableArguments() != null ) {
          argString = jobDescription.getExecutableArguments().toString();
        }
        return argString;
      }
    };
  }
  
}
