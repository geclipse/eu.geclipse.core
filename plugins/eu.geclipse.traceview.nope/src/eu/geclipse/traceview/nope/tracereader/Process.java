/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.nope.tracereader;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.nope.Activator;
import eu.geclipse.traceview.utils.AbstractProcessFileCache;

/**
 * NOPE (NOndeterministic Program Evaluator) Process
 */
final public class Process extends AbstractProcessFileCache {

  /* StatusByte */
  /** a processors byte ordering */
  public static byte trcLittleEndian = ( byte )0x80;
  /** a processors byte ordering */
  public static byte trcBigEndian = ( byte )0x00;
  /** completeness of a trace */
  public static byte trcPartial = ( byte )0x00;
  /** completeness of a trace */
  public static byte trcTotal = ( byte )0x40;
  /** source file information added */
  public static byte trcWithout = ( byte )0x00;
  /** source file information added */
  public static byte trcWith = ( byte )0x20;
  /** level of monitor implementation */
  public static byte trcFunctions = ( byte )0x00;
  /** level of monitor implementation */
  public static byte trcMacros = ( byte )0x08;
  /** level of monitor implementation */
  public static byte trcAssembler = ( byte )0x10;
  /** level of monitor implementation */
  public static byte trcTraps = ( byte )0x18;
  /** amount of traced event data */
  public static byte trcMinimum = ( byte )0x00;
  /** amount of traced event data */
  public static byte trcMedium = ( byte )0x02;
  /** amount of traced event data */
  public static byte trcMaximum = ( byte )0x06;
  /** hardware and OS used */
  public static byte trcNCUBE = ( byte )0x00;
  /** hardware and OS used */
  public static byte trcPARSYTEC = ( byte )0x01;
  /** boolean values */
  public static byte trcFALSE = ( byte )0x00;
  /** boolean values */
  public static byte trcTRUE = ( byte )0x80;
  /* event types */
  /** no event, end of trace file */
  public static byte trcNoEv = 0x00;
  /** receive event (nread, nreadp) */
  public static byte trcRecv = 0x01;
  /** send event (nwrite, nwritep) */
  public static byte trcSend = 0x02;
  /** test event (ntest) */
  public static byte trcTest = 0x03;
  /** internal event: new source file */
  public static byte trcSourceLine = 0x04;
  /** event of type "others", e.g. nsync */
  public static byte trcOthers = 0x05;
  /** trace event, e.g. monULONGTRACE */
  public static byte trcTrace = 0x07;
  /* event subtypes */
  /** variable inspection type */
  public static byte trcTVarInsp = 0x00;
  /** switch flushing - type */
  public static byte trcTFlush = 0x01;
  /** activate/deactivate mon */
  public static byte trcTOnOff = 0x02;
  /** record time stamp (abs) */
  public static byte trcTTimer = 0x03;
  /** message queue trace */
  public static byte trcTMsgQueue = 0x04;
  /** repeated test event type */
  public static byte trcTTestLoop = 0x05;
  /** array inspection type */
  public static byte trcTArrInsp = 0x06;

  private static final String PROP_ID = "Process.Id"; //$NON-NLS-1$

  private static IPropertyDescriptor[] descriptors;

  /*
   * Information of from the File
   */
  private String traceFileName;
  private long fileSize;
  /* Information from the Header */
  private int version;
  private boolean isTrcBigEndian;
  private int traceAmount;
  private boolean source;
  private int level;
  private int dataAmount;
  private int multiprocessor;
  private String filename;
  private String arguments;
  private int monStrat;
  private int monPModel;
  private int monStor;
  private int monError;
  private int monFlush;
  private int monBuffer;
  private long runTimeStart;
  /* Information from the Event */
  private HashMap<Integer, Integer> ignoreCounts = new HashMap<Integer, Integer>();
  private int previousLogicalClock = -1;
  private boolean debug = false;
  // private FileProcessCache processCache;
  // private List<String> sourceFilenames;
  private int sourceFilenameIndex;
  private int[] initialVectorClock;
  private boolean supportsVectorClocks;
  // private FileInputStream fileInputStream = null;
  private DataInputStream dataInputStream = null;
  /** Process */
  static {
    descriptors = new IPropertyDescriptor[]{
      new PropertyDescriptor( PROP_ID, "Process ID" ), //$NON-NLS-1$
    };
  }

