package eu.geclipse.core.security;

import org.ietf.jgss.GSSException;
import eu.geclipse.core.GridException;

public class GridGSSException extends GridException {
  
  public GridGSSException( final GSSException exc ) {
    super( GridGSSProblems.getProblemID( exc ), exc );
  }
  
}
