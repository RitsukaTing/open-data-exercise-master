package edu.nyu.cs;

// some basic java imports
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import java.util.regex.Matcher;

// some imports used by the UnfoldingMap library
import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.*;
import de.fhpotsdam.unfolding.providers.MapBox;
import de.fhpotsdam.unfolding.providers.Google.*;
import de.fhpotsdam.unfolding.providers.Microsoft;

// import de.fhpotsdam.unfolding.utils.ScreenPosition;


/**
 * A program that pops open an interactive map.
 */
public class App extends PApplet {

	/****************************************************************/
	/*                  BEGIN - DON'T MODIFY THIS CODE              */
	/****************************************************************/
	UnfoldingMap map; // will be a reference to the actual map
	String mapTitle; // will hold the title of the map
	final float SCALE_FACTOR = 0.0002f; // a factor used to scale pedestrian counts to calculate a reasonable radius for a bubble marker on the map
	final int DEFAULT_ZOOM_LEVEL = 11;
	final Location DEFAULT_LOCATION = new Location(40.7286683f, -73.997895f); // a hard-coded NYC location to start with
	String[][] data; // will hold data extracted from the CSV data file
	/****************************************************************/
	/*                    END - DON'T MODIFY THIS CODE              */
	/****************************************************************/

	/**
	 * This method is automatically called every time the user presses a key while viewing the map.
	 * The `key` variable (type char) is automatically is assigned the value of the key that was pressed.
	 * Complete the functions called from here, such that:
	 * 	- when the user presses the `1` key, the code calls the showMay2021MorningCounts method to show the morning counts in May 2021, with blue bubble markers on the map.
	 * 	- when the user presses the `2` key, the code calls the showMay2021EveningCounts method to show the evening counts in May 2021, with blue bubble markers on the map.
	 * 	- when the user presses the `3` key, the code calls the showMay2021EveningMorningCountsDifferencemethod to show the difference between the evening and morning counts in May 2021.  If the evening count is greater, the marker should be a green bubble, otherwise, the marker should be a red bubble.
	 * 	- when the user presses the `4` key, the code calls the showMay2021VersusMay2019Counts method to show the difference between the average of the evening and morning counts in May 2021 and the average of the evening and morning counts in May 2019.  If the counts for 2021 are greater, the marker should be a green bubble, otherwise, the marker should be a red bubble.
	 * 	- when the user presses the `5` key, the code calls the customVisualization1 method to show data of your choosing, visualized with marker types of your choosing.
	 * 	- when the user presses the `6` key, the code calls the customVisualization2 method to show data of your choosing, visualized with marker types of your choosing.
	 */
	public void keyPressed() {
		// System.out.println("Key pressed: " + key);
		// complete this method
		String filepath = "D:/Homework/CS 101 HW/open-data-exercise-master/data/PedCountLocationsMay2015.csv";

		try {
			String[] data = getLinesFromFile(filepath);
			String[][] dataSet = getDataFromLines(data);
			
			if (key == 49) {
				showMay2021MorningCounts(dataSet);
			} 
			else if (key == 50) {
				showMay2021EveningCounts(dataSet);
			} 
			else if (key == 51) {
				showMay2021EveningMorningCountsDifference(dataSet);
			}
			else if (key == 52) {
				showMay2021VersusMay2019Counts(dataSet);
			}
			else if (key == 53) {
				customVisualization1(dataSet);
			}
			else if (key == 54) {
				customVisualization2(dataSet);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + filepath);
		} catch (IOException e) {
			System.out.println("Error reading file");
		}
	}

