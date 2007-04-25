package eu.geclipse.ui.internal.wizards.jobs;

import java.util.Properties;


public class DataStageData {
  
  private Properties valueVsURI;
  
  private String argName;

  public DataStageData( final String argName, final String value, final String URI ){
    this.argName = argName;
    this.valueVsURI = new Properties();
    this.valueVsURI.setProperty( value, URI );
  }
  
  public String getArgName() {
    return this.argName;
  }

  public Properties getValuesAndURIs() {
    return this.valueVsURI;
  }
}
