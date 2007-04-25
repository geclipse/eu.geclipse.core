/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.ui.internal.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;
import eu.geclipse.ui.wizards.jobs.IApplicationSpecificPage;

/**
 * Class to handle ui definition in XML from
 * eu.geclipse.ui.jsdlApplicationParameters extension point
 */
public class ApplicationSpecificControlsFactory {


  ArrayList<Text> textFieldsFromParent;
  ArrayList<Text> textFieldsFromParentPositions;
  ArrayList<SelectionAdapter> adapters = new ArrayList<SelectionAdapter>();
  private HashMap<Control, String> parentControlsParametersNames;
  private ArrayList<DataStageControlsData> parentStagingInControls;
  private ArrayList<DataStageControlsData> parentStagingOutControls;

  /**
   * Creates controls from xml file defined in extension of
   * eu.geclipse.ui.jsdlApplicationParameters extension point
   * @param element element form xml (page element child) describing controls used on {@link IApplicationSpecificPage} for which those controls will be created 
   * @param composite composite to create the controls on
   * @param textFieldsWithFileChooser list holding all Text composites to which
   *          access from GridFileDialog is required
   * @param controlsParametersNames map used by wizard containing application
   *          specific page (for which controls are created by this factory), to
   *          bind value held by control with parameter name in JSDL 
   */
  public void createControls( final Element element,
                              final Composite composite,
                              final ArrayList<Text> textFieldsWithFileChooser,
                              final HashMap<Control, String> controlsParametersNames,
                              ArrayList<DataStageControlsData> stagingIn,
                              ArrayList<DataStageControlsData> stagingOut )
  {
    this.textFieldsFromParent = textFieldsWithFileChooser;
    this.parentControlsParametersNames = controlsParametersNames;
    this.parentStagingInControls = stagingIn;
    this.parentStagingOutControls = stagingOut;
    try {
      FirstLevelElements currentFirstLevelElement = FirstLevelElements.NULL;
      HashMap<ChildrenElements, String> parametersMap = new HashMap<ChildrenElements, String>();
      ArrayList<String> listValues = new ArrayList<String>();
      NodeList chilrenElements = element.getElementsByTagName( "*" ); //$NON-NLS-1$
      for( int i = 0; i < chilrenElements.getLength(); i++ ) {
        // for( Node n = iterator.nextNode(); n != null; n = iterator.nextNode()
        // ) {
        Element el = ( Element )chilrenElements.item( i );
        String name = el.getTagName();
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
          case TEXT_DATA_STAGING:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.TEXT_DATA_STAGING;
          break;
          case MULTIPLE_DATA_STAGING:
            if( !currentFirstLevelElement.equals( FirstLevelElements.NULL ) ) {
              createControl( currentFirstLevelElement,
                             composite,
                             parametersMap,
                             listValues );
            }
            parametersMap = new HashMap<ChildrenElements, String>();
            listValues = new ArrayList<String>();
            currentFirstLevelElement = FirstLevelElements.MULTIPLE_DATA_STAGING;
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
                // TODO katis - how to pass this information to Wizard,
                // validation
                parametersMap.put( ChildrenElements.OPTIONAL,
                                   el.getTextContent() );
              break;
              case MAX_ARGUMENTS_COUNT:
                parametersMap.put( ChildrenElements.MAX_ARGUMENTS_COUNT,
                                   el.getTextContent() );
              break;
              case MIN_ARGUMENTS_COUNT:
                parametersMap.put( ChildrenElements.MIN_ARGUMENTS_COUNT,
                                   el.getTextContent() );
              break;
              case FILE:
                parametersMap.put( ChildrenElements.FILE,
                                   el.getTextContent() );
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
        createTextWithFileChooserControl( composite, parametersMap );
      break;
      case MULTIPLE_ARGUMENTS:
        createMultipleArgumentsListControl( composite, parametersMap );
      break;
      case TEXT_DATA_STAGING:
        createTextDataStagingControl ( composite, parametersMap );
      break;
//      case MULTIPLE_DATA_STAGING:
//        createMultipleDataStagingControl ( composite, parametersMap );
//      break;
      default:
        // do nothing
      break;
    }
  }

  private void createTextDataStagingControl( final Composite composite, final HashMap<ChildrenElements, String> parametersMap ) {
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
    
    Label nameLabel = new Label ( composite, SWT.NONE );
    nameLabel.setText( "Local file name" );
    Text textControlName = new Text( composite, SWT.BORDER );
    
    Label URILabel = new Label ( composite, SWT.NONE );
    URILabel.setText( "Remote location" );
    Text textControlURI = new Text( composite, SWT.BORDER );
    
    String paramName = "";
    FileType fileType = FileType.NULL;
    for( ChildrenElements child : parametersMap.keySet() ) {
      switch( child ) {
        case ENABLED:
          boolean enabled = false;
          if( parametersMap.get( child )
            .compareToIgnoreCase( Boolean.TRUE.toString() ) == 0 )
          {
            enabled = true;
          }
          textControlName.setEnabled( enabled );
          textControlURI.setEnabled( enabled );
        break;
        case HINT:
          textControlName.setToolTipText( parametersMap.get( child ) );
          textControlURI.setToolTipText( parametersMap.get( child ) );
        break;
        case PARAM_NAME:
          paramName = parametersMap.get( child );
//          this.parentControlsParametersNames.put( textControl,
//                                                  parametersMap.get( child ) );
        break;
        case FILE:
          fileType = FileType.valueOfAlias( parametersMap.get( child ) );
//        default:
//        break;
      }
    }
    switch ( fileType ){
      case INPUT:
        this.parentStagingInControls.add( new DataStageControlsData(paramName, textControlName, textControlURI) );
      break;
      case OUTPUT:
        this.parentStagingOutControls.add( new DataStageControlsData(paramName, textControlName, textControlURI) );
      break;
      case NULL:
        //do nothing
      break;
    }
    textControlName.setLayoutData( layout );
    textControlURI.setLayoutData( layout );

    
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

  private void createTextWithFileChooserControl( final Composite composite,
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

  private void createMultipleArgumentsListControl( final Composite composite,
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
    HashMap<String, String> temp = new HashMap<String, String>();
    if( parametersMap.containsKey( ChildrenElements.LABEL ) ) {
      temp.put( parametersMap.get( ChildrenElements.LABEL ),
                parametersMap.get( ChildrenElements.LABEL ) );
    } else {
      // temp.put( "value", "value" );
    }
    MultipleArgumentList multipleList = new MultipleArgumentList( new ArrayContentProvider(),
                                                                  new StringLabelProvider(),
                                                                  temp );
    multipleList.createControl( composite );
    multipleList.getControl().setSize( 2, 10 );
    multipleList.getControl().setLayoutData( layout );
  }
}
