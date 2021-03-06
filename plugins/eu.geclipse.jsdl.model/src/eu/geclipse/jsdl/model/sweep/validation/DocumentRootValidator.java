/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.sweep.validation;

import eu.geclipse.jsdl.model.sweep.AssignmentType;
import eu.geclipse.jsdl.model.sweep.SweepType;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.sweep.DocumentRoot}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DocumentRootValidator
{
  boolean validate();

  boolean validateMixed(FeatureMap value);
  boolean validateXMLNSPrefixMap(EMap value);
  boolean validateXSISchemaLocation(EMap value);
  boolean validateAssignment(AssignmentType value);
  boolean validateFunction(EObject value);
  boolean validateParameter(String value);
  boolean validateSweep(SweepType value);
}
