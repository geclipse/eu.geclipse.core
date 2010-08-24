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
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.eventgraph.tracereader.otf.preferences.PreferenceConstants;
import eu.geclipse.eventgraph.tracereader.otf.util.Node;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceReader;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;
import eu.geclipse.traceview.utils.ClockCalculator;

/**
 * Reader for the Open Trace Format (OTF)
 */
@SuppressWarnings("boxing")
public class OTFReader extends AbstractTraceFileCache implements IPhysicalTrace, ILamportTrace, ITraceReader {

  private static final String PROP_TRACE_VERSION = "OTF.TraceVersion"; //$NON-NLS-1$
  private static final String PROP_TRACE_CODENAME= "OTF.TraceCodename"; //$NON-NLS-1$
  private static final String PROP_TRACE_CREATOR= "OTF.TraceCreator"; //$NON-NLS-1$
  private static final String PROP_TRACE_PROCESSGROUPS= "OTF.TraceProcessGroups"; //$NON-NLS-1$
  private static final String PROP_TRACE_FUNCTIONGROUPS= "OTF.TraceFunctionGroups"; //$NON-NLS-1$
  
  private static IPropertyDescriptor[] otfTracePropertyDescriptors = new IPropertyDescriptor[]{
    new PropertyDescriptor( PROP_TRACE_VERSION, "Version" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_TRACE_CODENAME, "Codename" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_TRACE_CREATOR, "Creator" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_TRACE_PROCESSGROUPS, "Process Groups" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_TRACE_FUNCTIONGROUPS, "Function Groups" ), //$NON-NLS-1$
  };
  
  private BufferedReader input;
  
  private int numProcs = 0;
  
  // Mapping of OTF Ids
  private Map<Integer, List<Integer>> streamMap;
  private Map<Integer, Integer> processIdMap;
  
  private Map<Integer, Integer>[] entered;
  private Process[] processes;
  private String filenameBase;
  private String filename;
  private OTFUtils otfUtils = new OTFUtils();
  private OTFDefinitionReader otfDefinitionReader;
  private Node[] nodes;
  
  /**
   * Creates a new Trace Reader
   */
  public OTFReader() {
    this.streamMap = new HashMap<Integer, List<Integer>>();
    this.processIdMap = new HashMap<Integer, Integer>();
    this.processIdMap.put( 0, -1 ); // OTFs 0 is Trace Viewers -1
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.utils.AbstractTrace#getPropertyDescriptors()
   */
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] abstractPropertyDescriptors = super.getPropertyDescriptors();
    IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[ abstractPropertyDescriptors.length + otfTracePropertyDescriptors.length ];
    System.arraycopy( abstractPropertyDescriptors, 0, propertyDescriptors, 0, abstractPropertyDescriptors.length );
    System.arraycopy( otfTracePropertyDescriptors, 0, propertyDescriptors, abstractPropertyDescriptors.length, otfTracePropertyDescriptors.length );
    return propertyDescriptors;
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.utils.AbstractTrace#getPropertyValue(java.lang.Object)
   */
  @Override
  public Object getPropertyValue( final Object id ) {
    Object result = null;
    if( PROP_TRACE_VERSION.equals( id ) ) {
      result = this.otfDefinitionReader.getVersion();
    } else if( PROP_TRACE_CODENAME.equals( id ) ) {
      result = this.otfDefinitionReader.getCodename();
    } else if( PROP_TRACE_CREATOR.equals( id ) ) {
      result = this.otfDefinitionReader.getCreator();
    } else if( PROP_TRACE_FUNCTIONGROUPS.equals( id ) ) {
      String[] functionGroupNames = this.otfDefinitionReader.getFunctionGroupNames();
      StringBuffer functionGroupNameString = new StringBuffer();
      for(int i = 0; i<functionGroupNames.length; i++){
        functionGroupNameString.append( functionGroupNames[i] + ", " ); //$NON-NLS-1$
      }
      result = functionGroupNameString.toString(); 
    } else if( PROP_TRACE_PROCESSGROUPS.equals( id ) ) {
      String[] processGroupNames = this.otfDefinitionReader.getProcessGroupNames();
      StringBuffer processGroupNameString = new StringBuffer();
      for(int i = 0; i<processGroupNames.length; i++){
        processGroupNameString.append( processGroupNames[i] + ", " ); //$NON-NLS-1$
      }
      result = processGroupNameString.toString(); 
    } else {
      result = super.getPropertyValue( id );
    }
    return result;
  }

