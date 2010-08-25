/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.callgraph.views;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class Visitor extends ASTVisitor {

  IASTNode node;

  public Visitor() {
    super();
    this.node = null;
    this.shouldVisitDeclarations = true;
    this.shouldVisitStatements = true;
    this.shouldVisitExpressions = true;
    // this.shouldvis
    // this.shouldVisitNames = true;
    // this.shouldVisitStatements = true;
  }

  public int visit( final IASTStatement statement ) {
    // System.out.println(statement.getRawSignature());
    return PROCESS_CONTINUE;
  }

  @Override
  public int visit( final IASTDeclaration declaration ) {
    // System.out.println(declaration.getRawSignature());
    if( declaration instanceof IASTFunctionDeclarator ) {
      IASTFunctionDeclarator functionDeclarator = ( IASTFunctionDeclarator )declaration;
      System.out.println( functionDeclarator.getName() );
    }
    return PROCESS_CONTINUE;
  }

  @Override
  public int visit( final IASTExpression expression ) {
    if( expression instanceof IASTFunctionCallExpression ) {
      IASTFunctionCallExpression functionCallExpression = ( IASTFunctionCallExpression )expression;
      if( this.node == null ) {
        functionCallExpression.getParent();
      }
      System.out.println( functionCallExpression.getRawSignature() );
    }
    return PROCESS_CONTINUE;
  }
}
