package hw3;

import java.util.Comparator;

/**
 * @author Nathaniel Chan
 * Compare two countries' ratio of population to number of airports
 * and returns the the greater one.
 * Used for sorting countries.
 */
public class CompareCountryPopAirportRatio implements Comparator<Country> {
	@Override
	public int compare(Country a, Country b) {
		Long aElec = a.getPopulationAirportRatio();
		Long bElec = b.getPopulationAirportRatio();
		if (aElec >= bElec) return 0;
		else return 1;
	}
}
