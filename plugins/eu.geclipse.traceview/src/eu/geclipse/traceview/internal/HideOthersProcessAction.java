package eu.geclipse.traceview.internal;

import eu.geclipse.traceview.IProcess;

public class HideOthersProcessAction extends AbstractProcessAction {
  @Override
  void performAction( AbstractGraphVisualization vis, IProcess[] procs ) {
    boolean[] hideProcesses = vis.getHideProcess();
    boolean[] procSelected = new boolean[hideProcesses.length];
    for( int i = 0; i < procs.length; i++ ) {
      procSelected[ procs[ i ].getProcessId() ] = true;
    }
    for( int i = 0; i < procSelected.length; i++ ) {
      hideProcesses[i] |= !procSelected[i];
    }
    vis.setHideProcess( hideProcesses );
  }
}