  protected Process( final InputStream inputStream,
                     final String filename,
		  			 final long filesize,
                     final int processId,
                     final boolean hasCache,
                     final Trace trace )
  {
    super( trace, processId );
    // initialise vectorclocks
    this.supportsVectorClocks = ( ( Trace )trace ).supportsVectorClocks();
    if ( supportsVectorClocks ) {
      this.initialVectorClock = new int[ trace.getNumberOfProcesses() ];
      for( int i = 0; i < this.initialVectorClock.length; i++ ) {
        this.initialVectorClock[ i ] = -1;
      }
    }
    try {
      // in case the cache does not exist
      InputStream bufferedInputStream = new BufferedInputStream( inputStream );
      this.dataInputStream = new DataInputStream( bufferedInputStream );
      this.traceFileName = filename;
      this.fileSize = filesize;
      /*
       * header information
       */
      readHeader();
      /*
       * debug output
       */
      if( this.debug ) {
        headerInfo();
      }
      /*
       * read the events
       */
      if( !hasCache ) {
        Event event = null;
        while( ( event = readEvent() ) != null ) {
          if( this.debug ) {
            eventInfo( event );
          }
        }
      }
    } catch( EOFException eofException ) {
      // do nothing (because of unsupported event types)
    } catch( FileNotFoundException fileNotFoundException ) {
      Activator.logException( fileNotFoundException );
    } catch( IOException ioException ) {
      Activator.logException( ioException );
    }
  }

  // *****************************************************
  // * IPropertySource
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return descriptors;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  @Override
  public Object getPropertyValue( final Object id ) {
    return Integer.valueOf( this.getProcessId() );
  }

  // *****************************************************
  // * IProcess
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IProcess#getEventByLogicalClock(int)
   */
  public Event getEventByLogicalClock( final int index )
    throws IndexOutOfBoundsException
  {
    Event result = null;
    if( index <= this.getMaximumLogicalClock() ) {
      if( this.supportsVectorClocks ) result = new VecEvent( index, this );
      else result = new Event( index, this );
    }
    return result;
  }

  private int readInt() throws IOException {
    int result = this.dataInputStream.readInt();
    if( !this.isTrcBigEndian ) {
      result = Integer.reverseBytes( result );
    }
    return result;
  }

  private long readLong() throws IOException {
    long result = this.dataInputStream.readLong();
    if( !this.isTrcBigEndian ) {
      result = Long.reverseBytes( result );
    }
    return result;
  }

  private void debugOutput( final String message ) {
//    System.out.println( message );
  }

  private void headerInfo() {
    debugOutput( "version of trace file : " + this.version ); //$NON-NLS-1$
    debugOutput( "processor             : " + this.processId ); //$NON-NLS-1$
    debugOutput( "filename:             : " + this.traceFileName ); //$NON-NLS-1$
    debugOutput( "filesize:             : " + this.fileSize ); //$NON-NLS-1$
    if( this.isTrcBigEndian ) {
      debugOutput( "byte ordering         : big endian" ); //$NON-NLS-1$
    } else {
      debugOutput( "byte ordering         : little endian" ); //$NON-NLS-1$
    }
    debugOutput( "trace amount          : " + this.traceAmount ); //$NON-NLS-1$
    debugOutput( "source switch         : " + this.source ); //$NON-NLS-1$
    debugOutput( "level of impl         : " + this.level ); //$NON-NLS-1$
    debugOutput( "data amount           : " + this.dataAmount ); //$NON-NLS-1$
    debugOutput( "multiprocessor        : " + this.multiprocessor ); //$NON-NLS-1$
    debugOutput( "program name          : " + this.filename ); //$NON-NLS-1$
    debugOutput( "program args          : " + this.arguments ); //$NON-NLS-1$
    debugOutput( "run time start        : " + this.runTimeStart ); //$NON-NLS-1$
    debugOutput( "mon strategy          : " + this.monStrat ); //$NON-NLS-1$
    debugOutput( "model of prog         : " + this.monPModel ); //$NON-NLS-1$
    debugOutput( "error mon             : " + this.monError ); //$NON-NLS-1$
    debugOutput( "flushing mode         : " + this.monFlush ); //$NON-NLS-1$
    debugOutput( "file buffer           : " + this.monBuffer ); //$NON-NLS-1$
    debugOutput( "tracing disk          : " + this.monStor ); //$NON-NLS-1$
  }

