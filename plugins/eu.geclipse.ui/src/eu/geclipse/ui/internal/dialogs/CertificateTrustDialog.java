package eu.geclipse.ui.internal.dialogs;

import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.security.ICertificateTrustVerifier.TrustMode;
import eu.geclipse.ui.dialogs.CertificateInfoDialog;
import eu.geclipse.ui.internal.Activator;

public class CertificateTrustDialog extends TitleAreaDialog {
  
  private static final String NEVER_TRUST_IMG = "icons/obj16/cert_trust_never_obj.gif"; //$NON-NLS-1$
  
  private static final String NOTNOW_TRUST_IMG = "icons/obj16/cert_trust_notnow_obj.gif"; //$NON-NLS-1$
  
  private static final String TEMP_TRUST_IMG = "icons/obj16/cert_trust_temp_obj.gif"; //$NON-NLS-1$
  
  private static final String SESSION_TRUST_IMG = "icons/obj16/cert_trust_session_obj.gif"; //$NON-NLS-1$
  
  private static final String PERMANENT_TRUST_IMG = "icons/obj16/cert_trust_permanent_obj.gif"; //$NON-NLS-1$
  
  private static Hashtable< String, Image > images
    = new Hashtable< String, Image >();
  
  protected TrustMode selection;
  
  private X509Certificate[] certificateChain;
  
  private Label issuerDNLabel;
  
  private Button neverButton;
  
  private Button notNowButton;
  
  private Button tempButton;
  
  private Button sessionButton;
  
  private Button permanentButton;
  
  public CertificateTrustDialog( final Shell parentShell ) {
    super( parentShell );
    setShellStyle( SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL );
    URL imgURL = Activator.getDefault().getBundle()
                   .getResource( "icons/wizban/cacert_wiz.gif" ); //$NON-NLS-1$
    ImageDescriptor imgDesc = ImageDescriptor.createFromURL( imgURL );
    setTitleImage( imgDesc.createImage() );
  }
  
  public static TrustMode openDialog( final Shell parentShell,
                                final X509Certificate[] chain ) {
    CertificateTrustDialog dialog = new CertificateTrustDialog( parentShell );
    dialog.setCertificateChain( chain );
    dialog.open();
    return dialog.getSelection();
  }
  
  public TrustMode getSelection() {
    return this.selection;
  }
  
  public void setCertificateChain( final X509Certificate[] chain ) {
    this.certificateChain = chain;
    if ( ( this.issuerDNLabel != null ) && ! this.issuerDNLabel.isDisposed()
        && ( chain != null ) && ( chain.length > 0 ) ) {
      this.issuerDNLabel.setText( chain[ 0 ].getIssuerX500Principal().getName() );
      this.issuerDNLabel.pack();
    }
  }
  
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setText( "Certificate Trust Dialog" );
  }
  
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    createButton( parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true );
  }
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    Label topRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    topRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

    Composite mainComp = new Composite( parent, SWT.NONE );
    GridData gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.widthHint = 250;
    mainComp.setLayoutData( gData );
    mainComp.setLayout( new GridLayout( 1, false ) );
    
    Label label1 = new Label( mainComp, SWT.WRAP );
    label1.setText( "A secured operation requires to trust a specific certificate. Since the certificate is issued by an unknown Certificate Authority (CA), there is no way to securely verify the certificate." );
    label1.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    Label label2 = new Label( mainComp, SWT.WRAP );
    label2.setText( "The Distinguished Name (DN) of the certificate's issuer is:" );
    label2.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    this.issuerDNLabel = new Label( mainComp, SWT.WRAP );
    Font font = this.issuerDNLabel.getFont();
    FontData[] fData = font.getFontData();
    for ( FontData fd : fData ) {
      fd.setStyle( fd.getStyle() | SWT.BOLD );
    }
    this.issuerDNLabel.setFont( new Font( font.getDevice(), fData ) );
    if ( ( this.certificateChain != null ) && ( this.certificateChain.length > 0 ) ) {
      this.issuerDNLabel.setText( this.certificateChain[ 0 ].getIssuerX500Principal().getName() );
    } else {
      this.issuerDNLabel.setText( "N/A" );
    }
    this.issuerDNLabel.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    Link link = new Link( mainComp, SWT.WRAP );
    link.setText( "<a>See certificate details.</a>" );
    
    Label label4 = new Label( mainComp, SWT.WRAP );
    label4.setText( "Please choose one of the following options:" );
    label4.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    Composite buttonComp = new Composite( mainComp, SWT.NONE );
    buttonComp.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, true ) );
    buttonComp.setLayout( new GridLayout( 2, false ) );
    
    this.neverButton = createRadioButton( buttonComp, NEVER_TRUST_IMG, "Never trust this certificate. Adds this certificate chain's subject certificate to the list of untrusted certificates for the duration of this session." );
    this.notNowButton = createRadioButton( buttonComp, NOTNOW_TRUST_IMG, "Do not trust this certificate now. You still have the chance to trust this certificate the next time." );
    this.tempButton = createRadioButton( buttonComp, TEMP_TRUST_IMG, "Trust this certificate temporarily. You still have the chance to not trust this certificate the next time." );
    this.sessionButton = createRadioButton( buttonComp, SESSION_TRUST_IMG, "Trust this certificate for the whole session. Adds the whole certificate chain to the list of trusted certificates for the duration of this session." );
    this.permanentButton = createRadioButton( buttonComp, PERMANENT_TRUST_IMG, "Always trust this certificate. Adds the whole certificate chain to the certificate manager." );
    
    Label bottomRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    bottomRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    link.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        openInfoDialog();
      }
    } );
    
    this.neverButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        CertificateTrustDialog.this.selection = TrustMode.Never;
      }
    } );
    
    this.notNowButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        CertificateTrustDialog.this.selection = TrustMode.NotNow;
      }
    } );
    
    this.tempButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        CertificateTrustDialog.this.selection = TrustMode.Temporarily;
      }
    } );
    
    this.sessionButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        CertificateTrustDialog.this.selection = TrustMode.Session;
      }
    } );
    
    this.permanentButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        CertificateTrustDialog.this.selection = TrustMode.Permanent;
      }
    } );
    
    this.notNowButton.setSelection( true );
    this.selection = TrustMode.NotNow;
    
    setTitle( "Certificate Trust Verifier" );
    setMessage( "You should carefully select from the options below if you are willing to trust this certificate or not." );
    
    return mainComp;
    
  }
  
  protected void openInfoDialog() {
    CertificateInfoDialog dialog = new CertificateInfoDialog( getShell() );
    dialog.setCertificateChain( this.certificateChain );
    dialog.open();
  }
  
  private Button createRadioButton( final Composite parent, final String image, final String text ) {
    
    Button button = new Button( parent, SWT.RADIO );
    button.setImage( getCertImage( image ) );
    button.setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, false, false ) );
    
    Label label = new Label( parent, SWT.WRAP );
    label.setText( text );
    label.setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, true, false ) );
    
    return button;
    
  }
  
  private static Image getCertImage( final String path ) {
    
    Image image = images.get( path );
    
    if ( image == null ) {
      image = loadCertImage( path );
      images.put( path, image );
    }
    
    return image;
    
  }
  
  private static Image loadCertImage( final String path ) {
    URL url = Activator.getDefault().getBundle().getEntry( path );
    ImageDescriptor descriptor = ImageDescriptor.createFromURL( url );
    return descriptor.createImage();
  }

}
