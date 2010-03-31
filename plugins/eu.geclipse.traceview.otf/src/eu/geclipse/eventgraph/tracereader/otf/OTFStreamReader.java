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
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *    Christof Klausecker MNM-Team, LMU Munich - improved event support
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.zip.InflaterInputStream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

//import com.jcraft.jzlib.ZInputStream;

import eu.geclipse.traceview.EventType;

class OTFStreamReader extends OTFUtils {

  private BufferedReader input;
  private OTFReader trace;
  /** read only communication events **/
  private boolean communication;
  /** MPI process number */
  private int processId;
  /** physical time */
  private int timestamp = 0;
  /** unknown event types */
  private Map<Integer, int[]> unknown;
  /** Stack or mapped Enter Events **/
  private int stacked = 0;
  /** stack to match Enter and Leave Events **/
  private Stack<Integer> enteredStack;
  /** map to match Enter and Leave Events */
  private Map<Integer, Integer>[] enteredMap;
  /** unmatched Leave Events */
  private Set<Integer> unmatchedLeaves;

  OTFStreamReader( final File file, final OTFReader trace, final Map<Integer, Integer>[] entered ) throws IOException {
    this.communication = false;
    this.trace = trace;
    this.enteredMap = entered;
    this.enteredStack = new Stack<Integer>();
    this.unknown = new HashMap<Integer, int[]>();
    this.unmatchedLeaves = new HashSet<Integer>();
    if( file.exists() ) {
      this.input = new BufferedReader( new FileReader( file ) );
    } else {
      File zFile = new File( file.getParentFile(), file.getName() + ".z" ); //$NON-NLS-1$
      // jcraft inflater
      // this.input = new BufferedReader( new InputStreamReader( new ZInputStream( new FileInputStream( zFile ) ) ) );
      // SUN inflater, suffers from java bug # 4040920
      this.input = new BufferedReader( new InputStreamReader( new InflaterInputStream( new FileInputStream( zFile ) ) ) );
    }
  }

  @SuppressWarnings("boxing")
  public void readStream() throws IOException {
    while( ( this.line = this.input.readLine() ) != null ) {
      this.lineIdx = 0;
      int ch = this.line.charAt( 0 );
      // read TimeStamp
      if( Character.isDigit( ch ) || Character.isLowerCase( ch ) ) {
        readTimestamp();
      }
      // read Enter
      else if( ch == 'E' ) {
        this.lineIdx++; // skip 'E'
        readEnter();
      }
      // read Leave
      else if( ch == 'L' ) {
        this.lineIdx++; // skip 'L'
        readLeave();
      }
      // read ProcessChange
      else if( ch == '*' ) {
        this.lineIdx++; // skip '*'
        readProcessId();
      }
      // read Send
      else if( ch == 'S' ) {
        this.lineIdx++; // skip 'S'
        readSend();
      }
      // read Receive
      else if( ch == 'R' ) {
        this.lineIdx++; // skip 'R'
        readRecv();
      }
      // read Collective Operation
      else if( this.line.startsWith( "COP" ) ) { //$NON-NLS-1$
        // TODO implement COP
      }
      // read Counter
      else if( this.line.startsWith( "CNT" ) ) { //$NON-NLS-1$
        // TODO implement COP
      }
      // unknown
      else {
        int[] value = this.unknown.get( ch );
        if( value == null ) {
          value = new int[ 1 ];
          value[ 0 ] = 1;
          this.unknown.put( new Integer( ch ), value );
        } else {
          value[ 0 ]++;
        }
      }
    }
    // for( Integer key : this.unknown.keySet() ) {
    // int[] value = this.unknown.get( key );
    // Activator.getDefault().getLog().log( new Status( IStatus.WARNING,
    // Activator.PLUGIN_ID, "Unsupported OTF Event Type " + ( char
    // )key.intValue() + " occured " + value[ 0 ] + " times in file" ) );
    // }
  }

