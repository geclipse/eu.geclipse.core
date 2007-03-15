package eu.geclipse.info;

import java.util.ArrayList;
import eu.geclipse.info.glue.GlueSite;


/**
 * Interface that all Information stores should implement
 * @author George Tsouloupas
 * 
 */
public interface IGlueInfoStore {
	/**
	 * @param listener
	 * @param objectName
	 */
	public void addListener(IGlueStoreChangeListerner listener, String objectName);
	public void removeListener(IGlueStoreChangeListerner listener, String resourceTypeName);
}
