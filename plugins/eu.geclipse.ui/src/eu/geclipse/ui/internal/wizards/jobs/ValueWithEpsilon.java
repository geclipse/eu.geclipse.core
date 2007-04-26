package eu.geclipse.ui.internal.wizards.jobs;


public class ValueWithEpsilon {
  
  double value;
  double epsilon;
  
  
  public ValueWithEpsilon( final double value, final double epsilon ) {
   this.value = value;
   this.epsilon = epsilon;
  }

  
  public double getEpsilon() {
    return epsilon;
  }

  
  public double getValue() {
    return value;
  }


  
  public void setEpsilon( double epsilon ) {
    this.epsilon = epsilon;
  }


  
  public void setValue( double value ) {
    this.value = value;
  }


  @Override
  public boolean equals( Object obj )
  {
    boolean result = false;
    if (super.equals( obj )){
      result = true;
    } else {
      if (obj instanceof ValueWithEpsilon){
        ValueWithEpsilon val = (ValueWithEpsilon) obj;
        if ( this.value == val.getValue() && this.epsilon == val.getEpsilon() ) {
          result = true;
        }
      }
    }
    return result;
  }
  
  
  
}
