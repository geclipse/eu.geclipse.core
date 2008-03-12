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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.dialogs;

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.ui.internal.Messages;

/**
 * A dialog that presents information about a batch job.
 */
public class BatchJobInfoDialog extends IconAndMessageDialog {
  
  /**
   * The job for which to display information.
   */
  private IBatchJobInfo job;

  /**
   * Construct a new info dialog from the specified job.
   * 
   * @param job The <code>IBatchJobInfo</code> for which to display the information.
   * @param parentShell The parent shell of this dialog.
   */
  public BatchJobInfoDialog( final IBatchJobInfo job, final Shell parentShell ) {
    super( parentShell );
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
    this.job = job;
  }
  
  /**
   * Get the job of this info dialog.
   * 
   * @return The <code>IBatchJobInfo</code> for which to display the info.
   */
  protected IBatchJobInfo getJob() {
    return this.job;
  }
 
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    createMessageArea(parent);
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 450;
    gData.heightHint = 250;
    mainComp.setLayoutData( gData );
    
    // Id field
    Label idLabel = new Label( mainComp, SWT.LEFT );
    idLabel.setText( Messages.getString( "BatchJobInfoDialog.job_id_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    idLabel.setLayoutData( gData );
    
    Text idText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    idText.setText( this.job.getJobId() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    idText.setLayoutData( gData );
    idText.setEditable( false );
    
    // Name field
    Label nameLabel = new Label( mainComp, SWT.LEFT );
    nameLabel.setText( Messages.getString( "BatchJobInfoDialog.job_name_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    nameLabel.setLayoutData( gData );
    
    Text nameText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    nameText.setText( this.job.getJobName() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    nameText.setLayoutData( gData );
    nameText.setEditable( false );
   
    // QueueName field
    Label queueNameLabel = new Label( mainComp, SWT.LEFT );
    queueNameLabel.setText( Messages.getString( "BatchJobInfoDialog.job_queue_name_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    queueNameLabel.setLayoutData( gData );
    
    Text queueNameText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    queueNameText.setText( this.job.getQueueName() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    queueNameText.setLayoutData( gData );
    queueNameText.setEditable( false );
    
    // UserAccount field 
    Label userAccountLabel = new Label( mainComp, SWT.LEFT );
    userAccountLabel.setText( Messages.getString( "BatchJobInfoDialog.job_user_account_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    userAccountLabel.setLayoutData( gData );
    
    Text userAccountText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    userAccountText.setText( this.job.getUserAccount() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    userAccountText.setLayoutData( gData );
    userAccountText.setEditable( false );

    // Status field
    Label statusLabel = new Label( mainComp, SWT.LEFT );
    statusLabel.setText( Messages.getString( "BatchJobInfoDialog.job_status_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    statusLabel.setLayoutData( gData );
    
    Text statusText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    statusText.setText( this.job.getStatus().toString() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    statusText.setLayoutData( gData );
    statusText.setEditable( false );
    
    // TimeUsed field
    Label timeUsedLabel = new Label( mainComp, SWT.LEFT );
    timeUsedLabel.setText( Messages.getString( "BatchJobInfoDialog.job_time_used_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    timeUsedLabel.setLayoutData( gData );
    
    Text timeUsedText = new Text( mainComp, SWT.LEFT | SWT.BORDER );
    timeUsedText.setText( this.job.getTimeUse() );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    timeUsedText.setLayoutData( gData );
    timeUsedText.setEditable( false );
    
    return mainComp;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
   */
  @Override
  protected Image getImage() {
    return getInfoImage();
  }
  
  /**
   * Create the token specific info area.
   * 
   * @param parent The parent composite that will contain the created info area.
   * @return The created info area.
   */
  protected Control createInfoArea( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.BORDER );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label label = new Label( mainComp, SWT.NONE );
    label.setText( Messages.getString( "BatchJobInfoDialog.no_info_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    label.setLayoutData( gData );
    
    return mainComp;
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
   */
//  @Override
//  protected IDialogSettings getDialogBoundsSettings() {
//    return eu.geclipse.batch.internal.Activator.getDefault().getDialogSettings();
//  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell ) {
    String batchType;
    String type;
    
    super.configureShell( shell );
    
    type = this.job.getServiceTypeName();
    if ( null != type )
      batchType = this.job.getServiceTypeName();
    else 
      batchType = Messages.getString( "BatchJobInfoDialog.unknown" ); //$NON-NLS-1$
        
    shell.setText( batchType 
                   + " " //$NON-NLS-1$ 
                   + Messages.getString( "BatchJobInfoDialog.info_suffix" ) ); //$NON-NLS-1$
  }

}
