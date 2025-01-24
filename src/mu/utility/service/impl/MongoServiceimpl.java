package mu.utility.service.impl;

import java.util.Arrays;
import java.util.Iterator;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import mu.utility.token.Constants;
import mu.utility.service.MongoService;
import mu.utility.service.impl.*;


public class MongoServiceimpl implements MongoService {
	
	private static final Logger LOGGER = Logger.getLogger(MongoServiceimpl.class.getName());

	@Override
	public AggregateIterable<Document> getPollAggregate(MongoCollection<Document> userpollsCollection, String pollID,
			int limit) {
		AggregateIterable<Document> result =	userpollsCollection.aggregate(Arrays.asList( 
				new Document("$match", new Document("pollID", pollID)),  
			    new Document("$group", 
			    new Document("_id", "$sourceIp")
			            .append("count", 
			    new Document("$sum", 1L))), 
			    new Document("$sort", 
			    new Document("count", -1L)), 
			    new Document("$limit", limit), 
			    new Document("$project", 
			    new Document("_id", 0L)
			            .append("sourceIp", "$_id")
			            .append("count", 1L))));		
		return result;
	}
	
	public int getTotalCount(MongoCollection<Document> userpollsCollection, String pollID) {
        AggregateIterable<Document> result = userpollsCollection.aggregate(Arrays.asList(
            new Document("$match", new Document("pollID", pollID)),
            new Document("$count", "totalCount")
        ));
        if (result.first() != null) {
            return result.first().getInteger("totalCount");
        }
        
        return 0;
    }

	
	
	

}
