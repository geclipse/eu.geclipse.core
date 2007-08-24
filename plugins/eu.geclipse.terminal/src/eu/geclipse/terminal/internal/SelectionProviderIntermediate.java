package eu.geclipse.terminal.internal;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * IPostSelectionProvider implementation that delegates to another
 * ISelectionProvider or IPostSelectionProvider. The selection provider used
 * for delegation can be exchanged dynamically. Registered listeners are
 * adjusted accordingly. This utility class may be used in workbench parts with
 * multiple viewers.
 * 
 * From the article "Eclipse Workbench: Using the Selection Service" at
 * http://www.eclipse.org/articles/Article-WorkbenchSelections/article.html
 * 
 * @author Marc R. Hoffmann
 */
public class SelectionProviderIntermediate implements IPostSelectionProvider {

  ISelectionProvider delegate;

  private final ListenerList selectionListeners = new ListenerList();

  private final ListenerList postSelectionListeners = new ListenerList();

  private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
    public void selectionChanged( final SelectionChangedEvent event ) {
      if (event.getSelectionProvider() == SelectionProviderIntermediate.this.delegate) {
        fireSelectionChanged(event.getSelection());
      }
    }
  };

  private ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
    public void selectionChanged( final SelectionChangedEvent event ) {
      if (event.getSelectionProvider() == SelectionProviderIntermediate.this.delegate) {
        firePostSelectionChanged(event.getSelection());
      }
    }
  };

  /**
   * Sets a new selection provider to delegate to. Selection listeners
   * registered with the previous delegate are removed before. 
   * 
   * @param newDelegate new selection provider
   */
  public void setSelectionProviderDelegate( final ISelectionProvider newDelegate ) {
    if (this.delegate != null) {
      this.delegate.removeSelectionChangedListener(this.selectionListener);
      if (this.delegate instanceof IPostSelectionProvider) {
        ((IPostSelectionProvider)this.delegate).removePostSelectionChangedListener(this.postSelectionListener);
      }
    }
    this.delegate = newDelegate;
    if (newDelegate != null) {
      newDelegate.addSelectionChangedListener(this.selectionListener);
      if (newDelegate instanceof IPostSelectionProvider) {
        ((IPostSelectionProvider)newDelegate).addPostSelectionChangedListener(this.postSelectionListener);
      }
      fireSelectionChanged(newDelegate.getSelection());
    }
  }
  
  protected void fireSelectionChanged( final ISelection selection ) {
    fireSelectionChanged(this.selectionListeners, selection);
  }

  protected void firePostSelectionChanged( final ISelection selection ) {
    fireSelectionChanged(this.postSelectionListeners, selection);
  }
  
  private void fireSelectionChanged( final ListenerList list, final ISelection selection ) {
    SelectionChangedEvent event = new SelectionChangedEvent(this.delegate, selection);
    Object[] listeners = list.getListeners();
    for (int i = 0; i < listeners.length; i++) {
      ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
      listener.selectionChanged(event);
    }
  }

  // IPostSelectionProvider Implementation

  public void addSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.selectionListeners.add(listener);
  }

  public void removeSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.selectionListeners.remove(listener);
  }

  public void addPostSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.postSelectionListeners.add(listener);
  }

  public void removePostSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.postSelectionListeners.remove(listener);
  }

  public ISelection getSelection() {
    return this.delegate == null ? null : this.delegate.getSelection();
  }

  public void setSelection( final ISelection selection ) {
    if (this.delegate != null) {
      this.delegate.setSelection(selection);
    }
  }
}
