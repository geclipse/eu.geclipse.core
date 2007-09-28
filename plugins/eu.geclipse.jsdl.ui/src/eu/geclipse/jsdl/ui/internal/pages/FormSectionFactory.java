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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;


/**
 * @author nickl
 *
 */
public class FormSectionFactory {
  
  /**
   * @param toolkit
   * @param parent
   * @param sectionTitle 
   * @param sectionDescription
   * @param numOfColumns
   *  
   * @return Section with Composite
   */
  
  public static Composite createStaticSection(final FormToolkit toolkit, 
                                        final Composite parent, 
                                        final String sectionTitle, 
                                        final String sectionDescription, 
                                        final int numOfColumns )
                                         {
    Section section;
    
      
    section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                              |Section.DESCRIPTION                                              
                                              | SWT.WRAP);
    
    
    section.setText(sectionTitle);    
    section.setDescription( sectionDescription );
    toolkit.createCompositeSeparator(section);
    section.setLayout(FormLayoutFactory.createClearTableWrapLayout(false, 1));
    
    TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
    section.setLayoutData(data);
    
    Composite client = toolkit.createComposite(section);
    client.setLayout(FormLayoutFactory.createSectionClientTableWrapLayout(false,
                                                                 numOfColumns));
    section.setClient(client);
    return client;
    
  }
  
  
  /**
   * @param toolkit
   * @param parent
   * @param sectionTitle
   * @param sectionDescription
   * @param numOfColumns
   * @param isInitialyExpanded
   * @return composite
   */
  public static Composite createExpandableSection(final FormToolkit toolkit, 
                                              final Composite parent, 
                                              final String sectionTitle, 
                                              final String sectionDescription, 
                                              final int numOfColumns,                                             
                                              final boolean isInitialyExpanded) {
    Section section;
         
    if (isInitialyExpanded) {
              
      section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                            |Section.DESCRIPTION
                                            |ExpandableComposite.TWISTIE
                                            | SWT.WRAP);
    }
    else {
      section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                      |Section.DESCRIPTION
                                      |ExpandableComposite.TWISTIE
                                      );
  
    }
            
   
            
    
   
          
    section.setText(sectionTitle);    
    section.setDescription( sectionDescription );
    toolkit.createCompositeSeparator(section);
    section.setLayout(FormLayoutFactory.createClearTableWrapLayout(false, 1));
          
    TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
    section.setLayoutData(data);
          
    Composite client = toolkit.createComposite(section);
    client.setLayout(FormLayoutFactory.createSectionClientTableWrapLayout(false,
                                                                       numOfColumns));
    section.setClient(client);
    return client;
          
 }
  


  /**
   * @param toolkit
   * @param parent
   * @param sectionTitle 
   * @param sectionDescription
   * @param numOfColumns
   * @return Section with Composite
   */
  public static Composite createGridStaticSection(final FormToolkit toolkit,
                                                  final Composite parent,
                                                  final String sectionTitle,
                                                  final String sectionDescription,
                                                  final int numOfColumns
                                                  ) {
    
   Section section;
    
    
     
    section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                              |Section.DESCRIPTION                                              
                                              | SWT.WRAP);
           
    section.setText(sectionTitle);
    section.setDescription( sectionDescription );
    toolkit.createCompositeSeparator(section);
    section.setLayout(FormLayoutFactory.createClearTableWrapLayout(false, 1));
  
    TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
    section.setLayoutData(data);
   
    Composite client = toolkit.createComposite(section);
    client.setLayout(FormLayoutFactory.createSectionClientGridLayout(false,
                                                                 numOfColumns));
    section.setClient(client);
    return client;
  
  }
  
  
  
  /**
   * @param toolkit
   * @param parent
   * @param sectionTitle
   * @param sectionDescription
   * @param numOfColumns
   * @param isInitialyExpanded
   * 
   * @return composite
   */
  public static Composite createGridExpandableSection(final FormToolkit toolkit,
                                                  final Composite parent,
                                                  final String sectionTitle,
                                                  final String sectionDescription,
                                                  final int numOfColumns,
                                                  final boolean isInitialyExpanded ) {
    
    Section section;
    

    
    if (isInitialyExpanded) {
     
      section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                           |Section.DESCRIPTION
                                           |ExpandableComposite.TWISTIE
                                           | SWT.WRAP);
    }
    else {
      section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR
                                     |Section.DESCRIPTION
                                     |ExpandableComposite.TWISTIE
                                     );
 
    }
     
    section.setText(sectionTitle);
    section.setDescription( sectionDescription );
    toolkit.createCompositeSeparator(section);
    section.setLayout(FormLayoutFactory.createClearTableWrapLayout(false, 1));
  
    TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
    section.setLayoutData(data);
   
    Composite client = toolkit.createComposite(section);
    client.setLayout(FormLayoutFactory.createSectionClientGridLayout(false,
                                                                 numOfColumns));
    section.setClient(client);
    return client;
  
  }
  
  
} 
