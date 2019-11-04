package mongodb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * @author <a href="mailto:zhengwt@51kahui.com">郑文婷</a> 于 2016年11月14日 下午5:07:10
 * @ClassName: MongoDbUtils
 * @Description: Description of this class
 */

public class MongoDbUtils {

    /**
     * MongoDb配置key
     */
    public static String MONGDB_URLS = null;
    public static String MONGDB_USER = null;
    public static String MONGDB_PASSWORD = null;
    private static Configuration config = null;
    private static String configfile = "mongodb.properties";

    static {
        try {
            config = new PropertiesConfiguration(configfile);
            MONGDB_URLS = config.getString("mongodb.urls");
            MONGDB_USER = config.getString("mongodb.user");
            MONGDB_PASSWORD = config.getString("mongodb.password");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    static Queue<MongoClient> queueWorking = new LinkedList<MongoClient>();

    public synchronized static MongoClient getMongoClient() {
        if (queueWorking.size() > 0)
            return queueWorking.poll();
        ServerAddress serverAddress = null;
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        if (MONGDB_URLS.contains(",")) {
            String urlCompletes[] = MONGDB_URLS.split("\\,");
            for (String urlComplete : urlCompletes) {
                String urls[] = urlComplete.split("\\:");
                serverAddress = new ServerAddress(urls[0], Integer.parseInt(urls[1]));
                seeds.add(serverAddress);
            }

        } else {
            String urls[] = MONGDB_URLS.split("\\:");
            serverAddress = new ServerAddress(urls[0], Integer.parseInt(urls[1]));
            seeds.add(serverAddress);
        }

        MongoCredential credentials = MongoCredential.createScramSha1Credential(MONGDB_USER, "admin", MONGDB_PASSWORD.toCharArray());
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        credentialsList.add(credentials);
        MongoClient client = null;
        if (queueWorking.size() == 0) {
            for (int i = 0; i < 3; i++) {
                client = new MongoClient(seeds, credentialsList);
                queueWorking.add(client);
            }
        }
        client = queueWorking.poll();
        return client;
    }

    public synchronized static void insert(String database, String collectionName, Document doc) {
        MongoClient mongoClient = MongoDbUtils.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        MongoCollection<Document> collection = db.getCollection(collectionName);
        // 将文档插入文档集合
        collection.insertOne(doc);
        queueWorking.add(mongoClient);
    }

    /**
     * 删除
     *
     * @param database
     * @param collectionName
     * @param filter
     * @throws
     * @Description:
     */
    public synchronized static void delete(String database, String collectionName, Bson filter) {
        MongoClient mongoClient = MongoDbUtils.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        MongoCollection<Document> collection = db.getCollection(collectionName);
        collection.deleteMany(filter);
        queueWorking.add(mongoClient);
    }

    /**
     * 更改范例
     *
     * @param database
     * @param collectionName
     * @param oldLog
     * @param newLog
     * @throws
     * @Description:
     */
    public synchronized static void findOneAndUpdate(String database, String collectionName, Bson filter, Document newLog) {
        MongoClient mongoClient = MongoDbUtils.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(database);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        FindIterable<Document> iterable = collection.find(filter);
        MongoCursor<Document> cursor = iterable.iterator();
        boolean hasOld = false;
        while (cursor.hasNext()) {
            Document user = cursor.next();
            System.out.println(user.toString());
            hasOld = true;
        }
        if (!hasOld) {
            collection.insertOne(newLog);
        } else {

            collection.updateMany(filter, new Document("$set", newLog));
        }
        queueWorking.add(mongoClient);
    }

    public static List<Document> find(String database, String collectionName, Bson filters) {
        MongoClient mongoClient = MongoDbUtils.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(database);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        FindIterable<Document> iterable = collection.find(filters).sort(Filters.eq("createTime", 1));
        MongoCursor<Document> cursor = iterable.iterator();
        List<Document> list = new ArrayList<Document>();
        while (cursor.hasNext()) {
            Document user = cursor.next();
            System.out.println(user.toString());
            list.add(user);
        }
        queueWorking.add(mongoClient);
        return list;
    }

    /**
     * 范例
     *
     * @param args
     * @throws
     * @Description:
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String uuid = "234";
        Document document = new Document("uuid", uuid);
        document.append("userId", 10000).append("ct", "ios").append("moduleId", 1).
                append("cid", 212).append("createTime", time);

        MongoDbUtils.group("userlogs", "t_module_click_collect", document);
    }

    /**
     * 范例
     *
     * @param args
     * @throws
     * @Description:
     */
    public static void group(String database, String collectionName, Bson filters) {
        MongoClient mongoClient = MongoDbUtils.getMongoClient();
        MongoDatabase db = mongoClient.getDatabase(database);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        List<Bson> ops = new ArrayList<Bson>();

        DBObject query = new BasicDBObject();
        query.put("cid", "20000");
        //query.put("moduleId","启动APP");
        Bson match = new BasicDBObject("$match", query);
        ops.add(match);
        ops.add(new BasicDBObject("$match", new BasicDBObject("userId", new BasicDBObject("$gt", 0))));
        //ops.add(new BasicDBObject("$match", new BasicDBObject("uuid",new BasicDBObject("$regex", "^\\d{6}+.*ww"))));
        ops.add(new BasicDBObject("$match", new BasicDBObject("createTime", (new BasicDBObject("$lte", "2017-04-05 00:00:01")))));
        //ops.add(new BasicDBObject("$match", new BasicDBObject("createTime",(new BasicDBObject("$gt", "2017-05-01 00:19:00")).append("$lt", "2017-05-21 09:19:00"))));
      /* Bson group = new BasicDBObject("$group", new BasicDBObject("_id",new BasicDBObject("uuid","$uuid").append("moduleId","$moduleId")).append("count", new BasicDBObject("$sum",1)));
        ops.add(group);*/
        ops.add(Document.parse("{ $group: { _id: {uuid:\"$uuid\"}, count: { $sum: 1} } }"));
        ops.add(Document.parse("{ $sort:{count:-1}}"));
        //ops.add(Document.parse("{ $skip:10}"));
        //ops.add(Document.parse("{ $limit:10}"));
        //System.out.println(ops);
        List<Document> results = collection.aggregate(ops).into(new ArrayList<Document>());
        System.out.println(results.size());
                /*for (Document cur : results) {
                    System.out.println(cur.toJson());
                }*/
    }
}
