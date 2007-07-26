/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Markus Knauer - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.widgets;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testclass for the <code>NumberVerifier</code> method.
 * It was written to ensure compatibility after refactoring.
 */
public class NumberVerifier_Test {


  /**
   * Test method for {@link eu.geclipse.ui.widgets.NumberVerifier#handleEvent(org.eclipse.swt.widgets.Event)}.
   */
  @Test
  public void testHandleEvent()
  {
    Listener numberVerifier = new NumberVerifier();

    Assert.assertTrue( this.createEvent( "" ).doit ); //$NON-NLS-1$

    Event event = createEvent( "1234567" ); //$NON-NLS-1$
    numberVerifier.handleEvent( event );
    Assert.assertTrue( event.doit );

    event = createEvent( "-1234567" ); //$NON-NLS-1$
    numberVerifier.handleEvent( event );
    Assert.assertFalse( event.doit );
    
    event = createEvent( "ABC1234" ); //$NON-NLS-1$
    numberVerifier.handleEvent( event );
    Assert.assertFalse( event.doit );
  }
  
  // helping methods
  //////////////////
  
  private Event createEvent( final String text ) {
    Event result = new Event();
    result.doit = true;
    result.text = text;
    return result;
  }
}
