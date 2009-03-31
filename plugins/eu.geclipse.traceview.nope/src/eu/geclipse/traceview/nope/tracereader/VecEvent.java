package eu.geclipse.traceview.nope.tracereader;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import eu.geclipse.traceview.IVectorEvent;


public class VecEvent extends Event implements IVectorEvent {
  private final static int vectorClockOffset = 17;
  private static IPropertyDescriptor[] descriptors;

  public VecEvent( final int logicalClock, final Process processCache ) {
    super( logicalClock, processCache );
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
    this.processCache.getBuffer().position( this.logicalClock
                                            * getSize()
                                            / 4
                                            + VecEvent.vectorClockOffset );
    this.processCache.getBuffer().get( result );
    return result;
  }

  protected void setVectorClock( final int[] src ) {
    this.processCache.getBuffer().position( this.logicalClock
                                            * getSize()
                                            / 4
                                            + VecEvent.vectorClockOffset );
    this.processCache.getBuffer().put( src );
  }
}
