package eu.geclipse.ui.views.jobdetails;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;


/**
 * Section item shown as simple text
 * 
 * @param <ESourceType>
 */
abstract public class TextSectionItem<ESourceType>
  extends AbstractSectionItem<ESourceType>
{

  Label valueLabel;

  /**
   * @param nameString section name
   */
  public TextSectionItem( final String nameString ) {
    super( nameString );
  }

  @Override
  protected Control createValueControl( final Composite parentComposite,
                                        final FormToolkit formToolkit )
  {
    this.valueLabel = formToolkit.createLabel( parentComposite, null );
    return this.valueLabel;
  }

  @Override
  public void refresh( final ESourceType sourceObject )
  {
    String valueString = getValue( sourceObject );
    this.valueLabel.setText( valueString == null
                                                ? "" : valueString ); //$NON-NLS-1$
  }
}
