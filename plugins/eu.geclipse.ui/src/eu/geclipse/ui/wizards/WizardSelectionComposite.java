/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import eu.geclipse.ui.internal.Activator;

class WizardSelectionComposite<InitDataType> extends Composite {
  Map<Object, Image> imageMap = new HashMap<Object, Image>();
  private Table table = null;
  private TableViewer tableViewer;

  WizardSelectionComposite( final Composite parent, final int style ) {
    super( parent, style );
    initialize();
    this.tableViewer = new TableViewer( this.table );
    this.tableViewer.setLabelProvider( new LabelProvider() {
      @Override
      public String getText( final Object element ) {
        return element.toString();
      }
      
      @Override
      public Image getImage( final Object element ) {
        return WizardSelectionComposite.this.imageMap.get( element );
      }
    } );
  }

  private void initialize() {
    GridData tableGridData = new GridData();
    tableGridData.horizontalAlignment = GridData.FILL;
    tableGridData.grabExcessHorizontalSpace = true;
    tableGridData.grabExcessVerticalSpace = true;
    tableGridData.verticalAlignment = GridData.FILL;
    this.table = new Table(this, SWT.BORDER);
    this.table.setLayoutData(tableGridData);
    setSize( new Point( 300, 200 ) );
    setLayout( new GridLayout() );
  }

  void addEntry( final String item ) {
    this.tableViewer.add( item ); 
  }

  void addSelectionListener( final SelectionListener listener ) {
    this.table.addSelectionListener( listener );
  }

  void addSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.tableViewer.addSelectionChangedListener( listener );
  }

  @SuppressWarnings("unchecked")
  void fillWizardList( final String extensionPointId ) {
    IExtensionRegistry registry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint 
      = registry.getExtensionPoint( extensionPointId );
    if ( extensionPoint != null ) {
    IExtension[] extensions = extensionPoint.getExtensions();
      for( IExtension extension : extensions ) {
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for( IConfigurationElement element : elements ) {
          if( INewWizard.EXT_WIZARD.equals( element.getName() ) ) {
            try {
//              final String name = element.getAttribute( INewWizard.EXT_NAME );
              Object object = element.createExecutableExtension( INewWizard.EXT_CLASS );
              if ( object instanceof INewWizard ) {
                final INewWizard<InitDataType> wizard = ( INewWizard<InitDataType> )object;
                String iconName = element.getAttribute( INewWizard.EXT_ICON );
                if ( iconName != null ) {
                  String pluginId = element.getContributor().getName();
                  final ImageDescriptor icon = 
                    AbstractUIPlugin.imageDescriptorFromPlugin( pluginId, iconName );
                  this.imageMap.put( wizard, icon.createImage() );
                }
                this.tableViewer.add( wizard );
              }
            } catch( CoreException coreEx ) {
              Activator.logException( coreEx );
            }
          }
        }
      }
    }
  }
}
