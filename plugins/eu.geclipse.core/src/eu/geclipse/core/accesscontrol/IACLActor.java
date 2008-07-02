/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.accesscontrol;

import eu.geclipse.core.reporting.ProblemException;


/**
 * An actor to be used in the ACL entries. An actor is granted or
 * denied a given operation or role on a given resource, by means
 * of an ACL.
 * <p>
 * Also known as 'target-subject' in the XACML standard.
 * 
 * @author agarcia
 */
public interface IACLActor {
  
  /**
   * The different types an ACL actor can be. Most of these types
   * require a type-specific extra field, like the X509's DN, to
   * identify the actor. Others, like type 'ANYBODY' do not require
   * more data. Not all implementations support all actor types.
   */
  public static enum ActorType {
    /** Absolutely anybody */
    ANYBODY,
    /** Anybody registered with a certificate of a known CA */
    CA_ANY_DN_ANY,
    /** Anybody registered with the given CA */
    CA_NAME_DN_ANY,
    /** The users satisfying the given certificate DN pattern */
    CA_NAME_DN_PATTERN,
    /** The user with the given certificate DN */
    CA_NAME_DN_NAME,
    /** Members of access groups satisfying the given pattern */
    GROUP_PATTERN,
    /** Members of the given access group */
    GROUP_NAME,
    /** The users satisfying the given user name pattern */
    USER_PATTERN,
    /** The user with the given user name */
    USER_NAME,
    /** The user identified by the given email address */
    USER_EMAIL,
    /** Users carrying the given SAML attribute */
    SAML_ATTRIBUTE,
    /** Other kind of actor, for implementation specific extensions */
    OTHER
  }
  
  /**
   * Queries the {@link ActorType}s supported by this implementation.
   * 
   * @return an array of supported ActorTypes
   */
  public ActorType[] getSupportedTypes();
  
  /**
   * Returns the {@link ActorType} of this actor.
   * 
   * @return the ActorType
   */
  public ActorType getActorType();
  
  /**
   * Sets the {@link ActorType} of this actor.
   * 
   * @param type the ActorType to set
   * @throws ProblemException if the type could not be set, for instance
   *         because the chosen type is not supported by this implementation
   */
  public void setActorType( final ActorType type ) throws ProblemException;
  
  /**
   * Gets the CA subject of this actor, if the type requires it.
   * 
   * @return the issuer subject of this actor. Must return <code>null</code>
   *         if the type doesn't require an authority.
   */
  public String getCA();
  
  /**
   * Sets the CA subject of this actor, if the type requires it.
   * 
   * @param caName the CA subject to set
   * @throws ProblemException if the type doesn't require a CA, or if
   *         the argument is <code>null</code> but the type requires it
   */
  public void setCA( final String caName ) throws ProblemException;
  
  /**
   * Gets the actor's ID. Depending on the {@link ActorType} this
   * means the X509's DN, a group name, or a SAML attribute in the
   * format <code>"attr_name=value"</code>.
   * 
   * @return the actor's ID
   */
  public String getID();
  
  /**
   * Sets the actor's ID. Depending on the {@link ActorType} this
   * means the X509's DN, a group name, a user name, or a SAML attribute
   * in the format <code>"attr_name=value"</code>. Might also be a pattern.
   * 
   * @param actorId the ID (resp. a pattern) to set for this actor
   * @throws ProblemException if the argument is <code>null</code>
   *         or invalid for the selected actor type
   */
  public void setID( final String actorId ) throws ProblemException;
  
}
