package eu.geclipse.core.model;

import java.util.List;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IManageable;


/**this job starts when g-Eclips erestarted
 * @author tao-j
 *
 */
public interface IDeployBackJob extends IGridElement, IManageable {
  /**
   * Method to access all unfinished deploy job.
   * 
   * @return list of job names
   */
  public List<String> getDeployJobNames();
}
