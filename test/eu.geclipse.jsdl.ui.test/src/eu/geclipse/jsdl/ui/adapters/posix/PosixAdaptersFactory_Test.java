/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.adapters.posix;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Event;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


public class PosixAdaptersFactory_Test {

  private static PosixAdaptersFactory factory;
  private static INotifyChangedListener listener;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    factory = new PosixAdaptersFactory();
    listener = new INotifyChangedListener(){

      public void notifyChanged( Notification arg0 ) {
        //do nothing
      }
      
    };
  }

  @Test
  public void testPosixAdaptersFactory() {
    Assert.assertNotNull( factory );
    
  }

  @Test
  public void testGetRootAdapterFactory() {
    Assert.assertNull( factory.getRootAdapterFactory() );
  }

  @Test
  public void testSetParentAdapterFactory() {
    //the tested method does nothing
    factory.setParentAdapterFactory( null );
  }

  @Test
  public void testAddListener() {
    INotifyChangedListener listener = new INotifyChangedListener(){

      public void notifyChanged( Notification arg0 ) {
        //do nothing
      }
      
    };
    factory.addListener( listener );
    factory.removeListener( listener );
  }

  @Test
  public void testFireNotifyChanged() {
    factory.addListener( listener );
    factory.fireNotifyChanged( null );
    factory.removeListener( listener );
  }

  @Test
  public void testRemoveListener() {
    factory.addListener( listener );
    factory.removeListener( listener );
  }

  @Test
  public void testDispose() {
    //the tested method does nothing
    factory.dispose();
  }
}
