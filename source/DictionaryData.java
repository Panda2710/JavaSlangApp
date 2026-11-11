import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;


public class DictionaryData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Map<String, List<String>> dictionary;

    public DictionaryData() {
        dictionary = new HashMap<>();
    }

    public Map<String, List<String>> getDictionary() {
        return dictionary;
    }
}