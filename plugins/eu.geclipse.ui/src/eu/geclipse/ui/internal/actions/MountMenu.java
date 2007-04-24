/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.actions.CompoundContributionItem;
import eu.geclipse.core.model.IGridStorage;

/**
 * Menu that holds all protocols available for a specific storage
 * element mount operation.
 */
public class MountMenu
    extends CompoundContributionItem
    implements ISelectionChangedListener {
  
  /**
   * All available protocols. 
   */
  private List< String > protocols
    = new ArrayList< String >();
  
  /**
   * The elements to be mounted.
   */
  private List< IGridStorage > sources
    = new ArrayList< IGridStorage >();
  
  /**
   * Standard constructor.
   */
  protected MountMenu() {
    checkVisible();
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged( final SelectionChangedEvent event ) {
    this.protocols = null;
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      List< ? > selectionList
        = ( ( IStructuredSelection ) selection ).toList();
      this.sources.clear();
      for ( Object obj : selectionList ) {
        if ( obj instanceof IGridStorage ) {
          IGridStorage storage = ( IGridStorage ) obj;
          this.sources.add( storage );
          List< String > storageProtocols = getProtocols( storage );
          mergeProtocols( storageProtocols );
        }
      }
    }
    checkVisible();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
   */
  @Override
  protected IContributionItem[] getContributionItems() {
    IContributionItem[] result = new IContributionItem[ 0 ];
    if ( !this.protocols.isEmpty() ) {
      result = new IContributionItem[ this.protocols.size() ];
      for ( int i = 0 ; i < this.protocols.size() ; i++ ) {
        IAction action = getAction( this.protocols.get( i ) );
        result[ i ] = new ActionContributionItem( action );
      }
    }
    return result;
  }
  
  /**
   * Construct a mount action for the specified protocol.
   * 
   * @param protocol The protocol for which to create the action.
   * @return A new action dedicated to mount the selected storage
   * element using the specified protocol.
   */
  protected IAction getAction( final String protocol ) {
    IGridStorage[] srcArray
      = this.sources.toArray( new IGridStorage[ this.sources.size() ] );
    MountAction action = new MountAction( srcArray, protocol );
    return action;
  }
  
  /**
   * Get all protocols that are available for the specified storage element.
   * 
   * @param storage The element for which to query the protocols.
   * @return All currently available protocols for the specified storage
   * element.
   */
  protected List< String > getProtocols( final IGridStorage storage ) {
    List< String > result = new ArrayList< String >();
    URI[] accessTokens = storage.getAccessTokens();
    if ( accessTokens != null ) {
      for ( int i = 0 ; i < accessTokens.length ; i++ ) {
        result.add( getProtocol( accessTokens[i] ) );
      }
    }
    return result;
  }
  
  /**
   * Test if this menu should be visible and set the visibility property
   * accordingly.
   */
  private void checkVisible() {
    setVisible( ( this.protocols != null ) && !this.protocols.isEmpty() );
  }
  
  /**
   * Merge the specified list of protocols with the internal list of
   * already available protocols.
   * 
   * @param toMerge The list to be merged with the internal list.
   */
  private void mergeProtocols( final List< String > toMerge ) {
    if ( ( this.protocols == null )
        && ( toMerge != null )
        && !toMerge.isEmpty() ) {
      this.protocols = new ArrayList< String >();
      for ( String scheme : toMerge ) {
        if ( !this.protocols.contains( scheme ) ) {
          this.protocols.add( scheme );
        }
      }
    } else if ( this.protocols != null ) {
      List< String > mergedList = new ArrayList< String >();
      for ( String target : this.protocols ) {
        if ( toMerge.contains( target ) ) {
          mergedList.add( target );
        }
      }
      this.protocols = mergedList;
    }
  }
  
  /**
   * Parse the specified {@link URI} and extract the protocol from it.
   * 
   * @param accessToken The {@link URI} access token to be parsed.
   * @return The protocol part of the specified access token, i.e.
   * the scheme combined with the port.
   */
  protected static String getProtocol( final URI accessToken ) {
    return accessToken.getScheme() + ":" + accessToken.getPort();  //$NON-NLS-1$
  }
  
}
