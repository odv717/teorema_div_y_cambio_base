package revision;
import core.Base;
import core.TDiv;

public class App {
    public static void main(String[] args) throws Exception {
        int dividendo_a = 4;
        int divisor_b =  2;

        int [] divisionSimple= TDiv.calcularDivision(dividendo_a, divisor_b,new StringBuilder());

        
        System.out.println("cociente: " + divisionSimple[0] + " resto: " + divisionSimple[1]);
        if(TDivCheck.check(dividendo_a, divisor_b, divisionSimple[0], divisionSimple[1])) {
            System.out.println("La división es correcta");
        } else {
            System.out.println("La división es incorrecta");
        }

        System.out.println(dividendo_a + " en base " + divisor_b + ": " + Base.calcularBase(dividendo_a, divisor_b,new StringBuilder()));
        
        
    }
}
