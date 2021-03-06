/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.validation;

import eu.geclipse.jsdl.model.BoundaryType;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.RangeValueType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 * @deprecated This interface is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.validation
 */
public interface RangeValueTypeValidator
{
  boolean validate();

  boolean validateUpperBound(BoundaryType value);
  boolean validateLowerBound(BoundaryType value);
  boolean validateExact(EList value);
  boolean validateRange(EList value);
  boolean validateAnyAttribute(FeatureMap value);
}
