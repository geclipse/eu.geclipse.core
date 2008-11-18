package eu.geclipse.ui.widgets;

import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import eu.geclipse.core.security.X509Util;

public class CertificateInfoPanel extends Composite {

  protected Text valueText;
  
  private X509Certificate[] certificateChain;
  
  private ScrolledComposite overviewComp;
  
  private Composite overviewPanel;
  
  private Table chainTable;
  
  private Tree detailsTree;
  
  private Label validText;
  
  private Label notBeforeText;
  
  private Label notAfterText;
  
  private Label sNoText;
  
  private Hashtable< String, Label > subjectLabels
    = new Hashtable< String, Label >();
  
  private Hashtable< String, Label > issuerLabels
    = new Hashtable< String, Label>();
  
  public CertificateInfoPanel( final Composite parent, final int style ) {
    
    super( parent, style );
    
    setLayout( new GridLayout( 1, false ) );
    
    TabFolder tabFolder = new TabFolder( this, SWT.NONE );
    GridData gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.widthHint = 300;
    gData.heightHint = 400;
    tabFolder.setLayoutData( gData );
    
    TabItem overviewItem = new TabItem( tabFolder, SWT.NONE );
    overviewItem.setText( "Overview" );
    
    this.overviewComp = new ScrolledComposite( tabFolder, SWT.V_SCROLL );
    this.overviewComp.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    this.overviewComp.setExpandHorizontal( true );
    this.overviewComp.setExpandVertical( true );
    overviewItem.setControl( overviewComp );
    
    this.overviewPanel = new Composite( this.overviewComp, SWT.NONE );
    this.overviewPanel.setLayout( new GridLayout( 1, false ) );
    this.overviewComp.setContent( this.overviewPanel );
    
    Group generalGroup = new Group( this.overviewPanel, SWT.NONE );
    generalGroup.setText( "General" );
    generalGroup.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    generalGroup.setLayout( new GridLayout( 2, false ) );
    
    Label validLabel = new Label( generalGroup, SWT.NONE );
    validLabel.setText( "Valid:" );
    
    this.validText = new Label( generalGroup, SWT.NONE );
    Font font = this.validText.getFont();
    FontData[] fontData = font.getFontData();
    for ( FontData fData : fontData ) {
      int fStyle = fData.getStyle();
      fData.setStyle( fStyle | SWT.BOLD );
    }
    this.validText.setFont( new Font( font.getDevice(), fontData ) );
    
    Label notBeforeLabel = new Label( generalGroup, SWT.NONE );
    notBeforeLabel.setText( "Not before:" );
    
    this.notBeforeText = new Label( generalGroup, SWT.NONE );
    
    Label notAfterLabel = new Label( generalGroup, SWT.NONE );
    notAfterLabel.setText( "Not after:" );
    
    this.notAfterText = new Label( generalGroup, SWT.NONE );
    
    Label sNoLabel = new Label( generalGroup, SWT.NONE );
    sNoLabel.setText( "Serial Number:" );
    
    this.sNoText = new Label( generalGroup, SWT.NONE );
    
    createX500PrincipalGroup( this.overviewPanel, "Issued to", this.subjectLabels );
    createX500PrincipalGroup( this.overviewPanel, "Issued from", this.issuerLabels );
    
    TabItem detailsItem = new TabItem( tabFolder, SWT.NONE );
    detailsItem.setText( "Details" );
    
    SashForm detailsForm = new SashForm( tabFolder, SWT.VERTICAL );
    detailsForm.setLayout( new FillLayout() );
    detailsItem.setControl( detailsForm );
    
    Composite tableComp = new Composite( detailsForm, SWT.NONE );
    tableComp.setLayout( new GridLayout( 1, false ) );
    
    Label tableLabel = new Label( tableComp, SWT.NONE );
    tableLabel.setText( "Certificate chain" );
    
    this.chainTable = new Table( tableComp, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE );
    this.chainTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    
    Composite treeComp = new Composite( detailsForm, SWT.NONE );
    treeComp.setLayout( new GridLayout( 1, false ) );
    
    Label treeLabel = new Label( treeComp, SWT.NONE );
    treeLabel.setText( "Certificate structure" );
    
    this.detailsTree = new Tree( treeComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.detailsTree.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    
    Composite valueComp = new Composite( detailsForm, SWT.NONE );
    valueComp.setLayout( new GridLayout( 1, false ) );
    
    Label valueLabel = new Label( valueComp, SWT.NONE );
    valueLabel.setText( "Field value" );
    
    this.valueText = new Text( valueComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.READ_ONLY | SWT.MULTI );
    this.valueText.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
    
    Display display = Display.getDefault();
    FontData[] fData = display.getSystemFont().getFontData();
    int fHeight = ( ( fData != null ) && ( fData.length > 0 ) ) ? fData[ 0 ].getHeight() : 10; 
    this.valueText.setFont( new Font( Display.getDefault(), new FontData( "Courier", fHeight, SWT.NONE ) ) );
    
    detailsForm.setWeights( new int[] { 1, 2, 1 } );
    
    this.chainTable.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        fillDetailsTree();
      }
    } );
    
    this.detailsTree.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        TreeItem item = ( TreeItem ) e.item;
        if ( ( item != null ) && ( item.getData() != null )
            && ! CertificateInfoPanel.this.valueText.isDisposed() ) {
          CertificateInfoPanel.this.valueText.setText( item.getData().toString() );
        } else {
          CertificateInfoPanel.this.valueText.setText( "" );
        }
      }
    } );
    
    updateOverviewPanel();
    
  }
  
  public X509Certificate[] getCertificateChain() {
    return this.certificateChain;
  }
  
  public X509Certificate getSelectedCertificate() {
    
    X509Certificate result = null;
    
    if ( ( this.chainTable != null ) && ! this.chainTable.isDisposed() ) {
      TableItem[] items = this.chainTable.getSelection();
      if ( ( items != null ) && ( items.length > 0 ) ) {
        result = ( X509Certificate ) items[ 0 ].getData();
      }
    }
    
    return result;
    
  }
  
  public X509Certificate getSubjectCertificate() {
    return ( this.certificateChain != null ) && ( this.certificateChain.length > 0 )
      ? this.certificateChain[ 0 ]
      : null;
  }
  
  public void setCertificate( final X509Certificate cert ) {
    setCertificateChain( new X509Certificate[] { cert } );
  }
  
  public void setCertificateChain( final X509Certificate[] chain ) {
    this.certificateChain = chain;
    fillOverviewPanel();
    fillChainTable();
    fillDetailsTree();
  }
  
  protected void fillChainTable() {
    
    this.chainTable.removeAll();
    
    X509Certificate[] chain = getCertificateChain();
    
    if ( chain != null ) {
      for ( X509Certificate cert : chain ) {
        TableItem item = new TableItem( this.chainTable, SWT.NONE );
        item.setText( cert.getSubjectDN().getName() );
        item.setData( cert );
      }
    }
    
    this.chainTable.setSelection( 0 );
      
  }
  
  protected void fillDetailsTree() {
    
    this.detailsTree.removeAll();
    this.valueText.setText( "" );
    
    X509Certificate certificate = getSelectedCertificate();
    
    if ( certificate != null ) {
      
      TreeItem certItem = new TreeItem( this.detailsTree, SWT.NONE );
      certItem.setText( "Certificate" );
      
      TreeItem tbsItem = createTreeItem( certItem, "TBS Certificate", "" );
        createTreeItem( tbsItem, "Version", String.valueOf( certificate.getVersion() ) );
        createTreeItem( tbsItem, "Serial Number", X509Util.formatSerialNumber( certificate.getSerialNumber() ) );
        createTreeItem( tbsItem, "Signature", certificate.getSigAlgName() );
        createTreeItem( tbsItem, "Issuer", certificate.getIssuerX500Principal().getName() );
        TreeItem validItem = createTreeItem( tbsItem, "Validity", "" );
          createTreeItem( validItem, "Not Before", certificate.getNotBefore().toString() );
          createTreeItem( validItem, "Not After", certificate.getNotAfter().toString() );
        createTreeItem( tbsItem, "Subject", certificate.getSubjectX500Principal().getName() );
        TreeItem subPubKeyInfoItem = createTreeItem( tbsItem, "Subject Public Key Info", "" );
          createTreeItem( subPubKeyInfoItem, "Subject Public Key Algorithm", certificate.getPublicKey().getAlgorithm() );
          createTreeItem( subPubKeyInfoItem, "Subject Public Key", X509Util.formatEncodedData( certificate.getPublicKey().getEncoded() ) );
        TreeItem extItem = createTreeItem( tbsItem, "Extensions", "" );
        fillExtensions( extItem, certificate );
      TreeItem sigAlgItem = createTreeItem( certItem, "Signature Algorithm", certificate.getSigAlgName() );
      TreeItem sigItem = createTreeItem( certItem, "Signature", X509Util.formatEncodedData( certificate.getSignature() ) );

      certItem.setExpanded( true );
      tbsItem.setExpanded( true );
      validItem.setExpanded( true );
      subPubKeyInfoItem.setExpanded( true );
      sigAlgItem.setExpanded( true );
      sigItem.setExpanded( true );
      
    }
    
  }
  
  private void fillExtensions( final TreeItem extRoot, final X509Certificate certificate ) {
    fillExtensions( extRoot, certificate, true );
    fillExtensions( extRoot, certificate, false );
  }
  
  private void fillExtensions( final TreeItem extRoot, final X509Certificate certificate, final boolean critical ) {
    
    Set< String > oids
      = critical
      ? certificate.getCriticalExtensionOIDs()
      : certificate.getNonCriticalExtensionOIDs();
    
    for ( String oid : oids ) {
      TreeItem extItem = createTreeItem( extRoot, "Extensions", "" );
      createTreeItem( extItem, "Extension ID", oid );
      createTreeItem( extItem, "Critical", Boolean.toString( critical ) );
      createTreeItem( extItem, "Extension Value", X509Util.formatEncodedData( certificate.getExtensionValue( oid ) ) );
    }
    
  }
  
  protected void fillOverviewPanel() {
    
    boolean valid = false;
    
    X509Certificate certificate = getSubjectCertificate();
    
    try {
      certificate.checkValidity();
      valid = true;
    } catch ( CertificateExpiredException cexpExc ) {
      // ignored
    } catch ( CertificateNotYetValidException cnvExc ) {
      // ignored
    }
    
    Display display = Display.getDefault();
    this.validText.setText( valid ? "YES" : "NO" );
    this.validText.setForeground( valid ? display.getSystemColor( SWT.COLOR_GREEN )
                                        : display.getSystemColor( SWT.COLOR_RED ) );
    this.notBeforeText.setText( certificate.getNotBefore().toString() );
    this.notAfterText.setText( certificate.getNotAfter().toString() );
    this.sNoText.setText( X509Util.formatSerialNumber( certificate.getSerialNumber() ) );
    
    this.notBeforeText.pack();
    this.notAfterText.pack();
    this.sNoText.pack();
    
    fillX500PrincipalLabels( certificate.getSubjectX500Principal(), this.subjectLabels );
    fillX500PrincipalLabels( certificate.getIssuerX500Principal(), this.issuerLabels );
    
    updateOverviewPanel();
    
  }
  
  private void fillX500PrincipalLabels( final X500Principal principal,
                                        final Hashtable< String, Label > table ) {
    String name = principal.getName();
    String[] parts = name.split( "[,/]" );
    for ( String part : parts ) {
      int index = part.indexOf( "=" );
      if ( index >= 0 ) {
        String key = part.substring( 0, index ).trim().toUpperCase();
        Label label = table.get( key );
        if ( label != null ) {
          String value = part.substring( index+1 ).trim();
          label.setText( value );
          label.pack();
        }
      }
    }
  }
  
  private TreeItem createTreeItem( final TreeItem parent,
                                   final String field,
                                   final String value ) {
    TreeItem item = new TreeItem( parent, SWT.NONE );
    item.setText( field );
    item.setData( value );
    return item;
  }
  
  private void createX500PrincipalGroup( final Composite parent,
                                         final String title,
                                         final Hashtable< String, Label > table ) {
    
    Group group = new Group( parent, SWT.H_SCROLL );
    group.setLayout( new GridLayout( 2, false ) );
    group.setText( title );
    group.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, true, false ) );
    
    createX500PrincipalGroupContent( group, table );
    
  }
  
  private void createX500PrincipalGroupContent( final Group group,
                                                final Hashtable< String, Label > table ) {
    table.put( "CN", createX500PrincipalField( group, "Common name:" ) );
    table.put( "O", createX500PrincipalField( group, "Organization:" ) );
    table.put( "OU", createX500PrincipalField( group, "Organizational Unit:" ) );
    table.put( "L", createX500PrincipalField( group, "Locality:" ) );
    table.put( "ST", createX500PrincipalField( group, "State:" ) );
    table.put( "C", createX500PrincipalField( group, "Country:" ) );
  }
  
  private Label createX500PrincipalField( final Group group, final String name ) {
    Label nameLabel = new Label( group, SWT.NONE );
    nameLabel.setText( name );
    Label valueLabel = new Label( group, SWT.NONE );
    return valueLabel;
  }
  
  private void updateOverviewPanel() {
    Point size = this.overviewPanel.computeSize( SWT.DEFAULT, SWT.DEFAULT );
    this.overviewComp.setMinSize( size );
  }
  
}
