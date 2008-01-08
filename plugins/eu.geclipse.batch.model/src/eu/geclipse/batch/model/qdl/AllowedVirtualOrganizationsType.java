/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.batch.model.qdl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Allowed Virtual Organizations Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.geclipse.batch.model.qdl.AllowedVirtualOrganizationsType#getVOName <em>VO Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.geclipse.batch.model.qdl.QdlPackage#getAllowedVirtualOrganizationsType()
 * @model extendedMetaData="name='AllowedVirtualOrganizationsType' kind='elementOnly'"
 * @generated
 */
public interface AllowedVirtualOrganizationsType extends EObject
{
  /**
   * Returns the value of the '<em><b>VO Name</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>VO Name</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>VO Name</em>' attribute list.
   * @see eu.geclipse.batch.model.qdl.QdlPackage#getAllowedVirtualOrganizationsType_VOName()
   * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='element' name='VOName' namespace='##targetNamespace'"
   * @generated
   */
  EList<String> getVOName();

} // AllowedVirtualOrganizationsType
