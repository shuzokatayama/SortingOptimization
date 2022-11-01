import java.util.concurrent.RecursiveAction;
import java.util.Arrays;
import java.util.Random;

class SortingThread extends RecursiveAction{

    int maxSize;
    double[] data;
    int hi;
    int low;

    public SortingThread(int m, double[] d, int h, int l){
        maxSize = m;
        data = d;
        hi = h;
        low = l;
    }

    @Override
    protected void compute() {
    
        // PLAN
        // Base case; if hi - low <= maxsize, compute using Array.sort
        if((hi-low) <= maxSize){
            Arrays.sort(data, low, hi);
        }

        // If not, partition(array, lo, hi, random)
        // then use fork join to create subtasks as recursion for quicksort
        Random rand = new Random();
        int r = rand.nextInt((hi - low) + 1) + low;
        int p = partition(data, low, hi, r);    

        SortingThread left = new SortingThread(maxSize, data, p-1, low);
        SortingThread right = new SortingThread(maxSize, data, hi, p);

        left.fork();
        right.fork();

        left.join();
        right.join();

    }

    public int partition(double[] array, int l, int h, int r){
        swap(array, r, h);
        int i = l;
        int j = h - 1;
        double x = array[h];

        while(i <= j){
            if(array[i] <= x){
                i++;
            }
            else{
                swap(array, i, j);
                j--;
            }
        }
        swap(array, h, j+1);
        return j+1;
    }

    public void swap(double[] array, int i, int j){ //helper method to swap 2 elements in array
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}