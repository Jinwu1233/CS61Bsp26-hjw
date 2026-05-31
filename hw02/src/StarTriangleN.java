public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      for(int i = N; i > 0; i--) {
         for (int j = i - 1; j > 0; j--) {
            System.out.print(" ");
         }
         for (int j = N - i + 1; j > 0; j--) {
            System.out.print("*");
         }
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}