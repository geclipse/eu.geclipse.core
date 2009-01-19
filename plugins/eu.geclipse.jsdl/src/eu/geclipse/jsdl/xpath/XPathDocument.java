package eu.geclipse.jsdl.xpath;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class simplifying querying of XML {@link Document} using XPath language.
 * Handle such things like namespaces, caching compiled expressions etc.
 */
public class XPathDocument {  
  private static XPath xpathEngine;
  private Document document;
  private Map<String,XPathExpression> expressionsMap;
  
  /**
   * @param document on which XPath queries will be run
   */
  public XPathDocument( final Document document ) {
    super();
    this.document = document;
    this.expressionsMap = new HashMap<String,XPathExpression>();
  }

  /**
   * Get nodes selected by giving XPath
   * @param parentNode XML node, from which query will be executed.
   * @param xpathQuery
   * @return nodes selected by query
   * @throws XPathExpressionException
   */
  public NodeList getNodes( final Node parentNode, final String xpathQuery ) throws XPathExpressionException  {
    NodeList nodes = ( NodeList )getExpression( xpathQuery ).evaluate( parentNode, XPathConstants.NODESET );
    return nodes;
  }
  
  /**
   * Makes the same as {@link XPathDocument#getNodes(Node, String)} starting from document root
   * @param xpathQuery
   * @return nodes selected by query
   * @throws XPathExpressionException
   */
  public NodeList getNodes( final String xpathQuery ) throws XPathExpressionException {
    return getNodes( this.document.getDocumentElement(), xpathQuery );
  }
  
  /**
   * Get String value from node selected by query.
   * @param parentNode
   * @param xpathQuery
   * @return text value from selected node
   * @throws XPathExpressionException 
   */
  public String getValue( final Node parentNode, final String xpathQuery ) throws XPathExpressionException {
    return getExpression( xpathQuery ).evaluate( parentNode );    
  }
  

  private XPath getXPathEngine() {
    if( xpathEngine == null ) {
      xpathEngine = XPathFactory.newInstance().newXPath();
      xpathEngine.setNamespaceContext( getNamespaceContext() );
    }
    return xpathEngine;    
  }
  
  private XPathExpression getExpression( final String xpathQuery ) throws XPathExpressionException {
    XPathExpression expression = this.expressionsMap.get( xpathQuery );
    
    if( expression == null ) {
      expression = getXPathEngine().compile( xpathQuery );
      this.expressionsMap.put( xpathQuery, expression );
    }    
    
    return expression;
  }
  
  private NamespaceContext getNamespaceContext() {
    
    return new NamespaceContext() {
// TODO mariusz parse source jsdl document add here all prefixes and namespaces (user may use these prefixes)

      public String getNamespaceURI( final String prefix ) {
        String namespace = XMLConstants.NULL_NS_URI;
        if ("jsdl".equals(prefix)) {  //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl"; //$NON-NLS-1$
        }
        else if( "jsdl-posix".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ggf.org/jsdl/2005/11/jsdl-posix"; //$NON-NLS-1$
        }
        else if( "sweep".equals( prefix ) ) {  //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep" ; //$NON-NLS-1$
        }
        else if( "sweepfunc".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://schemas.ogf.org/jsdl/2007/01/jsdl-sweep/functions" ; //$NON-NLS-1$
        }
        else if ("xml".equals( prefix )) { //$NON-NLS-1$
          namespace = XMLConstants.XML_NS_URI;
        }
        else if( "fn".equals( prefix ) ) { //$NON-NLS-1$
          namespace = "http://www.w3.org/2005/xpath-functions"; //$NON-NLS-1$
        }
        return namespace;
      }

      public String getPrefix( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }

      @SuppressWarnings("unchecked")
      public Iterator getPrefixes( final String namespaceURI ) {
        throw new UnsupportedOperationException();
      }};
  }

  @Override
  public String toString() {
    String string = "(null)"; //$NON-NLS-1$
    
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform( new DOMSource( this.document ), new StreamResult( outputStream ) );
      string = outputStream.toString();
    } catch( TransformerConfigurationException exception ) {      
      string = exception.getMessage();
    } catch( TransformerException exception ) {
      string = exception.getMessage();
    }  

    return string;
  }

  /**
   * @return dom document, which is attached to this xpath document
   */
  public Document getDomDocument() {
    return this.document;
  }  
}