  private void eventInfo( final Event event ) {
    debugOutput( "Type: " + event.getType() ); //$NON-NLS-1$
    debugOutput( "" );
    debugOutput( "traceComm.blocking: " + event.getBlocking() ); //$NON-NLS-1$
    debugOutput( "traceComm.commname: " + event.getSubType() ); //$NON-NLS-1$
    debugOutput( "" );
    debugOutput( "(traceComm.supposed)->partner: " //$NON-NLS-1$
                        + event.getSupposedPartnerProcess() );
    debugOutput( "(traceComm.supposed)->msgtype: " //$NON-NLS-1$
                        + event.getSupposedMessageType() );
    debugOutput( "(traceComm.supposed)->msglength: " //$NON-NLS-1$
                        + event.getSupposedMessageLength() );
    debugOutput( "" );
    debugOutput( "(traceComm.accepted)->partner: " //$NON-NLS-1$
                        + event.getAcceptedPartnerProcess() );
    debugOutput( "(traceComm.accepted)->msgtype: " //$NON-NLS-1$
                        + event.getAcceptedMessageType() );
    debugOutput( "(traceComm.accepted)->msglength: " //$NON-NLS-1$
                        + event.getAcceptedMessageLength() );
    debugOutput( "" );
    debugOutput( "traceTime.log: " + event.getLogicalClock() ); //$NON-NLS-1$
    debugOutput( "traceTime.partnerLog: " //$NON-NLS-1$
                        + event.getPartnerLogicalClock() );
    debugOutput( "traceTime.timeStart: " + event.getPhysicalStartClock() ); //$NON-NLS-1$
    debugOutput( "traceTime.timeStop: " + event.getPhysicalStopClock() ); //$NON-NLS-1$
    debugOutput( "" );
    debugOutput( "traceExt.line: " + event.getSourceLineNumber() ); //$NON-NLS-1$
    debugOutput( "traceExt.file: " + event.getSourceFilename() ); //$NON-NLS-1$
  }

  void readHeader() throws IOException {
    this.version = this.dataInputStream.readInt();
    byte statusByte = this.dataInputStream.readByte();
    this.isTrcBigEndian = ( statusByte & trcLittleEndian ) == 0x00;
    if( !this.isTrcBigEndian )
      this.version = Integer.reverseBytes( this.version );
    this.traceAmount = ( statusByte & trcTotal );
    this.source = ( statusByte & trcWith ) != 0x00;
    this.level = ( statusByte & trcTraps );
    this.dataAmount = ( statusByte & trcMaximum );
    this.multiprocessor = ( statusByte & trcPARSYTEC );
    byte[] filenameArray = new byte[ readInt() ];
    this.dataInputStream.read( filenameArray );
    this.filename = new String( filenameArray );
    byte[] argumentsArray = new byte[ readInt() ];
    this.dataInputStream.read( argumentsArray );
    this.arguments = new String( argumentsArray );
    this.monStrat = readInt();
    this.monPModel = readInt();
    this.monStor = readInt();
    this.monError = readInt();
    this.monFlush = readInt();
    this.monBuffer = readInt();
    this.runTimeStart = readLong();
  }

  private Event readEvent() throws IOException {
    Event event = null;
    byte fileByte = 0;
    fileByte = this.dataInputStream.readByte();
    int traceType = ( fileByte & ( ~trcTRUE ) );
    if( traceType == trcRecv || traceType == trcSend || traceType == trcTest ) {
      event = readReceiveSendTestEvent( traceType );
    } else if( traceType == trcSourceLine ) {
      readSourceEvent();
      event = readEvent();
    } else if( traceType == trcOthers ) {
      Activator.logMessage( IStatus.WARNING, "unimplemented event type: trcOthers" ); //$NON-NLS-1$
    } else if( traceType == trcTrace ) {
      event = readTraceEvent();
    } else {
      Activator.logMessage( IStatus.ERROR, "unknown event type" ); //$NON-NLS-1$
    }
    return event;
  }

