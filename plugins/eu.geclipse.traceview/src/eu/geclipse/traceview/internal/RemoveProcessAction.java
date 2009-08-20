package eu.geclipse.traceview.internal;

import eu.geclipse.traceview.IProcess;

public class RemoveProcessAction extends AbstractProcessAction {
  @Override
  void performAction( AbstractGraphVisualization vis, IProcess[] procs ) {
    int[] mapping = vis.getProcessToLineMapping();
    for (int i = 0; i < procs.length; i++) {
      mapping[procs[i].getProcessId()] = -1;
    }
    vis.setProcessToLineMapping( mapping );
    vis.removeEmptyLines();
  }
}
