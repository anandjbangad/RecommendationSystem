package userlog;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String p = "asddsa,asdd,dsa,asd,asd,das,das,ads,dsa,dsa,dsa,dsa,dsa,dsa,dsa,dsa,dsa";
        String mno[] = p.split(",");
        List<String> m = new ArrayList<String>();
        for (int i=0; i< mno.length; i++){
            m.add(mno[i]);
        }
    }
}
