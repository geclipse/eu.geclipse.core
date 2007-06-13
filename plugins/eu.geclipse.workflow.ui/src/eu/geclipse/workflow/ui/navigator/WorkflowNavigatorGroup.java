/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.navigator;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @generated
 */
public class WorkflowNavigatorGroup extends WorkflowAbstractNavigatorItem {

  /**
   * @generated
   */
  private String myGroupName;
  /**
   * @generated
   */
  private String myIcon;
  /**
   * @generated
   */
  private Collection myChildren = new LinkedList();

  /**
   * @generated
   */
  WorkflowNavigatorGroup( String groupName, String icon, Object parent ) {
    super( parent );
    myGroupName = groupName;
    myIcon = icon;
  }

  /**
   * @generated
   */
  public String getGroupName() {
    return myGroupName;
  }

  /**
   * @generated
   */
  public String getIcon() {
    return myIcon;
  }

  /**
   * @generated
   */
  public Object[] getChildren() {
    return myChildren.toArray();
  }

  /**
   * @generated
   */
  public void addChildren( Collection children ) {
    myChildren.addAll( children );
  }

  /**
   * @generated
   */
  public void addChild( Object child ) {
    myChildren.add( child );
  }

  /**
   * @generated
   */
  public boolean isEmpty() {
    return myChildren.size() == 0;
  }

  /**
   * @generated
   */
  public boolean equals( Object obj ) {
    if( obj instanceof eu.geclipse.workflow.ui.navigator.WorkflowNavigatorGroup )
    {
      eu.geclipse.workflow.ui.navigator.WorkflowNavigatorGroup anotherGroup = ( eu.geclipse.workflow.ui.navigator.WorkflowNavigatorGroup )obj;
      if( getGroupName().equals( anotherGroup.getGroupName() ) ) {
        return getParent().equals( anotherGroup.getParent() );
      }
    }
    return super.equals( obj );
  }

  /**
   * @generated
   */
  public int hashCode() {
    return getGroupName().hashCode();
  }
}
