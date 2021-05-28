package com.dsosedov.springldap.controllers;

import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    private StringRedisTemplate template;

    public RedisController(StringRedisTemplate template) {
        this.template = template;
    }

    @PostMapping
    public void post(@RequestParam String value) {
        template.execute((RedisCallback<Boolean>) connection -> ((StringRedisConnection) connection).set("foo", value));
    }

    @GetMapping
    public String get() {
        return template.execute((RedisCallback<String>) connection -> ((StringRedisConnection) connection).get("foo"));
    }

}
