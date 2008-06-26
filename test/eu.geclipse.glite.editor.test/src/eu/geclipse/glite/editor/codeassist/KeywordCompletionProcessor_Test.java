/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Jie Tao - test class (Junit test)
 *****************************************************************************/

package eu.geclipse.glite.editor.codeassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;



/**test class for {@link KeywordCompletionProcessor}
 * @author tao-j
 *
 */
public class KeywordCompletionProcessor_Test {

  private static KeywordCompletionProcessor processor;
  
  /**initial setups; create a KeywordCompletionProcessor class
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    processor = new KeywordCompletionProcessor();
  }
  
  /**tests the method {@link KeywordCompletionProcessor#
   * computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)}
   * the value is null
   */

  @Test
  public void testComputeCompletionProposals() {
    ICompletionProposal[] result = processor.computeCompletionProposals( null, 0 );
    Assert.assertEquals( "Test2", result[0].getAdditionalProposalInfo()); //$NON-NLS-1$
  }

  /**tests the method {@link KeywordCompletionProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)}
   * the value is null
   */
  @Test
  public void testComputeContextInformation() {
    Assert.assertNull( processor.computeContextInformation( null, 0 ) );
  }

  /**tests the method {@link KeywordCompletionProcessor#
   * getCompletionProposalAutoActivationCharacters()}
   * 
   */
  @Test
  public void testGetCompletionProposalAutoActivationCharacters() {
    Assert.assertEquals( new Integer( 0 ), 
                         new Integer( processor.getCompletionProposalAutoActivationCharacters().length ));
  }

  /**tests the method {@link KeywordCompletionProcessor#
   * getContextInformationAutoActivationCharacters()}
   * 
   */
  @Test
  public void testGetContextInformationAutoActivationCharacters() {
    Assert.assertEquals( new Integer( 0 ), 
                         new Integer( processor.getCompletionProposalAutoActivationCharacters().length ));
  }

  /**tests the method {@link KeywordCompletionProcessor#getErrorMessage()}
   * the value is null
   */
  @Test
  public void testGetErrorMessage() {
   Assert.assertNull( processor.getErrorMessage() );
  }


  /**tests the method {@link KeywordCompletionProcessor#getContextInformationValidator()}
   * the value is null
   */
  @Test
  public void testGetContextInformationValidator() {
    Assert.assertNull( processor.getContextInformationValidator() );
  }
}
