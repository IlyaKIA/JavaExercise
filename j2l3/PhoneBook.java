import java.util.ArrayList;

public class PhoneBook {
    public static ArrayList<String[]> phoneBook = new ArrayList<>();

    {
        phoneBook.add(new String[]{"Носков", "8-925-255-55-00"});
        phoneBook.add(new String[]{"Кувырков", "8-925-255-55-01"});
        phoneBook.add(new String[]{"Арбузов", "8-925-255-55-02"});
        phoneBook.add(new String[]{"Светлячков", "8-925-255-55-03"});
        phoneBook.add(new String[]{"Носков", "8-925-255-55-04"});
        phoneBook.add(new String[]{"Кругов", "8-925-255-55-05"});
        phoneBook.add(new String[]{"Круглов", "8-925-255-55-06"});
        phoneBook.add(new String[]{"Крюков", "8-925-255-55-07"});
        phoneBook.add(new String[]{"Кругов", "8-925-255-55-08"});
        phoneBook.add(new String[]{"Носков", "8-925-255-55-09"});
    }

    public void add (String familyName, String phoneNumber){
        phoneBook.add(new String[]{familyName, phoneNumber});
    }

    public String get (String familyName) {
        String phoneList = "";
        for(String[] name : phoneBook){
            if (name[0].equals(familyName)){
                phoneList = phoneList + name[0] + " : " + name[1] + "\n";
            }
        }
        return phoneList;
    }
}
