package eu.geclipse.ui.internal.preference;

import java.net.URL;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;

import eu.geclipse.ui.internal.Activator;

public class PerspectivePreferencePage extends FieldEditorPreferencePage
  implements IWorkbenchPreferencePage
{

  public static final String KEY_NOT_SWITCH_FROM_GECLIPSE_PERSPECTIVE = "NotSwitchFromGEclipsePerspectives";  //$NON-NLS-1$
  public static final String KEY_REMEMBER_SWITCHING = "RememberSwitchingPerspective"; //$NON-NLS-1$
  public static final String KEY_DEFAULT_PERSPECTIVE = "DefaultPerspective"; //$NON-NLS-1$

  /**
   * Constructor for Jobs Preferences page
   */
  public PerspectivePreferencePage() {
    super( FieldEditorPreferencePage.GRID );
    IPreferenceStore preferenceStore = Activator.getDefault()
      .getPreferenceStore();
    setPreferenceStore( preferenceStore );
    preferenceStore.setDefault( KEY_NOT_SWITCH_FROM_GECLIPSE_PERSPECTIVE, true );
    preferenceStore.setDefault( KEY_DEFAULT_PERSPECTIVE, Activator.ID_USER_PERSPECTIVE );
    preferenceStore.setDefault( KEY_REMEMBER_SWITCHING, MessageDialogWithToggle.PROMPT );
    setDescription( "These settings control perspectives for running grid projects. A perspective can automatically open when new grid project is created." );
  }

  @Override
  protected void createFieldEditors() {
    ImageDescriptor userPerspectiveImage=null;
    ImageDescriptor operatorPerspectiveImage=null;
    ImageDescriptor developerPerspectiveImage=null;

    RadioGroupFieldEditor editor = new RadioGroupFieldEditor( KEY_REMEMBER_SWITCHING,
                                                              "Open g-Eclipse perspective after creating Grid Project",
                                                              3,
                                                              new String[][]{
                                                                {
                                                                  "Always",
                                                                  MessageDialogWithToggle.ALWAYS
                                                                },
                                                                {
                                                                  "Never",
                                                                  MessageDialogWithToggle.NEVER
                                                                },
                                                                {
                                                                  "Prompt",
                                                                  MessageDialogWithToggle.PROMPT
                                                                },
                                                              },
                                                              getFieldEditorParent(),
                                                              true );
    addField( editor );
    IPerspectiveRegistry reg =
      PlatformUI.getWorkbench().getPerspectiveRegistry();

    String[][] labels=new String[][]{
      {
        "Grid User Perspective",
        Activator.ID_USER_PERSPECTIVE
      },
      {
        "Grid Operator Perspective",
        Activator.ID_OPERATOR_PERSPECTIVE
      },
      {
        "Grid Developer Perspective",
        Activator.ID_DEVELOPER_PERSPECTIVE
      }};

    IPerspectiveDescriptor p;
    p = reg.findPerspectiveWithId( Activator.ID_USER_PERSPECTIVE );
    if(p!=null){
      labels[0][0]=p.getLabel();
      userPerspectiveImage = p.getImageDescriptor();
    }
    p = reg.findPerspectiveWithId( Activator.ID_OPERATOR_PERSPECTIVE );
    if(p!=null){
      labels[1][0]=p.getLabel();
      operatorPerspectiveImage = p.getImageDescriptor();
    }
    p = reg.findPerspectiveWithId( Activator.ID_DEVELOPER_PERSPECTIVE );
    if(p!=null){
      labels[2][0]=p.getLabel();
      developerPerspectiveImage = p.getImageDescriptor();
    }

    editor = new RadioGroupFieldEditor( KEY_DEFAULT_PERSPECTIVE,
                                        "Choose perspective to open for new Grid Project",
                                        1,
                                        labels,
                                        getFieldEditorParent(),
                                        true );
    addField( editor );

    //add perspective images
    Control[] children = editor.getRadioBoxControl( getFieldEditorParent() ).getChildren();
    if(userPerspectiveImage!=null && children[0]!=null && children[0] instanceof Button){
      ((Button)children[0]).setImage( userPerspectiveImage.createImage() );
    }
    if(operatorPerspectiveImage!=null && children[1]!=null && children[1] instanceof Button){
      ((Button)children[1]).setImage( operatorPerspectiveImage.createImage() );
    }
    if(developerPerspectiveImage!=null && children[2]!=null && children[2] instanceof Button){
      ((Button)children[2]).setImage( developerPerspectiveImage.createImage() );
    }

    
    BooleanFieldEditor bfe=new BooleanFieldEditor(KEY_NOT_SWITCH_FROM_GECLIPSE_PERSPECTIVE, "Do not switch from g-Eclipse perspective (User, Operator or Developer)",getFieldEditorParent());
    addField(bfe);
  }

  public void init( IWorkbench workbench ) {
  }
}
