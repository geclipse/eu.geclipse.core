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
 *     IT Research Division, NEC Laboratories Europe, NEC Europe Ltd. (http://www.it.neclab.eu)
 *     - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preferences for the SLA editors
 */
public class PreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  /**
   * constructor
   */
  public PreferencePage() {
    super( GRID );
    setDescription( Messages.getString( "PreferencePage.Description" ) ); //$NON-NLS-1$
  }

  @Override
  protected void createFieldEditors() {
    // no fields to enter!! this is a blank page
  }

  public void init( IWorkbench workbench ) {
    // 
  }
}
