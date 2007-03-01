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

package eu.geclipse.ui.cheatsheets;

import org.eclipse.help.ILiveHelpAction;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.ui.UIAuthTokenProvider;

/**
 * Action for opening the authentication token dialog from cheat sheets and
 * html help files.
 */
public class OpenAuthTokenDialogAction extends Action implements ILiveHelpAction {
  @Override
  public void run() {
    Display.getDefault().syncExec( new Runnable() {
      public void run() {
        UIAuthTokenProvider tokenProvider
          = new UIAuthTokenProvider( Display.getDefault().getActiveShell() );
        tokenProvider.showNewTokenWizard( null );
      }
    } );
  }

  /* (non-Javadoc)
   * @see org.eclipse.help.ILiveHelpAction#setInitializationString(java.lang.String)
   */
  public void setInitializationString( final String data ) {
    // not needed
  }
}
