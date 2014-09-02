package hw3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Nathaniel Chan
 *
 * Contains methods that can be run to analyze relevant data of countries.
 * 
 */
public class Analysis {

	/**
	 * @param continent: to filter countries with
	 * @return ArrayList of the countries that pass the filter
	 * finds the countries that are in the specified continent
	 */
	public static ArrayList<Country> filterByContinent(ArrayList<Country> countries,
			String continent) {
		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			String countryContinent = x.getContinent();
			if (countryContinent != null) {
				if (continent.equals(countryContinent))
					sorted.add(x);
			}
		}
		return sorted;
	}

	/**
	 * @param hazard: to filter countries with
	 * @return ArrayList of countries that pass the filter
	 * finds the countries that have the specified hazard as one of
	 * its probable hazards
	 */
	public static ArrayList<Country> filterByHazard(ArrayList<Country> countries,
			String hazard) {
		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			ArrayList<String> countryHazards = x.getHazards();
			if (countryHazards.contains(hazard))
				sorted.add(x);
		}
		return sorted;
	}

	/**
	 * @param countries
	 * @return the country with the lowest elevation from the input list
	 * finds the country with the lowest elevation
	 * 
	 */
	public static Country filterByLowestElevation(
			ArrayList<Country> countries) {
		int lowest = (int) Double.NEGATIVE_INFINITY;
		Country lowestCountry = null;
		for (Country x: countries) {
			int countryElevation = x.getLowestElevation();
			if (countryElevation < lowest)
				lowest = countryElevation;
			lowestCountry = x;
		}
		return lowestCountry;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param latitude: N or S hemisphere
	 * @param longitude: E or W hemisphere
	 * @return list of countries in the specific hemisphere
	 * 
	 * finds the countries that are in the specified hemisphere
	 */
	public static ArrayList<Country> filterByHemisphere(
			ArrayList<Country> countries, String latitude, String longitude) {

		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			//get the hemispheres that each country is in
			String[] hemispheres = x.getHemisphere();

			if ((hemispheres != null) && (hemispheres[0] != null) 
					&& (hemispheres[1] != null)) {

				//if both lat and long matches, then add country to the list
				if (hemispheres[0].equals(latitude) && 
						hemispheres[1].equals(longitude))
					sorted.add(x);
			}
		}
		return sorted;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param numParties: minimum number of political parties
	 * @return list of countries that match the specified filter
	 * 
	 * finds the countries that have the input minimum number of political
	 * parties
	 */
	public static ArrayList<Country> filterByMinPartyNumber(
			ArrayList<Country> countries, int numParties) {

		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			if (x.getNumParties() > numParties)
				sorted.add(x);
		}
		return sorted;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param returnNumber: top n number of countries to be returned
	 * @return top n countries with the greatest electricity consumption
	 * 			per capita
	 * sorts the electricity consumption per capita of the input countries
	 * and returns the top n (user specified number) countries
	 */
	public static ArrayList<Country> sortCountriesByElecConsumption(
			ArrayList<Country> countries, int returnNumber) {

		ArrayList<Country> toSort = new ArrayList<Country>();
		//get countries that have the relevant data
		for (Country x: countries) {
			if (x.getElecPerCapita() != 0) {
				toSort.add(x);
			}
		}
		//sort the countries
		Collections.sort(toSort, new CompareCountryElec());

		//select the top n countries
		ArrayList<Country> toReturn = new ArrayList<Country>();
		int count = 0;
		for (Country x: toSort) {
			if (count > returnNumber - 1) break;
			toReturn.add(x);
			count++;
		}
		return toReturn;
	}


	/**
	 * @param countries: countries to be filtered
	 * @param minPercentage: minimum percentage of people in dominant religion
	 * @param moreOrLess: true for comparing country's percentage with more than
	 * 					the input percent, false for less than
	 * @return list of countries that match the criteria
	 * 
	 * finds the countries with more or less than a specified percentage 
	 * of people in the dominant religion of the country
	 */
	public static ArrayList<Country> filterByDominantReligion(
			ArrayList<Country> countries, double minPercentage,
			boolean moreOrLess) {

		ArrayList<Country> sorted = new ArrayList<Country> ();
		for (Country x: countries) {
			double xPercent = x.getDomReligionPercent();
			if (moreOrLess) {
				if (xPercent != 0 && xPercent > minPercentage) {
					sorted.add(x);
				}
			}
			else {
				if (xPercent != 0 && xPercent < minPercentage) {
					sorted.add(x);
				}
			}
		}
		return sorted;
	}

	/**
	 * @param countries: countries to be filtered
	 * @return landlocked countries
	 * 
	 * finds the countries that are landlocked
	 */
	public static ArrayList<Country> filterByLandlocked(
			ArrayList<Country> countries) {

		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			if (x.getLandlocked())
				sorted.add(x);
		}
		return sorted;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param borderNumber: number of bordering countries
	 * @return list of countries that meet the criteria
	 * 
	 * finds countries that have the specified number of bordering countries
	 */
	public static ArrayList<Country> filterByBorderNumber(
			ArrayList<Country> countries, int borderNumber) {

		ArrayList<Country> sorted = new ArrayList<Country>();
		for (Country x: countries) {
			if (x.getBorderCountries() == borderNumber)
				sorted.add(x);
		}
		return sorted;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param returnNumber: number of top countries to be returned
	 * @return top n countries with criteria
	 * 
	 * finds the top n countries with the greatest military expenditure
	 * per capita
	 */
	public static ArrayList<Country> sortCountriesByMilSpending(
			ArrayList<Country> countries, int returnNumber) {

		ArrayList<Country> toSort = new ArrayList<Country>();
		//get countries that have the relevant data
		for (Country x: countries) {
			if (x.getMilSpendingPerCapita() != 0) {
				toSort.add(x);
			}
		}
		//sort countries
		Collections.sort(toSort, new CompareCountryMilSpending());

		//select top n countries to be returned
		ArrayList<Country> toReturn = new ArrayList<Country>();
		int count = 0;
		for (Country x: toSort) {
			if (count > returnNumber - 1) break;
			toReturn.add(x);
			count++;
		}
		return toReturn;
	}

	/**
	 * @param countries: countries to be filtered
	 * @param returnNumber: number of top countries to be returned
	 * @return top n countries with criteria
	 * 
	 * finds the top n countries with the greatest ratio of number of airports
	 * to population
	 */
	public static ArrayList<Country> sortCountriesByPopulationAirportRatio(
			ArrayList<Country> countries, int returnNumber) {

		ArrayList<Country> toSort = new ArrayList<Country>();
		//get countries that have relevant data
		for (Country x: countries) {
			if (x.getPopulationAirportRatio() != 0) {
				toSort.add(x);
			}
		}
		//sort countries
		Collections.sort(toSort, new CompareCountryPopAirportRatio());

		//select top n countries to be returned
		ArrayList<Country> toReturn = new ArrayList<Country>();
		int count = 0;
		for (Country x: toSort) {
			if (count > returnNumber - 1) break;
			toReturn.add(x);
			count++;
		}
		return toReturn;
	}

}
