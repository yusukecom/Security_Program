public class IntMul {
    public static void main(String[] args) {
        // 入力
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        // 掛け算
        int c = a * b;
        /*
         * 出力 intで表現できる範囲：-2,147,483,648～2,147,483,647 それを超えるとエラーになる
         */
        System.out.println(c);
    }
    
}
