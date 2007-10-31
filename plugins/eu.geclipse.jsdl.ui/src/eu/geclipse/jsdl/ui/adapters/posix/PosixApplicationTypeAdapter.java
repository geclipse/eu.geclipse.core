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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.jsdl.ui.adapters.posix;

/**
 * @author nickl
 *
 */
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.DocumentRoot;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.GroupNameType;
import eu.geclipse.jsdl.model.posix.LimitsType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.UserNameType;
import eu.geclipse.jsdl.ui.internal.Activator;


/**
 * PosixApplicationTypeAdapter Class
 *
 * This class provides adapters for manipulating <b>POSIXApplication</b> elements 
 * through the Application Page of the JSDL editor. Supported POSIXApplication 
 * elements are:
 * <p>
 * - Executable
 * <p>
 * - Argument
 * <p>
 * - Input
 * <p>
 * - Output
 * <p>
 * - Error
 * <p>
 * - Environment
 */
public class PosixApplicationTypeAdapter extends PosixAdaptersFactory {
  
  protected ApplicationType applicationType = 
                                  JsdlFactory.eINSTANCE.createApplicationType();
  protected DocumentRoot documentRoot = PosixFactory.eINSTANCE.createDocumentRoot();
  
  protected JobDescriptionType jobDescriptionType =
                                JsdlFactory.eINSTANCE.createJobDescriptionType();
  
  protected POSIXApplicationType posixApplicationType = 
                            PosixFactory.eINSTANCE.createPOSIXApplicationType();
  
  protected EnvironmentType environmentType = PosixFactory.eINSTANCE.
                                                        createEnvironmentType();

  protected ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  
  
  private Hashtable< Integer, Text > widgetFeaturesMap = 
                                               new Hashtable< Integer, Text >();
  private Hashtable< Integer, TableViewer > tableFeaturesMap = 
                                        new Hashtable< Integer, TableViewer >();

  
  
  
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  
  /**
   * PosixApplicationTypeAdapter Class Constructor
   * @param rootJsdlElement
   */
  public PosixApplicationTypeAdapter(final EObject rootJsdlElement){
    
     getTypeForAdapter(rootJsdlElement);
    
  } // End Class Constructor
  
  
  
  /*
  * Get the Application Type Element from the root Jsdl Element.
  */  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
   
