
public class Main {
    public static void main(String[] args) throws Exception {
        SimuFrame simuFrame = new SimuFrame(2, 1, 0);
        while (true) {
            Thread.sleep(15);
            simuFrame.update();
        }
    }

}
