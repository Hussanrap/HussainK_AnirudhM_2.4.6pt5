import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ReviewRunner {
    public static void main(String[] args) {
        double senti1 = Review.Totalsentiment("SimpleReview.txt");
        double senti2 = Review.Totalsentiment("SimpleReview2.txt");
        double senti3;
        double senti4;
        double senti5;
        double senti6;
        double senti7;
        double senti8;
        double senti9;
        double senti10;
        System.out.println(senti1);
        System.out.print(senti2);
    }
}
