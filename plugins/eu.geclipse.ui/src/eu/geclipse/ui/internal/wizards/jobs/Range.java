package eu.geclipse.ui.internal.wizards.jobs;


public class Range{
  private double start;
  private double end;
  
   public Range(double start, double end)throws NumberFormatException{
     if (start >= end){
       throw new NumberFormatException();
     }
    this.start = start;
    this.end = end;
  }

  
  public double getEnd() {
    return end;
  }

  
  public void setEnd( double end ) {
    this.end = end;
  }

  
  public double getStart() {
    return start;
  }

  
  public void setStart( double start ) {
    this.start = start;
  }


  @Override
  public boolean equals( Object obj )
  {
    boolean result = false;
    if (super.equals( obj )){
      result = true;
    } else {
      if (obj instanceof Range){
        Range rangeToCompare = (Range) obj;
        if ( this.start == rangeToCompare.getStart() && this.end == rangeToCompare.getEnd()){
          result = true;
        }
      }
    }
    return result;
  }
  
  
  
}
