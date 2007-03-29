package eu.geclipse.core.model;

import java.net.URI;

public interface IGridConnection
    extends IGridConnectionElement, IManageable, IStorableElement {
  
    URI getURI(); 
}
