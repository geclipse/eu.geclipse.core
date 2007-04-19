/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Mariusz Wojtysiak
 *           
 *****************************************************************************/
package eu.geclipse.ui.properties;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.PropertyDescriptor;


/**
 * Property, which shown first line of muliline {@link String} finished by "..."
 * If user click on button near the "...", then dialog is shown with all lines for property-value
 * @param <ESourceType> type of object, for which property is displayed
 */
abstract public class MultilineProperty<ESourceType>
  extends AbstractProperty<ESourceType>
{

  static private ILabelProvider multilineLabelProvider = null;

  /**
   * @param name The property name
   * @param category The category, in which property will be visibled. May be
   *          null
   */
  public MultilineProperty( final String name, final String category ) {
    super( name, category );
    if( MultilineProperty.multilineLabelProvider == null ) {
      MultilineProperty.multilineLabelProvider = createLabelProvider();
    }
    this.setLabelProvider( createLabelProvider() );
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.properties.AbstractProperty#createDescriptor(eu.geclipse.ui.properties.PropertyId,
   *      java.lang.String)
   */
  @Override
  protected PropertyDescriptor createDescriptor( final PropertyId<ESourceType> propertyId,
                                                 final String nameString )
  {
    PropertyDescriptor descriptor = new PropertyDescriptor( propertyId,
                                                            nameString )
    {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.ui.views.properties.PropertyDescriptor#createPropertyEditor(org.eclipse.swt.widgets.Composite)
       */
      @Override
      public CellEditor createPropertyEditor( final Composite parent )
      {
        return createCellEditor( parent );
      }
    };
    return descriptor;
  }

  protected CellEditor createCellEditor( final Composite parent ) {
    return new DialogCellEditor( parent ) {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
       */
      @Override
      protected Object openDialogBox( final Control cellEditorWindow )
      {
        PropertyDialog dialog = new PropertyDialog( cellEditorWindow.getShell() );
        Object value = getValue();
        if( value != null && value instanceof String ) {
          dialog.setValue( ( String )value );
        }
        dialog.open();
        return null;
      }
    };
  }
  private class PropertyDialog extends Dialog {

    private Text valueText;
    private String valueString;

    protected PropertyDialog( final Shell parentShell ) {
      super( parentShell );
      setShellStyle( getShellStyle() | SWT.RESIZE );
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea( final Composite parent )
    {
      Composite dlgComposite = ( Composite )super.createDialogArea( parent );
      this.valueText = new Text( dlgComposite, SWT.READ_ONLY
                                               | SWT.BORDER
                                               | SWT.MULTI
                                               | SWT.H_SCROLL
                                               | SWT.V_SCROLL );
      if( this.valueString != null ) {
        this.valueText.setText( this.valueString );
      }
      GridData gridData = new GridData( GridData.FILL_BOTH );
      gridData.heightHint = 200;
      gridData.widthHint = 350;
      this.valueText.setLayoutData( gridData );
      getShell().setText( getName() );
      return dlgComposite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar( final Composite parent )
    {
      createButton( parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true );
    }

    void setValue( final String value ) {
      this.valueString = value;
    }
  }

  static private ILabelProvider createLabelProvider() {
    return new LabelProvider() {

      /*
       * (non-Javadoc)
       * 
       * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
       */
      @Override
      public String getText( final Object element )
      {
        String string;
        if( element instanceof String ) {
          string = removeNewLineChars( ( String )element );
        } else {
          string = element.toString();
        }
        return string;
      }

      private String removeNewLineChars( final String valueString ) {
        String result = valueString;
        int newLineIndex = valueString.indexOf( "\r" ); //$NON-NLS-1$
        if( newLineIndex > -1 ) {
          result = valueString.substring( 0, newLineIndex ) + "..."; //$NON-NLS-1$
        }
        return result;
      }
    };
  }
}
