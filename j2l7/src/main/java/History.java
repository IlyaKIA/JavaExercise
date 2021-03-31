import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class History {
    private List <String> lastSessionHistory = new ArrayList<>();

    public void writeToFile(String userName, String history) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName = userName + "History.txt";
                File file = new File(fileName);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    for(String s : lastSessionHistory){
                        writer.write(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public List<String> readFromFile (String userName){
        String fileName = userName + "History.txt";
        File file = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String str;
            List<String> history = new ArrayList<>();
            while ((str = reader.readLine()) != null){
                history.add(str);
            }
            if (history.size() <= 100){
                return history;
            } else {
                return history.subList((history.size()-100), history.size());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addToLastSessionHistory(String msg) {
        lastSessionHistory.add(msg);
    }
}
