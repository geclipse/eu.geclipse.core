package eu.geclipse.ui.views.jobdetails;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.model.IGridJob;

/**
 * Section containing grouped details of submitted job
 */
public interface IJobDetailsSection {

  /**
   * Refresh all items in section using gridJob. Also creates section widgets if
   * haven't created yet.
   * 
   * @param gridJob refreshed job
   * @param parent parent, on which widgets should be created
   * @param viewConfiguration current view configuration
   */
  void refresh( final IGridJob gridJob,
                final Composite parent,
                final IViewConfiguration viewConfiguration );

  /**
   * @return position, on which section will be visibled in
   *         {@link JobDetailsView}
   */
  int getOrder();

  /**
   * Puts detail to section
   * 
   * @param detail
   */
  void addDetail( IJobDetail detail );

  /**
   * Removes detail from section
   * 
   * @param detail
   */
  void removeDetail( IJobDetail detail );

  /**
   * Disposes widgets created in section
   */
  void dispose();
  
  /**
   * @return details currently shown in section
   */
  List<IJobDetail> getDetails();
}
