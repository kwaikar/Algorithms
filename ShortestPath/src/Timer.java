import java.util.ArrayList;
import java.util.List;

/*
 * Implementation of timer and memory calculation
 */
public class Timer {
	
	private static long startTime, endTime, elapsedTime;
	private static int phase = 0;
	public static List<Long> runtime=new ArrayList<Long>();
	public static List<Long> memory=new ArrayList<Long>();
	public static int timeCounter=0;
	public static int memoryCounter=0;
	
	/*
	 * The Timer method is used to calculate the elapsed Time. 
	 * Here, we are performing 3 iterations of sort for every Data type(Integer,Float,etc). 
	 * Hence we set a counter and reset the runtime and memory lists after the 3rd data
	 * as we re use the same List for every Datatype 
	 */
	 public static void timer()
	    {
	        if(phase == 0) {
		    startTime = System.currentTimeMillis();
		    phase = 1;
		} else {
		    endTime = System.currentTimeMillis();
	            elapsedTime = endTime-startTime;
	            if(timeCounter<3){
	            runtime.add(elapsedTime);
	            timeCounter++;
	            }else{
	            	runtime.clear();
	            	timeCounter=0;
	            }
	            System.out.println("Time: " + elapsedTime + " msec.");
	            memory();
	            phase = 0;
	        }
	    }
	   

	    public static void memory() {
	        long memAvailable = Runtime.getRuntime().totalMemory();
	        long memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	        if(memoryCounter<3){
	            memory.add(memUsed/1000000);
	            memoryCounter++;
	            }else{
	            	memory.clear();;
	            	memoryCounter=0;
	            }
	        System.out.println("Memory: " + memUsed/1000000 + " MB / " + memAvailable/1000000 + " MB.");
	    
}

}
