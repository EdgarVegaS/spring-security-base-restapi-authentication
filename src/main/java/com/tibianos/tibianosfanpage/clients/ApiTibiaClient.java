package com.tibianos.tibianosfanpage.clients;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibianos.tibianosfanpage.models.DateFormat;
import com.tibianos.tibianosfanpage.models.News;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static com.tibianos.tibianosfanpage.utils.MethodsUtils.getValueNode;

@Service
@Configurable
public class ApiTibiaClient {
    
    @Value("${tibia.endpoint}")
	private String host;

    @Value("${tibia.char}")
    private String character;
    
    @Value("${tibia.guilds.g-all}")
    private String guilds;
    
    @Value("${tibia.guilds.g-specific}")
    private String guild;
    
    @Value("${tibia.high}")
    private String highscores;
    
    @Value("${tibia.news.lstnw}")
	private String lstnews;

    @Value("${tibia.news.new}")
    private String news;
    
    @Value("${tibia.world.w-all}")
	private String worlds;

    @Autowired
    private RestTemplate restConnection;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public JsonNode getChar(String name) {
        name = name.trim();
        name = name.replaceAll(" ", "+");
        try {
            JsonNode response = restConnection.getForEntity(host+character+name+".json",JsonNode.class).getBody();
            JsonNode data = response.get("characters");
            return data;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public JsonNode getGuilds(String world) {
        world = world.trim();
        try {
            JsonNode response = restConnection.getForEntity(host+guilds+world+".json",JsonNode.class).getBody();
            JsonNode data = response.get("guilds");
            return data;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public JsonNode getWorlds() {
        
        try {
            JsonNode response = restConnection.getForEntity(host+worlds+".json",JsonNode.class).getBody();
            JsonNode data = response.get("worlds");
            return data;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public JsonNode getGuild(String name) {
        name = name.trim();
        name = name.replaceAll(" ", "+");
        try {
            JsonNode response = restConnection.getForEntity(host+guild+name+".json",JsonNode.class).getBody();
            JsonNode data = response.get("guild");
            return data;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public JsonNode getHS(String mundo,String categoria,String voc) {
        if (!categoria.equals("1")) {
            if (!voc.equals("1")) {
                categoria = categoria.trim()+"/";    
            }else{
                categoria = categoria.trim();
                voc = "";
            }
            mundo = mundo.trim()+"/";
        }else{
            categoria = "";
            voc = "";
            mundo = mundo.trim();
        }
        voc = voc.trim();
        try {
            JsonNode response = restConnection.getForEntity(host+highscores+mundo+categoria+voc+".json",JsonNode.class).getBody();
            JsonNode data = response.get("highscores");
            return data;

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<News> getNews(){
        List<News> list = new ArrayList<>();
        try {
            JsonNode response = restConnection.getForEntity(host+lstnews+".json",JsonNode.class).getBody();
            JsonNode data = response.get("newslist");
            for (int i = 0; i < data.get("data").size() ; i++) {
                JsonNode iter = data.get("data").get(i);
                News nw = new News();
                nw.setId(getValueNode(iter, "id"));
                list.add(nw);
            }
            return list;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        return list;
    }

    public List<News> filterNews(List<News> list) {
        List<News> listFilter = new ArrayList<>();
        try {          
            for (int i = 0; i < 5; i++) {
                News n = list.get(i);
                DateFormat dtf = new DateFormat();
                JsonNode response = restConnection.getForEntity(host+news+n.getId()+".json", JsonNode.class).getBody();
                JsonNode data = response.get("news");
                JsonNode date = data.get("date");
                n.setTitle(getValueNode(data,"title"));
                n.setContent(getValueNode(data,"content"));
                dtf.setDate(getValueNode(date,"date"));
                dtf.setTimeZoneType(getValueNode(date,"timezone_type"));
                dtf.setTimeZone(getValueNode(date,"timezone"));
                n.setDate(dtf);
                listFilter.add(n);
            }
            return listFilter;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return listFilter;
        }
    }

    public JsonNode generateJsonNode(List<News> lst){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode arr = mapper.valueToTree(lst);  
            return arr;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