  @SuppressWarnings("boxing")
  private void readEnter() {
    int functionId = readNumber();
    ( ( Process )this.trace.getProcess( this.processId ) ).appendFunctionEvent( EventType.OTHER, functionId, this.timestamp );
    if( this.stacked > 0 ) {
      this.enteredStack.push( this.trace.getProcess( this.processId ).getMaximumLogicalClock() );
    } else if( this.stacked < 0 ) {
      this.enteredMap[ this.processId ].put( functionId, this.trace.getProcess( this.processId ).getMaximumLogicalClock() );
    } else {
      this.enteredStack.push( this.trace.getProcess( this.processId ).getMaximumLogicalClock() );
      this.enteredMap[ this.processId ].put( functionId, this.trace.getProcess( this.processId ).getMaximumLogicalClock() );
    }
  }

  @SuppressWarnings("boxing")
  private void readLeave() {
    int functionId = readNumber();
    Integer eventId = null;
    if( this.stacked > 0 ) {
      eventId = this.enteredStack.pop();
    } else if( this.stacked < 0 ) {
      eventId = this.enteredMap[ this.processId ].remove( functionId );
    } else if( !this.enteredStack.isEmpty() ) {
      eventId = this.enteredStack.pop();
      this.stacked = 1;
    } else {
      eventId = this.enteredMap[ this.processId ].remove( functionId );
      this.stacked = -1;
    }
    if( eventId != null ) {
      ( ( Event )this.trace.getProcess( this.processId ).getEventByLogicalClock( eventId ) ).setPhysicalStopClock( this.timestamp );
    } else {
      if( !this.unmatchedLeaves.contains( functionId ) ) {
        this.unmatchedLeaves.add( functionId );
        Activator.getDefault().getLog().log( new Status( IStatus.WARNING, Activator.PLUGIN_ID, "OTF Event Type Leave with function ID "
                                                                                               + functionId
                                                                                               + " occured without Enter on process "
                                                                                               + this.processId ) );
      }
    }
  }

  private void readRecv() throws IOException {
    int otfPartnerId = readNumber();
    int length = -1;
    int type = -1;
    int communicator = -1;
    while( this.lineIdx != this.line.length() - 1 ) {
      // Read Length
      if( this.line.charAt( this.lineIdx ) == 'L' ) {
        this.lineIdx++; // skip L
        length = readNumber();
      }
      // Read type
      else if( this.line.charAt( this.lineIdx ) == 'T' ) {
        this.lineIdx++; // skip T
        length = readNumber();
      }
      // Read Communicator
      else if( this.line.charAt( this.lineIdx ) == 'C' ) {
        this.lineIdx++;
        communicator = readNumber();
      }
      // Read Unknown
      else {
        System.out.println( "Receive contains unknown information " + ( char )this.line.charAt( this.lineIdx ) );
        this.lineIdx++;
        readNumber();
      }
    }
    int partnerId = this.trace.getProcessIdForOTFIndex( otfPartnerId );
    ( ( Process )this.trace.getProcess( this.processId ) ).appendEvent( EventType.RECV, partnerId, this.timestamp );
  }

  private void readSend() throws IOException {
    int otfPartnerId = readNumber();
    int length = -1;
    int type = -1;
    int communicator = -1;
    while( this.lineIdx != this.line.length() - 1 ) {
      // Read Length
      if( this.line.charAt( this.lineIdx ) == 'L' ) {
        this.lineIdx++; // skip L
        length = readNumber();
      }
      // Read type
      else if( this.line.charAt( this.lineIdx ) == 'T' ) {
        this.lineIdx++; // skip T
        length = readNumber();
      }
      // Read Communicator
      else if( this.line.charAt( this.lineIdx ) == 'C' ) {
        this.lineIdx++;
        communicator = readNumber();
      }
      // Read Unknown
      else {
        System.out.println( "Receive contains unknown information " + ( char )this.line.charAt( this.lineIdx ) );
        this.lineIdx++;
        readNumber();
      }
    }
    int partnerId = this.trace.getProcessIdForOTFIndex( otfPartnerId );
    ( ( Process )this.trace.getProcess( this.processId ) ).appendEvent( EventType.SEND, partnerId, this.timestamp );
  }

  private void readProcessId() {
    this.processId = this.trace.getProcessIdForOTFIndex( readNumber() );
  }

  private void readTimestamp() {
    this.timestamp = readTime();
  }
}
