package hw3;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Nathaniel Chan
 *
 * Goes to the main page of the CIA World Factbook and collects the names
 * and URLs of countries
 * 
 */

public class MainPage {
	String url;
	
	public MainPage(String url) {
		this.url = url;
	}
	
	/**
	 * @return List of all the available and relevant countries in the 
	 * CIA World Factbook
	 */
	public ArrayList<Country> getCountryURLs() {
		ArrayList<Country> allCountries = new ArrayList<Country>();
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			
			// get country name and links
			Elements links = doc.select("a[href]");
			boolean check = false;
			for (Element link : links) {
	 
				//if reach this line then start parsing links
				if (link.text().equals("World")) check = true;
				
				//if reached end of links then top parsing links
				if (link.attr("href").equals("#wfbtop")) break;
				
				//parse links if in relevant area
				if (check) {
					String name = link.text();
					
					//skip entries that can be ignored as a country
					if (name.equals("World")
							|| name.equals("European Union")
							|| name.contains("Ocean")) continue;
					
					String url = link.attr("href");
					url = url.substring(2);
					
					//create new country with name and URL, then add
					//to list of countries
					Country current = new Country(name, url);
					allCountries.add(current);
				}
			}
		} catch (IOException e) {
			System.out.println("Cannot connect to CIA World Factbook main page.");
			e.printStackTrace();
		}
		//return all countries with parsed URLs
		return allCountries;
	}
	
	
	
	
	
	

}
