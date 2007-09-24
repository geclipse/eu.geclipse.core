/*******************************************************************************
 * Copyright (c) 2006 g-Eclipse Consortium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Harald Kornmayer - initial implementation
 *******************************************************************************/
package eu.geclipse.glite.editor.scanner;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

class WhitespaceDetector implements IWhitespaceDetector {

  public boolean isWhitespace( final char c ) {
    return ( c == ' ' || c == '\t' || c == '\n' || c == '\r' );
  }
}