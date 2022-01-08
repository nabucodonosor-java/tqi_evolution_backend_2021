package com.tqi.banktqi.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.tqi.banktqi.model.Cliente;
import com.tqi.banktqi.repositories.ClienteRepository;

/**
 * A classe <b>JwtTokenEnchancer</b> Componente que verifica pelo email se existe o cliente e retorna as credenciais de acesso
 * @author Franco Canizo Brasil Sobrinho
 * @since jan 2022
 * @version 1.0
 */

@Component
public class JwtTokenEnchancer implements TokenEnhancer {

    @Autowired
    private ClienteRepository repository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Cliente cliente = repository.findByEmail(authentication.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("userId", cliente.getId());
        map.put("userName", cliente.getNome());
        map.put("userEmail", cliente.getEmail());

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(map);

        return accessToken;
    }

}
