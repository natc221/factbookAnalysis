package hw3;

import java.util.Comparator;

/**
 * @author Nathaniel Chan
 * Compare two countries' electricity consumption per capita and returns the
 * the greater one.
 * Used for sorting countries.
 */
public class CompareCountryElec implements Comparator<Country> {

	@Override
	public int compare(Country a, Country b) {
		Long aElec = a.getElecPerCapita();
		Long bElec = b.getElecPerCapita();
		if (aElec >= bElec) return 0;
		else return 1;
	}

}
