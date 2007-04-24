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

/**
 * Wizard page for providing a list of other wizards which can be used for the
 * next steps in the wizard. The wizard list is built querying an extension
 * point.
 */
public class ExtPointWizardSelectionListPage extends WizardSelectionListPage {
  static final String EXT_CLASS = "class"; //$NON-NLS-1$
  static final String EXT_WIZARD = "wizard"; //$NON-NLS-1$
  static final String EXT_NAME = "name"; //$NON-NLS-1$
  static final String EXT_ICON = "icon"; //$NON-NLS-1$
  static final String EXT_ID = "id"; //$NON-NLS-1$

  /**
   * Creates a wizard page which allows to select a wizard for the next steps.
   * The wizard list is queried from an extension point.
   * @param pageName Name of the wizard page.
   * @param extensionPointId Id of the extension point to query the wizard list
   *                         from.
   * @param title Title of the page.
   * @param desc Description text of the page.
   */
  public ExtPointWizardSelectionListPage( final String pageName,
                                          final String extensionPointId,
                                          final String title,
                                          final String desc ) {
    super( pageName,
           getWizardList( extensionPointId, null ),
           title,
           desc );
  }

  /**
   * Creates a wizard page which allows to select a wizard for the next steps.
   * The wizard list is queried from an extension point.
   * @param pageName Name of the wizard page.
   * @param extensionPointId Id of the extension point to query the wizard list
   *                         from.
   * @param filterList List of wizard Ids of wizards which are allowed to be
   *                   displayed in the wizard list, if the Id of a wizard is
   *                   not in the list it won't be displayed in the list.
   * @param title Title of the page.
   * @param desc Description text of the page.
   */
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

  /**
   * Sets the Id of the wizard which should be preselected. If set the
   * WizardSelectionListPage will be skiped and the first page of the
   * preselected wizard will be displayed.
   * @param id Id representing the wizard to be preselected.
   * @param hidePrevPage true if it should not be possible to go back to the
   *                     WizardSelectionListPage to select another wizard,
   *                     false otherwise.
   */
  public void setPreselectedId( final String id, final boolean hidePrevPage ) {
    if ( id != null ) {
      for ( IWizardSelectionNode node : this.wizardSelectionNodes ) {
        ExtPointWizardSelectionNode extPointNode = ( ExtPointWizardSelectionNode ) node;
        if ( id.equals( extPointNode.getId() ) ) {
          setPreselectedNode( extPointNode, hidePrevPage );
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
