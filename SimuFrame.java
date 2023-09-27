import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimuFrame extends JFrame {
    public JPanel simuPanel;
    int[] jet = new int[2];
    int jetAcc;

    // 0-x 1-y 2-r
    ArrayList<int[]> soundWaves;

    int jetVelo;
    int soundVelo;

    int waveCode;

    SimuFrame(int jetVelo, int soundVelo, int jetAcc) {
        simuPanel = new SimuPanel();
        jet[0] = 800;
        jet[1] = 300;
        soundWaves = new ArrayList<int[]>();

        waveCode = 0;

        this.jetVelo = jetVelo;
        this.soundVelo = soundVelo;
        this.jetAcc = jetAcc;

        this.setTitle("Hypersonic Simulation");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setContentPane(simuPanel);
    }

    public void update() {
        for (int i = 0; i < soundWaves.size(); i++) {
            soundWaves.get(i)[2] += soundVelo;
        }

        jet[0] -= jetVelo;

        soundWaves.add(new int[] { jet[0], jet[1], 0, waveCode++ });
        if (soundWaves.size() > 999) {
            soundWaves.remove(0);
        }

        jetVelo += jetAcc;
        simuPanel.repaint();
    }

    public class SimuPanel extends JPanel {
        Color[] colors;
        JCheckBox focusBox;

        SimuPanel() {
            colors = new Color[] { Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.ORANGE, Color.MAGENTA };
            focusBox = new JCheckBox("focused");

            this.add(focusBox);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int[] wave;
            int heightHalf = this.getHeight() / 2;
            int widthHalf = this.getWidth() / 2;
            if (focusBox.isSelected()) {

                for (int i = 0; i < soundWaves.size(); i++) {
                    wave = soundWaves.get(i);
                    // g.setColor(colors[wave[3]%colors.length]);
                    g.setColor(new Color(10 * wave[3] & 255, 10 * wave[3] & 255, 10 * wave[3] & 255));
                    g.drawOval(wave[0] - wave[2] - jet[0] + widthHalf, wave[1] - wave[2] - jet[1] + heightHalf,
                            2 * wave[2],
                            2 * wave[2]);
                }

                g.setColor(Color.RED);
                g.fillOval(widthHalf - 3, heightHalf - 3, 6, 6);
            } else {
                int detected = 0;
                int detectX = widthHalf;
                int detectY = heightHalf + 300;
                for (int i = 0; i < soundWaves.size(); i++) {
                    wave = soundWaves.get(i);
                    // g.setColor(colors[wave[3]%colors.length]);
                    g.setColor(new Color(10 * wave[3] & 255, 10 * wave[3] & 255, 10 * wave[3] & 255));
                    g.drawOval(wave[0] - wave[2], wave[1] - wave[2], 2 * wave[2], 2 * wave[2]);

                    if (Math.abs(Math.pow(wave[0] - detectX, 2) + Math.pow(wave[1] - detectY, 2) - Math.pow(wave[2], 2)) < 10000) {
                        detected += 1;
                    }

                }

                g.setColor(Color.RED);
                g.fillOval(jet[0] - 3, jet[1] - 3, 6, 6);

                g.setColor(Color.BLUE);
                g.fillOval(detectX - 3, detectY - 3, 6, 6);
                g.drawString(String.valueOf(detected), detectX - 5, detectY - 5);
            }

        }
    }

}
