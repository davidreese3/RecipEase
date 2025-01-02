package com.thesis.recipease.util.processer;

import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class LemmaProcessor {
    public String processText(String contents){
        try {
            // Load tokenizer model
            InputStream tokenModelIn = getClass().getResourceAsStream("/models/en-token.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            TokenizerME tokenizer = new TokenizerME(tokenModel);

            // Tokenize the sentence
            String[] tokens = tokenizer.tokenize(contents);

            // Load POS tagging model
            InputStream posModelIn = getClass().getResourceAsStream("/models/en-pos-maxent.bin");

            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel);

            // Get POS tags
            String[] posTags = posTagger.tag(tokens);

            // Load lemmatizer model
            InputStream lemmatizerModelIn = getClass().getResourceAsStream("/models/en-lemmatizer.bin");
            //InputStream lemmatizerModelIn = getClass().getResourceAsStream("/models/en-lemmatizer.bin");

            LemmatizerModel lemmatizerModel = new LemmatizerModel(lemmatizerModelIn);
            LemmatizerME lemmatizer = new LemmatizerME(lemmatizerModel);

            // Perform lemmatization
            String[] lemmas = lemmatizer.lemmatize(tokens, posTags);

            // Return the final lemmatized string
            return String.join(" ", lemmas);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
