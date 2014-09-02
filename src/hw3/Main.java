package hw3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Nathaniel Chan
 *
 * Main class to be run for collecting and analyzing data from the
 * CIA World Factbook. Prompts user to select analysis method
 */
public class Main extends Analysis {

	private static ArrayList<Country> allCountries;
	public final static String FACTBOOK_URL = 
			"https://www.cia.gov/library/publications"
					+ "/the-world-factbook/print/textversion.html";

	public static ArrayList<String> continents;
	public static ArrayList<String> hazards;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		//set up possible continents and hazards to be matched
		setUpContinents();
		setUpHazards();

		//go to the mainpage of the CIA WorldFactbook and get the URLs
		//for all the countries
		MainPage homepage = new MainPage(FACTBOOK_URL);
		System.out.println("Collecting country URLs...");
		allCountries = homepage.getCountryURLs();

		//parse the data for each country
		System.out.println("Collecting data for countries...");
		for (Country country: allCountries) {
			ParseCountry current = new ParseCountry(country);
			current.parse();
			System.out.println();
		}

		while (true) {
			//ask user which method to run
			System.out.println();
			System.out.println("Which would you like to run?");
			System.out.println("A. Countries in continent "
					+ "with a natural hazard");
			System.out.println("B. Lowest elevation in continent");
			System.out.println("C. Countries in quarter hemisphere");
			System.out.println("D. Countries in continent with at least "
					+ "n political parties");
			System.out.println("E. Top n countries with highest electricity "
					+ "consumption per capita");
			System.out.println("F. Countries with dominant religion accounting"
					+ " for \n less/more than n% of the population");
			System.out.println("G. Landlocked countries "
					+ "with n bordering countries");
			System.out.println("H. Top n countries with highest miltary "
					+ "expenditure per capita");
			System.out.println("I. Top n countries with the highest ratio"
					+ " of population to \n number of airports");
			System.out.println("J. Find 10 x 10 box with most capital cities");
			System.out.println("\n(--For all queries, "
					+ "separate inputs with the return button--)");
			System.out.println();

			//match the user's answer with the functions to be run
			String answer = in.nextLine();

			if (answer.equals("a") || answer.equals("A")) {
				System.out.println("Please enter (1) continent, (2) hazard");
				String continent = "";
				String hazard = "";
				if (in.hasNextLine()) {
					continent = in.nextLine();
					hazard = in.nextLine();
				}

				//select countries in the continent
				ArrayList<Country> filtered = 
						filterByContinent(allCountries, continent);

				//select countries with the specified hazard, and print
				//the names of selected countries
				System.out.println("\nAnswer:");
				for (Country x : filterByHazard(filtered, hazard))
					x.print();
			}

			else if (answer.equals("b") || answer.equals("B")) {
				System.out.println("Please enter (1) continent");
				String continent = "";
				if (in.hasNextLine()) {
					continent = in.nextLine();
				}
				//select countries in the continent
				ArrayList<Country> filtered = 
						filterByContinent(allCountries, continent);

					System.out.println("\nAnswer:");
					//select the country with lowest elevation
					filterByLowestElevation(filtered).print();
			}

			else if (answer.equals("c") || answer.equals("C")) {
				System.out.println("Please enter (1) N/S (2) E/W");
				String ns = in.nextLine();
				String ew = in.nextLine();

				//select countries in the specified hemisphere
				System.out.println("\nAnswer:");
				for (Country x: filterByHemisphere(allCountries, ns, ew))
					x.print();
			}

			else if (answer.equals("d") || answer.equals("D")) {
				System.out.println("Please enter (1) continent "
						+ "(2) number of political parties");
				String continent = "";
				int num = 0;
				try {
					if (in.hasNextLine()) {
						continent = in.nextLine();
						num = Integer.parseInt(in.nextLine());
					}
					//select countries in the continent
					ArrayList<Country> inContinent = 
							filterByContinent(allCountries, continent);

					//select countries with the minimum number of political parties
					System.out.println("\nAnswer:");
					for(Country x: filterByMinPartyNumber(
							inContinent, num))
						x.print();
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("e") || answer.equals("E")) {
				System.out.println("Please enter (1) the top n "
						+ "number of countries");
				try {
					int num = Integer.parseInt(in.nextLine());


					//sort countries by electricity consumption,
					//then print the names of each country along with
					//its corresponding data
					System.out.println("\nAnswer:");
					for (Country x: 
						sortCountriesByElecConsumption(allCountries, num))
						System.out.println(x.getName() + " : " 
								+ x.getElecPerCapita() + " kWh");
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("f") || answer.equals("F")) {
				System.out.println("Please enter (1) the percentage n in n%");
				try {
					double percent = 0.0;
					String moreLess = "";
					if (in.hasNextLine()) {
						percent = Double.parseDouble(in.nextLine());

						System.out.println("Please enter if a country should "
								+ "have more (>) or less (<) than this "
								+ "percentage");
						moreLess = in.nextLine();
					}
					boolean moreOrLess = false;
					if (moreLess.contains(">") || moreLess.contains("more")) {
						moreOrLess = true;
					}

					//print the countries that match the requirement
					System.out.println("\nAnswer:");
					for (Country x: filterByDominantReligion(
							allCountries, percent, moreOrLess)) {
						System.out.println(x.getName() + ": " 
								+ x.getDomReligionName());
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("g") || answer.equals("G")) {
				System.out.println("Please enter (1) the number of bordering"
						+ " countries for a landlocked country");
				try {
				int borderNumber = Integer.parseInt(in.nextLine());

				//select all landlocked countries
				ArrayList<Country> landlocked = 
						filterByLandlocked(allCountries);

				//select and print countries with the specified number
				//of surrounding countries
				System.out.println("\nAnswer:");
				for (Country x: filterByBorderNumber(
						landlocked, borderNumber))
					x.print();
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("h") || answer.equals("H")) {
				System.out.println("Please enter (1) the top n "
						+ "number of countries");
				try {
					int num = Integer.parseInt(in.nextLine());

					//sort and print the countries with the top
					//military expenditure per capita
					System.out.println("\nAnswer:");
					for (Country x: 
						sortCountriesByMilSpending(allCountries, num))
						System.out.println(x.getName() + " : $" 
								+ x.getMilSpendingPerCapita());
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("i") || answer.equals("I")) {
				System.out.println("Please enter (1) the top n "
						+ "number of countries");
				try {
					int num = Integer.parseInt(in.nextLine());

					//sort and print countries with the top ratio of
					//number of airports to GDP
					System.out.println("\nAnswer:");
					for (Country x: sortCountriesByPopulationAirportRatio(
							allCountries, num))
						System.out.println(x.getName());
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid input");
				}
			}

			else if (answer.equals("j") || answer.equals("J")) {
				CapitalAnalysis capitals = new CapitalAnalysis(allCountries);
				Country max = capitals.maxAround(10);
				int xMin = max.getCapitalX() - 5;
				int xMax = max.getCapitalX() + 5;
				int yMin = max.getCapitalY() - 5;
				int yMax = max.getCapitalY() + 5;
				String corner = "(" + xMin + ", " + yMin + ")";
				String corner1 = "(" + xMax + ", " + yMax + ")";
				System.out.println("If (0,0) is defined as the coordinate"
						+ " 90 N, 180 W, \n then the box is between the "
						+ "coordinates \n" + corner + "\n" + corner1);
				System.out.println("Countries within this box:");
				for (Country x: capitals.checkAround(max, 10)) {
					x.print();
				}
			}
			else
				System.out.println("Invalid input");
		}
	}

	/**
	 * initializes arraylist of hazards to be matched with
	 */
	private static void setUpHazards() {
		hazards = new ArrayList<String>();
		hazards.add("earthquake");
		hazards.add("cyclonic storm");
		hazards.add("volcanism");
		hazards.add("hurricanes");
		hazards.add("drought");
		hazards.add("forest fire");
		hazards.add("typhoon");
	}

	/**
	 * initializes arraylist of continents to be matched with
	 */
	private static void setUpContinents() {
		continents = new ArrayList<String>();
		continents.add("Asia");
		continents.add("North America");
		continents.add("South America");
		continents.add("Europe");
		continents.add("Africa:");
		continents.add("Australia-Oceania");
		continents.add("Antarctica");
	}

}
