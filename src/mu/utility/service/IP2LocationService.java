package mu.utility.service;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

public interface IP2LocationService {

	String getGeolocation(String ipAddress);

}
