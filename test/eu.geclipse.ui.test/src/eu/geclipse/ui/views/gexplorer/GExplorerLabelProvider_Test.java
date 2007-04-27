package eu.geclipse.ui.views.gexplorer;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.efs.gridftp.GridFile;

public class GExplorerLabelProvider_Test {

  private URI uri;
  private FileInfo fileInfo;
  private ISharedImages sharedImages;
  private ILabelProvider labelProvider;

  @Before
  public void setUp()
  {
    // TODO mateusz pabis
    try {
      this.uri = new URI( "gridftp://ce.egee.man.poznan.pl:2811/home/egee/" ); //$NON-NLS-1$
    } catch( URISyntaxException e ) {
      e.printStackTrace();
    }
    Assert.assertNotNull( this.uri );
    this.labelProvider = new GExplorerLabelProvider();
  }

  @After
  public void tearDown()
  {
    // TODO mateusz pabis
  }

  @Test
  public void testGetTextFromNull()
  {
    Assert.assertNotNull( this.labelProvider.getText( null ) );
  }

  @Test
  public void testGetTextFromFile()
  {
    String name = "file_name"; //$NON-NLS-1$
    this.fileInfo = new FileInfo( name );
    this.fileInfo.setDirectory( false );
    GridFile gridFile = new GridFile( this.uri, this.fileInfo );
    ResourceNode node = new ResourceNode( gridFile );
    Assert.assertEquals( name, this.labelProvider.getText( node ) );
  }

  @Test
  public void testGetTextFromNonResourceNode()
  {
    Assert.assertNotNull( this.labelProvider.getText( "non ResourceNode object" ) ); //$NON-NLS-1$
  }

  @Test
  public void testGetDirectoryImage()
  {
    this.sharedImages = PlatformUI.getWorkbench().getSharedImages();
    this.fileInfo = new FileInfo( "dirname/" ); //$NON-NLS-1$
    this.fileInfo.setDirectory( true );
    GridFile gridFile = new GridFile( this.uri, this.fileInfo );
    ResourceNode node = new ResourceNode( gridFile );
    Assert.assertEquals( this.sharedImages.getImage( ISharedImages.IMG_OBJ_FOLDER ),
                         this.labelProvider.getImage( node ) );
  }

  @Test
  public void testGetFileImage()
  {
    this.sharedImages = PlatformUI.getWorkbench().getSharedImages();
    this.fileInfo = new FileInfo( "filename" ); //$NON-NLS-1$
    this.fileInfo.setDirectory( false );
    GridFile gridFile = new GridFile( this.uri, this.fileInfo );
    ResourceNode node = new ResourceNode( gridFile );
    Assert.assertEquals( this.sharedImages.getImage( ISharedImages.IMG_OBJ_FILE ),
                         this.labelProvider.getImage( node ) );
  }

  @Test
  public void testGetUnknownImage()
  {
    this.sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Assert.assertEquals( this.sharedImages.getImage( ISharedImages.IMG_OBJS_ERROR_TSK ),
                         this.labelProvider.getImage( "non ResourceNode object" ) ); //$NON-NLS-1$
  }
}
