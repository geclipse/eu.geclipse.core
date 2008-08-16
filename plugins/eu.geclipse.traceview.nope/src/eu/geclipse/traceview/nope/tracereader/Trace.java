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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceReader;
import eu.geclipse.traceview.nope.Activator;
import eu.geclipse.traceview.nope.preferences.PreferenceConstants;
import eu.geclipse.traceview.utils.AbstractTrace;
import eu.geclipse.traceview.utils.ClockCalculator;
import eu.geclipse.traceview.utils.LamportEventComparator;

/**
 * NOPE (NOndeterministic Program Evaluator) Trace.
 */
public class Trace extends AbstractTrace
  implements ILamportTrace, IPhysicalTrace, ITraceReader
{

  private IPath tracePath;
  private String tracedir;
  private Process[] processes;
  private int maximumLamportClock = 0;
  private int maxTimeStop = 0;
  private List<String> sourceFilenames = new LinkedList<String>();
  private File tmpDir;
  private boolean supportsVectorClocks;

  private void updatePartnerClocks() {
    for( int procId = 0; procId < getNumberOfProcesses(); procId++ ) {
      IProcess process = getProcess( procId );
      for( int i = 0; i <= process.getMaximumLogicalClock(); i++ ) {
        Event event = ( Event )process.getEventByLogicalClock( i );
        if( event.getType() == EventType.RECV ) {
          IProcess partnerProcess = getProcess( event.getPartnerProcessId() );
          Event sendEvent = ( Event )partnerProcess.getEventByLogicalClock( event.getPartnerLogicalClock() );
          sendEvent.setPartnerLogicalClock( event.getLogicalClock() );
        }
      }
    }
  }

  private void updateVectorClocks() {
    TreeSet<Event> events = new TreeSet<Event>( new LamportEventComparator() );
    try {
      // set to first event
      for( int i = 0; i < this.processes.length; i++ ) {
        events.add( this.processes[ i ].getEventByLogicalClock( 0 ) );
      }
      while( !events.isEmpty() ) {
        Event first = events.first();
        Event next = ( Event )first.getNextEvent();
        // increment local clock;
        int[] vectorClock = first.getVectorClock();
        vectorClock[ first.getProcessId() ]++;
        first.setVectorClock( vectorClock );
        // pass vector clock to partner
        if( first.getType() == EventType.SEND
            && first.getSubType() != EventSubtype.MPI_BCAST )
        {
          Event partner = ( Event )first.getPartnerEvent();
          if( partner != null ) {
            int[] partnerVectorClock = partner.getVectorClock();
            // partnerVectorClock[ partner.getProcess() ]++;
            for( int i = 0; i < this.getNumberOfProcesses(); i++ ) {
              if( vectorClock[ i ] > partnerVectorClock[ i ] ) {
                partnerVectorClock[ i ] = vectorClock[ i ];
              }
            }
            partner.setVectorClock( partnerVectorClock );
          }
        } else if( first.getType() == EventType.RECV
                   && first.getSubType() == EventSubtype.MPI_BCAST )
        {
          Event partner = ( Event )first.getPartnerEvent();
          if( partner != null ) {
            int[] partnerVectorClock = partner.getVectorClock();
            for( int i = 0; i < this.getNumberOfProcesses(); i++ ) {
              if( vectorClock[ i ] > partnerVectorClock[ i ] ) {
                partnerVectorClock[ i ] = vectorClock[ i ];
              }
            }
            first.setVectorClock( partnerVectorClock );
          }
        }
        if( next != null ) {
          // pass vector clock to next event
          int[] f = first.getVectorClock();
          int[] n = next.getVectorClock();
          for( int i = 0; i < this.getNumberOfProcesses(); i++ ) {
            if( f[ i ] > n[ i ] ) {
              n[ i ] = f[ i ];
            }
          }
          next.setVectorClock( n );
          // add next event to set
          events.add( next );
        }
        events.remove( first );
      }
    } catch( IndexOutOfBoundsException exception ) {
      Activator.logException( exception );
    }
  }

  /**
   * Opens NOPE trace files.
   *
   * @param trace NOPE trace directory.
   * @throws IOException thrown if a read error occurs.
   */
  public ITrace openTrace( final IPath trace ) throws IOException {
    this.supportsVectorClocks = Activator.getDefault()
      .getPreferenceStore()
      .getBoolean( PreferenceConstants.vectorClocks );
    this.tracePath = trace.removeLastSegments( 1 );
    File dir = this.tracePath.toFile();
    this.tracedir = this.tracePath.toPortableString();
    File[] files = dir.listFiles( new FileFilter() {

      public boolean accept( final File file ) {
        return !file.isDirectory() && file.getName().startsWith( "trace" ) //$NON-NLS-1$
               && file.getName().charAt( file.getName().length() - 4 ) == '.';
      }
    } );
    // part of the workspace ?
    String projectName = ""; //$NON-NLS-1$
    boolean workspace = ResourcesPlugin.getWorkspace()
      .getRoot()
      .getLocation()
      .isPrefixOf( this.tracePath );
    if( workspace ) {
      IPath path = this.tracePath.removeFirstSegments( ResourcesPlugin.getWorkspace()
        .getRoot()
        .getLocation()
        .segmentCount() );
      projectName = path.uptoSegment( 1 ).toPortableString();
      String device = path.getDevice();
      if( device != null ) {
        projectName = projectName.substring( device.length() );
      }
    }
    this.processes = new Process[ files.length ];
    int dirNr = this.tracedir.hashCode();
    this.tmpDir = new File( System.getProperty( "java.io.tmpdir" ), //$NON-NLS-1$
                            "trace_" + Integer.toHexString( dirNr ) ); //$NON-NLS-1$
    boolean reuseFile = this.tmpDir.exists()
                        && new File( this.tracedir ).lastModified() < this.tmpDir.lastModified();
    if( !this.tmpDir.exists() ) {
      this.tmpDir.mkdir();
    }
    File sourceNamesListFile = new File( this.tmpDir, "sourcefiles.txt" ); //$NON-NLS-1$
    if( sourceNamesListFile.exists() ) {
      BufferedReader bufferedReader = new BufferedReader( new FileReader( sourceNamesListFile ) );
      String entry = bufferedReader.readLine();
      while( entry != null ) {
        this.sourceFilenames.add( entry );
        entry = bufferedReader.readLine();
      }
      bufferedReader.close();
    }
    for( File file : files ) {
      String filename = file.getName();
      int traceProc = Integer.parseInt( filename.substring( filename.length() - 3 ) );
      Process processTrace = new Process( new File( this.tracedir, filename ),
                                          traceProc,
                                          reuseFile,
                                          this.tmpDir,
                                          this.sourceFilenames,
                                          projectName,
                                          this );
      this.processes[ traceProc ] = processTrace;
    }
    if( !reuseFile ) {
      updatePartnerClocks();
      ClockCalculator.calcLamportClock( this );
      if( this.supportsVectorClocks )
        updateVectorClocks();
    }
    for( Process process : this.processes ) {
      if( this.maximumLamportClock < process.getMaximumLamportClock() )
        this.maximumLamportClock = process.getMaximumLamportClock();
      if( this.maxTimeStop < ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() ) )
      {
        this.maxTimeStop = ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() );
      }
    }
    BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter( sourceNamesListFile ) );
    for( String entry : this.sourceFilenames ) {
      bufferedWriter.write( entry + '\n' );
    }
    bufferedWriter.close();
    return this;
  }

  public int getNumberOfProcesses() {
    return this.processes.length;
  }

  public IProcess getProcess( final int processId )
    throws IndexOutOfBoundsException
  {
    return this.processes[ processId ];
  }

  public String getName() {
    String name = this.tracePath.removeFirstSegments( 1 ).lastSegment();
    int index = name.indexOf( ".traces" ); //$NON-NLS-1$
    if( index != -1 ) {
      name = name.substring( 0, index );
    }
    return name;
  }

  public int getMaximumLamportClock() {
    return this.maximumLamportClock;
  }

  protected Process[] getProcesses() {
    return this.processes;
  }

  public int getMaximumPhysicalClock() {
    return this.maxTimeStop;
  }


  protected boolean supportsVectorClocks() {
    return this.supportsVectorClocks;
  }

  public IPath getPath() {
    return this.tracePath;
  }
}
