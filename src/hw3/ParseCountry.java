package hw3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author Nathaniel Chan 
 * Class to pull relevant data about a country from it's site
 */
//TODO correct North America answer for lowest elevation: NPE thrown
public class ParseCountry {
	private Country country;
	private Document doc;
	private Map<String, Long> numberMap;

	/**
	 * @param country
	 * constructor for ParseCountry, establishes an HTTP connection to the
	 * country's site and uses jsoup to pull a document from the HTML data
	 */
	public ParseCountry(Country country) {
		this.country = country;
		System.out.println("************************************************");
		System.out.println("Country: " + country.getName());
		try {
			String url = Main.FACTBOOK_URL;
			url = url.substring(0, 59);
			url += country.getURL();
			System.out.println("URL: " + url);
			doc = Jsoup.connect(url).get();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//method to call methods to collect relevant data about a country
	public Country parse() {
		parseContinent();
		parseHazards();
		parseElevation();
		parseCoordinates();
		parsePoliticalParties();
		parsePopulation();

		createNumberMap();
		parseElectricity();
		country.calculateElecPerCapita();

		parseReligion();
		parseLandlocked();
		parseBorderCountries();
		parseMilitarySpending();
		parseGDP();
		country.calculateAbsoluteMilitarySpending();
		country.calcMilitarySpendingPerCapita();

		parseAirport();
		country.calculatePopulationAirportRatio();

		parseCapitalCoor();

		return country;
	}

	private void createNumberMap() {
		numberMap = new HashMap<String, Long>();
		numberMap.put("million", 1000000L);
		numberMap.put("billion", 1000000000L);
		numberMap.put("trillion", 1000000000000L);
	}

	/** finds which continent the country belongs to and stores it in
	 * country object
	 */
	public void parseContinent() {
		Elements regionElements = doc.select("div.region1");
		for (Element element: regionElements) {
			String text = element.text();

			//cycles through the array of continents and checks to see if
			//country belongs to a continent, then assigns the continent
			//to the Country object
			for (String continent: Main.continents) {
				if (text.contains(continent)) {
					country.setContinent(continent);
					System.out.println("Continent: " + continent);
				}
			}
		}
	}

	/** finds hazards for the country and stores it in country object*/
	public void parseHazards() {
		Element countryHazard = 
				doc.getElementsContainingText("Natural hazards").first();
		if (countryHazard != null) {
			String text = countryHazard.text();
			for (String hazard: Main.hazards) {
				if (text.contains(hazard)) {
					country.addHazard(hazard);
				}
			}
			System.out.print("Hazards:");
			for (String x: country.getHazards()) {
				System.out.print(" " + x);
			}
			System.out.println();
		}
	}

	/** finds the lowest elevation for a country and stores it in
	 * country object
	 */
	public void parseElevation() {
		Element lowest = 
				doc.getElementsContainingOwnText("lowest point").first();
		if (lowest != null) {
			String text = lowest.text();
			if (text.contains("lowest point")) {
				//separate all text into array of words
				String[] splited = text.split("\\s+");
				int elevation = 0;
				for (String x: splited) {
					//
					try {
						elevation = Integer.parseInt(x);
					}
					catch (NumberFormatException e) {
					}
				}
				System.out.println("Elevation: " + elevation);

				country.setLowestElevation(elevation);
			}
		}
	}

	/**
	 * finds the coordinates for a country and stores it in country object
	 */
	public void parseCoordinates() {
		Elements coor = 
				doc.getElementsContainingOwnText("Geographic coordinates");
		if (coor != null) {
			String text = coor.text();
			text = text.replace("Geographic coordinates: ", "");
			text = text.replace("geographic coordinates: ", "");
			text = text.replace("geographic coordinates define outer limit ", "");
			//split into N/S and E/W coordinates
			String[] coors2D = text.split(",");
			String[] latArray = null;
			String[] longArray = null;

			//if such coordinates exist, split the set of coordinates
			//into the number and the direction, e.g. 10 20 S into an
			//array {"10", "20", "S"}
			if (coors2D.length == 2) {
				for (int i = 0; i < 2; i++) {
					if (i == 0) latArray = coors2D[i].split(" ");
					if (i == 1) longArray = coors2D[i].split(" ");
				}

				//parse each coordinate
				int lat1 = 0;
				int lat2 = 0;
				int long1 = 0;
				int long2 = 0;
				String finalLat = null;
				String finalLong = null;
				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						lat1 = Integer.parseInt(latArray[i]);
						long1 = Integer.parseInt(longArray[i + 1]);
					}
					if (i == 1) {
						lat2 = Integer.parseInt(latArray[i]);
						long2 = Integer.parseInt(longArray[i + 1]);
					}
					if (i == 2) {
						finalLat = latArray[i];
						finalLong = longArray[i + 1];
					}
				}

				//print and store coordinates into country
				System.out.println("Geographic Coordinates:");
				System.out.println(lat1 + " " + lat2 + " " + finalLat);
				System.out.println(long1 + " " + long2+ " " + finalLong);
				country.setCoordinates(lat1, lat2, long1, long2);
				country.setHemisphere(finalLat, finalLong);
			}
		}
	}

