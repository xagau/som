package ann.genome;

public class Genome {
    private String sequence;

    public Genome(String sequence) {
        this.sequence = sequence;
    }

    public char get(int index){
        if(index >= 0 && index < sequence.length()) {
            return sequence.charAt(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for sequence of length " + sequence.length());
        }
    }

    public boolean equals(Genome other) {
        return this.sequence.equals(other.sequence);
    }

    public static int checksum(String sequence) {
        int sum = 0;
        for (int i = 0; i < sequence.length(); i++) {
            sum += sequence.charAt(i);
        }
        return sum;
    }
}

