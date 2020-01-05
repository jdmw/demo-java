package jd.demo.ds.elasticsearch.transportclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jd.demo.ds.elasticsearch.entity.Song;
import jd.demo.ds.elasticsearch.util.JoddHttpClient;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SongDataPrepare {

    public static final String MUSC_ALBUM_URL = "https://www.kugou.com/song/#hash=D3544418AB747BEA8D23F967300A1600&album_id=21816225";
    public static final String SONGS_CONTNET;
    static {
        try {
            SONGS_CONTNET = new String("[{\"id\":1,\"name\":\"Yummy\",\"artist\":\"Justin Bieber\",\"time\":210},{\"id\":2,\"name\":\"Changes\",\"artist\":\"Lauv\",\"time\":160},{\"id\":3,\"name\":\"Chills\",\"artist\":\"Why Don t We\",\"time\":165},{\"id\":4,\"name\":\"On Somebody\",\"artist\":\"Ava Max\",\"time\":184},{\"id\":5,\"name\":\"Wrong Direction\",\"artist\":\"Hailee Steinfeld\",\"time\":249},{\"id\":6,\"name\":\"Happy\",\"artist\":\"Oh Wonder\",\"time\":172},{\"id\":7,\"name\":\"Sinning With You\",\"artist\":\"Sam Hunt\",\"time\":196},{\"id\":8,\"name\":\"Miss You 2\",\"artist\":\"Gabrielle Aplin,Nina Nesbitt\",\"time\":196},{\"id\":9,\"name\":\"Kiss Somebody (with Seeb)\",\"artist\":\"Julie Bergan,Seeb\",\"time\":138},{\"id\":10,\"name\":\"I m Like A Bird\",\"artist\":\"LVNDSCAPE,John Adams\",\"time\":202},{\"id\":11,\"name\":\"Monkey business (radio edit)\",\"artist\":\"Pet Shop Boys\",\"time\":189},{\"id\":12,\"name\":\"20/20 (Explicit)\",\"artist\":\"Lil Tjay\",\"time\":230},{\"id\":13,\"name\":\"Bad Vibe (Explicit)\",\"artist\":\"Quando Rondo,A Boogie wit da Hoodie,2 Chainz\",\"time\":198},{\"id\":14,\"name\":\"Wolves\",\"artist\":\"Kaaze,Sam Tinnesz,Silverberg\",\"time\":169},{\"id\":15,\"name\":\"Dare Me\",\"artist\":\"NERVO,Plastik Funk,Tim Morrison\",\"time\":182},{\"id\":16,\"name\":\"Not So Bad\",\"artist\":\"Yves V,Ilkay Sencan,Emie\",\"time\":153},{\"id\":17,\"name\":\"I Can Hardly Speak\",\"artist\":\"Bombay Bicycle Club\",\"time\":239},{\"id\":18,\"name\":\"Beach Ballin\",\"artist\":\"Yung Pinch,Blackbear\",\"time\":204},{\"id\":19,\"name\":\"U Played\",\"artist\":\"Moneybagg Yo,Lil Baby\",\"time\":164},{\"id\":20,\"name\":\"Healing\",\"artist\":\"Camden Cox\",\"time\":244},{\"id\":21,\"name\":\"VALENTINO (Remix (Explicit))\",\"artist\":\"24KGoldn,Lil Tjay\",\"time\":199},{\"id\":22,\"name\":\"Obvious\",\"artist\":\"Le Youth,Adam Rom\",\"time\":383},{\"id\":23,\"name\":\"Radical\",\"artist\":\"AMTRAC,Totally Enormous Extinct Dinosaurs\",\"time\":238},{\"id\":24,\"name\":\"Wolf (Explicit)\",\"artist\":\"lil Tr33zy,A Boogie wit da Hoodie\",\"time\":150}]".getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String ES_HOST = "http://localhost:9200" ;
    public static final String INDEX = "music" ;
    public static final String TYPE = "songs" ;



    private static boolean createIndexIfNotExist(){
        JSONObject jsonObject = JSON.parseObject(JoddHttpClient.get(String.format("%s/%s/_mapping",ES_HOST ,INDEX), null));
        Integer status = jsonObject.getInteger("status");
        if( status != null && status == 404){
            String errorType = jsonObject.getJSONObject("error").getString("type");
            if("index_not_found_exception".equals(errorType)){
                // put {{host}}/music
                HttpResponse send = HttpRequest.put(String.format("%s/%s", ES_HOST, INDEX)).send();
                System.out.println("create index /" + INDEX + " :" + send.body());
                return true ;
            }else{
                System.err.println(errorType);
                System.exit(1);
            }
        }
        return false ;
    }

    /**
     *
     * parse kugou music songs list :
     *
     * var getSongs = function(){
     *   var songs = []
     *   for(var n of document.querySelectorAll(".musiclist>li")){
     *     var song = {}
     *     song['id'] = Number.parseInt(n.querySelector(".musiclist-number").innerText)
     *     song['name'] = n.querySelector(".musiclist-songname-txt").innerText
     *     song['artist'] = n.querySelector(".musiclist-artist").innerText
     *     var time = n.querySelector(".musiclist-time").innerText
     *     song['time'] = time.split(':')[0] * 60 + time.split(':')[1] *1
     *     songs.push(song)
     *   }
     *   return songs
     * }
     * @return
     */
    private static List<Song> parseMusicAlbum(){
        List<Song> list = new ArrayList<>();
        String html = JoddHttpClient.get(MUSC_ALBUM_URL, null);
        Document doc = Jsoup.parse(html);
        doc.select(".musiclist>li").forEach(n->{
            Song song = new Song();
            song.setId(Integer.parseInt(n.select(".musiclist-number").first().text()));
            song.setName(n.select(".musiclist-songname-txt").first().text());
            song.setArtist(n.select(".musiclist-artist").first().text());
            String time = n.select(".musiclist-tim").first().text();
            song.setTime(Integer.parseInt(time.split("\\:")[0]) * 60 + Integer.parseInt(time.split("\\:")[1]) );
            list.add(song);
        });
        return list ;
    }


    public static int insert(int index, int count){
        List<Song> songs = JSON.parseArray(SONGS_CONTNET,Song.class);//parseMusicAlbum();
        int resultCount = 0 ;
        for(int i=index;i<songs.size();i++){
            if(count > 0  && i > index + count) break;
            Song song = songs.get(i);
            JoddHttpClient.putJson(String.format("%s/%s/%s/%d",ES_HOST,INDEX,TYPE,song.getId()),song);
            resultCount++ ;
        }

        String searchResult = JoddHttpClient.get(String.format("%s/%s/_search", ES_HOST, INDEX));
        System.out.println(searchResult);
        return resultCount ;
    }

    public static void main(String[] args) {
        if(createIndexIfNotExist()){
            HttpRequest.delete(String.format("%s/%s/",ES_HOST,INDEX)).send();
            createIndexIfNotExist();
        }
       /* List<Song> songs = JSON.parseArray(SONGS_CONTNET,Song.class);//parseMusicAlbum();
        songs.forEach(song->JoddHttpClient.putJson(String.format("%s/%s/%s/%d",ES_HOST,INDEX,TYPE,song.getId()),song));
        String searchResult = JoddHttpClient.get(String.format("%s/%s/_search", ES_HOST, INDEX));
        System.out.println(searchResult);*/
    }
}
