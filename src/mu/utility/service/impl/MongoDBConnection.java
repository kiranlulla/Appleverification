//-----------------------------------------------------------------------

/*
* Copyright (c) 2015, 2022, HCL Technologies Ltd. All rights reserved.
* Material published by HCL Technologies on these web
* pages/mobile app may not be reproduced without permission.
*/

//-----------------------------------------------------------------------

package mu.utility.service.impl;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;



import com.mu.api.transformer.MongoConnectionObject;

import mu.utility.token.Constants;

public class MongoDBConnection {

	private static final Logger LOGGER = LogManager.getLogger(MongoDBConnection.class);

	public static MongoClient mongoinstance;

	static {
		try {
            
			String mongoDbConnectionString = MongoConnectionObject.getMongoDBURL();
			mongoDbConnectionString ="mongodb://localhost:27017";
					//Constants.MONGO_DB_SERVER + mongoDbConnectionString;
			int connectionsPerHost = Integer.parseInt(MongoConnectionObject.getConnectionsPerHost());
			int maxConnectionIdleTime = Integer.parseInt(MongoConnectionObject.getMaxConnectionIdleTime());
			int maxConnectionLifeTime = Integer.parseInt(MongoConnectionObject.getMaxConnectionLifeTime());
			//String mongoConnectionConfigFlag = MongoConnectionObject.getMongoConnectionConfigFlag();

		
			LOGGER.debug("Mongo DB Connection String : " + mongoDbConnectionString);
			
			mongoinstance = new MongoClient(new MongoClientURI(mongoDbConnectionString));

		} catch (Exception e) {
			throw new RuntimeException("Mongo Connection Failure :: Exception occured in creating MongoDB instance");
		}

	}



	public static MongoClient closeInstance() {
		mongoinstance.close();
		return null;
	}
}
