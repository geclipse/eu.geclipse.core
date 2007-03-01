package eu.geclipse.ui.wizards;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.IGridElement;


public class GridElementCreatorWizard extends ExtensionWizard {
  
  public GridElementCreatorWizard( final Class< ? extends IGridElement > elementType ) {
    super( Extensions.GRID_ELEMENT_CREATOR_POINT,
           Extensions.GRID_ELEMENT_CREATOR_ELEMENT );
  }
  
}
