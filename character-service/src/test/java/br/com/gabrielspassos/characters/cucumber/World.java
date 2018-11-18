package br.com.gabrielspassos.characters.cucumber;

import br.com.gabrielspassos.characters.controllers.dto.CharacterDTO;
import br.com.gabrielspassos.characters.error.SimpleError;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class World {
    public WireMockServer wireMockServer;
    public Map<String, Object> map = Maps.newHashMap();
    public SimpleError simpleError;
    public Integer status;
    public CharacterDTO characterDTO;
}

