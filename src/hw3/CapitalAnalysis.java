package hw3;

import java.util.ArrayList;

/**
 * @author Nathaniel Chan
 * Finds the maximum number of capitals within a defined coordinate box size
 */
public class CapitalAnalysis {

	ArrayList<Country> allCountries;
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;

	/**
	 * @param countries
	 * Constructor to take in the countries to analyze
	 */
	public CapitalAnalysis(ArrayList<Country> countries) {
		allCountries = countries;
	}
	
	/**
	 * @param size: length of side of the box to be searched
	 * @return country that has the maximum number of capitals around
	 * 			its own capital, within the box size
	 */
	public Country maxAround(int size) {
		int max = -1;
		Country maxCountry = null;
		
		//check all of the countries
		for (Country x: allCountries) {
			int around = checkAround(x, size).size();
			if (around > max) {
				max = around;
				maxCountry = x;
			}
		}
		//return the country with the maximum number of capitals within
		//the box
		return maxCountry;
	}

	/**
	 * @param country: country to find how many capitals are around
	 * @param size: side length of the box to be measured
	 * @return the number of capitals within the box, centered at the
	 * 			country's capital
	 */
	public ArrayList<Country> checkAround(Country country, int size) {
		ArrayList<Country> within = new ArrayList<Country>();
		int xCoor = country.getCapitalX();
		int yCoor = country.getCapitalY();
		
		//if there is a valid coordinate for the coutnry capital
		if ((xCoor != 0) && (yCoor != 0)) {
			
			//define the coordinates of the box edges
			xMin = xCoor - (size / 2);
			xMax = xCoor + (size / 2);
			yMin = yCoor - (size / 2);
			yMax = yCoor + (size / 2);
			
			//if the capital of another country is within the box,
			//increase the counter
			for (Country check: allCountries) {
				int checkX = check.getCapitalX();
				int checkY = check.getCapitalY();
				if ((checkX <= xMax) && (checkX >= xMin)
						&& (checkY >= yMin) && (checkY <= yMax)) {
					within.add(check);
				}
			}
		}

		return within;
	}

}

