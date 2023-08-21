package Lab_3;

public class Rational implements Comparable<Rational> {
    private Integer numerator = 0;// числитель
    private Integer denominator = 1;// знаменатель

    public Rational() {

    }

    public Rational(Integer numerator, Integer denominator) {
        // выбрасываем исключение если
        // знаменатель равен нулю
        // если числитель больше знаменателя, значит дробь неправильная. Не работаем с этими случаями
        // так как в задании об этом сказано не было.
        if (denominator == 0 || numerator > denominator || denominator < 0) {
            throw new ArithmeticException();
        }

        // находим наибольший общий делитель, сокращаем дробь
        Integer nodValue = nod(numerator, denominator);
        this.numerator = numerator / nodValue;
        this.denominator = denominator / nodValue;
    }

    // геттер для числителя
    public Integer getNumerator() {
        return numerator;
    }

    // геттер для знаменателя
    public Integer getDenomimator() {
        return denominator;
    }

    public String toString() {
        return this.numerator + "\\" + this.denominator;
    }

    // реализуем метод сравнения
    public int compareTo(Rational val) {
        Integer firstValNumerator = this.getNumerator() * val.getDenomimator();
        Integer secondValNumerator = val.getNumerator() * this.getDenomimator();

        if (firstValNumerator - secondValNumerator == 0) {
            return 0;
        }
        return firstValNumerator > secondValNumerator ? 1 : -1;
    }

    // Величайший общий делитель
    private Integer nod(Integer n, Integer d) {
        Integer temp = 1;
        if (n < d) {
            temp = n;
            n = d;
            d = temp;
        }

        while (temp != 0) {
            temp = n % d;
            n = d;
            d = temp;
        }
        return n;
    }

}
