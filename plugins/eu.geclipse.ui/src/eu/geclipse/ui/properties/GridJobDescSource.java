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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Properties for {@link IGridJobDescription}
 */
public class GridJobDescSource extends AbstractPropertySource<IGridJobDescription> {
  static private List<IProperty<IGridJobDescription>> staticProperties;

  /**
   * @param source Object, for which properties will be shown
   */
  public GridJobDescSource( final IGridJobDescription source ) {
    super( source );
  }
  
  @Override
  protected List<IProperty<IGridJobDescription>> getStaticProperties() {
    if( staticProperties == null ) {
      staticProperties = createProperties();    
    }
    
    return staticProperties;
  }
  
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridJobDescSource.class;
  }
  
  static private List<IProperty<IGridJobDescription>> createProperties() {
    List<IProperty<IGridJobDescription>> propertiesList = new ArrayList<IProperty<IGridJobDescription>>();
    propertiesList.add( createDescription() );
    propertiesList.add( createPropertyExecutable() );
    propertiesList.add( createPropertyExecutableArg() );
    propertiesList.add( createPropertyInput() ); 
    propertiesList.add( createPropertyOutput() );
    propertiesList.add( createPropertyError() );
    propertiesList.add( createPropertyStdOutputUri() );
    propertiesList.add( createPropertyStdInputUri() );
    propertiesList.add( createPropertyStdErrorUri() );
    return propertiesList;
  }
  
  static private IProperty<IGridJobDescription> createDescription() {
    return new MultilineProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyDescription" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryApplication" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription source )
      {
        return source.getDescription();
      }
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyExecutable() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyExecutable" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryApplication" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription source )
      {
        return source.getExecutable();
      }
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyExecutableArg() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyExecArgs" ), //$NON-NLS-1$ 
                                                      Messages.getString( "GridJobDescSource.categoryApplication" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridJobDescription jobDescription )
      {
        StringBuilder stringBuilder = new StringBuilder();
        if( jobDescription.getExecutableArguments() != null ) {
          List<String> argList = jobDescription.getExecutableArguments();
          for( String argString : argList ) {
            if( stringBuilder.length() > 0 ) {
              stringBuilder.append( ", " ); //$NON-NLS-1$
            }
            stringBuilder.append( argString );
          }
        }
        return stringBuilder.toString();
      }
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyInput() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyInput" ), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            return jobDescription.getStdInputFileName();
          }      
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyOutput() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString( "GridJobDescSource.propertyOutput" ), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            return jobDescription.getStdOutputFileName();
          }      
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyError() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString("GridJobDescSource.propertyStdErrFilename"), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            return jobDescription.getStdErrorFileName();
          }      
    };
  }    

  static private IProperty<IGridJobDescription> createPropertyStdOutputUri() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString("GridJobDescSource.propertyStdOutputUri"), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            String value = null;
            
            try {
              URI stdOutputUri = jobDescription.getStdOutputUri();
              
              if( stdOutputUri != null ) {
                value = stdOutputUri.toString();
              }              
            } catch( ProblemException exception ) {
              // ignore exceptions
            }
            
            return value;
          }      
    };
  }  
  
  static private IProperty<IGridJobDescription> createPropertyStdInputUri() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString("GridJobDescSource.propertyStdInputUri"), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            String value = null;
            
            try {
              URI stdInputUri = jobDescription.getStdInputUri();
              
              if( stdInputUri != null ) {
                value = stdInputUri.toString();
              }              
            } catch( ProblemException exception ) {
              // ignore exceptions
            }
            
            return value;
          }      
    };
  }
  
  static private IProperty<IGridJobDescription> createPropertyStdErrorUri() {
    return new AbstractProperty<IGridJobDescription>( Messages.getString("GridJobDescSource.propertyStdErrUri"), //$NON-NLS-1$
        Messages.getString( "GridJobDescSource.categoryApplication" ), false ) { //$NON-NLS-1$

          @Override
          public Object getValue( final IGridJobDescription jobDescription )
          {
            String value = null;
            
            try {
              URI stdErrorUri = jobDescription.getStdErrorUri();
              
              if( stdErrorUri != null ) {
                value = stdErrorUri.toString();
              }              
            } catch( ProblemException exception ) {
              // ignore exceptions
            }
            
            return value;
          }      
    };
  }    
  
}
