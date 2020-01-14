package jd.demo.ds.elasticsearch.transportclient;

import com.alibaba.fastjson.JSON;
import jd.demo.ds.elasticsearch.entity.Song;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;
import java.util.List;

import static jd.demo.ds.elasticsearch.transportclient.SongDataPrepare.*;

/**
 * Created by Hubery on 2020/1/3.
 * ref: https://www.cnblogs.com/chenmz1995/p/10033561.html
 */
public class ElasticSearchClientTest {

    static TransportClient client ;
    static List<Song> songs = JSON.parseArray(SONGS_CONTNET, Song.class);

    @BeforeClass
    public static void beforeAll() throws Exception{
        Settings settings = Settings.builder().build();
        //org.elasticsearch.script.ScriptEngine engine;
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        // on shutdown

        //client.close();
    }


    @AfterClass
    public static void close(){
        if(client != null) client.close();
    }

    @Test
    public void prepareIndex() throws Exception {
        Song song = songs.get(0);
        IndexResponse indexResponse = client.prepareIndex(INDEX, TYPE, song.getId() + "").setSource(JSON.parseObject(JSON.toJSONString(song)), XContentType.JSON).get();
        /**
         * TCP protocol
         * request: Elasticsearch
         *     Token: ES
         *     Message length: 150
         *     Request ID: 5
         *     Status flags: 0x00
         *     Version: 6.1.1 (6010199)
         *     Action:
         *     Data
         *
         * response : Elasticsearch
         *     Token: ES
         *     Message length: 150
         *     Request ID: 5
         *     Status flags: 0x00
         *     Version: 6.1.1 (6010199)
         *     Action: 0x00
         *     Data: 
         */
        System.out.println(indexResponse.status());



        GetResponse getResponse = client.prepareGet(INDEX, TYPE, song.getId() + "").get();
        System.out.println(getResponse.getSource());

        DeleteResponse deleteResponse = client.prepareDelete("twitter", "_doc", "1").get();
        System.out.println(deleteResponse.toString());
        //System.out.println(searchResponse.getProfileResults());

        // restore data
        client.prepareIndex(INDEX, TYPE, song.getId() + "").setSource(JSON.parseObject(JSON.toJSONString(song)), XContentType.JSON).get();

    }

    @Test
    public void deleteByQueryAction(){
        //SongDataPrepare.insert(1,10);
        // Delete By Query API

        BulkByScrollResponse deleteRes = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("name", "I m Like A Bird"))
                .source(INDEX)
                .get();
        System.out.println(deleteRes.getDeleted());
    }

    @Test
    public void multiGetItem (){
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet().add(INDEX, TYPE, "1", "2", "3", "4", "5").get();
        for (MultiGetItemResponse multiGetItemRespons : multiGetItemResponses) {
            GetResponse response = multiGetItemRespons.getResponse();
            if(response.isExists()){
                System.out.printf("%s -> %s\n",multiGetItemRespons.getId(),response.getSourceAsString().trim());
            }
        }
    }
}