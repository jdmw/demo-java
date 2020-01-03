package jd.demo.ds.elasticsearch.transportclient;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.InetAddress;

/**
 * Created by huangxia on 2020/1/3.
 */
public class ElasticSearchClientTest {


    static TransportClient client ;

    static final String INDEX = "music" ;
    static final String TYPE = "songs" ;
    static class Song {
        String name ;
        String year ;
        String lyrics ;

        public Song(String name, String year, String lyrics) {
            this.name = name;
            this.year = year;
            this.lyrics = lyrics;
        }
    }

    @BeforeClass
    public static void beforeAll() throws Exception{
        Settings settings = Settings.builder().build();
        //org.elasticsearch.script.ScriptEngine engine;
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host1"), 9300))
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));

// on shutdown

        client.close();
    }


    @AfterClass
    public static void close(){
        if(client != null) client.close();
    }


    @Test
    public void prepareIndex() throws Exception {
        SearchResponse searchResponse = client.prepareSearch(INDEX, TYPE).get();
        System.out.println(searchResponse.getProfileResults());
    }

}