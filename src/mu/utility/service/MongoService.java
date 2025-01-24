package mu.utility.service;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;

public interface MongoService {

	AggregateIterable<Document> getPollAggregate(MongoCollection<Document> userpollsCollection, String pollID,
			int limit);

	int getTotalCount(MongoCollection<Document> userpollsCollection, String pollID);

}
