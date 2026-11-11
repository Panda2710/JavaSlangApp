import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.*;
import java.io.IOException;

public class SlangService {
    private final DictionaryData data;
    private final Random random = new Random();
    private final SlangFileStore store = new SlangFileStore();
    private final List<String> history = new ArrayList<>();
    //constructor
    public SlangService(DictionaryData data) throws IOException {
        this.data = data;
        this.history.addAll(store.readHistory("history.txt"));
    }
    //lay history
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }


    //lay nghia cua slang, k add vao history
    public List<String> getMeanings(String slang) {
        return data.getDictionary().get(slang.trim());
    }

    //tim bang slang
    public List<String> searchBySlang(String key) throws IOException {
        String slang=key.trim();
        if(slang.isEmpty()){
            return null;
        }

        store.appendHistory(slang, "history.txt");
        history.add(slang);
        return data.getDictionary().get(slang);
    }
    //tim bang dinh nghia
    public Map<String, List<String>> searchByDefinition(String key){
        Map<String, List<String>> results= new HashMap<>();
        String query=key.trim().toLowerCase();
        if(query.isEmpty()){
            return results;
        }
        for(Map.Entry<String, List<String>> entry : data.getDictionary().entrySet()){
            for(String meaning : entry.getValue()){
                if(meaning.toLowerCase().contains(query)){
                    results.put(entry.getKey(), entry.getValue());
                    break;
                }
            }
        }
        // data.getDictionary().forEach((ke, meanings) -> {
        // for (String meaning : meanings) {
        //     if (meaning.toLowerCase().contains(query)) {
        //         results.put(ke, meanings);
        //         break; // Thoát vòng lặp inner ngay khi tìm thấy từ đầu tiên khớp
        //     }
        // }
        // });
        return results;
    }
    //them slang 
    public boolean addSlang(String slang,List<String> meanings){
        String key=slang.trim();
        if(key.isEmpty() || meanings.isEmpty()){
            return false;
        }

        List<String> cleanedMeanings = new ArrayList<>();

        for(String meaning : meanings){
            String cleaned = meaning.trim();
            if(cleaned.isEmpty()){
                return false;
            }
            cleanedMeanings.add(cleaned);
        }

        
        //trung thi false
        if(data.getDictionary().containsKey(key)){
            return false;
        }


        data.getDictionary().put(key, cleanedMeanings);
        return true;
    }

    //edit slang

    public boolean editSlang(String slang, List<String> edited){
        String key= slang.trim();

        if(key.isEmpty() || edited.isEmpty()){
            return false;
        }
        if(!data.getDictionary().containsKey(key)){
            return false;
        }

        List<String> cleanedMeanings = new ArrayList<>();
        
        for(String meaning : edited){
            String cleaned = meaning.trim();
            if(cleaned.isEmpty()){
                return false;
            }
            cleanedMeanings.add(cleaned);
        }

        data.getDictionary().put(key, cleanedMeanings);


        return true;
    }

    //delete slang

    public boolean deleteSlang(String slang){
        String key = slang.trim();
        if(!data.getDictionary().containsKey(key)){
            return false;
        }
        data.getDictionary().remove(key);
        return true;
    }

    //random slang

    public String getRandomSlang(){
        if(data.getDictionary().isEmpty()){
            return null;
        }
        List<String> keys= new ArrayList<>(data.getDictionary().keySet());
        int index = random.nextInt(keys.size());
        return keys.get(index);
    }

    //reset dictionary

    public void resetDictionary() throws IOException {
        SlangFileStore store = new SlangFileStore();
        DictionaryData originalData = store.readTextFile("slang.txt");
        data.getDictionary().clear();
        data.getDictionary().putAll(originalData.getDictionary());
    }

    //save dictionary to file

    public void save() throws IOException {
        SlangFileStore store = new SlangFileStore();
        store.saveData(data, "dictionary.dat");
    }

    //them nghia vao slang hoac ghi de slang da co

    public boolean appendMeanings(String slang, List<String> newMeanings){
        String key= slang.trim();
        if(key.isEmpty() || newMeanings.isEmpty()){
            return false;
        }
        List<String> currentMeanings = data.getDictionary().get(key);
        if(currentMeanings==null){
            return false;
        }
        
        List<String> combinedMeanings = new ArrayList<>(currentMeanings);
        for(String meaning : newMeanings){
            String cleaned = meaning.trim();

            if(cleaned.isEmpty()){
                return false;
            }

            if(!combinedMeanings.contains(cleaned)){
                combinedMeanings.add(cleaned);
            }
        }
        data.getDictionary().put(key, combinedMeanings);
        return true;
    }
    //tao quiz

    public QuizQuestion createSlangQuiz(){
        String slang = getRandomSlang();

        if(slang==null){
            return null;
        }
        List<String> meanings = getMeanings(slang);

        String correctAnswer= meanings.get(random.nextInt(meanings.size()));

        Set<String> wrongAnswers= new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for(List<String> definitions : data.getDictionary().values()){
            wrongAnswers.addAll(definitions);
        }
        for(String meaning : meanings){
            wrongAnswers.remove(meaning);
        }

        if(wrongAnswers.size()<3){
            return null;
        }
        List<String> candidates = new ArrayList<>(wrongAnswers);
        Collections.shuffle(candidates,random);

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.addAll(candidates.subList(0,3));
        Collections.shuffle(options,random);

        int correctIndex = options.indexOf(correctAnswer);

        return new QuizQuestion("slang '" + slang+ "' co nghia la gi?", options, correctIndex);

    }

    public QuizQuestion createDefinitionQuiz(){
        String correctSlang = getRandomSlang();

        if(correctSlang==null){
            return null;
        }
        List<String> meanings = getMeanings(correctSlang);

        String definition= meanings.get(random.nextInt(meanings.size()));

        List<String> candidates = new ArrayList<>();

        data.getDictionary().forEach((slang, defs) -> {
            boolean alsoCorrect=false;
            for(String meaning : defs){
                if(meaning.equalsIgnoreCase(definition)){
                    alsoCorrect=true;
                    break;

                }
            }
            if(!alsoCorrect){
                candidates.add(slang);
            }
            


        });
        if(candidates.size()<3){
            return null;
        }

        Collections.shuffle(candidates,random);
        List<String> options = new ArrayList<>();
        options.add(correctSlang);
        options.addAll(candidates.subList(0,3));
        Collections.shuffle(options,random);
        int correctIndex = options.indexOf(correctSlang);

        return new QuizQuestion("slang nao co nghia '" + definition + "' ?", options, correctIndex);

        
    }
}
