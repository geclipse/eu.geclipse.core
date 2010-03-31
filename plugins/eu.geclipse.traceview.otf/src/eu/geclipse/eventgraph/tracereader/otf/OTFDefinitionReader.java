/*****************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.InflaterInputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

//import com.jcraft.jzlib.ZInputStream;

/**
 * Reader for OTF Definition Files
 */
public class OTFDefinitionReader {

  private String version = null;
  private String codename = null;
  private String creator = null;
  private Map<Integer, String> processNames;
  private Map<Integer, String> processGroups;
  private Map<Integer, String> sourceFiles;
  private Map<Integer, int[]> sourceLocations;
  private Map<Integer, String> functionGroups;
  private Map<Integer, String> functions;
  private Map<Integer, String> collectiveOperations;
  private BufferedReader input;

  /**
   * Creates a new Definition Reader
   * 
   * @param file (the .z ending is added automatically do not specify when creating the File)
   * @throws IOException
   */
  public OTFDefinitionReader( final File file ) throws IOException {
    this.version = null;
    this.codename = null;
    this.creator = null;
    this.processNames = new HashMap<Integer, String>();
    this.processGroups = new HashMap<Integer, String>();
    this.sourceFiles = new HashMap<Integer, String>();
    this.sourceLocations = new HashMap<Integer, int[]>();
    this.functionGroups = new HashMap<Integer, String>();
    this.functions = new HashMap<Integer, String>();
    this.collectiveOperations = new HashMap<Integer, String>();
    if( file.exists() ) {
      this.input = new BufferedReader( new FileReader( file ) );
    } else {
      File zFile = new File( file.getParentFile(), file.getName() + ".z" ); //$NON-NLS-1$
      // jcraft inflater
      // this.input = new BufferedReader( new InputStreamReader( new ZInputStream( new FileInputStream( zFile ) ) ) );
      // SUN inflater, suffers from java bug # 4040920
      this.input = new BufferedReader( new InputStreamReader( new InflaterInputStream( new FileInputStream( zFile ) ) ) );
    }
    read();
  }

  private void read() throws IOException {
    String line;
    while( ( line = this.input.readLine() ) != null ) {
      // Version
      if( line.startsWith( "DV" ) ) { //$NON-NLS-1$
        int start = line.indexOf( "\"" ); //$NON-NLS-1$
        int end = line.lastIndexOf( "\"" ); //$NON-NLS-1$
        this.version = line.substring( 2, start );
        this.codename = line.substring( start + 1, end );
      }
      // Creator
      else if( line.startsWith( "DCR" ) ) { //$NON-NLS-1$
        this.creator = line.substring( 4, line.length() - 1 );
      }
      // Trace Id?
      else if( line.startsWith( "DTR" ) ) { //$NON-NLS-1$
        // TODO
      }
      // Process Names
      else if( line.startsWith( "DP" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Communicators
      else if( line.startsWith( "DPG" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Source Files
      else if( line.startsWith( "DSF" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Source Locations
      else if( line.startsWith( "DS" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Source Groups
      else if( line.startsWith( "DFG" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Functions -- TODO add to group map
      else if( line.startsWith( "DF" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        Integer functionId = Integer.valueOf( line.substring( 2, line.indexOf( 'G' ) ), 16 );
        String name = line.substring( line.indexOf( '\"' ) + 1, line.lastIndexOf( '\"' ) );
        Integer groupId = Integer.valueOf( line.substring( line.indexOf( 'G' ) + 1, line.indexOf( 'N' ) ), 16 );
        this.functions.put( functionId, name );
      }
      // Collective Operations
      else if( line.startsWith( "DCO" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        // TODO
      }
      // Unsupported Definition
      else {
        Activator.getDefault().getLog().log( new Status( IStatus.WARNING, Activator.PLUGIN_ID, "Unsupported OTF Definition Type " + line ) );
      }
    }
  }

  /**
   * Returns the OTF version of the trace
   * 
   * @return OTF Version
   */
  public String getVersion() {
    return this.version;
  }

  /**
   * Returns the codename of the OTF version of the trace
   * 
   * @return Codename
   */
  public String getCodename() {
    return this.codename;
  }

  /**
   * Returns the name of the tool used to create the trace
   * 
   * @return Name of the tool used to create the trace
   */
  public String getCreator() {
    return this.creator;
  }

  /**
   * Returns the process name for the specified process id
   * 
   * @param processId
   * @return process name
   */
  @SuppressWarnings("boxing")
  public String getProcessName( final int processId ) {
    return this.processNames.get( processId );
  }

  /**
   * Returns the function name for the specified function id
   * 
   * @param functionId
   * @return name of the according function
   */
  @SuppressWarnings("boxing")
  public String getFunctionName( final int functionId ) {
    return this.functions.get( functionId );
  }
}