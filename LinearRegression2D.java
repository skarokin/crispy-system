public class LinearRegression2D {

    public static void main (String [] args){

        // get amount of x and get x values
        StdOut.println("how many X");
        int amountX = StdIn.readInt();

        double [] x = new double [amountX];

        StdOut.println("please enter x values");

        for (int i = 0; i < x.length; i++){
            x[i] = StdIn.readDouble();
        }
        
        // get amount of y and get y values
        StdOut.println("how many Y");
        int amountY = StdIn.readInt();

        if (amountX != amountY){
            throw new IllegalArgumentException("x not equal to y");
        }

        double [] y = new double [amountY];

        StdOut.println("please enter y values");

        for (int i = 0; i < y.length; i++){
            y[i] = StdIn.readDouble();
        }

        // print C
        StdOut.println("c = ");

        double [][] c = createC(x, amountX);

        for (int i = 0; i < c.length; i++){
            for (int j = 0; j < c[i].length; j++){

                System.out.print("[" + c[i][j] + "]");
            }
        System.out.println();
        }

        // print C transpose
        StdOut.println("cT = ");

        double [][] cT = createCT(c, amountX);

        for (int i = 0; i < cT.length; i++){
            for (int j = 0; j < cT[i].length; j++){

                System.out.print("[" + cT[i][j] + "]");
            }
        System.out.println();
        }

        // print C * CT
        double [][] cTc = createCTC(c, cT);

        StdOut.println("cTc = ");

        for (int i = 0; i < cTc.length; i++){
            for (int j = 0; j < cTc[i].length; j++){

                System.out.print("[" + cTc[i][j] + "]");
            }
        System.out.println();
        }

        // print CT*y
        StdOut.println("(cT)(y) = ");

        double [][] cTxY = CTxY(cT, y);

        for (int i = 0; i < cTxY.length; i++){
            for (int j = 0; j < cTxY[i].length; j++){

                System.out.print("[" + cTxY[i][j] + "]");

            }
        System.out.println();
        }

        // augment CTC with CTxY
        StdOut.println("CTC augmented with cTxY = ");

        double [][] a = CTCaugmentCTxY(cTc, cTxY);

        for (int i = 0; i < a.length; i++){
            for (int j = 0; j < a[i].length; j++){

                System.out.print("[" + a[i][j] + "]");

            }
        System.out.println();
        }

        // Gauss Jordan Elimination to find the solution of [CTC | CTxY]
        StdOut.println("solution for cTc augmented with cTxY");

        double [][] b = GaussJordan.gaussJordan(a);

        for (int i = 0; i < b.length; i++){
            for (int j = 0; j < b[i].length; j++){

                System.out.print("[" + b[i][j] + "]");

            }

        System.out.println();

        }
        
        // print line of best fit using least square approximation
        StdOut.println("the line of best fit with least square approximation is ");

        System.out.print("y = " + b[0][2] + " + " + b[1][2] + "x");

    }

        /*** create C, using values of vector x ***/ 
        public static double [][] createC (double [] x, int amountX){

            double [][] c = new double [amountX][2];

            for (int i = 0; i < c.length; i++){
                for (int j = 0; j < c[i].length; j++){

                    c[i][j] = x[i] * 1.0; 

                    c[i][0] = 1;

                }
            }

            return c;

        }

        /*** create CT, using C ***/
        public static double [][] createCT (double [][] c, int amountX){

            double [][] cT = new double [c[0].length][c.length];

            double [] temp = new double [2 * amountX];

            int index = 0;

            for (int i = 0; i < c.length; i++){
                for (int j = 0; j < c[i].length; j++){

                    temp[index] = c[i][j];
                    index++;

                }
            }

            for (int j = 0; j < c[0].length; j++){
                for (int i = 0; i < c.length; i++){

                    cT[j][i] = c[i][j];

                }
            }

            return cT;
            
        }

        // create CT*C, using CT and C
        public static double [][] createCTC (double [][] c, double [][] cT){

            int row1 = cT.length;
            int col1 = cT[0].length;
            int row2 = c.length;
            int col2 = c[0].length;
            
            double [][] cTc = new double [row1][col2];
            
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col2; j++) {
                    for (int k = 0; k < row2; k++){
                        cTc[i][j] += cT[i][k] * c[k][j];
                    }
                }
            }

            return cTc;

        }

        // Create CT x Y, using CT and Y
        public static double [][] CTxY (double [][] cT, double [] y){

            int row1 = cT.length;
            int col1 = cT[0].length;
            int row2 = y.length;
            int col2 = 1;

            double [][] cTxY = new double [row1][col2];

            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < col2; j++) {
                    for (int k = 0; k < row2; k++){
                        cTxY[i][j] += cT[i][k] * y[k];
                    }
                }
            }

            return cTxY;

        }

        // augment CT with Y
        public static double [][] CTCaugmentCTxY ( double [][] cTc, double [][] cTxY){

            int rows = cTc.length;
            int cols = cTc[0].length + 1;

            double [][] a = new double [rows][cols];

            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    if (j == cols - 1){
                        a[i][j] = cTxY[i][0];
                    } else {
                    a[i][j] = cTc[i][j];
                    }
                }
            }

            return a;

        }

}