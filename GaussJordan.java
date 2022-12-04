public class GaussJordan{

   public static double[] rowMul(double[] row, double scalar) {
      double [] temp = new double[row.length];
      double [] newrow = new double[row.length];
  
      for (int i = 0; i < row.length; i++) {
          temp[i] = row[i]* scalar;
          newrow[i] = temp[i];
      }
      return newrow;
  }

   public static double[] rowDiv(double[] row, double divisor) {
      double [] temp = new double[row.length];
      double[] newrow = new double[row.length];

      for (int i = 0; i < row.length; i++) {
         temp[i] = (row[i])/divisor;
         newrow[i] = temp[i];
      }
      return newrow;
   }

   public static double[] subtractRow(double[] mat1, double[] mat2) {
      double[] c = new double[mat1.length];
      for (int i = 0; i < mat1.length; i++) {
          c[i] = mat1[i] - mat2[i];
      }
      return c;
  }

  public static boolean checkPivot(double[][] mat, int row) {
    if (Math.abs(mat[row][row]) < 1e-9) {
       return true;
    } else {
       return false;
    }
    }
    // Keeps track of the number of swaps performed to secure a finite solution.
    private static int swapcount = 0;

    // Mind the index value; so row starting from 0 up instead of 1!
    public static double[][] swapRow(double[][] mat, int row) {

        swapcount++;
        if (swapcount >= mat.length - row) {
            System.out.println("no possible combinations.");
            swapcount = 0;
            return mat;
        }

        double[] temp = mat[row];
        for (int i = row; i < mat.length - 1; i++) {
            mat[i] = mat[i + 1];
        }
        mat[mat.length - 1] = temp;
        if (checkPivot(mat, row) == true) {
            mat = swapRow(mat, row);
        }
        swapcount = 0;
        return mat;
}


    // perform the row operations, using swapRow, subtractRow, checkPivot, rowDiv, and rowMult in conditionals
    public static double[][] gaussJordan(double[][] matrix) {
        double[][] mat = matrix;
        int m = mat.length;
        for (int i = 0; i < m; i++) {
            if (checkPivot(mat, i) == true) {
                mat = swapRow(mat, i);
            }
            mat[i] = rowDiv(mat[i], mat[i][i]);
            for (int j = i+1; j < m; j++) {
                if (j == i) {
                    j++;
                } else {
                    mat[j] = subtractRow(mat[j], rowMul(mat[i], mat[j][i] / mat[i][i])); 
                }
            }
            for (int e = m-1; e>= 0; e--) {
                for(int j=i-1; j>=0; j--){
                    if (j == i) {
                    j++;
                } else {
                    mat[j] = subtractRow(mat[j], rowMul(mat[i], mat[j][i] / mat[i][i])); 
                }
                }
            }
        }
        return mat;
        }


    }