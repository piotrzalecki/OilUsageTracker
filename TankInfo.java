
public class TankInfo {


	// Class created as pattern for information about oil tank


	private  double height;
	private double capacity;
	private double currentOilAmount;
	
	public TankInfo(double height, double capacity,  double currentOilAmount) {
		super();
		this.height = height;
		this.capacity = capacity;
		this.currentOilAmount = currentOilAmount;
		
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	
	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
		
	
	public double getCurrentOilAmount() {
		return currentOilAmount;
	}

	public void setCurrentOilAmount(double currentOilAmount) {
		this.currentOilAmount = currentOilAmount;
	}



	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder("");
		sb.append("\tTank info: \n");
		sb.append("\tThe height of tank is: ");
		sb.append("\t" + this.getHeight()).append("\n");
		sb.append("\tThe capacity is: ");
		sb.append("\t" + this.getCapacity()).append("\n");
		sb.append("\tCurrent amount of oil is: ");
		sb.append("\t" + this.getCurrentOilAmount()).append("\n");
		
		return sb.toString();
	}
	
	
	

}
