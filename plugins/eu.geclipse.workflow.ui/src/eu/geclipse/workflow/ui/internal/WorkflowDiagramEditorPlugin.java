/*******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import eu.geclipse.workflow.provider.WorkflowItemProviderAdapterFactory;
import eu.geclipse.workflow.ui.part.WorkflowDocumentProvider;

/**
 * @generated
 */
public class WorkflowDiagramEditorPlugin extends AbstractUIPlugin {

  /**
   * @generated
   */
  public static final String ID = "eu.geclipse.workflow.ui"; //$NON-NLS-1$
  /**
   * @generated
   */
  public static final PreferencesHint DIAGRAM_PREFERENCES_HINT = new PreferencesHint( ID );
  /**
   * The shared instance
   * @generated
   */
  private static WorkflowDiagramEditorPlugin instance;
  /**
   * @generated
   */
  private ComposedAdapterFactory adapterFactory;
  /**
   * @generated
   */
  private WorkflowDocumentProvider myDocumentProvider;

  /**
   * Constructor
   */
  public WorkflowDiagramEditorPlugin() {
    instance = this;
  }

  /**
   * @generated
   */
  @Override
  public void start( BundleContext context ) throws Exception {
    super.start( context );
    instance = this;
    PreferencesHint.registerPreferenceStore( DIAGRAM_PREFERENCES_HINT,
                                             getPreferenceStore() );
    this.adapterFactory = createAdapterFactory();
  }

  /**
   * @generated
   */
  @Override
  public void stop( BundleContext context ) throws Exception {
    this.adapterFactory.dispose();
    this.adapterFactory = null;
    instance = null;
    super.stop( context );
  }

  /**
   * @generated
   */
  public static WorkflowDiagramEditorPlugin getInstance() {
    return instance;
  }

  /**
   * @generated
   */
  protected ComposedAdapterFactory createAdapterFactory() {
    List factories = new ArrayList();
    fillItemProviderFactories( factories );
    return new ComposedAdapterFactory( factories );
  }

  /**
   * @generated
   */
  protected void fillItemProviderFactories( List factories ) {
    factories.add( new WorkflowItemProviderAdapterFactory() );
    factories.add( new ResourceItemProviderAdapterFactory() );
    factories.add( new ReflectiveItemProviderAdapterFactory() );
  }

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static WorkflowDiagramEditorPlugin getDefault() {
    return instance;
  }
  
  /**
   * @generated
   */
  public AdapterFactory getItemProvidersAdapterFactory() {
    return this.adapterFactory;
  }

  /**
   * @generated
   */
  public ImageDescriptor getItemImageDescriptor( Object item ) {
    IItemLabelProvider labelProvider = ( IItemLabelProvider )this.adapterFactory.adapt( item, IItemLabelProvider.class );
    if( labelProvider != null ) {
      return ExtendedImageRegistry.getInstance().getImageDescriptor( labelProvider.getImage( item ) );
    }
    return null;
  }

  /**
   * Returns an image descriptor for the image file at the given
   * plug-in relative path.
   *
   * @generated
   * @param path the path
   * @return the image descriptor
   */
  public static ImageDescriptor getBundledImageDescriptor( String path ) {
    return AbstractUIPlugin.imageDescriptorFromPlugin( ID, path );
  }

  /**
   * Respects images residing in any plug-in. If path is relative,
   * then this bundle is looked up for the image, otherwise, for absolute 
   * path, first segment is taken as id of plug-in with image
   *
   * @generated
   * @param path the path to image, either absolute (with plug-in id as first segment), or relative for bundled images
   * @return the image descriptor
   */
  public static ImageDescriptor findImageDescriptor( String path ) {
    final IPath p = new Path( path );
    if( p.isAbsolute() && p.segmentCount() > 1 ) {
      return AbstractUIPlugin.imageDescriptorFromPlugin( p.segment( 0 ),
                                                         p.removeFirstSegments( 1 )
                                                           .makeAbsolute()
                                                           .toString() );
    } else {
      return getBundledImageDescriptor( p.makeAbsolute().toString() );
    }
  }

  /**
   * Returns string from plug-in's resource bundle
   * @generated
   */
  public static String getString( String key ) {
    return Platform.getResourceString( getInstance().getBundle(), "%" + key ); //$NON-NLS-1$
  }

  /**
   * Returns an image for the image file at the given plug-in relative path.
   * Client do not need to dispose this image. Images will be disposed automatically.
   *
   * @generated
   * @param path the path
   * @return image instance
   */
  public Image getBundledImage( String path ) {
    Image image = getImageRegistry().get( path );
    if( image == null ) {
      getImageRegistry().put( path, getBundledImageDescriptor( path ) );
      image = getImageRegistry().get( path );
    }
    return image;
  }

  /**
   * @return 
   * @generated
   */
  public WorkflowDocumentProvider getDocumentProvider() {
    if( this.myDocumentProvider == null ) {
      this.myDocumentProvider = new WorkflowDocumentProvider();
    }
    return this.myDocumentProvider;
  }

  /**
   * @generated
   */
  public void logError( String error ) {
    logError( error, null );
  }

  /**
   * @param throwable actual error or null could be passed
   * @generated NOT
   */
  public void logError( String error, Throwable throwable ) {
    if( error == null && throwable != null ) {
      error = throwable.getMessage();
    }
    getLog().log( new Status( IStatus.ERROR,
                              WorkflowDiagramEditorPlugin.ID,
                              IStatus.OK,
                              error,
                              throwable ) );
//    debug( error, throwable );
  }

  /**
   * Logs an exception to the eclipse logger.
   * 
   * @param exc The exception to be logged.
   */
  public static void logException( final Throwable exc ) {
    String message = exc.getLocalizedMessage();
    if( message == null )
      message = exc.getClass().getName();
    IStatus status = new Status( IStatus.ERROR,
                                 ID,
                                 IStatus.OK,
                                 message,
                                 exc );
    logStatus( status );
  }
  
  /**
   * @generated
   */
  public void logInfo( String message ) {
    logInfo( message, null );
  }

  /**
   * @param throwable actual error or null could be passed
   * @generated NOT
   */
  public void logInfo( String message, Throwable throwable ) {
    if( message == null && throwable != null ) {
      message = throwable.getMessage();
    }
    getLog().log( new Status( IStatus.INFO,
                              WorkflowDiagramEditorPlugin.ID,
                              IStatus.OK,
                              message,
                              throwable ) );
//    debug( message, throwable );
  }
  
  /**
   * Logs a status object to the eclipse logger.
   * 
   * @param status The status to be logged.
   */
  public static void logStatus( final IStatus status ) {
    getDefault().getLog().log( status );
  }

//  private void debug( String message, Throwable throwable ) {
//    if( !isDebugging() ) {
//      return;
//    }
//    if( message != null ) {
//      System.err.println( message );
//    }
//    if( throwable != null ) {
//      throwable.printStackTrace();
//      ProblemDialog.openProblem( getSite().getShell(),
//                                 Messages.getString("Title goes here"), //$NON-NLS-1$
//                                 Messages.getString("Actual text of error goes here"), //$NON-NLS-1$
//                                 throwable );
//    }
//  }
}
