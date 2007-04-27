package eu.geclipse.ui.views;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementManager;

public class GridTransferView extends ElementManagerViewPart {

  @Override
  protected IGridElementManager getManager() {
    return GridModel.getTransferManager();
  }
  
}
