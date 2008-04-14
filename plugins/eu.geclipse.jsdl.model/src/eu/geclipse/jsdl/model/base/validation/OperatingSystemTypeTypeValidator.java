/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.validation;

import eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.base.OperatingSystemTypeType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface OperatingSystemTypeTypeValidator
{
  boolean validate();

  boolean validateOperatingSystemName(OperatingSystemTypeEnumeration value);
  boolean validateAny(FeatureMap value);
  boolean validateAnyAttribute(FeatureMap value);
}
