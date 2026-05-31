public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      for(int i = 5; i > 0; i--) {
         for (int j = i - 1; j > 0; j--) {
            System.out.print(" ");
         }
         for (int j = 5 - i + 1; j > 0; j--) {
            System.out.print("*");
         }
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}