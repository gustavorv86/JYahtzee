
package frames;

public class Scores {
    
    private final int[] order;
    
    public Scores(int[] values) {
        int[] copy = new int[values.length];
        System.arraycopy(values, 0, copy, 0, values.length);
        for(int i=0; i<copy.length; i++) {
            for(int j=(i+1); j<copy.length; j++) {
                if(copy[j] < copy[i]) {
                    int tmp = copy[i];
                    copy[i] = copy[j];
                    copy[j] = tmp;
                }
            }
        }
        this.order = copy;
    }
    
    private int numberOf(int eq){
        int acc = 0;
        for(int val : order) {
            if(val == eq) {
                acc += eq;
            }
        }
        return acc;
    }
    
    public int numberOfAces() {
        return numberOf(1);
    }
    
    public int numberOfTwos() {
        return numberOf(2);
    }
    
    public int numberOfThrees() {
        return numberOf(3);
    }
    
    public int numberOfFours() {
        return numberOf(4);
    }
    
    public int numberOfFives() {
        return numberOf(5);
    }
    
    public int numberOfSixes() {
        return numberOf(6);
    }
     
    private int maxCount(){
        int count = 1;
        int partial = 1;
        for(int i=1; i<order.length; i++) {
            if(order[i-1] == order[i]) {
                partial++;
                if(partial > count) {
                    count = partial;
                }
            } else {
                partial = 1;
            }
        }
        return count;
    }
    
    private int sum(){
        int acc = 0;
        for(int val : order) {
            acc += val;
        }
        return acc;
    }
    
    private int kind(int kind) {
        int count = maxCount();
        if(count >= kind) {
            return sum();
        } else {
            return 0;
        }
    }
    
    public int threeOfAKind(){
        return kind(3);
    }
    
    public int fourOfAKind(){
        return kind(4);
    }
    
    public int fullHouse(){
        if(order[0] == order[1] && order[1] == order[2]) {
            if(order[3] == order[4]) {
                return 25;
            } else {
                return 0;
            }
        } else if(order[0] == order[1]) {
            if(order[2] == order[3] && order[3] == order[4]) {
                return 25;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    private boolean binSearch(int value) {
        int start = 0;
        int end = order.length-1;
        while(start <= end) {
            int medium = (start+end)/2;
            if(value == order[medium]) {
                return true;
            }
            if(value < order[medium]) {
                end = medium -1;
            }
            if(order[medium] < value) {
                start = medium +1;
            }
        }
        return false;
    }
    
    public int sequenceOf4(){
        
        boolean one   = binSearch(1);
        boolean two   = binSearch(2);
        boolean three = binSearch(3);
        boolean four  = binSearch(4);
        boolean five  = binSearch(5);
        boolean six   = binSearch(6);
        
        if(one && two && three && four) {
            return 30;
        }
        if(two && three && four && five) {
            return 30;
        }
        if(three && four && five && six) {
            return 30;
        }

        return 0;
    }
    
    public int sequenceOf5(){
        for(int i=1; i<order.length; i++) {
            if(order[i-1]+1 != order[i]) {
                return 0;
            }
        }
        return 40;
    }
    
    public int yahtzee(){
        int count = maxCount();
        if(count >= 5) {
            return 50;
        } else {
            return 0;
        }
    }
    
    public int chance(){
        return sum();
    }
}
