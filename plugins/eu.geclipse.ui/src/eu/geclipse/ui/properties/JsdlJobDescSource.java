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
import java.util.Iterator;
import java.util.List;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import eu.geclipse.core.model.impl.JSDLJobDescription;
import eu.geclipse.jsdl.RangeValueType;


/**
 * Properties for {@link JSDLJobDescription}
 */
public class JsdlJobDescSource
  extends AbstractPropertySource<JSDLJobDescription>
{

  static private IPropertyDescriptor[] staticDescriptors;

  /**
   * @param sourceObject - job description object, for which properties will be
   *          displayed
   */
  public JsdlJobDescSource( final JSDLJobDescription sourceObject ) {
    super( sourceObject );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return JsdlJobDescSource.class;
  }

  @Override
  protected IPropertyDescriptor[] getStaticDescriptors()
  {
    if( staticDescriptors == null ) {
      staticDescriptors = AbstractPropertySource.createDescriptors( createProperties(),
                                                                    getPropertySourceClass() );
    }
    return staticDescriptors;
  }

  static private List<IProperty<JSDLJobDescription>> createProperties() {
    List<IProperty<JSDLJobDescription>> propertiesList = new ArrayList<IProperty<JSDLJobDescription>>( 10 );
    propertiesList.add( createCpuArchitecture() );
    propertiesList.add( createFSMountPoint() );
    propertiesList.add( createFSDiskSpace() );
    propertiesList.add( createFSFileSystemType() );
    propertiesList.add( createOSType() );
    propertiesList.add( createOSVersion() );
    return propertiesList;
  }

  static private IProperty<JSDLJobDescription> createCpuArchitecture() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.propertyCpuArchitecture" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirements" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getCpuArchitectureName();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSMountPoint() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.propertyMountPoint" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirementsFS" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getFilesystemMountPoint();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSDiskSpace() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.propertyDiskSpace" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirementsFS" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription sourceObject )
      {
        StringBuilder stringBuilder = new StringBuilder();
        RangeValueType diskSpaceRange = sourceObject.getFilesystemDiskSpace();
        if( diskSpaceRange != null ) {
          if( diskSpaceRange.getLowerBoundedRange() != null ) {
            stringBuilder.append( Messages.getString( "JsdlJobDescSource.propertyDiskSpaceMin" ) ); //$NON-NLS-1$
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getLowerBoundedRange()
              .getValue() ) );
          }
          if( diskSpaceRange.getUpperBoundedRange() != null ) {
            if( stringBuilder.length() > 0 ) {
              stringBuilder.append( " - " ); //$NON-NLS-1$
            }
            stringBuilder.append( Messages.getString( "JsdlJobDescSource.propertyDiskSpaceMax" ) ); //$NON-NLS-1$
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getUpperBoundedRange()
              .getValue() ) );
          }
        }
        return stringBuilder.toString();
      }

      private String getBytesFormattedString( final double value ) {
        String formattedString;
        double smallerValue = value;
        ArrayList<String> suffixList = new ArrayList<String>( 5 );
        suffixList.add( "B" ); //$NON-NLS-1$
        suffixList.add( "kB" ); //$NON-NLS-1$
        suffixList.add( "MB" ); //$NON-NLS-1$
        suffixList.add( "GB" ); //$NON-NLS-1$
        suffixList.add( "TB" ); //$NON-NLS-1$
        Iterator<String> iterator = suffixList.iterator();
        String suffixString = iterator.next();
        while( iterator.hasNext() && smallerValue > 1024 ) {
          suffixString = iterator.next();
          smallerValue /= 1024;
        }
        if( ( ( int )smallerValue ) == smallerValue ) {
          formattedString = String.format( "%d", Integer.valueOf( ( int )smallerValue ) ); //$NON-NLS-1$
        } else {
          formattedString = String.format( "%.2f", Double.valueOf( smallerValue ) ); //$NON-NLS-1$
        }
        return formattedString + suffixString;
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSFileSystemType() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.propertyFileSystem" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirementsFS" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getFilesystemType();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createOSType() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.properyOSType" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirementsOS" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getOSTypeName();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createOSVersion() {
    return new AbstractProperty<JSDLJobDescription>( Messages.getString( "JsdlJobDescSource.properyOSVersion" ), //$NON-NLS-1$
                                                     Messages.getString( "JsdlJobDescSource.sectionRequirementsOS" ) ) { //$NON-NLS-1$

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getOSVersion();
      }
    };
  }
}
