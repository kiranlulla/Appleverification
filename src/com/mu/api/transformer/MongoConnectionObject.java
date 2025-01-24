//-----------------------------------------------------------------------

/*
* Copyright (c) 2015, 2022, HCL Technologies Ltd. All rights reserved.
* Material published by HCL Technologies on these web
* pages/mobile app may not be reproduced without permission.
*/

//-----------------------------------------------------------------------

package com.mu.api.transformer;

public class MongoConnectionObject {

	private static String mongoDBURL;
	
	private static String mongoConnectionConfigFlag;
	
	private static String maxConnectionIdleTime;
	
	private static String maxConnectionLifeTime;
	
	private static String connectionsPerHost;

	/**
	 * @return the mongoDBURL
	 */
	public static String getMongoDBURL() {
		return mongoDBURL;
	}

	/**
	 * @param mongoDBURL the mongoDBURL to set
	 */
	public static void setMongoDBURL(String mongoDBURL) {
		MongoConnectionObject.mongoDBURL = mongoDBURL;
	}

	/**
	 * @return the mongoConnectionConfigFlag
	 */
	public static String getMongoConnectionConfigFlag() {
		return mongoConnectionConfigFlag;
	}

	/**
	 * @param mongoConnectionConfigFlag the mongoConnectionConfigFlag to set
	 */
	public static void setMongoConnectionConfigFlag(String mongoConnectionConfigFlag) {
		MongoConnectionObject.mongoConnectionConfigFlag = mongoConnectionConfigFlag;
	}

	/**
	 * @return the maxConnectionIdleTime
	 */
	public static String getMaxConnectionIdleTime() {
		return maxConnectionIdleTime;
	}

	/**
	 * @param maxConnectionIdleTime the maxConnectionIdleTime to set
	 */
	public static void setMaxConnectionIdleTime(String maxConnectionIdleTime) {
		MongoConnectionObject.maxConnectionIdleTime = maxConnectionIdleTime;
	}

	/**
	 * @return the maxConnectionLifeTime
	 */
	public static String getMaxConnectionLifeTime() {
		return maxConnectionLifeTime;
	}

	/**
	 * @param maxConnectionLifeTime the maxConnectionLifeTime to set
	 */
	public static void setMaxConnectionLifeTime(String maxConnectionLifeTime) {
		MongoConnectionObject.maxConnectionLifeTime = maxConnectionLifeTime;
	}

	/**
	 * @return the connectionsPerHost
	 */
	public static String getConnectionsPerHost() {
		return connectionsPerHost;
	}

	/**
	 * @param connectionsPerHost the connectionsPerHost to set
	 */
	public static void setConnectionsPerHost(String connectionsPerHost) {
		MongoConnectionObject.connectionsPerHost = connectionsPerHost;
	}


}
