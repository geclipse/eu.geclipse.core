package eu.geclipse.ui.views.jobdetails;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 *
 * @param <ESourceType> type of object, from which data for this item will be obtained
 */
public interface ISectionItem<ESourceType> {

  /**
   * Creates widgets for visualization of item in section
   * 
   * @param parentComposite
   * @param formToolkit
   */
  void createWidgets( final Composite parentComposite,
                      final FormToolkit formToolkit );

  /**
   * @param sourceObject
   * @return true if value is specified (is not null)
   */
  boolean refresh( final ESourceType sourceObject );

  /**
   * @param visible true if item should be shown
   */
  void setVisible( final boolean visible );
}
