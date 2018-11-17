package br.com.gabrielspassos.users.cucumber;

import br.com.gabrielspassos.users.controllers.dto.ResponseUserDTO;
import br.com.gabrielspassos.users.error.SimpleError;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class World {
    public Map<String, Object> map = Maps.newHashMap();
    public SimpleError simpleError;
    public Integer status;
    public ResponseUserDTO responseUserDTO;
}
