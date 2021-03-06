package org.jongo.use_native;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class WithDocumentNativeTest extends NativeTestBase {

    private MongoCollection<Bson> collection;

    @Before
    public void setUp() throws Exception {
        collection = jongo.raw(database.getCollection("friends"));
    }

    @After
    public void tearDown() throws Exception {
        collection.drop();
    }

    @Test
    public void canQueryWithNativeDocument() throws Exception {

        Document document = new Document("name", "Abby").append("address", "123 Wall Street");

        collection.insertOne(document);

        Bson result = collection.find(new Document("address", "123 Wall Street")).first();
        BsonDocument bsonDocument = result.toBsonDocument(DBObject.class, MongoClient.getDefaultCodecRegistry());
        assertThat(bsonDocument.toJson()).contains("123 Wall Street");
    }
}
