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
package eu.geclipse.jsdl.posix;

/**
 * @author nickl
 *
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.DocumentRoot;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.GroupNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.model.posix.UserNameType;


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
  
  protected POSIXApplicationType posixApplicationType = 
                            PosixFactory.eINSTANCE.createPOSIXApplicationType();
  
  protected EnvironmentType environmentType = PosixFactory.eINSTANCE.
                                                        createEnvironmentType();

  protected ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  
  protected DirectoryNameType directoryNameType = PosixFactory.eINSTANCE
                                                     .createDirectoryNameType();
  
//  protected LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
  
  protected UserNameType userNameType = PosixFactory.eINSTANCE.createUserNameType();
  
  protected GroupNameType groupNameType = PosixFactory.eINSTANCE.createGroupNameType();
  
  private Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  private Hashtable< Integer, TableViewer > tableFeaturesMap = new Hashtable< Integer, TableViewer >();

  
  
  
  
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
         
     if (testType instanceof ApplicationType) {
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
        if (!widget.getText().equals( "" )){ //$NON-NLS-1$
          this.fileName.setValue( widget.getText() );
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
        
        if (!widget.getText().equals( "" )){ //$NON-NLS-1$
          checkPosixApplicationElement();
          this.fileName.setValue( widget.getText() );
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
        
        if (!widget.getText().equals( "" )){ //$NON-NLS-1$
          checkPosixApplicationElement();
          this.fileName.setValue( widget.getText() );
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
  
  
  
//  /**
//   * Adapter interface to attach to the PosixApplication WorkingDirectory text 
//   * widget.
//   * 
//   * @param button The SWT button which enalbes or disables the associated text 
//   * widget 
//   * @param text The SWT text widget which is associated with the 
//   * PosixApplication Working Directory element of the JSDL document.
//   */
//  public void attachToWorkingDirectory(final Button button, final Text text){
//    
//    button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
//      @Override
//     public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
////      Determines if the checkBox is checked or not
//      boolean selected = button.getSelection();
//      if (selected == true) {
//        text.setEnabled( true );
//      }
//      else
//      {
//        text.setText( "" ); //$NON-NLS-1$
//        text.setEnabled( false );
//        
//        
//      }
//     }
//     });
//  }
//  
//  
//  
//  
//  /**
//   * @param text
//   */
//  public void attachToWallTimeLimit(final Text text){
//    
//    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT);
//    this.widgetFeaturesMap.put( featureID , text );    
//      
//      text.addModifyListener( new ModifyListener() {   
////        LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
//        BigInteger bigInteger ;
//        public void modifyText( final ModifyEvent e ) {
//         
//          if (!text.getText().equals( "" )){ //$NON-NLS-1$ 
//            this.bigInteger = new BigInteger(text.getText());
//            PosixApplicationTypeAdapter.this.limits.setValue( this.bigInteger );
//            PosixApplicationTypeAdapter.this.posixApplicationType.setWallTimeLimit( PosixApplicationTypeAdapter.this.limits );
//          }
//          else{
//           PosixApplicationTypeAdapter.this
//           .deleteElement( PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT ) ;
//          }
//          contentChanged();
//          
//        }
//      } );
//    }
//  
//  
//  
//  /**
//   * Adapter interface to attach to the PosixApplication FileSizeLimit text 
//   * widget.
//   * 
//   * @param text The SWT text widget which is associated with the 
//   * PosixApplication Working Directory element of the JSDL document.
//   */
//  public void attachToFileSizeLimit(final Text text){
//    
//    Integer featureID = 
//              new Integer(PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
//    
//    this.widgetFeaturesMap.put( featureID , text );    
//      
//    text.addModifyListener( new ModifyListener() {   
////      LimitsType limits = PosixFactory.eINSTANCE.createLimitsType();
//      BigInteger bigInteger ;
//      public void modifyText( final ModifyEvent e ) {
//      
//        if (!text.getText().equals( "" )) { //$NON-NLS-1$ 
//          this.bigInteger = new BigInteger(text.getText());
//          PosixApplicationTypeAdapter.this.limits.setValue( this.bigInteger );
//          PosixApplicationTypeAdapter.this.posixApplicationType.setFileSizeLimit( PosixApplicationTypeAdapter.this.limits );
//          }
//        
//        else {
//          PosixApplicationTypeAdapter.this
//           .deleteElement(PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT);
//          }
//        
//          contentChanged();
//          
//        }
//      } );
//    
//    } // End void attachToFileSizeLimit()
//  
//  
//  
//  /**
//   * Adapter interface to attach to the PosixApplication CoreDumpLimit text 
//   * widget.
//   * 
//   * @param text The SWT text widget which is associated with the 
//   * PosixApplication CoreDumpLimit element of the JSDL document.
//   */
//  public void attachToCoreDumpLimit(final Text text){
//    
//    Integer featureID = 
//              new Integer(PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT);
//    
//    this.widgetFeaturesMap.put( featureID , text );    
//      
//    text.addModifyListener( new ModifyListener() {   
//      
//      BigInteger bigInteger ;
//      public void modifyText( final ModifyEvent e ) {      
//        if (!text.getText().equals( "" )) { //$NON-NLS-1$
//          this.bigInteger = new BigInteger(text.getText());
//         PosixApplicationTypeAdapter.this.limits.setValue( this.bigInteger );      
//          PosixApplicationTypeAdapter.this.posixApplicationType
//                                                .setCoreDumpLimit( PosixApplicationTypeAdapter.this.limits );
//          }
//        
//        else {        
//            PosixApplicationTypeAdapter.this
//          .deleteElement(PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT);
//          }
//        
//          contentChanged();
//          
//        }
//      } );
//    
//    } // End void attachToFileSizeLimit()
  
  
  
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
        public void modifyText( final ModifyEvent e ) {
          checkPosixApplicationElement();
         
          if (!text.getText().equals( "" )){ //$NON-NLS-1$            
            PosixApplicationTypeAdapter.this.userNameType.setValue( text.getText() );
            PosixApplicationTypeAdapter.this.posixApplicationType
                                      .setUserName( PosixApplicationTypeAdapter
                                                    .this.userNameType );
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
  public void attachToGroupName(final Text text){
    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME);
    this.widgetFeaturesMap.put( featureID , text );    
      
      text.addModifyListener( new ModifyListener() {
        public void modifyText( final ModifyEvent e ) {
          checkPosixApplicationElement();
         
          if (!text.getText().equals( "" )){ //$NON-NLS-1$            
            PosixApplicationTypeAdapter.this.groupNameType.setValue( text.getText() );
            PosixApplicationTypeAdapter.this.posixApplicationType
                                     .setGroupName( PosixApplicationTypeAdapter.
                                                    this.groupNameType);
          }
          else{
           PosixApplicationTypeAdapter.this
           .posixApplicationType.setGroupName( null );
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

    viewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        button.setEnabled( true );
       
        
      }
      
    });

    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(viewer);
        if (viewer.getTable().getItemCount() == 0){
          button.setEnabled( false );
        }
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  } // End void attachToDelete()
  
  
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
  
  
  
//  /**
//   * @param tableViewer
//   * @param name
//   * @param value
//   */
//  public void performAdd (final TableViewer tableViewer,
//                        final String name, final Object[] value) {
//    
//    EStructuralFeature eStructuralFeature = null;
//    Collection<Object> collection = new ArrayList<Object>();
//    int featureID;
//    
//    if (name == "argumentViewer"){ //$NON-NLS-1$
//      featureID = PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT;
//    }
//    else{
//      featureID = PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT;
//    }
//    
//    // Get EStructural Feature.
//    eStructuralFeature = this.posixApplicationType.eClass().getEStructuralFeature( featureID );
//       
//    
//    tableViewer.add(value);
//    
//     for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {
//        collection.add( tableViewer.getElementAt( i ) );
//      
//     }
//     
//
//    this.posixApplicationType.eSet(eStructuralFeature, collection);
//    
//    tableViewer.refresh();
//   
//    this.contentChanged();
//    
//    eStructuralFeature = null;
//    collection = null;
//    
//  }
  
 
  protected void deleteElement(final int featureID){
        
    EStructuralFeature eStructuralFeature = this.posixApplicationType.eClass().getEStructuralFeature( featureID );
    
    EcoreUtil.remove( eStructuralFeature );
    
  }
  
  
  
  protected void performDelete(final TableViewer viewer ){
    
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    Object feature = structSelection.getFirstElement();
    
    
    if (feature instanceof ArgumentType){
      
      ArgumentType argument = (ArgumentType) feature;    

      EcoreUtil.remove( argument);
        
    }
    else if (feature instanceof EnvironmentType) {
      
      EnvironmentType environment = (EnvironmentType) feature;
      EcoreUtil.remove( environment );
      
    }
    
    viewer.refresh();
    contentChanged();
    
  } // End void performDelete()
  
  
 
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * PosixApplication adapter.
   */ 
  public void load()
  {
    
    this.isNotifyAllowed = false;
    EObject posixEObject = this.posixApplicationType;
    Text widgetName = null;
    TableViewer tableName = null;
    Object eStrFeatValue = null;
     
    /* Test if eObject is not empty. */
    
    if (posixEObject != null) {
      EClass eClass = posixEObject.eClass();
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();        
        
       
        
        if (eStructuralFeature instanceof EReference) {
          
          /* Check if Feature is Set ? */
          if (posixEObject.eIsSet( eStructuralFeature )) {
            
          /* Check for the features Multiplicity.
           * and if the associated widget key is contained in the FeatureMap.
           */
          
          if (eStructuralFeature.getUpperBound() 
            != ETypedElement.UNBOUNDED_MULTIPLICITY
            && this.widgetFeaturesMap.containsKey( new Integer(featureID)) ) {
           
           EObject eObject = (EObject) posixEObject.eGet( eStructuralFeature );           
                
           eStrFeatValue = new Object();
          
//           if (ExtendedMetaData.INSTANCE.getContentKind(eObject.eClass()) 
//               == ExtendedMetaData.SIMPLE_CONTENT) {
             
             eStrFeatValue = eObject.eGet(ExtendedMetaData.INSTANCE.
                                            getSimpleFeature(eObject.eClass())); 
            
//           } // End if SIMPLE.CONTENT

           widgetName = this.widgetFeaturesMap.get( new Integer(featureID) );
           
                        
             switch( featureID ) {
              case PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE:{
                           
                widgetName.setText(eStrFeatValue.toString());  
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__INPUT:{
                             
                widgetName.setText(eStrFeatValue.toString());  
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT:{
                
                widgetName.setText(eStrFeatValue.toString());  
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ERROR:{
                
                widgetName.setText(eStrFeatValue.toString());  
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__WALL_TIME_LIMIT:{
                
                widgetName.setText(eStrFeatValue.toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__FILE_SIZE_LIMIT:{
                
                widgetName.setText(eStrFeatValue.toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__CORE_DUMP_LIMIT: {     

                    widgetName.setText(eStrFeatValue.toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__GROUP_NAME: {     

                widgetName.setText(eStrFeatValue.toString());
              } 
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__USER_NAME: {     

                widgetName.setText(eStrFeatValue.toString());
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
                               
                EList valueList = (EList) posixEObject.eGet( eStructuralFeature );                
                if( !this.adapterRefreshed
                    && this.tableFeaturesMap.containsKey( new Integer(featureID))) {
                  
                  for (Iterator  it = valueList.iterator(); it.hasNext();){                    
                    this.environmentType = (EnvironmentType) it.next();                   
                    tableName.setInput( valueList );
                    
                    } // End Iterator
                             
                  } // Endif
               }//
                              
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:                
              {                
                tableName = this.tableFeaturesMap.get( new Integer(featureID) );
                
                EList<ArgumentType> valueList = (EList) posixEObject.eGet( eStructuralFeature );
                
                
                if( !this.adapterRefreshed 
                    && this.tableFeaturesMap.containsKey( new Integer(featureID))) {
                  
                  for (Iterator <ArgumentType> it = valueList.iterator(); it.hasNext();) {                   
                    this.argumentType =  it.next();        
                     tableName.setInput( valueList );
                   } // End Iterator
                             
                  } // Endif
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
          Object value = posixEObject.eGet( eStructuralFeature );
          
          // Check if Attribute has any value
          if (posixEObject.eIsSet( eStructuralFeature ) 
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
