package eu.geclipse.core.model;

public interface IGridProject extends IGridContainer {
  
  public static final String DIR_MOUNTS = "Filesystems";

  public static final String DIR_JOBDESCRIPTIONS = "Job Descriptions";
  
  public static final String DIR_JOBS = "Jobs";
  
  public boolean isGridProject();
  
  public boolean isOpen();
  
}
