/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     RUR - David Johnson, University of Reading, UK
 *****************************************************************************/
package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.workflow.IGridWorkflowDescription;


/**
 * @author david
 *
 * Properties for {@link IGridWorkflowDescription}
 *
 */

public class GridWorkflowDescSource extends AbstractPropertySource<IGridWorkflowDescription> {
  
  static private List<IProperty<IGridWorkflowDescription>> staticProperties;

  /**
   * @param source Object, for which properties will be shown
   */
  public GridWorkflowDescSource( IGridWorkflowDescription source ) {
    super( source );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridWorkflowDescSource.class;
  }

  @Override
  protected List<IProperty<IGridWorkflowDescription>> getStaticProperties() {
    if( staticProperties == null ) {
      staticProperties = createProperties();    
    }
    
    return staticProperties;
  }

  static private List<IProperty<IGridWorkflowDescription>> createProperties() {
    List<IProperty<IGridWorkflowDescription>> propertiesList = new ArrayList<IProperty<IGridWorkflowDescription>>();
    propertiesList.add( createDescription() );
    propertiesList.add( createPropertyNumJobs() );
    return propertiesList;
  }
  
  static private IProperty<IGridWorkflowDescription> createDescription() {
    return new AbstractProperty<IGridWorkflowDescription>( Messages.getString( "GridWorkflowDescSource.propertyDescription" ), null ) { //$NON-NLS-1$

      @Override
      public Object getValue( final IGridWorkflowDescription source )
      {
        return source.getPath().toFile().toURI().toString();
      }
    };
  }
  
  static private IProperty<IGridWorkflowDescription> createPropertyNumJobs() {
    return new AbstractProperty<IGridWorkflowDescription>( Messages.getString( "GridWorkflowDescSource.propertyNumJobs" ), null ) { //$NON-NLS-1$

      @SuppressWarnings("boxing")
      @Override
      public Object getValue( final IGridWorkflowDescription source )
      {
        return source.getChildrenJobs().size();
      }
    };
  }  
  
  
  
}
