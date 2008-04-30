package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.actions.CompoundContributionItem;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.ui.internal.Activator;

public class TransformMenu
    extends CompoundContributionItem
    implements ISelectionChangedListener {
  
  private List< TransformAction > actions
    = new ArrayList< TransformAction >();
  
  public TransformMenu() {
    checkVisible();
  }
  
  @Override
  protected IContributionItem[] getContributionItems() {
    
    IContributionItem[] result = new IContributionItem[ 0 ];
    
    if ( ( this.actions != null ) && ( this.actions.size() > 0 ) ) {
      result = new IContributionItem[ this.actions.size() ];
      for ( int i = 0 ; i < this.actions.size() ; i++ ) {
        result[ i ] = new ActionContributionItem( actions.get( i ) );
      }
    }
    
    return result;
    
  }

  public void selectionChanged( final SelectionChangedEvent event ) {
    
    ISelection selection = event.getSelection();
    
    if ( selection instanceof IStructuredSelection ) {

      List< IGridJobDescription > descList = new ArrayList< IGridJobDescription >();
      List< ? > selectionList = ( ( IStructuredSelection ) selection ).toList();
      
      for ( Object obj : selectionList ) {
        if ( obj instanceof IGridJobDescription ) {
          descList.add( ( IGridJobDescription ) obj );
        } else {
          descList = null;
          break;
        }
      }
      
      setSelectedDescriptions( descList );
      
    }
    
  }
  
  protected void setSelectedDescriptions( final List< IGridJobDescription > descriptions ) {
    createActions( descriptions );
    checkVisible();
  }
  
  protected void checkVisible() {
    setVisible( ( this.actions != null ) && ( this.actions.size() > 0 ) );
  }
  
  @SuppressWarnings("null")
  private void createActions( final List< IGridJobDescription > descriptions ) {
    
    this.actions.clear();
    Class< ? extends IGridJobDescription > sourceType = null;
    
    if ( ( descriptions != null ) && ( descriptions.size() > 0 ) ) {
      sourceType = descriptions.get( 0 ).getClass();
      for ( int i = 1 ; i < descriptions.size() ; i++ ) {
        if ( ! descriptions.get( i ).getClass().equals( sourceType ) ) {
          sourceType = null;
          break;
        }
      }
    }
    
    if ( sourceType != null ) {
      List< IConfigurationElement > configs
        = Extensions.getRegisteredElementCreatorConfigurations( sourceType, IGridJobDescription.class );
      if ( ( configs != null ) && ( configs.size() > 0 ) ) {
        IGridJobDescription[] array = descriptions.toArray( new IGridJobDescription[ descriptions.size() ] );
        for ( IConfigurationElement element : configs ) {
          TransformAction action = getAction( element, array );
          if ( action != null ) {
            this.actions.add( action );
          }
        }
      }
    }
    
  }
  
  private TransformAction getAction( final IConfigurationElement fromElement,
                                     final IGridJobDescription[] descriptions ) {
    
    TransformAction result = null;
    
    String name
      = fromElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE );
    
    try {
      IGridElementCreator creator
        = ( IGridElementCreator ) fromElement.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
      result = new TransformAction( name, creator, descriptions );
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    
    return result;
    
  }

}
