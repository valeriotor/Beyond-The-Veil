package com.valeriotor.beyondtheveil.letters;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.valeriotor.beyondtheveil.BeyondTheVeil;
import com.valeriotor.beyondtheveil.dialogue.DialogueTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRegistry {

    private static final Map<String, ExchangeTemplate> EXCHANGES = new HashMap<>();

    public static void registerExchanges() {
        registerExchange("scholar_offer_help");
        registerExchange("keeper_ask_slugs");
        registerExchange("keeper_baptism");
        registerExchange("mauer_ask_thesis");
        registerExchange("west_offer_surgeon");
        registerExchange("another_surgeon");
        registerExchange("more_surgeons");
        registerExchange("ask_weeper");
    }

    private static void registerExchange(String name) {
        try {
            URL resource = BeyondTheVeil.class.getResource("/data/beyondtheveil/letters/%s.json".formatted(name));
            if (resource != null) {
                String file = Resources.toString(resource, Charsets.UTF_8);
                ExchangeTemplate template = BeyondTheVeil.GSON.fromJson(file, ExchangeTemplate.class);
                template.postProcess();
                EXCHANGES.put(name, template);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ExchangeTemplate byName(String name) {
        return EXCHANGES.get(name);
    }


}
