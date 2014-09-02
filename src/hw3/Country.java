package hw3;

import java.util.ArrayList;

/**
 * @author Nathaniel Chan
 * Country object for containing data about a country
 *
 */
public class Country {

	private String name;
	private String endURL;
	private String continent;
	private ArrayList<String> hazards;
	private int lowestElevation;
	private int[] coordinates;
	private String[] hemispheres;
	private int numParties;
	private long elecConsumtion;
	private int population;
	private long elecPerCapita;
	private double domReligionPercent;
	private boolean landlocked;
	private int borderCountries;
	private double militarySpending;
	private long absMilitarySpending;
	private long milSpendingPerCapita;
	private long gdp;
	private int airportNumber;
	private int populationAirportRatio;
	private String domReligionName;
	
	private int capitalX;
	private int capitalY;
	

	/**
	 * @param name
	 * @param url
	 * Constructor for object
	 */
	public Country(String name, String url) {
		this.name = name;
		this.endURL = url;
		this.hazards = new ArrayList<String>();
		this.coordinates = new int[4];
		this.hemispheres = new String[2];
	}

	/** @return name of country */
	public String getName() {
		return this.name;
	}

	/**
	 * @param url
	 * setter for URL ending path of the country
	 */
	public void setURL(String url) {
		this.endURL = url;
	}

	/** @return ending URL path for country */
	public String getURL() {
		return this.endURL;
	}

	/**
	 * @param continent
	 * setter for continent that the coutnry belongs to
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}

	/** @return continent that the country belongs to */
	public String getContinent() {
		return this.continent;
	}

	/**
	 * @param hazard
	 * adds hazard to the list of common natural hazards the country has
	 */
	public void addHazard(String hazard) {
		hazards.add(hazard);
	}

	/** @return list of the common hazards the country has */
	public ArrayList<String> getHazards() {
		return hazards;
	}

	/**
	 * @param elevation
	 * setter for the lowest elevation point for a country
	 */
	public void setLowestElevation(int elevation) {
		this.lowestElevation = elevation;
	}

	/** @return lowest elevation of country */
	public int getLowestElevation() {
		return this.lowestElevation;
	}

	/**
	 * @param lat1: first coordinate of latitude
	 * @param lat2: second coordinate of latitude
	 * @param long1: first coordinate of longitude
	 * @param long2: second coordinate of longitude
	 * sets the latlong coordinates of the country
	 */
	public void setCoordinates(int lat1, int lat2, int long1, int long2) {
		coordinates[0] = lat1;
		coordinates[1] = lat2;
		coordinates[2] = long1;
		coordinates[3] = long2;
	}

	/** @return array of coordinates of country */
	public int[] getCoordinates() {
		return coordinates;
	}

	/**
	 * @param latitude: N or S
	 * @param longitude: E or W
	 * sets which hemisphere the coordinates belong to
	 */
	public void setHemisphere(String latitude, String longitude) {
		hemispheres[0] = latitude;
		hemispheres[1] = longitude;
	}

	/** @return array of hemispheres that the country belongs to 
	 *  e.g {N, E}
	 */
	public String[] getHemisphere() {
		return hemispheres;
	}

	/**
	 * @param numParties: number of political parties
	 * sets the number of political parties a country has
	 */
	public void setNumParties(int numParties) {
		this.numParties = numParties;
	}

	/** @return number of political parties a country has */
	public int getNumParties() {
		return this.numParties;
	}

	/**
	 * @param electricityConsumption: electricity consumption in kWh
	 * setter for the total electricity consumption of a country 
	 * expressed in kilowatt-hours
	 */
	public void setElecConsumption(long electricityConsumption) {
		this.elecConsumtion = electricityConsumption;
	}

	/** @return	electricity consumption of a country */
	public long getElecConsumption() {
		return this.elecConsumtion;
	}

	/**
	 * @param pop: population
	 * sets population of a country
	 */
	public void setPopulation(int pop) {
		if (this.population != 0) {
			this.population += pop;
		}
		else this.population = pop;
	}

	/**
	 * @return population of country
	 */
	public int getPopulation() {
		return this.population;
	}

