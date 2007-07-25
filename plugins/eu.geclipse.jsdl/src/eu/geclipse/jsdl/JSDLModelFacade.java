package eu.geclipse.jsdl;

import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;

/**
 * This class is for obtaining instances of model's objects. It hides subsequent
 * factories calls.
 * 
 * @author Kasia
 */
public class JSDLModelFacade {
  
  public static DataStagingType getDataStagingType(){
    return JsdlPackage.eINSTANCE.getJsdlFactory().createDataStagingType();
  }
  
  public static SourceTargetType getSourceTargetType(){
    return JsdlPackage.eINSTANCE.getJsdlFactory().createSourceTargetType();
  }
  
}
