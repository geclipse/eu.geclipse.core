/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     FZK (http://www.fzk.de)
 *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
 *     GUP, JKU (http://www.gup.jku.at/)
 *      - Thomas Koeckerbauer
 *****************************************************************************/

package eu.geclipse.ui.widgets;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * The <code>StoredCombo</code> is a extension of the <code>org.eclipse.swt.widgets.Combo</code>
 * that provides functionality for storing the entered values in a preference store
 * and restoring these values the next time a StoredCombo is initialized with the same
 * preference ID. 
 */
public class StoredCombo extends Combo {
  
  /**
   * The separator used to separate the values that are stored in the preference store.
   */
  private final static String prefSeparator = "\u001E"; //$NON-NLS-1$

  /**
   * The prefix used to denote the last item preference
   */
  private static String LAST_ITEM_PREFIX = "_last"; //$NON-NLS-1$
  
  /**
   * Internal switch used for auto completion.
   */
  protected boolean allowCompletion = true;
  
  /**
   * The preference store used to re-/store the values of this combo.
   */
  private IPreferenceStore prefStore = null;
  
  /**
   * The ID that is used to query the preference store.
   */
  private String prefID;
  
  /**
   * Construct a new <code>StoredCombo</code>.
   * 
   * @param parent The parent of this <code>StoredCombo</code>.
   * @param style The style of this <code>StoredCombo</code>.
   */
  public StoredCombo( final Composite parent, final int style ) {
    super( parent, style );
    addModifyListener( new ModifyListener() {
      private boolean alreadyModifing = false;
      public void modifyText( final ModifyEvent event ) {
        if ( !this.alreadyModifing && StoredCombo.this.allowCompletion ) {
          this.alreadyModifing = true;
          doAutoCompletion();
          this.alreadyModifing = false;
        }
      }
    } );
    addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetDefaultSelected( final SelectionEvent event ) {
        StoredCombo.this.allowCompletion = false;
      }
    } );
    addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent event ) {
        switch ( event.character ) {
          case SWT.BS:
          case SWT.DEL:
            StoredCombo.this.allowCompletion = false;
            break;
          default:
            StoredCombo.this.allowCompletion = true;
            break;
        }
      }
    } );
    addFocusListener( new FocusAdapter() {
      @Override
      public void focusLost( final FocusEvent event ) {
        Combo combo = (Combo) event.widget;
        String text = combo.getText();
        combo.setSelection( new Point( text.length(), text.length() ) );
      }
    } );
    addDisposeListener( new DisposeListener() {
      public void widgetDisposed( final DisposeEvent e ) {
        savePreferences();
      }
    });
  }

  /**
   * Set the preferences of this <code>StoredCombo</code>. This method initialises the
   * Combo with the values that were found in the specified preference store with the
   * help of the specified ID.
   * 
   * @param preferenceStore The preference store to query for the initial settings. This preference
   * store is also used to store the values when the combo is disposed.
   * @param preferenceID
   */
  public void setPreferences( final IPreferenceStore preferenceStore,
                              final String preferenceID ) {
    removeAll();
    this.prefStore = preferenceStore;
    this.prefID = preferenceID;
    if ( this.prefStore != null ) {
      String itemList = this.prefStore.getString( this.prefID );
      String[] items = itemList.split( StoredCombo.prefSeparator );
      for( String item : items ) {
        if ( isValidItem( item ) ) add( item );
      }
      String lastItem = this.prefStore.getString( this.prefID+LAST_ITEM_PREFIX );
      if ( isValidItem( lastItem ) ) {
        boolean save = this.allowCompletion;
        this.allowCompletion = false;
        setText( lastItem );
        this.allowCompletion = save;
      }
    }
  }
  
  /**
   * Set the item that is displayed as default. The item is tested for validity with the 
   * {@link #isValidItem(String)} method. If the item is not valid it is ignored and not
   * set as default. This method returns the result of {@link #isValidItem(String)} and
   * therefore returns true if the item was set successfully as default.
   * 
   * @param defaultItem The item that should be set as default.
   * @return True of the item is valid and could be set as default, false otherwise.
   */
  public boolean setDefaultItem( final String defaultItem ) {
    boolean valid = isValidItem( defaultItem ); 
    if ( valid ) {
      boolean save = this.allowCompletion;
      this.allowCompletion = false;
      setText( defaultItem );
      this.allowCompletion = save;
    }
    return valid;
  }
  
  protected void doAutoCompletion() {
    
    String text = getText();
    
    if ( text.length() > 0 ) {
      
      String newText = null;
      String[] items = getItems();

      for( String item : items ) {
        if ( item.startsWith( text ) && ( ( newText == null ) || ( item.length() < newText.length() ) ) ) {
          newText = item;
        }
      }
      
      if ( newText != null ) {
        setText( newText );
        setSelection( new Point( text.length(), newText.length() ) );
      }
      
    }
    
  }

  /**
   * Save the items of this Combo to the formerly specified preference store.
   */
  protected void savePreferences() {
    if ( this.prefStore != null ) {
      String cText = getText();
      this.prefStore.setValue( this.prefID+LAST_ITEM_PREFIX, cText );
      List< String > itemList = new ArrayList< String >();
      if ( cText.length() > 0 && isValidItem( cText ) ) {
        itemList.add( cText );
      }
      String[] items = getItems();
      for( String item : items ) {
        if ( isValidItem( item ) && !itemList.contains( item ) ) {
          itemList.add( item );
        }
      }
      StringBuilder buffer = new StringBuilder();
      for ( String item : itemList ) {
        buffer.append( item+StoredCombo.prefSeparator );
      }
      if ( buffer.length() > 0 ) {
        String s = buffer.substring( 0, buffer.length()-1 );
        this.prefStore.setValue( this.prefID, s );
      }      
    }
  }

  /**
   * Check if the specified item is valid for this <code>StoredCombo</code>.
   * Only if the item is valid it is re-/stored. The default implementation always
   * returns true. Subclasses may override this function.
   * 
   * @param item The item to be tested.
   * @return True if the item is valid and should be re-/stored.
   */
  protected boolean isValidItem( @SuppressWarnings("unused") final String item ) {
    return true;
  }

  /* (non-Javadoc)
   * @see org.eclipse.swt.widgets.Combo#checkSubclass()
   */
  @Override
  protected void checkSubclass() {
    // required to allow subclassing of Combo
  }
  
}
