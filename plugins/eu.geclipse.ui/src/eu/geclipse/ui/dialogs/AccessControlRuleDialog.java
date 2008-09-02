/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.accesscontrol.ActorType;
import eu.geclipse.core.accesscontrol.IACLActor;
import eu.geclipse.core.accesscontrol.IACLCapability;
import eu.geclipse.core.accesscontrol.IACLEntry;
import eu.geclipse.core.accesscontrol.IACLPolicy;
import eu.geclipse.ui.widgets.StoredCombo;


/**
 * Dialog for editing single ACL entries {@link IACLEntry}.
 * 
 * @author agarcia
 */
public class AccessControlRuleDialog extends Dialog {
  
  /** If <code>true</code> the "OK" button will be relabeled "Save" */
  boolean withSaveButton;
  
  /** The ACL entry to edit */
  private IACLEntry entry;
  
  
  /**
   * The constructor.
   * 
   * @param entry the {@link IACLEntry} to edit.
   * @param withSaveButton display a "Save" button instead of the "OK" one.
   *        To be used in the case single ACL entries must be saved separately.
   * @param parentShell the parent shell.
   */
  protected AccessControlRuleDialog( final IACLEntry entry,
                                     final boolean withSaveButton,
                                     final Shell parentShell )
  {
    super( parentShell );
    setShellStyle( SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL
                   | SWT.RESIZE | SWT.MIN | SWT.MAX );
    
    this.withSaveButton = withSaveButton;
    this.entry = entry;
  }
  
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public Control createDialogArea( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    mainComp.setLayoutData( gData );
    
    Group entryGroup = new Group( mainComp, SWT.NONE );
    entryGroup.setLayout( new GridLayout( 2, false ) );
    entryGroup.setText( Messages.getString("AccessControlRuleDialog.group_title") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 180;
    gData.widthHint = 500;
    entryGroup.setLayoutData( gData );
    
    // The policy widgets
    Label policyLabel = new Label( entryGroup, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false);
    policyLabel.setLayoutData( gData );
    policyLabel.setText( Messages.getString("AccessControlRuleDialog.policy_label") ); //$NON-NLS-1$
    
    Combo policyCombo = new Combo( entryGroup, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false);
    policyCombo.setLayoutData( gData );
    
    // The capability widgets
    Label capLabel = new Label( entryGroup, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false);
    capLabel.setLayoutData( gData );
    capLabel.setText( Messages.getString("AccessControlRuleDialog.capability_label") ); //$NON-NLS-1$
    
    Combo capCombo = new Combo( entryGroup, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false);
    capCombo.setLayoutData( gData );
    
    // The actor type widgets
    Label actorTypeLabel = new Label( entryGroup, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false);
    actorTypeLabel.setLayoutData( gData );
    actorTypeLabel.setText( Messages.getString("AccessControlRuleDialog.actor_type_label") ); //$NON-NLS-1$
    
    Combo actorTypeCombo = new Combo( entryGroup, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false);
    actorTypeCombo.setLayoutData( gData );
    
    // The actor ID widgets
    Label actorIDLabel = new Label( entryGroup, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false);
    actorIDLabel.setLayoutData( gData );
    actorIDLabel.setText( Messages.getString("AccessControlRuleDialog.actor_ID_label") ); //$NON-NLS-1$
    
    StoredCombo actorIDCombo = new StoredCombo( entryGroup, SWT.NONE | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false);
    actorIDCombo.setLayoutData( gData );
    
    // The actor CA widgets
    Label actorCALabel = new Label( entryGroup, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false);
    actorCALabel.setLayoutData( gData );
    actorCALabel.setText( Messages.getString("AccessControlRuleDialog.signing_CA_label") ); //$NON-NLS-1$
    
    StoredCombo actorCACombo = new StoredCombo( entryGroup, SWT.NONE | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false);
    actorCACombo.setLayoutData( gData );
    
    
    // Fill in the policy data
    List< String > policyNames = new ArrayList< String >( 5 );
    IACLPolicy[] policies = this.entry.getSupportedPolicies();
    for ( IACLPolicy policy : policies ) {
      String name = policy.toString();
      policyNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    policyCombo.setItems( policyNames.toArray( new String[ policyNames.size() ] ) );
    IACLPolicy policy = this.entry.getPolicy();
    if ( policy != null ) {
      policyCombo.select( policyCombo.indexOf( policy.toString() ) );
    }
    
    // Fill in the capability data
    List< String > capNames = new ArrayList< String >( 5 );
    IACLCapability[] capabilities = this.entry.getSupportedCapabilities();
    for ( IACLCapability capability : capabilities ) {
      String name = capability.getName();
      capNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    capCombo.setItems( capNames.toArray( new String[ capNames.size() ] ) );
    IACLCapability capability = this.entry.getCapability();
    if ( capability != null ) {
      capCombo.select( capCombo.indexOf( capability.getName() ) );
    }
    
    // Fill in the actor type data
    List< String > aTypeNames = new ArrayList< String >( 5 );
    IACLActor actor = this.entry.getActor();
    assert actor != null
           : "Null actor in ACL entry: implementation BUG."; //$NON-NLS-1$
    ActorType[] aTypes = actor.getSupportedTypes();
    for ( ActorType aType : aTypes ) {
      String name = aType.toString();
      aTypeNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    actorTypeCombo.setItems( aTypeNames.toArray( new String[ aTypeNames.size() ] ) );
    int index = actorTypeCombo.indexOf( actor.getActorType().toString() );
    actorTypeCombo.select( index );
    
    // Fill in the actor ID data
    String actorID = this.entry.getActor().getID();
    actorIDCombo.setText( actorID != null ? actorID : "" ); //$NON-NLS-1$
    
    // Fill in the actor ID data
    String actorCA = this.entry.getActor().getCA();
    actorCACombo.setText( actorCA != null ? actorCA : "" ); //$NON-NLS-1$
    
    return mainComp;
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    shell.setText( Messages.getString("AccessControlRuleDialog.dialog_title") ); //$NON-NLS-1$
  }
  
  /**
   * Create the dialog buttons. We override the superclass' method to be able
   * to modify the OK button label if each entry has to be saved individually.
   */
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    
    // We change just the label of the OK button
    String buttonText = null;
    if ( this.withSaveButton ) {
      buttonText = Messages.getString("AccessControlRuleDialog.save_button_text"); //$NON-NLS-1$
    } else {
      buttonText = IDialogConstants.OK_LABEL;
    }
    
    Button button;
    button = createButton( parent, IDialogConstants.OK_ID,
                  buttonText, true );
    // TODO reenable when write support is available
    button.setEnabled( false );
    createButton( parent, IDialogConstants.CANCEL_ID,
                  IDialogConstants.CANCEL_LABEL, false );
  }

}
