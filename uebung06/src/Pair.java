public class Pair implements Comparable<Integer> {
    int item;
    double value;

    public Pair(int item, int value) {
        this.item = item;
        this.value = value;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Integer integer) {
        return 0;
    }
}
