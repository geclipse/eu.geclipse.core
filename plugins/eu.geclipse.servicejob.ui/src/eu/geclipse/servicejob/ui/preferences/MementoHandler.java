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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.preferences;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;

import eu.geclipse.servicejob.model.ServiceJobStates;
import eu.geclipse.servicejob.ui.Activator;

/**
 * Helper class for handling memento file with information of colors used for
 * showing different Operator's Jobs result.
 */
public class MementoHandler {

  private static final String ROOT = "preferences"; //$NON-NLS-1$
  private static boolean changed = false;

  /**
   * Method for loading saved state from memento file or - in absence of such a
   * file - loading default values for colors.
   * 
   * @return mapping of Operator's Job's states to colors.
   */
  public static Map<ServiceJobStates, Color> loadState() {
    Map<ServiceJobStates, Color> values = new HashMap<ServiceJobStates, Color>();
    try {
      FileReader reader = new FileReader( getMementoFile() );
      XMLMemento memento = XMLMemento.createReadRoot( reader );
      for( IMemento child : memento.getChildren( "Value" ) ) { //$NON-NLS-1$
        String id = child.getString( "result" ); //$NON-NLS-1$
        RGB rgb = new RGB( child.getInteger( "red" ).intValue(), //$NON-NLS-1$
                           child.getInteger( "green" ).intValue(), //$NON-NLS-1$
                           child.getInteger( "blue" ).intValue() ); //$NON-NLS-1$
        Color color = new Color( Display.getCurrent(), rgb );
        values.put( ServiceJobStates.valueOfAlias( id ), color );
      }
    } catch( Exception e ) {
      Activator.logException( e );
    }
    changed = false;
    return values;
  }

  /**
   * Method to serialize color mapping to memento file.
   * 
   * @param values map with mapping from Operator's Job's states to colors which
   *            should be used to displaying those states.
   */
  public static void saveState( final Map<ServiceJobStates, Color> values ) {
    try {
      changed = true;
      XMLMemento memento = XMLMemento.createWriteRoot( ROOT );
      for( ServiceJobStates value : values.keySet() ) {
        IMemento child = memento.createChild( "Value" ); //$NON-NLS-1$
        child.putString( "result", value.getAlias() ); //$NON-NLS-1$
        Color color = values.get( value );
        child.putInteger( "red", color.getRed() ); //$NON-NLS-1$
        child.putInteger( "green", color.getGreen() ); //$NON-NLS-1$
        child.putInteger( "blue", color.getBlue() ); //$NON-NLS-1$
      }
      Writer writer = new FileWriter( MementoHandler.getMementoFile() );
      memento.save( writer );
    } catch( Exception e ) {
      Activator.logException( e );
    }
  }

  private static File getMementoFile() throws IOException {
    File mementofile = Activator.getDefault()
      .getStateLocation()
      .append( "color_preferences.xml" ) //$NON-NLS-1$
      .toFile();
    if( !mementofile.exists() ) {
      mementofile.createNewFile();
      // initial write a root to prevent errors while reading
      XMLMemento memento = XMLMemento.createWriteRoot( ROOT );
      Writer writer = new FileWriter( getMementoFile() );
      memento.save( writer );
    }
    return mementofile;
  }

  /**
   * Method to access flag indicating that color mapping has changed.
   * 
   * @return <code>true</code> if map has changed, <code>false</code>
   *         otherwise
   */
  public static boolean isChanged() {
    return changed;
  }
}
