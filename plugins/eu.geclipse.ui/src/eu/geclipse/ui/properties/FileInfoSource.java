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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;


/**
 * Property source for IFileInfo
 */
public class FileInfoSource extends AbstractPropertySource<IFileInfo> {
  static private List<IProperty<IFileInfo>> staticProperties;
  static private List<Attribute> attributes;
  
  static protected class Attribute {
    final int efsId;
    final String name;
    
    Attribute( final int id, final String name ) {
      super();
      this.efsId = id;
      this.name = name;
    }    
  }  

  /**
   * @param sourceObject
   */
  public FileInfoSource( final IFileInfo sourceObject ) {
    super( sourceObject );    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getPropertySourceClass()
   */
  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return FileInfoSource.class;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.properties.AbstractPropertySource#getStaticDescriptors()
   */
  @Override
  protected List<IProperty<IFileInfo>> getStaticProperties()
  {
    if( staticProperties == null ) {
      staticProperties = createProperties();
    }
    return staticProperties;
  }
  
  static private List<IProperty<IFileInfo>> createProperties() {
    List<IProperty<IFileInfo>> propertiesList = new ArrayList<IProperty<IFileInfo>>();
    propertiesList.add( createName() );
    propertiesList.add( createLength() );
    propertiesList.add( createLastModified() );
    propertiesList.add( createAttributes() );
    propertiesList.add( createLinkTarget() );
    return propertiesList;
  }
  
  static protected List<Attribute> getAttributes() {
    if( attributes == null ) {
      attributes = Arrays.asList( new Attribute( EFS.ATTRIBUTE_EXECUTABLE, Messages.getString("FileInfoSource.attrExecutable") ), //$NON-NLS-1$
                                  new Attribute( EFS.ATTRIBUTE_SYMLINK, Messages.getString("FileInfoSource.attrLink") ), //$NON-NLS-1$
                                  new Attribute( EFS.ATTRIBUTE_HIDDEN, Messages.getString("FileInfoSource.attrHidden") ), //$NON-NLS-1$
                                  new Attribute( EFS.ATTRIBUTE_ARCHIVE, Messages.getString("FileInfoSource.attrArchive") ), //$NON-NLS-1$
                                  new Attribute( EFS.ATTRIBUTE_READ_ONLY, Messages.getString("FileInfoSource.attrReadOnly") ) //$NON-NLS-1$
      );
    }
    return attributes;
  }  
  
  static private IProperty<IFileInfo> createName() {
    return new AbstractProperty<IFileInfo>( Messages.getString("FileInfoSource.propName"), null )  //$NON-NLS-1$
    {
      @Override
      public Object getValue( final IFileInfo fileInfo )
      {
        return fileInfo.getName();
      }
    };
  }

  static private IProperty<IFileInfo> createLength() {
    return new AbstractProperty<IFileInfo>( Messages.getString("FileInfoSource.propLength"), null ) //$NON-NLS-1$
    {
      @Override
      public Object getValue( final IFileInfo fileInfo )
      {
        String valueString = fileInfo.isDirectory() ? Messages.getString("FileInfoSource.directory") : getBytesFormattedString( fileInfo.getLength() );          //$NON-NLS-1$
        
        return valueString;
      }
    };
  }
  
  static private IProperty<IFileInfo> createLastModified() {
    return new AbstractProperty<IFileInfo>( Messages.getString("FileInfoSource.propLastModified"), null ) //$NON-NLS-1$
    {
      @Override
      public Object getValue( final IFileInfo fileInfo )
      {
        String valueString = null;
        
        if( fileInfo.getLastModified() == EFS.NONE ) {
          valueString = Messages.getString("FileInfoSource.unknown"); //$NON-NLS-1$
        } else {
          Calendar calendar = Calendar.getInstance();
          calendar.setTimeInMillis( fileInfo.getLastModified() );
          valueString = DateFormat.getDateTimeInstance().format( calendar.getTime() );
        }
      
        return valueString;
      }
    };
  }
  
  static private IProperty<IFileInfo> createAttributes() {
    return new AbstractProperty<IFileInfo>( Messages.getString("FileInfoSource.propAttributes"), null, false ) //$NON-NLS-1$
    {
      @Override
      public Object getValue( final IFileInfo fileInfo )
      {
        StringBuilder valueString = new StringBuilder();
        
        for( Attribute attribute : getAttributes() ) {
          if( fileInfo.getAttribute( attribute.efsId ) ) {
            if( valueString.length() > 0 ) {
              valueString.append( ", " ); //$NON-NLS-1$
            }
            valueString.append( attribute.name );
          }
        }
        
        return valueString.length() > 0 ? valueString : null;
      }
    };
  }
  
  static private IProperty<IFileInfo> createLinkTarget() {
    return new AbstractProperty<IFileInfo>( Messages.getString("FileInfoSource.propLinkTarget"), null, false ) //$NON-NLS-1$
    {
      @Override
      public Object getValue( final IFileInfo fileInfo )
      {
        return fileInfo.getStringAttribute( EFS.ATTRIBUTE_LINK_TARGET );
      }
    };
  } 
}
