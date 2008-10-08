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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.accesscontrol.ActorType;
import eu.geclipse.core.accesscontrol.IACLActor;
import eu.geclipse.core.accesscontrol.IACLCapability;
import eu.geclipse.core.accesscontrol.IACLEntry;
import eu.geclipse.core.accesscontrol.IACLPolicy;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.StoredCombo;


/**
 * Dialog for editing single ACL entries {@link IACLEntry}.
 * 
 * @author agarcia
 */
public class AccessControlRuleDialog extends Dialog {
  
  /** If <code>true</code> the "OK" button will be relabeled "Save" */
  boolean withSaveButton;
  
  /* Dialog widgets */
  Combo actorTypeCombo;
  
  private Group entryGroup;
  private Combo policyCombo;
  private Combo capCombo;
  
  private CertificateComposite actorDNCertComposite;
  private CertificateComposite actorCACertComposite;
  private FieldComposite groupNameComposite;
  private FieldComposite userNameComposite;
  private FieldComposite userEmailComposite;
  private SAMLComposite samlComposite;
  
  /** The ACL entry to edit */
  private IACLEntry entry;
  
  /** The old value of actor type, for updating the dialog correctly */
  private ActorType type;
  
  
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
    this.type = this.entry.getActor().getActorType();
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
    
