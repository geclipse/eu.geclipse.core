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
package eu.geclipse.batch.ui.internal.adapters;

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

import eu.geclipse.batch.model.qdl.util.QdlAdapterFactory;


/**
 * @author nickl
 *
 */
public class QdlAdaptersFactory extends QdlAdapterFactory
  implements ComposeableAdapterFactory, IChangeNotifier, IDisposable
{
  
  protected Collection <Object> supportedTypes = new ArrayList<Object>();
  
  private List< INotifyChangedListener > listeners = new ArrayList< INotifyChangedListener >();
  
  
  /**
   * Default Class Constructor.
   * 
   */
  public QdlAdaptersFactory() {
    this.supportedTypes.add(IEditingDomainItemProvider.class);
    this.supportedTypes.add(IStructuredItemContentProvider.class);
    this.supportedTypes.add(ITreeItemContentProvider.class);
    this.supportedTypes.add(IItemLabelProvider.class);
    this.supportedTypes.add(IItemPropertySource.class); 
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.ComposeableAdapterFactory#getRootAdapterFactory()
   */
  public ComposeableAdapterFactory getRootAdapterFactory() {
    // Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.ComposeableAdapterFactory#setParentAdapterFactory(
   * org.eclipse.emf.edit.provider.ComposedAdapterFactory)
   */
  public void setParentAdapterFactory( final ComposedAdapterFactory parentAdapterFactory )
  {
    // Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.IChangeNotifier#addListener(
   * org.eclipse.emf.edit.provider.INotifyChangedListener)
   */
  public void addListener( final INotifyChangedListener notifyChangedListener ) {
    if ( ! this.listeners.contains( notifyChangedListener ) ) {
      this.listeners.add( notifyChangedListener );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.IChangeNotifier#fireNotifyChanged(org.eclipse.emf.common.notify.Notification)
   */
  public void fireNotifyChanged( final Notification notification ) {
    for ( INotifyChangedListener l : this.listeners ) {
      l.notifyChanged( notification );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.IChangeNotifier#removeListener(
   * org.eclipse.emf.edit.provider.INotifyChangedListener)
   */
  public void removeListener( final INotifyChangedListener notifyChangedListener ) {
    this.listeners.remove( notifyChangedListener );
  }

  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.IDisposable#dispose()
   */
  public void dispose() {
    // Auto-generated method stub
  }
  
} // end QueueAdaptersFactory
