/******************************************************************************
 * Copyright (c) 2007 - 2008 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *                  
 *****************************************************************************/
package eu.geclipse.servicejob.ui.properties;

import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import eu.geclipse.core.model.IServiceJob;

/**
 * Class responsible for handling service job's ({@link IServiceJob}) properties.
 */
public class ServiceJobResultProperties implements IPropertySource {

  private IServiceJob serviceJob;

  /**
   * Creates instance of this class for given source object.
   * 
   * @param serviceJob Service job object for which properties should be shown.
   */
  public ServiceJobResultProperties( final IServiceJob serviceJob ) {
    this.serviceJob = serviceJob;
  }

  public Object getEditableValue() {
    return null;
  }

  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] result = new IPropertyDescriptor[ this.serviceJob.getProperties()
      .size() ];
    int i = 0;
    Map<String, String> desc = this.serviceJob.getProperties();
    for( String key : desc.keySet() ) {
      result[ i ] = new TextPropertyDescriptor( key, key );
      i++;
    }
    return result;
  }

  public Object getPropertyValue( final Object id ) {
    return getPropertyForServiceJob( id );
  }

  private Object getPropertyForServiceJob( final Object id ) {
    Object result = "N/A"; //$NON-NLS-1$
    Map<String, String> desc = this.serviceJob.getProperties();
    result = desc.get( id );
    return result;
  }

  public boolean isPropertySet( final Object id ) {
    return true;
  }

  public void resetPropertyValue( final Object id ) {
    // empty implementation
  }

  public void setPropertyValue( final Object id, final Object value ) {
    // empty implementation
  }
}
