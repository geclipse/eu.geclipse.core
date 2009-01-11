/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.aws.ec2.EC2SecurityGroup;

/**
 * A base class for security group related actions in the VO subtree.
 * 
 * @author Moritz Post
 */
public abstract class AbstractSecurityGroupAction
  implements IObjectActionDelegate
{

  /**
   * Flag denoting that the action is enabled when exactly one element is
   * selected
   */
  public static int ENABLE_FOR_ONE = 1;

  /**
   * Flag denoting that the action is enabled when more than zero elements are
   * selected.
   */
  public static int ENABLE_FOR_MANY = 2 | AbstractSecurityGroupAction.ENABLE_FOR_ONE;

  /** The workbench part. */
  private IWorkbenchPart workbenchPart;

  /** The list of selected {@link EC2SecurityGroup}s. */
  private List<EC2SecurityGroup> securityGroupList;

  /**
   * Creates a new action to edit the selected security group.
   */
  public AbstractSecurityGroupAction() {
    this.securityGroupList = new ArrayList<EC2SecurityGroup>();
  }

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    this.workbenchPart = targetPart;
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.securityGroupList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;

      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2SecurityGroup ) {
          EC2SecurityGroup securityGroup = ( EC2SecurityGroup )element;
          this.securityGroupList.add( securityGroup );
        }
      }
    }
    if( ( getEnablementCount() ^ AbstractSecurityGroupAction.ENABLE_FOR_ONE ) == 0
        && this.securityGroupList.size() == 1 )
    {
      enable = true;
    } else if( ( getEnablementCount() ^ AbstractSecurityGroupAction.ENABLE_FOR_MANY ) == 0
               && this.securityGroupList.size() > 0 )
    {
      enable = true;
    }
    action.setEnabled( enable );

  }

  /**
   * Defines the count for which this Action should be enabled. Possible values
   * are:
   * <ul>
   * <li>{@link #ENABLE_FOR_ONE}</li>
   * <li>{@link #ENABLE_FOR_MANY}</li>
   * </ul>
   * 
   * @return the flag denoting if the desired selection count
   */
  abstract protected int getEnablementCount();

  /**
   * Getter for the {@link #workbenchPart}.
   * 
   * @return the workbenchPart
   */
  public IWorkbenchPart getWorkbenchPart() {
    return this.workbenchPart;
  }

  /**
   * Returns the list of currently selected security groups. Getter for the
   * {@link #securityGroupList}
   * 
   * @return the securityGroupList
   */
  public List<EC2SecurityGroup> getSecurityGroupList() {
    return this.securityGroupList;
  }
}
