package mu.utility.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import org.bson.conversions.Bson;

import org.json.simple.JsonObject;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;
import com.mongodb.client.AggregateIterable;

import mu.utility.service.MongoService;
import mu.utility.token.App;
import mu.utility.token.Constants;

import com.mu.api.transformer.*;

public class GamificationProcessor {

	/**
	 * Instance of mongoService dependency.
	 */
	MongoService mongoService = new MongoServiceimpl();

	private MongoConnectionObject mongoConnectionObject;

	IP2LocationServiceimpl ip2location = new IP2LocationServiceimpl();

	Properties properties = new Properties();

	private static final Logger LOGGER = Logger.getLogger(GamificationProcessor.class.getName());

	
	public void processMongoRequests(String pollID) {
		String URL = "";
		int limit = 1;

		// Load the properties file using FileInputStream
		try {

			properties.load(new FileInputStream("application.properties"));
			String limitstring = properties.getProperty("limit");
			limit = Integer.parseInt(limitstring);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		URL = properties.getProperty(Constants.MONGO_URL);

		MongoConnectionObject.setConnectionsPerHost(Constants.ConnectionsPerHost);
		MongoConnectionObject.setMaxConnectionIdleTime(Constants.MaxConnectionIdleTime);
		MongoConnectionObject.setMaxConnectionLifeTime(Constants.MaxConnectionLifeTime);
		MongoConnectionObject.setMongoDBURL(URL);

		try {
			MongoDatabase gamification = getDatabase(Constants.DATABASE);
			MongoCollection<Document> userpollsCollection = getCollection(gamification, Constants.COLLECTION);
			System.out.println("Poll ID: " + pollID);
			int totalCount = mongoService.getTotalCount(userpollsCollection, pollID);
			System.out.println("Total Count of documents " + totalCount);
			AggregateIterable<Document> result = mongoService.getPollAggregate(userpollsCollection, pollID, limit);
			if (result.first() != null) {
				Scanner sc = new Scanner(System.in);
				for (Document document : result) {
					PollArray pollArray = new PollArray();
					pollArray.setCount(Integer.parseInt(document.get("count").toString()));
					if (document.get("sourceIp") != null) {
						pollArray.setSourceIp(document.get("sourceIp").toString());
						String jsonResponse = ip2location.getGeolocation(document.get("sourceIp").toString());
						JSONObject jsonObject = new JSONObject(jsonResponse);
						String ip = jsonObject.getString("ip");
						Optional<String> countryNameOpt = Optional.ofNullable(jsonObject.optString("country_name"));
						Optional<String> cityNameOpt = Optional.ofNullable(jsonObject.optString("city_name"));

						Optional<Double> latitudeOpt = Optional
								.ofNullable(jsonObject.optDouble("latitude", Double.NaN));
						Optional<Double> longitudeOpt = Optional
								.ofNullable(jsonObject.optDouble("longitude", Double.NaN));
						String country = countryNameOpt.orElse("Null");
						String city = cityNameOpt.orElse("Null");

						double latitude = latitudeOpt.orElse(Double.NaN);
						double longitude = longitudeOpt.orElse(Double.NaN);

						System.out.println("SourceIp " + pollArray.getSourceIp() + " Count: " + pollArray.getCount()
								+ " Country " + country + " City " + city + " latitude " + latitude + " longitude "
								+ longitude);
					} else {
						System.out.println("SourceIp " + pollArray.getSourceIp() + " Count: " + pollArray.getCount());

					}

				}

				System.out.print("Do you want to update the Flag ? : (Y/N) ");
				String flag = sc.next();
				if (flag.equalsIgnoreCase("Y")) {
					updateDocumentsWithIsIgnoreFlag(userpollsCollection, result);
				}
				else if(!flag.equalsIgnoreCase("N"))
				{
					System.out.println("Incorrect input.");
				}

			} else {
				System.out.println("No data for mentioned Poll ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateDocumentsWithIsIgnoreFlag(MongoCollection<Document> userpollsCollection, AggregateIterable<Document> result) 
	{
		long updatedCount = 0;
		for (Document document : result) {
		Document updateDoc = new Document("$set", new Document("isIgnore", true));
		 if (document.get("sourceIp") != null) {
		String sourceIp = document.get("sourceIp").toString();
		UpdateResult updateResult =userpollsCollection.updateMany(Filters.eq("sourceIp", sourceIp), updateDoc);
		 updatedCount += updateResult.getModifiedCount();
		 }
	   
		}
		System.out.println("Documents updated : "+ updatedCount );
		
	}
		 

	private static MongoDatabase getDatabase( String databaseName) {
		MongoDatabase database = MongoDBConnection.mongoinstance.getDatabase(databaseName);
		return database;
	}

	private static MongoCollection<Document> getCollection(MongoDatabase database, String collectionName) {
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * @return the mongoConnectionObject
	 */
	public MongoConnectionObject getMongoConnectionObject() {
		return mongoConnectionObject;
	}

	/**
	 * @param mongoConnectionObject the mongoConnectionObject to set
	 */
	public void setMongoConnectionObject(MongoConnectionObject mongoConnectionObject) {
		this.mongoConnectionObject = mongoConnectionObject;
	}

	/**
	 * @return the mongoService
	 */
	public MongoService getMongoService() {
		return mongoService;
	}

	/**
	 * @param mongoService the mongoService to set
	 */
	public void setMongoService(MongoService mongoService) {
		this.mongoService = mongoService;
	}


}
