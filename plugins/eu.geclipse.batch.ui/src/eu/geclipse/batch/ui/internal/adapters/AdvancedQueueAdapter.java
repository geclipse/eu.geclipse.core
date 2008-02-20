/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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

import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QueueType;


/**
 * @author nickl
 *
 */
public class AdvancedQueueAdapter extends QdlAdaptersFactory {
  
  
  protected QueueType queue = QdlFactory.eINSTANCE.createQueueType();
  
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  /**
   * AdvancedQueueAdapter class default constructor.
   * 
   * Get's as parameter the input type for this adapter {@link QueueType}
   * 
   * @param queue
   */
  public AdvancedQueueAdapter( final QueueType queue ) {
    
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
   
   /**
    * Loads the Queue Attributes from the QDL file to the Editor Page
    */
   public void load() {
    //
    } // end void load()
   
}
