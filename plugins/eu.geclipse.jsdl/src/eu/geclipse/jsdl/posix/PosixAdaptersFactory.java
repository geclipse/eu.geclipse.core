/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
  *
  *****************************************************************************/
package eu.geclipse.jsdl.posix;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import eu.geclipse.jsdl.model.posix.util.PosixAdapterFactory;


/**
 * @author nickl
 *
 */
public class PosixAdaptersFactory extends PosixAdapterFactory implements ComposeableAdapterFactory,
                                                                IChangeNotifier,
                                                                   IDisposable {

  
  protected Collection <Object> supportedTypes = new ArrayList<Object>();
  
  public PosixAdaptersFactory() {
    supportedTypes.add(IEditingDomainItemProvider.class);
    supportedTypes.add(IStructuredItemContentProvider.class);
    supportedTypes.add(ITreeItemContentProvider.class);
    supportedTypes.add(IItemLabelProvider.class);
    supportedTypes.add(IItemPropertySource.class);  
  }
  
  
  public ComposeableAdapterFactory getRootAdapterFactory() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setParentAdapterFactory( final ComposedAdapterFactory arg0 ) {
    // TODO Auto-generated method stub
    
  }

  public void addListener( final INotifyChangedListener arg0 ) {
    // TODO Auto-generated method stub
    
  }

  public void fireNotifyChanged( final Notification arg0 ) {
    // TODO Auto-generated method stub
    
  }

  public void removeListener( final INotifyChangedListener arg0 ) {
    // TODO Auto-generated method stub
    
  }

  public void dispose() {
    // TODO Auto-generated method stub
    
  }
}
