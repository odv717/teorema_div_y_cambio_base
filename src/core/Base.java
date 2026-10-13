package core;
import java.util.ArrayList;

public class Base {


       private static  String [] conversion = {
            "A","B","C","D","E",
            "F","G","H","I","J",
            "K","L","M","N","Ñ",
            "O","P","Q","R","S",
            "T","U","V","W","X",
            "Y","Z","@","#","$",
            "^"
        };
    
    public static String calcularBase(int decimalOriginal, int base, StringBuilder historial){
        //a = bq0 + r0, --> q0 = bq1 + r1, --> q1 = bq2 + r2...
        
        boolean negativo = false;
        int ultimoCociente;
        ArrayList<String> resultados = new ArrayList<>();

        /*if (base == 0 || base == 1 || base == -1 || base > conversion.length + 10){
            return "La base debe ser distinta de -1,0 o 1 con un maximo de " + conversion.length + 10;
        }*/


        if (base <= 1 || base > conversion.length + 10){
            return "La base debe ser un número entre 2 y " + (conversion.length + 10);
        }


        if(decimalOriginal < 0){
            negativo = true;
            ultimoCociente = -decimalOriginal;
        }else{
            ultimoCociente = decimalOriginal;
        }


        historial.append("\n");

        while(ultimoCociente != 0){
            int [] paso = TDiv.calcularDivision( ultimoCociente, base, new StringBuilder());
            historial.append(ultimoCociente +" / " + base + ": cociente: "+ paso[0] + " resto: " + ((String.valueOf(paso[1]).equals(tratarDigitos(paso[1]))  )? paso[1] : paso[1] + " --> " + tratarDigitos(paso[1]))+ "<br>");

            resultados.add(tratarDigitos(paso[1]));
            ultimoCociente = paso[0];

        }

        StringBuilder salida = new StringBuilder();

        if(negativo){
            salida.append("-");
        }

        if(resultados.size() == 0){
            salida.append(0);
        }else{

            for (int i = resultados.size() -1;i>= 0; i--){
                    salida.append(resultados.get(i));
            }

        }



        return decimalOriginal + " en base " + base + " es: " + salida.toString();

    }

    private static String tratarDigitos(int digito){
        //si la base es mayor de 10 los digitos del rango [0,9] no son suficientes
        if(digito >=10)
            return conversion[digito -10];
        else
            return String.valueOf(digito);
    }

}
