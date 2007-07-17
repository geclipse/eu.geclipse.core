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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.jsdl.jsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import eu.geclipse.jsdl.model.util.JsdlAdapterFactory;

/**
 * @author nickl
 *
 */
public class JsdlAdaptersFactory extends JsdlAdapterFactory implements ComposeableAdapterFactory,
                                                                IChangeNotifier,
                                                                IDisposable  {
  
  protected Collection <Object> supportedTypes = new ArrayList<Object>();
  
  private List< INotifyChangedListener > listeners
    = new ArrayList< INotifyChangedListener >();
    
  /**
   * JsdlAdaptersFactory Class Constructor.
   */
  public JsdlAdaptersFactory(){
    this.supportedTypes.add(IEditingDomainItemProvider.class);
    this.supportedTypes.add(IStructuredItemContentProvider.class);
    this.supportedTypes.add(ITreeItemContentProvider.class);
    this.supportedTypes.add(IItemLabelProvider.class);
    this.supportedTypes.add(IItemPropertySource.class);  
    
  }
  
  
  public ComposeableAdapterFactory getRootAdapterFactory() {
    //  Auto-generated method stub
    return null;
  }

  public void setParentAdapterFactory( final ComposedAdapterFactory arg0 ) {
    //  Auto-generated method stub
    
  }

  public void addListener( final INotifyChangedListener l ) {
    if ( ! this.listeners.contains( l ) ) {
      this.listeners.add( l );
    }
  }

  public void fireNotifyChanged( final Notification n ) {
    for ( INotifyChangedListener l : this.listeners ) {
      l.notifyChanged( n );
    }
  }

  public void removeListener(final INotifyChangedListener l ) {
    this.listeners.remove( l );
  }

  public void dispose() {
    //  Auto-generated method stub
    
  }
  
}
