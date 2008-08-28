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

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
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

import eu.geclipse.jsdl.model.sweep.SweepType;

public class SweepDeleteDialog extends Dialog {

  private TableViewer viewer;
  private List<String> input;
  private List<String> outputReturn;

  public SweepDeleteDialog( final Shell parentShell, final List<String> input )
  {
    super( parentShell );
    this.input = input;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    GridData gData = new GridData();
    Label description = new Label( mainComp, SWT.LEAD );
    gData.horizontalSpan = 2;
    description.setLayoutData( gData );
    description.setText( "There is more than one sweep at this level. Which one do you want to remove?" );
    gData = new GridData();
    gData.horizontalSpan = 2;
    this.viewer = new TableViewer( mainComp );
    this.viewer.setLabelProvider( new LabelProvider() );
    this.viewer.setContentProvider( new cProvider() );
    this.viewer.setInput( this.input );
    this.viewer.getTable().setLayoutData( gData );
    return mainComp;
  }

  @Override
  protected void okPressed() {
    ISelection sel = this.viewer.getSelection();
    if( sel instanceof StructuredSelection ) {
      StructuredSelection sSel = ( StructuredSelection )sel;
      this.outputReturn = sSel.toList();
    }
    super.okPressed();
  }

  public List<String> getElementsToRemove() {
    return this.outputReturn;
  }
  class cProvider implements IStructuredContentProvider {

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

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      // do nothing
    }
  }
}
