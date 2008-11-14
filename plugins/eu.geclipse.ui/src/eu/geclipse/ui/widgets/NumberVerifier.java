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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.widgets;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Verifier for Text widgets. Allows to only enter numbers. Usage:
 * textWidget.addListener( SWT.Verify, new NumberVerifier() );
 */
public class NumberVerifier implements Listener {
  private boolean allowNegative;

  public NumberVerifier() {
    this.allowNegative = false;
  }

  public NumberVerifier( final boolean allowNegative ) {
    this.allowNegative = allowNegative;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
   */
  public void handleEvent( final Event event ) {
    String eventText = event.text;
    for( int ii = 0; ii < eventText.length(); ++ii ) {
      if( !Character.isDigit( eventText.charAt( ii ) )
          && !(allowNegative && eventText.charAt( ii ) == '-'
               && ii == 0 && event.start == 0 ) ) {
        event.doit = false;
        return;
      }
    }
  }
}
