import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;

public class Snake extends JPanel implements ActionListener {

    static final int SZEROKOSC_EKRANU = 600;
    static final int WYSOKOSC_EKRANU = 600;
    static final int WIELKOSC_OBIEKTU = 25;
    static final int RZECZY = (SZEROKOSC_EKRANU*WYSOKOSC_EKRANU)/WIELKOSC_OBIEKTU;
    static final int OPOZNIENIE =75;
    final int x[] = new int [RZECZY];
    final int y[] = new int [RZECZY];
    int czesciWeza = 2;
    int zjedzoneJablka;
    int jablkoX;
    int jablkoY;
    char kierunek = 'R';
    boolean dzialaniegry = false;
    Timer czas;
    Random losulosu;

    Snake(){
        losulosu = new Random();
        this.setPreferredSize(new Dimension(SZEROKOSC_EKRANU, WYSOKOSC_EKRANU));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new Sterowanie());
        start();
    }
    public void start(){
        noweJablko();
        dzialaniegry = true;
        czas = new Timer(OPOZNIENIE,this);
        czas.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if(dzialaniegry) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            */
            g.setColor(Color.red);
            g.fillOval(jablkoX, jablkoY, WIELKOSC_OBIEKTU, WIELKOSC_OBIEKTU);

            for (int i = 0; i < czesciWeza; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], WIELKOSC_OBIEKTU, WIELKOSC_OBIEKTU);
                } else {
                    g.setColor(new Color(losulosu.nextInt(255),losulosu.nextInt(255),losulosu.nextInt(255)));
                    g.fillRect(x[i], y[i], WIELKOSC_OBIEKTU, WIELKOSC_OBIEKTU);
                }
            }
            g.setColor(Color.white);
            g.setFont(new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("TWÓJ WYNIK: "+zjedzoneJablka, (SZEROKOSC_EKRANU - metrics.stringWidth("TWÓJ WYNIK: "+zjedzoneJablka))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    public void noweJablko(){
        jablkoX = losulosu.nextInt((int)(SZEROKOSC_EKRANU/WIELKOSC_OBIEKTU))*WIELKOSC_OBIEKTU;
        jablkoY = losulosu.nextInt((int)(WYSOKOSC_EKRANU/WIELKOSC_OBIEKTU))*WIELKOSC_OBIEKTU;
    }
    public void ruch(){
        for(int i = czesciWeza;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (kierunek) {
            case 'U':
                y[0] = y[0] - WIELKOSC_OBIEKTU;
                break;
            case 'D':
                y[0] = y[0] + WIELKOSC_OBIEKTU;
                break;
            case 'L':
                x[0] = x[0] - WIELKOSC_OBIEKTU;
                break;
            case 'R':
                x[0] = x[0] + WIELKOSC_OBIEKTU;
                break;
        }

    }
    //jedzenie
    public void jablkoSprawdz() {
        if((x[0] == jablkoX) && (y[0] == jablkoY)) {
            czesciWeza++;
            zjedzoneJablka++;
            noweJablko();
        }

    }
    //sprawdzanie kolizji
    public void  kolizjeSprawdz() {
        for(int i = czesciWeza;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                dzialaniegry = false;
            }
        }
        //sprawdzanie czy dotknięto lewej ściany
        if(x[0] < 0) {
            dzialaniegry = false;
        }
        //sprawdzanie czy dotknięto prawej ściany
        if(x[0] > SZEROKOSC_EKRANU) {
            dzialaniegry = false;
        }
        //sprawdzanie czy dotknięto górnej ściany
        if(y[0] < 0) {
            dzialaniegry = false;
        }
        //sprawdzanie czy dotknięto dolnej ściany
        if(y[0] > WYSOKOSC_EKRANU) {
            dzialaniegry = false;
        }

        if(!dzialaniegry) {
            czas.stop();
        }
    }

    public void gameOver(Graphics g) {
        //wynik koncowy
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("WYNIK KONCOWY: "+zjedzoneJablka, (SZEROKOSC_EKRANU - metrics.stringWidth("WYNIK KONCOWY: "+zjedzoneJablka))/2, g.getFont().getSize());
        //koniec gry
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("KONIEC GRY!", (SZEROKOSC_EKRANU - metrics2.stringWidth("KONIEC GRY!"))/2, WYSOKOSC_EKRANU/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(dzialaniegry) {
            ruch();
            jablkoSprawdz();
            kolizjeSprawdz();
        }
        repaint();

    }

    public class Sterowanie extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(kierunek != 'R') {
                        kierunek = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(kierunek != 'L') {
                        kierunek = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(kierunek != 'D') {
                        kierunek = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(kierunek != 'U') {
                        kierunek = 'D';
                    }
                    break;
            }
        }
    }
}
