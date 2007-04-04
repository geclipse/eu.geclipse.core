package eu.geclipse.ui.wizards.jobs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;
import eu.geclipse.ui.internal.Activator;

/**
 * Class to handle ui definition in XML from
 * eu.geclipse.ui.jsdlApplicationParameters extension point
 */
public class ApplicationSpecificControlsFactory {

  /**
   * Name of language used when creating xml schema
   */
  public static final String SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$
  /**
   * Path to location of the xml schema
   */
//  public static final String SCHEMA_LOCATION = "C:\\Documents and Settings\\User\\workspace\\gExplorer_svn\\xsd\\ui_definition.xsd"; //$NON-NLS-1$
  public static final String SCHEMA_LOCATION = "schema/ui_definition.xsd"; //$NON-NLS-1$
  protected static final String TEXT_ELEMENT = "text"; //$NON-NLS-1$
  protected static final String LIST_ELEMENT = "list"; //$NON-NLS-1$
  protected static final String TEXT_WITH_FILE_CHOOSER_ELEMENT = "textWithFileChooser"; //$NON-NLS-1$
  protected static final String TEXT_NAME_ELEMENT = "name"; //$NON-NLS-1$
  protected static final String TEXT_ENABLED_ELEMENT = "enabled"; //$NON-NLS-1$
  protected static final String TEXT_HINT_ELEMENT = "hint"; //$NON-NLS-1$
  protected static final String TEXT_PARAM_NAME_ELEMENT = "paramName"; //$NON-NLS-1$
  protected static final String TEXT_PARAM_PREFIX_ELEMENT = "paramPrefix"; //$NON-NLS-1$
  protected static final String LIST_VALUE_ELEMENT = "value"; //$NON-NLS-1$
  protected static final String LIST_WRITABLE_ELEMENT = "writable"; //$NON-NLS-1$
  ArrayList<Text> textFieldsFromParent;
  ArrayList<Text> textFieldsFromParentPositions;
  ArrayList<SelectionAdapter> adapters = new ArrayList<SelectionAdapter>();
  private HashMap<Control, String> parentControlsParametersNames;

