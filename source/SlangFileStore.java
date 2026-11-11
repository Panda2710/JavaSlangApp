
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.*;


public class SlangFileStore {

    //doc file txt vao DictionaryData
    public DictionaryData readTextFile(String filePath) throws IOException{
        DictionaryData data = new DictionaryData();

        Map<String, List<String>> dictionary = data.getDictionary();

        try ( BufferedReader reader= Files.newBufferedReader(Paths.get(filePath),StandardCharsets.UTF_8)){
            reader.readLine(); // Bỏ qua dòng đầu tiên
            String line;
            int linenum=1;
            while((line=reader.readLine())!=null){
                linenum++;
                if(line.isBlank()){
                    continue;
                }
                String[] parts=line.split("`", 2);
                if(parts.length!=2){
                    throw new IOException("Invalid format at line " + linenum);
                }
                String slang = parts[0].trim();
                String[] definitions= parts[1].split("\\|",-1);
                List<String> meanings = new ArrayList<>();
                for(String definition : definitions){
                    String meaning = definition.trim();
                    if(meaning.isEmpty()){
                        throw new IOException("line " + linenum + " has an empty definition");
                    }
                    meanings.add(meaning);
                }
                if(slang.isEmpty()){
                    throw new IOException("line " + linenum + " has an empty slang");
                }

                if(dictionary.containsKey(slang)){
                    dictionary.get(slang).addAll(meanings);
                }else{
                    dictionary.put(slang, meanings);
                }
                
            }
            return data;
        }
    }
    //luu data vao file .dat

    public void saveData(DictionaryData data, String filePath) throws IOException{
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath))){
            output.writeObject(data);
        }
    }
    
    //doc data tu file .dat
    public DictionaryData loadData(String filePath) throws IOException, ClassNotFoundException{
        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))){
            return (DictionaryData) input.readObject();
        }
    }
    //doc hoac tao file .dat neu chua ton tai
    public DictionaryData loadOrCreateData() throws IOException, ClassNotFoundException{
       if(Files.exists(Paths.get("dictionary.dat"))){
        return loadData("dictionary.dat");
       }
       DictionaryData data = readTextFile("slang.txt");
       saveData(data, "dictionary.dat");
        return data;
    }

    //doc history

    public List<String> readHistory(String filePath) throws IOException{
        List<String> history = new ArrayList<>();
        if(!Files.exists(Paths.get(filePath))){
            return history;
        }
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(filePath),StandardCharsets.UTF_8)){
            String line;
            while((line=reader.readLine())!=null){
                String slang = line.trim();
                if(!slang.isEmpty()){
                    history.add(slang);
                }
            }
        }
        return history;
    }

    //ghi them history 

    public void appendHistory(String slang, String filePath) throws IOException{

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            writer.write(slang);
            writer.newLine();
        }
    }

}

