package eu.geclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;

public class EmptySelection implements ISelection {
  
  public EmptySelection() {
    // empty implementation
  }

  public boolean isEmpty() {
    return true;
  }
  
}
