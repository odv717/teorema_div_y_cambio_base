package revision;
import java.util.List;
import java.util.Map;

import core.Base;

public class BaseCheck {

    public static void main(String[] args) {
        check();
    }

    public static void  check(){

        Map<Integer, List<TestCase>> casos = crearCasosDePrueba();

        for (Map.Entry<Integer, List<TestCase>> entrada : casos.entrySet()) {

            int base = entrada.getKey();
            List<TestCase> tests = entrada.getValue();

            System.out.println("=== BASE " + base + " ===");

            for (TestCase test : tests) {

                String resultado = Base.calcularBase(test.decimal(), base, new StringBuilder());

                boolean correcto = resultado.equals(test.esperado());

                System.out.printf("%d -> %s %s%n",test.decimal(),resultado,correcto ? "OK" : "ERROR (esperado " + test.esperado() + ")");
            }

        }

    }


    record TestCase(int decimal, String esperado) {}

    public static Map<Integer, List<TestCase>> crearCasosDePrueba() {
        return Map.of(
            2, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "10"),
                new TestCase(3, "11"),
                new TestCase(4, "100"),
                new TestCase(5, "101"),
                new TestCase(8, "1000"),
                new TestCase(10, "1010"),
                new TestCase(15, "1111"),
                new TestCase(16, "10000"),
                new TestCase(31, "11111"),
                new TestCase(32, "100000"),
                new TestCase(64, "1000000"),
                new TestCase(100, "1100100"),
                new TestCase(255, "11111111"),
                new TestCase(256, "100000000")
            ),

            3, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "10"),
                new TestCase(4, "11"),
                new TestCase(5, "12"),
                new TestCase(8, "22"),
                new TestCase(10, "101"),
                new TestCase(15, "120"),
                new TestCase(16, "121"),
                new TestCase(31, "1011"),
                new TestCase(32, "1012"),
                new TestCase(64, "2101"),
                new TestCase(100, "10201"),
                new TestCase(255, "100110"),
                new TestCase(256, "100111")
            ),

            4, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "3"),
                new TestCase(4, "10"),
                new TestCase(5, "11"),
                new TestCase(8, "20"),
                new TestCase(10, "22"),
                new TestCase(15, "33"),
                new TestCase(16, "100"),
                new TestCase(31, "133"),
                new TestCase(32, "200"),
                new TestCase(64, "1000"),
                new TestCase(100, "1210"),
                new TestCase(255, "3333"),
                new TestCase(256, "10000")
            ),

            5, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "3"),
                new TestCase(4, "4"),
                new TestCase(5, "10"),
                new TestCase(8, "13"),
                new TestCase(10, "20"),
                new TestCase(15, "30"),
                new TestCase(16, "31"),
                new TestCase(31, "111"),
                new TestCase(32, "112"),
                new TestCase(64, "224"),
                new TestCase(100, "400"),
                new TestCase(255, "2010"),
                new TestCase(256, "2011")
            ),

            8, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "3"),
                new TestCase(4, "4"),
                new TestCase(5, "5"),
                new TestCase(8, "10"),
                new TestCase(10, "12"),
                new TestCase(15, "17"),
                new TestCase(16, "20"),
                new TestCase(31, "37"),
                new TestCase(32, "40"),
                new TestCase(64, "100"),
                new TestCase(100, "144"),
                new TestCase(255, "377"),
                new TestCase(256, "400")
            ),

            10, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "3"),
                new TestCase(4, "4"),
                new TestCase(5, "5"),
                new TestCase(8, "8"),
                new TestCase(10, "10"),
                new TestCase(15, "15"),
                new TestCase(16, "16"),
                new TestCase(31, "31"),
                new TestCase(32, "32"),
                new TestCase(64, "64"),
                new TestCase(100, "100"),
                new TestCase(255, "255"),
                new TestCase(256, "256")
            ),

            16, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "2"),
                new TestCase(3, "3"),
                new TestCase(4, "4"),
                new TestCase(5, "5"),
                new TestCase(8, "8"),
                new TestCase(10, "A"),
                new TestCase(15, "F"),
                new TestCase(16, "10"),
                new TestCase(31, "1F"),
                new TestCase(32, "20"),
                new TestCase(64, "40"),
                new TestCase(100, "64"),
                new TestCase(255, "FF"),
                new TestCase(256, "100")
            ),

            -2, List.of(
                new TestCase(0, "0"),
                new TestCase(1, "1"),
                new TestCase(2, "110"),
                new TestCase(3, "111"),
                new TestCase(4, "100"),
                new TestCase(5, "101"),
                new TestCase(6, "11010"),
                new TestCase(7, "11011"),
                new TestCase(8, "11000"),
                new TestCase(9, "11001"),
                new TestCase(10, "11110"),
                new TestCase(15, "10011"),
                new TestCase(16, "10000"),
                new TestCase(31, "100011"),
                new TestCase(32, "100000"),
                new TestCase(64, "1000000"),
                new TestCase(100, "110100"),
                new TestCase(255, "11000001"),
                new TestCase(256, "11000000")
            )
        );
    }
}