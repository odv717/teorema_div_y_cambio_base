package core;

public class TDiv {
 
    
    public static int[] calcularDivision(int dividendo_a, int divisor_b, StringBuilder historial) {
    
        //a = bq + r y 0 ≤ r < |b|

        //variables
        int resto_r = dividendo_a;
        int cociente_q = 0;
        boolean invertido = false;

        //comprobaciones
        if(divisor_b == 0) {
            return null;
        }

        if(divisor_b < 0){
            invertido = true;
            divisor_b = -divisor_b;
        }

        //cuerpo 

        if(dividendo_a >= 0) {
            //System.out.println("div 1");
            while(resto_r - divisor_b >= 0) {
                //System.out.println("resto_r: " + resto_r + " cociente: " + cociente_q + " while: " + (resto_r - divisor_b));
                historial.append("Cociente: " + cociente_q + " resto: " + resto_r + "<br>");
                resto_r = resto_r - divisor_b;
                cociente_q++;
            }

        } else {
            //System.out.println("div 2");
            while(resto_r < 0) {
                //System.out.println("resto_r: " + resto_r + " cociente: " + cociente_q + " while: " + (resto_r));
                historial.append("Cociente: " + cociente_q + " resto: " + resto_r + "<br>");
                resto_r += divisor_b;
                cociente_q--;
            }
            
        }

        historial.append("cociente: " + cociente_q + " resto:" + resto_r);
        //resultado

        if(invertido){
            cociente_q = - cociente_q;
        }

        return new int[]{cociente_q, resto_r};

    }
}
