package com.tpfinalgrupo9spring.utils;

import com.tpfinalgrupo9spring.exceptions.AliasGenerationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class AliasGen {

    private final RestTemplate restTemplate;
    private final Random random;

    public AliasGen(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.random = new Random();
    }

    public String generateUniqueAlias() throws AliasGenerationException {
        try {
            String[] randomWords = getRandomWordsFromApi();
            String alias = combineWordsWithRandomSeparators(randomWords);

            if (isAliasLengthValid(alias) && isAliasUnique(alias)) {
                return alias;
            } else {
                throw new AliasGenerationException("Alias inválido generado");
            }
        } catch (Exception e) {
            throw new AliasGenerationException("Error al generar el alias: " + e.getMessage());
        }
    }

    private String[] getRandomWordsFromApi() {
        String apiUrl = "https://api.generadordni.es/v2/text/words";
        String[] randomWords = restTemplate.getForObject(apiUrl, String[].class);
        return randomWords != null ? randomWords : new String[0];
    }

    private String combineWordsWithRandomSeparators(String[] words) throws AliasGenerationException {
        try {
            StringBuilder aliasBuilder = new StringBuilder();
            int maxLength = 20;

            for (int i = 0; i < words.length; i++) {
                aliasBuilder.append(words[i]);
                if (i != words.length - 1) {
                    int separatorChoice = random.nextInt(2);
                    if (separatorChoice == 0) {
                        aliasBuilder.append(".");
                    } else {
                        aliasBuilder.append("-");
                    }
                }
            }
            String alias = aliasBuilder.toString();
            if (alias.length() > maxLength) {
                alias = alias.substring(0, maxLength);
            }
            return alias.replaceAll(" ",".");
        } catch (Exception e) {
            throw new AliasGenerationException("Error al combinar palabras para el alias: " + e.getMessage());
        }
    }

    private boolean isAliasUnique(String alias) {
        Set<String> existingAliases = new HashSet<>();
        return !existingAliases.contains(alias);
    }

    private boolean isAliasLengthValid(String alias) {
        return alias.length() >= 6 && alias.length() <= 20;
    }
}