  public ITrace openTrace( final IPath tracePath, final IProgressMonitor monitor ) throws IOException {
    File file = tracePath.toFile();
    long modTime = file.lastModified();
    this.input = new BufferedReader( new FileReader( file ) );
    this.filenameBase = file.getAbsolutePath().substring( 0, file.getAbsolutePath().length() - 4 );
    this.filename = file.getName();
    readOTFMapping( monitor );
    this.otfDefinitionReader = new OTFDefinitionReader( new File( this.filenameBase + ".0.def" )); //$NON-NLS-1$
    this.nodes = new Node[this.numProcs];
    Event.addIds( this,this.otfDefinitionReader );
    String traceOptions = ""; //$NON-NLS-1$
    if( Activator.getDefault().getPreferenceStore().getBoolean( PreferenceConstants.readFunctions ) ) {
      traceOptions += "readFunctions"; //$NON-NLS-1$
    }
    boolean hasCache = openCacheDir( file.getAbsolutePath(), traceOptions, modTime );
    if( !readOTFData( hasCache, monitor ) )
      return null;
    enableMemoryMap();
    if( !hasCache ) {
      if( monitor.isCanceled() )
        return null;
      monitor.subTask( "Calculating logical clocks" );
      ClockCalculator.calcPartnerLogicalClocks( this );
      if( monitor.isCanceled() )
        return null;
      monitor.subTask( "Calculating lamport clocks" );
      ClockCalculator.calcLamportClock(this, new NullProgressMonitor());
      saveCacheMetadata();
    }
    return this;
  }

  private void readOTFMapping( final IProgressMonitor monitor ) throws IOException {
    while( ( this.otfUtils.line = this.input.readLine() ) != null ) {
      this.otfUtils.lineIdx = 0;
      parseStreamFileDef();
    }
    this.processes = new Process[ this.numProcs ];
    for( int i = 0; i < this.numProcs; i++ ) {
      this.processes[ i ] = new Process( i, this );
    }
  }

  private boolean readOTFData( final boolean hasCache, final IProgressMonitor monitor ) throws IOException {
    if( !hasCache ) {
      Set<Integer> streamNrs = this.streamMap.keySet();
      monitor.beginTask( "Loading trace", streamNrs.size() );
      for( Integer streamNr : streamNrs ) {
        if( monitor.isCanceled() )
          return false;
        monitor.subTask( "Loading stream " + streamNr );
        loadStream( streamNr.intValue() );
        monitor.worked( 1 );
      }
    }
    return true;
  }

  private void loadStream( final int nr ) throws IOException {
    String eventsFilename = this.filenameBase + '.' + Integer.toHexString( nr ) + ".events"; //$NON-NLS-1$
    OTFStreamReader otfStreamReader = new OTFStreamReader( new File( eventsFilename ), this, this.entered );
    otfStreamReader.readStream();
    nodes[0] = otfStreamReader.getNode();
    //rec( nodes[0].getChildren()[0] );
  }
  
  public Node getRootNode(){
    return this.nodes[0];
  }
  
  @SuppressWarnings("unchecked")
  private void parseStreamFileDef() {
    Integer streamNr = Integer.valueOf( this.otfUtils.readNumber() );
    this.otfUtils.checkChar( ':' );
    do {
      Integer processNr = Integer.valueOf( this.otfUtils.readNumber() );
      List<Integer> processList = this.streamMap.get( streamNr );
      if( processList == null ) {
        processList = new LinkedList<Integer>();
        this.streamMap.put( streamNr, processList );
      }
      processList.add( processNr );
      this.processIdMap.put( processNr, Integer.valueOf( this.numProcs ) );
      this.numProcs++;
    } while( this.otfUtils.read() == ',' );
    this.entered = new HashMap[ this.numProcs ];
    for( int i = 0; i < this.entered.length; i++ ) {
      this.entered[ i ] = new HashMap<Integer, Integer>();
    }
  }

  public int getNumberOfProcesses() {
    return this.numProcs;
  }

  public IProcess getProcess( final int processId ) throws IndexOutOfBoundsException {
    return this.processes[ processId ];
  }

  int getProcessIdForOTFIndex( final int processId ) {
    return this.processIdMap.get( Integer.valueOf( processId ) ).intValue();
  }

  Process getProcessTraceForOTFIndex( final int processId ) throws IndexOutOfBoundsException {
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
      if( maxTimeStop < ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() ) ) {
        maxTimeStop = ( ( ( IPhysicalEvent )process.getEventByLogicalClock( process.getMaximumLogicalClock() ) ).getPhysicalStopClock() );
      }
    }
    return maxTimeStop;
  }

  public IPath getPath() {
    return null;
  }

  public String getFunctionName( final int functionId ) {
    return this.otfDefinitionReader.getFunctionName( functionId );
  }
  
  public OTFDefinitionReader getDefinition(){
    return this.otfDefinitionReader;
  }
  
  public int estimateMaxLogicalClock() {
    return Integer.MAX_VALUE;
  }
}
