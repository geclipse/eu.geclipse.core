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
package eu.geclipse.batch.ui.internal.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType;
import eu.geclipse.batch.model.qdl.BoundaryType;
import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueStatusEnumeration;
import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeValueType;
import eu.geclipse.batch.ui.internal.Activator;


/**
 * @author nloulloud
 *
 */
public class QueueAdapter extends QdlAdaptersFactory {
  
  protected HashMap< Integer, Text > textWidgetMap = new HashMap< Integer, Text >();
  protected HashMap< Integer, Combo > comboWidgetMap = new HashMap< Integer, Combo >();
  protected HashMap< Integer, TableViewer > viewerWidgetMap = new HashMap< Integer, TableViewer >();
  protected HashMap<Integer, Spinner>spinnerWidgetMap = new HashMap<Integer, Spinner>();
  protected AllowedVirtualOrganizationsType allowedVOs = QdlFactory.eINSTANCE.createAllowedVirtualOrganizationsType();
  protected QueueType queue = QdlFactory.eINSTANCE.createQueueType();
  
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  
  
  /**
   * QueueAdapter class default constructor.
   * 
   * Get's as parameter the input type for this adapter {@link QueueType}
   * 
   * @param queue
   */
  public QueueAdapter(final QueueType queue) {
    
    getTypeForAdapter (queue);
    
  }
  
  
  
  protected void contentChanged(){
    if ( this.isNotifyAllowed ){
      
      fireNotifyChanged( null);
    }
    
  } // End void contentChanged()
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param q
   */
  public void setContent(final QueueType q){
    
    getTypeForAdapter( q );
    
  } // End void setContent()
  
  
  
  /*
   * Get the Queue Element from the QDL Element.
   */
   private void  getTypeForAdapter(final QueueType q) {
     
        this.queue = q;
        
   } // End void getTypeForAdapter()
   
   
   
   protected EObject checkProxy(final EObject refEObject) {
     
     EObject eObject = refEObject;
     
     if (eObject != null && eObject.eIsProxy() ) {
      
       eObject =  EcoreUtil.resolve( eObject, 
                                   QueueAdapter.this.queue );
     }
         
     return eObject;
     
   }
   
   
   /**
    * @param text The {@link Text} widget that handles operations on the Queue Name.
    */
   public void attachQueueName(final Text text){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__QUEUE_NAME);
     this.textWidgetMap.put( featureID, text );
     
