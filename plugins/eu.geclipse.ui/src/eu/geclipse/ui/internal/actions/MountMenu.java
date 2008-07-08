package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.CompoundContributionItem;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.IMountable.MountPointID;

public class MountMenu
    extends CompoundContributionItem
    implements ISelectionChangedListener {
  
  private Shell shell;
  
  /**
   * The elements to be mounted.
   */
  private List< IMountable > mountables
    = new ArrayList< IMountable >();
  
  private List< MountPointID > mergedIDs
    = new ArrayList< MountPointID >();
  
  public MountMenu( final Shell shell ) {
    super( "eu.geclipse.ui.actions.newmount" );
    this.shell = shell;
    checkVisible();
  }

  @Override
  protected IContributionItem[] getContributionItems() {
    IContributionItem[] result = new IContributionItem[ 0 ];
    if ( ! this.mergedIDs.isEmpty() ) {
      result = new IContributionItem[ this.mergedIDs.size() ];
      for ( int i = 0 ; i < this.mergedIDs.size() ; i++ ) {
        IAction action = getAction( this.mergedIDs.get( i ) );
        result[ i ] = new ActionContributionItem( action );
      }
    }
    return result;
  }

  public void selectionChanged( final SelectionChangedEvent event ) {
    
    this.mountables.clear();
    this.mergedIDs.clear();
    ISelection selection = event.getSelection();
    
    if ( selection instanceof IStructuredSelection ) {
    
      List< ? > selectionList = ( ( IStructuredSelection ) selection ).toList();
      
      for ( Object obj : selectionList ) {
        if ( obj instanceof IMountable ) {
          IMountable mount = ( IMountable ) obj;
          this.mountables.add( mount );
        }
      }
      
    }
    
    this.mergedIDs = getMergedIDs( this.mountables );
    
    checkVisible();
    
  }
  
  private List< MountPointID > getMergedIDs( final List< IMountable > mounts ) {
    
    List< MountPointID > result = new ArrayList< MountPointID >();
    
    for ( int i = 0 ; i < mounts.size() ; i++ ) {
      MountPointID[] mountIDs = mounts.get( i ).getMountPointIDs();
      if ( mountIDs != null ) {
        result = mergeIDs( result, mountIDs, i == 0 );
      }
    }
    
    return result;
    
  }
  
  private List< MountPointID > mergeIDs( final List< MountPointID > ids,
                                         final MountPointID[] mountIDs,
                                         final boolean first ) {
    
    List< MountPointID > result = ids;
    
    if ( first ) {
      for ( MountPointID mountID : mountIDs ) {
        if ( isSupported( mountID ) ) {
          result.add( mountID );
        }
      }
    }
    
    else {
      result = new ArrayList< MountPointID >();
      for ( MountPointID id : ids ) {
        for ( MountPointID mountID : mountIDs ) {
          if ( id.equals( mountID ) ) {
            result.add( id );
            break;
          }
        }
      }
    }
    
    return result;
    
  }
  
  /**
   * Construct a mount action for the specified protocol.
   * 
   * @param mountID The ID for which to create the action.
   * @return A new action dedicated to mount the selected storage
   * element using the specified protocol.
   */
  protected IAction getAction( final MountPointID mountID ) {
    IMountable[] srcArray
      = this.mountables.toArray( new IMountable[ this.mountables.size() ] );
    MountAction action = new MountAction( this.shell, srcArray, mountID );
    return action;
  }
  
  /**
   * Test if this menu should be visible and set the visibility property
   * accordingly.
   */
  private void checkVisible() {
    setVisible( ! this.mergedIDs.isEmpty() );
  }
  
  private boolean isSupported( final MountPointID mountID ) {
    List< String > schemes = Extensions.getRegisteredFilesystemSchemes();
    return schemes.contains( mountID.getScheme() );
  }
  
}