  /**
   * Method to validate XML file agains XML schema sefinition
   * 
   * @param pathToXML path to file to validate
   * @throws SAXException if validation was not successful
   */
  public static void validateFile( final String pathToXML ) throws SAXException
  {
    SchemaFactory factory = SchemaFactory.newInstance( SCHEMA_LANGUAGE );
    
    Path result = new Path( SCHEMA_LOCATION );
    URL fileURL = FileLocator.find( Platform.getBundle( Activator.PLUGIN_ID ),
                                    result,
                                    null );
    try {
      fileURL = FileLocator.toFileURL( fileURL );
    } catch( IOException e1 ) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    String temp = fileURL.toString();
    temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                           + fileURL.getProtocol().length()
                           + 1, temp.length() );
    result = new Path( temp );
    
//    File schemaLocation = new File( result );
    File schemaLocation = result.toFile();
    Schema schema;
    try {
      schema = factory.newSchema( schemaLocation );
      Validator validator = schema.newValidator();
      Source source = new StreamSource( pathToXML );
      validator.validate( source );
    } catch( IOException e ) {
      // TODO katis log
      e.printStackTrace();
    }
  }

  /**
   * Creates controls from xml file defined in extension of
   * eu.geclipse.ui.jsdlApplicationParameters extension point
   * 
   * @param extensionId id ob bundle extending
   *          eu.geclipse.ui.jsdlApplicationParameters extension point
   * @param composite composite to create the controls on
   * @param textFieldsWithFileChooser list holding all Text composites to which
   *          access from GridFileDialog is required
   * @param controlsParametersNames map used by wizard containing application
   *          specific page (for which controls are created by this factory), to
   *          bind value held by control with parameter name in JSDL
   * @throws SAXException This exception is thrown when validation of xml was
   *           not successful
   */
  public void createControls( final String extensionId,
                              final Composite composite,
                              final ArrayList<Text> textFieldsWithFileChooser,
                              final HashMap<Control, String> controlsParametersNames )
    throws SAXException
  {
    this.textFieldsFromParent = textFieldsWithFileChooser;
    this.parentControlsParametersNames = controlsParametersNames;
//    ArrayList<Text> textFieldsFromParent = textFieldsWithFileChooser;
    String path = Extensions.getXMLPath( extensionId ).toString();
    validateFile( path );
    DocumentBuilderFactory factoryDOM = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    try {
      builder = factoryDOM.newDocumentBuilder();
      Document document = builder.parse( new File( path ) );
      DocumentTraversal traversalDoc = ( DocumentTraversal )document;
      NodeIterator iterator = traversalDoc.createNodeIterator( document.getDocumentElement(),
                                                               NodeFilter.SHOW_ELEMENT,
                                                               null,
                                                               true );
//      iterator.nextNode();
//      NodeIterator iterator1 = traversalDoc.createNodeIterator( iterator.nextNode(),
//                                                               NodeFilter.SHOW_ELEMENT,
//                                                               null,
//                                                               true );
      FirstLevelElements currentFirstLevelElement = FirstLevelElements.NULL;
      HashMap<ChildrenElements, String> parametersMap = new HashMap<ChildrenElements, String>();
      ArrayList<String> listValues = new ArrayList<String>();
      for( Node n = iterator.nextNode(); n != null; n = iterator.nextNode() ) {
        String name = ( ( Element )n ).getTagName();
        Element el = ( Element )n;
        switch( FirstLevelElements.valueOfAlias( name ) ) {
          case TEXT:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.TEXT;
          break;
          case LIST:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.LIST;
          break;
          case TEXT_WITH_FILE_CHOOSER:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.TEXT_WITH_FILE_CHOOSER;
          break;
          case MULTIPLE_ARGUMENTS:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.MULTIPLE_ARGUMENTS;
          break;
          case NULL:
            switch( ChildrenElements.valueOfAlias( name ) ) {
              case ENABLED:
                parametersMap.put( ChildrenElements.ENABLED,
                                   el.getTextContent() );
              break;
              case HINT:
                parametersMap.put( ChildrenElements.HINT, el.getTextContent() );
              break;
              case PARAM_NAME:
                // TODO katis - how to pass this information to Wizard???
                parametersMap.put( ChildrenElements.PARAM_NAME,
                                   el.getTextContent() );
              break;
              case PARAM_PREFIX:
                // TODO katis - how to pass this information to Wizard???
                parametersMap.put( ChildrenElements.PARAM_PREFIX,
                                   el.getTextContent() );
              break;
              case VALUE:
                listValues.add( el.getTextContent() );
              break;
              case WRITEABLE:
                parametersMap.put( ChildrenElements.WRITEABLE,
                                   el.getTextContent() );
              break;
              case LABEL:
                parametersMap.put( ChildrenElements.LABEL, el.getTextContent() );
              break;
              case OPTIONAL:
                // TODO katis = how to pass this information to Wizard, walidacja
                parametersMap.put( ChildrenElements.OPTIONAL,
                                   el.getTextContent() );
              break;
              case MAX_ARGUMENTS_COUNT:
                parametersMap.put( ChildrenElements.MAX_ARGUMENTS_COUNT, el.getTextContent() );
              break;
              case MIN_ARGUMENTS_COUNT:
                parametersMap.put( ChildrenElements.MIN_ARGUMENTS_COUNT, el.getTextContent() );
              break;
              default:
                // do nothing
              break;
            }
          break;
        }
      }
      // creating last read element
      if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
        createControl( currentFirstLevelElement,
                       composite,
                       parametersMap,
                       listValues );
      }
    } catch( ParserConfigurationException parserConfigExc ) {
      // TODO katis log
      parserConfigExc.printStackTrace();
    } catch( IOException ioExc ) {
      // TODO katis log
      ioExc.printStackTrace();
    } catch( IllegalArgumentException iaExc ) {
      // TODO katis log
      iaExc.printStackTrace();
    }
  }

  private void createControl( final FirstLevelElements currentFirstLevelElement,
                              final Composite composite,
                              final HashMap<ChildrenElements, String> parametersMap,
                              final ArrayList<String> listValues )
  {
    switch( currentFirstLevelElement ) {
      case TEXT:
        createTextControl( composite, parametersMap );
      break;
      case LIST:
        createListControl( composite, parametersMap, listValues );
      break;
      case TEXT_WITH_FILE_CHOOSER:
        createTextWithFileChooser( composite, parametersMap );
      break;
      case MULTIPLE_ARGUMENTS:
        createMultipleArgumentsList( composite, parametersMap );
      break;
      default:
        //do nothing
      break;
    }
  }


  private void createTextControl( final Composite composite,
                                  final HashMap<ChildrenElements, String> parametersMap )
  {
    GridData layout = new GridData();
    Label textLabel = new Label( composite, SWT.NONE );
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      textLabel.setText( parametersMap.get( ChildrenElements.LABEL ) );
    }
    layout.horizontalAlignment = GridData.BEGINNING;
    layout.verticalAlignment = GridData.CENTER;
    textLabel.setLayoutData( layout );
    layout = new GridData();
    layout.verticalAlignment = GridData.CENTER;
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    layout.grabExcessHorizontalSpace = true;
    Text textControl = new Text( composite, SWT.BORDER );
    for( ChildrenElements child : parametersMap.keySet() ) {
      switch( child ) {
        case ENABLED:
          boolean enabled = false;
          if( parametersMap.get( child )
            .compareToIgnoreCase( Boolean.TRUE.toString() ) == 0 )
          {
            enabled = true;
          }
          textControl.setEnabled( enabled );
        break;
        case HINT:
          textControl.setToolTipText( parametersMap.get( child ) );
        break;
        case PARAM_NAME:
          this.parentControlsParametersNames.put( textControl,
                                                  parametersMap.get( child ) );
        break;
        default:
        break;
      }
    }
    textControl.setLayoutData( layout );
  }

  private void createListControl( final Composite composite,
                                  final HashMap<ChildrenElements, String> parametersMap,
                                  final ArrayList<String> values )
  {
    GridData layout = new GridData();
    Label textLabel = new Label( composite, SWT.NONE );
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      textLabel.setText( parametersMap.get( ChildrenElements.LABEL ) );
    }
    layout.horizontalAlignment = GridData.BEGINNING;
    layout.verticalAlignment = GridData.CENTER;
    textLabel.setLayoutData( layout );
    layout = new GridData();
    layout.verticalAlignment = GridData.CENTER;
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    layout.grabExcessHorizontalSpace = true;
    // according to xsd list is read-only by default
    boolean isReadOnly = false;
    if( parametersMap.containsKey( ChildrenElements.WRITEABLE ) ) {
      if( parametersMap.get( ChildrenElements.WRITEABLE )
        .equalsIgnoreCase( Boolean.TRUE.toString() ) )
      {
        isReadOnly = true;
      }
    }
    Combo listControl;
    if( isReadOnly ) {
      listControl = new Combo( composite, SWT.READ_ONLY );
    } else {
      listControl = new Combo( composite, SWT.NONE );
    }
    for( ChildrenElements child : parametersMap.keySet() ) {
      switch( child ) {
        case ENABLED:
          boolean enabled = false;
          if( parametersMap.get( child )
            .equalsIgnoreCase( Boolean.TRUE.toString() ) )
          {
            enabled = true;
          }
          listControl.setEnabled( enabled );
        break;
        case HINT:
          listControl.setToolTipText( parametersMap.get( child ) );
        break;
        case PARAM_NAME:
          this.parentControlsParametersNames.put( listControl,
                                                  parametersMap.get( child ) );
        break;
        default:
        break;
      }
    }
    for( String value : values ) {
      listControl.add( value );
    }
    listControl.setLayoutData( layout );
  }
  

  private void createTextWithFileChooser( final Composite composite,
                                          final HashMap<ChildrenElements, String> parametersMap )
  {
    GridData layout = new GridData();
    Label textLabel = new Label( composite, SWT.NONE );
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      textLabel.setText( parametersMap.get( ChildrenElements.LABEL ) );
    }
    layout.horizontalAlignment = GridData.BEGINNING;
    layout.verticalAlignment = GridData.CENTER;
    textLabel.setLayoutData( layout );
    layout = new GridData();
    layout.verticalAlignment = GridData.CENTER;
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 1;
    layout.grabExcessHorizontalSpace = true;
    Text textControl = new Text( composite, SWT.BORDER );
    for( ChildrenElements child : parametersMap.keySet() ) {
      switch( child ) {
        case ENABLED:
          boolean enabled = false;
          if( parametersMap.get( child )
            .compareToIgnoreCase( Boolean.TRUE.toString() ) == 0 )
          {
            enabled = true;
          }
          textControl.setEnabled( enabled );
        break;
        case HINT:
          textControl.setToolTipText( parametersMap.get( child ) );
        break;
        case PARAM_NAME:
          this.parentControlsParametersNames.put( textControl,
                                                  parametersMap.get( child ) );
        break;
        default:
        break;
      }
    }
    textControl.setLayoutData( layout );
    Button fileButton = new Button( composite, SWT.PUSH );
    if( this.textFieldsFromParent.isEmpty() ) {
      this.textFieldsFromParent.add( textControl );
    } else {
      // make sure that newly added Text ist last in ArrayList
      this.textFieldsFromParent.add( this.textFieldsFromParent.size(),
                                     textControl );
    }
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    fileButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.adapters.add( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog dialog = new GridFileDialog( PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          ApplicationSpecificControlsFactory.this.textFieldsFromParent.get( ApplicationSpecificControlsFactory.this.adapters.indexOf( this ) )
            .setText( filename );
          // fileButton.setText( filename );
        }
      }
    } );
    fileButton.addSelectionListener( this.adapters.get( this.adapters.size() - 1 ) );
    fileButton.setLayoutData( layout );
  }
  
  private void createMultipleArgumentsList( final Composite composite, final HashMap<ChildrenElements, String> parametersMap ) {
    GridData layout = new GridData();
    Label textLabel = new Label( composite, SWT.NONE );
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      textLabel.setText( parametersMap.get( ChildrenElements.LABEL ) );
    }
    layout.horizontalAlignment = GridData.BEGINNING;
    layout.verticalAlignment = GridData.CENTER;
    textLabel.setLayoutData( layout );
    layout = new GridData();
    layout.verticalAlignment = GridData.CENTER;
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    layout.grabExcessHorizontalSpace = true;
    HashMap<String, String> temp = new HashMap<String, String>();
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      temp.put( parametersMap.get( ChildrenElements.LABEL ), parametersMap.get( ChildrenElements.LABEL ) );
    } else {
//      temp.put( "value", "value" );
    }
    
    MultipleArgumentList multipleList = new MultipleArgumentList( new ArrayContentProvider(), new StringLabelProvider(), temp );
    multipleList.createControl( composite );
    multipleList.getControl().setSize( 2, 10 );
    multipleList.getControl().setLayoutData( layout );
  }
  
  protected enum FirstLevelElements {
    /**
     * Text element (child of specificInput element)
     */
    TEXT("text"), //$NON-NLS-1$
    /**
     * List element ((child of specificInput element)
     */
    LIST("list"), //$NON-NLS-1$
    /**
     * TextWithFileChooser element (child of specificInput element)
     */
    TEXT_WITH_FILE_CHOOSER("textWithFileChooser"), //$NON-NLS-1$
    /**
     * MultipleArguments element (child of specificInput element)
     */
    MULTIPLE_ARGUMENTS("multipleArguments"), //$NON-NLS-1$
    /**
     * Value for any other value than {@link FirstLevelElements#TEXT},
     * {@link FirstLevelElements#LIST} or
     * {@link FirstLevelElements#TEXT_WITH_FILE_CHOOSER}
     */
    NULL("null"); //$NON-NLS-1$

    private final String alias;

    FirstLevelElements( final String alias ) {
      this.alias = alias;
    }

    /**
     * Get alias ov enum value
     * 
     * @return "text" for {@link FirstLevelElements#TEXT}<br>
     *         "list" for {@link FirstLevelElements#LIST}<br>
     *         "textWithFileChooser" for
     *         {@link FirstLevelElements#TEXT_WITH_FILE_CHOOSER}
     */
    public String getAlias() {
      return this.alias;
    }

    /**
     * Returns enum value for given alias (see
     * {@link FirstLevelElements#getAlias()}
     * 
     * @param alias alias of enum value
     * @return enum value for given alias or {@link FirstLevelElements#NULL} if
     *         there is no value with this alias
     */
    public static FirstLevelElements valueOfAlias( final String alias ) {
      FirstLevelElements result = null;
      try {
        result = valueOf( alias );
      } catch( IllegalArgumentException iaExc ) {
        for( FirstLevelElements element : FirstLevelElements.values() ) {
          if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
            result = element;
          }
        }
        if( result == null ) {
          result = FirstLevelElements.NULL;
        }
      }
      return result;
    }
  }
  protected enum ChildrenElements {
    /**
     * Enabled element (child of Text, List or TextFithFileChooser elements)
     */
    ENABLED("enabled"), //$NON-NLS-1$
    /**
     * Hint element (child of Text, List or TextFithFileChooser elements)
     */
    HINT("hint"), //$NON-NLS-1$
    /**
     * ParamName element (child of Text, List or TextFithFileChooser elements)
     */
    PARAM_NAME("paramName"), //$NON-NLS-1$
    /**
     * ParamPrefix element (child of Text, List or TextFithFileChooser elements)
     */
    PARAM_PREFIX("paramPrefix"), //$NON-NLS-1$
    /**
     * Value element (child of List elelemnt)
     */
    VALUE("value"), //$NON-NLS-1$
    /**
     * Writeable element (child of List element)
     */
    WRITEABLE("writeable"), //$NON-NLS-1$
    /**
     * Label element (child of Text, List or TextFithFileChooser elements)
     */
    LABEL("label"), //$NON-NLS-1$
    /**
     * Optional element (child of Text, List or TextWithFileChooser elements)
     */
    OPTIONAL("optional"), //$NON-NLS-1$
    /**
     * MinArgumentsCount element (child of multipleArguments element) 
     */
    MIN_ARGUMENTS_COUNT ("minArgumentsCount"), //$NON-NLS-1$
    /**
     * MaxArgumentsCount element (child of multipleArguments element) 
     */
    MAX_ARGUMENTS_COUNT ("maxArgumentsCount"), //$NON-NLS-1$
    /**
     * Value for any other value that is not a value of children elements
     * defined in this enum
     */
    NULL("null"); //$NON-NLS-1$

    private final String alias;

    ChildrenElements( final String alias ) {
      this.alias = alias;
    }

    /**
     * Get alias ov enum value
     * 
     * @return alias of a given enum value
     */
    public String getAlias() {
      return this.alias;
    }

    /**
     * Returns enum value for given alias
     * 
     * @param alias alias of enum value
     * @return enum value for given alias or {@link ChildrenElements#NULL} if
     *         there is no value with this alias
     */
    public static ChildrenElements valueOfAlias( final String alias ) {
      ChildrenElements result = null;
      try {
        result = valueOf( alias );
      } catch( IllegalArgumentException iaExc ) {
        for( ChildrenElements element : ChildrenElements.values() ) {
          if( element.getAlias().compareToIgnoreCase( alias ) == 0 ) {
            result = element;
          }
        }
        if( result == null ) {
          result = ChildrenElements.NULL;
        }
      }
      return result;
    }
  }
}
