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

import java.util.Iterator;
import java.util.Stack;

import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import eu.geclipse.eventgraph.tracereader.otf.OTFReader;
import eu.geclipse.eventgraph.tracereader.otf.Process;
import eu.geclipse.eventgraph.tracereader.otf.util.Node;
import eu.geclipse.traceview.IProcess;

/**
 * Uses Zest to draw a Call Graph
 */
public class CallGraphView extends ViewPart implements IZoomableWorkbenchPart {

  private Graph graph;
  private GraphViewer viewer;
  private long max;
  private int depth;
  private int x;
  private Stack<GraphNode> stack;
  private ISelectionListener selectionListener;

  /**
   * Constructs a new CallGraphView
   */
  public CallGraphView() {
    this.max = 0;
    this.x = 100;
    this.depth = 0;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {
    this.graph = new Graph( parent, SWT.NONE );
    this.graph.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        super.widgetSelected( e );
      }
    } );
    this.graph.addPaintListener( new PaintListener() {

      @SuppressWarnings("synthetic-access")
      public void paintControl( final PaintEvent e ) {
        CallGraphView.this.graph.layout();
      }
    } );
    this.selectionListener = new ISelectionListener() {
      @SuppressWarnings("synthetic-access")
      public void selectionChanged( final IWorkbenchPart part, final ISelection selection ) {
        if( graph.isDisposed() ) {
        	getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener( selectionListener );
        	return;
        }
        if( selection instanceof IStructuredSelection ) {
          IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
          // OTF based call graph
          if( structuredSelection.getFirstElement() instanceof IProcess ) {
            Iterator iterator = structuredSelection.iterator();
            Object[] objects = CallGraphView.this.graph.getConnections().toArray();
            for( int i = 0; i < objects.length; i++ ) {
              ( ( GraphConnection )objects[ i ] ).dispose();
            }
            objects = CallGraphView.this.graph.getNodes().toArray();
            for( int i = 0; i < objects.length; i++ ) {
              ( ( GraphNode )objects[ i ] ).dispose();
            }
            while( iterator.hasNext() ) {
              Object next = iterator.next();
              if( next instanceof Process ) {
                Process process = ( Process )next;
                OTFReader otfReader = ( OTFReader )process.getTrace();
                Node root = otfReader.getRootNode( process.getProcessId() );
                CallGraphView.this.max = 0;
                getMax( root );
                drawNode( root, null );
                CompositeLayoutAlgorithm treeLayoutAlgorithm = new CompositeLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[]{
                  new TreeLayoutAlgorithm( LayoutStyles.NO_LAYOUT_NODE_RESIZING ), new HorizontalShift( LayoutStyles.NO_LAYOUT_NODE_RESIZING )
                } );
                CallGraphView.this.graph.setLayoutAlgorithm( treeLayoutAlgorithm, true );
              }
            }
          } else
          // experimental C/C++ file call graph
          if( structuredSelection.getFirstElement() instanceof ITranslationUnit ) {
            ITranslationUnit translationUnit = ( ITranslationUnit )structuredSelection.getFirstElement();
            Object[] objects = CallGraphView.this.graph.getConnections().toArray();
            for( int i = 0; i < objects.length; i++ ) {
              ( ( GraphConnection )objects[ i ] ).dispose();
            }
            objects = CallGraphView.this.graph.getNodes().toArray();
            for( int i = 0; i < objects.length; i++ ) {
              ( ( GraphNode )objects[ i ] ).dispose(); // TODO find fix to dispose selected nodes
            }
            try {
              IASTTranslationUnit astTranslationUnit = translationUnit.getAST();
              // IASTDeclaration[] declarations = astTranslationUnit.getDeclarations();
              // declarations[ 0 ].getFileLocation();
              // Visitor visitor = new Visitor();
              // astTranslationUnit.accept( visitor);
              IASTNode[] nodes = astTranslationUnit.getChildren();
              for( IASTNode node : nodes ) {
                if( node instanceof IASTFunctionDefinition ) {
                  IASTFunctionDefinition functionDefinition = ( IASTFunctionDefinition )node;
                  if( "main".equals( functionDefinition.getDeclarator().getName().toString() ) ) { //$NON-NLS-1$
                    CallGraphView.this.depth = 0;
                    CallGraphView.this.x = 0;
                    GraphNode main = new GraphNode( CallGraphView.this.graph, SWT.NONE, "main" ); //$NON-NLS-1$
                    CallGraphView.this.stack = new Stack<GraphNode>();
                    CallGraphView.this.stack.push( main );
                    node( functionDefinition );
                  }
                }
              }
            } catch( CoreException e ) {
              e.printStackTrace();
            }
          }
        }
      }
    };
    getSite().getWorkbenchWindow().getSelectionService().addSelectionListener( this.selectionListener );
  }

  private void nodeIf( final IASTIfStatement ifStatement ) {
    this.depth += 50;
    int elsepos = this.depth + 50;
    // IF
    GraphNode ifNode = new GraphNode( this.graph, SWT.NONE, "if " + ifStatement.getConditionExpression().getRawSignature() ); //$NON-NLS-1$
    ifNode.setLocation( this.x - ifNode.getSize().width / 2, this.depth );
    this.x -= ifNode.getSize().width + 50;
    this.depth += 50;
    GraphNode thenNode = new GraphNode( this.graph, SWT.NONE, "then" ); //$NON-NLS-1$
    thenNode.setLocation( this.x - thenNode.getSize().width / 2, this.depth );
    new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, this.stack.lastElement(), ifNode );
    new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, ifNode, thenNode );
    this.stack.push( thenNode );
    node( ifStatement.getThenClause() );
    this.stack.pop();
    this.x += ifNode.getSize().width + 50;
    int backup = this.depth;
    this.depth = elsepos;
    if( ifStatement.getElseClause() != null ) {
      this.x += ifNode.getSize().width + 50;
      GraphNode elseNode = new GraphNode( this.graph, SWT.NONE, "else" ); //$NON-NLS-1$
      new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, ifNode, elseNode );
      elseNode.setLocation( this.x - elseNode.getSize().width / 2, this.depth );
      this.stack.push( elseNode );
      node( ifStatement.getElseClause() );
      this.stack.pop();
      this.x -= ifNode.getSize().width + 50;
    }
    this.depth = backup;
  }

  private void nodeFor( final IASTForStatement forStatement ) {
    this.depth += 50;
    GraphNode graphNode = new GraphNode( this.graph, SWT.NONE, "for " + forStatement.getInitializerStatement().getRawSignature()); //$NON-NLS-1$
    graphNode.setLocation( this.x - graphNode.getSize().width / 2, this.depth );
    this.x -= graphNode.getSize().width + 50;
    new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, this.stack.lastElement(), graphNode );
    this.stack.push( graphNode );
    this.x += graphNode.getSize().width + 50;
    node( forStatement.getBody() );
    new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, this.stack.pop(), graphNode );
  }

  private void node( final IASTNode node ) {
    IASTNode[] children = node.getChildren();
    for( IASTNode child : children ) {
      // Function Call
      if( child instanceof IASTFunctionCallExpression ) {
        this.depth += 50;
        IASTFunctionCallExpression functionCallExpression = ( IASTFunctionCallExpression )child;
        for( IASTNode n : functionCallExpression.getFunctionNameExpression().getChildren() ) {
          GraphNode graphNode = new GraphNode( this.graph, SWT.NONE, n.getRawSignature() );
          graphNode.setLocation( this.x - graphNode.getSize().width / 2, this.depth );
          new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, this.stack.pop(), graphNode );
          this.stack.push( graphNode );
        }
      }
      // IF and ELSE
      else if( child instanceof IASTIfStatement ) {
        IASTIfStatement ifStatement = ( IASTIfStatement )child;
        nodeIf( ifStatement );
        // WHILE
      } else if( child instanceof IASTWhileStatement ) {
        this.depth += 50;
        IASTWhileStatement whileStatement = ( IASTWhileStatement )child;
        this.depth++;
        GraphNode graphNode = new GraphNode( this.graph, SWT.NONE, "while" ); //$NON-NLS-1$
        graphNode.setLocation( x - graphNode.getSize().width / 2, this.depth );
        new GraphConnection( this.graph, ZestStyles.CONNECTIONS_DIRECTED, this.stack.lastElement(), graphNode );
        this.stack.push( graphNode );
        node( child );
        this.stack.pop();
      } else
      // FOR
      if( child instanceof IASTForStatement ) {
        IASTForStatement forStatement = ( IASTForStatement )child;
        nodeFor( forStatement );
      } else {
        node( child );
      }
    }
  }

  private void getMax( final Node node ) {
    if( this.max < node.getTime() ) {
      this.max = node.getTime();
    }
    for( Node child : node.getChildren() ) {
      getMax( child );
    }
  }

  private void drawNode( final Node node, final GraphNode parent ) {
    GraphNode graphNode = new GraphNode( this.graph, SWT.NONE, node.getFunctionName() );
    int percentage = ( ( int )( ( node.getTime() * 255 ) / this.max ) );
    Label label = new Label();
    label.setText( "time: " + node.getTime() );
    graphNode.setTooltip( label );
    Color color = new Color( Display.getDefault(), new RGB( 255, 255 - percentage, 255 - percentage ) );
    graphNode.setBackgroundColor( color );
    if( parent != null ) {
      GraphConnection graphConnection = new GraphConnection( this.graph, SWT.None, parent, graphNode );
      graphConnection.setText( Integer.toString( node.getCount() ) );
    }
    for( Node child : node.getChildren() )
      drawNode( child, graphNode );
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    // TODO Auto-generated method stub
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.zest.core.viewers.IZoomableWorkbenchPart#getZoomableViewer()
   */
  public AbstractZoomableViewer getZoomableViewer() {
    return this.viewer;
  }
}
