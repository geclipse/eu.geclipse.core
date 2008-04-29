/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.base.validation;

import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.base.CandidateHostsType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CandidateHostsTypeValidator
{
  boolean validate();

  boolean validateHostName(EList value);
}
