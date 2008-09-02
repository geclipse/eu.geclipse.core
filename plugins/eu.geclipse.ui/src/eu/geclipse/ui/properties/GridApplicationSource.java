package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.info.glue.GlueLocation;
import eu.geclipse.info.model.GridApplication;


public class GridApplicationSource extends AbstractPropertySource<GridApplication>{

  static private List<IProperty<GridApplication>> staticDescriptors;
  
  public GridApplicationSource( final GridApplication sourceObject ) {
    super( sourceObject );
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass()
  {
    return GridApplicationSource.class;
  }

  @Override
  protected List<IProperty<GridApplication>> getStaticProperties() {
    
    if( staticDescriptors == null ) {
      staticDescriptors = createProperties();
    }
    
    return staticDescriptors;
  }
  
  static private List<IProperty<GridApplication>> createProperties() {
    List<IProperty<GridApplication>> propertiesList = new ArrayList<IProperty<GridApplication>>( 1 );
    propertiesList.add( createName() );
    propertiesList.add( createLocationPath() );
    propertiesList.add( createComputing() );
    propertiesList.add( createLocationVersion() );
    
    return propertiesList;
  }
  
  static private IProperty<GridApplication> createName() {
    return new AbstractProperty<GridApplication>( "Name", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridApplication gridApplication )
      {
        return gridApplication.getName();
      } 
    };
  }
  
  static private IProperty<GridApplication> createLocationPath() {
    return new AbstractProperty<GridApplication>( "Location", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridApplication gridApplication )
      {
        return gridApplication.getTag();
      } 
    };
  }
  
  static private IProperty<GridApplication> createComputing() {
    return new AbstractProperty<GridApplication>( "Computing Elements", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridApplication gridApplication )
      {
        IGridComputing[] myComputingArray = gridApplication.getComputing();
        String result = ""; //$NON-NLS-1$
        for (int i=0; myComputingArray!=null && i<myComputingArray.length; i++)
        {
          result += myComputingArray[i].getURI() + ";"; //$NON-NLS-1$
        }
        
        return result;
      } 
    };
  }
  
  static private IProperty<GridApplication> createLocationVersion() {
    return new AbstractProperty<GridApplication>( "Version", null) { //$NON-NLS-1$
      @Override
      public Object getValue( final GridApplication gridApplication )
      {
        String result = ""; //$NON-NLS-1$
        GlueLocation myLocationObject = (GlueLocation)gridApplication.getGlueElement();
        if (myLocationObject!=null)
        {
          result = myLocationObject.version;
        }
        
        return result;
      } 
    };
  }
}
