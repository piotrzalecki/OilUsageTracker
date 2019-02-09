import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;



public  class Menu {

//	Display main menu and two submenus for 'adding measure' and 'upgrade tank'.
//	Two validation methods on the bottom.
	
	public static int mainMenu() throws InputMismatchException {
		
		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder("\n\n");
		sb.append("\t******************").append("\n");
		sb.append("\t      MENU        ").append("\n");
		sb.append("\t******************").append("\n");
		sb.append("\t1- Show information about tank").append("\n");
		sb.append("\t2- Show current oil level").append("\n");
		sb.append("\t3- Show previous measures").append("\n");
		sb.append("\t4- Show average usage").append("\n");
		sb.append("\t5- Show estimated end of oil date").append("\n");
		sb.append("\t6- Add measure").append("\n");
		sb.append("\t7- Refill tank").append("\n");
		sb.append("\t8- Upgrade/set tank info").append("\n");
		sb.append("\t0- Exit").append("\n");

		System.out.println(sb.toString());
		int choice = scanner.nextInt();
		
		if(choice > 9) {
			throw new InputMismatchException();
		}
		
		return choice;
	}
	
	
	public static Measure addMeasureMenu() throws InputMismatchException, DateTimeParseException, ParseException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("\tEnter height of oil leve.\n\t(use digits formated to xx.xx eg. 20.00)");
		
		
		double measure = oilLevelValidation(scanner.nextDouble());
		System.out.println("\tEnter date.\n\t(use format  DD/MM/YYY eg. 29/01/2019");
		String date = scanner.next();		
		dateValidation(date);
		return (new Measure(measure, date));
				
	}


	public static TankInfo upgradeTankMenu() throws InputMismatchException {

		OilUsageTracker.showTankInfo();
		Scanner scanner = new Scanner(System.in);
		System.out.println("\tEnter height of tank.\n\t(use digits formated to xx.xx eg. 20.00)");
		double hight = scanner.nextDouble();
		System.out.println("\tEnter tank's capacity.\n\t(use digits formated to xx.xx eg. 20.00)");
		double capacity = scanner.nextDouble();
		System.out.println("\tEnter current oil level.\n\t(use digits formated to xx.xx eg. 20.00)");
		double oilLevel = oilLevelValidation(scanner.nextDouble());
		
		return (new TankInfo(hight, capacity, oilLevel));
				
	}

	 // Date validation, made to throw exception if date is not valid
	private static void dateValidation(String date) throws DateTimeParseException, ParseException  {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.parse(date);  //It throw exception if date is in wrong format

        String[] dateToCheck = date.split("/");
		int day = Integer.parseInt(dateToCheck[0].trim());
    	int month = Integer.parseInt(dateToCheck[1]);
     	int year = Integer.parseInt(dateToCheck[2]);

        //Set of conditions which set date to wrong format
        //if number representing day number or month number is incorrect
		if(day > 31 || month > 13){
			date = "";
		}

		if(month != 8){
            if(month%2 == 0 && day >= 31 ){
                date = "";
            }
        } else if ( day > 31){
            date = "";
        }

        if(year%4 == 0 && month ==2 && day > 29){
            date = "";
        } else if ( year%4 != 0 && month == 2 && day > 28 ){
            date = "";
        }

		sdf.parse(date); //Data parsed second time to throw exception
                        // if number representing day number or month number is incorrect

	}

	private static double oilLevelValidation(double measure){

	    double tankCapacity = DataSource.getInstance().getTankInfo().getCapacity();
		if(measure > tankCapacity && tankCapacity > 0) {
			System.out.println("\tYou have put oil level over tank capacity.\n"
					+ "\tCurrent oil level will be equal tank capacity");
			return tankCapacity;
			
		} else {
			return measure;
		}
	}
}
