package eu.geclipse.ui.wizards;

import eu.geclipse.ui.Extensions;

public interface IConnectionTokenValidator {
  
  public static final String URI_TOKEN = Extensions.EFS_URI_ATT;
  
  public static final String SCHEME_SPEC_TOKEN = Extensions.EFS_SCHEME_SPEC_PART_ATT;
  
  public static final String AUTHORITY_TOKEN = Extensions.EFS_AUTHORITY_ATT;
  
  public static final String USER_INFO_TOKEN = Extensions.EFS_USER_INFO_ATT;
  
  public static final String HOST_TOKEN = Extensions.EFS_HOST_ATT;
  
  public static final String PORT_TOKEN = Extensions.EFS_PORT_ATT;
  
  public static final String PATH_TOKEN = Extensions.EFS_PATH_ATT;
  
  public static final String QUERY_TOKEN = Extensions.EFS_QUERY_ATT;
  
  public static final String FRAGMENT_TOKEN = Extensions.EFS_FRAGMENT_ATT;

  public String validateToken( final String tokenID, final String tokenValue );
  
}
