package Example;

public class Example {
    public static void main(String[] args) {
        sayStuff();
    }

    private static void sayStuff() {
        System.out.println("Stuff");
    }

    private void exceptionExample() {
        try {
            int i = 13;    
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void finallyExceptionExample() {
        int i;
        try {
            i = 15;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            i = 13;
        }
    }

    private void multipleExceptionHandlersExample() {
        try {
            int x = 13;
        } catch(RuntimeException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void whileExample() {
        int x = 10;
        while(x > 0) {
            x--;
        }
    }

    private void forLoopExample() {
        for(int x = 10; x > 0; x--) {

        }
    }

    private void recursiveFunction() {
        recursiveFunction();
    }

    private int getNumber() {
        return 3;
    }

    private void ifStatementExample() {
        int y = getNumber();

        if(y < 3) {
            y += 3;
        }
    }

    private void switchStatementExample() {
        int x = getNumber();

        switch(x) {
            case 1:
                x += 1;
                break;
            case 2:
                x += 2;
                break;
            case 3:
            case 4:
                x += 3;
            default:
                x -= 1;
        }
    }

    private void switchStatementNoDefault() {
        int x = getNumber();

        switch(x) {
            case 0:
                x += 1;
                break;
            case 15:
                x += 15;
                break;
        }
    }

    private void exampleMultiDimesionArray() {
        String[][][][][][] tiles = new String[1][2][3][4][5][10];
    }
}