	/**
	 * calculates electricity consumption per capita if both the data for
	 * population and electricity consumption exist
	 */
	public void calculateElecPerCapita() {
		if ((this.population != 0) && (this.elecConsumtion != 0))
			this.elecPerCapita = this.elecConsumtion / this.population;
	}

	/**
	 * @return electricity consumption per capita of the country
	 */
	public long getElecPerCapita() {
		return this.elecPerCapita;
	}

	/**
	 * @param percent: percentage of people with the dominant religion
	 * 					in the country
	 * @param religionName: name of dominant religion
	 * sets the dominant religion percentage and name of country
	 */
	public void setDomReligion(double percent, String religionName) {
		this.domReligionPercent = percent;
		this.domReligionName = religionName;
	}
	
	/** @return percent dominant religion percentage of country */
	public double getDomReligionPercent() {
		return this.domReligionPercent;
	}
	
	/** @return dominant religion name */
	public String getDomReligionName() {
		return this.domReligionName;
	}
	
	/**
	 * @param b: whether a country is landlocked or not
	 * sets if a country is landlocked
	 */
	public void setLandlocked(boolean b) {
		this.landlocked = b;
	}
	
	/**
	 * @return boolean of whether a country is landlocked or not
	 */
	public boolean getLandlocked() {
		return this.landlocked;
	}
	
	/**
	 * @param borderNumber number of bordering countries
	 * set the number of bordering countries
	 */
	public void setBorderCountryNumber(int borderNumber) {
		this.borderCountries = borderNumber;
	}

	/** @return number of bordering countries */
	public int getBorderCountries() {
		return this.borderCountries;
	}
	
	/**
	 *  @param spendingPercent
	 * set military spending in percent of GDP per year
	 */
	public void setMilitarySpending(double spendingPercent) {
		this.militarySpending = spendingPercent;
	}
	
	/** @return	military spending in percent of GDP per year */
	public double getMiltarySpending() {
		return this.militarySpending;
	}
	
	/**
	 * @param gdp
	 * set GDP of a country
	 */
	public void setGDP(long gdp) {
		this.gdp = gdp;
	}
	
	/** @return	GDP of a country */
	public long getGDP() {
		return this.gdp;
	}
	
	/** calculate and store the total military spending */
	public void calculateAbsoluteMilitarySpending() {
		if ((this.gdp != 0) && (this.militarySpending != 0)) {
			this.absMilitarySpending = 
					(long) (this.gdp * this.militarySpending);
		}
	}
	
	/** @return	total military spending */
	public long getAbsMilitarySpending() {
		return this.absMilitarySpending;
	}
	
	/**
	 * calculate and store the miltary spending per capita for the country
	 */
	public void calcMilitarySpendingPerCapita() {
		if ((this.absMilitarySpending != 0) && (this.population != 0)) {
			this.milSpendingPerCapita = 
					this.absMilitarySpending / this.population;
		}
	}
	
	/** @return military spending per capita */
	public long getMilSpendingPerCapita() {
		return this.milSpendingPerCapita;
	}
	
	/**
	 * @param airportNumber
	 * sets number of airports in a country
	 */
	public void setAirportNumber(int airportNumber) {
		this.airportNumber = airportNumber;
	}
	
	/** @return	number of airports in country */
	public int getAirportNumber() {
		return this.airportNumber;
	}
	
	/** calculates the ratio of airports to the GDP of a country */
	public void calculatePopulationAirportRatio() {
		if ((this.airportNumber != 0) && (this.gdp != 0))
			this.populationAirportRatio = this.population / this.airportNumber;
	}
	
	/** @return	ratio of population to airport */
	public long getPopulationAirportRatio() {
		return this.populationAirportRatio;
	}
	
	/**
	 * @param x: latitude
	 * @param y: longitude (with transformed coordinate system)
	 * sets the x and y coordinates of the capital city
	 */
	public void setCapitalCoor(int x, int y) {
		this.capitalX = x;
		this.capitalY = y;
	}
	
	/**
	 * @returnx coordinate of capital city
	 */
	public int getCapitalX() {
		return this.capitalX;
	}
	
	/**
	 * @return y coordinate of capital city
	 */
	public int getCapitalY() {
		return this.capitalY;
	}

	/**	prints the name of the country, makes it more convenient in
	 * other methods to print country name
	 */
	public void print() {
		System.out.println(this.name);
	}


}
