/*****************************************************************************
 * Copyright (c) 2009, 2010 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

// import java.util.zip.InflaterInputStream;
import com.jcraft.jzlib.ZInputStream;

import eu.geclipse.eventgraph.tracereader.otf.preferences.PreferenceConstants;
import eu.geclipse.eventgraph.tracereader.otf.util.Node;
import eu.geclipse.traceview.EventType;

class OTFStreamReader extends OTFUtils {

  private BufferedReader input;
  private OTFReader trace;
  /* Preferences */
  /** read function events **/
  private boolean readFunctions;
  /** build calltree **/
  private boolean buildCalltree = true;
  /* Options */
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
  private Node node;
  private int logicalClock=0;

  OTFStreamReader( final File file, final OTFReader trace, final Map<Integer, Integer>[] entered ) throws IOException {
    this.readFunctions = Activator.getDefault().getPreferenceStore().getBoolean( PreferenceConstants.readFunctions );
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
      this.input = new BufferedReader( new InputStreamReader( new ZInputStream( new FileInputStream( zFile ) ) ) );
      // SUN inflater, suffers from java bug # 4040920
      // this.input = new BufferedReader( new InputStreamReader( new InflaterInputStream( new FileInputStream( zFile ) ) ) );
    }
    if( this.buildCalltree ) {
      this.node = new Node( this.trace.getDefinition(), -1 );
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
      else if( ch == 'E' && this.readFunctions ) {
        this.lineIdx++; // skip 'E'
        readEnter();
      }
      // read Leave
      else if( ch == 'L' && this.readFunctions ) {
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
        this.lineIdx += 3;
        readCollective();
      }
      // read Counter
      else if( this.line.startsWith( "CNT" ) ) { //$NON-NLS-1$
        // TODO implement CNT
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
    if( this.buildCalltree ) {
      this.node = this.node.enter( functionId, this.timestamp );
    }
    this.logicalClock++;
    if(this.logicalClock%1000 == 0){
      ((Process)this.trace.getProcess( this.processId )).addPreviousEvents( this.enteredStack.toArray( new Integer[0] ));
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
    if( this.buildCalltree ) {
      this.node = this.node.leave( this.timestamp );
    }
  }

  private void readRecv() {
    int otfPartnerId = readNumber();
    int length = -1;
    int type = -1;
    int group = -1;
    while( this.lineIdx != this.line.length() - 1 ) {
      // Read Length
      if( this.line.charAt( this.lineIdx ) == 'L' ) {
        this.lineIdx++; // skip L
        length = readNumber();
      }
      // Read type
      else if( this.line.charAt( this.lineIdx ) == 'T' ) {
        this.lineIdx++; // skip T
        type = readNumber();
      }
      // Read Communicator
      else if( this.line.charAt( this.lineIdx ) == 'C' ) {
        this.lineIdx++;
        group = readNumber();
      }
      // Read Unknown
      else {
        //System.out.println( "Receive contains unknown information " + ( char )this.line.charAt( this.lineIdx ) );
        this.lineIdx++;
        readNumber();
      }
    }
    int partnerId = this.trace.getProcessIdForOTFIndex( otfPartnerId );
    if( this.readFunctions ) {
      Process process = ( Process )this.trace.getProcess( this.processId );
      Event event = process.getEventByLogicalClock( process.getMaximumLogicalClock() );
      event.setType( EventType.RECV );
      event.setPartnerProcessId( partnerId );
      event.setMessageGroup( group );
      event.setReceivedMessageLength( length );
      event.setMessageType( type );
    } else {
      ( ( Process )this.trace.getProcess( this.processId ) ).appendEvent( EventType.RECV, partnerId, this.timestamp );
    }
  }

  private void readSend() {
    int otfPartnerId = readNumber();
    int length = -1;
    int type = -1;
    int group = -1;
    while( this.lineIdx != this.line.length() - 1 ) {
      // Read Length
      if( this.line.charAt( this.lineIdx ) == 'L' ) {
        this.lineIdx++; // skip L
        length = readNumber();
      }
      // Read type
      else if( this.line.charAt( this.lineIdx ) == 'T' ) {
        this.lineIdx++; // skip T
        type = readNumber();
      }
      // Read Communicator
      else if( this.line.charAt( this.lineIdx ) == 'C' ) {
        this.lineIdx++;
        group = readNumber();
      }
      // Read Unknown
      else {
        //System.out.println( "Sends contains unknown information " + ( char )this.line.charAt( this.lineIdx ) );
        this.lineIdx++;
        readNumber();
      }
    }
    int partnerId = this.trace.getProcessIdForOTFIndex( otfPartnerId );
    if( this.readFunctions ) {
      Process process = ( Process )this.trace.getProcess( this.processId );
      Event event = process.getEventByLogicalClock( process.getMaximumLogicalClock() );
      event.setType( EventType.SEND );
      event.setPartnerProcessId( partnerId );
      event.setMessageGroup( group );
      event.setSentMessageLength( length );
      event.setMessageType( type );
    } else {
      ( ( Process )this.trace.getProcess( this.processId ) ).appendEvent( EventType.SEND, partnerId, this.timestamp );
    }
  }

  private void readCollective() {
    int collectiveOperationId = readNumber();
    int group = -1;
    int root = -1;
    int sent = -1;
    int received = -1;
    if( this.line.charAt( this.lineIdx ) == 'C' ) {
      this.lineIdx++; // skip C
      group = readNumber(); // Skip
    }
    if( this.line.indexOf( "RT" ) == this.lineIdx ) { //$NON-NLS-1$
      this.lineIdx += 2; // skip RT
      root = readNumber();
    }
    if( this.line.charAt( this.lineIdx ) == 'S' ) {
      this.lineIdx++; // skip S
      sent = readNumber();
    }
    if( this.line.charAt( this.lineIdx ) == 'R' ) {
      this.lineIdx++; // skip R
      received = readNumber();
    }
    if( this.readFunctions ) {
      Process process = ( Process )this.trace.getProcess( this.processId );
      Event event = process.getEventByLogicalClock( process.getMaximumLogicalClock() );
      event.setType( EventType.OTHER );
      event.setMessageGroup( group );
      event.setPartnerProcessId( this.trace.getProcessIdForOTFIndex( root ) );
      event.setSentMessageLength( sent );
      event.setReceivedMessageLength( received );
      // event.setMessageType( type );
    } else {
      ( ( Process )this.trace.getProcess( this.processId ) ).appendEvent( EventType.SEND, -1, this.timestamp );
    }
  }

  private void readProcessId() {
    this.processId = this.trace.getProcessIdForOTFIndex( readNumber() );
  }

  private void readTimestamp() {
    this.timestamp = readTime();
  }

  public Node getNode() {
    return this.node;
  }
}
