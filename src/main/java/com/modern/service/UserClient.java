package com.modern.service;

import com.modern.model.ResultDTO;
import com.modern.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public record UserClient(WebClient client) {
    private static final Logger LOG = LoggerFactory.getLogger(UserClient.class);

    public UserClient(@Autowired WebClient client) {
        this.client = client;
    }

    public List<User> getUsers() {
        return getUsers(1);
    }

    public List<User> getUsers(int pageNumber) {
        List<User> result = Collections.emptyList();
        try {
            result = client.get().uri(uriBuilder -> {
                                return uriBuilder.path("/api/users")
                                        .queryParam("page", pageNumber).build();

                            }
                    )
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ResultDTO.class)
                    .map(ResultDTO::getUsers).map(users -> users.stream().map(udt -> new User(udt.getFirstName(), udt.getLastName())).collect(Collectors.toList()))
                    .block();
        } catch (Exception e) {
            LOG.error("Unable to retrieve from remote service", e);
        }
        return result;
    }

}
