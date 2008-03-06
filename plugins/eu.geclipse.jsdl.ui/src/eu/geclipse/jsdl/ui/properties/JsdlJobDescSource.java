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

package eu.geclipse.jsdl.ui.properties;

import java.util.ArrayList;
import java.util.List;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.ui.properties.AbstractProperty;
import eu.geclipse.ui.properties.AbstractPropertySource;
import eu.geclipse.ui.properties.IProperty;


/**
 * Properties for {@link JSDLJobDescription}
 */
public class JsdlJobDescSource
  extends AbstractPropertySource<JSDLJobDescription>
{  
  static private List<IProperty<JSDLJobDescription>> staticProperties;

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

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getStaticDescriptors()
   */
  @Override
  protected List<IProperty<JSDLJobDescription>> getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
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
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_CpuArchitecture, 
                                                     Messages.JsdlJobDescSource_Requirements, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getCpuArchitectureName();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSMountPoint() {
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_MountPoint, 
                                                     Messages.JsdlJobDescSource_RequirementsFS, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription sourceObject )
      {
        return sourceObject.getFilesystemMountPoint();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSDiskSpace() {
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_DiskSpace, 
        Messages.JsdlJobDescSource_RequirementsFS, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription sourceObject )
      {
        StringBuilder stringBuilder = new StringBuilder();
        RangeValueType diskSpaceRange = sourceObject.getFilesystemDiskSpace();
        if( diskSpaceRange != null ) {
          if( diskSpaceRange.getLowerBound() != null ) {
            stringBuilder.append( Messages.JsdlJobDescSource_Min ); 
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getLowerBound()
              .getValue() ) );
          }
          if( diskSpaceRange.getUpperBound() != null ) {
            if( stringBuilder.length() > 0 ) {
              stringBuilder.append( " - " );  //$NON-NLS-1$
            }
            stringBuilder.append( Messages.JsdlJobDescSource_Max ); 
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getUpperBound()
              .getValue() ) );
          }
        }
        return stringBuilder.length() > 0 ?  stringBuilder.toString() : null;
      }
    };
  }

  static private IProperty<JSDLJobDescription> createFSFileSystemType() {
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_FileSystem, 
        Messages.JsdlJobDescSource_RequirementsFS, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getFilesystemType();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createOSType() {
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_OS, 
                                                     Messages.JsdlJobDescSource_RequirementsOS, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getOSTypeName();
      }
    };
  }

  static private IProperty<JSDLJobDescription> createOSVersion() {
    return new AbstractProperty<JSDLJobDescription>( Messages.JsdlJobDescSource_OSVersion, 
                                                     Messages.JsdlJobDescSource_RequirementsOS, false ) { 

      @Override
      public Object getValue( final JSDLJobDescription source )
      {
        return source.getOSVersion();
      }
    };
  }

}
