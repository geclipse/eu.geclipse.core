/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/


package eu.geclipse.jsdl.ui.providers;


/**
 * @author nickl
 */

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.jsdl.model.base.FileSystemType;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.ui.internal.Activator;

/**
 * This class provided a Label Provider for Structural Features of a JSDL document.
 *  
 *
 */
public class FeatureLabelProvider extends LabelProvider implements
                                                           ITableLabelProvider {
  
  /**
   * String Type Constant for Default strings.
   */
  public static final int STRING_TYPE_DEFAULT = 1;
  /**
   * String Type Constant for Job Annotation strings.
   */
  public static final int STRING_TYPE_ANNOTATION = 2;
  /**
   * String Type Constant for Job Project strings.
   */
  public static final int STRING_TYPE_PROJECT = 3;
  /**
   * String Type Constant for Candidate Hosts string.
   */
  public static final int STRING_TYPE_CANDIDATE_HOSTS = 4;
  
  private Image argsImage;
  private Image candHostsImage;
  private Image projectsImage;
  private Image annotationImage;
  private int stringType;
  
  /**
   * Class Constructor
   */
  public FeatureLabelProvider(){
    loadImages();
    this.stringType = STRING_TYPE_DEFAULT;
  }
  
  
  public Image getColumnImage(final Object element,final int columnIndex) {
    Image image = null;
    
    switch ( columnIndex ) {
      case 0:
       
        if ( element instanceof ArgumentType ) {
          image = this.argsImage;
        }
        else if (element instanceof String) {
          switch( this.stringType ) {
            case STRING_TYPE_ANNOTATION:
              image = this.annotationImage;
            break;
            case STRING_TYPE_PROJECT:
              image = this.projectsImage;
              break;
            case STRING_TYPE_CANDIDATE_HOSTS:
              image = this.candHostsImage;    
              break;
            default:
            break;
          }
          
        }
                        
        break;
      default:
        break;
    } // end switch
       
   
    return image;
    
  }
  
  
  
  public String getColumnText(final Object element, final int columnIndex) {
    
    String text = null;
    Object[] array = null;
    
      
    if ( element instanceof ArgumentType ) {
      
      ArgumentType argumentType = ( ArgumentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = argumentType.getFilesystemName();
          break;
        case 1:
          text = argumentType.getValue();
          break;
          default:
          break;
      }
      
    } 
    else if (element instanceof EnvironmentType) {
      
      EnvironmentType environmentType = ( EnvironmentType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = environmentType.getName();
          break;
        case 1:
          text = environmentType.getFilesystemName();
          break;
        case 2:
          text = environmentType.getValue();
          break;
          default:
          break;
          
      } // end switch
      
    } // end else_if EnvironmentType
    
    else if ( element instanceof FileSystemType ) {
      
      FileSystemType fileSystemType = ( FileSystemType ) element;      
      switch ( columnIndex ) {
        case 0:
          text = fileSystemType.getName();
          break;
        case 1:
          text = fileSystemType.getFileSystemType().toString();
          break;
        case 2:
          text = fileSystemType.getMountPoint();
          break;
          default:
          break;
          
      } // end switch
      
    } // end else_if EnvironmentType
    
    else if (element instanceof Object[]) {
      
      array = ( Object[] ) element;
      
    }
    
    else {
       
      switch ( columnIndex ) {
        case 0:
          text = element.toString();
          break;        
          default:
          break;
      } // end switch
      
    }
    
    if ( ( array != null ) && ( columnIndex < array.length ) ) {
      text = getText( array[ columnIndex ] );
    }
    return text;
    
  }// End String getColumnText()
  
  
  
  /**
   * @param type The FeatureLabelProvide String Type. If not specified the 
   * default string Type is used.
   * 
   */
  public void setStringType(final int type) {
    this.stringType = type;
  }
  
  
  void loadImages(){
    
    URL argsURL = Activator.getDefault().getBundle().getEntry( "icons/args.gif" ); //$NON-NLS-1$
    URL candHostsURL = Activator.getDefault().getBundle().getEntry( "icons/computing_obj.gif" ); //$NON-NLS-1$
    URL projectsURL = Activator.getDefault().getBundle().getEntry( "icons/gridprojects.gif" ); //$NON-NLS-1$
    URL annotationURL = Activator.getDefault().getBundle().getEntry( "icons/annotation_obj.gif" ); //$NON-NLS-1$
    ImageDescriptor argsDesc = ImageDescriptor.createFromURL( argsURL ) ;
    ImageDescriptor candHostsDesc = ImageDescriptor.createFromURL( candHostsURL ) ;
    ImageDescriptor projectsDesc = ImageDescriptor.createFromURL( projectsURL ) ;
    ImageDescriptor annotDesc = ImageDescriptor.createFromURL( annotationURL ) ;
    this.argsImage = argsDesc.createImage();
    this.candHostsImage = candHostsDesc.createImage();
    this.projectsImage = projectsDesc.createImage();
    this.annotationImage = annotDesc.createImage();
    
    
  }

} // End class
