package Lab_1;

public class Main {
    public static void main(String[] args) {

        ListString listString_1 = new ListString();
        ListString listString_2 = new ListString();

        System.out.println("Тест метода: char charAt(int index)\n");
        System.out.println("Входные данные: Солнце");
        listString_1.append("Солнце");
        listString_1.append("Солнце");

        System.out.println("Ожидаемый результат:[С,e,л]");
        System.out.println();
        System.out.print(listString_1.charAt(0) + ",");
        System.out.print(listString_1.charAt(5) + ",");
        System.out.print(listString_1.charAt(2) + "\n");
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода:  void setCharAt(int index, char ch)\n");
        System.out.println("Входные данные: Солнце");
        System.out.println("Ожидаемый результат: СоЛнцЕ");
        System.out.println();
        listString_1.setCharAt(2, 'Л');
        listString_1.setCharAt(5, 'Е');
        System.out.println(listString_1);
        listString_1.clear();
        System.out.println("=====".repeat(10));

        /////////////////////////////////////////////////////

        System.out.println("Тест метода: ListString substring(int start, int end)\n");
        System.out.println("Входные данные: Ничего в этой жизни не дается легко.");
        System.out.println("Ожидаемый результат: Ничего дается легко");
        System.out.println();
        listString_1.append("Ничего в этой жизни не дается легко.");
        System.out.print(listString_1.substring(0, 7));
        System.out.print(listString_1.substring(23, 50));
        System.out.println();
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода:  void append(char ch)\n");
        System.out.println("Входные данные: Ничего в этой жизни не дается легко");
        System.out.println("Ожидаемый результат: Ничего в этой жизни не дается легко.!!!!!");
        System.out.println();
        listString_1.append('!');
        listString_1.append('!');
        listString_1.append('!');
        listString_1.append('!');
        listString_1.append('!');
        System.out.println(listString_1);
        listString_1.clear();
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода:  void append(ListString string)\n");
        System.out.println("Входные данные:Троцкий - политическая ");
        System.out.println("Ожидаемый результат: Троцкий - политическая фигура");
        System.out.println();
        listString_1.append("Троцкий - политическая ");
        listString_2.append("фигура");
        listString_1.append(listString_2);
        System.out.println(listString_1);
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода:  void append(String string)\n");
        System.out.println("Входные данные: Троцкий - политическая фигура");
        System.out.println("Ожидаемый результат: Троцкий - политическая фигура планетарного масштаба");
        System.out.println();
        listString_1.append(" планетарного масштаба");
        System.out.println(listString_1);
        listString_1.clear();
        listString_2.clear();
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода: void insert(int index, ListString string)\n");
        System.out.println("Входные данные:Место для . Можно конечно и сюда . А можно и сюда ");
        System.out.println("Ожидаемый результат: Место для вставки. Можно конечно и сюда вставить. А можно и сюда вставить");
        System.out.println();
        listString_1.append("Место для . Можно конечно и сюда . А можно и сюда ");
        listString_2.append("вставки");
        listString_1.insert(10, listString_2);
        listString_2.clear();
        listString_2.append("вставить");
        listString_1.insert(40, listString_2);
        listString_1.insert(65, listString_2);
        System.out.println(listString_1);
        listString_1.clear();
        listString_2.clear();
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода: void insert(int index, String string)\n");
        System.out.println("Входные данные: Строка_1");
        System.out.println("Ожидаемый результат: Строка_1 + Строка_2 = Смысл");
        System.out.println();
        listString_1.append("Строка_1");
        listString_2.append(" + Строка_2");
        listString_2.append(" = Смысл");
        listString_1.append(listString_2);
        System.out.println(listString_1);
        listString_1.clear();
        listString_2.clear();
        System.out.println("=====".repeat(10));

        ///////////////////////////////////////////////////////

        System.out.println("Тест метода:  int length()");
        System.out.println("Входные данные: Тысяча");
        System.out.println("Ожидаемый результат: 6");
        System.out.println();
        listString_1.append("Тысяча");
        System.out.println(listString_1.length());
        System.out.println("=====".repeat(10));


    }
}
