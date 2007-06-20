package eu.geclipse.ui.wizards;

public class GenericConnectionTokenValidator implements
    IConnectionTokenValidator {

  public String validateToken( final String tokenID,
                               final String tokenValue ) {
    
    String error = null;
    
    if ( URI_TOKEN.equals( tokenID ) ) {
      error = validateURI( tokenValue );
    }
    
    else if ( SCHEME_SPEC_TOKEN.equals( tokenID ) ) {
      error = validateSchemeSpecificPart( tokenValue );
    }
    
    else if ( AUTHORITY_TOKEN.equals( tokenID ) ) {
      error = validateAuthority( tokenValue );
    }
    
    else if ( USER_INFO_TOKEN.equals( tokenID ) ) {
      error = validateUserInfo( tokenValue );
    }
    
    else if ( HOST_TOKEN.equals( tokenID ) ) {
      error = validateHost( tokenValue );
    }
    
    else if ( PORT_TOKEN.equals( tokenID ) ) {
      error = validatePort( tokenValue );
    }
    
    else if ( PATH_TOKEN.equals( tokenID ) ) {
      error = validatePath( tokenValue );
    }
    
    else if ( QUERY_TOKEN.equals( tokenID ) ) {
      error = validateQuery( tokenValue );
    }
    
    else if ( FRAGMENT_TOKEN.equals( tokenID ) ) {
      error = validateFragment( tokenValue );
    }
    
    return error;
    
  }
  
  protected String validateURI( final String uri ) {
    return null;
  }
  
  protected String validateSchemeSpecificPart( final String schemeSpecificPart ) {
    return null;
  }
  
  protected String validateAuthority( final String authority ) {
    return null;
  }
  
  protected String validateUserInfo( final String userInfo ) {
    return null;
  }
  
  protected String validateHost( final String host ) {
    return null;
  }
  
  protected String validatePort( final String port ) {
    
    String error = null;
    
    try {
      int portnr = Integer.parseInt( port );
      error = validatePort( portnr );
    } catch ( NumberFormatException nfExc ) {
      error = "The port is not a number"; 
    }
    
    return error;
    
  }
  
  protected String validatePort( final int port ) {
    
    String error = null;
    
    if ( ( port < 1 ) || ( port > 65535 ) ) {
      error = "The port has to be in the range 1-65535";
    }
    
    return error;
    
  }
  
  protected String validatePath( final String path ) {
    return null;
  }
  
  protected String validateQuery( final String query ) {
    return null;
  }
  
  protected String validateFragment( final String fragment ) {
    return null;
  }

}
