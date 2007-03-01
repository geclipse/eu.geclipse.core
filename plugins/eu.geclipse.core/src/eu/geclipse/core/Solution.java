package eu.geclipse.core;


public class Solution implements ISolution {
  
  private int id;
  
  private String text;
  
  protected Solution( final int id, final String text ) {
    this.id = id;
    this.text = text;
  }
  
  public int getID() {
    return this.id;
  }

  public String getText() {
    return this.text;
  }

  public boolean isActive() {
    return false;
  }

  public void solve() {
    // empty implementation
  }
  
}
