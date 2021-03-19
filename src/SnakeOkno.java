import javax.swing.JFrame;

public class SnakeOkno extends JFrame {

    SnakeOkno(){

        this.add(new Snake());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
