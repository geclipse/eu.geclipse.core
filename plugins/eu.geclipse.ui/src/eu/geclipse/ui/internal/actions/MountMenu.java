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

public class MountMenu
    extends CompoundContributionItem
    implements ISelectionChangedListener {
  
  private List< String > protocols
    = new ArrayList< String >();
  
  private List< IGridStorage > sources
    = new ArrayList< IGridStorage >();

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
    setVisible( ( this.protocols != null ) && !this.protocols.isEmpty() );
  }
  
  @Override
  protected IContributionItem[] getContributionItems() {
    IContributionItem[] result = null;
    if ( !this.protocols.isEmpty() ) {
      result = new IContributionItem[ this.protocols.size() ];
      for ( int i = 0 ; i < this.protocols.size() ; i++ ) {
        IAction action = getAction( this.protocols.get( i ) );
        result[ i ] = new ActionContributionItem( action );
      }
    }
    return result;
  }
  
  protected IAction getAction( final String protocol ) {
    IGridStorage[] srcArray
      = this.sources.toArray( new IGridStorage[ this.sources.size() ] );
    MountAction action = new MountAction( srcArray, protocol );
    return action;
  }
  
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
  
  protected static String getProtocol( final URI accessToken ) {
    return accessToken.getScheme() + ":" + accessToken.getPort();  //$NON-NLS-1$
  }
  
}
