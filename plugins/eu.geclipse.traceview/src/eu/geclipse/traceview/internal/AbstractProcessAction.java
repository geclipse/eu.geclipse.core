package eu.geclipse.traceview.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.TraceVisualization;


public abstract class AbstractProcessAction extends Action implements IActionDelegate {
  private Object[] selectedObjs;

  public void run( final IAction action ) {
    boolean allProcs = true;
    for (Object obj : this.selectedObjs) {
      if (!(obj instanceof IProcess)) allProcs = false;
    }
    if (!allProcs) {
      ErrorDialog.openError( Display.getDefault().getActiveShell(),
                             "Error", "This action can only be performed on processes",
                             new Status(IStatus.ERROR, Activator.PLUGIN_ID, "This action can only be performed on processes"));
    } else {
      try {
        IProcess[] procs = new IProcess[this.selectedObjs.length];
        for (int i = 0; i < procs.length; i++) {
          procs[i] = ( IProcess )this.selectedObjs[i];
        }
        ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage()
          .showView( "eu.geclipse.traceview.views.TraceView" );
        TraceVisualization vis = traceView.getVisualisationForTrace(((IProcess)this.selectedObjs[0]).getTrace());
        if (vis instanceof AbstractGraphVisualization) {
          AbstractGraphVisualization graphVis = (AbstractGraphVisualization) vis;
          performAction( graphVis, procs );
        }
      } catch( PartInitException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  abstract void performAction(AbstractGraphVisualization vis, IProcess[] procs);

  public void selectionChanged( final IAction action, final ISelection selection ) {
    if( selection instanceof StructuredSelection ) {
      StructuredSelection structuredSelection = ( StructuredSelection )selection;
      this.selectedObjs = structuredSelection.toArray();
    }
  }
}
