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
package eu.geclipse.ui.wizards.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Element;
import eu.geclipse.ui.internal.wizards.jobs.ApplicationSpecificControlsFactory;
import eu.geclipse.ui.internal.wizards.jobs.DataStageControlsData;
import eu.geclipse.ui.wizards.jobs.wizardnodes.BasicWizardPart;
import eu.geclipse.ui.wizards.jobs.wizardnodes.SpecificWizardPart;

/**
 * The last page in {@link SpecificWizardPart}, holding {@link BasicWizardPart}
 * wizard node. There should be only one instance of this class per wizard
 */
public class ApplicationSpecificLastPage extends WizardSelectionPage
  implements IApplicationSpecificPage
{

  private Element pageElement;
  private ArrayList<Text> textFieldsWithFileChooser;
  private HashMap<Control, String> controlsParametersNames;
  private ArrayList<DataStageControlsData> controlsDataStagingIn = new ArrayList<DataStageControlsData>();
  private ArrayList<DataStageControlsData> controlsDataStagingOut = new ArrayList<DataStageControlsData>();

  /**
   * Method to create an instance of {@link ApplicationSpecificLastPage}
   * 
   * @param pageName name of the page
   * @param element element form the xml holding information about this page
   * @param node wizard node of next wizard (its pages will be displayed after
   *          this page)
   */
  public ApplicationSpecificLastPage( final String pageName,
                                      final Element element,
                                      final IWizardNode node )
  {
    super( pageName );
    this.pageElement = element;
    this.setSelectedNode( node );
    this.textFieldsWithFileChooser = new ArrayList<Text>();
    this.controlsParametersNames = new HashMap<Control, String>();
  }

  public Map<String, ArrayList<String>> getParametersValues() {
    Map<String, ArrayList<String>> result = null;
    if( this.controlsParametersNames != null
        && !this.controlsParametersNames.isEmpty() )
    {
      result = new HashMap<String, ArrayList<String>>();
      for( Control control : this.controlsParametersNames.keySet() ) {
        String controlText = null;
        if( control instanceof Text ) {
          controlText = ( ( Text )control ).getText();
        } else if( control instanceof Combo ) {
          controlText = ( ( Combo )control ).getText();
        }
        if( !result.containsKey( this.controlsParametersNames.get( control ) ) )
        {
          ArrayList<String> values = new ArrayList<String>();
          if( controlText != null && !controlText.equals( "" ) ) { //$NON-NLS-1$
            values.add( controlText );
          }
          result.put( this.controlsParametersNames.get( control ), values );
        } else {
          result.get( this.controlsParametersNames.get( control ) )
            .add( controlText );
        }
      }
    }
    return result;
  }

  public void createControl( final Composite parent ) {
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 3, false );
    mainComp.setLayout( gLayout );
    ApplicationSpecificControlsFactory factory = new ApplicationSpecificControlsFactory();
    factory.createControls( this.pageElement,
                            mainComp,
                            this.textFieldsWithFileChooser,
                            this.controlsParametersNames,
                            this.controlsDataStagingIn,
                            this.controlsDataStagingOut );
    setControl( mainComp );
    setPageComplete( true );
    this.getContainer().updateButtons();
  }

  public Map<String, Properties> getStageInFiles() {
    Map<String, Properties> result = null;
    if( this.controlsDataStagingIn != null
        && !this.controlsDataStagingIn.isEmpty() )
    {
      result = new HashMap<String, Properties>();
      for( DataStageControlsData controlsData : this.controlsDataStagingIn ) {
        // 1st need to check if this control is simple text or multiple list
        if( controlsData.isMultipleList() ) {
          // multiple list data processing
        } else {
          if( result.containsKey( controlsData.getArgName() ) ) {
            result.get( controlsData.getArgName() )
              .setProperty( ( ( Text )( controlsData.getNameControl() ) ).getText(),
                            ( ( Text )( controlsData.getURIControl() ) ).getText() );
          } else {
            Properties prop = new Properties();
            prop.setProperty( ( ( Text )( controlsData.getNameControl() ) ).getText(),
                              ( ( Text )( controlsData.getURIControl() ) ).getText() );
            result.put( controlsData.getArgName(), prop );
          }
        }
      }
    }
    return result;
  }

  public Map<String, Properties> getStageOutFiles() {
    Map<String, Properties> result = null;
    if( this.controlsDataStagingOut != null
        && !this.controlsDataStagingOut.isEmpty() )
    {
      result = new HashMap<String, Properties>();
      for( DataStageControlsData controlsData : this.controlsDataStagingOut ) {
        // 1st need to check if this control is simple text or multiple list
        if( controlsData.isMultipleList() ) {
          // multiple list data processing
        } else {
          if( result.containsKey( controlsData.getArgName() ) ) {
            result.get( controlsData.getArgName() )
              .setProperty( ( ( Text )( controlsData.getNameControl() ) ).getText(),
                            ( ( Text )( controlsData.getURIControl() ) ).getText() );
          } else {
            Properties prop = new Properties();
            prop.setProperty( ( ( Text )( controlsData.getNameControl() ) ).getText(),
                              ( ( Text )( controlsData.getURIControl() ) ).getText() );
            result.put( controlsData.getArgName(), prop );
          }
        }
      }
    }
    return result;
  }
}
