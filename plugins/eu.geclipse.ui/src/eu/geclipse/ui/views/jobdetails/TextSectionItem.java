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

  private Label valueLabel;

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

  public boolean refresh( final ESourceType sourceObject ) {
    boolean valueSpecified = false;
    String valueString = null;
    if( sourceObject != null ) {
      valueString = getValue( sourceObject );
    }
    if( valueString == null ) {
      this.valueLabel.setText( "" ); //$NON-NLS-1$ 
    } else {
      this.valueLabel.setText( valueString );
      valueSpecified = valueString.length() > 0;
    }
    return valueSpecified;
  }
}
