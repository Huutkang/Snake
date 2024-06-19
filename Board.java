import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 1400;
    private final int B_HEIGHT = 860;
    private final int DOT_SIZE = 10;
    private final int BDOT_SIZE = 40;
    private final int ALL_DOTS = 4000;
    private final int DELAY = 40;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private int dots;
    private int diem;
    private int apple_x;
    private int apple_y;
    private int bapple_x = B_WIDTH/2;
    private int bapple_y = B_HEIGHT/2;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean zz=true;
    private boolean l=true,xx=true,t=true,p=true;
    private boolean xuyentuong=true;
    private boolean damvaothanlachet=true;
    private boolean test=true;
    private boolean hien=false;
    private int dem=0;
    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image bapple;
    //  viết comment
    public Board() {
        
        initBoard();
    }
    //Khởi tạo bảng/màn hình game.
    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }
    // Tải hình ảnh.
    private void loadImages() {

        ImageIcon iid = new ImageIcon("than.jpg");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("tao.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("dau.jpg");
        head = iih.getImage();

        ImageIcon iib = new ImageIcon("ba.png");
        bapple = iib.getImage();
    }
    //Tạo táo ngẫu nhiên
    private void locateApple() {

        int RAND_POS1 = (int) (B_WIDTH/DOT_SIZE);
        int r1 = (int) (Math.random() * RAND_POS1);
        apple_x = ((r1 * DOT_SIZE));

        int RAND_POS2 = (int) (B_HEIGHT/DOT_SIZE);
        int r2 = (int) (Math.random() * RAND_POS2);
        apple_y = ((r2 * DOT_SIZE));//r * DOT_SIZE
    }
    private void locateBigApple() {

        int RAND_POS_1 = (int) (B_WIDTH/BDOT_SIZE);
        int r_1 = (int) (Math.random() * RAND_POS_1);
        bapple_x = ((r_1 * BDOT_SIZE));
        

        int RAND_POS_2 = (int) (B_HEIGHT/BDOT_SIZE);
        int r_2 = (int) (Math.random() * RAND_POS_2);
        bapple_y = ((r_2 * BDOT_SIZE));//r * DOT_SIZE
        
    }
    //Khởi đầu game.
    private void initGame() {
        dots = 3;
        diem = 0;
        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }
        
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }
    // viết comment
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    public void tg(){
        if(dem == (3000/DELAY)){
            test=false;
            hien=false;
            dem=0;
            locateBigApple();
        }
    }
    //Hiển thị hình ảnh lên bảng.
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);
            if (test){
                if(((dots - 3) % 5 == 0)&&(dots > 3)){
                    g.drawImage(bapple, bapple_x, bapple_y, this);
                    hien=true;
                    dem++;
                    tg();
                }
            }
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }
            scoreShow(g);

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
            scoreShow(g);
        }        
    }
    // Cài đặt game over.
    private void gameOver(Graphics g) {
        
        String msg = "Game Over";   //Tên hiện ra sau khi thua.
        String re = "Nhấn T để chơi tiếp";
        String e = "Nhấn E để thoát";
        Font small = new Font("Helvetica", Font.BOLD, 14);//Kích cơ font chữ.
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.ORANGE);//Màu của chữ.
        g.setFont(small);//Kích thước chữ.
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);//Vị trí in ra.
        g.drawString(re, (B_WIDTH - metr.stringWidth(msg)-50) / 2, B_HEIGHT / 2+20 + 2 * DOT_SIZE);
        g.drawString(e, (B_WIDTH - metr.stringWidth(msg)-30) / 2, B_HEIGHT / 2+50 + 2 * DOT_SIZE);
    }
    // Hiển thị điểm chơi ra màn hình
    private void scoreShow(Graphics g) {
        
        String scr = "Điểm: " + String.valueOf(diem);
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.CYAN);//Màu của chữ.
        g.setFont(small);//Kích thước chữ.
        g.drawString(scr, (9*B_WIDTH/5 + metr.stringWidth(scr)) / 2, B_HEIGHT / 18);//Vị trí in ra.
    }   
    //Tăng kích cỡ sau khi ăn táo.
    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            test=true;
            dots++;
            diem++;
            locateApple();
        }
        for(int i = 0;i <= BDOT_SIZE/DOT_SIZE; i++)
        {
            for(int j = 0;j <= BDOT_SIZE/DOT_SIZE;j++){
                if ((x[0] == bapple_x + i*DOT_SIZE) && (y[0] == bapple_y + j*DOT_SIZE) && hien){
                    dots++;
                    diem += (5 + Math.random()*5);
                    locateBigApple();
                    hien=false;
                
                
                }
            }


        }

        
    }
    // Mô tả cách di chuyển.
    private void move() { 
        if (zz==true){
            for (int z = dots; z > 0; z--) {
                x[z] = x[(z - 1)];
                y[z] = y[(z - 1)];
            }
        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }

    }
    //  Điều kiện để kết thúc trò chơi. (đâm vào tường và đâm vào thân là chết)
    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if (damvaothanlachet==true){
                if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                    inGame = false;
                }
            }
        }

        if (xuyentuong==false) {
            if (y[0] >= B_HEIGHT) {
                inGame = false;
            }

            if (y[0] < 0) {
                inGame = false;
            }

            if (x[0] >= B_WIDTH) {
                inGame = false;
            }

            if (x[0] < 0) {
                inGame = false;
            }
        }
        else {
            if (y[0] >= B_HEIGHT) {
                y[0] = 0;
            }

            if (y[0] < 0) {
                y[0]=B_HEIGHT;
            }

            if (x[0] >= B_WIDTH) {
                x[0] = 0;
            }

            if (x[0] < 0) {
                x[0]=B_WIDTH;
            }
        }
        
        if (!inGame) {
            timer.stop();
        }
    }
    //  Cách thức game hoạt động.
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            move(); 
            checkApple();
            checkCollision();
            
            
        }

        repaint();
    }
    // tạm dừng
    public void tamdung() {
        rightDirection = false;
        upDirection = false;
        downDirection = false;
        leftDirection = false;
        
    }
    // không đi lùi khi tạm dừng
    public void kdl() {
        if (leftDirection == true){
        p=false;
        xx=true;
        l=true;
        }
        if(rightDirection == true){
            t=false;
            xx=true;
            l=true;
        }
        if(upDirection == true){
            xx=false;
            t=true;
            p=true;
        }
        if(downDirection == true){
            l=false;
            t=true;
            p=true;
        }
    }
    // Thiết kế nút điều khiển.
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            
            int key = e.getKeyCode();
            // Nhấn P để tạm dừng
            if (key == KeyEvent.VK_P) {
                kdl();
                tamdung();
                zz=false;
                }
            if (!inGame) {
                // Nhấn T để chơi lại
                if (key == KeyEvent.VK_T) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                    leftDirection = false;
                    inGame = true;
                    initGame();
                }
                // Nhấn E để thoát
                if (key == KeyEvent.VK_E) {
                    System.exit(0);
                }
            } else {
                // Nhấn E để thoát
                if (key == KeyEvent.VK_E) {
                    System.exit(0);
                }
                // Nhấn trái 
                if (((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) && (!rightDirection)) {
                    if(t==true){
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                    zz=true;
                    xx=true;
                    l=true;
                    }
                }
                // Nhấn phải
                if (((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) && (!leftDirection)) {
                    if (p==true){
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                    zz=true;
                    xx=true;
                    l=true;
                    }
                }
                // Nhấn lên
                if (((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP)) && (!downDirection)) {
                    if (l==true){
                    upDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                    zz=true;
                    t=true;
                    p=true;
                    }
                }
                // Nhấn xuống
                if (((key == KeyEvent.VK_S) || (key == KeyEvent.VK_DOWN)) && (!upDirection)) {
                    if (xx==true){
                    downDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                    zz=true;
                    t=true;
                    p=true;
                    }
                }
            }
        }
    }
}
// Biến zz là dừng phần thân con rắn
// Lưu ý chạy chương trình để bàn phím dạng tiếng anh
//  cài đặt xuyên tường và đâm vào thân chết hay không ở dòng 38 39


