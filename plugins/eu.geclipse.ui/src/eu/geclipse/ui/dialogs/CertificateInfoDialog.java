package eu.geclipse.ui.dialogs;

import java.net.URL;
import java.security.cert.X509Certificate;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.CertificateInfoPanel;

public class CertificateInfoDialog extends TitleAreaDialog {
  
  private CertificateInfoPanel infoPanel;
  
  private X509Certificate[] certificateChain;

  public CertificateInfoDialog( final Shell parentShell ) {
    super( parentShell );
    setShellStyle( SWT.DIALOG_TRIM | SWT.RESIZE );
    URL imgURL = Activator.getDefault().getBundle()
                   .getResource( "icons/wizban/cacert_wiz.gif" ); //$NON-NLS-1$
    ImageDescriptor imgDesc = ImageDescriptor.createFromURL( imgURL );
    setTitleImage( imgDesc.createImage() );
  }
  
  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setText( "Certificate Information" );
  }
  
  public static void openDialog( final Shell parentShell,
                                 final X509Certificate certificate ) {
    CertificateInfoDialog dialog = new CertificateInfoDialog( parentShell );
    dialog.setCertificate( certificate );
    dialog.open();
  }
  
  public void setCertificate( final X509Certificate certificate  ) {
    setCertificateChain( new X509Certificate[] { certificate } );
  }
  
  public void setCertificateChain( final X509Certificate[] chain ) {
    this.certificateChain = chain;
    if ( ( this.infoPanel != null ) && ( ! this.infoPanel.isDisposed() )
        && ( chain != null ) && ( chain.length > 0 ) ) {
      internalSetCertificateChain( chain );
    }
  }
  
  @Override
  protected void createButtonsForButtonBar( final Composite parent ) {
    createButton( parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true );
  }
  
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    Label topRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    topRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

    this.infoPanel = new CertificateInfoPanel( parent, SWT.NONE );
    this.infoPanel.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );

    Label bottomRule = new Label( parent, SWT.HORIZONTAL | SWT.SEPARATOR );
    bottomRule.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
    
    if ( ( this.certificateChain != null ) && ( this.certificateChain.length > 0 ) ) {
      internalSetCertificateChain( this.certificateChain );
    }
    
    setMessage( "Information about X.509 based certificates." );
    
    return this.infoPanel;
    
  }
  
  @Override
  protected IDialogSettings getDialogBoundsSettings() {
    return Activator.getDefault().getDialogSettings();
  }
  
  private void internalSetCertificateChain( final X509Certificate[] chain ) {
    this.infoPanel.setCertificateChain( chain );
    setTitle( chain[ 0 ].getSubjectX500Principal().getName() );
  }
  
}
