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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.widgets;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Verifier for Text widgets. Allows to only enter float numbers. Usage:
 * textWidget.addListener( SWT.Verify, new NumberVerifier() );
 */
public class DoubleNumberVerifier implements Listener {
  private boolean allowNegative;

  public DoubleNumberVerifier() {
    this.allowNegative = false;
  }

  public DoubleNumberVerifier( final boolean allowNegative ) {
    this.allowNegative = allowNegative;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
   */
  public void handleEvent( final Event event ) {
    String eventText = event.text;
    for( int ii = 0; ii < eventText.length(); ++ii ) {
      if( !Character.isDigit( eventText.charAt( ii ) )
          && !(eventText.charAt( ii ) == '.')
          && !(allowNegative && eventText.charAt( ii ) == '-'
               && ii == 0 && event.start == 0 ) ) {
        event.doit = false;
        return;
      }
    }
  }
}
