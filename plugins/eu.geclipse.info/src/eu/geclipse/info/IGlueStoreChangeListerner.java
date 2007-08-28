package eu.geclipse.info;

import java.util.ArrayList;

import eu.geclipse.info.glue.AbstractGlueTable;

public interface IGlueStoreChangeListerner {

  public void infoChanged( final ArrayList<AbstractGlueTable> modifiedGlueEntries );
}
