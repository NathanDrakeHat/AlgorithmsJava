package multiThread;


import java.util.Arrays;
import java.util.Objects;

public class ParalleledFor extends Thread{
    private final int limit;
    private final int current;
    private final ForRunnable runnable;

    private ParalleledFor(int start, int limit, ForRunnable runnable){
        this.current = start;
        this.limit = limit;
        this.runnable = runnable;
    }

    @Override
    public void run(){
        if(current < limit - 1) {
            var next_thread = new ParalleledFor(current + 1, limit, runnable);
            next_thread.start();
            this.runnable.run(current);
            try{
                next_thread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }else{
            this.runnable.run(current);
        }

    }

    public static void forParallel(int start, int end, ForRunnable runnable){
        Objects.requireNonNull(runnable);
        var task = new ParalleledFor(start,end,runnable);
        task.start();
        try{
            task.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static double[] matrixVector(double[][] A, double[] x){
        Objects.requireNonNull(A);
        Objects.requireNonNull(x);
        if(x.length != A.length){
            throw new IllegalArgumentException("dimension not match,");
        }
        double[] y = new double[A.length];
        forParallel(0,A.length,(index)->y[index] = 0);
        forParallel(0,A.length,(i)->{
            for(int j = 0; j < A.length; j++){
                y[i] = y[i] + A[i][j]*x[j];
            }
        });
        return y;
    }
}
