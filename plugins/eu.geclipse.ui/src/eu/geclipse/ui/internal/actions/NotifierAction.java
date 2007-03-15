package eu.geclipse.ui.internal.actions;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.Action;

public abstract class NotifierAction extends Action {

  private ListenerList aListeners
    = new ListenerList();
  
  protected NotifierAction( final String text ) {
    super( text );
  }

  public void addActionListener( final IActionListener listener ) {
    this.aListeners.add( listener );
  }
  
  public void removeActionListener( final IActionListener listener ) {
    this.aListeners.remove( listener );
  }
  
  @Override
  public void run() {
    firePreRunEvent();
    internalRun();
    firePostRunEvent();
  }
  
  protected abstract void internalRun();
  
  private void firePreRunEvent() {
    for ( Object listener : this.aListeners.getListeners() ) {
      ( ( IActionListener ) listener ).actionAboutToRun();
    }
  }
  
  private void firePostRunEvent() {
    for ( Object listener : this.aListeners.getListeners() ) {
      ( ( IActionListener ) listener ).actionFinished();
    }
  }
   
}
