package mongodb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * 数据库存储的时间是格林尼治时间，当前时间是东八区，存储的是当前时区时间-8小时
 */
public class MongoDBUtil {

    private static String database = "guanjia_email";

    private static String collectionName = "email_content";

    private static String MONGDB_USER = "ruifu";

    private static String MONGDB_PASSWORD = "ruifu";

    private static String MONGODB_SIT_IP = "192.168.0.106";

    private static String MONGODB_SIT_PORT = "27017";

    private static ThreadLocal<MongoClient> threadLocal = new ThreadLocal<MongoClient>() {
        protected MongoClient initialValue() {
//            return new MongoClient("localhost", 27017);

            MongoCredential credentials = MongoCredential.createScramSha1Credential(MONGDB_USER, "admin", MONGDB_PASSWORD.toCharArray());
            List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
            credentialsList.add(credentials);
            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
            seeds.add(new ServerAddress(MONGODB_SIT_IP, Integer.parseInt(MONGODB_SIT_PORT)));
            MongoClient client = new MongoClient(seeds, credentialsList);

            return client;
        }

        ;
    };

    private static void remove() {
        threadLocal.get().close();
        threadLocal.remove();
    }

    private static void close() {
        threadLocal.get().close();
        threadLocal.remove();
    }

    public static void main(String[] args) throws Exception {

        testInsert();
//       testDelete();
//       testFind();
//       testUpdate();

    }

    private static void testUpdate() {

        BasicDBObject filters = new BasicDBObject();
        //查询
        filters.append("userId", new BasicDBObject("$gte", 46));

        BasicDBObject updateBson = new BasicDBObject();
        //更新字段
        updateBson.append("$set", new BasicDBObject("createTime", new Timestamp(System.currentTimeMillis())));

        UpdateResult result = updateOneOrMany(database, collectionName, filters, updateBson, false);
        System.out.println("更新数量：" + result.getModifiedCount());

    }

    private static void testFind() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Integer> array = new ArrayList<Integer>();
        array.add(51);
        array.add(43);
        array.add(45);

        BasicDBObject queryObject = new BasicDBObject();

        //时间区间查询 记住如果想根据这种形式进行时间的区间查询 ，存储的时候 记得把字段存成字符串，就按yyyy-MM-dd HH:mm:ss 格式来
//        queryObject.put("times", new BasicDBObject("$gte", "2018-06-02 12:20:00").append("$lte","2018-07-04 10:02:46"));

        //or
//        queryObject.append("$or", new BasicDBObject[] {new BasicDBObject("userId", 42), new BasicDBObject("userId", 51)});
        //大于等于
        queryObject.append("userId", new BasicDBObject("$gte", 43));
        //in
//        queryObject.append("userId", new BasicDBObject("$in", array));
        //时间比较
        queryObject.append("createTime", new BasicDBObject("$lte", new Timestamp(System.currentTimeMillis())));
//        queryObject.append("createTime", new BasicDBObject("$lte", new Timestamp(sdf.parse("2019-06-11 15:56:15").getTime())));
        //$regex like模糊,like搜索不支持int类型，仅支持String
//        Pattern pattern = Pattern.compile("^.*4.*$", Pattern.CASE_INSENSITIVE);
//        queryObject.append("userId", new BasicDBObject("$regex", pattern));
        Pattern pattern = Pattern.compile("^.*con.*$", Pattern.CASE_INSENSITIVE);
        queryObject.append("content", new BasicDBObject("$regex", pattern));

        //排序,descending反序,ascending正序
        Bson sort = Sorts.orderBy(Sorts.descending("userId", "createTime"));

        List<Document> list = findMany(database, collectionName, queryObject, sort);
        Document document = null;
        for (int i = 0; i < list.size(); i++) {
            document = list.get(i);

            //取出时间，存的时候是timestamp
            JSONObject jsonObject = JSONObject.parseObject(document.toJson().toString());
            Date date = ((JSONObject) jsonObject.get("createTime")).getDate("$date");

            System.out.println(sdf.format(date) + " --- " + document.getObjectId("_id") + "---" + jsonObject.getLongValue("userId"));

            System.out.println(jsonObject.toJSONString());

        }

        System.out.println(list.size());


    }

    private static void testDelete() {

        Document document = new Document();

        document.append("userId", new Document("$gte", 50));//userId大于等于40

        DeleteResult result = deleteOneOrMany(database, collectionName, document, false);

        System.out.println("删除的条数：" + result.getDeletedCount());

    }

    private static void testInsert() throws InterruptedException {
        for (int i = 0; i < 60; i++) {
            Thread.sleep(500);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Document document = new Document();
            document.append("userId", i).append("createTime", timestamp);//插入
            insertOne(database, collectionName, document);
        }

    }

    //插入一条数据
    public static void insertOne(String database, String collectionName, Document doc) {
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);
        //将文档插入文档集合  
        collection.insertOne(doc);
    }

    //插入多条数据
    public static void insertMany(String database, String collectionName, List<Document> doc) {
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);
        //将文档插入文档集合  
        collection.insertMany(doc);
    }

    //查找第一条
    public static Document findOne(String database, String collectionName, Bson filters, Bson sort) {
        Document document = null;
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);
        //查询出一条数据,排序
        document = collection.find(filters).sort(sort).first();
        return document;
    }

    //查找多条
    public static List<Document> findMany(String database, String collectionName, Bson filters, Bson sort) {
        List<Document> documentList = new ArrayList<Document>();
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);
        //查询出所有数据
        MongoCursor<Document> cursor = collection.find(filters).sort(sort).iterator();

        while (cursor.hasNext()) {
            documentList.add(cursor.next());
        }

        return documentList;
    }

    //更新一条或多条数据
    public static UpdateResult updateOneOrMany(String database, String collectionName, Bson filters, Bson updateBson, boolean updateOne) {
        UpdateResult result = null;
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);  //数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);

        if (updateOne) {
            result = collection.updateOne(filters, updateBson);
        } else {
            result = collection.updateMany(filters, updateBson);
        }
        return result;
    }

    //删除一条或者多条
    public static DeleteResult deleteOneOrMany(String database, String collectionName, Bson filters, boolean deleteOne) {
        DeleteResult result = null;
        //获得MongoDB客户端
        MongoClient mongoClient = threadLocal.get();
        //获取数据库
        MongoDatabase db = mongoClient.getDatabase(database);//数据库名称
        //获取表collection
        MongoCollection<Document> collection = db.getCollection(collectionName);

        if (deleteOne) {
            result = collection.deleteOne(filters);
        } else {
            result = collection.deleteMany(filters);
        }
        return result;
    }

}