	/**
	 * Adds markers to the map for the morning pedestrian counts in May 2021.
	 * These counts are in the second-to-last field in the CSV data file.  So we look at the second-to-last array element in our data array for these values.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021MorningCounts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "May 2021 Morning Pedestrian Counts";
		// complete this method - DELETE THE EXAMPLE CODE BELOW
	
		// remove the code below and replace with your own code that solves the problem indicated in the comments
		// example of how to create a marker at a specific location and place it on the map
		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]); 
				int populationPoint = Integer.parseInt(data[i][87]);
				
				Location markerLocation = new Location(lat, lng); 
				int pedestrianCount = populationPoint; 
				float markerRadius = pedestrianCount * SCALE_FACTOR; 
				float[] markerColor = {0, 0, 255, 127};
				MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
				map.addMarker(marker);
			}
		}
	}

	/**
	 * Adds markers to the map for the evening pedestrian counts in May 2021.
	 * These counts are in the second-to-last field in the CSV data file.  So we look at the second-to-last array element in our data array for these values.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021EveningCounts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "May 2021 Evening Pedestrian Counts";
		// complete this method
		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]); 
				int populationPoint = Integer.parseInt(data[i][88]);
				
				Location markerLocation = new Location(lat, lng); 
				int pedestrianCount = populationPoint; 
				float markerRadius = pedestrianCount * SCALE_FACTOR; 
				float[] markerColor = {255, 0, 0, 127};
				MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
				map.addMarker(marker);
			}
		}
	}

	/**
	 * Adds markers to the map for the difference between evening and morning pedestrian counts in May 2021.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021EveningMorningCountsDifference(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Difference Between May 2021 Evening and Morning Pedestrian Counts";
		// complete this method

		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]); 
				int populationPoint = Integer.parseInt(data[i][88]) - Integer.parseInt(data[i][87]);
				
				Location markerLocation = new Location(lat, lng); 
				int pedestrianCount = populationPoint; 
				float markerRadius = pedestrianCount * SCALE_FACTOR; 
				float[] markerColor = {0, 255, 0, 127};
				MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
				map.addMarker(marker);
			}
		}
	}

	/**
	 * Adds markers to the map for the difference between the average pedestrian count in May 2021 and the average pedestrian count in May 2019.
	 * Note that some pedestrian counts were not done in May 2019, and the data is blank.  
	 * Do not put a marker at those locations with missing data.
	 * 
	 * @param data A two-dimensional String array, containing the data returned by the getDataFromLines method.
	 */
	public void showMay2021VersusMay2019Counts(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Difference Between May 2021 and May 2019 Pedestrian Counts";
		// complete this method
		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]);
				int pop2021 = (Integer.parseInt(data[i][88]) + Integer.parseInt(data[i][87]))/2;
				int pop2019;
				if (data[i][83].equals("")) {
					continue;
				} else {
					pop2019 = (Integer.parseInt(data[i][81]) + Integer.parseInt(data[i][82]))/2;
				}
				
				Location markerLocation = new Location(lat, lng); 
				int pedestrianDiff;
				if (pop2021 > pop2019) {
					pedestrianDiff = pop2021 - pop2019;
				} else {
					pedestrianDiff = pop2019 - pop2021;
				}
				float markerRadius = pedestrianDiff * SCALE_FACTOR; 
				float[] markerColor = {128, 0, 128, 127};
				MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
				map.addMarker(marker);
			}
		}
	}

	/**
	 * A data visualization of your own choosing.  
	 * Do some data analysis and map the results using markers.
	 * 
	 * @param data
	 */
	public void customVisualization1(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Locations without 2019 Pedestrian Counts";
		// complete this method		
		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]);
				int circlesize = 2250;

				if (data[i][83].equals("")) {
					Location markerLocation = new Location(lat, lng); 
					float markerRadius = circlesize * SCALE_FACTOR; 
					float[] markerColor = {290, 50, 189, 127};
					MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); // don't worry about the `this` keyword for now... just make sure it's there.
					map.addMarker(marker);
				} 
			}		
		}
	}

	/**
	 * A data visualization of your own choosing.  
	 * Do some data analysis and map the results using markers.
	 * 
	 * @param data
	 */
	public void customVisualization2(String[][] data) {
		clearMap(); // clear any markers previously placed on the map
		mapTitle = "Weekday Growth % from 2019 to 2021 (Blue for Growth and Red for Decay)";
		
		for (int i = 0; i < data.length; i++) {
			if (data[i].length < 90) {
				continue;
			} else {
				double lat = Double.parseDouble(data[i][1]); 
				double lng = Double.parseDouble(data[i][0]);
				float pop2021 = (Integer.parseInt(data[i][88]) + Integer.parseInt(data[i][87]));
				float pop2019;
				if (data[i][83].equals("")) {
					continue;
				} else {
					pop2019 = (Integer.parseInt(data[i][81]) + Integer.parseInt(data[i][82]));
				}

				float growthRate;
				int colorR;
				int colorB;

				if (pop2021 > pop2019) {
					growthRate = ((pop2021 - pop2019)/pop2021) * 2500; 
					colorR = 0;
					colorB = 255;
					 
				} else {
					growthRate = ((pop2019 - pop2021)/pop2021) * 2500; 
					colorR = 255;
					colorB = 0;
				}
				
				int growthInt = (int) growthRate;
					Location markerLocation = new Location(lat, lng); 
					float markerRadius = growthInt * SCALE_FACTOR; 
					float[] markerColor = {colorR, 0, colorB, 127};
					MarkerBubble marker = new MarkerBubble(this, markerLocation, markerRadius, markerColor); 
					map.addMarker(marker);
			}	
		}
	}

	

	/**
	 * Opens a file and returns an array of the lines within the file, as Strings with their line breaks removed.
	 * 
	 * @param filepath The filepath to open
	 * @return A String array, where each String contains the text of a line of the file, with its line break removed.
	 * @throws FileNotFoundException
	 */
	public String[] getLinesFromFile(String filepath) throws FileNotFoundException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		List<String> lines = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		return lines.toArray(new String[0]);
	}

	/**
	 * Takes an array of lines of text in comma-separated values (CSV) format and splits each line into a sub-array of data fields.
	 * For example, an argument such as {"1,2,3", "100,200,300"} could result in a returned array { {"1", "2", "3"}, {"100", "200", "300"} }
	 * This method must skip any lines that don't contain mappable data (i.e. don't have any geospatial data in them) 
	 * and do any other cleanup of the data necessary for it to be easily mapped by other code in the program.
	 *
	 * @param lines A String array of lines of text, where each line is in comma-separated values (CSV) format.
	 * @return A two-dimensional String array, where each inner array contains the data from one of the lines, split by commas.
	 */
	
	public String[][] getDataFromLines(String[] lines) {

		List<String[]> data = new ArrayList<>();
		for (int i = 1; i < lines.length; i++ ) {
			String[] subsets = lines[i].split(",");
			data.add(subsets);
		}
	
		String[][] dataSet = new String[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			dataSet[i] = data.get(i);
		}

		String[][] newSet = new String[dataSet.length][dataSet[0].length + 1];
		for (int i = 0; i < dataSet.length; i++) {
			String[] coords = dataSet[i][0].replace("POINT (", "").replace(")","").split(" ");
			String longitude = coords[0];
			String latitude = coords[1];

			String[] dataSub = new String[dataSet[i].length + 1];
			dataSub[0] = longitude;
			dataSub[1] = latitude;

			for (int h = 1; h < dataSet[i].length; h++) {
				dataSub[h + 1] = dataSet[i][h];
			}
			newSet[i] = dataSub;
		}
		return newSet;
	}

	


	/****************************************************************/
	/**** YOU WILL MOST LIKELY NOT NEED TO EDIT ANYTHING BELOW HERE */
	/****               Proceed at your own peril!                  */
	/****************************************************************/

	/**
	 * This method will be automatically called when the program runs
	 * Put any initial setup of the window, the map, and markers here.
	 */
	public void setup() {
		size(1200, 800, P2D); // set the map window size, using the OpenGL 2D rendering engine
		// size(1200, 800); // set the map window size, using Java's default rendering engine (try this if the OpenGL doesn't work for you)
		map = getMap(); // create the map and store it in the global-ish map variable

		// load the data from the file... you will have to complete the functions called to make sure this works
		try {
			String cwd = Paths.get("").toAbsolutePath().toString(); // the current working directory as an absolute path
			String path = Paths.get(cwd, "data", "PedCountLocationsMay2015.csv").toString(); // e.g "data/PedCountLocationsMay2015.csv" on Mac/Unix vs. "data\PedCountLocationsMay2015.csv" on Windows
			String[] lines = getLinesFromFile(path); // get an array of the lines from the file.
			data = getDataFromLines(lines); // get a two-dimensional array of the data in these lines; complete the getDataFromLines method so the data from the file is returned appropriately
			// System.out.println(Arrays.deepToString(data)); // for debugging

			// automatically zoom and pan into the New York City location
			map.zoomAndPanTo(DEFAULT_ZOOM_LEVEL, DEFAULT_LOCATION);

			// by default, show markers for the morning counts in May 2021 (the third-to-last field in the CSV file)
			showMay2021MorningCounts(data);

			// various ways to zoom in / out
			// map.zoomLevelOut();
			// map.zoomLevelIn();
			// map.zoomIn();
			// map.zoomOut();

			// it's possible to pan to a location or a position on the screen
			// map.panTo(nycLocation); // pan to NYC
			// ScreenPosition screenPosition = new ScreenPosition(100, 100);
			// map.panTo(screenPosition); // pan to a position on the screen

			// zoom and pan into a location
			// int zoomLevel = 10;
			// map.zoomAndPanTo(zoomLevel, nycLocation);

			// it is possible to restrict zooming and panning
			// float maxPanningDistance = 30; // in km
			// map.setPanningRestriction(nycLocation, maxPanningDistance);
			// map.setZoomRange(5, 22);


		}
		catch (Exception e) {
			System.out.println("Error: could not load data from file: " + e);
		}

	} // setup

	/**
	 * Create a map using a publicly-available map provider.
	 * There will usually not be a need to modify this method. However, in some cases, you may need to adjust the assignment of the `map` variable.
	 * If there are error messages related to the Map Provider or with loading the map tile image files, try all of the other commented-out map providers to see if one works.
	 * 
	 * @return A map object.
	 */
	private UnfoldingMap getMap() {
		// not all map providers work on all computers.
		// if you have trouble with the one selected, try the others one-by-one to see which one works for you.
		map = new UnfoldingMap(this, new Microsoft.RoadProvider());
		// map = new UnfoldingMap(this, new Microsoft.AerialProvider());
		// map = new UnfoldingMap(this, new GoogleMapProvider());
		// map = new UnfoldingMap(this);
		// map = new UnfoldingMap(this, new OpenStreetMapProvider());

		// enable some interactive behaviors
		MapUtils.createDefaultEventDispatcher(this, map);
		map.setTweening(true);
		map.zoomToLevel(DEFAULT_ZOOM_LEVEL);

		return map;
	}
	
	/**
	 * This method is called automatically to draw the map.
	 * This method is given to you.
	 * There is no need to modify this method.
	 */
	public void draw() {
		background(0);
		map.draw();
		drawTitle();
	}

	/**
	 * Clear the map of all markers.
	 * This method is given to you.
	 * There is no need to modify this method.
	 */
	public void clearMap() {
		map.getMarkers().clear();
	}

	/**
	 * Draw the title of the map at the bottom of the screen
	 */
	public void drawTitle() {
		fill(0);
		noStroke();
		rect(0, height-40, width, height-40); // black rectangle
		textAlign(CENTER);
		fill(255);
		text(mapTitle, width/2, height-15); // white centered text
	}

	/**
	 * The main method is automatically called when the program runs.
	 * This method is given to you.
	 * There is no need to modify this method.
	 * @param args A String array of command-line arguments.
	 */
	public static void main(String[] args) {
		System.out.printf("\n###  JDK IN USE ###\n- Version: %s\n- Location: %s\n### ^JDK IN USE ###\n\n", SystemUtils.JAVA_VERSION, SystemUtils.getJavaHome());
		boolean isGoodJDK = SystemUtils.IS_JAVA_1_8;
		if (!isGoodJDK) {
			System.out.printf("Fatal Error: YOU MUST USE JAVA 1.8, not %s!!!\n", SystemUtils.JAVA_VERSION);
		}
		else {
			PApplet.main("edu.nyu.cs.App"); // do not modify this!
		}
	}

}