  private Event readReceiveSendTestEvent( final int traceType )
    throws IOException
  {
    this.previousLogicalClock++;
    this.setMaximumLogicalClock( this.previousLogicalClock );
    Event event;
    if( this.supportsVectorClocks ) event = new VecEvent( this.previousLogicalClock, this );
    else event = new Event( this.previousLogicalClock, this );
    event.setLamportClock( -1 );
    if( this.supportsVectorClocks )
      ((VecEvent)event).setVectorClock( this.initialVectorClock );
    event.setPartnerLamportClock( -1 );
    event.setType( getEventType( traceType ) );
    // blocking
    int blocking = ( traceType & trcTRUE );
    event.setBlocking( blocking );
    event.setSubType( this.dataInputStream.readUnsignedByte() );
    if( this.source ) {
      event.setSourceFilenameIndex( this.sourceFilenameIndex );
      event.setSourceLineNumber( readInt() );
      if( this.ignoreCounts.get( Integer.valueOf( event.getSourceLineNumber() ) ) != null )
      {
        event.setIgnoreCount( this.ignoreCounts.get( Integer.valueOf( event.getSourceLineNumber() ) )
          .intValue() + 1 );
        this.ignoreCounts.put( Integer.valueOf( event.getSourceLineNumber() ),
                               Integer.valueOf( event.getIgnoreCount() ) );
      } else {
        this.ignoreCounts.put( Integer.valueOf( event.getSourceLineNumber() ),
                               Integer.valueOf( 0 ) );
        event.setIgnoreCount( 0 );
      }
      // source tab
      this.dataInputStream.skipBytes( 1 );
    }
    if( this.dataAmount == trcMedium || this.dataAmount == trcMaximum ) {
      event.setPhysicalStartClock( readInt() );
    }
    if( this.dataAmount == trcMaximum ) {
      event.setSupposedPartnerProcess( readInt() );
      event.setSupposedMessageType( readInt() );
      event.setSupposedMessageLength( readInt() );
    }
    event.setAcceptedPartnerProcess( readInt() );
    event.setAcceptedMessageType( readInt() );
    event.setAcceptedMessageLength( readInt() );
    readInt();
//    int logicalClock =
//    if( logicalClock != this.previousLogicalClock ) {
//      System.out.println( "logical clock mismatch: "
//                          + logicalClock
//                          + " "
//                          + this.previousLogicalClock );
//    }
    event.setPartnerLogicalClock( readInt() );
    if( this.dataAmount == trcMaximum ) {
      event.setPhysicalStopClock( readInt() );
    }
    // this.previousLogicalClock = tmpEvent.getLogicalClock();
    return event;
  }

  private void readSourceEvent() throws IOException {
    int len = this.dataInputStream.readUnsignedByte();
    byte[] srcfile = new byte[ len ];
    this.dataInputStream.read( srcfile );
    String sourceFilename = new String( srcfile );
    this.sourceFilenameIndex = addSourceFilename( sourceFilename );
  }

  private Event readTraceEvent() throws IOException {
    Event event = null;
    byte subtype = this.dataInputStream.readByte();
    if( subtype == trcTVarInsp ) {
      Activator.logMessage( IStatus.WARNING, "subtype trcTVarInsp not supported" ); //$NON-NLS-1$
    } else if( subtype == trcTFlush ) {
      Activator.logMessage( IStatus.WARNING, "subtype trcTFlush not supported" ); //$NON-NLS-1$
    } else if( subtype == trcTOnOff ) {
      event = readEventSubTypeTOnOff();
    } else if( subtype == trcTTimer ) {
      Activator.logMessage( IStatus.WARNING, "subtype trcTTimer not supported" ); //$NON-NLS-1$
    } else if( subtype == trcTMsgQueue ) {
      Activator.logMessage( IStatus.WARNING, "subtype trcTMsgQueue not supported" ); //$NON-NLS-1$
    } else if( subtype == trcTTestLoop ) {
      Activator.logMessage( IStatus.WARNING, "subtype trcTTestLoop not supported" ); //$NON-NLS-1$
    } else if( subtype == trcTArrInsp ) {
      event = readEventSubTypeTArrInsp();
    }
    return event;
  }

