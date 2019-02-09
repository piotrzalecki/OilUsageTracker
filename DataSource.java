import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataSource {

	//Based on singleton pattern class for reading and writing in to the file

	
	private TankInfo tankInfo = new TankInfo(0,0,0);
	private List<Measure> measures = new ArrayList<>();

	//Constants of path of files storing data
	private static final String MEASURES_FILE_PATH = "./measures.txt";
	private static final String TANKINFO_FILE_PATH = "./tankinfo.txt";

    //Singleton implementation: creating empty constructor
	private DataSource(){}
    //Singleton implementation: self instantiating object
    // with "static" and "final" what enable to make other instances base on DataSource class
    public static final DataSource instance = new DataSource();
	

	//Initialization code to read data from files and set values to variables
	{
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(MEASURES_FILE_PATH))) {  
		    Measure measure;
			String[] output;
			double measureResult;
			String dateOfMeasure;
			String line = bufferedReader.readLine();
			
			if(!line.isEmpty()) {
				 while(line != null) {
				       output = line.split("\t");
				      try {
				    	  measureResult = Double.parseDouble(output[0]);
				    	  dateOfMeasure = output[1];
					       measure = new Measure(measureResult,dateOfMeasure);
					       this.measures.add(measure);
					       line = bufferedReader.readLine();
				      } catch(NumberFormatException e) {
				    	  System.out.println("\tYour measures.txt file is broken!!\n "
				    	  		+ "\tIt can affect program functionality");
				    	   break;				    	 			    	  
				      }
				       
				      
				    }
			} else {
				System.out.println("\tYour measures.txt file is broken!!\n "
		    	  		+ "\tIt can affect program functionality\n");
			}
			
		   
		} catch (FileNotFoundException e) {
		    System.out.println("\tYour measures.txt file doesn't exist\n" +
		    		"\tType 6 to add measure (file will be created automatically.\n");
		} catch (IOException e) {
			System.out.println("IO exception: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Your measures.txt file is empty.\n"
					+ "\tIt can affect program functionality \n"
					+ "\tType 6 to add measure.\n");
		}
		
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(TANKINFO_FILE_PATH))) {  
		    
			String[] output;
			double height;
			double capacity;
			double currentOilAmmount;
			
		    String line = bufferedReader.readLine();
		    	if(!line.isEmpty()) {
		    		try {
		    			output = line.split("\t");
					    height = Double.parseDouble(output[0]);
					    capacity = Double.parseDouble(output[1]);
					    currentOilAmmount = Double.parseDouble(output[2]);
					    this.tankInfo.setHeight(height); 
					    this.tankInfo.setCapacity(capacity);
					    this.tankInfo.setCurrentOilAmount(currentOilAmmount);
		    		} catch(NumberFormatException e) {
				    	  System.out.println("\tYour tankinfo.txt file is broken!!\n "
					    	  		+ "\tIt can affect program functionality");
					   	  			    	 			    	  
					 } catch(ArrayIndexOutOfBoundsException e) {
				    	  System.out.println("\tYour tankinfo.txt file is broken!!\n "
					    	  		+ "\tIt can affect program functionality");
					   	  			    	 			    	  
					 } 
		    		
		    	} else {
		    		
		    		System.out.println("\tYoure tankinfo.txt file is broken!!\n "
		    	
		    	  		+ "\tIt can affect program functionality");
		    	}
		    
		} catch (FileNotFoundException e) {
		    System.out.println("\tYour tankinfo.txt file doesn't exist\n"
		    		+ "\tType 8 to set your tank (file will be created automatically.");
		} catch (IOException e) {
			System.out.println("\tIO exception: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("\tYour tankinfo.txt file is empty.\n"
					+ "\tType 8 to set your tank!");
		}
			
	}		
	
	
	public static DataSource getInstance(){
	       return instance;
	}


	public TankInfo getTankInfo() {
		return tankInfo;
	}


	public List<Measure> getMeasures() {
		return measures;
	}
	

	public void addMeasure(Measure measure) {
		
		StringBuilder sb = new StringBuilder("");
		sb.append(measure.getResult());
		sb.append("\t");
		sb.append(measure.getDateOfMeasure());
		String inputString = sb.toString();
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(MEASURES_FILE_PATH, true))) { 
			bufferedWriter.write(inputString);
			bufferedWriter.newLine();
	} catch (IOException e) {
	   e.getMessage();
	}		
		measures.add(measure);
		updateTankInfo(measure.getResult());
		
		
	}

	//Update all information about tank
	public void updateTankInfo(TankInfo tankInfo) {
		StringBuilder sb = new StringBuilder("");
		sb.append(tankInfo.getHeight());
		sb.append("\t");
		sb.append(tankInfo.getCapacity());
		sb.append("\t");
		sb.append(tankInfo.getCurrentOilAmount());
		String inputString = sb.toString();
		
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TANKINFO_FILE_PATH))) { 
			bufferedWriter.write(inputString);
		} catch (IOException e) {
	   e.getMessage();
	}		
		this.tankInfo = tankInfo;
		
	}

	//Update only amount of oil in tank
	public void updateTankInfo(double amount) {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.tankInfo.getHeight());
		sb.append("\t");
		sb.append(this.tankInfo.getCapacity());
		sb.append("\t");
		sb.append(amount);
		String inputString = sb.toString();
	
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TANKINFO_FILE_PATH))) { 
			bufferedWriter.write(inputString);
		} catch (IOException e) {
	    e.getMessage();
	    }
		this.tankInfo.setCurrentOilAmount(amount);
		
	}
}
	
	
	 
