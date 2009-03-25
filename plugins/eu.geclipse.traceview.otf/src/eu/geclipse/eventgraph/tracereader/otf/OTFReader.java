package eu.geclipse.eventgraph.tracereader.otf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceReader;
import eu.geclipse.traceview.utils.AbstractTrace;
import eu.geclipse.traceview.utils.ClockCalculator;

public class OTFReader extends AbstractTrace
  implements IPhysicalTrace, ILamportTrace, ITraceReader
{

  private BufferedReader input;
  private Map<Integer, List<Integer>> streamMap = new HashMap<Integer, List<Integer>>();
  private Map<Integer, Integer> processIdMap = new HashMap<Integer, Integer>();
  private int numProcs = 0;
  private Process[] processes;
  private String filenameBase;
  private String filename;
  private OTFUtils otfUtils = new OTFUtils();

  public ITrace openTrace( final IPath tracePath, final IProgressMonitor monitor ) throws IOException {
    File file = tracePath.toFile();
    this.input = new BufferedReader( new FileReader( file ) );
    this.filenameBase = file.getAbsolutePath()
      .substring( 0, file.getAbsolutePath().length() - 4 );
    this.filename = file.getName();
    readOTF( monitor );
    for( Process process : this.processes ) {
      process.doneReading();
    }
    monitor.subTask( "Calculating logical clocks" );
    ClockCalculator.calcPartnerLogicalClocks(this);
    monitor.subTask( "Calculating lamport clocks" );
    ClockCalculator.calcLamportClock(this);
    return this;
  }

  private void readOTF( final IProgressMonitor monitor ) throws IOException {
    while( ( this.otfUtils.line = this.input.readLine() ) != null ) {
      this.otfUtils.lineIdx = 0;
      parseStreamFileDef();
    }
    this.processes = new Process[ this.numProcs ];
    for( int i = 0; i < this.numProcs; i++ ) {
      this.processes[ i ] = new Process( i, this );
    }
    Set<Integer> streamNrs = this.streamMap.keySet();
    monitor.beginTask( "Loading trace", streamNrs.size() );
    for( Integer streamNr : streamNrs ) {
      monitor.subTask( "Loading stream " + streamNr );
      loadStream( streamNr.intValue() );
      monitor.worked( 1 );
    }
  }

  private void loadStream( final int nr ) throws IOException {
    String eventsFilename = this.filenameBase + '.' + Integer.toHexString( nr ) + ".events"; //$NON-NLS-1$
    new OTFStreamReader( new File( eventsFilename ), this );
  }

  private void parseStreamFileDef() {
    Integer streamNr = new Integer( this.otfUtils.readNumber() );
    this.otfUtils.checkChar( ':' );
    do {
      Integer processNr = new Integer( this.otfUtils.readNumber() );
      List<Integer> processList = this.streamMap.get( streamNr );
      if( processList == null ) {
        processList = new LinkedList<Integer>();
        this.streamMap.put( streamNr, processList );
      }
      processList.add( processNr );
      this.processIdMap.put( processNr, new Integer( this.numProcs ) );
      this.numProcs++;
    } while( this.otfUtils.read() == ',' );
  }

  public int getNumberOfProcesses() {
    return this.numProcs;
  }

  public IProcess getProcess( final int processId )
    throws IndexOutOfBoundsException
  {
    return this.processes[ processId ];
  }

  int getProcessIdForOTFIndex( final int processId ) {
    return this.processIdMap.get( new Integer( processId ) ).intValue();
  }

  Process getProcessTraceForOTFIndex( final int processId )
    throws IndexOutOfBoundsException
  {
    return this.processes[ getProcessIdForOTFIndex( processId ) ];
  }

  public int getMaximumLamportClock() {
    int result = 0;
    for( ILamportProcess proc : this.processes ) {
      int numEvents = proc.getMaximumLamportClock();
      if( numEvents > result ) {
        result = numEvents;
      }
    }
    return result;
  }

  public String getName() {
    return this.filename;
  }


  public int getMaximumPhysicalClock() {
    int maxTimeStop = 0;
    for( Process process : this.processes ) {
      if( maxTimeStop < ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() ) )
      {
        maxTimeStop = ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() );
      }
    }
    return maxTimeStop;
  }

  public IPath getPath() {
    return null;
  }
}
