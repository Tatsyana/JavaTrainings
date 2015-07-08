package search_word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tatsyana.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final String FILE_PATH = "Homeworks/src/file.txt";

        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String s;
        String[] words;
        Integer ig;
        Map<String, Integer> map = new HashMap<String, Integer>();
        while ((s = reader.readLine()) != null) {

            words = s.split(" ");

            // необходимо избавитьс€ от знаков препинани€

            for (int i = 0; i < words.length; i++) {

                if (map.get(words[i]) != null) {

                    map.put(words[i], map.get(words[i]) + 1);

                } else {
                    map.put(words[i], 1);
                }
            }



        }
        String str_max = null;
        int max=0;
        int k=0;

        for(String key : map.keySet()){
            k = map.get(key).intValue();
            if(k>max){

                max=k;
                str_max = key;


            }

        }
        System.out.println(str_max + " " + max);


    }
}