  @SuppressWarnings("unused")
  private Event readEventSubTypeTOnOff() throws IOException {
    // TODO better support for this event type
    this.previousLogicalClock++;
    this.setMaximumLogicalClock( this.previousLogicalClock );
    Event event;
    if( this.supportsVectorClocks ) event = new VecEvent( this.previousLogicalClock, this );
    else event = new Event( this.previousLogicalClock, this );
    event.setLamportClock( -1 );
    if( this.supportsVectorClocks )
      ((VecEvent)event).setVectorClock( this.initialVectorClock );
    event.setPartnerLamportClock( -1 );
    event.setType( EventType.OTHER );
    event.setAcceptedPartnerProcess( this.processId );
    int trcSrcLine = -1;
    int trcSrcTab = -1;
    if( this.source ) {
      trcSrcLine = readInt();
      trcSrcTab = this.dataInputStream.readByte();
    }
    int timeStart = -1;
    // only guessed that it is the same way as with the other events
    // (dataamount)
    if( this.dataAmount == trcMedium || this.dataAmount == trcMaximum ) {
      timeStart = readInt();
    }
    this.dataInputStream.skipBytes( 5 );
    int size = readInt();
    int noe = readInt();
    int timeStop = readInt();
    this.dataInputStream.readUnsignedByte();
    this.dataInputStream.skip( 4 );
    return event;
  }

  @SuppressWarnings("unused")
  private Event readEventSubTypeTArrInsp() throws IOException {
    // TODO better support for this event type
    this.previousLogicalClock++;
    ArrayEvent event = new ArrayEvent( this.previousLogicalClock, this );
    event.setLamportClock( -1 );
    event.setPartnerLamportClock( -1 );
    event.setType( EventType.OTHER );
    event.setAcceptedPartnerProcess( this.processId );
    if( this.source ) {
      event.setSourceLineNumber( readInt() );
      this.dataInputStream.skipBytes( 1 );
    }
    if( this.dataAmount == trcMedium || this.dataAmount == trcMaximum ) {
      event.setPhysicalStartClock( readInt() );
    }
    this.dataInputStream.skipBytes( 5 );
    int size = readInt();
    int noe = readInt();
    event.setPhysicalStopClock( readInt() );
    int elemType = this.dataInputStream.readUnsignedByte();
    int elemSize = readInt();
    int numDims = readInt();
    int numPADims = readInt();
    Vector<Integer> arrSize = new Vector<Integer>();
    for( int v = 0; v < numDims; v++ ) {
      arrSize.add( Integer.valueOf( readInt() ) );
    }
    Vector<Integer> paSize = new Vector<Integer>();
    for( int v = 0; v < numDims; v++ ) {
      paSize.add( Integer.valueOf( readInt() ) );
    }
    Vector<Integer> dist = new Vector<Integer>();
    for( int v = 0; v < numDims; v++ ) {
      dist.add( Integer.valueOf( readInt() ) );
    }
    Vector<Integer> remainder = new Vector<Integer>();
    for( int v = 0; v < numDims; v++ ) {
      remainder.add( Integer.valueOf( readInt() ) );
    }
    StringBuffer test = new StringBuffer();
    char b = 0;
    do {
      b = ( char )this.dataInputStream.readByte();
      test.append( b );
    } while( b != 0 );
    StringBuffer test2 = new StringBuffer();
    do {
      b = ( char )this.dataInputStream.readByte();
      test2.append( b );
    } while( b != 0 );
    return event;
  }

  /**
   * Converts event type from TrcUtil constant to {@link EventType} enum
   *
   * @param type TrcUtil constant.
   * @return EventType matching the TrcUtil constant.
   */
  private EventType getEventType( final int type ) {
    EventType evType = null;
    if( type == 1 )
      evType = EventType.RECV;
    else if( type == 2 )
      evType = EventType.SEND;
    else if( type == 3 )
      evType = EventType.TEST;
    else if( type == 4 )
      evType = EventType.OTHER;
    return evType;
  }
}
