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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A set of utility functions, used to execute repeating tasks which are
 * scattered throughout the code base.
 * 
 * @author Moritz Post
 */
public class AWSUtil {

  /**
   * Transforms an AWS account number in the form "AAAA-BBBB-CCCC" to be in the
   * form "AAAABBBBCCCC". The later format is accepted by the AWS webservice
   * infrastructure. If the account no is allready in the suitable form, the
   * string is trimed and returned.
   * <p>
   * Additionally, if the inputs account id is not in the correct form, the
   * conversation does not take place at all and <code>null</code> is returned.
   * 
   * @param accountNumber the account number to transform
   * @return the transformed account number or <code>null</code> if the provided
   *         number did not comply to the expected format
   */
  public static String transformAWSAccountNumber( final String accountNumber ) {

    if( accountNumber != null ) {

      String trimmedAcNo = accountNumber.trim();

      Pattern p = Pattern.compile( "\\d{4}-\\d{4}-\\d{4}" ); //$NON-NLS-1$
      Matcher m = p.matcher( trimmedAcNo );
      if( m.matches() ) {
        return trimmedAcNo.replace( "-", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      }

      p = Pattern.compile( "\\d{12}" ); //$NON-NLS-1$
      m = p.matcher( trimmedAcNo );
      if( m.matches() ) {
        return trimmedAcNo;
      }

    }
    return null;
  }
}