   TreeIterator<EObject> iterator = rootJsdlElement.eAllContents();
   
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

  } // End getTypeforAdapters()
 
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public void setContent(final EObject rootJsdlElement){
    
   this.adapterRefreshed = true; 
   getTypeForAdapter( rootJsdlElement );   
   
  } // End setContent()
  
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  } // End void contentChanged()
  
  
  protected void checkPosixApplicationElement(){
    
    EStructuralFeature eStructuralFeature = this.documentRoot.eClass()
    .getEStructuralFeature( PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION );
    
    Collection<POSIXApplicationType> collection = 
                                          new ArrayList<POSIXApplicationType>();
    collection.add( this.posixApplicationType);
        
    if (!this.applicationType.eIsSet( eStructuralFeature )){      
      this.applicationType.eSet( eStructuralFeature, collection );


    }
  }
  
  
  protected EObject checkProxy(final EObject refEObject) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, 
                                  PosixApplicationTypeAdapter.this.posixApplicationType );
    }
        
    return eObject;
    
  }
  
  
  /**
   * Adapter interface to attach to the PosixApplication Name text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * Name attribute of a PosixApplication element in a JSDL document.
   */
  public void attachPosixApplicationName(final Text widget){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__NAME);
    this.widgetFeaturesMap.put( featureID, widget );
    
     widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
        PosixApplicationTypeAdapter.this.posixApplicationType.setName(widget.getText());
        contentChanged();
        
      }
    } );
     
  } // End void attachPosixApplicationName()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication Executable text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * PosixApplication Executable element of the JSDL document.
   */
  public void attachPosixApplicationExecutable(final Text widget){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE);
    this.widgetFeaturesMap.put( featureID , widget );    
    
   
    widget.addModifyListener( new ModifyListener() {      
      FileNameType fileName = PosixFactory.eINSTANCE.createFileNameType();
    public void modifyText( final ModifyEvent e ) {
      checkPosixApplicationElement();
      
      if (!widget.getText().equals( "" )){ //$NON-NLS-1$
        
        this.fileName.setValue( widget.getText() );

        this.fileName = (FileNameType) checkProxy( this.fileName );
       
        PosixApplicationTypeAdapter.this.posixApplicationType
                                                  .setExecutable(this.fileName);
      }
      else{
        
        PosixApplicationTypeAdapter.this.posixApplicationType
                                                          .setExecutable(null);
      }
      contentChanged();
      
    }
  } );   
    
  } // End void attachPosixApplicationExecutable()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication Input text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * PosixApplication Input element of the JSDL document.
   */
  public void attachPosixApplicationInput(final Text widget){   
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__INPUT);
    this.widgetFeaturesMap.put( featureID , widget );

    
    widget.addModifyListener( new ModifyListener() {
      FileNameType fileName = PosixFactory.eINSTANCE.createFileNameType();
      public void modifyText( final ModifyEvent e ) {
      
        checkPosixApplicationElement();
        if ( !widget.getText().equals( "" ) ) { //$NON-NLS-1$
          this.fileName.setValue( widget.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                    .setInput(this.fileName);
        }
        else{
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                            .setInput(null);
        }
        contentChanged();
        
      }
    } ); 
     
  }// End void attachPosixApplicationInput()

  
  
  /**
   * Adapter interface to attach to the PosixApplication Output text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * PosixApplication Output element of the JSDL document.
   */
  public void attachPosixApplicationOutput(final Text widget){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT);
    this.widgetFeaturesMap.put( featureID, widget );
   
    
    widget.addModifyListener( new ModifyListener() {
      FileNameType fileName = PosixFactory.eINSTANCE.createFileNameType();
      public void modifyText( final ModifyEvent e ) {
        
        if ( !widget.getText().equals( "" ) ) { //$NON-NLS-1$
          checkPosixApplicationElement();
          this.fileName.setValue( widget.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                    .setOutput(this.fileName);
        }
        else{
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                            .setOutput(null);
        }
        contentChanged();
        
      }
    } ); 
    
  } // End void attachPosixApplicationOutput()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication Error text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * PosixApplication Error element of the JSDL document.
   */
  public void attachPosixApplicationError(final Text widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ERROR);
    this.widgetFeaturesMap.put( featureID , widget );
  
    
    widget.addModifyListener( new ModifyListener() {   
      FileNameType fileName = PosixFactory.eINSTANCE.createFileNameType();
      public void modifyText( final ModifyEvent e ) {
        
        if ( !widget.getText().equals( "" ) ) { //$NON-NLS-1$
          checkPosixApplicationElement();
          this.fileName.setValue( widget.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                    .setError(this.fileName);
        }
        else{
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                            .setError(null);
        }
        contentChanged();
        
      }
    } );
    
  } // End void attachPosixApplicationError()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication Argument text widget.
   * 
   * @param widget The {@link org.eclipse.jface.viewers.TableViewer} which is associated with the 
   * PosixApplication Argument element of the JSDL document.
   */
  public void attachToPosixApplicationArgument(final TableViewer widget){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT);
    this.tableFeaturesMap.put( featureID , widget );
    
  } // End void attachToPosixApplicationArgument()
  
  
  /**
   * Adapter interface to attach to the PosixApplication Enivronment text widget.
   * 
   * @param widget The {@link org.eclipse.jface.viewers.TableViewer} which is associated with the 
   * PosixApplication Environment element of the JSDL document.
   */
  public void attachToPosixApplicationEnvironment(final TableViewer widget){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT);
    this.tableFeaturesMap.put( featureID , widget );
    
  } // End void attachToPosixApplicationEnvironment()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication WorkingDirectory text 
   * widget.
   *     
   * @param widget The SWT text widget which is associated with the 
   * PosixApplication Working Directory element of the JSDL document.
   */
  public void attachToWorkingDirectory(final Text widget){    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      DirectoryNameType directoryNameType =
                        PosixFactory.eINSTANCE.createDirectoryNameType();


      public void modifyText( final ModifyEvent e ) {
        
        if (!widget.getText().equals( "" )){ //$NON-NLS-1$
          checkPosixApplicationElement();          
          this. directoryNameType.setValue( widget.getText() );
          this.directoryNameType = (DirectoryNameType) checkProxy( this.directoryNameType );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                    .setWorkingDirectory( this.directoryNameType );
        }
        else{
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                            .setError(null);
        }
        contentChanged();
        
     }
      
      
    });

  }
  
  
 
  /**
   * @param text
   */
  public void attachToWallTimeLimit(final Text text){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT);
    this.widgetFeaturesMap.put( featureID , text );    
      
      text.addModifyListener( new ModifyListener() {  
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      BigInteger bigInteger ;
        
        public void modifyText( final ModifyEvent e ) {
        
          if (!text.getText().equals( "" )){ //$NON-NLS-1$ 
           this.bigInteger = new BigInteger(text.getText());
           this.limits.setValue( this.bigInteger );
           this.limits = (LimitsType) checkProxy( this.limits );
            PosixApplicationTypeAdapter.this.posixApplicationType.setWallTimeLimit( this.limits );
          }
          
          else{
           PosixApplicationTypeAdapter.this
           .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT ) ;
          }
          
        contentChanged();
        
        }
      } );
    }
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication FileSizeLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication Working Directory element of the JSDL document.
   */
  public void attachToFileSizeLimit(final Text text){
    
    Integer featureID = 
              new Integer(PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {
    LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
    BigInteger bigInteger ;

      public void modifyText( final ModifyEvent e ) {

      if (!text.getText().equals( "" )) { //$NON-NLS-1$ 
        this.bigInteger = new BigInteger(text.getText());
        this.limits.setValue( this.bigInteger );
        this.limits = (LimitsType) checkProxy( this.limits );
        PosixApplicationTypeAdapter.this.posixApplicationType.setFileSizeLimit( this.limits );
      }
      
     else {
       PosixApplicationTypeAdapter.this
           .deleteElement(PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
          }
        
     contentChanged();
     

        }
      } );
    
  } // End void attachToFileSizeLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication CoreDumpLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication CoreDumpLimit element of the JSDL document.
   */
  public void attachToCoreDumpLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
    BigInteger bigInteger ;
    LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();      
      
      public void modifyText( final ModifyEvent e ) {
        
        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setCoreDumpLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT );
          }
        
       contentChanged();

      }
      } );
    
  } // End void attachToCoreDumpLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication DataSegmentLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication DataSegmentLimit element of the JSDL document.
   */
  public void attachToDataSegmentLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
    BigInteger bigInteger ;
    LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();    
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );      
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setDataSegmentLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT );
          }
        
       contentChanged();
       
        }
      } );
    
  } // End void attachToDataSegmentLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication LockedMemoryLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication LockedMemoryLimit element of the JSDL document.
   */
  public void attachToLockedMemoryLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );      
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setLockedMemoryLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToLockedMemoryLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication MemoryLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication MemoryLimit element of the JSDL document.
   */
  public void attachToMemoryLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );    
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setMemoryLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToMemoryLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication OpenDescriptorLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication OpenDescriptorLimit element of the JSDL document.
   */
  public void attachToOpenDesciptorsLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );    
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setOpenDescriptorsLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToOpenDescriptorLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication OpenDescriptorLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication OpenDescriptorLimit element of the JSDL document.
   */
  public void attachToPipeSizeLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );   
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setPipeSizeLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToPipeSizeLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication StackSizeLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication StackSizeLimit element of the JSDL document.
   */
  public void attachToStackSizeLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );  
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setStackSizeLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToStackSizeLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication CPUTimeLimit text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication CPUTimeLimit element of the JSDL document.
   */
  public void attachToCPUTimeLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger ); 
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setCPUTimeLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToCPUTimeLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication ProcessCountLimit( text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication ProcessCountLimit( element of the JSDL document.
   */
  public void attachToProcessCountLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger );  
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setProcessCountLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToProcessCountLimit()
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication ProcessCountLimit( text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication ProcessCountLimit( element of the JSDL document.
   */
  public void attachToVirtualMemoryLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger ); 
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setVirtualMemoryLimit( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToVirtualMemoryLimit()
  
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication ProcessCountLimit( text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication ProcessCountLimit( element of the JSDL document.
   */
  public void attachToThreadCountLimit( final Text text ) {
    
    Integer featureID = 
              new Integer( PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT );
    
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {   
      BigInteger bigInteger ;
      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
      
      public void modifyText( final ModifyEvent e ) {

        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$
          this.bigInteger = new BigInteger(text.getText());
          this.limits.setValue( this.bigInteger ); 
          this.limits = (LimitsType) checkProxy( this.limits );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                                .setThreadCountLimit ( this.limits );
          }
        
        else {        
            PosixApplicationTypeAdapter.this
          .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT );
          }
        
          contentChanged();
        
        }      
      } );
    
  } // End void attachToThreadCountLimit()
  
  
  
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication UserName text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication UserName element of the JSDL document.
   */  
  public void attachToUserName(final Text text){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME);
    this.widgetFeaturesMap.put( featureID , text );    
      
      text.addModifyListener( new ModifyListener() {
      UserNameType userNameType = PosixFactory.eINSTANCE.createUserNameType();

      public void modifyText( final ModifyEvent e ) {
          checkPosixApplicationElement();
         
          if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$            
            this.userNameType.setValue( text.getText() );
            this.userNameType = (UserNameType) checkProxy( this.userNameType );
            PosixApplicationTypeAdapter.this.posixApplicationType
                                      .setUserName( this.userNameType );
          }
          else{
           PosixApplicationTypeAdapter.this
           .posixApplicationType.setUserName( null );
          }
          contentChanged();
          
        }
      } );
    }
  
  
  
  /**
   * Adapter interface to attach to the PosixApplication GroupName text 
   * widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * PosixApplication GroupName element of the JSDL document.
   */  
  public void attachToGroupName( final Text text ) {
    
    Integer featureID = new Integer( PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME );
    this.widgetFeaturesMap.put( featureID , text );    
      
    text.addModifyListener( new ModifyListener() {
      GroupNameType groupNameType = PosixFactory.eINSTANCE.createGroupNameType();
      
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
         
        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$            
          this.groupNameType.setValue( text.getText() );
          this.groupNameType = (GroupNameType) checkProxy( this.groupNameType );
          PosixApplicationTypeAdapter.this.posixApplicationType
                                     .setGroupName(this.groupNameType );
        }
       else{
         PosixApplicationTypeAdapter.this.posixApplicationType.setGroupName( null );
       }
        
       contentChanged();
          
       }
     } );
  }
  
  
      
  /**
   * 
   * Adapter interface to attach to the PosixApplication Delete button
   * widget.
   *  
   * @param button The SWT Button that triggered the Delete event.
   * @param viewer The {@link org.eclipse.jface.viewers.TableViewer}
   * containing the element to be deleted.
   */
  public void attachToDelete(final Button button, final TableViewer viewer){


    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(viewer);

      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  } // End void attachToDelete()
  
  
  
