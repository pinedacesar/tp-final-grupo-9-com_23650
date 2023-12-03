package com.tpfinalgrupo9spring.utils;
import org.springframework.web.client.RestTemplate;
public class AliasGen { // Enviado por Matias
//    public String generateUniqueAlias() {
//
//        String[] randomWords = getRandomWordsFromApi();
//
//        String alias = combineWordsWithRandomSeparators(randomWords);
//
//        if (isAliasLengthValid(alias) && isAliasUnique(alias)) {
//            return alias;
//        } else {
//            return "alias_invalido";
//        }
//    }
//    private String[] getRandomWordsFromApi() {
//
//        String apiUrl = "https://api.generadordni.es/v2/text/words";
//        String[] randomWords = restTemplate.getForObject(apiUrl, String[].class);
//        return randomWords != null ? randomWords : new String[0];
//    }
//    private String combineWordsWithRandomSeparators(String[] words) {
//        StringBuilder aliasBuilder = new StringBuilder();
//        int maxLength = 20;
//        int minLength = 6;
//
//        for (int i = 0; i < words.length; i++) {
//            aliasBuilder.append(words[i]);
//            if (i != words.length - 1) {
//                int separatorChoice = random.nextInt(2);
//                if (separatorChoice == 0) {
//                    aliasBuilder.append(".");
//                } else {
//                    aliasBuilder.append("-");
//                }
//            }
//        }
//        String alias = aliasBuilder.toString();
//        if (alias.length() > maxLength) {
//            alias = alias.substring(0, maxLength);
//        }
//        return alias;
//    }
//    private boolean isAliasUnique(String alias) {
//        Set<String> existingAliases = new HashSet<>();
//        return !existingAliases.contains(alias);
//    }
//
//    private boolean isAliasLengthValid(String alias) {
//        return alias.length() >= 6 && alias.length() <= 20;
//    }
}

