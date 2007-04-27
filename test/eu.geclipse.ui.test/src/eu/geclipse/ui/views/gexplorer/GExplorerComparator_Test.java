package eu.geclipse.ui.views.gexplorer;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import eu.geclipse.efs.gridftp.GridFile;


public class GExplorerComparator_Test {
  private GExplorerComparator comparator;
  private URI uri;
  
  @Before 
  public final void setUp() {
    this.comparator = new GExplorerComparator();
    try {
      this.uri = new URI( "gridftp://ce.egee.man.poznan.pl:2811/home/egee/" ); //$NON-NLS-1$
    } catch( URISyntaxException e ) {
      e.printStackTrace();
    }
    Assert.assertNotNull( this.uri );
  }
  @Test
  public final void testCategory()
  {
    Assert.assertNotNull( this.comparator );
    // Test category of undefined object
    Assert.assertEquals( new Integer( GExplorerComparator.TYPE_UNKOWN ),
                         new Integer( this.comparator.category( "Non ResourceNode object" ) ) ); //$NON-NLS-1$
    // Test category of null object
    Assert.assertNotNull( new Integer( this.comparator.category( null ) ) );
    Assert.assertEquals( new Integer( GExplorerComparator.TYPE_UNKOWN ),
                                      new Integer( this.comparator.category( null ) ) );
    // Test category of file object
    FileInfo fileInfo1 = new FileInfo( "file_name" ); //$NON-NLS-1$
    fileInfo1.setDirectory( false );
    GridFile gridFile1 = new GridFile( this.uri, fileInfo1 );
    ResourceNode file_node = new ResourceNode( gridFile1 );
    Assert.assertNotNull( file_node );
    Assert.assertEquals( new Integer( GExplorerComparator.TYPE_FILE ), 
                                      new Integer( this.comparator.category( file_node ) ) );
    // Test category of directory object    
    FileInfo fileInfo2 = new FileInfo( "dir_name" ); //$NON-NLS-1$
    fileInfo2.setDirectory( true );
    GridFile gridFile2 = new GridFile( this.uri, fileInfo2 );
    ResourceNode dir_node = new ResourceNode( gridFile2 );
    Assert.assertNotNull( dir_node );
    Assert.assertEquals( new Integer( GExplorerComparator.TYPE_DIR ), 
                                      new Integer( this.comparator.category( dir_node ) ) );
  }

  @Test
  public final void testCompare()
  {
    FileInfo fileInfo1 = new FileInfo( "aby" ); //$NON-NLS-1$
    fileInfo1.setDirectory( false );
    GridFile gridFile1 = new GridFile( this.uri, fileInfo1 );
    ResourceNode file_node = new ResourceNode( gridFile1 );

    FileInfo fileInfo2 = new FileInfo( "aby/" ); //$NON-NLS-1$
    fileInfo2.setDirectory( true );
    GridFile gridFile2 = new GridFile( this.uri, fileInfo2 );
    ResourceNode dir_node = new ResourceNode( gridFile2 );

    FileInfo fileInfo3 = new FileInfo( "Aazab" ); //$NON-NLS-1$
    fileInfo3.setDirectory( false );
    GridFile gridFile3 = new GridFile( this.uri, fileInfo3 );
    ResourceNode file_node_z = new ResourceNode( gridFile3 );

    FileInfo fileInfo4 = new FileInfo( "Aazab" ); //$NON-NLS-1$
    fileInfo4.setDirectory( true );
    GridFile gridFile4 = new GridFile( this.uri, fileInfo4 );
    ResourceNode dir_node_z = new ResourceNode( gridFile4 );

    
    Assert.assertNotNull( file_node );
    Assert.assertNotNull( file_node_z );
    Assert.assertNotNull( dir_node );
    Assert.assertNotNull( dir_node_z );
    
    boolean result = this.comparator.compare( null, dir_node, file_node ) < 0;
    Assert.assertTrue( result );
    result = this.comparator.compare( null, dir_node, dir_node_z ) > 0;
    Assert.assertTrue( result );
    result = this.comparator.compare( null, file_node, file_node_z ) > 0;
    Assert.assertTrue( result );

  }
}
