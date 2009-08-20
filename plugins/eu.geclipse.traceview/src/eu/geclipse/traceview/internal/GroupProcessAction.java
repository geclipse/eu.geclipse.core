package eu.geclipse.traceview.internal;

import eu.geclipse.traceview.IProcess;

public class GroupProcessAction extends AbstractProcessAction {
  @Override
  void performAction( AbstractGraphVisualization vis, IProcess[] procs ) {
    int firstLineId = procs[ 0 ].getProcessId();
    int[] mapping = vis.getProcessToLineMapping();
    for( int i = 0; i < procs.length; i++ ) {
      mapping[ procs[ i ].getProcessId() ] = firstLineId;
    }
    vis.setProcessToLineMapping( mapping );
    vis.removeEmptyLines();
  }
}
