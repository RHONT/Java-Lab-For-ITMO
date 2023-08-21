package Lab_3;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws ElementNotFoundException {

        MySequance<Integer> mySequance = new MySequance(Integer.class, 5);
        mySequance.insert(1, 1);
        mySequance.insert(1, 1);
        mySequance.insert(1, mySequance.end());
        mySequance.insert(1, mySequance.end());
        mySequance.insert(1, mySequance.end());
        mySequance.printlist();
        MySequance.deleteDublicates(mySequance);
        mySequance.printlist();

        MySequance<String> mySequanceString = new MySequance(String.class, 5);
        mySequanceString.insert("1", 1);
        mySequanceString.insert("1", 1);
        mySequanceString.insert("1", 1);
        mySequanceString.insert("1", 1);
        mySequanceString.printlist();
        MySequance.deleteDublicates(mySequanceString);
        mySequanceString.printlist();

        Rational r1 = new Rational(100, 101);
        Rational r2 = new Rational(-80, 100);
        Rational r3 = new Rational(100, 101);
        Rational r4 = new Rational(-80, 100);
        Rational r5 = new Rational(100, 101);


        System.out.println("--".repeat(20));
        MySequance<Rational> mySequanceRational = new MySequance(Rational.class, 5);
        mySequanceRational.insert(r1, 1);
        mySequanceRational.insert(r2, 1);
        mySequanceRational.insert(r3, 1);
        mySequanceRational.insert(r4, 1);
        mySequanceRational.insert(r5, 1);

        mySequanceRational.printlist();
        MySequance.deleteDublicates(mySequanceRational);
        mySequanceRational.printlist();

    }


}
