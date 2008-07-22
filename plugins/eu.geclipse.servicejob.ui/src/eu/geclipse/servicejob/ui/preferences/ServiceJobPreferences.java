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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.ui.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.servicejob.model.ServiceJobStates;

/**
 * {@link PreferencePage} implementation for managing colors used for ysplaying
 * different Operator's Job's statuses.
 */
public class ServiceJobPreferences extends PreferencePage
  implements IWorkbenchPreferencePage
{

  protected Map<ServiceJobStates, Button> buttonsMap = new HashMap<ServiceJobStates, Button>();
  protected Map<ServiceJobStates, Color> colors = new HashMap<ServiceJobStates, Color>();
  protected boolean changed;

  @Override
  protected Control createContents( final Composite parent ) {
    noDefaultAndApplyButton();
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    Label infoText = new Label( mainComp, SWT.LEAD );
    infoText.setText( Messages.getString( "TestsViewGeneralPreferences.preferences_title" ) ); //$NON-NLS-1$
    gData.horizontalSpan = 2;
    infoText.setLayoutData( gData );
    for( ServiceJobStates value : ServiceJobStates.values() ) {
      if( !value.equals( ServiceJobStates.NULL ) ) {
        Label labelValue = new Label( mainComp, SWT.LEAD );
        labelValue.setText( Messages.getString("TestsViewGeneralPreferences.color_for") + " &" + value.getAlias() + Messages.getString( "TestsViewGeneralPreferences.result" ) );  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        gData = new GridData();
        labelValue.setLayoutData( gData );
        Button newButton = new Button( mainComp, SWT.PUSH );
        this.buttonsMap.put( value, newButton );
        Color colorButton = PreferencesManager.getManager()
          .getColor( value.getAlias() );
        buttonColor( colorButton, newButton );
        this.colors.put( value, colorButton );
        final ServiceJobStates valFinal = value;
        newButton.addSelectionListener( new SelectionAdapter() {

          @Override
          public void widgetSelected( final SelectionEvent e ) {
            ColorDialog dialog = new ColorDialog( parent.getShell() );
            dialog.setText( Messages.getString( "TestsViewGeneralPreferences.select_color" ) //$NON-NLS-1$
                            + valFinal.getAlias()
                            + Messages.getString( "TestsViewGeneralPreferences.result" ) ); //$NON-NLS-1$
            RGB chosenColor = dialog.open();
            if( chosenColor != null ) {
              Color newColor = new Color( Display.getCurrent(), chosenColor );
              buttonColor( newColor,
                           ServiceJobPreferences.this.buttonsMap.get( valFinal ) );
              ServiceJobPreferences.this.colors.put( valFinal, newColor );
              ServiceJobPreferences.this.changed = true;
            }
          }
        } );
      }
    }
    return mainComp;
  }

  public void init( final IWorkbench workbench ) {
    // do nothing
  }

  protected final void buttonColor( final Color color, final Button button ) {
    Color foreground = button.getForeground();
    Color background = color;
    int x = 0;
    int y = 0;
    Rectangle rect = button.getBounds();
    int width = rect.width;
    int height = rect.height;
    String text = button.getText();
    width = 30;
    height = 10;
    button.setImage( new Image( button.getParent().getDisplay(), width, height ) );
    Image original = button.getImage();
    GC gc;
    gc = new GC( original );
    gc.setForeground( foreground );
    gc.setBackground( background );
    gc.drawRectangle( x, y, width, height );
    gc.fillRectangle( x, y, width, height );
    gc.setFont( button.getFont() );
    int ximg = 2;// ( x + width ) / 2 - fontSize * text.length() / 3;
    int yimg = 2;// ( y + height ) / 2 - fontSize * 3 / 4;
    gc.drawText( text, ximg > 4
                               ? ximg
                               : 4, yimg > 4
                                            ? yimg
                                            : 4, SWT.DRAW_TRANSPARENT
                                                 | SWT.DRAW_MNEMONIC );
    gc.dispose();
    Image iDisable = new Image( button.getParent().getDisplay(),
                                original,
                                SWT.IMAGE_BMP );
    button.setImage( iDisable );
  }

  @Override
  public boolean performOk() {
    PreferencesManager.getManager().saveState( this.colors );
    return super.performOk();
  }
}
