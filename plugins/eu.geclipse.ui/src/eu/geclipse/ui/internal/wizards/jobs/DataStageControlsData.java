package eu.geclipse.ui.internal.wizards.jobs;

import org.eclipse.swt.widgets.Control;


public class DataStageControlsData {
  
  private Control nameControl;
  
  private Control URIControl;
  
  String argName;
  
  private boolean isMultipleList;
  
  public DataStageControlsData( final String argName, final Control nameControl, final Control URIControl ){
    this.argName = argName;
    this.nameControl = nameControl;
    this.URIControl = URIControl;
    if (this.URIControl == null){
      this.isMultipleList = true;
    } else {
      this.isMultipleList = false;
    }
  }

  
  public String getArgName() {
    return this.argName;
  }

  
  public Control getNameControl() {
    return this.nameControl;
  }

  
  public Control getURIControl() {
    return this.URIControl;
  }


  public boolean isMultipleList() {
    return this.isMultipleList;
  }
  
  
  
}