//  protected void checkDataStageMissMatch(final String element) {
//    DataStagingType dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
//    dataStagingType = ( DataStagingType )this.jobDescriptionType.getDataStaging();
//    
//
//    
//  }
  
  
  /**
   * @return true if the PosixApplicationTypeAdapter is Empty. 
   */
  public boolean isEmpty(){
    boolean status = false;

    if (!this.posixApplicationType.equals( null )){       
      status = true;
    }
    
    return status;
    
  } // End boolean isEmpty()
  
  
  
  /**
   * @param tableViewer
   * @param name
   * @param value
   */
  @SuppressWarnings("unchecked")
  public void performAdd ( final TableViewer tableViewer,
                           final String name, final Object[][] value ) {
        
    if ( value[0][0] == null ) {
      return;
    }
    
    Object[] valuesArray = value[0];  
    
    EList <EObject> newInputList = ( EList<EObject> )tableViewer.getInput(); 
     
    if (newInputList == null ) {
      newInputList = new BasicEList<EObject>();
    }
        

    
    /* Check if PosixApplication Element Exists */
    checkPosixApplicationElement();
    
    if ( name == "argumentViewer" ) { //$NON-NLS-1$
      
      this.argumentType = PosixFactory.eINSTANCE.createArgumentType();
      this.argumentType.setFilesystemName( valuesArray[0].toString() );
      this.argumentType.setValue( valuesArray[1].toString() );
      newInputList.add( this.argumentType );
      
      /* Add the Argument to PosixApplication */
      this.posixApplicationType.getArgument().addAll( newInputList );
      tableViewer.setInput( this.posixApplicationType.getArgument() );

      
    }
    
    else {
    
      this.environmentType = PosixFactory.eINSTANCE.createEnvironmentType();
      this.environmentType.setName( valuesArray[0].toString() );
      this.environmentType.setFilesystemName( valuesArray[1].toString() );
      this.environmentType.setValue( valuesArray[2].toString() );
      newInputList.add( this.environmentType );
      
      /* Add the Environmental Variable to PosixApplication */
      this.posixApplicationType.getEnvironment().addAll( newInputList );
      tableViewer.setInput( this.posixApplicationType.getEnvironment() );

    }
    
    
    tableViewer.refresh();   
    this.contentChanged();
    newInputList = null;

    
  }
  
  
  
  /**
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param value 
   */
  @SuppressWarnings("unchecked")
  public void performEdit(final TableViewer tableViewer, final Object[][] value) {
    
    TableViewer table = null;
    int featureID;
    
    if (value[0][0] == null) {
      return;
    }
    
    Object[] valuesArray = value[0];
        
    EStructuralFeature eStructuralFeature;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
    
    /* If the selection is not null then Change the selected element */
    if (structSelection != null) {
    
      Object feature = structSelection.getFirstElement();
      
      table = this.tableFeaturesMap.get( new Integer (PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT) );
    
      if (tableViewer == table) {
    
        featureID = PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT;
        
        eStructuralFeature = this.posixApplicationType.eClass()
                                            .getEStructuralFeature( featureID );

        

        /* Get the Index of the Element that needs to be changed */
        int index = (( java.util.List<Object> )this.posixApplicationType
                                 .eGet(eStructuralFeature)).indexOf( feature  );

        /* 
         * Create a new Argument Type EObject with the new values that will 
         * substitute the old EObject.
         */
        this.argumentType = PosixFactory.eINSTANCE.createArgumentType();
        this.argumentType.setFilesystemName( valuesArray[0].toString() );
        this.argumentType.setValue( valuesArray[1].toString() );
        
        
        /* Change the element. The element is located through it's index position
         *   in the list.
         */
        
        (( java.util.List<Object> )this.posixApplicationType
                   .eGet( eStructuralFeature )).set( index, this.argumentType );
        
      } 
      else {
        
        featureID = PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT;
        
        eStructuralFeature = this.posixApplicationType.eClass()
                                            .getEStructuralFeature( featureID );

        
        /* Get the Index of the Element that needs to be changed */
        int index = (( java.util.List<Object> )this.posixApplicationType
                                 .eGet(eStructuralFeature)).indexOf( feature  );

        /* 
         * Create a new Environment Type EObject with the new values that will 
         * substitute the old EObject.
         */
        this.environmentType = PosixFactory.eINSTANCE.createEnvironmentType();
        this.environmentType.setName( valuesArray[0].toString() );
        this.environmentType.setFilesystemName( valuesArray[1].toString() );
        this.environmentType.setValue( valuesArray[2].toString() );
        
        
        /* Change the element. The element is located through it's index position
         *   in the list.
         */
        
        (( java.util.List<Object> )this.posixApplicationType
                .eGet( eStructuralFeature )).set( index, this.environmentType );

            
      }
    

  
  
   /* Refresh the table viewer and notify the editor that
   *  the page content has changed. 
   */

    tableViewer.refresh();
    contentChanged();
    
    }  // end_if (structSelection != null)
    
  } // end void performEdit()
  
 
  
  protected void deleteElement(final int featureID){
        
    EStructuralFeature eStructuralFeature = this.posixApplicationType.eClass().getEStructuralFeature( featureID );
    
    EcoreUtil.remove( eStructuralFeature );
    
  }
  
  
  
  protected void performDelete(final TableViewer viewer ) {
    
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    Iterator<?> it = structSelection.iterator();

    /*
     * Iterate over the selections and delete them from the model.
     */
    while ( it.hasNext() ) {
    
      Object feature = structSelection.getFirstElement();
        
      if (feature instanceof ArgumentType) {
      
        ArgumentType argument = (ArgumentType) feature;    

        try {
          EcoreUtil.remove( argument);
        
        } catch( Exception e ) {
          Activator.logException( e );
        }
        
      } //end ArgumentType
      else if (feature instanceof EnvironmentType) {
        EnvironmentType environment = (EnvironmentType) feature;
        
        try {
          EcoreUtil.remove( environment );
          viewer.setInput( this.posixApplicationType.getEnvironment() );
        } catch( Exception e ) {
          Activator.logException( e );
        }
      
      } // end EnvironmentType
    }
    
    viewer.refresh();
    contentChanged();
    
  } // End void performDelete()
  
  
 
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * PosixApplication adapter.
   */ 
  @SuppressWarnings("unchecked")
  public void load()
  {
    
    this.isNotifyAllowed = false;
//    EObject this.posixApplicationType = this.posixApplicationType;
    Text widgetName = null;
    TableViewer tableName = null;
//    Object eStrFeatValue = null;
     
    /* Test if eObject is not empty. */
    
    if (this.posixApplicationType != null) {
      EClass eClass = this.posixApplicationType.eClass();
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();        
        
       
        
        if (eStructuralFeature instanceof EReference) {
          
          /* Check if Feature is Set ? */
          if (this.posixApplicationType.eIsSet( eStructuralFeature )) {
            
          /* Check for the features Multiplicity.
           * and if the associated widget key is contained in the FeatureMap.
           */
          
          if (eStructuralFeature.getUpperBound() 
            != ETypedElement.UNBOUNDED_MULTIPLICITY
            && this.widgetFeaturesMap.containsKey( new Integer(featureID)) ) {
           
           EObject eObject = (EObject) this.posixApplicationType.eGet( eStructuralFeature );           
                
//           eStrFeatValue = new Object();
          
////           if (ExtendedMetaData.INSTANCE.getContentKind(eObject.eClass()) 
//               == ExtendedMetaData.SIMPLE_CONTENT) {
//             
//             eStrFeatValue = eObject.eGet(ExtendedMetaData.INSTANCE.
//                                            getSimpleFeature(eObject.eClass())); 
            
//           } // End if SIMPLE.CONTENT

           widgetName = this.widgetFeaturesMap.get( new Integer(featureID) );
           
                        
             switch( featureID ) {
              case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:{
                           
//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getExecutable().getValue());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:{
                             
//                widgetName.setText(eStrFeatValue.toString()); 
                widgetName.setText(this.posixApplicationType.getInput().getValue());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:{
                
//                widgetName.setText(eStrFeatValue.toString());  
                widgetName.setText(this.posixApplicationType.getOutput().getValue());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:{
                
//                widgetName.setText(eStrFeatValue.toString());  
                widgetName.setText(this.posixApplicationType.getError().getValue());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__WORKING_DIRECTORY: {
//                widgetName.setText( eStrFeatValue.toString() );
                widgetName.setText(this.posixApplicationType.getWorkingDirectory().getValue());
              }
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:{
                
//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getWallTimeLimit().getValue().toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:{
                
//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getFileSizeLimit().getValue().toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT: {     

//                    widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getCoreDumpLimit().getValue().toString());

              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__DATA_SEGMENT_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getDataSegmentLimit().getValue().toString());

              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__LOCKED_MEMORY_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getLockedMemoryLimit().getValue().toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__MEMORY_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getMemoryLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__OPEN_DESCRIPTORS_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getOpenDescriptorsLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__PIPE_SIZE_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getPipeSizeLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__STACK_SIZE_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getStackSizeLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__CPU_TIME_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getCPUTimeLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__PROCESS_COUNT_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getProcessCountLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__VIRTUAL_MEMORY_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getVirtualMemoryLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__THREAD_COUNT_LIMIT: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getThreadCountLimit().getValue().toString());
              }              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getGroupName().getValue());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME: {     

//                widgetName.setText(eStrFeatValue.toString());
                widgetName.setText(this.posixApplicationType.getUserName().getValue());
              } 
              break;

              case PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE:               
              break;
              
              default:
              break;
            }

         } // End UNBOUNDED_MULTIPLICITY
          
          else {
           
            switch( featureID ) {
              case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:                
              {
               
                tableName = this.tableFeaturesMap.get( new Integer(featureID) );
                               
//                EList<EnvironmentType> valueList = (EList<EnvironmentType>) this.posixApplicationType.eGet( eStructuralFeature );
                EList<EnvironmentType> valueList = this.posixApplicationType.getEnvironment();
                if(/* !this.adapterRefreshed
                    && */this.tableFeaturesMap.containsKey( new Integer(featureID))) {
                  
                  for (Iterator<EnvironmentType>  it = valueList.iterator(); it.hasNext();){                    
                    this.environmentType = it.next();                   
                    tableName.setInput( valueList );
                    
                    } // End Iterator
                             
                  } // Endif
               }//
                              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:                
              {                
                tableName = this.tableFeaturesMap.get( new Integer(featureID) );
                
//                EList<ArgumentType> valueList = (EList<ArgumentType>) this.posixApplicationType.eGet( eStructuralFeature );
                EList<ArgumentType> valueList = this.posixApplicationType.getArgument();
                
                
                if(/* !this.adapterRefreshed 
                    &&*/ this.tableFeaturesMap.containsKey( new Integer(featureID))) {
                  
                  for (Iterator <ArgumentType> it = valueList.iterator(); it.hasNext();) {                   
                    this.argumentType =  it.next();        
                     tableName.setInput( valueList );
                   } // End Iterator
                             
                  } // End_if
              }                
              break;
              
              default:
              break;
              
            }

            
          } // End Else
         } // End if eIsSet()
        } // End else EReference        
        // Then this is an attribute.
        else if (eStructuralFeature instanceof EAttribute) {
          
          // Get Attribute Value.
          Object value = this.posixApplicationType.eGet( eStructuralFeature );
          
          // Check if Attribute has any value
          if (this.posixApplicationType.eIsSet( eStructuralFeature ) 
              && this.widgetFeaturesMap.containsKey( new Integer(featureID) )){ 
            
             widgetName = this.widgetFeaturesMap.get( new Integer(featureID) );
             
             if (eStructuralFeature.getName().toString() != "any"){ //$NON-NLS-1$
               widgetName.setText(value.toString());
           } // End if
          
          } // End if for eIsSet

        } // End else attribute
        
        else{
          //Do Nothing.
        }

     } // End Iterator.
      
      
    } // End if    
    this.isNotifyAllowed = true;
    
  } // End void load()
    
  
} // End Class
