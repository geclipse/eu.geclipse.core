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


/**
 * The different types an ACL actor can be. Most of these types
 * require a type-specific extra field, like the X509's DN, to
 * identify the actor. Others, like type 'ANYBODY' do not require
 * more data. Not all implementations support all actor types.
 */
public enum ActorType {
  
  /**
   * Absolutely anybody.
   */
  ANYBODY {
    @Override
    public String toString() {
      return Messages.getString("ActorType.anybody"); //$NON-NLS-1$
    }
  },
  
  /**
   * Anybody registered with a certificate of a known CA.
   */
  CA_ANY_DN_ANY {
    @Override
    public String toString() {
      return Messages.getString("ActorType.CA_any"); //$NON-NLS-1$
    }
  },
  
  /**
   * Anybody registered with the given CA.
   */
  CA_NAME_DN_ANY {
    @Override
    public String toString() {
      return Messages.getString("ActorType.CA_subject"); //$NON-NLS-1$
    }
  },
  
  /**
   * The users satisfying the given certificate DN pattern.
   */
  CA_NAME_DN_PATTERN {
    @Override
    public String toString() {
      return Messages.getString("ActorType.CA_subject_DN_pattern"); //$NON-NLS-1$
    }
  },
  
  /**
   * The user with the given certificate DN.
   */
  CA_NAME_DN_NAME {
    @Override
    public String toString() {
      return Messages.getString("ActorType.CA_subject_DN_subject"); //$NON-NLS-1$
    }
  },
  
  /**
   * Members of access groups satisfying the given pattern.
   */
  GROUP_PATTERN {
    @Override
    public String toString() {
      return Messages.getString("ActorType.group_pattern"); //$NON-NLS-1$
    }
  },
  
  /**
   * Members of the given access group.
   */
  GROUP_NAME {
    @Override
    public String toString() {
      return Messages.getString("ActorType.group_name"); //$NON-NLS-1$
    }
  },
  
  /**
   * The users satisfying the given user name pattern.
   */
  USER_PATTERN {
    @Override
    public String toString() {
      return Messages.getString("ActorType.user_pattern"); //$NON-NLS-1$
    }
  },
  
  /**
   * The user with the given user name.
   */
  USER_NAME {
    @Override
    public String toString() {
      return Messages.getString("ActorType.user_name"); //$NON-NLS-1$
    }
  },
  
  /**
   * The user identified by the given email address.
   */
  USER_EMAIL {
    @Override
    public String toString() {
      return Messages.getString("ActorType.user_email"); //$NON-NLS-1$
    }
  },
  
  /**
   * Users carrying the given SAML attribute.
   */
  SAML_ATTRIBUTE {
    @Override
    public String toString() {
      return Messages.getString("ActorType.SAML_attribute"); //$NON-NLS-1$
    }
  },
  
  /**
   * Other kind of actor, for implementation specific extensions.
   */
  OTHER {
    @Override
    public String toString() {
      return Messages.getString("ActorType.other"); //$NON-NLS-1$
    }
  }

}
