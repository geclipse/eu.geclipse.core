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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceReader;
import eu.geclipse.traceview.nope.Activator;
import eu.geclipse.traceview.nope.preferences.PreferenceConstants;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;
import eu.geclipse.traceview.utils.ClockCalculator;
import eu.geclipse.traceview.utils.LamportEventComparator;

/**
 * NOPE (NOndeterministic Program Evaluator) Trace.
 */
public class Trace extends AbstractTraceFileCache
  implements ILamportTrace, IPhysicalTrace, ITraceReader {
  private String tracedir;
  private Process[] processes;
  private int maximumLamportClock = 0;
  private boolean supportsVectorClocks;
  private int estimatedMaxLogClock;
  
  private void updatePartnerClocks(final IProgressMonitor monitor) {
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
      if (monitor.isCanceled()) return;
      monitor.worked( 1 );
    }
  }

  private void updateVectorClocks() {
    TreeSet<VecEvent> events = new TreeSet<VecEvent>( new LamportEventComparator() );
    try {
      // set to first event
      for( int i = 0; i < this.processes.length; i++ ) {
        events.add( (VecEvent) this.processes[ i ].getEventByLogicalClock( 0 ) );
      }
      while( !events.isEmpty() ) {
        VecEvent first = events.first();
        VecEvent next = ( VecEvent )first.getNextEvent();
        // increment local clock;
        int[] vectorClock = first.getVectorClock();
        vectorClock[ first.getProcessId() ]++;
        first.setVectorClock( vectorClock );
        // pass vector clock to partner
        if( first.getType() == EventType.SEND
            && first.getSubType() != EventSubtype.MPI_BCAST )
        {
          VecEvent partner = ( VecEvent )first.getPartnerEvent();
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
          VecEvent partner = ( VecEvent )first.getPartnerEvent();
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
  public ITrace openTrace( final IPath trace, final IProgressMonitor monitor ) throws IOException {
    int numProcs;
    long maxFileLen = 0;
    long modTime;
    ZipFile zipFile = null;
    List fileList = new LinkedList();
    if( trace.lastSegment().endsWith( ".zip" ) ) {
      this.tracePath = trace;
      File file = this.tracePath.toFile();
      zipFile = new ZipFile( file );
      Enumeration entries = zipFile.entries();
      numProcs = zipFile.size(); // currently no other files are allowed in the zip file
      while( entries.hasMoreElements() ) {
        ZipEntry entry = ( ZipEntry )entries.nextElement();
        long len = entry.getSize();
        if( len > maxFileLen )
          maxFileLen = len;
        fileList.add( entry );
      }
      modTime = file.lastModified();
    } else {
      this.tracePath = trace.removeLastSegments( 1 );
      File dir = this.tracePath.toFile();
      this.tracedir = this.tracePath.toPortableString();
      File[] files = dir.listFiles( new FileFilter() {
        public boolean accept( final File file ) {
          return !file.isDirectory() && file.getName().startsWith( "trace" ) //$NON-NLS-1$
                 && file.getName().charAt( file.getName().length() - 4 ) == '.';
        }
      } );
      Arrays.sort( files );
      numProcs = files.length;
      modTime = dir.lastModified();
      for (File file : files) {
        long len = file.length();
        if (len > maxFileLen) maxFileLen = len;
        if (file.lastModified() > modTime) modTime = file.lastModified();
        fileList.add(file);
      }
    }
    this.processes = new Process[ numProcs ];
    this.estimatedMaxLogClock = (int) (maxFileLen / 47);
    Event.addIds( this );
    
    String traceOptions = "";
    this.supportsVectorClocks = Activator.getDefault()
      .getPreferenceStore()
      .getBoolean( PreferenceConstants.vectorClocks );
    if( supportsVectorClocks() ) {
      VecEvent.addIds( this );
      traceOptions += "vectorClocks ";
    }
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
    monitor.beginTask( "Loading trace data", this.processes.length * 3 );
    monitor.subTask( "Opening trace cache" );
    boolean hasCache = openCacheDir( this.tracePath.toFile().getAbsolutePath(), traceOptions, modTime );
    for( Object fileObj : fileList ) {
      if( monitor.isCanceled() )
        return null;
      String filename;
      long filesize;
      InputStream inputStream;
      if( fileObj instanceof ZipEntry ) {
        ZipEntry zipEntry = ( ZipEntry )fileObj;
        filename = zipEntry.getName();
        filesize = zipEntry.getSize();
        inputStream = zipFile.getInputStream( zipEntry );
      } else {
        File file = ( File )fileObj;
        filename = file.getName();
        filesize = file.length();
        File inputFile = new File( this.tracedir, filename );
        inputStream = new FileInputStream( inputFile );
      }
      int traceProc = Integer.parseInt( filename.substring( filename.length() - 3 ) );
      monitor.subTask( "Loading process " + traceProc );
      Process processTrace = new Process( inputStream, filename, filesize, traceProc, hasCache, this );
      this.processes[ traceProc ] = processTrace;
      monitor.worked( 1 );
    }
    enableMemoryMap();
    if( !hasCache ) {
      if (monitor.isCanceled()) return null;
      monitor.subTask( "Updating logical clocks" );
      updatePartnerClocks(monitor);
      if (monitor.isCanceled()) return null;
      monitor.subTask( "Calculating lamport clocks" );
      ClockCalculator.calcLamportClock( this, monitor );
      if (monitor.isCanceled()) return null;
      if( this.supportsVectorClocks ) {
        monitor.subTask( "Calculating vector clocks" );
        updateVectorClocks();
        if (monitor.isCanceled()) return null;
      }
      saveCacheMetadata();
    }
    for( Process process : this.processes ) {
      if( this.maximumLamportClock < process.getMaximumLamportClock() )
        this.maximumLamportClock = process.getMaximumLamportClock();
    }
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
    int maxTimeStop = 0;
    for( Process process : this.processes ) {
      if( maxTimeStop  < ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() ) ) {
        maxTimeStop = ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() );
      }
    }
    return maxTimeStop;
  }

  protected boolean supportsVectorClocks() {
    return this.supportsVectorClocks;
  }
  
  @Override
  public int estimateMaxLogicalClock() {
    return this.estimatedMaxLogClock;
  }
}
