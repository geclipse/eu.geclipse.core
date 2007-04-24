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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import eu.geclipse.core.ISolution;

/**
 * Concrete implementation of an {@link UISolution} that opens a
 * preference page in order to point the user to the place where
 * some erroneous settings may cause a problem.
 */
public class PreferenceSolution extends UISolution {
  
  /**
   * ID of the network preference page.
   */
  public static final String NETWORK_PREFERENCE_PAGE
    = "eu.geclipse.ui.internal.preference.NetworkPreferencePage"; //$NON-NLS-1$
  
  /**
   * The id of the preference page that should be opened.
   */
  private String preferencePageId;
  
  /**
   * Construct a new <code>PreferenceSolution</code> for the specified
   * preference page. The parameters for this solution are taken from
   * the slave solution.
   * 
   * @param slave The slave solution that should be wrapped by this
   * solution.
   * @param shell The {@link Shell} used for the solution.
   * @param preferencePageId The ID of the preference page that
   * should be opened.
   */
  public PreferenceSolution( final ISolution slave,
                             final Shell shell,
                             final String preferencePageId ) {
    this( slave.getID(), slave.getText(), shell, preferencePageId );
  }

  /**
   * Construct a new <code>PreferenceSolution</code> for the specified
   * preference page.
   * 
   * @param id The unique ID of this solution.
   * @param text The solution's descriptive text.
   * @param shell The {@link Shell} used for the solution.
   * @param preferencePageId The ID of the preference page that
   * should be opened.
   */
  public PreferenceSolution( final int id,
                             final String text,
                             final Shell shell,
                             final String preferencePageId ) {
    super( id, text, shell );
    this.preferencePageId = preferencePageId;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.UISolution#isActive()
   */
  @Override
  public boolean isActive() {
    return true;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.UISolution#solve()
   */
  @Override
  public void solve() {
    PreferenceDialog dialog
      = PreferencesUtil.createPreferenceDialogOn( getShell(),
                                                  this.preferencePageId,
                                                  null,
                                                  null );
    dialog.open();
  }
  
}
