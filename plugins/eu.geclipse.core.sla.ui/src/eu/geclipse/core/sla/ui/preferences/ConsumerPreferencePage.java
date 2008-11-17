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

import eu.geclipse.core.sla.Activator;
import eu.geclipse.core.sla.preferences.PreferenceConstants;

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
public class ConsumerPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  /**
   * 
   */
  public ConsumerPreferencePage() {
    super( GRID );
    Plugin nonUIPlugin = eu.geclipse.core.sla.Activator.getDefault();
    IPreferenceStore preferenceStore = new ScopedPreferenceStore( new InstanceScope(),
                                                                  nonUIPlugin.getBundle()
                                                                    .getSymbolicName() );
    setPreferenceStore( preferenceStore );
    setDescription( Messages.getString( "ConsumerPreferencePage.Description" ) ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new StringFieldEditor( PreferenceConstants.cRegistryURI,
                                     Messages.getString( "ConsumerPreferencePage.registyURI" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cName,
                                     Messages.getString( "ConsumerPreferencePage.Name" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cFullName,
                                     Messages.getString( "ConsumerPreferencePage.FullName" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cDepartment,
                                     Messages.getString( "ConsumerPreferencePage.Department" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cStreet,
                                     Messages.getString( "ConsumerPreferencePage.Street" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cBuildingNb,
                                     Messages.getString( "ConsumerPreferencePage.BuildingNumber" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cPostalZone,
                                     Messages.getString( "ConsumerPreferencePage.PostalZone" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cCity,
                                     Messages.getString( "ConsumerPreferencePage.City" ), //$NON-NLS-1$
                                     getFieldEditorParent() ) );
    addField( new StringFieldEditor( PreferenceConstants.cCountry,
                                     Messages.getString( "ConsumerPreferencePage.Country" ), //$NON-NLS-1$
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