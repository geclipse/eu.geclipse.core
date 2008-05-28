package eu.geclipse.jsdl.ui.adapters.jsdl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;


public class AppAdapter extends AdapterImpl {
  
  public AppAdapter(){
    //
  }
  
  @Override
  public void notifyChanged(final Notification msg){
    System.out.println(msg.toString());
  }
  
  
}