       text.addModifyListener( new ModifyListener() {
               
       public void modifyText( final ModifyEvent e ) {
         QueueAdapter.this.queue.setQueueName( text.getText() );
         contentChanged();
         
       }
     } );   
       
   } // End void attachQueueName()
   
   
   
   /**
    * @param text The {@link Text} widget that handles operations on the Queue Description.
    */
   public void attachQueueDescription(final Text text){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__DESCRIPTION);
     this.textWidgetMap.put( featureID, text );
     
       text.addModifyListener( new ModifyListener() {
               
       public void modifyText( final ModifyEvent e ) {
         QueueAdapter.this.queue.setDescription( text.getText() );
         contentChanged();
         
       }
     } );   
       
   } // End void attachQueueDescription()

   
   
   /**
    * @param combo The {@link Combo} widget that handles operations on the Queue Type.
    */
   public void attachQueueType(final Combo combo){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__QUEUE_TYPE);
     this.comboWidgetMap.put( featureID, combo );
     
     /* Populate the Combo Box with the QDL Status Literals. */    
     EEnum cFEnum = QdlPackage.Literals.QUEUE_TYPE_ENUMERATION;
     
     for (int i=0; i<cFEnum.getELiterals().size(); i++){         
          combo.add( cFEnum.getEEnumLiteral( i ).toString() );
     }  
          
     combo.addSelectionListener( new SelectionListener() {

       public void widgetDefaultSelected( final SelectionEvent e ) {
         // Auto-generated method stub
       }

       public void widgetSelected( final SelectionEvent e ) {

         String selection = combo.getItem( combo.getSelectionIndex() );
         QueueAdapter.this.queue.setQueueType(QueueTypeEnumeration.get( selection ));
         contentChanged();
       }
     });
       
   } // End void attachQueueType()
   
   
   
   /**
    * 
    * @param combo The {@link Combo} widget that handles operations on the Queue Status.
    */
   public void attachQueueStatus(final Combo combo){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__QUEUE_STATUS);
     this.comboWidgetMap.put( featureID, combo );
     
     /* Populate the Combo Box with the QDL Status Literals. */    
     EEnum cFEnum = QdlPackage.Literals.QUEUE_STATUS_ENUMERATION;
     
     for (int i=0; i<cFEnum.getELiterals().size(); i++){         
          combo.add( cFEnum.getEEnumLiteral( i ).toString() );
     }  
     
     combo.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Auto-generated method stub
      }

      public void widgetSelected( final SelectionEvent e ) {

        String selection = combo.getItem( combo.getSelectionIndex() );
        QueueAdapter.this.queue.setQueueStatus(QueueStatusEnumeration.get( selection ));
        contentChanged();
      }
       
     });
      
   } // End void attachQueueStatus()
   
   
   
   /**
    * 
    * @param combo The {@link Combo} widget that handles operations on the Queue Status.
    */
   public void attachQueueStarted(final Combo combo){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__QUEUE_STARTED);
     this.comboWidgetMap.put( featureID, combo );
     
     combo.add( "true" ); //$NON-NLS-1$
     combo.add( "false" ); //$NON-NLS-1$
     
     combo.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Auto-generated method stub
      }

      public void widgetSelected( final SelectionEvent e ) {

        QueueAdapter.this.queue.setQueueStarted( Boolean.parseBoolean( combo.getText() ) );
        contentChanged();
      }
       
     });
      
   } // End void attachQueueStatus()
   
   
   /**
    * Adapter interface to attach to the Max CPU Time Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * CPU_TIME_LIMIT element of the QDL document.
    */
   public void attachMaxCPUTimeSpinner(final Spinner spinner){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT);
     this.spinnerWidgetMap.put( featureID,spinner );
     
     spinner.addModifyListener( new ModifyListener(){
       RangeValueType rangeValueType = QdlFactory.eINSTANCE.createRangeValueType();
       BoundaryType boundaryType = QdlFactory.eINSTANCE.createBoundaryType();
      public void modifyText( final ModifyEvent e ) {
        
        this.boundaryType.setValue( spinner.getSelection() );
        this.boundaryType = (BoundaryType) checkProxy( this.boundaryType );
        this.rangeValueType = (RangeValueType) checkProxy( this.rangeValueType );
        this.rangeValueType.setUpperBoundedRange( this.boundaryType );
        QueueAdapter.this.queue.setCPUTimeLimit( this.rangeValueType );
        contentChanged();
        
      }
       
     });
     
   }
   
   
   
   /**
    * Adapter interface to attach to the Max Wall Time Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * WALL_TIME_LIMIT element of the QDL document.
    */
   public void attachMaxWallTimeSpinner(final Spinner spinner){
     
     Integer featureID = Integer.valueOf (QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT);
     this.spinnerWidgetMap.put( featureID,spinner );
     
     spinner.addModifyListener( new ModifyListener(){
       RangeValueType rangeValueType = QdlFactory.eINSTANCE.createRangeValueType();
       BoundaryType boundaryType = QdlFactory.eINSTANCE.createBoundaryType();
      public void modifyText( final ModifyEvent e ) {
        
        this.boundaryType.setValue( spinner.getSelection() );
        this.boundaryType = (BoundaryType) checkProxy( this.boundaryType );
        this.rangeValueType = (RangeValueType) checkProxy( this.rangeValueType );
        this.rangeValueType.setUpperBoundedRange( this.boundaryType );
        QueueAdapter.this.queue.setWallTimeLimit( this.rangeValueType );
        contentChanged();
        
        
      }
       
     });
     
   }
   
   
   /**
    * Adapter interface to attach to the AllowedVirtualOrganizations TableViewer widget.
    * 
    * @param tableViewer The {@link TableViewer} which is associated with the 
    * AllowedVirtualOrganizations element of the QDL document.
    */
   public void attachAllowedVOs(final TableViewer tableViewer){
     
     Integer featureID = Integer.valueOf(QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS);
     this.viewerWidgetMap.put( featureID , tableViewer );
        
   } // End attachToHostName()
   
   
   
   /**
    * Add's a new Allowed Virtual Organization to a {@link TableViewer} that holds the VO's 
    * for the {@link QueueType}. 
    * 
   * @param tableViewer The {@link TableViewer} that contains all allowed VO's for the Queue.
   * @param value The VO name.
   */
  @SuppressWarnings("unchecked")
  public void addAllowedVO(final TableViewer tableViewer, final Object value) {
     
     if (value == null) {
       return;
     }

     Collection<String> collection = new ArrayList<String>();

     
     EList <String> newInputList = ( EList<String> )tableViewer.getInput(); 
         
     
     if (newInputList == null) {
       newInputList = new BasicEList<String>();
     }
     
            
     newInputList.add( (String)value );  
     

     tableViewer.setInput( newInputList  );
     
     
     for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
       collection.add( (String) tableViewer.getElementAt( i ) );
     }
     
     this.allowedVOs.getVOName().clear();
     this.allowedVOs.getVOName().addAll( collection );
     
     if (this.queue.getAllowedVirtualOrganizations() == null) {
       this.queue.setAllowedVirtualOrganizations( this.allowedVOs );
     }


     this.contentChanged();
     

     collection = null;
     
   } // end void addCandidateHosts()
  
  
  
  /**
   * Edit a selected Virtual Organizations (VO) name.
   * 
   * @param tableViewer The {@link TableViewer} that contains all allowed VO's for the {@link QueueType}.
   * @param value The new value for the VO Name.
   */
  @SuppressWarnings("unchecked")
  public void editAllowedVO(final TableViewer tableViewer, final Object value) {
    
    if (value == null) {
      return;
    }
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null then Change the selected element */
    if (structSelection != null) {
      
      
      Object feature = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */

      int idx = this.allowedVOs.getVOName().indexOf( feature );
      
    /* Change the element. The element is located through it's index position
     * in the list.
     */

      try {
        this.allowedVOs.getVOName().set( idx, value.toString() );
      } catch( Exception e ) {
        Activator.logException( e );
      }
      
      
  
    /* Refresh the table viewer and notify the editor that
     *  the page content has changed. 
     */
      tableViewer.refresh();
      contentChanged();
    
    }  
    
  } // end void editAllowedVO()
  
  
  
  /**
   * Delete a selected Virtual Organizations (VO) name.
   * 
   * @param tableViewer The {@link TableViewer} that contains all allowed VO's for the {@link QueueType}. 
   */
  public void deleteAllowedVo( final TableViewer tableViewer ) {
    
    
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
    

    if (structSelection != null) {
      
    
      Iterator<?> it = structSelection.iterator();

      /*
       * Iterate over the selections and delete them from the model.
       */
      while ( it.hasNext() ) {
    
        Object feature = it.next();
           
        try {
          /* Delete only Multi-Valued Elements */
          
          if ( !this.adapterRefreshed ) {
            
            this.allowedVOs.getVOName().remove( feature );
              
            if ( this.allowedVOs.getVOName().size() == 0 ) {
                
              EcoreUtil.remove( this.allowedVOs );
              
            }
                        
            contentChanged();
    
          }
        else {
          tableViewer.remove( feature );
        }
          
      } //end try
      catch ( Exception e ) {
        Activator.logException( e );
      }
    
    /*
     * Refresh the table viewer and notify the editor that the page content has
     * changed.
     */  
    tableViewer.refresh();
    
      } // end While
      
    }// end_if
    
    
  } //end performDelete()
   
   
      
   /**
    * Loads the Queue Attributes from the QDL file to the Editor Page
    */
   public void load()
    {
     
      this.isNotifyAllowed = false;
      Text text = null;
      Combo combo = null;
      Spinner spinner = null;
      TableViewer tableViewer = null;
      
      BoundaryType boundaryType = null;
      RangeValueType rangeValueType = null;
        
      // Test if eObject is not empty.
      if ( this.queue != null ) {
        EClass eClass = this.queue.eClass();
        int featureID;
        
        EList<EStructuralFeature> eAllStructuralFeaures = eClass.getEAllStructuralFeatures();
        
        for( EStructuralFeature eStructuralFeature : eAllStructuralFeaures ) {
         
          featureID = eStructuralFeature.getFeatureID();

          if ( this.queue.eIsSet( eStructuralFeature ) ){
          
            if (eStructuralFeature instanceof EReference) {
              
              switch( featureID ) {
                case QdlPackage.QUEUE_TYPE__ALLOWED_VIRTUAL_ORGANIZATIONS : {
                  tableViewer = this.viewerWidgetMap.get( Integer.valueOf( featureID ) );
                  
                  this.allowedVOs = (AllowedVirtualOrganizationsType) this.queue
                                                        .eGet( eStructuralFeature );
                  
                  EList<String> valueEList = this.allowedVOs.getVOName();
              
                  tableViewer.setInput( valueEList  ); 
                }
                  
                break;
                case QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT: {
                  if (this.queue.getCPUTimeLimit() != null ){
                    spinner = this.spinnerWidgetMap.get( Integer.valueOf(QdlPackage.QUEUE_TYPE__CPU_TIME_LIMIT) );
                    
                    rangeValueType = this.queue.getCPUTimeLimit();
                    if ( rangeValueType.getUpperBoundedRange() != null ) {
                      boundaryType = rangeValueType.getUpperBoundedRange();
                    }
                    else{
                      boundaryType = rangeValueType.getLowerBoundedRange();
                    }
                    spinner.setSelection( (int)boundaryType.getValue() );
                    
                  }
                }                  
                break;
                case QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT: {
                  if (this.queue.getCPUTimeLimit() != null ){
                    spinner = this.spinnerWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__WALL_TIME_LIMIT) );
                    
                    rangeValueType = this.queue.getWallTimeLimit();
                    if ( rangeValueType.getUpperBoundedRange() != null ) {
                      boundaryType = rangeValueType.getUpperBoundedRange();
                    }
                    else{
                      boundaryType = rangeValueType.getLowerBoundedRange();
                    }
                    spinner.setSelection( (int)boundaryType.getValue() );
                    
                  }
                }                  
                break;   
                default:
                break;
              } // end switch
              
            } // end_if (eStructuralFeature instanceof EReference)
            
            else {
              switch( featureID ) {
                case QdlPackage.QUEUE_TYPE__QUEUE_NAME: {
                  if (this.queue.getQueueName() != null ){
                    text = this.textWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__QUEUE_NAME) );
                    text.setText( this.queue.getQueueName() );
                  }
                }                  
                break;
                case QdlPackage.QUEUE_TYPE__DESCRIPTION: {
                  if (this.queue.getDescription() != null ){
                    text = this.textWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__DESCRIPTION) );
                    text.setText( this.queue.getDescription() );
                  }
                }                  
                break;
                case QdlPackage.QUEUE_TYPE__QUEUE_STATUS: {
                  if (this.queue.getQueueStatus() != null ){
                    combo = this.comboWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__QUEUE_STATUS) );
                    combo.setText( this.queue.getQueueStatus().getLiteral() );
                  }
                }                  
                break;
                case QdlPackage.QUEUE_TYPE__QUEUE_STARTED: {                  
                    combo = this.comboWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__QUEUE_STARTED) );
                    combo.setText( Boolean.toString( this.queue.isQueueStarted() ) );
                  
                }                  
                break;
                case QdlPackage.QUEUE_TYPE__QUEUE_TYPE: {
                  if (this.queue.getQueueType() != null ){
                    combo = this.comboWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__QUEUE_TYPE) );
                    combo.setText( this.queue.getQueueType().getLiteral() );
                  }
                }                  
                break;                
                default:
                break;
              }
            } //end else
            
          }
        }
        
      } // end_if ( this.queue != null )
      
      this.isNotifyAllowed = true;
      
      if ( this.adapterRefreshed ) {
        this.adapterRefreshed = false;
      }
      
    } // End void load()
  
}// end QueueAdapter class
