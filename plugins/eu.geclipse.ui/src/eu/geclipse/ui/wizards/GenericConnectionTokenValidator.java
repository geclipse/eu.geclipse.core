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
  
  protected String validateURI( @SuppressWarnings("unused")
                                final String uri ) {
    return null;
  }
  
  protected String validateSchemeSpecificPart( @SuppressWarnings("unused")
                                               final String schemeSpecificPart ) {
    return null;
  }
  
  protected String validateAuthority( @SuppressWarnings("unused")
                                      final String authority ) {
    return null;
  }
  
  protected String validateUserInfo( @SuppressWarnings("unused")
                                     final String userInfo ) {
    return null;
  }
  
  protected String validateHost( @SuppressWarnings("unused")
                                 final String host ) {
    return null;
  }
  
  protected String validatePort( final String port ) {
    
    String error = null;
    
    try {
      int portnr = Integer.parseInt( port );
      error = validatePort( portnr );
    } catch ( NumberFormatException nfExc ) {
      error = Messages.getString("GenericConnectionTokenValidator.port_nan");  //$NON-NLS-1$
    }
    
    return error;
    
  }
  
  protected String validatePort( final int port ) {
    
    String error = null;
    
    if ( ( port < 1 ) || ( port > 65535 ) ) {
      error = Messages.getString("GenericConnectionTokenValidator.port_invalid_range"); //$NON-NLS-1$
    }
    
    return error;
    
  }
  
  protected String validatePath( @SuppressWarnings("unused")
                                 final String path ) {
    return null;
  }
  
  protected String validateQuery( @SuppressWarnings("unused")
                                  final String query ) {
    return null;
  }
  
  protected String validateFragment( @SuppressWarnings("unused")
                                     final String fragment ) {
    return null;
  }

}
