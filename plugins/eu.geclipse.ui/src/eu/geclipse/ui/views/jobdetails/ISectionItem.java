package eu.geclipse.ui.views.jobdetails;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import eu.geclipse.core.model.IGridJob;

interface ISectionItem<ESourceType> {

  /**
   * Creates widgets for visualization of item in section
   * 
   * @param parentComposite
   * @param formToolkit
   */
  void createWidgets( final Composite parentComposite,
                      final FormToolkit formToolkit );

  /**
   * Refresh value shown in item
   * 
   * @param sourceObject {@link IGridJob} or inherited (depends of ESourceType)
   *          cointaing job data
   */
  void refresh( final ESourceType sourceObject );
}
