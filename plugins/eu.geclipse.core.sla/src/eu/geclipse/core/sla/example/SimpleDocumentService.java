/* Copyright (c) 2008 g-Eclipse consortium
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
package eu.geclipse.core.sla.example;

import org.eclipse.core.runtime.Preferences;

import eu.geclipse.core.sla.ISLADocumentService;
import eu.geclipse.core.sla.preferences.PreferenceConstants;

/**
 * @author korn
 */
public class SimpleDocumentService implements ISLADocumentService {

  public String addConsumerData( final String sltDocument ) {
    // include the preferences
    Preferences prefs = eu.geclipse.core.sla.Activator.getDefault()
      .getPluginPreferences();
    String value = "<Consumer>" + //$NON-NLS-1$
                   "\n  <ShortName>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cName )
                   + "</ShortName>" + //$NON-NLS-1$ 
                   "\n  <FullName>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cFullName )
                   + "</FullName>" + //$NON-NLS-1$
                   "\n  <Department>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cDepartment )
                   + "</Department>" + //$NON-NLS-1$
                   "\n  <Street>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cStreet )
                   + "</Street>" + //$NON-NLS-1$
                   "\n  <BuildingNumber>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cBuildingNb )
                   + "</BuildingNumber>" + //$NON-NLS-1$ 
                   "\n  <City>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cCity )
                   + "</City>" + //$NON-NLS-1$ 
                   "\n  <PostalCode>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cPostalZone )
                   + "</PostalCode>" + //$NON-NLS-1$
                   "\n  <Country>" //$NON-NLS-1$
                   + prefs.getString( PreferenceConstants.cCountry )
                   + "</Country>" + //$NON-NLS-1$ 
                   "\n</Consumer>"; //$NON-NLS-1$
    return sltDocument + value;
  }
}
