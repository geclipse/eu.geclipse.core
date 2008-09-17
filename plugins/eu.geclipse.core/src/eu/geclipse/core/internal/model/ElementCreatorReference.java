package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.ICreatorSourceMatcher;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;

public class ElementCreatorReference {
  
  
  private static class SourceMatcher {
    
    private Class< ? > sourceClass;
    
    private Pattern sourcePattern;
    
    private boolean sourceDefault;
    
    private ICreatorSourceMatcher sourceMatcher;
    
    SourceMatcher( final IConfigurationElement element )
        throws ProblemException {
      
      String srcclsatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_CLASS_ATTRIBUTE );
      String srccntr = element.getContributor().getName();
      Bundle bundle = Platform.getBundle( srccntr );
      
      if ( bundle != null ) {
        try {
          this.sourceClass = bundle.loadClass( srcclsatt );
        } catch( ClassNotFoundException cnfExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( "Source class %s can not be loaded for element creator %s", srcclsatt, idatt ),
                                      cnfExc,
                                      Activator.PLUGIN_ID );
        }
      } else {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                    String.format( "Bundle %s can not be resolved for element creator source %s", srccntr, srcclsatt ),
                                    Activator.PLUGIN_ID );
      }
      
      String srcptrnatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_PATTERN_ATTRIBUTE );
      if ( srcptrnatt != null ) {
        try {
          this.sourcePattern = Pattern.compile( srcptrnatt );
        } catch ( PatternSyntaxException psExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( "Invalid source pattern %s for element creator %s", srcptrnatt, idatt ),
                                      Activator.PLUGIN_ID );
        }
      }
      
      String srcdfltatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_DEFAULT_ATTRIBUTE );
      if ( srcdfltatt != null ) {
        this.sourceDefault = Boolean.parseBoolean( srcdfltatt );
      } else {
        this.sourceDefault = false;
      }
      
      String srcmtchratt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_MATCHER_ATTRIBUTE );
      if ( srcmtchratt != null ) {
        try {
          this.sourceMatcher
            = ( ICreatorSourceMatcher ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_SOURCE_MATCHER_ATTRIBUTE );
        } catch( CoreException cExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( "Source matcher %s can not be loaded for element creator %s", srcmtchratt, idatt ),
                                      cExc,
                                      Activator.PLUGIN_ID );
        }
      }
      
    }
    
    public boolean matches( final Object source ) {
      
      boolean result = false;
      
      if ( source instanceof Class< ? > ) {
        result = this.sourceClass.isAssignableFrom( ( Class< ? > ) source );
      }
      
      else { 
      
        result = this.sourceClass.isAssignableFrom( source.getClass() );
      
        if ( result && ( this.sourcePattern != null ) ) {
          result = this.sourcePattern.matcher( source.toString() ).matches();
        }
        
        if ( result && ( this.sourceMatcher != null ) ) {
          result = this.sourceMatcher.canCreate( source );
        }
        
      }
      
      return result;
      
    }
    
  }
  
  
  private static class TargetMatcher {
    
    private Class< ? extends IGridElement > targetClass;
    
    private String targetName;
    
    TargetMatcher( final IConfigurationElement element )
        throws ProblemException {
      
      String trgtclsatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_CLASS_ATTRIBUTE );
      String trgtcntr = element.getContributor().getName();
      Bundle bundle = Platform.getBundle( trgtcntr );
      
      if ( bundle != null ) {
        try {
          this.targetClass = bundle.loadClass( trgtclsatt );
        } catch( ClassNotFoundException cnfExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( "Target class %s can not be loaded for element creator %s", trgtclsatt, idatt ),
                                      cnfExc,
                                      Activator.PLUGIN_ID );
        }
      } else {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                    String.format( "Bundle %s can not be resolved for element creator target %s", trgtcntr, trgtclsatt ),
                                    Activator.PLUGIN_ID );
      }
      
      this.targetName = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_NAME_ATTRIBUTE );
      
    }
    
    public boolean matches( final Class< ? extends IGridElement > target ) {
      return target.isAssignableFrom( this.targetClass );
    }
    
  }
  
  
  private IConfigurationElement configurationElement;
  
  private IGridElementCreator elementCreator;
  
  private List< SourceMatcher > sourceMatchers;
  
  private TargetMatcher targetMatcher;
  
  public ElementCreatorReference( final IConfigurationElement configurationElement ) {
    this.configurationElement = configurationElement;
  }
  
  public boolean checkSource( final Object source ) throws ProblemException {
    
     boolean result = source == null;

     if ( source != null ) {
       if ( this.sourceMatchers == null ) {
         initSourceMatchers();
       }
       for ( SourceMatcher matcher : this.sourceMatchers ) {
         if ( matcher.matches( source ) ) {
           result = true;
           break;
         }
       }
     }
     
     return result;
     
  }
  
  public boolean checkTarget( final Class< ? extends IGridElement > target ) throws ProblemException {
    
    boolean result = target == null;

    if ( target != null ) {
      if (  this.targetMatcher == null ) {
        initTargetMatcher();
      }
      if ( this.targetMatcher.matches( target ) ) {
        result = true;
      }
    }
    
    return result;
    
 }
  
  public IConfigurationElement getConfigurationElement() {
    return this.configurationElement;
  }
  
  public IGridElementCreator getElementCreator() {
    
    if ( this.elementCreator == null ) {
      try {
        this.elementCreator
          = ( IGridElementCreator ) this.configurationElement.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    
    return this.elementCreator;
    
  }
  
  @Override
  public String toString() {
    return "ElementCreatorReference@" + this.configurationElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
  }
  
  private void initSourceMatchers() throws ProblemException {
    this.sourceMatchers = new ArrayList< SourceMatcher >();
    IConfigurationElement[] children
      = this.configurationElement.getChildren( Extensions.GRID_ELEMENT_CREATOR_SOURCE_ELEMENT );
    for ( IConfigurationElement child : children ) {
      this.sourceMatchers.add( new SourceMatcher( child ) );
    }
  }
  
  private void initTargetMatcher() throws ProblemException {
    IConfigurationElement[] children
      = this.configurationElement.getChildren( Extensions.GRID_ELEMENT_CREATOR_TARGET_ELEMENT );
    this.targetMatcher = new TargetMatcher( children[ 0 ] );
  }
  
}
