import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;


//	Program: Oil usage tracker
//	Version: 0.5
//	Author: Piotr Załęcki
//	Description: Very first version of program for tracking usage of oil in tank
//				in oil central heating.


public class OilUsageTracker {

//	Main program's class. Created as controller based on MVC model,
// where view is Menu class and model is DataSource class

	public static void main(String[] args) {
		
		int choice;
		boolean exit = false;

//		While loop created to display menu
		while(!exit) {
						
		try {
			choice = Menu.mainMenu();
			switch(choice) {
			
			case 1:
				showTankInfo();
			
				break;
			case 2:
				showOilInTank();
				break;
			case 3:
				showMeasures();
				break;
			case 4:
				showAvarageUsage();
				break;
			case 5:
				predictEndOfOil();
				break;
				
			case 6:
				try {
					addMeasure(Menu.addMeasureMenu());
				} catch (InputMismatchException e ) {
					System.out.println("\tPlease insert value following the provided pattern!!\n"
							+ "\tTry again. Thank you");
				} catch (DateTimeParseException e) {
					System.out.println("\tWrong date format or date is incorrect.\n\tfollow the provided pattern!!");
				}  catch (ParseException e) {
					System.out.println("\tWrong date format or date is incorrect.\n\tfollow the provided pattern!!");
				} catch (NullPointerException e){
					System.out.println("\tDay or mnth out of band!");
				}
				
								
				break;
			case 7:
				try {
					refillTank(Menu.addMeasureMenu());
				} catch (InputMismatchException e ) {
					System.out.println("\tPlease insert value following the provided pattern!!\n"
							+ "\tTry again. Thank you");
				} catch (DateTimeParseException e) {
					System.out.println("\tWrong date format.\n\tfollowing the provided pattern!!");
				}  catch (ParseException e) {
					System.out.println("\tWrong date format.\n\tfollowing the provided pattern!!");
				} 				
				break;
			case 8:
				try {
					upgradeTank(Menu.upgradeTankMenu());
				} catch (InputMismatchException e ) {
					System.out.println("\tPlease insert value following the provided pattern!!\n"
							+ "\tTry again. Thank you");
				}
								
				break;
			case 0:
				System.out.println("\tExiting...");
				exit = true;
							}			
		} catch (InputMismatchException e) {
			System.out.println("\tYou inserted wrong value.\n\tPut value within range 0-9");
		}
		pause();
			
		}
	}

	
	
	public static void showMeasures() {
		
		StringBuilder sb = new StringBuilder("\n\tMeasures:\n");
		
		for(Measure measure : DataSource.getInstance().getMeasures()) {
			sb.append("\tOn ");
			sb.append(measure.getDateOfMeasure());
			sb.append("\tthere was ");
			sb.append(measure.getResult());
			sb.append("\tcm of oil.");
			System.out.println(sb.toString());
			sb.setLength(0);
		}	
		
	}
	
	public static void addMeasure(Measure measure ) {
		DataSource.getInstance().addMeasure(measure);
	}
	
	
	public static void refillTank(Measure measure) {
		double newOilAmmount = DataSource.getInstance().getTankInfo().getCurrentOilAmount() + measure.getResult();
		addMeasure(new Measure(measure.getResult(), measure.getDateOfMeasure()));
		DataSource.getInstance().updateTankInfo(newOilAmmount);
				
	}
	
	public static void showOilInTank() {
		
		
		TankInfo tank = DataSource.getInstance().getTankInfo();
		
		StringBuilder sb = new StringBuilder("\n\tTank info:\n");
		sb.append("\tThere is ");
		sb.append(tank.getCurrentOilAmount());
		sb.append(" cm of oil in tank.\t");
		
		System.out.println(sb.toString());
		
	}
	
	public static void showTankInfo() {
		System.out.println(DataSource.getInstance().getTankInfo().toString());
	}
	
	private static double[] countAverageUsage() {
		double[] results= new double [2];
		double previouse;
		double sum = 0;
				
		
		previouse = DataSource.getInstance().getMeasures().get(0).getResult();
		
		for(Measure measure : DataSource.getInstance().getMeasures()) {
			if(measure.getResult() < previouse) {
				double result = previouse - measure.getResult();
				sum += result; 
			}	
			previouse = measure.getResult();
		}
				
		List<Measure> measures = new ArrayList<Measure>(DataSource.getInstance().getMeasures());
		Collections.sort(measures);
		String dateFromStr = measures.get(0).getDateOfMeasure();
		String dateToStr = measures.get(measures.size()-1).getDateOfMeasure();
		Date dateFrom =  parseDate(dateFromStr);
		Date dateTo = parseDate(dateToStr);
		long diff = dateTo.getTime() - dateFrom.getTime();
		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		
		results[0]= sum/diffDays;
		results[1]= diffDays;
				
		return results;

	}
	
	public static void showAvarageUsage() {
		
		StringBuilder sb = new StringBuilder("\nAverage usage:\n");
		sb.append("\tAverage usage within last ");
		sb.append(countAverageUsage()[1]);
		sb.append(" days was ");
		String s = String.format("%.2f", countAverageUsage()[0]);
		sb.append(s);
		sb.append(" of oil per day.\n ");
		
		System.out.println(sb.toString());
	}
	
	public static void predictEndOfOil() {
		
		List<Measure> measures = new ArrayList<Measure>(DataSource.getInstance().getMeasures());
		String dateToStr = measures.get(measures.size()-1).getDateOfMeasure();
		Date startDateToSet = parseDate(dateToStr);
		LocalDate startDate = startDateToSet.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		double currentOil = DataSource.getInstance().getTankInfo().getCurrentOilAmount();
		double currentUsage = 	countAverageUsage()[0];
				
		do {
			startDate= startDate.plusDays(1);
			currentOil -= currentUsage;
						
		} while(currentOil >= 0.0);		
		System.out.println("\tOil will end on: " + startDate.plusDays(-1).toString());
		
	}
	
	public static void upgradeTank(TankInfo tankInfo) {
		DataSource.getInstance().updateTankInfo(tankInfo);
	}
	
	
	 public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd/MM/yyyy").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	 
	public static void pause() {
		
		try {
			Thread.sleep(1000);
		}catch (InterruptedException e) {
			
		}
		
		
	}
	
	
}
