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

package eu.geclipse.gvid.internal.preferences;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.*;
// import org.eclipse.jface.preference.ComboFieldEditor; // does not yet exist
// in this version (should be in 3.3 M1)
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import eu.geclipse.gvid.Activator;
import eu.geclipse.gvid.IDecoder;

/**
 * Preference page for the GVid output client.
 */
public class GVidPreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage {
  /**
   * Creates the preferences page for the GVid output client. 
   */
  public GVidPreferencePage() {
    super( GRID );
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
    setDescription( Messages.getString("GVidPreferencePage.settingsForGVid") ); //$NON-NLS-1$
  }

  /**
   * Creates the field editors. Field editors are abstractions of the common GUI
   * blocks needed to manipulate various types of preferences. Each field editor
   * knows how to save and restore itself.
   */
  @Override
  public void createFieldEditors() {
    addField( new BooleanFieldEditor( PreferenceConstants.P_NO_FRAME_SKIP,
                                      Messages.getString("GVidPreferencePage.noFrameSkip"), //$NON-NLS-1$
                                      getFieldEditorParent() ) );
    // TODO codec combo box instead of radio group
    addField( new RadioGroupFieldEditor( PreferenceConstants.P_CODEC_NAME,
                                         Messages.getString("GVidPreferencePage.codec"), //$NON-NLS-1$
                                         1,
                                         getCodecList(),
                                         getFieldEditorParent(),
                                         true ) );
  }

  private String[][] getCodecList() {
    List<String[]> list = new LinkedList<String[]>();
    IExtensionPoint p = Platform.getExtensionRegistry()
      .getExtensionPoint( IDecoder.CODEC_EXTENSION_POINT );
    IExtension[] extensions = p.getExtensions();
    for( IExtension extension : extensions ) {
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for( IConfigurationElement element : elements ) {
        if( IDecoder.EXT_CODEC.equals(element.getName() ) ) {
          list.add( new String[]{
            element.getAttribute( IDecoder.EXT_NAME ), element.getAttribute( IDecoder.EXT_NAME )
          } );
        }
      }
    }
    return list.toArray( new String[ 0 ][ 0 ] );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
   */
  public void init( final IWorkbench workbench ) {
    // nothing to initialize
  }
}
