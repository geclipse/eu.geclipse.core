/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.validation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.JobIdentificationType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 * @deprecated This interface is deprecated. Substitute with the respective class in package eu.geclipse.jsdl.model.base.validation
 */
public interface JobIdentificationTypeValidator
{
  boolean validate();

  boolean validateJobName(String value);
  boolean validateDescription(String value);
  boolean validateJobAnnotation(EList value);
  boolean validateJobProject(EList value);
  boolean validateAny(FeatureMap value);
  boolean validateAnyAttribute(FeatureMap value);
}
