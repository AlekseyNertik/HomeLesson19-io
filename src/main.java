import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
    private static FileOutputStream out, out2, out3, out4, out5;
    private static FileInputStream in;
    private static long a;

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        File file1 = new File("data/first.dat");
        File file10 = new File("data/10.txt");
        out = new FileOutputStream("data/first.dat");
        out2 = new FileOutputStream("data/second.dat");
        out3 = new FileOutputStream("data/third.dat");
        out4 = new FileOutputStream("data/fourth.dat");
        out5 = new FileOutputStream("data/fivth.dat");
        in = new FileInputStream("data/first.dat");

        try {
// 1 Блок читаю байловый файл длиной 50 байт и вывести в консоль
            System.out.println("1. прочитать байтовый файл длинной 50 и вывести в консоль");
            byte[] arr = new byte[50]; //массив для записи в файл
            byte[] arr1 = new byte[100]; // массив для чтения из файла
            for (byte i = 0; i < 50; i++) { //заполняю массив для записи его в файл
//                arr[i] = i; можно и так, но плохо визуально контролировать данные из массива
                if (i<10) arr[i]=0; // if  для контроля длинны файла визуально
                if (10<=i & i<20) arr[i]=1; //по цифрам легко понять о целостности чтения-вывода
                if (20<=i & i<30) arr[i]=2;
                if (30<=i & i<40) arr[i]=3;
                if (40<=i & i<50) arr[i]=4;
            }
            out.write(arr);//собственно запись в файл
            out.close(); //закрываю поток в файл
            in.read(arr1); //читаю массив из файла
            long sizeFile = file1.length(); //чтобы не выводить лишнее беру длину файла
            for (int i = 0; i <sizeFile ; i++) { //вывод массива на консоль с ограничением по длине файла
                System.out.print(arr1[i]);
            }
            in.close();

// 2 Блок. Сшиваю данные из 5 файлов. Для визуального контроля целостности данных
System.out.println("\n\n2. Сшить 5 файлов");
            for (int i = 50; i <100 ; i++) { //Дополняю массив до длинны 100
                if (i<60) arr1[i]=5; // цифры задаю для визуального контроля длины
                if (60<=i & i<70) arr1[i]=6;
                if (70<=i & i<80) arr1[i]=7;
                if (80<=i & i<90) arr1[i]=8;
                if (90<=i & i<100) arr1[i]=9;
            }
            out2.write(arr1);//собственно запись в файлы длина 1000 байт
            out3.write(arr1);
            out4.write(arr1);
            out5.write(arr1);
            out2.close();
            out3.close();
            out4.close();
            out5.close();
            ArrayList<InputStream> arr5Files = new ArrayList<>();
            arr5Files.add(new FileInputStream("data/first.dat"));
            arr5Files.add(new FileInputStream("data/second.dat"));
            arr5Files.add(new FileInputStream("data/third.dat"));
            arr5Files.add(new FileInputStream("data/fourth.dat"));
            arr5Files.add(new FileInputStream("data/fivth.dat"));

            SequenceInputStream in5Files = new SequenceInputStream(Collections.enumeration(arr5Files));
        int x;
        int i1=0;
        while ((x = in5Files.read()) != -1) {
            i1++;
            if (i1==101) {System.out.println(); i1=1; } // делаю вывод читабельным хоть немного
            System.out.print(x);
        }
        in5Files.close();

// 3 Блок Читать файл более 10 Мб.

            System.out.println("\n\n3. Читать файл >10Mb постранично. 1 стр=1800 символов.");
            sizeFile = file10.length(); //Уточнаю длину файла для контроля
            System.out.println("Размер файла 10.txt: " + sizeFile + " bytes =  " +
                    sizeFile/1024 + " kb  = " + sizeFile/(1024*1024)+" Mb \nВведите любое число:");
            Scanner scanner = new Scanner(System.in); // ввод с клавы для точного замера времени
            int ask=scanner.nextInt();
            a = System.currentTimeMillis(); //засекаю время
// Обрабатываю весь файл чтением построчно
            FileReader fr = new FileReader(file10);// создаю объект FileReader для объекта File
            BufferedReader reader = new BufferedReader(fr); // создаю BufferedReader от FileReader для построчного считывания
            String line = reader.readLine();//первая строка
            while (line != null) { //если файл не пустой запускаю цикл
                System.out.println(line);
                line = reader.readLine();
            }
            fr.close();
            System.out.println("\nВремя чтения-вывода для всего файла : "+ (System.currentTimeMillis() - a)+"\n");

// Читаю файл посимвольно, по 1800 символов и вывожу на консоль
            final int byteInPage = 1800;//задаю размер страницы в байтах
            int numPage=0;//номер-указатель на номер страницы (положение) в файле - тут не используется
                          // но если потребуется отлиснуть назад страницу, будет использована для пропуска
                          // ненужных страниц
            int currentByte=0;// счетчик байтов для ограничения вывода на консоль.
                            // лучше выводить по линиям, но в задании - побайтово
            a = System.currentTimeMillis(); //снова засекаю время
            System.out.println("\nЧтение-вывод блока 1800 символов : ");
            // создаю поток на чтение файла
            InputStreamReader file101 = new InputStreamReader(new FileInputStream("data/10.txt"), "UTF-8");
            int x1; //Читаю пока не конец файла или 1800 символов
            while ((x1 = file101.read()) != -1 | currentByte==byteInPage) {  //хотя 2 условие - лишнее
                System.out.print((char) x1);
                currentByte++; //счетчик байт
                if (currentByte == byteInPage) { //если счетчик 1800 байт - тормозим до решения юзера
                    System.out.println("\nВремя чтения-вывода для блока 1800 символов : "+ (System.currentTimeMillis() - a));
                    System.out.println("\n              Для вывода следующих 1800 символов введите 1:" +
                                            "\nДля прерывания чтения файла и выхода - любая другая цифра :");
                    ask = scanner.nextInt();
                    if (ask==1) { // если продолжаем читать
                        currentByte=0; // сброс счетчика
                        a = System.currentTimeMillis(); //снова засекаю время
                    }
                    else {   // если прекращаем чтение
                        file101.close();
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
