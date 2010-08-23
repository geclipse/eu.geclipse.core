package eu.geclipse.traceview.nope.tracereader;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import eu.geclipse.traceview.IVectorEvent;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;


public class VecEvent extends Event implements IVectorEvent {
  private final static int vectorClockOffset = 17;
  private static IPropertyDescriptor[] descriptors;

  public VecEvent( final int logicalClock, final Process processCache ) {
    super( logicalClock, processCache );
  }

  static void addIds(AbstractTraceFileCache cache) {
    cache.addEntry( vectorClockOffset, cache.getNumberOfProcesses(), cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() )+3, true ); // TODO find a way to estimate value or change to Integer.MAX_VALUE
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if( descriptors == null ) {
      descriptors = buildPropertyDescriptors();
    }
    return descriptors;
  }

  // *****************************************************
  // * IVectorEvent
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IVectorEvent#getVectorClock()
   */
  public int[] getVectorClock() {
    int[] result = new int[ getProcess().getTrace().getNumberOfProcesses() ];
    this.process.readArray( this.logicalClock, vectorClockOffset, result );
    return result;
  }

  protected void setVectorClock( final int[] src ) {
    this.process.writeArray( this.logicalClock, vectorClockOffset, src );
  }
}
