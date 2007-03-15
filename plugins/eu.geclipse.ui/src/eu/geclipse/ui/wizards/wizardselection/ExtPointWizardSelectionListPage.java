package eu.geclipse.ui.wizards.wizardselection;

import java.util.List;
import java.util.Vector;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class ExtPointWizardSelectionListPage extends WizardSelectionListPage {
  static final String EXT_CLASS = "class"; //$NON-NLS-1$
  static final String EXT_WIZARD = "wizard"; //$NON-NLS-1$
  static final String EXT_NAME = "name"; //$NON-NLS-1$
  static final String EXT_ICON = "icon"; //$NON-NLS-1$
  static final String EXT_ID = "id"; //$NON-NLS-1$

  public ExtPointWizardSelectionListPage( final String pageName,
                                          final String extensionPointId,
                                          final String title,
                                          final String desc ) {
    super( pageName,
           getWizardList( extensionPointId, null ),
           title,
           desc );
  }

  public ExtPointWizardSelectionListPage( final String pageName,
                                          final String extensionPointId,
                                          final List<String> filterList,
                                          final String title,
                                          final String desc ) {
    super( pageName,
           getWizardList( extensionPointId , filterList ),
           title,
           desc );
  }

  public void setPreselectedId( final String id ) {
    if ( id != null ) {
      for ( IWizardSelectionNode node : this.wizardSelectionNodes ) {
        ExtPointWizardSelectionNode extPointNode = ( ExtPointWizardSelectionNode ) node;
        if ( id.equals( extPointNode.getId() ) ) {
          setPreselectedNode( extPointNode );
        }
      }
    }
  }
  
  private static IWizardSelectionNode[] getWizardList( final String extensionPointId,
                                                       final List<String> filterList ) {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = registry.getExtensionPoint( extensionPointId );
    Vector<IWizardSelectionNode> nodes = new Vector<IWizardSelectionNode>();
    if ( extensionPoint != null ) {
      IExtension[] extensions = extensionPoint.getExtensions();
      for( IExtension extension : extensions ) {
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for( IConfigurationElement element : elements ) {
          if( EXT_WIZARD.equals( element.getName() ) ) {
            String id = element.getAttribute( EXT_ID );
            if ( filterList == null || filterList.contains( id ) ) {
              String name = element.getAttribute( EXT_NAME );
              String iconName = element.getAttribute( EXT_ICON );
              Image icon = null;
              if ( iconName != null ) {
                String pluginId = element.getContributor().getName();
                ImageDescriptor iconDesc = AbstractUIPlugin.imageDescriptorFromPlugin( pluginId, iconName );
                if ( iconDesc != null ) icon = iconDesc.createImage();
              }
              IWizardSelectionNode wizardNode = new ExtPointWizardSelectionNode( element, id, name, icon );
              nodes.add( wizardNode );
            }
          }
        }
      }
    }
    return nodes.toArray( new IWizardSelectionNode[0] );
  }

  public void setCheatSheetManager( final ICheatSheetManager cheatSheetManager ) {
    this.cheatSheetManager = cheatSheetManager;
  }
}
