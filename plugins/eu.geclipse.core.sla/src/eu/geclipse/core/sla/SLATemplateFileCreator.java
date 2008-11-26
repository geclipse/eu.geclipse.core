package eu.geclipse.core.sla;

import org.eclipse.core.resources.IFile;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * @author korn
 *
 */
public class SLATemplateFileCreator extends AbstractGridElementCreator {

  public IGridElement create( IGridContainer parent ) throws ProblemException {
   
    IGridElement result = null ; 
    Object source = this.getSource() ; 
    
    if (source instanceof IFile )
      result = new SLATemplateFile((IFile) source);
    
    return result ; 
  }
}
