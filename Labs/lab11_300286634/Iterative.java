public class Iterative {

	public static BitList complement( BitList in ) {
        BitList result = new BitList();
		Iterator inIt = in.iterator();
        Iterator inResult = result.iterator();
        while (inIt.hasNext()){
            int temp = inIt.next();
            if (temp == 0){
                inResult.add(1);
            }
            else{
                inResult.add(0);
            }
        }
        return result;
	}

	public static BitList or( BitList a, BitList b ) {
        BitList result = new BitList();
        Iterator itA = a.iterator();
        Iterator itB = b.iterator();
        Iterator itResult = result.iterator();
        if (!itA.hasNext()){
            throw new IllegalArgumentException("List A is empty");
        }
        if (!itB.hasNext()){
            throw new IllegalArgumentException("List B is empty");
        }
        while (itA.hasNext()){
            if (!itB.hasNext()){
                throw new IllegalArgumentException("List B is shorter than List A");
            }
            int tempA = itA.next();
            int tempB = itB.next();
            if (tempA == 1 || tempB == 1){
                itResult.add(1);
            }
            else{
                itResult.add(0);
            }
        }
        if (itB.hasNext()){
            throw new IllegalArgumentException("List A is shorter than List B");
        }
        return result;

	}

	public static BitList and( BitList a, BitList b ) {
        BitList result = new BitList();
        Iterator itA = a.iterator();
        Iterator itB = b.iterator();
        Iterator itResult = result.iterator();

        if (!itA.hasNext()){
            throw new IllegalArgumentException("List A is empty");
        }
        if (!itB.hasNext()){
            throw new IllegalArgumentException("List B is empty");
        }
        while (itA.hasNext()){
            if (!itB.hasNext()){
                throw new IllegalArgumentException("List B is shorter than List A");
            }
            int tempA = itA.next();
            int tempB = itB.next();
            if (tempA == 1 && tempB == 1){
                itResult.add(1);
            }
            else{
                itResult.add(0);
            }
        }
        if (itB.hasNext()){
            throw new IllegalArgumentException("List A is shorter than List B");
        }
        return result;
	}

	public static BitList xor( BitList a, BitList b ) {
        BitList result = new BitList();
        Iterator itA = a.iterator();
        Iterator itB = b.iterator();
        Iterator itResult = result.iterator();

        if (!itA.hasNext()){
            throw new IllegalArgumentException("List A is empty");
        }
        if (!itB.hasNext()){
            throw new IllegalArgumentException("List B is empty");
        }
        while (itA.hasNext()){
            if (!itB.hasNext()){
                throw new IllegalArgumentException("List B is shorter than List A");
            }
            int tempA = itA.next();
            int tempB = itB.next();
            if (tempA == tempB){
                itResult.add(0);
            }
            else{
                itResult.add(1);
            }
        }
        if (itB.hasNext()){
            throw new IllegalArgumentException("List A is shorter than List B");
        }
        return result;
	}
}