/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

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

/**
 * An internally used reference to an extension of the
 * <code>eu.geclipse.core.gridElementCreator</code> extension point.
 */
public class ElementCreatorReference {
  
  
  /**
   * Matches a source object with the source information of the corresponding
   * element creator extension. The matched object may be a {@link Class} in
   * which case the matching does only take into account the source type and
   * not the pattern or the specified {@link ICreatorSourceMatcher}.
   */
  private static class SourceMatcher {
    
    /**
     * The default priority taken if no priority is specified in the extension.
     */
    private static final int DEFAULT_PRIORITY = 50;
    
    /**
     * The type of the supported source.
     */
    private Class< ? > sourceClass;
    
    /**
     * An optional pattern matched against the toString() result of the matched
     * object.
     */
    private Pattern sourcePattern;
    
    /**
     * The priority of the creator concerning this source matcher.
     */
    private int sourcePriority;
    
    /**
     * Specifies if this is the default source of the corresponding creator.
     */
    private boolean sourceDefault;
    
    /**
     * An optional source matcher for more sophisticated source matching
     * patterns.
     */
    private ICreatorSourceMatcher sourceMatcher;
    
    /**
     * Standard constructor.
     * 
     * @param element The {@link IConfigurationElement} this
     * {@link SourceMatcher} should refer to.
     * @throws ProblemException If the matcher could not be created. Possible
     * reasons include class loading problems for the source class and source
     * matcher or invalid regular expressions for the source pattern.
     */
    SourceMatcher( final IConfigurationElement element )
        throws ProblemException {
      
      // Get the base classes for the class loading
      String srcclsatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_CLASS_ATTRIBUTE );
      String srccntr = element.getContributor().getName();
      Bundle bundle = Platform.getBundle( srccntr );
      
