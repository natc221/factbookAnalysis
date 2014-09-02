package hw3;

import java.util.Comparator;

/**
 * @author Nathaniel Chan
 * Compare two countries' miltary expenditure per capita and returns the
 * the greater one.
 * Used for sorting countries.
 */
public class CompareCountryMilSpending implements Comparator<Country>{

	@Override
	public int compare(Country a, Country b) {
		Long aElec = a.getMilSpendingPerCapita();
		Long bElec = b.getMilSpendingPerCapita();
		if (aElec >= bElec) return 0;
		else return 1;
	}
}
