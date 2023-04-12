public class ITIStringBuffer {
    
    private LinkedList<String> list;

    public ITIStringBuffer() { 
        list = new LinkedList<String>();
    }

    public ITIStringBuffer(String  firstString){
        list.addFirst(firstString);
    }

    public void append(String nextString){
        list.add(nextString);
    }

    public String toString() {      
        //Get the total length of the strings
        Iterator<String> it = list.iterator();
        int len = 0;
        while (it.hasNext()) {
            len += it.next().length();
        }
        
        //Copy the strings into a char array
        Iterator<String> it1 = list.iterator();
        char[] result = new char[len];
        int index = 0;
        while (it1.hasNext()) {
            String s = it1.next();
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                result[index++] = chars[i];
            }
        }
        return new String(result);
    }
    
}
