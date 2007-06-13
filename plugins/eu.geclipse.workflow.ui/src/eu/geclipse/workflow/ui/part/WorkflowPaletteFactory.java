/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.part;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @generated
 */
public class WorkflowPaletteFactory {

  /**
   * @generated
   */
  public void fillPalette( PaletteRoot paletteRoot ) {
    paletteRoot.add( createWorkflowGroup() );
  }

  /**
   * Creates "Workflow" palette tool group
   * @generated
   */
  private PaletteContainer createWorkflowGroup() {
    PaletteGroup paletteContainer = new PaletteGroup( Messages.WorkflowGroup_title );
    paletteContainer.setDescription( Messages.WorkflowGroup_desc );
    paletteContainer.add( createLinkCreationTool() );
    paletteContainer.add( createInputPortCreationTool() );
    paletteContainer.add( createOutputPortCreationTool() );
    paletteContainer.add( createWorkflowJobCreationTool() );
    return paletteContainer;
  }

  /**
   * @generated
   */
  private ToolEntry createLinkCreationTool() {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/( 1 );
    types.add( WorkflowElementTypes.ILink_3001 );
    LinkToolEntry entry = new LinkToolEntry( Messages.LinkCreationTool_title,
                                             Messages.LinkCreationTool_desc,
                                             types );
    entry.setSmallIcon( WorkflowDiagramEditorPlugin.findImageDescriptor( "/eu.geclipse.workflow.edit/icons/full/obj16/Link.gif" ) ); //$NON-NLS-1$
    entry.setLargeIcon( entry.getSmallIcon() );
    return entry;
  }

  /**
   * @generated
   */
  private ToolEntry createInputPortCreationTool() {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/( 1 );
    types.add( WorkflowElementTypes.IInputPort_2002 );
    NodeToolEntry entry = new NodeToolEntry( Messages.InputPortCreationTool_title,
                                             Messages.InputPortCreationTool_desc,
                                             types );
    entry.setSmallIcon( WorkflowDiagramEditorPlugin.findImageDescriptor( "/eu.geclipse.workflow.edit/icons/full/obj16/InputPort.gif" ) ); //$NON-NLS-1$
    entry.setLargeIcon( entry.getSmallIcon() );
    return entry;
  }

  /**
   * @generated
   */
  private ToolEntry createOutputPortCreationTool() {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/( 1 );
    types.add( WorkflowElementTypes.IOutputPort_2001 );
    NodeToolEntry entry = new NodeToolEntry( Messages.OutputPortCreationTool_title,
                                             Messages.OutputPortCreationTool_desc,
                                             types );
    entry.setSmallIcon( WorkflowDiagramEditorPlugin.findImageDescriptor( "/eu.geclipse.workflow.edit/icons/full/obj16/OutputPort.gif" ) ); //$NON-NLS-1$
    entry.setLargeIcon( entry.getSmallIcon() );
    return entry;
  }

  /**
   * @generated
   */
  private ToolEntry createWorkflowJobCreationTool() {
    List/*<IElementType>*/types = new ArrayList/*<IElementType>*/( 1 );
    types.add( WorkflowElementTypes.IWorkflowJob_1001 );
    NodeToolEntry entry = new NodeToolEntry( Messages.WorkflowJobCreationTool_title,
                                             Messages.WorkflowJobCreationTool_desc,
                                             types );
    entry.setSmallIcon( WorkflowDiagramEditorPlugin.findImageDescriptor( "/eu.geclipse.workflow.edit/icons/full/obj16/WorkflowJob.gif" ) ); //$NON-NLS-1$
    entry.setLargeIcon( entry.getSmallIcon() );
    return entry;
  }
  /**
   * @generated
   */
  private static class NodeToolEntry extends ToolEntry {

    /**
     * @generated
     */
    private final List elementTypes;

    /**
     * @generated
     */
    private NodeToolEntry( String title, String description, List elementTypes )
    {
      super( title, description, null, null );
      this.elementTypes = elementTypes;
    }

    /**
     * @generated
     */
    public Tool createTool() {
      Tool tool = new UnspecifiedTypeCreationTool( elementTypes );
      tool.setProperties( getToolProperties() );
      return tool;
    }
  }
  /**
   * @generated
   */
  private static class LinkToolEntry extends ToolEntry {

    /**
     * @generated
     */
    private final List relationshipTypes;

    /**
     * @generated
     */
    private LinkToolEntry( String title,
                           String description,
                           List relationshipTypes )
    {
      super( title, description, null, null );
      this.relationshipTypes = relationshipTypes;
    }

    /**
     * @generated
     */
    public Tool createTool() {
      Tool tool = new UnspecifiedTypeConnectionTool( relationshipTypes );
      tool.setProperties( getToolProperties() );
      return tool;
    }
  }
}
