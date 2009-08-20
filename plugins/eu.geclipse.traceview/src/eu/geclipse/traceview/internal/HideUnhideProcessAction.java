package eu.geclipse.traceview.internal;

import eu.geclipse.traceview.IProcess;

public class HideUnhideProcessAction extends AbstractProcessAction {
  @Override
  void performAction( AbstractGraphVisualization vis, IProcess[] procs ) {
    boolean[] hideProcesses = vis.getHideProcess();
    for( int i = 0; i < procs.length; i++ ) {
      hideProcesses[ procs[ i ].getProcessId() ] ^= true;
    }
    vis.setHideProcess( hideProcesses );
  }
}