    this.entryGroup = new Group( mainComp, SWT.NONE );
    this.entryGroup.setLayout( new GridLayout( 2, false ) );
    this.entryGroup.setText( Messages.getString("AccessControlRuleDialog.group_title") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    gData.heightHint = 260;
    gData.widthHint = 500;
    this.entryGroup.setLayoutData( gData );
    
    createDialogWidgets( this.entryGroup );
    
    // Fill in the policy data
    List< String > policyNames = new ArrayList< String >( 5 );
    IACLPolicy[] policies = this.entry.getSupportedPolicies();
    for ( IACLPolicy policy : policies ) {
      String name = policy.toString();
      policyNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    this.policyCombo.setItems( policyNames.toArray( new String[ policyNames.size() ] ) );
    IACLPolicy policy = this.entry.getPolicy();
    if ( policy != null ) {
      this.policyCombo.select( this.policyCombo.indexOf( policy.toString() ) );
    }
    
    // Fill in the capability data
    List< String > capNames = new ArrayList< String >( 5 );
    IACLCapability[] capabilities = this.entry.getSupportedCapabilities();
    for ( IACLCapability capability : capabilities ) {
      String name = capability.getName();
      capNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    this.capCombo.setItems( capNames.toArray( new String[ capNames.size() ] ) );
    IACLCapability capability = this.entry.getCapability();
    if ( capability != null ) {
      this.capCombo.select( this.capCombo.indexOf( capability.getName() ) );
    }
    
    // Fill in the actor type data
    List< String > aTypeNames = new ArrayList< String >( 5 );
    IACLActor actor = this.entry.getActor();
    assert actor != null
           : "Null actor in ACL entry: implementation BUG."; //$NON-NLS-1$
    ActorType[] aTypes = actor.getSupportedTypes();
    for ( ActorType aType : aTypes ) {
      String name = aType.getName();
      aTypeNames.add( name != null ? name : "" ); //$NON-NLS-1$
    }
    this.actorTypeCombo.setItems( aTypeNames.toArray( new String[ aTypeNames.size() ] ) );
    
    ActorType aType = actor.getActorType();
    int index = this.actorTypeCombo.indexOf( aType.getName() );
    this.actorTypeCombo.select( index );
    
    updateDialogContents( aType );
    
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
    
    // TODO reenable when write support is available
    Button button;
    button = createButton( parent, IDialogConstants.OK_ID,
                  buttonText, true );
    button.setEnabled( false );
    createButton( parent, IDialogConstants.CANCEL_ID,
                  IDialogConstants.CANCEL_LABEL, false );
  }
  
  
  /**
   * Helper method to create all the dialog widgets.
   */
  private void createDialogWidgets( final Composite group ) {
    
    GridData gData;
    
    // The policy widgets
    Label policyLabel = new Label( group, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false );
    policyLabel.setLayoutData( gData );
    policyLabel.setText( Messages.getString("AccessControlRuleDialog.policy_label") ); //$NON-NLS-1$
    
    this.policyCombo = new Combo( group, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    this.policyCombo.setLayoutData( gData );
    
    // The capability widgets
    Label capLabel = new Label( group, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false );
    capLabel.setLayoutData( gData );
    capLabel.setText( Messages.getString("AccessControlRuleDialog.capability_label") ); //$NON-NLS-1$
    
    this.capCombo = new Combo( group, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    this.capCombo.setLayoutData( gData );
    
    // The actor type widgets
    Label actorTypeLabel = new Label( group, SWT.LEAD );
    gData = new GridData( SWT.LEAD, SWT.CENTER, false, false );
    actorTypeLabel.setLayoutData( gData );
    actorTypeLabel.setText( Messages.getString("AccessControlRuleDialog.actor_type_label") ); //$NON-NLS-1$
    
    this.actorTypeCombo = new Combo( group, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.LEAD );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    this.actorTypeCombo.setLayoutData( gData );
    this.actorTypeCombo.addModifyListener( new ModifyListener() {
        
        public void modifyText( final ModifyEvent e ) {
          ActorType aType = getSelectedActorType(
                              AccessControlRuleDialog.this.actorTypeCombo.getText() );
          updateDialogContents( aType );
        }
      } );
    
    // The actor DN composite
    this.actorDNCertComposite
      = new CertificateComposite( this.entryGroup,
              Messages.getString("AccessControlRuleDialog.actor_DN_label") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    this.actorDNCertComposite.setLayoutData( gData );
    setVisibility( this.actorDNCertComposite, false );
    
    // The actor CA composite
    this.actorCACertComposite
      = new CertificateComposite( this.entryGroup,
              Messages.getString("AccessControlRuleDialog.signing_CA_label") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    this.actorCACertComposite.setLayoutData( gData );
    setVisibility( this.actorCACertComposite, false );
    
    // The group composite
    this.groupNameComposite
      = new FieldComposite( this.entryGroup,
              Messages.getString("AccessControlRuleDialog.group_name_label") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    this.groupNameComposite.setLayoutData( gData );
    setVisibility( this.groupNameComposite, false );
    
    // The user composite
    this.userNameComposite
      = new FieldComposite( this.entryGroup,
              Messages.getString("AccessControlRuleDialog.user_name_label") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    this.userNameComposite.setLayoutData( gData );
    setVisibility( this.userNameComposite, false );
    
    // The user email composite
    this.userEmailComposite
      = new FieldComposite( this.entryGroup,
              Messages.getString("AccessControlRuleDialog.user_email_label") ); //$NON-NLS-1$
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    this.userEmailComposite.setLayoutData( gData );
    setVisibility( this.userEmailComposite, false );
    
    // The SAML composite
    this.samlComposite = new SAMLComposite( this.entryGroup );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 2;
    gData.exclude = true;
    this.samlComposite.setLayoutData( gData );
    setVisibility( this.samlComposite, false );

  }
  
  /**
   * Helper method to update the dialog widgets when the actor type is changed.
   */
  void updateDialogContents( final ActorType newType ) {
    
    // Fill in the actor DN data
    String actorID = this.entry.getActor().getID();
    this.actorDNCertComposite.setDN( actorID != null ? actorID : "" ); //$NON-NLS-1$
    
    // Fill in the actor CA data
    String actorCA = this.entry.getActor().getCA();
    this.actorCACertComposite.setDN( actorCA != null ? actorCA : "" ); //$NON-NLS-1$
    
    // Make the old widgets invisible
    if ( this.type.equals( ActorType.ANYBODY ) ) {
      // Nothing to do
    } else if ( this.type.equals( ActorType.CA_ANY_DN_ANY ) ) {
      // Nothing to do
    } else if ( this.type.equals( ActorType.CA_NAME_DN_ANY ) ) {
      setVisibility( this.actorCACertComposite, false );
    } else if ( this.type.equals( ActorType.CA_NAME_DN_PATTERN ) ) {
      //
    } else if ( this.type.equals( ActorType.CA_NAME_DN_NAME ) ) {
      setVisibility( this.actorDNCertComposite, false );
      setVisibility( this.actorCACertComposite, false );
    } else if ( this.type.equals( ActorType.GROUP_PATTERN ) ) {
      //
    } else if ( this.type.equals( ActorType.GROUP_NAME ) ) {
      setVisibility( this.groupNameComposite, false );
    } else if ( this.type.equals( ActorType.USER_PATTERN ) ) {
      //
    } else if ( this.type.equals( ActorType.USER_NAME ) ) {
      setVisibility( this.userNameComposite, false );
    } else if ( this.type.equals( ActorType.USER_EMAIL ) ) {
      setVisibility( this.userEmailComposite, false );
    } else if ( this.type.equals( ActorType.SAML_ATTRIBUTE ) ) {
      setVisibility( this.samlComposite, false );
    }
    
    // Make the right widgets visible
    if ( newType.equals( ActorType.ANYBODY ) ) {
      // Nothing to do
    } else if ( newType.equals( ActorType.CA_ANY_DN_ANY ) ) {
      // Nothing to do
    } else if ( newType.equals( ActorType.CA_NAME_DN_ANY ) ) {
      setVisibility( this.actorCACertComposite, true );
    } else if ( newType.equals( ActorType.CA_NAME_DN_PATTERN ) ) {
      //
    } else if ( newType.equals( ActorType.CA_NAME_DN_NAME ) ) {
      setVisibility( this.actorDNCertComposite, true );
      setVisibility( this.actorCACertComposite, true );
    } else if ( newType.equals( ActorType.GROUP_PATTERN ) ) {
      //
    } else if ( newType.equals( ActorType.GROUP_NAME ) ) {
      setVisibility( this.groupNameComposite, true );
    } else if ( newType.equals( ActorType.USER_PATTERN ) ) {
      //
    } else if ( newType.equals( ActorType.USER_NAME ) ) {
      setVisibility( this.userNameComposite, true );
    } else if ( newType.equals( ActorType.USER_EMAIL ) ) {
      setVisibility( this.userEmailComposite, true );
    } else if ( newType.equals( ActorType.SAML_ATTRIBUTE ) ) {
      setVisibility( this.samlComposite, true );
    }

//    this.actorDNText.setText();
//    this.actorCAText.setText();
    
    this.type = newType;
    this.entryGroup.layout();
  }
  
  /**
   * Helper method to hide or make visible a control.
   */
  private void setVisibility( final Control control, final boolean visible ) {
    control.setVisible( visible );
    ( ( GridData ) control.getLayoutData() ).exclude = ! visible;
  }
  
  /**
   * Helper method to get the selected actorType. May return null if
   * the selected type was not found.
   */
  ActorType getSelectedActorType( final String selectedItem ) {
    
    ActorType result = null;
    
    IACLActor actor = this.entry.getActor();
    ActorType[] aTypes = actor.getSupportedTypes();
    for ( ActorType aType : aTypes ) {
      String name = aType.getName();
      if ( name.equals( selectedItem ) ) {
        result = aType;
        break;
      }
    }
    
    return result;
  }
  
  /**
   * SelectionAdapter used for opening the <code>GridFileDialog</code> when
   * selecting a certificate file.
   */
  class CertFileSelectionAdapter extends SelectionAdapter {
    
    private Combo widget;
    
    /**
     * Constructs a SelectionAdapter for a given combo widget.
     * 
     * @param widget the combo widget where the filename is stored.
     */
    public CertFileSelectionAdapter( final Combo widget ) {
      super();
      this.widget = widget;
    }
    
    @Override
    public void widgetSelected( final SelectionEvent e ) {
      String filename = this.widget.getText();
      filename = showFileDialog( filename,
                                 Messages.getString("AccessControlRuleDialog.file_dialog_title" ) ); //$NON-NLS-1$ );
      if ( filename != null ) {
        this.widget.setText( filename );
      }
    }
  }
  
  /**
   * Show a dialog for selecting a file.
   * 
   * @param path the initial path to be set on the dialog.
   * @param title the title of the dialog window.
   * @return the chosen file or <code>null</code> if "Cancel" was pressed.
   */
  protected String showFileDialog( final String path, final String title  ) {
    String[] filterExtensions = { "*.pem", "*.p12", "*;*.*" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    String[] filterNames = { "Base64 encoded certificate", //$NON-NLS-1$
                             "PKCS#12 encoded certificate", //$NON-NLS-1$
                             "All Files" }; //$NON-NLS-1$
    FileDialog fileDialog = new FileDialog( getShell(), SWT.OPEN | SWT.SINGLE );
    fileDialog.setFilterExtensions( filterExtensions );
    fileDialog.setFilterNames( filterNames );
    fileDialog.setFileName( path );
    fileDialog.setText( title );
    String selected = fileDialog.open();
    return selected;
  }
  
  
  /**
   * A composite holding all the widgets necessary for displaying or entering
   * a certificate.
   */
  class CertificateComposite extends Composite {
    
    /** The text field displaying the certificate DN */
    private Text dnText;
    
    /** The combo-box for selecting the certificate file */
    private StoredCombo certFileCombo;
    
    /**
     * Constructs a certificate composite with the given parent.
     * 
     * @param parent the parent composite where this collection of widgets should live.
     * @param label a string used as a label for the field.
     */
    CertificateComposite( final Composite parent, final String label ) {
      
      super( parent, SWT.NONE );
      this.setLayout( new GridLayout( 2, false ) );
      
      GridData gData;
      
      URL openFileIcon = Activator.getDefault().getBundle().getEntry( "icons/obj16/open_file.gif" ); //$NON-NLS-1$
      Image openFileImage = ImageDescriptor.createFromURL( openFileIcon ).createImage();
      
      // The certificate DN widgets
      Label dnLabel = new Label( this, SWT.LEAD );
      gData = new GridData( SWT.LEAD, SWT.CENTER, false, false );
      dnLabel.setLayoutData( gData );
      dnLabel.setText( label );
      
      this.dnText = new Text( this, SWT.READ_ONLY | SWT.LEAD );
      gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
      this.dnText.setLayoutData( gData );
      
      // The certificate file widgets
      Label certFileLabel = new Label( this, SWT.TRAIL);
      gData = new GridData( SWT.FILL, SWT.CENTER, false, false );
      certFileLabel.setLayoutData( gData );
      certFileLabel.setText( Messages.getString("AccessControlRuleDialog.dn_file_label") ); //$NON-NLS-1$
      
      Composite dnComp = new Composite( this, SWT.NONE );
      dnComp.setLayout( new GridLayout( 2, false ) );
      gData = new GridData( SWT.FILL, SWT.FILL, true, false );
      dnComp.setLayoutData( gData );
      
      this.certFileCombo = new StoredCombo( dnComp, SWT.DROP_DOWN | SWT.LEAD );
      gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
      this.certFileCombo.setLayoutData( gData );
      
      Button dnFileButton = new Button( dnComp, SWT.PUSH );
      dnFileButton.setImage( openFileImage );
      gData = new GridData();
      dnFileButton.setLayoutData( gData );
      dnFileButton.setToolTipText(
        Messages.getString("AccessControlRuleDialog.dn_file_button_tooltip" ) ); //$NON-NLS-1$
      dnFileButton.addSelectionListener( new CertFileSelectionAdapter( this.certFileCombo ) );
    }
    
    /**
     * Sets the content of the text widget displaying the certificate's DN.
     * 
     * @param text the text string to set.
     */
    void setDN( final String text ) {
      this.dnText.setText( text );
    }
    
    /**
     * Returns the content of certificate file combo-box.
     * 
     * @return the path of the certificate file which was selected by the user.
     */
    String getCertificateFile() {
      return this.certFileCombo.getText();
    }

  }
  
  /**
   * A composite holding a label and a combo-box for entering text.
   */
  class FieldComposite extends Composite {
    
    /** The combo-box for entering the data */
    private StoredCombo combo;
    
    /**
     * Constructs a composite for entering data, with the given parent.
     * 
     * @param parent the parent composite where this collection of widgets should live.
     * @param label a string used as a label for the field.
     */
    FieldComposite( final Composite parent, final String label ) {
      
      super( parent, SWT.NONE );
      this.setLayout( new GridLayout( 2, false ) );
      
      GridData gData;
      
      Label dnLabel = new Label( this, SWT.LEAD );
      gData = new GridData( SWT.LEAD, SWT.CENTER, false, false );
      dnLabel.setLayoutData( gData );
      dnLabel.setText( label );
      
      this.combo = new StoredCombo( this, SWT.DROP_DOWN | SWT.LEAD );
      gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
      this.combo.setLayoutData( gData );
    }
    
    /**
     * Sets the content of the data field.
     * 
     * @param text the text string to set.
     */
    void setText( final String text ) {
      this.combo.setText( text );
    }
    
    /**
     * Returns the content of the combo-box.
     * 
     * @return the text entered by the user.
     */
    String getText() {
      return this.combo.getText();
    }

  }
  
  /**
   * A composite holding the widgets necessary for entering a SAML attribute.
   */
  class SAMLComposite extends Composite {
    
    /** The SAML attribute composite. */
    private FieldComposite samlAttr;
    
    /** The SAML value composite. */
    private FieldComposite samlValue;
    
    /**
     * Constructs a composite for a SAML attribute, with the given parent.
     * 
     * @param parent the parent composite where this collection of widgets should live.
     */
    SAMLComposite( final Composite parent ) {
      
      super( parent, SWT.NONE );
      this.setLayout( new GridLayout( 1, false ) );
      
      GridData gData;
      
      this.samlAttr
        = new FieldComposite( this,
                Messages.getString("AccessControlRuleDialog.SAML_attribute_label") ); //$NON-NLS-1$
      gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
      this.samlAttr.setLayoutData( gData );
      
      this.samlValue
        = new FieldComposite( this,
                Messages.getString("AccessControlRuleDialog.SAML_value_label") ); //$NON-NLS-1$
      gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
      this.samlValue.setLayoutData( gData );
    }
    
    /**
     * Sets the SAML attribute field.
     * 
     * @param attr the attribute to set.
     */
    void setAttribute( final String attr ) {
      this.samlAttr.setText( attr );
    }
    
    /**
     * Sets the SAML value field.
     * 
     * @param value the attribute value to set.
     */
    void setValue( final String value ) {
      this.samlValue.setText( value );
    }
    
    /**
     * Returns the SAML attribute field.
     * 
     * @return the attribute entered by the user.
     */
    String getAttribute() {
      return this.samlAttr.getText();
    }
    
    /**
     * Returns the SAML attribute value field.
     * 
     * @return the attribute value entered by the user.
     */
    String getValue() {
      return this.samlValue.getText();
    }

  }

}
