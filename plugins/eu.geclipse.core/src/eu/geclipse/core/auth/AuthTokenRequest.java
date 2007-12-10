package eu.geclipse.core.auth;

public class AuthTokenRequest {
  
  private IAuthenticationTokenDescription description;
  
  private String requester;
  
  private String purpose;

  public AuthTokenRequest( final IAuthenticationTokenDescription description,
                           final String requester,
                           final String purpose ) {
    this.description = description;
    this.requester = requester;
    this.purpose = purpose;
  }
  
  public IAuthenticationTokenDescription getDescription() {
    return this.description;
  }
  
  public String getPurpose() {
    return this.purpose;
  }
  
  public String getRequester() {
    return this.requester;
  }

}
