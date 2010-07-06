/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium 
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.jcraft.jzlib.ZInputStream;

/**
 * Reader for OTF Definition Files
 */
@SuppressWarnings("boxing")
public class OTFDefinitionReader {

  private BufferedReader input;
  // trace information
  private int maxFunctionID;
  private int maxProcessGroupID;
  private String version = null;
  private String codename = null;
  private String creator = null;
  // DP
  private Map<Integer, String> processNames;
  // DPG
  private Map<Integer, String> processGroupNames;
  private Map<Integer, ArrayList<Integer>> processGroups;
  // DSF
  private Map<Integer, String> sourceFileNames;
  // DS
  private Map<Integer, Integer> sourceLineNumbers;
  private Map<Integer, Integer> sourceFileIDs;
  // DFG
  private Map<Integer, String> functionGroupNames;
  // DF
  private Map<Integer, String> functionNames;
  private Map<Integer, Integer> functionGroupIDs;
  private Map<Integer, Integer> functionSourceLocationIDs;
  // DCO
  private Map<Integer, String> collectiveOperationsNames;
  private Map<Integer, Integer> collectiveOperationsTypes;

  // private Map<Integer, CollectiveOperation> collectiveOperations;
  /**
   * Creates a new Definition Reader
   * 
   * @param file (the .z ending is added automatically do not specify when creating File)
   * @throws IOException
   */
  public OTFDefinitionReader( final File file ) throws IOException {
    this.maxFunctionID = 0;
    this.maxProcessGroupID = 0;
    this.version = null;
    this.codename = null;
    this.creator = null;
    // DP
    this.processNames = new HashMap<Integer, String>();
    // DPG
    this.processGroupNames = new HashMap<Integer, String>();
    this.processGroups = new HashMap<Integer, ArrayList<Integer>>();
    // DSF
    this.sourceFileNames = new HashMap<Integer, String>();
    // DS
    this.sourceLineNumbers = new HashMap<Integer, Integer>();
    this.sourceFileIDs = new HashMap<Integer, Integer>();
    // DFG
    this.functionGroupNames = new HashMap<Integer, String>();
    // DF
    this.functionNames = new HashMap<Integer, String>();
    this.functionGroupIDs = new HashMap<Integer, Integer>();
    this.functionSourceLocationIDs = new HashMap<Integer, Integer>();
    // DCO
    this.collectiveOperationsNames = new HashMap<Integer, String>();
    this.collectiveOperationsTypes = new HashMap<Integer, Integer>();
    if( file.exists() ) {
      this.input = new BufferedReader( new FileReader( file ) );
    } else {
      File zFile = new File( file.getParentFile(), file.getName() + ".z" ); //$NON-NLS-1$
      // jcraft inflater
      this.input = new BufferedReader( new InputStreamReader( new ZInputStream( new FileInputStream( zFile ) ) ) );
      // SUN inflater, suffers from java bug # 4040920
      // this.input = new BufferedReader( new InputStreamReader( new InflaterInputStream( new FileInputStream( zFile ) ) ) );
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
      // Process Names
      else if( line.startsWith( "DP" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        Integer processId = Integer.valueOf( line.substring( 2, line.indexOf( "NM" ) ), 16 ); //$NON-NLS-1$
        String processName = line.substring( line.indexOf( "NM" ) + 3, line.length() - 1 ); //$NON-NLS-1$
        this.processNames.put( processId, processName );
      }
      // Process Group
      else if( line.startsWith( "DPG" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        Integer processGroupID = Integer.valueOf( line.substring( 3, line.indexOf( 'M' ) ), 16 );
        String processIDString = line.substring( line.indexOf( 'M' ) + 1, line.indexOf( "NM" ) ); //$NON-NLS-1$
        String processGroupName = line.substring( line.indexOf( "NM" ) + 3, line.length() - 1 ); //$NON-NLS-1$
        StringTokenizer stringTokenizer = new StringTokenizer( processIDString, "," ); //$NON-NLS-1$
        ArrayList<Integer> processGroup = new ArrayList<Integer>();
        while( stringTokenizer.hasMoreElements() ) {
          processGroup.add( Integer.parseInt( stringTokenizer.nextToken(), 16 ) );
        }
        this.processGroups.put( processGroupID, processGroup );
        this.processGroupNames.put( processGroupID, processGroupName );
        this.maxProcessGroupID = Math.max( this.maxProcessGroupID, processGroupID );
      }
      // Source Files
      else if( line.startsWith( "DSF" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        Integer sourceId = Integer.valueOf( line.substring( 3, line.indexOf( "NM" ) ), 16 ); //$NON-NLS-1$
        String sourceFileName = line.substring( line.indexOf( "NM" ) + 3, line.length() - 1 ); //$NON-NLS-1$
        this.sourceFileNames.put( sourceId, sourceFileName );
      }
      // Source Locations
      else if( line.startsWith( "DS" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        Integer locationID = Integer.valueOf( line.substring( 2, line.indexOf( 'F' ) ), 16 );
        Integer sourceFileID = Integer.valueOf( line.substring( line.indexOf( 'F' ) + 1, line.indexOf( "LN" ) ), 16 ); //$NON-NLS-1$
        Integer sourceLineNumber = Integer.valueOf( line.substring( line.indexOf( "LN" ) + 2, line.length() ), 16 ); //$NON-NLS-1$
        this.sourceLineNumbers.put( locationID, sourceLineNumber );
        this.sourceFileIDs.put( locationID, sourceFileID );
      }
      // Function Groups
      else if( line.startsWith( "DFG" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        Integer groupId = Integer.valueOf( line.substring( 3, line.indexOf( "NM" ) ), 16 ); //$NON-NLS-1$
        String groupName = line.substring( line.indexOf( "NM" ) + 3, line.length() - 1 ); //$NON-NLS-1$
        this.functionGroupNames.put( groupId, groupName );
      }
      // Functions
      else if( line.startsWith( "DF" ) && ( Character.isDigit( line.charAt( 2 ) ) || Character.isLowerCase( line.charAt( 2 ) ) ) ) { //$NON-NLS-1$
        int sourceLocationID = -1;
        Integer functionID = Integer.valueOf( line.substring( 2, line.indexOf( 'G' ) ), 16 );
        Integer functionGroupId = Integer.valueOf( line.substring( line.indexOf( 'G' ) + 1, line.indexOf( 'N' ) ), 16 );
        String functionName = line.substring( line.indexOf( '\"' ) + 1, line.lastIndexOf( '\"' ) );
        int sourcelocation = line.indexOf( 'X' );
        if( sourcelocation != -1 ) {
          sourceLocationID = Integer.valueOf( line.substring( sourcelocation + 1 ), 16 );
        }
        this.functionNames.put( functionID, functionName );
        this.functionGroupIDs.put( functionID, functionGroupId );
        this.functionSourceLocationIDs.put( functionID, sourceLocationID );
        this.maxFunctionID = Math.max( this.maxFunctionID, functionID );
      }
      // Collective Operations
      else if( line.startsWith( "DCO" ) && ( Character.isDigit( line.charAt( 3 ) ) || Character.isLowerCase( line.charAt( 3 ) ) ) ) { //$NON-NLS-1$
        Integer operationId = Integer.valueOf( line.substring( 3, line.indexOf( "NM" ) ), 16 ); //$NON-NLS-1$
        String operationName = line.substring( line.indexOf( "NM" ) + 3, line.lastIndexOf( '\"' ) ); //$NON-NLS-1$
        Integer operationType = Integer.valueOf( line.substring( line.indexOf( 'Y', line.lastIndexOf( '\"' ) ) + 1 ), 16 );
        this.collectiveOperationsNames.put( operationId, operationName );
        // 0 scan
        // 1 one sided, barrier
        // 2 scatter
        // 4 gather, reduce
        // 4 all , exscan
        this.collectiveOperationsTypes.put( operationId, operationType );
      }
      // Unsupported Definition
      else {
        Activator.getDefault().getLog().log( new Status( IStatus.WARNING, Activator.PLUGIN_ID, "Unsupported OTF Definition Type: " + line ) );
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
  public String getProcessName( final int processId ) {
    return this.processNames.get( processId );
  }

  /**
   * Returns the process group members
   * 
   * @param groupId
   * @return process group members
   */
  public Integer[] getProcessGroup( final int groupId ) {
    return this.processGroups.get( groupId ).toArray( new Integer[ 0 ] );
  }

  /**
   * Returns the name of the process group
   * 
   * @param groupId
   * @return process group
   */
  public String getProcessGroupName( final int groupId ) {
    return this.processGroupNames.get( groupId );
  }

  /**
   * Returns the Name of the sourcefile
   * 
   * @param sourceFileID
   * @return Name of sourcefile
   */
  public String getSourceFileName( final int sourceFileID ) {
    return this.sourceFileNames.get( sourceFileID );
  }

  /**
   * Returns the line number of the source location
   * 
   * @param sourceLocationID
   * @return line number
   */
  public int getSourceLineNumber( final int sourceLocationID ) {
    return this.sourceLineNumbers.get( sourceLocationID );
  }

  /**
   * Returns the source file id for the source location
   * 
   * @param sourceLocationID
   * @return source file id
   */
  public int getSourceFileID( final int sourceLocationID ) {
    return this.sourceFileIDs.get( sourceLocationID );
  }

  /**
   * Returns the group name for the specified function group id
   * 
   * @param functionGroupID
   * @return name of the according group
   */
  public String getFunctionGroupName( final int functionGroupID ) {
    return this.functionGroupNames.get( functionGroupID );
  }

  /**
   * Returns the function name for the specified function id
   * 
   * @param functionID
   * @return name of the according function
   */
  public String getFunctionName( final int functionID ) {
    return this.functionNames.get( functionID );
  }

  /**
   * Returns the function group id for the specified function id
   * 
   * @param functionID
   * @return function group id
   */
  public int getFunctionGroupID( final int functionID ) {
    return this.functionGroupIDs.get( functionID );
  }

  /**
   * Returns the source location id for the specified function id
   * 
   * @param functionID
   * @return source location id
   */
  public int getSourceLocationID( final int functionID ) {
    int result = -1;
    Integer id = this.functionSourceLocationIDs.get( functionID );
    if( id != null ) {
      result = id.intValue();
    }
    return result;
  }

  protected int getMaxFunctionID() {
    return this.maxFunctionID;
  }

  protected int getMaxProcessGroupID() {
    return this.maxProcessGroupID;
  }
}