      // Try to load the source class from its home bundle
      if ( bundle != null ) {
        try {
          this.sourceClass = bundle.loadClass( srcclsatt );
        } catch( ClassNotFoundException cnfExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( Messages.getString("ElementCreatorReference.source_class_loading_failed"), srcclsatt, idatt ), //$NON-NLS-1$
                                      cnfExc,
                                      Activator.PLUGIN_ID );
        }
      } else {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                    String.format( Messages.getString("ElementCreatorReference.source_bundle_loading_failed"), srccntr, srcclsatt ), //$NON-NLS-1$
                                    Activator.PLUGIN_ID );
      }
      
      // Parse and compile the source pattern
      String srcptrnatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_PATTERN_ATTRIBUTE );
      if ( srcptrnatt != null ) {
        try {
          this.sourcePattern = Pattern.compile( srcptrnatt );
        } catch ( PatternSyntaxException psExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( Messages.getString("ElementCreatorReference.invalid_source_pattern"), srcptrnatt, idatt ), //$NON-NLS-1$
                                      Activator.PLUGIN_ID );
        }
      }
      
      String srcprrtyatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_PRIORITY_ATTRIBUTE );
      if ( srcprrtyatt != null ) {
        try {
          this.sourcePriority = Integer.parseInt( srcprrtyatt );
          if ( this.sourcePriority < 1 ) {
            this.sourcePriority = 1;
          } else if ( this.sourcePriority > 99 ) {
            this.sourcePriority = 99;
          }
        } catch ( NumberFormatException nfExc ) {
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      Messages.getString("ElementCreatorReference.invalid_priority"), //$NON-NLS-1$
                                      Activator.PLUGIN_ID );
        }
      } else {
        this.sourcePriority = DEFAULT_PRIORITY;
      }
      
      // Parse the default attribute
      String srcdfltatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_DEFAULT_ATTRIBUTE );
      if ( srcdfltatt != null ) {
        this.sourceDefault = Boolean.parseBoolean( srcdfltatt );
      } else {
        this.sourceDefault = false;
      }
      
      // Parse and eventually create the source matcher.
      String srcmtchratt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_SOURCE_MATCHER_ATTRIBUTE );
      if ( srcmtchratt != null ) {
        try {
          this.sourceMatcher
            = ( ICreatorSourceMatcher ) element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_SOURCE_MATCHER_ATTRIBUTE );
        } catch( CoreException cExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( Messages.getString("ElementCreatorReference.source_matcher_loading_failed"), srcmtchratt, idatt ), //$NON-NLS-1$
                                      cExc,
                                      Activator.PLUGIN_ID );
        }
      }
      
    }
    
    /**
     * Get the priority assigned to this source matcher.
     * 
     * @return The matcher's priority.
     */
    public int getPriority() {
      return this.sourcePriority;
    }
    
    /**
     * Determine if this is the default source for the corresponding creator.
     * 
     * @return <code>true</code> if this is the default source.
     */
    public boolean isDefault() {
      return this.sourceDefault;
    }
    
    /**
     * Matches the specified object with the requirements of this source
     * matcher. If the specified source is a {@link Class} the matching
     * algorithm only takes into account the source's type. If the object is not
     * a {@link Class} the object's type and it's toString() result are matched.
     * If both matches are successful an optional {@link ICreatorSourceMatcher}
     * is applied as last step.
     *  
     * @param source The source object to be matched.
     * @return <code>true</code> if the specified object matches all
     * requirements of this source matcher.
     */
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
  
  
  /**
   * Matches a target element with the target information of the corresponding
   * element creator extension.
   */
  private static class TargetMatcher {
    
    /**
     * The type of the supported target.
     */
    private Class< ? > targetClass;
    
    /**
     * The target's name.
     */
    private String targetName;
    
    /**
     * Standard constructor.
     * 
     * @param element The {@link IConfigurationElement} this
     * {@link TargetMatcher} should refer to.
     * @throws ProblemException If the matcher could not be created. Possible
     * reasons include class loading problems for the target class.
     */
    TargetMatcher( final IConfigurationElement element )
        throws ProblemException {
      
      // Get the base classes for the class loading
      String trgtclsatt = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_CLASS_ATTRIBUTE );
      String trgtcntr = element.getContributor().getName();
      Bundle bundle = Platform.getBundle( trgtcntr );
      
      // Try to load the target class from its home bundle
      if ( bundle != null ) {
        try {
          this.targetClass = bundle.loadClass( trgtclsatt );
        } catch( ClassNotFoundException cnfExc ) {
          String idatt = ( ( IConfigurationElement ) element.getParent() ).getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
          throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                      String.format( Messages.getString("ElementCreatorReference.target_class_loading_failed"), trgtclsatt, idatt ), //$NON-NLS-1$
                                      cnfExc,
                                      Activator.PLUGIN_ID );
        }
      } else {
        throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                    String.format( Messages.getString("ElementCreatorReference.target_bundle_loading_failed"), trgtcntr, trgtclsatt ), //$NON-NLS-1$
                                    Activator.PLUGIN_ID );
      }
      
      // Load the target name
      this.targetName = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_TARGET_NAME_ATTRIBUTE );
      
    }
    
    /**
     * Get the name of the target.
     * 
     * @return The target's name.
     */
    public String getName() {
      return this.targetName;
    }
    
    /**
     * Matches the specified class with the requirements of this target
     * matcher.
     *  
     * @param target The target class to be matched.
     * @return <code>true</code> if the specified class matches the target type.
     */
    public boolean matches( final Class< ? extends IGridElement > target ) {
      return target.isAssignableFrom( this.targetClass );
    }
    
  }
  
  
  /**
   * The {@link IConfigurationElement} corresponding to this reference.
   */
  private IConfigurationElement configurationElement;
  
  /**
   * The instantiated element creator specified in the extension.
   */
  private IGridElementCreator elementCreator;
  
  /**
   * The source matchers.
   */
  private List< SourceMatcher > sourceMatchers;
  
  /**
   * The target matcher.
   */
  private TargetMatcher targetMatcher;
  
  /**
   * Create a new creator reference from the specified
   * {@link IConfigurationElement}.
   * 
   * @param configurationElement The {@link IConfigurationElement} corresponding
   * to this reference.
   */
  ElementCreatorReference( final IConfigurationElement configurationElement ) {
    this.configurationElement = configurationElement;
  }
  
  /**
   * Test if the specified source object is supported by the corresponding
   * element creator.
   * 
   * @param source The source to be checked.
   * @return A number between 1 and 99 inclusive if the source is supported by
   * the corresponding element creator. The number corresponds to the creators
   * priority. If the source is not supported -1 will be returned.
   * @throws ProblemException If a problem occurs.
   * @see SourceMatcher#matches(Object)
   */
  public int checkSource( final Object source ) throws ProblemException {
    
     int result = -1;

     if ( source != null ) {
       if ( this.sourceMatchers == null ) {
         initSourceMatchers();
       }
       for ( SourceMatcher matcher : this.sourceMatchers ) {
         if ( matcher.matches( source ) ) {
           int priority = matcher.getPriority();
           if ( priority > result ) {
             result = priority;
           }
         }
       }
     }
     
     return result;
     
  }
  
  /**
   * Test if the specified target type is supported by the corresponding
   * element creator.
   * 
   * @param target The target to be checked.
   * @return <code>true</code> if the target is supported by the corresponding
   * element creator.
   * @throws ProblemException If a problem occurs.
   * @see TargetMatcher#matches(Class)
   */
  public boolean checkTarget( final Class< ? extends IGridElement > target )
      throws ProblemException {
    
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
  
  /**
   * Get the {@link IConfigurationElement} that is associated to this reference.
   * 
   * @return This reference's associated configuration element.
   */
  public IConfigurationElement getConfigurationElement() {
    return this.configurationElement;
  }
  
  /**
   * Get the {@link IGridElementCreator} from the corresponding extension. If
   * the element creator was not yet created an attempt is made to create it
   * now. If this attempt fails an error is logged and the methods returns
   * <code>null</code>.
   * 
   * @return The associated {@link IGridElementCreator} or <code>null</code> if
   * the creation of the creator failed.
   */
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
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ElementCreatorReference@" //$NON-NLS-1$
    + this.configurationElement.getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE );
  }
  
  /**
   * Initialize the reference's source matchers.
   * 
   * @throws ProblemException If the initialization failed.
   */
  private void initSourceMatchers() throws ProblemException {
    this.sourceMatchers = new ArrayList< SourceMatcher >();
    IConfigurationElement[] children
      = this.configurationElement.getChildren( Extensions.GRID_ELEMENT_CREATOR_SOURCE_ELEMENT );
    for ( IConfigurationElement child : children ) {
      this.sourceMatchers.add( new SourceMatcher( child ) );
    }
  }
  
  /**
   * Initialize the reference's target matcher.
   * 
   * @throws ProblemException If the initialization failed.
   */
  private void initTargetMatcher() throws ProblemException {
    IConfigurationElement[] children
      = this.configurationElement.getChildren( Extensions.GRID_ELEMENT_CREATOR_TARGET_ELEMENT );
    this.targetMatcher = new TargetMatcher( children[ 0 ] );
  }
  
}
