package eu.geclipse.info.glue;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;


public class GlueUtility {
  
  public static String getStringAttribute( final String attribute,
                                           final Attributes attributes )
  {
    String result = "";
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  attr.get().toString();
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }

  public static int getIntegerAttribute( final String attribute,
                                   final Attributes attributes )
  {
    int result = -1;
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  Integer.parseInt( attr.get().toString() );
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }

  public static long getLongAttribute( final String attribute,
                                       final Attributes attributes )
  {
    long result = -1;
    Attribute attr = attributes.get( attribute );
    if( attr != null ) {
      try {
        result =  Long.parseLong( attr.get().toString() );
      } catch( NamingException e ) {
        // ignore missing fields
      }
    }
    return result;
  }
}
