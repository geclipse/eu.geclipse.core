/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package eu.geclipse.jsdl.model.posix.validation;

import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.DirectoryNameType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.GroupNameType;
import eu.geclipse.jsdl.model.posix.LimitsType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.UserNameType;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * A sample validator interface for {@link eu.geclipse.jsdl.model.posix.DocumentRoot}.
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
  boolean validateArgument(ArgumentType value);
  boolean validateCoreDumpLimit(LimitsType value);
  boolean validateCPUTimeLimit(LimitsType value);
  boolean validateDataSegmentLimit(LimitsType value);
  boolean validateEnvironment(EnvironmentType value);
  boolean validateError(FileNameType value);
  boolean validateExecutable(FileNameType value);
  boolean validateFileSizeLimit(LimitsType value);
  boolean validateGroupName(GroupNameType value);
  boolean validateInput(FileNameType value);
  boolean validateLockedMemoryLimit(LimitsType value);
  boolean validateMemoryLimit(LimitsType value);
  boolean validateOpenDescriptorsLimit(LimitsType value);
  boolean validateOutput(FileNameType value);
  boolean validatePipeSizeLimit(LimitsType value);
  boolean validatePOSIXApplication(POSIXApplicationType value);
  boolean validateProcessCountLimit(LimitsType value);
  boolean validateStackSizeLimit(LimitsType value);
  boolean validateThreadCountLimit(LimitsType value);
  boolean validateUserName(UserNameType value);
  boolean validateVirtualMemoryLimit(LimitsType value);
  boolean validateWallTimeLimit(LimitsType value);
  boolean validateWorkingDirectory(DirectoryNameType value);
}