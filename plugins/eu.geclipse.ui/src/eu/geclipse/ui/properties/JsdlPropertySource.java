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
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.impl.JSDLJobDescription;
import eu.geclipse.jsdl.RangeValueType;


/**
 * Property source for this properties of {@link JSDLJobDescription}, which are not in {@link IGridJobDescription}
 *
 */
public class JsdlPropertySource extends AbstractPropertySource {
  protected final JSDLJobDescription jobDescription; 

  /**
   * @param jobDescription - description for which properties will be displayed
   */
  public JsdlPropertySource( final JSDLJobDescription jobDescription ) {
    super();
    this.jobDescription = jobDescription;
  }

  @Override
  protected IProperty[] createProperties()
  {
    return new IProperty[] {
      createCpuArchitecture(),
      createFSMountPoint(),
      createFSDiskSpace(),
      createFSFileSystemType(),
      createOSType(),
      createOSVersion()
    };
  }
  
  private IProperty createCpuArchitecture() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.propertyCpuArchitecture"), //$NON-NLS-1$ 
                                 Messages.getString("JsdlPropertySource.sectionRequirements") ) { //$NON-NLS-1$

      /* (non-Javadoc)
       * @see eu.geclipse.ui.properties.IProperty#getValue()
       */
      public String getValue() {
        return JsdlPropertySource.this.jobDescription.getCpuArchitectureName();
      }
      
    };
  }
  
  IProperty createFSMountPoint() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.propertyMountPoint"), //$NON-NLS-1$
                                 Messages.getString("JsdlPropertySource.sectionRequirementsFS") ) { //$NON-NLS-1$

      public String getValue() {            
        return JsdlPropertySource.this.jobDescription.getFilesystemMountPoint();
      }          
    };
  }
  
  IProperty createFSDiskSpace() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.propertyDiskSpace"), //$NON-NLS-1$
                                 Messages.getString("JsdlPropertySource.sectionRequirementsFS") ) { //$NON-NLS-1$

      public String getValue() {
        StringBuilder stringBuilder = new StringBuilder();
        RangeValueType diskSpaceRange = JsdlPropertySource.this.jobDescription.getFilesystemDiskSpace();
        
        if( diskSpaceRange != null ) 
        {
          if( diskSpaceRange.getLowerBoundedRange() != null ) 
          {
            stringBuilder.append( Messages.getString("JsdlPropertySource.propertyDiskSpaceMin") ); //$NON-NLS-1$
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getLowerBoundedRange().getValue() ) );            
          }
          
          if( diskSpaceRange.getUpperBoundedRange() != null ) {
            if( stringBuilder.length() > 0 ){
              stringBuilder.append( " - " ); //$NON-NLS-1$
            }            
            stringBuilder.append( Messages.getString("JsdlPropertySource.propertyDiskSpaceMax") ); //$NON-NLS-1$
            stringBuilder.append( getBytesFormattedString( diskSpaceRange.getUpperBoundedRange().getValue() ) );            
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
        
        while( iterator.hasNext() && smallerValue > 1024 )
        {
          suffixString = iterator.next();
          smallerValue /= 1024;
        }
        
        if( ( (int)smallerValue ) == smallerValue ) {
          formattedString = String.format( "%d", Integer.valueOf( (int) smallerValue ) ); //$NON-NLS-1$
        } else {
          formattedString = String.format( "%.2f", Double.valueOf( smallerValue ) ); //$NON-NLS-1$
        }
        
        return formattedString + suffixString;
      }
    };
  }
  
  IProperty createFSFileSystemType() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.propertyFileSystem"), //$NON-NLS-1$
                                 Messages.getString("JsdlPropertySource.sectionRequirementsFS") ) { //$NON-NLS-1$
      public String getValue() {
        return JsdlPropertySource.this.jobDescription.getFilesystemType();
      }          
    };
  }
  
  IProperty createOSType() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.properyOSType"),    //$NON-NLS-1$ 
                                 Messages.getString("JsdlPropertySource.sectionRequirementsOS") ) { //$NON-NLS-1$
      public String getValue() {
        return JsdlPropertySource.this.jobDescription.getOSTypeName();
      }          
    };
  }
    
  IProperty createOSVersion() {
    return new AbstractProperty( Messages.getString("JsdlPropertySource.properyOSVersion"), //$NON-NLS-1$ 
                                 Messages.getString("JsdlPropertySource.sectionRequirementsOS") ) { //$NON-NLS-1$
      public String getValue() {
        return JsdlPropertySource.this.jobDescription.getOSVersion();
      }          
    };    
  }  
}