	/**
	 * parse the number of political parties in the country
	 */
	private void parsePoliticalParties() {
		Elements data = new Elements();
		Elements parties = doc.select("div.category, div.category_data");
		boolean check = false;
		//iterate through all the category and category_data and select
		//the relevant lines
		for (Element x: parties) {
			if (x.text().contains("Political pressure groups and leaders:"))
				check = false;
			if (check) {
				data.add(x);
			}
			if (x.text().contains("Political parties and leaders"))
				check = true;
		}
		//select only the category_data, since we do not need
		//the category titles
		data = data.select("div.category_data");

		//select only the relevant political parties, column_data
		//like "other political parties" will be removed
		Elements finalData = new Elements();
		for (Element x: data) {
			if (x.text().contains("[") && x.text().contains("]")) {
				finalData.add(x);
			}
		}
		country.setNumParties(finalData.size());
		System.out.println("Number of political parties: " 
				+ country.getNumParties());
	}

	/**
	 * parse the population number for the country
	 */
	private void parsePopulation() {
		Element populationData = null;
		Elements categoryData = doc.select("div.category, div.category_data");

		//iterate through elements until the "Population:" title appears
		//then select the line after that which contains the population data
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				populationData = x;
				break;
			}
			if (x.text().contains("Population:")) {
				check = true;
			}
		}
		//clean up data to isolate population value
		if (populationData != null) {
			String[] splitData = populationData.text().split(" ");
			String replaced = splitData[0].replace(",", "");

			try {
				int population = Integer.parseInt(replaced);
				country.setPopulation(population); //set country population
			}
			catch (NumberFormatException e) {
				for (String x: splitData) {
					try {
						x = x.replace(",", "");
						country.setPopulation(Integer.parseInt(x));
					}
					catch (NumberFormatException e2) {
					}
				}
			}
			System.out.println("Population: " + country.getPopulation());
		}
		else System.out.println("Population: N/A");
	}

	/**
	 * get the electricity consumption of the country
	 */
	private void parseElectricity() {
		Element elecData = null;
		Elements categoryData = doc.select("div.category, div.category_data");

		//iterate through elements until the the relevant line,
		//then select line that contains the electricity data
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				elecData = x;
				break;
			}
			if (x.text().contains("Electricity - consumption:"))
				check = true;
		}
		//clean up data to isolate electricity consumption value
		if (elecData != null) {
			String[] splitData = elecData.text().split(" ");

			//convert xx billion/trillion into a Long type
			String magnitude = splitData[1];
			double elec = 0.0;
			if (magnitude.equals("million") || magnitude.equals("billion")
					|| magnitude.equals("trillion")) {
				long multiplier = numberMap.get(splitData[1]);
				elec = Double.parseDouble(splitData[0].replace(",", "")) * multiplier;
			}
			else elec = Double.parseDouble(splitData[0].replace(",", ""));
			country.setElecConsumption((long) elec); //save value to country
			System.out.println("Electricity consumption: " 
					+ country.getElecConsumption());
		}
		else System.out.println("Electricity consumption: N/A");
	}

	/**
	 * get the percentage of the dominant religion of the country
	 */
	private void parseReligion() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element religionData = null;
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				religionData = x;
				break;
			}
			if (x.text().contains("Religions:"))
				check = true;
		}
		if (religionData != null) {
			String[] splitData = religionData.text().split(",");

			System.out.println(religionData.text());
			//let us only consider the dominant religion
			String first = splitData[0];
			try {
				double percentage = 0.0;

				//if a range of percentage given, then take the average
				if (first.contains("-")) {
					String[] splitNumbers = first.split("-");
					String number1 = 
							splitNumbers[0].replaceAll("[^\\d.]", "");
					String number2 = splitNumbers[1].replaceAll(
							"[^\\d.]", "");
					percentage = (Double.parseDouble(number1)
							+ Double.parseDouble(number2)) / 2;
				}
				else {
					String number = first.replaceAll("[^\\d.]", "");
					percentage = Double.parseDouble(number);
				}
				country.setDomReligion(percentage, first);
				System.out.println("Dominant religion: " 
						+ country.getDomReligionName() + 
						" " + country.getDomReligionPercent());
			}
			catch (NumberFormatException e) {
				System.out.println("Dominant religion percentage: N/A");
			}
		}
		else System.out.println("Dominant religion percentage: N/A");
	}

	/**
	 * check to see if a country is landlocked, then store the data
	 * to the country object
	 */
	private void parseLandlocked() {
		Elements categoryData = doc.select("div.category, div.category_data");
		boolean check = false;
		boolean landlocked = false;
		for (Element x: categoryData) {
			if (check) {
				landlocked = x.text().contains("landlocked");
				break;
			}
			if (x.text().contains("Coastline:"))
				check = true;
		}
		country.setLandlocked(landlocked);
		System.out.println("Landlocked: " + landlocked);
	}

	/**
	 * finds the number of bordering countries that the country has
	 */
	private void parseBorderCountries() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element borderElement = null;
		for (Element x: categoryData) {
			if (x.text().contains("border countries:")) {
				borderElement = x;
				break;
			}
		}
		if (borderElement != null) {
			//since each country is separated by a comma, the number of
			//bordering countries would be the array length after the
			//String is split by the commas
			String[] borderCountries = borderElement.text().split(",");
			int borderNumber = borderCountries.length;
			country.setBorderCountryNumber(borderNumber);
			System.out.println("Border countries number: " 
					+ country.getBorderCountries());
		}
		else System.out.println("Border countries number: N/A");
	}

	/**
	 * finds the military expenditure of the country and stores it
	 * in the country data
	 */
	private void parseMilitarySpending() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element militaryElement = null;
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				militaryElement = x;
				break;
			}
			if (x.text().contains("Military expenditures:")) {
				check = true;
			}
		}
		if (militaryElement != null) {
			String[] split = militaryElement.text().split(" ");
			try {
				double percent = Double.parseDouble(split[0].replace("%", ""));
				country.setMilitarySpending(percent / 100);
				System.out.println("Military expenditure: " + 
						country.getMiltarySpending() * 100 + "% of GDP");
			}
			catch (NumberFormatException e) {
				System.out.println("Military expenditure: N/A");
			}
		}
		else 
			System.out.println("Military expenditure: N/A");
	}

	/**
	 * finds the Gross Domestic Product (GDP) of a country and
	 * stores it in the country data
	 */
	private void parseGDP() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element gdpElement = null;
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				gdpElement = x;
				break;
			}
			if (x.text().contains("GDP (purchasing power parity)"))
				check = true;
		}
		if (gdpElement != null) {
			String[] split = gdpElement.text().split(" ");
			try {
				double gdpNumber = Double.parseDouble(split[0].replace("$", ""));
				String magnitude = split[1];
				long multiplier = 0;
				if (magnitude.equals("million") || magnitude.equals("billion")
						|| magnitude.equals("trillion")) {
					multiplier = numberMap.get(split[1]);
				}
				long gdp = (long) (gdpNumber * multiplier);
				country.setGDP(gdp);
				System.out.println("GDP: " + country.getGDP());
			}
			catch (NumberFormatException e){
				System.out.println("GDP: N/A");
			}
		}
		else
			System.out.println("GDP: N/A");
	}

	/**
	 * finds the number of airports in the country and stores it in
	 * the country data
	 */
	private void parseAirport() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element airportElement = null;
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				airportElement = x;
				break;
			}
			if (x.text().contains("Airports:"))
				check = true;
		}
		if (airportElement != null) {
			String[] split = airportElement.text().split(" ");
			String number = split[0].replaceAll("[^\\d]", "");
			try {
				int airportNumber = Integer.parseInt(number);
				country.setAirportNumber(airportNumber);
				System.out.println("Number of airports: " 
						+ country.getAirportNumber());
			}
			catch (NumberFormatException e) {
				System.out.println("Number of airports: N/A");
			}
		}
		else
			System.out.println("Number of airports: N/A");
	}

	/**
	 * find the coordinates of the capital of the country, and store it
	 * in the country object
	 */
	private void parseCapitalCoor() {
		Elements categoryData = doc.select("div.category, div.category_data");
		Element capitalData = null;
		boolean check = false;
		for (Element x: categoryData) {
			if (check) {
				capitalData = x;
				break;
			}
			if (x.text().contains("Capital:"))
				check = true;
		}
		if (capitalData != null) {
			String text = capitalData.nextElementSibling().text();
			System.out.println(text);
			text = text.replace("geographic coordinates: ", "");

			String[] coors2D = text.split(",");
			String[] latArray = null;
			String[] longArray = null;

			//if such coordinates exist, split the set of coordinates
			//into the number and the direction, e.g. 10 20 S into an
			//array {"10", "20", "S"}
			if (coors2D.length == 2) {
				for (int i = 0; i < 2; i++) {
					if (i == 0) latArray = coors2D[i].split(" ");
					if (i == 1) longArray = coors2D[i].split(" ");
				}

				//parse the coordinates
				int lat1 = 0;
				int long1 = 0;
				String finalLat = null;
				String finalLong = null;
				try {
					lat1 = Integer.parseInt(latArray[0]);
					long1 = Integer.parseInt(longArray[1]);
					finalLat = latArray[2];
					finalLong = longArray[3];

					int xCoor = 0;
					int yCoor = 0;

					if (finalLat.equals("N")) {
						xCoor = 90 - lat1;
					}
					if (finalLat.equals("S")) {
						xCoor = 90 + lat1;
					}
					if (finalLong.equals("E")) {
						yCoor = 180 + long1;
					}
					if (finalLong.equals("W")) {
						yCoor = 180 - long1;
					}

					//print and store coordinates into country
					country.setCapitalCoor(yCoor, xCoor);
					System.out.println("Capital Coordinates:");
					System.out.println("X: " + yCoor + " Y:" + xCoor);
				}
				catch (NumberFormatException e) {
					System.out.println("Capital coordinates: N/A");
				}
			}
			else
				System.out.println("Capital coordinates: N/A");

		}
	}




}

