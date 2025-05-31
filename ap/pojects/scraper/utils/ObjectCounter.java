package ap.pojects.scraper.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class ObjectCounter {
    private ArrayList<String> keyList;
    private ArrayList<Integer> valueList;

    public ObjectCounter() {
        this.keyList = new ArrayList<>();
        this.valueList = new ArrayList<>();
    }

    public void add(String item){
        boolean find = false;

        for (int i = 0; i < this.keyList.size(); i ++) {
            if (keyList.get(i).equals(item)) {
                this.valueList.set(i, this.valueList.get(i) + 1);
                find = true;
                break;
            }
        }

        if (!find) {
            this.keyList.add(item);
            this.valueList.add(1);
        }

    }

    public ArrayList<String> getTop(int k, ArrayList<String> keys, ArrayList<Integer> values) {

        ArrayList<String> result = new ArrayList<>();
        ArrayList<Boolean> used = new ArrayList<>(Collections.nCopies(keys.size(), false));

    for (int i = 0; i < k; i++) {
        int maxIndex = -1;
        int maxValue = -1;

        for (int j = 0; j < values.size(); j++) {
            if (!used.get(j) && values.get(j) > maxValue) {
                maxValue = values.get(j);
                maxIndex = j;
            }
        }

        if (maxIndex != -1) {
            result.add(keys.get(maxIndex));
            used.set(maxIndex, true);       
        } else {
            break;
        }
    }

    return result;
}

}