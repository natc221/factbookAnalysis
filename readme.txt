NETS 150 - Homework 3
Name: Nathaniel Chan
Pennkey: channat

The main method is in the Main.java class. The program first collects the names and URLs of the countries on the CIA World Factbook through the MainPage.java class. Then the page for each of these countries are visited and the relevant data is collected for the country. Each country is of the object Country, and there are getter, setter methods for the relevant data. There are also methods where necessary calculations are made. The data is also printed in System.out.

Collecting the data may take up to 20 seconds. It is also possible that there will be a timeout error if the connection with a page is too long. This may occur if the internet connection is too slow, or if there are other errors.

Afterwards, a list of options, A-J are printed, and the user is asked to enter the corresponding letter to run a particular analysis. 
For example, option “B. Lowest elevation in continent”, the user would enter “b”, then hit the return key.

Further instructions will be displayed if necessary, and for this example, it will print “Please enter (1) continent”. The user would then enter the name of the continent, e.g. “North America”, then hit the return key.

The answer/result will then be displayed.
The extra credit questions were also implemented, and can be answered.


There were many cases where the data parsing gave FormatExceptions, or in other cases where a particular value. Therefore, try/catch is used frequently to ensure the input field or data is valid. There are also checks to see if the user inputs are valid, and also if such data exists for each country.
There are some countries that are ignored, namely “European Union” and oceans, which are listed in the CIA World Factbook, but whose data can be ignored since these are not valid countries in our analysis.

The list of possible hazards are setup in the Main.java class, in the setUpHazards() method. Since there are many possible hazards, and they are not input in the CIA World Factbook in a standard form, a number of common hazards have been selected.

Jsoup is used for the HTTP connection, getting an HTML page, and also for parsing the data.