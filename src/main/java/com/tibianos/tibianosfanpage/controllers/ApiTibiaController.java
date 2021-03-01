package com.tibianos.tibianosfanpage.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.tibianos.tibianosfanpage.clients.ApiTibiaClient;
import com.tibianos.tibianosfanpage.models.News;
import com.tibianos.tibianosfanpage.models.responses.ServiceResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTibiaController {

    @Autowired
    ApiTibiaClient restData;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/char/{name}")
    public ServiceResponse findChar(@PathVariable String name){
        try {
            logger.info("Buscando Char: {}",name);
            JsonNode data = restData.getChar(name);
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", data);
        } catch (Exception e) {
            logger.info("Error buscando Char. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }

    @GetMapping("/guilds/{world}")
    public ServiceResponse findGuilds(@PathVariable String world){
        try {
            logger.info("Buscando Guild por mundo: {}",world);
            JsonNode data = restData.getGuilds(world);
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", data);
        } catch (Exception e) {
            logger.info("Error buscando Guilds. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }

    @GetMapping("/guild/{name}")
    public ServiceResponse findGuild(@PathVariable String name){
        try {
            logger.info("Buscando Guild: {}",name);
            JsonNode data = restData.getGuild(name);
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", data);
        } catch (Exception e) {
            logger.info("Error buscando Guild. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }


    @GetMapping("/hs/{mundo}/{categoria}/{voc}")
    public ServiceResponse findHS(@PathVariable String mundo,@PathVariable String categoria,@PathVariable String voc){
        try {
            logger.info("Buscando HighScore Mundo: {},Categoria: {},vocacion: {}",mundo,categoria,voc);
            JsonNode data = restData.getHS(mundo,categoria,voc);
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", data);
        } catch (Exception e) {
            logger.info("Error buscando HighScore. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }

    @GetMapping("/news")
    public ServiceResponse findNews(){
        try {
            List<News> data = restData.getNews();
            List<News> dataFiltered = restData.filterNews(data);
            logger.info("Buscando News");
            JsonNode res = restData.generateJsonNode(dataFiltered);
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", res);
        } catch (Exception e) {
            logger.error("Error buscando News. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }

    @GetMapping("/worlds")
    public ServiceResponse findWorlds(){
        try {
            logger.info("Buscando Mundos");
            JsonNode js = restData.getWorlds();
            logger.info("Respuesta Ok");
            return new ServiceResponse("200", js);
        } catch (Exception e) {
            logger.error("Error buscando Mundos. Error: {}",e.getMessage());
            return new ServiceResponse("404 : Internal Error", null);
        }
    }
    
}
