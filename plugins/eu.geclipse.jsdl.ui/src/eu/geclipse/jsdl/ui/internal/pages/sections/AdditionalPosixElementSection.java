/**
 * 
 */
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.DocumentRoot;
import eu.geclipse.jsdl.model.posix.GroupNameType;
import eu.geclipse.jsdl.model.posix.LimitsType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.UserNameType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


/**
 * @author nloulloud
 *
 */
public class AdditionalPosixElementSection extends JsdlAdaptersFactory {
  
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$ 
  protected Label lblWallTimeLimit = null;
  protected Label lblFileSizeLimit = null;
  protected Label lblCoreDumpLimit = null;
  protected Label lblDataSegmentLimit = null;
  protected Label lblLockedMemoryLimit = null;
  protected Label lblMemoryLimit = null;
  protected Label lblOpenDescriptorsLimit = null;
  protected Label lblPipeSizeLimit = null;
  protected Label lblStackSizeLimit = null;
  protected Label lblCPUTimeLimit = null;
  protected Label lblProcessCountLimit = null;
  protected Label lblVirtualMemoryLimit = null;
  protected Label lblThreadCountLimit = null;
  protected Label lblUserName = null;
  protected Label lblGroupName = null;
  protected Label lblUnits = null;
  protected Label lblWorkingDirectory = null;
  protected Text txtWorkingDirectory = null;
  protected Text txtWallTimeLimit = null;
  protected Text txtFileSizeLimit = null;
  protected Text txtCoreDumpLimit = null;
  protected Text txtDataSegmentLimit = null;
  protected Text txtLockedMemoryLimit = null;
  protected Text txtMemoryLimit = null;
  protected Text txtOpenDescriptorsLimit = null;
  protected Text txtPipeSizeLimit = null;
  protected Text txtStackSizeLimit = null;
  protected Text txtCPUTimeLimit = null;
  protected Text txtProcessCountLimit = null;
  protected Text txtVirtualMemoryLimit = null;
  protected Text txtThreadCountLimit = null;
  protected Text txtUserName = null;
  protected Text txtGroupName = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected JobDescriptionType jobDescriptionType = null;
  protected ApplicationType applicationType = null;
  protected DocumentRoot documentRoot = PosixFactory.eINSTANCE.createDocumentRoot();
  protected POSIXApplicationType posixApplicationType = null;
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  
  public AdditionalPosixElementSection( final Composite parent, final FormToolkit toolkit ) {

    createSection( parent, toolkit );
    
  }
    
  
  private void createSection( final Composite parent, final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "JobApplicationPage_additionalPosixApplElementTitle" ); //$NON-NLS-1$
    String sectionDescripiton = Messages.getString( "JobApplicationPage_additionalPosixApplDescr" ); //$NON-NLS-1$
    TableWrapData td;
    Composite client = FormSectionFactory.createExpandableSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescripiton,
                                                                   3,
                                                                   true );
    
    
    /* ========================= Working Directory =========================== */
    this.lblWorkingDirectory = toolkit.createLabel( client,
                                                    Messages.getString(
                                                               "JobApplicationPage_WorkingDirectory" ) ); //$NON-NLS-1$
    
    this.txtWorkingDirectory = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtWorkingDirectory.setLayoutData( td );
    
    this.txtWorkingDirectory.addModifyListener( new ModifyListener() {
      DirectoryNameType directoryNameType;

      public void modifyText( final ModifyEvent e ) {
        
        if ( !AdditionalPosixElementSection.this.txtWorkingDirectory.getText().equals( EMPTY_STRING ) ){
          if (null == this.directoryNameType) {
            this.directoryNameType = PosixFactory.eINSTANCE.createDirectoryNameType();
          }
          checkPosixApplicationElement();          
          this. directoryNameType.setValue( AdditionalPosixElementSection.this.txtWorkingDirectory.getText() );
          this.directoryNameType = (DirectoryNameType) checkProxy( this.directoryNameType );
          AdditionalPosixElementSection.this.posixApplicationType.setWorkingDirectory( this.directoryNameType );
        }
        else{
          if (null != this.directoryNameType) {
            this.directoryNameType = (DirectoryNameType) checkProxy( this.directoryNameType );
            this.directoryNameType = null;
            AdditionalPosixElementSection.this.posixApplicationType.setWorkingDirectory (null );
          }

        }
        contentChanged();
        
     }
      
      
    });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Wall Time Limit ========================= */
    this.lblWallTimeLimit = toolkit.createLabel( client,
                                              Messages.getString( "JobApplicationPage_WallTimeLimit" ) ); //$NON-NLS-1$
    this.txtWallTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtWallTimeLimit.setLayoutData( td );
    this.txtWallTimeLimit.addModifyListener( new ModifyListener() {
    LimitsType limitsType ;
    BigInteger bigInteger ;

      public void modifyText( final ModifyEvent e ) {
        
        
        
//        if ( !AdditionalPosixElementSection.this.txtWallTimeLimit.getText().equals( EMPTY_STRING ) ){
//          if ( null == this.limitsType) {
//            this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
//          }
//         
//          checkPosixApplicationElement();          
//          this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtWallTimeLimit.getText());
//          this.limitsType.setValue( this.bigInteger );
//          this.limitsType = (LimitsType) checkProxy( this.limitsType );
//          AdditionalPosixElementSection.this.posixApplicationType.setWallTimeLimit( this.limitsType );
//        }
//        else{
//          if (null != this.limitsType) {
//            this.limitsType = (LimitsType) checkProxy( this.limitsType );
//            this.limitsType = null;
//            AdditionalPosixElementSection.this.posixApplicationType.setWallTimeLimit (null );
//          }
//
//        }
//        contentChanged();
//        
     }
      
      
    });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$

    
    /* ============================ File Size Limit ========================== */
    this.lblFileSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( 
                                                                  "JobApplicationPage_FileSizeLimit" ) ); //$NON-NLS-1$
    this.txtFileSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtFileSizeLimit.setLayoutData( td );
    this.txtFileSizeLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtFileSizeLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtFileSizeLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setFileSizeLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setFileSizeLimit (null );
            }

          }
          contentChanged();
          
       }
        
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$

    
     
    /* ============================ Core Dump Limit ========================== */
    this.lblCoreDumpLimit = toolkit.createLabel( client,
                                                 Messages.getString( 
                                                                  "JobApplicationPage_CoreDumpLimit" ) ); //$NON-NLS-1$
    this.txtCoreDumpLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCoreDumpLimit.setLayoutData( td );
    this.txtCoreDumpLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtCoreDumpLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtCoreDumpLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setCoreDumpLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setCoreDumpLimit (null );
            }

          }
          contentChanged();
          
       }
        
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
     
     
    /* ==========================Data Segment Limit ========================== */
    this.lblDataSegmentLimit = toolkit.createLabel( client,
                                                    Messages.getString( 
                                                               "JobApplicationPage_DataSegmentLimit" ) ); //$NON-NLS-1$
    
    this.txtDataSegmentLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtDataSegmentLimit.setLayoutData( td );
    this.txtDataSegmentLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtDataSegmentLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtDataSegmentLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setDataSegmentLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setDataSegmentLimit (null );
            }

          }
          contentChanged();
          
       }
        
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ======================== Locked Memory Limit ========================== */
    this.lblLockedMemoryLimit = toolkit.createLabel( client,
                                                     Messages.getString( 
                                                              "JobApplicationPage_LockedMemoryLimit" ) ); //$NON-NLS-1$
    this.txtLockedMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtLockedMemoryLimit.setLayoutData( td );
    this.txtLockedMemoryLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtLockedMemoryLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtLockedMemoryLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setLockedMemoryLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setLockedMemoryLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client,  Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* =============================== Memory Limit ========================== */
    this.lblMemoryLimit = toolkit.createLabel( client,
                                               Messages.getString( "JobApplicationPage_MemoryLimit" ) ); //$NON-NLS-1$
    this.txtMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtMemoryLimit.setLayoutData( td );
    this.txtMemoryLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtMemoryLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtMemoryLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setMemoryLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setMemoryLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ====================== Open Descriptors Limit ========================= */
    this.lblOpenDescriptorsLimit = toolkit.createLabel( client,
                                                        Messages.getString( 
                                                           "JobApplicationPage_OpenDescriptorsLimit" ) ); //$NON-NLS-1$
    this.txtOpenDescriptorsLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtOpenDescriptorsLimit.setLayoutData( td );
    this.txtOpenDescriptorsLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtOpenDescriptorsLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtOpenDescriptorsLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setOpenDescriptorsLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setOpenDescriptorsLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Pipe Size Limit ========================== */
    this.lblPipeSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( 
                                                                  "JobApplicationPage_PipeSizeLimit" ) ); //$NON-NLS-1$
    this.txtPipeSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtPipeSizeLimit.setLayoutData( td );
    this.txtPipeSizeLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtPipeSizeLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtPipeSizeLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setPipeSizeLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setPipeSizeLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* =========================== Stack Size Limit ========================== */
    this.lblStackSizeLimit = toolkit.createLabel( client,
                                                  Messages.getString( 
                                                                 "JobApplicationPage_StackSizeLimit" ) ); //$NON-NLS-1$
    this.txtStackSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtStackSizeLimit.setLayoutData( td );
    this.txtStackSizeLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtStackSizeLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtStackSizeLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setStackSizeLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setStackSizeLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* ============================= CPU Time Limit ========================== */
    this.lblCPUTimeLimit = toolkit.createLabel( client,
                                                Messages.getString( "JobApplicationPage_CPUTimeLimit" ) ); //$NON-NLS-1$
    this.txtCPUTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCPUTimeLimit.setLayoutData( td );
    this.txtCPUTimeLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtCPUTimeLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtCPUTimeLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setCPUTimeLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setCPUTimeLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$
    
    
    /* ====================== Process Count Limit============================ */
    this.lblProcessCountLimit = toolkit.createLabel( client,
                                                     Messages.getString( 
                                                             "JobApplicationPage_ProcessCountLimit" ) ); //$NON-NLS-1$
    this.txtProcessCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtProcessCountLimit.setLayoutData( td );
    this.txtProcessCountLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtProcessCountLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtProcessCountLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setProcessCountLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setProcessCountLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_process" ) ); //$NON-NLS-1$
    
    
    /* ============================= Virtual Memory Limit ==================== */
    this.lblVirtualMemoryLimit = toolkit.createLabel( client,
                                                      Messages.getString( 
                                                             "JobApplicationPage_VirtualMemoryLimit" ) ); //$NON-NLS-1$
    this.txtVirtualMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtVirtualMemoryLimit.setLayoutData( td );
    this.txtVirtualMemoryLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtVirtualMemoryLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtVirtualMemoryLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setVirtualMemoryLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setVirtualMemoryLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ========================= Thread Count Limit ========================== */
    this.lblThreadCountLimit = toolkit.createLabel( client,
                                                    Messages.getString( 
                                                                        "JobApplicationPage_ThreadCountLimit" ) ); //$NON-NLS-1$
    this.txtThreadCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtThreadCountLimit.setLayoutData( td );
    this.txtThreadCountLimit.addModifyListener( new ModifyListener() {
      LimitsType limitsType ;
      BigInteger bigInteger ;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtThreadCountLimit.getText().equals( EMPTY_STRING ) ){
            if ( null == this.limitsType) {
              this.limitsType = PosixFactory.eINSTANCE.createLimitsType();
            }
           
            checkPosixApplicationElement();          
            this.bigInteger = new BigInteger(AdditionalPosixElementSection.this.txtThreadCountLimit.getText());
            this.limitsType.setValue( this.bigInteger );
            this.limitsType = (LimitsType) checkProxy( this.limitsType );
            AdditionalPosixElementSection.this.posixApplicationType.setThreadCountLimit( this.limitsType );
          }
          else{
            if (null != this.limitsType) {
              this.limitsType = (LimitsType) checkProxy( this.limitsType );
              this.limitsType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setThreadCountLimit (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_threads" ) ); //$NON-NLS-1$
    
    /* ============================= User Name Limit ======================== */
    this.lblUserName = toolkit.createLabel( client,
                                            Messages.getString( "JobApplicationPage_UserName" ) ); //$NON-NLS-1$
    this.txtUserName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtUserName.setLayoutData( td );
    this.txtUserName.addModifyListener( new ModifyListener() {
      UserNameType userNameType;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtUserName.getText().equals( EMPTY_STRING ) ){
            if ( null == this.userNameType) {
              this.userNameType = PosixFactory.eINSTANCE.createUserNameType();
            }
           
            checkPosixApplicationElement();          
            this.userNameType.setValue( AdditionalPosixElementSection.this.txtUserName.getText());
            this.userNameType = (UserNameType) checkProxy( this.userNameType );
            AdditionalPosixElementSection.this.posixApplicationType.setUserName( this.userNameType );
          }
          else{
            if (null != this.userNameType) {
              this.userNameType = (UserNameType) checkProxy( this.userNameType );
              this.userNameType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setUserName (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    /* ============================= Group Name Limit ======================== */
    this.lblGroupName = toolkit.createLabel( client,
                                             Messages.getString( "JobApplicationPage_GroupName" ) ); //$NON-NLS-1$
    this.txtGroupName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtGroupName.setLayoutData( td );
    this.txtGroupName.addModifyListener( new ModifyListener() {
      GroupNameType groupNameType;

        public void modifyText( final ModifyEvent e ) {
          
          if ( !AdditionalPosixElementSection.this.txtGroupName.getText().equals( EMPTY_STRING ) ){
            if ( null == this.groupNameType) {
              this.groupNameType = PosixFactory.eINSTANCE.createGroupNameType();
            }
           
            checkPosixApplicationElement();          
            this.groupNameType.setValue( AdditionalPosixElementSection.this.txtGroupName.getText());
            this.groupNameType = (GroupNameType) checkProxy( this.groupNameType );
            AdditionalPosixElementSection.this.posixApplicationType.setGroupName( this.groupNameType );
          }
          else{
            if (null != this.groupNameType) {
              this.groupNameType = (GroupNameType) checkProxy( this.groupNameType );
              this.groupNameType = null;
              AdditionalPosixElementSection.this.posixApplicationType.setGroupName (null );
            }

          }
          contentChanged();
          
       }       
        
      });
    this.lblUnits = toolkit.createLabel( client, Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    toolkit.paintBordersFor( client );
    
  }
  
  
  protected void contentChanged() {
    
    if ( this.isNotifyAllowed ){
      fireNotifyChanged( null );
    }
    
  }
  
  
  /**
   * @param jobDefinition The JSDL Job Definition element.
   */
  public void setInput( final JobDefinitionType jobDefinition ) {
    
    this.adapterRefreshed = true;
    if( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
      
      TreeIterator<EObject> iterator = this.jobDefinitionType.eAllContents();
      
      while ( iterator.hasNext (  )  )  {  
        
        EObject testType = iterator.next();         
        
        if (testType instanceof JobDescriptionType) {
          this.jobDescriptionType = (JobDescriptionType) testType;
        }
        else if (testType instanceof ApplicationType) {
          this.applicationType = (ApplicationType) testType;
        }
        else if ( testType instanceof POSIXApplicationType ) {
          this.posixApplicationType = (POSIXApplicationType) testType;  
         
        } 
        
      }
      
      fillFields();
    }
    
  }
  
  
  protected void checkApplicationElement() {
    
    EStructuralFeature eStructuralFeature = this.jobDescriptionType.eClass()
          .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION );
    
    /*
     * Check if the Application element is not set. If not set then set it to its 
     * container (JobDescriptionType).
     */
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )) {      
      this.jobDescriptionType.eSet( eStructuralFeature, this.applicationType );
    }
    /* 
     * If the Application Element is set, check for any possible contents which may
     * be set. If none of the above are true, then delete the Resources Element from it's
     * container (JobDescriptionType).
     */
    else {
      if ( this.applicationType.eContents().size() == 0) {
        EcoreUtil.remove( this.applicationType );
      }
    }
  }

  
  
  protected void checkPosixApplicationElement() {
    
    EStructuralFeature eStructuralFeature = this.documentRoot.eClass()
    .getEStructuralFeature( PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION );
    
    
    Collection<POSIXApplicationType> collection = new ArrayList<POSIXApplicationType>();
    checkApplicationElement();
    collection.add( this.posixApplicationType);
        
    if ( !this.applicationType.eIsSet( eStructuralFeature ) ){      
      this.applicationType.eSet( eStructuralFeature, collection );


    }
  }
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
    
    if( null != this.posixApplicationType ) {
      if( null != this.posixApplicationType.getWorkingDirectory() ){
        this.txtWorkingDirectory.setText( this.posixApplicationType.getWorkingDirectory().getValue() );
      }else{
        this.txtWorkingDirectory.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getWallTimeLimit() ) {
        this.txtWallTimeLimit.setText( this.posixApplicationType.getWallTimeLimit().getValue().toString() );
      }else{
        this.txtWallTimeLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getFileSizeLimit() ) {
        this.txtFileSizeLimit.setText( this.posixApplicationType.getFileSizeLimit().getValue().toString() );
      }else{
        this.txtFileSizeLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getCoreDumpLimit() ) {
        this.txtCoreDumpLimit.setText( this.posixApplicationType.getCoreDumpLimit().getValue().toString() );
      }else{
        this.txtCoreDumpLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getDataSegmentLimit() ) {
        this.txtDataSegmentLimit.setText( this.posixApplicationType.getDataSegmentLimit().getValue().toString() );
      }else{
        this.txtDataSegmentLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getLockedMemoryLimit() ) {
        this.txtLockedMemoryLimit.setText( this.posixApplicationType.getLockedMemoryLimit().getValue().toString() );
      }else{
        this.txtLockedMemoryLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getMemoryLimit() ) {
        this.txtMemoryLimit.setText( this.posixApplicationType.getMemoryLimit().getValue().toString() );
      }else{
        this.txtMemoryLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getOpenDescriptorsLimit() ) {
        this.txtOpenDescriptorsLimit.setText(this.posixApplicationType.getOpenDescriptorsLimit().getValue().toString());
      }else{
        this.txtOpenDescriptorsLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getPipeSizeLimit() ) {
        this.txtPipeSizeLimit.setText(this.posixApplicationType.getPipeSizeLimit().getValue().toString() );
      }else{
        this.txtPipeSizeLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getStackSizeLimit() ) {
        this.txtStackSizeLimit.setText( this.posixApplicationType.getStackSizeLimit().getValue().toString() );
      }else{
        this.txtStackSizeLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getCPUTimeLimit() ) {
        this.txtCPUTimeLimit.setText( this.posixApplicationType.getCPUTimeLimit().getValue().toString() );
      }else{
        this.txtCPUTimeLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getProcessCountLimit() ) {
        this.txtProcessCountLimit.setText( this.posixApplicationType.getProcessCountLimit().getValue().toString() );
      }else{
        this.txtProcessCountLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getVirtualMemoryLimit() ) {
        this.txtVirtualMemoryLimit.setText( this.posixApplicationType.getVirtualMemoryLimit().getValue().toString() );
      }else{
        this.txtVirtualMemoryLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getThreadCountLimit() ) {
        this.txtThreadCountLimit.setText( this.posixApplicationType.getThreadCountLimit().getValue().toString() );
      }else{
        this.txtThreadCountLimit.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getGroupName() ) {
        this.txtGroupName.setText( this.posixApplicationType.getGroupName().getValue() );
      }else{
        this.txtGroupName.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getUserName() ) {
        this.txtUserName.setText( this.posixApplicationType.getUserName().getValue() );
      }else{
        this.txtUserName.setText( EMPTY_STRING );
      }
      
    }
    this.isNotifyAllowed = true;
    
    if( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
  
  
  /*
   * Check if the EObject is lazy loaded.
   */
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, this.posixApplicationType );
    }
        
    return eObject;
    
  }
  
}
