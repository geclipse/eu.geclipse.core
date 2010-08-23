package eu.geclipse.traceview;


public interface IStatisticsTrace {
  
  public double getPercentageOfTimeSpentOnCommunication();
  public double getPercentageOfTimeSpentOnCalculation();
  public double getTimeSpentOnCommunication();
  public double getTimeSpentOnCalculation();
  
}
