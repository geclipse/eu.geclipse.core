/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for handling deletion of sweeped parameters in case there is more than
 * one parameter on the same sweep level.
 */
public class SweepDeleteDialog extends Dialog {

  private TableViewer viewer;
  private List<String> input;
  private List<String> outputReturn;

  /**
   * Creates new instance of SweepDeleteDialog class.
   * 
   * @param parentShell shell of parent widget
   * @param input list of parameters from the same sweep level of deletion
   */
  public SweepDeleteDialog( final Shell parentShell, final List<String> input )
  {
    super( parentShell );
    this.input = input;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
   * .Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    Label description = new Label( mainComp, SWT.LEAD );
    gData.horizontalSpan = 2;
    description.setLayoutData( gData );
    description.setText( Messages.getString( "SweepDeleteDialog.info_message_part1" ) ); //$NON-NLS-1$
    Label descr1 = new Label( mainComp, SWT.LEAD );
    descr1.setText( Messages.getString( "SweepDeleteDialog.info_message_part2" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalSpan = 2;
    descr1.setLayoutData( gData );
    gData = new GridData( GridData.FILL_BOTH
                          | GridData.GRAB_HORIZONTAL
                          | GridData.GRAB_VERTICAL );
    gData.horizontalSpan = 2;
    gData.heightHint = 100;
    this.viewer = new TableViewer( mainComp, SWT.V_SCROLL
                                             | SWT.BORDER
                                             | SWT.MULTI );
    this.viewer.setLabelProvider( new LabelProvider() );
    this.viewer.setContentProvider( new cProvider() );
    this.viewer.setInput( this.input );
    this.viewer.getTable().setLayoutData( gData );
    return mainComp;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
   */
  @Override
  protected void buttonPressed( final int buttonId ) {
    if( buttonId == IDialogConstants.SELECT_ALL_ID ) {
      this.outputReturn = this.input;
      okPressed();
    } else if( buttonId == IDialogConstants.PROCEED_ID ) {
      this.outputReturn = getSelection();
      okPressed();
    } else if( buttonId == IDialogConstants.CANCEL_ID ) {
      cancelPressed();
    }
  }

  @SuppressWarnings("unchecked")
  private List<String> getSelection() {
    List<String> result = new ArrayList<String>();
    ISelection sel = this.viewer.getSelection();
    if( sel instanceof StructuredSelection ) {
      StructuredSelection sSel = ( StructuredSelection )sel;
      result = sSel.toList();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.jface.dialogs.Dialog#createButtonBar(org.eclipse.swt.widgets
   * .Composite)
   */
  @Override
  protected Control createButtonBar( final Composite parent ) {
    Composite buttonsComp = new Composite( parent, SWT.NONE );
    buttonsComp.setLayout( new GridLayout( 0, true ) );
    GridData gData = new GridData( GridData.FILL_BOTH );
    gData.verticalIndent = 10;
    gData.horizontalAlignment = SWT.END;
    buttonsComp.setLayoutData( gData );
    createButton( buttonsComp,
                  IDialogConstants.SELECT_ALL_ID,
                  Messages.getString( "SweepDeleteDialog.delete_all_button" ), //$NON-NLS-1$
                  true );
    createButton( buttonsComp,
                  IDialogConstants.PROCEED_ID,
                  Messages.getString( "SweepDeleteDialog.delete_all_selected" ), //$NON-NLS-1$
                  false );
    createButton( buttonsComp,
                  IDialogConstants.CANCEL_ID,
                  IDialogConstants.CANCEL_LABEL,
                  false );
    return buttonsComp;
  }

  /**
   * Method to access list of elements to remove as selected in dialog when user
   * confirmed exit.
   * 
   * @return list of parameters to delete from the same sweep level of deletion
   */
  public List<String> getElementsToRemove() {
    return this.outputReturn;
  }
  class cProvider implements IStructuredContentProvider {

    @SuppressWarnings("unchecked")
    public Object[] getElements( final Object inputElement ) {
      String[] result = null;
      if( inputElement instanceof List ) {
        result = ( String[] )( ( List )inputElement ).toArray( new String[ 0 ] );
      }
      return result;
    }

    public void dispose() {
      // do nothing
    }

    public void inputChanged( final Viewer viewer1,
                              final Object oldInput,
                              final Object newInput )
    {
      // do nothing
    }
  }
}
