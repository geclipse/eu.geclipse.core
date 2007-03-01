package eu.geclipse.ui.internal.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;
import eu.geclipse.core.model.IGridElement;

public class GridElementTransfer extends ByteArrayTransfer {
  
  private static final GridElementTransfer singleton
    = new GridElementTransfer();
  
  /**
   * Copied from {@link org.eclipse.ui.part.ResourceTransfer}.
   */
  private static final String TYPE_NAME
    = "grid-element-transfer-format:" + System.currentTimeMillis() + ":" + singleton.hashCode(); //$NON-NLS-2$ //$NON-NLS-1$
  
  private static final int TYPE_ID
    = registerType( TYPE_NAME );
  
  private GridElementTransfer() {
    // empty implementation
  }
  
  public static GridElementTransfer getInstance() {
    return singleton;
  }

  @Override
  protected int[] getTypeIds() {
    return new int[] { TYPE_ID };
  }

  @Override
  protected String[] getTypeNames() {
    return new String[] { TYPE_NAME };
  }
  
  @Override
  protected void javaToNative( final Object data,
                               final TransferData transferData ) {
    
    if ( ! ( data instanceof IGridElement[] ) ) {
      return;
    }
    
    IGridElement[] input = ( IGridElement[] ) data;
    int nElements = input.length;
    
    try {
    
      ByteArrayOutputStream boStream = new ByteArrayOutputStream();
      DataOutputStream doStream = new DataOutputStream( boStream );
      doStream.writeInt( nElements );
      
      for ( IGridElement element : input ) {
        writeElement( element, doStream );
      }
      
      doStream.close();
      byte[] bytes = boStream.toByteArray();
      super.javaToNative( bytes, transferData );
      
    } catch ( IOException ioExc ) {
      // Just catch and do nothing
    }
    
  }
  
  @Override
  protected Object nativeToJava( final TransferData transferData ) {
    
    Object result = null;
    
    byte[] bytes = ( byte[] ) super.nativeToJava( transferData );
    
    if ( bytes != null ) {

      ByteArrayInputStream biStream = new ByteArrayInputStream( bytes );
      DataInputStream diStream = new DataInputStream( biStream );
      
      try {
        
        int nElements = diStream.readInt();
        IGridElement[] elements = new IGridElement[ nElements ];
        for ( int i = 0 ; i < nElements ; i++ ) {
          elements[i] = readElement( diStream );
        }
        
        result = elements;
        
      } catch ( IOException ioExc ) {
        // Just catch and ignore
      }
      
    }
    
    return result;
    
  }
  
  private void writeElement( final IGridElement element,
                             final DataOutputStream doStream )
      throws IOException {
    
    if ( element.isLocal() && !element.isVirtual() ) {
      IResource resource = element.getResource();
      writeResource( resource, doStream );
    }
    
  }
  
  private void writeResource( final IResource resource, 
                              final DataOutputStream doStream )
      throws IOException {
    
    doStream.writeInt(resource.getType());
    doStream.writeUTF(resource.getFullPath().toString());
    
  }
  
  private IGridElement readElement( final DataInputStream diStream ) {
    IGridElement element = null;
    return element;
  }
  
}
