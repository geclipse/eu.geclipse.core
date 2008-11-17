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

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import eu.geclipse.core.sla.preferences.PreferenceConstants;
import eu.geclipse.core.sla.ui.Activator;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class ProviderPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  /**
   * PreferencePage for SLA editor for the Provider
   */
  public ProviderPreferencePage() {
    super( GRID );
    Plugin nonUIPlugin = eu.geclipse.core.sla.Activator.getDefault();
    IPreferenceStore preferenceStore = new ScopedPreferenceStore( new InstanceScope(),
                                                                  nonUIPlugin.getBundle()
                                                                    .getSymbolicName() );
    setPreferenceStore( preferenceStore );
    setDescription( Messages.getString( "ProviderPreferencePage.Description" ) ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new StringFieldEditor( PreferenceConstants.pRegistryURI,
                                     Messages.getString( "ProviderPreferencePage.registryURI" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pName,
                                     Messages.getString( "ProviderPreferencePage.Name" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pFullName,
                                     Messages.getString( "ProviderPreferencePage.FullName" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pDepartment,
                                     Messages.getString( "ProviderPreferencePage.Department" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pStreet,
                                     Messages.getString( "ProviderPreferencePage.Street" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pBuildingNb,
                                     Messages.getString( "ProviderPreferencePage.BuildingNumber" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pPostalZone,
                                     Messages.getString( "ProviderPreferencePage.PostalZone" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pCity,
                                     Messages.getString( "ProviderPreferencePage.City" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.pCountry,
                                     Messages.getString( "ProviderPreferencePage.Country" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( IWorkbench workbench ) {
    //
  }
}