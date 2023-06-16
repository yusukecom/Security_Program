import java.math.BigInteger;
public class LCM {
    public static void main(String[] args){

        BigInteger a = new BigInteger(args[0]);
        BigInteger b = new BigInteger(args[1]);
        BigInteger c = a.gcd(b);
        BigInteger d = a.multiply(b);
        BigInteger lcm = d.divide(c);
        
        System.out.println(lcm);
       



            
    }
}
