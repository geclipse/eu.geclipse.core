package eu.geclipse.core.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * The <code>IStatusProvider</code> interface defines status
 * objects that can be used to create logs and/or exceptions.
 * This interface centralised on the one hand the efforts for
 * internatiolization and offers the possibility to reuse status
 * objects.
 */
public interface IStatusProvider {
  
  /**
   * IStatus object determining that a method of a class is not
   * or not yet implemented.
   */
  public static final IStatus METHOD_NOT_IMPLEMENTED
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  Messages.getString("IStatusProvider.method_not_implemented"), //$NON-NLS-1$
                  null );
  
  /**
   * IStatus object for a resource that is not a project.
   */
  public static final IStatus RESOURCE_IS_NOT_PROJECT
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  Messages.getString("IStatusProvider.resource_is_not_project"), //$NON-NLS-1$
                  null );
  
  /**
   * IStatus object for a resource that can not be created
   * by a certain type of resource manager.
   */
  public static final IStatus RESOURCE_NOT_CREATEABLE
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  Messages.getString("IStatusProvider.resource_not_createable"), //$NON-NLS-1$
                  null );
  
  /**
   * IStatus object declaring that a resource could not
   * be found during a resource operation
   */
  public static final IStatus RESOURCE_NOT_FOUND
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  Messages.getString("IStatusProvider.resource_not_found"), //$NON-NLS-1$
                  null );
  
  /**
   * IStatus object for a resource that can not be managed
   * by a certain type of resource manager.
   */
  public static final IStatus RESOURCE_NOT_MANAGEABLE
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  eu.geclipse.core.internal.Messages.getString("IStatusProvider.resource_not_manageable"), //$NON-NLS-1$
                  null );
  
  /**
   * IStatus object for already existing VO. 
   */
  public static final IStatus VO_EXISTS
    = new Status( IStatus.ERROR,
                  Activator.PLUGIN_ID,
                  IStatus.CANCEL,
                  eu.geclipse.core.internal.Messages.getString("IStatusProvider.vo_exists"), //$NON-NLS-1$
                  null );

}
