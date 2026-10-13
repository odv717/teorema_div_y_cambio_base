package revision;
public class TDivCheck {

    public static boolean  check(int dividendo_a, int divisor_b,int cociente_q, int resto_r){
        
        //revisa que la solucion generada cumpla con a = bq + r 
        int dividendoOriginal = divisor_b * cociente_q + resto_r;
        //System.out.println("d" + divisor_b + " c "+ cociente_q +" r "+ resto_r);
        return dividendoOriginal == dividendo_a && resto_r >= 0; 

    }

}
