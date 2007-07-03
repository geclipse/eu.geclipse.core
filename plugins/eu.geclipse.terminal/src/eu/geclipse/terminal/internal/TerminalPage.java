/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.terminal.internal;

import java.io.IOException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.themes.IThemeManager;
import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.terminal.ITerminalListener;
import eu.geclipse.terminal.ITerminalPage;
import eu.geclipse.terminal.internal.preferences.PreferenceConstants;

class TerminalPage extends Composite implements ITerminalPage {
  private static final String fgColorThemeEntry = "eu.geclipse.terminal.ForgroundColor"; //$NON-NLS-1$
  private static final String bgColorThemeEntry = "eu.geclipse.terminal.BackgroundColor"; //$NON-NLS-1$
  private static final String fontTextEntry = "eu.geclipse.terminal.TextFont"; //$NON-NLS-1$
  Label descLabel = null;
  CTabItem tabItem;
  private Terminal terminal;
  private IBidirectionalConnection connection;

  TerminalPage( final Composite parent, final int style, final CTabItem cTabItem ) {
    super( parent, style );
    this.tabItem = cTabItem;
    createPartControl();
  }

  void paste() {
    this.terminal.paste();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#setTabName(java.lang.String)
   */
  public void setTabName( final String name ) {
    Display.getDefault().syncExec(new Runnable() {
      public void run () {
        TerminalPage.this.tabItem.setText( name );
      }
    });
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#getTabName()
   */
  public String getTabName() {
    return this.tabItem.getText();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#setDescription(java.lang.String)
   */
  public void setDescription( final String desc ) {
    Display.getDefault().syncExec( new Runnable() {
      public void run () {
        TerminalPage.this.descLabel.setText( desc );
      }
    } );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#getDescription()
   */
  public String getDescription() {
    return this.descLabel.getText();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#setFont(org.eclipse.swt.graphics.Font)
   */
  @Override
  public void setFont( final Font font ) {
    this.terminal.setFont( font );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.views.ITerminalPage#getFont()
   */
  @Override
  public Font getFont() {
    return this.terminal.getFont();
  }

  void setConnection( final IBidirectionalConnection conn ) throws IOException {
    this.connection = conn;
    this.terminal.setInputStream( this.connection.getInputStream() );
    this.terminal.setOutputStream( this.connection.getOutputStream() );
  }

  void setTerminalListener( final ITerminalListener termListener ) {
    this.terminal.setTerminalListener( termListener );
  }
  
  void closeConnection() {
    this.connection.close();
  }
  
  private void createPartControl() {
    GridLayout gridLayout = new GridLayout();
    gridLayout.horizontalSpacing = 0;
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    gridLayout.verticalSpacing = 0;
    GridData labelGridData = new GridData();
    labelGridData.horizontalAlignment = GridData.FILL;
    labelGridData.grabExcessHorizontalSpace = true;
    labelGridData.verticalAlignment = GridData.CENTER;
    this.descLabel = new Label( this, SWT.NONE );
    this.descLabel.setText( Messages.getString( "TerminalPage.noDescription" ) ); //$NON-NLS-1$
    this.descLabel.setLayoutData( labelGridData );
    GridData terminalGridData = new GridData();
    terminalGridData.horizontalAlignment = GridData.FILL;
    terminalGridData.grabExcessHorizontalSpace = true;
    terminalGridData.grabExcessVerticalSpace = true;
    terminalGridData.verticalAlignment = GridData.FILL;
    IThemeManager themeManager = Activator.getDefault().getWorkbench().getThemeManager();
    Color fgColor = themeManager.getCurrentTheme().getColorRegistry().get( fgColorThemeEntry );
    Color bgColor = themeManager.getCurrentTheme().getColorRegistry().get( bgColorThemeEntry );
    Font font = themeManager.getCurrentTheme().getFontRegistry().get( fontTextEntry );
    IPreferenceStore store = Activator.getDefault().getPreferenceStore();
    int historySize = store.getInt( PreferenceConstants.P_HISTORY_SIZE );
    this.terminal = new Terminal( this, SWT.NONE, fgColor, bgColor, historySize );
    this.terminal.setFont( font );
    this.terminal.setLayoutData( terminalGridData );
    this.tabItem.setText( Messages.getString( "TerminalPage.terminal" ) ); //$NON-NLS-1$
    setLayout( gridLayout );
  }
}
