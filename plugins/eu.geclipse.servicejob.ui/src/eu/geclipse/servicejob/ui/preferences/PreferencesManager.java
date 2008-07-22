/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.servicejob.model.ServiceJobStates;

/**
 * Class managing preferences of mapping of Operator's Job's states to colors
 * which should be used for displaying those states. </br> Singleton class.
 */
public class PreferencesManager {

  private static PreferencesManager singleton;
  private static Map<ServiceJobStates, Color> colorMap;

  private PreferencesManager() {
    colorMap = new HashMap<ServiceJobStates, Color>();
    colorMap = MementoHandler.loadState();
    if( colorMap.size() == 0 ) {
      colorMap = loadDefaultMap();
    }
  }

  private Map<ServiceJobStates, Color> loadDefaultMap() {
    Map<ServiceJobStates, Color> result = new HashMap<ServiceJobStates, Color>();
    for( ServiceJobStates value : ServiceJobStates.values() ) {
      switch( value ) {
        case CRITICAL:
          result.put( ServiceJobStates.CRITICAL, Display.getCurrent()
            .getSystemColor( SWT.COLOR_DARK_MAGENTA ) );
        break;
        case ERROR:
          result.put( ServiceJobStates.ERROR, Display.getCurrent()
            .getSystemColor( SWT.COLOR_RED ) );
        break;
        case NA:
          result.put( ServiceJobStates.NA, Display.getCurrent()
            .getSystemColor( SWT.COLOR_DARK_GRAY ) );
        break;
        case OK:
          result.put( ServiceJobStates.OK, Display.getCurrent()
            .getSystemColor( SWT.COLOR_GREEN ) );
        break;
        case RUNNING:
          result.put( ServiceJobStates.RUNNING, Display.getCurrent()
            .getSystemColor( SWT.COLOR_BLACK ) );
        break;
        case WARNING:
          result.put( ServiceJobStates.WARNING, Display.getCurrent()
            .getSystemColor( SWT.COLOR_DARK_YELLOW ) );
        break;
        case NULL:
          // do nothing
        break;
      }
    }
    return result;
  }

  /**
   * Method for accessing singleton object of {@link PreferencesManager} class.
   * 
   * @return the only existing {@link PreferencesManager} object
   */
  public static PreferencesManager getManager() {
    if( singleton == null ) {
      singleton = new PreferencesManager();
    }
    return singleton;
  }

  /**
   * Method for accessing map with mapping from Operator's Job's states to
   * colors which should be used for displaying those states.
   * 
   * @return map loaded from memento file or - in absence of this file - default
   *         values.
   */
  public Map<ServiceJobStates, Color> getColorMapping() {
    if( MementoHandler.isChanged() ) {
      colorMap = MementoHandler.loadState();
    }
    return colorMap;
  }

  /**
   * Gets color which should be used for displaying given status of Operator's
   * Job.
   * 
   * @param result string representation of Operator's Job status
   * @return color which should be used for presenting given status to user
   */
  public Color getColor( final String result ) {
    return getColorMapping().get( ServiceJobStates.valueOfAlias( result ) );
  }

  /**
   * Serializes given mapping of Operator's Job statuses to colors to memento
   * file.
   * 
   * @param map for serialization - mapping from Operator's Job's statuses to
   *            colors which should be used for dysplaying those statuses
   */
  public void saveState( final Map<ServiceJobStates, Color> map ) {
    MementoHandler.saveState( map );
  }
}
