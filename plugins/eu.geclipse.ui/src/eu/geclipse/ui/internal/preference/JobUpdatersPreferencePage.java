/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.preference;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class JobUpdatersPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  /**
   * Constructor for Jobs Preferences page
   */
  public JobUpdatersPreferencePage() {
    super( FieldEditorPreferencePage.GRID );
    IPreferenceStore preferenceStore = new ScopedPreferenceStore( new InstanceScope(),
                                                                  "eu.geclipse.core" ); //$NON-NLS-1$
    setPreferenceStore( preferenceStore );
    setDescription( Messages.getString("JobUpdatersPreferencePage.page_name") ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    BooleanFieldEditor updateStatus = new BooleanFieldEditor( Messages.getString( "JobsPreferencePage.jobs_update_jobs_status" ), //$NON-NLS-1$
                                                              Messages.getString("JobUpdatersPreferencePage.job_status_updates_active"), //$NON-NLS-1$
                                                              getFieldEditorParent() );
    addField( updateStatus );
    IntegerFieldEditor updatePeriod = new IntegerFieldEditor( Messages.getString( "JobsPreferencePage.jobs_update_jobs_period" ), //$NON-NLS-1$
                                                              Messages.getString("JobUpdatersPreferencePage.period_between_updates"), //$NON-NLS-1$
                                                              getFieldEditorParent(),
                                                              6 );
    updatePeriod.setValidRange( 0, 100000 );
    addField( updatePeriod );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    //empty block
  }
}