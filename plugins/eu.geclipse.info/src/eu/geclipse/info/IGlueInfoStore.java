package eu.geclipse.info;

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
    
    public void removeAllListeners();
    
    public void addStateListener(IGlueStoreStateChangeListerner listener);

    public void removeStateListener(IGlueStoreStateChangeListerner listener);
    
    public void removeAllStateListeners();
    
}
