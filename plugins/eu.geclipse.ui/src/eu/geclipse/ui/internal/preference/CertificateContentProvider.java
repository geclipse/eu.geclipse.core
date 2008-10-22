package eu.geclipse.ui.internal.preference;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ICertificateManager;

public class CertificateContentProvider
    implements ITreeContentProvider {
  
  public enum GroupMode {
    Country,
    State,
    Locality,
    Organization,
    OrganizationalUnit,
    Trust
  }
  
  private static final String NA_STRING = "N/A";
  
  private boolean grouped;
  
  private GroupMode groupMode;
  
  public CertificateContentProvider() {
    this.grouped = true;
    this.groupMode = GroupMode.Organization;
  }

  public Object[] getChildren( final Object parentElement ) {
    
    Object[] result = new Object[ 0 ];
    
    if ( parentElement instanceof Entry< ?, ? > ) {
      Object value = ( ( Entry< ?, ? > ) parentElement ).getValue();
      if ( value instanceof List< ? > ) {
        List< ? > list = ( List< ? > ) value;
        result = list.toArray( new Object[ list.size() ] );
      }
    }
    
    return result;
    
  }

  public Object getParent( final Object element ) {
    return null;
  }

  public boolean hasChildren( final Object element ) {
    
    boolean result = false;
    
    if ( element instanceof Entry< ?, ? > ) {
      result = true;
    }
    
    return result;
    
  }

  public Object[] getElements( final Object inputElement ) {
    
    Object[] result = new Object[ 0 ];
    
    if ( inputElement instanceof ICertificateManager ) {
    
      List< ICertificateHandle > certificates
        = ( ( ICertificateManager ) inputElement ).getAllCertificates();
      
      if ( ! isGrouped() ) {
        result = certificates.toArray();
      }
      
      else {
        Set< Entry < String, List< ICertificateHandle > > > entrySet = getGroupedCertificates( certificates, getGroupMode() );
        result = entrySet.toArray();
      }
      
    }
    
    return result;
    
  }

  public void dispose() {
  }

  public void inputChanged( final Viewer viewer,
                            final Object oldInput,
                            final Object newInput ) {
  }
  
  public GroupMode getGroupMode() {
    return this.groupMode;
  }
  
  public boolean isGrouped() {
    return this.grouped;
  }
  
  public void setGrouped( final boolean b ) {
    this.grouped = b;
  }
  
  public void setGroupMode( final GroupMode mode ) {
    this.groupMode = mode;
  }
  
  private Set< Entry < String, List< ICertificateHandle > > > getGroupedCertificates( final List< ICertificateHandle > certs,
                                                                                     final GroupMode m ) {
    
    Hashtable< String, List< ICertificateHandle > > table
      = new Hashtable< String, List< ICertificateHandle > >();
  
    for ( ICertificateHandle c : certs ) {
      String name = getGroupName( c, m );
      if ( ( name == null ) || ( name.trim().length() == 0 ) ) {
        name = NA_STRING;
      }
      List< ICertificateHandle > list = table.get( name );
      if ( list == null ) {
        list = new ArrayList< ICertificateHandle >();
        table.put( name, list );
      }
      list.add( c );
    }

    return table.entrySet();
    
  }
  
  private String getGroupName( final ICertificateHandle h, final GroupMode m ) {
    
    String result = NA_STRING;
    
    if ( m == GroupMode.Country ) {
      result = getSubjectDN( h.getCertificate(), "C" );
    } else if ( m == GroupMode.State ) {
      result = getSubjectDN( h.getCertificate(), "ST" );
    } else if ( m == GroupMode.Locality ) {
      result = getSubjectDN( h.getCertificate(), "L" );
    } else if ( m == GroupMode.Organization ) {
      result = getSubjectDN( h.getCertificate(), "O" );
    } else if ( m == GroupMode.OrganizationalUnit ) {
      result = getSubjectDN( h.getCertificate(), "OU" );
    } else if ( m == GroupMode.Trust ) {
      result = h.getTrust().toString();
    }
    
    return result;
    
  }
  
  static String getSubjectDN( final X509Certificate c, final String part ) {
    
    String result = NA_STRING;
    
    String search = part.toUpperCase() + "="; 
    String name = c.getSubjectX500Principal().getName();
    String[] parts = name.split( "[,/]" );
    
    for ( String s : parts ) {
      if ( s.trim().toUpperCase().startsWith( search ) ) {
        result = s.trim().substring( search.length() );
        break;
      }
    }
    
    return result;
    
  }
  
}