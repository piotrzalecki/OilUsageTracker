import java.util.Date;

public class Measure implements Comparable<Measure> {

	// Class created as pattern for information about measure
	
	
	private double result;
	private String dateOfMeasure;
	
	public Measure(double result, String dateOfMeasure) {
		super();
		this.result = result;
		this.dateOfMeasure = dateOfMeasure;
	}

	public double getResult() {
		return result;
	}


	public String getDateOfMeasure() {
		return dateOfMeasure;
	}


	// Method overridden to enable compare measures in term of date of measure
	@Override
	public int compareTo(Measure passedMeasure) {
		
		String currentMeasureDate = this.getDateOfMeasure();
		String passedMeasureDate = passedMeasure.getDateOfMeasure();
						 
		Date currentDate =  OilUsageTracker.parseDate(currentMeasureDate);
		Date passedDate = OilUsageTracker.parseDate(passedMeasureDate);
		
		if(currentDate.after(passedDate)) {
			return 1;
		} else if (currentDate.before(passedDate)) {
			return -1;
		} else {
			return 0;
		}

	}
	

}
