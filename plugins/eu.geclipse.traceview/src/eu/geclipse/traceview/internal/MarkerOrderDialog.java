package eu.geclipse.traceview.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class MarkerOrderDialog extends TitleAreaDialog {
  private List<EventMarkerEntry> initMarkerList;
  private List<EventMarkerEntry> newMarkerList;
  private Table table;
  private Button upButton;
  private Button downButton;

  public MarkerOrderDialog( Shell parentShell ) {
    super( parentShell );
  }

  void setEventMarkerEntries(List<EventMarkerEntry> list) {
    this.initMarkerList = list;
  }

  List<EventMarkerEntry> getEventMarkerEntries() {
    return this.newMarkerList;
  }

  @Override
  protected boolean isResizable() {
    return true;
  }

  @Override
  protected Control createDialogArea( Composite parent ) {
    setTitle( Messages.getString("MarkerOrderDialog.dialogTitle") ); //$NON-NLS-1$
    setMessage( Messages.getString("MarkerOrderDialog.dialogDesc") ); //$NON-NLS-1$
    getShell().setText( Messages.getString("MarkerOrderDialog.winTitle") ); //$NON-NLS-1$
    Composite comp = new Composite( parent, SWT.FILL );
    comp.setLayout( new GridLayout(2, false) );
    comp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
    gData.verticalSpan = 3;
    table = new Table(comp, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    table.setLayoutData( gData );
    for ( EventMarkerEntry item : this.initMarkerList ) {      
      getTableItem( item, table.getItemCount() );
    }
    upButton = new Button( comp, SWT.BORDER );
    upButton.setText( Messages.getString("MarkerOrderDialog.moveUp") ); //$NON-NLS-1$
    upButton.setEnabled( false );
    upButton.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, false ) );
    upButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        int selectionIndex = table.getSelectionIndex();
        EventMarkerEntry item = ( EventMarkerEntry )table.getItem( selectionIndex ).getData();
        boolean selected = table.getItem( selectionIndex ).getChecked();
        table.remove( selectionIndex );
        TableItem tableItem = getTableItem( item, selectionIndex - 1 );
        tableItem.setChecked( selected );
        table.setSelection( selectionIndex - 1 );
        updateButtonState();
      }
    });
    downButton = new Button( comp, SWT.BORDER );
    downButton.setText( Messages.getString("MarkerOrderDialog.moveDown") ); //$NON-NLS-1$
    downButton.setEnabled( false );
    downButton.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, false ) );
    downButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent e ) {
        int selectionIndex = table.getSelectionIndex();
        EventMarkerEntry item = ( EventMarkerEntry )table.getItem( selectionIndex ).getData();
        boolean selected = table.getItem( selectionIndex ).getChecked();
        table.remove( selectionIndex );
        TableItem tableItem = getTableItem( item, selectionIndex + 1 );
        tableItem.setChecked( selected );
        table.setSelection( selectionIndex + 1 );
        updateButtonState();
      }
    });
    table.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        updateButtonState();
      }
    } );
    return comp;
  }
  
  private void updateButtonState() {
    upButton.setEnabled( table.getSelectionIndex() > 0 );
    downButton.setEnabled( table.getSelectionIndex() < table.getItemCount()-1 );
  }

  private TableItem getTableItem(EventMarkerEntry entry, int index) {
    TableItem tableItem = new TableItem( table, SWT.NONE, index );
    tableItem.setText( entry.label );
    tableItem.setData( entry );
    tableItem.setChecked( entry.enabled );
    return tableItem;
  }

  @Override
  protected void okPressed() {
    this.newMarkerList = new ArrayList<EventMarkerEntry>();
    for (TableItem item : table.getItems()) {
      EventMarkerEntry entry = ( EventMarkerEntry )item.getData();
      entry.enabled = item.getChecked();
      this.newMarkerList.add( entry );
    }
    super.okPressed();
  }
}
