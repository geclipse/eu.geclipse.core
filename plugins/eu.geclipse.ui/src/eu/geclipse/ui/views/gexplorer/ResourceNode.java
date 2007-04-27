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
 *    Mateusz Pabis (PSNC) - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.views.gexplorer;

import java.util.Vector;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.dialogs.Solution;
import eu.geclipse.ui.internal.preference.Messages;

/**
 * Wrappers <code>IFileStore</code> into TreeNode. Provides model for 
 * gExplorer (note that gExplorer will be moved to another architecture 
 * until next release).
 * 
 * @author Mateusz Pabiœ
 */
public class ResourceNode extends TreeNode {

  /**
   * Specifies whenever this ResourceNode is a root node or not. The only behaviour by now 
   * is to show host name followed by its name for root nodes when toString is invoked. 
   * Note that non root nodes will return only its name with toString method.
   */
  private boolean rootNode = false;

  /**
   * Default constructor, assumes that provided node is not a root node (@see #toString())
   * @param value Object containing information about resource (IFileStore by default)
   */
  public ResourceNode( final Object value ) {
    super( value );
  }
  
  /**
   * Use this constructor when additional data about being root is provided (@see #toString())
   * @param value Object containing information about resouce (IFileStore by default)
   * @param rootNode information is value a root node (means: has no parent node)
   */
  public ResourceNode( final Object value, final boolean rootNode) {
    super( value );
    this.rootNode = rootNode;
  }

  @Override
  public TreeNode[] getChildren()
  {
    TreeNode[] resultArray;
    Object obj = this.getValue();
    if( obj instanceof IFileStore ) {
      IFileStore store = ( IFileStore )obj;
      Vector<ResourceNode> result = new Vector<ResourceNode>();
      try {
        IFileStore[] stores = store.childStores( EFS.NONE, null );
        for( IFileStore st : stores ) {
          result.add( new ResourceNode( st ) );
        }
      } catch( CoreException coreEx ) {
        Solution[] solutions = {
          new Solution( Messages.getString("ResourceNode.CACertProblem") ), //$NON-NLS-1$
          new Solution( Messages.getString("ResourceNode.solution.out_of_sync")), //$NON-NLS-1$
          new Solution( Messages.getString("ResourceNode.solution.firewall") ), //$NON-NLS-1$
          new Solution( Messages.getString("ResourceNode.solution.connection_data")  ) //$NON-NLS-1$
        };
        
        ProblemDialog.openProblem( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                   Messages.getString("ResourceNode.solution.title"),  //$NON-NLS-1$
                                   Messages.getString("ResourceNode.solution.header"),  //$NON-NLS-1$
                                   coreEx.getStatus(),
                                   solutions );
      }
      resultArray = result.toArray( new ResourceNode[ 0 ] );
    } else {
      resultArray = super.getChildren();
    }
    return resultArray;
  }

  @Override
  public TreeNode getParent()
  {
    TreeNode resultNode;
    Object obj = this.getValue();
    if( obj instanceof IFileStore ) {
      IFileStore store = ( IFileStore )obj;
      resultNode = new ResourceNode( store.getParent() );
    } else {
      resultNode = super.getParent();
    }
    return resultNode;
  }

  @Override
  public boolean hasChildren()
  {
    boolean result;
    Object obj = this.getValue();
    if( obj instanceof IFileStore ) {
      IFileStore store = ( IFileStore )obj;
      result = store.fetchInfo().isDirectory();
    } else {
      result = super.hasChildren();
    }
    return result;
  }

  @Override
  public String toString()
  {
    String result;
    Object obj = this.getValue();
    if( obj instanceof IFileStore ) {
      IFileStore store = ( IFileStore )obj;
      if ( isRootNode() ) {
        if ( store.toURI().getHost() == null ) 
          result = "localhost"; //$NON-NLS-1$
        else result = store.toURI().getHost(); 
        result = result.concat( store.toURI().getPath() );
      } else {
        result = store.getName();
      }
    } else {
      result = super.toString();
    }
    return result;
  }

  /**
   * @return boolean value that indicates whenever this <code>ResourceNode</code> 
   * is a root node or not.
   */
  public boolean isRootNode() {
    return this.rootNode;
  }
  /**
   * @param rootNode - boolean value that indicates whenever this 
   * <code>ResourceNode</code> is a root node or not.
   */
  public void setRootNode( final boolean rootNode ) {
    this.rootNode = rootNode;
  }
}
