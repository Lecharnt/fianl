package main;
import UIelements.BannanaStore;
import UIelements.Level;
import UIelements.UiImplement;
import UIelements.Upgrades;
import inputs.KeyBoardInputs;
import inputs.MouseInputs;


import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

import static UIelements.Level.fixTextLevel;
import static UIelements.BannanaStore.fixTextBannana;

public class GamePanel extends JPanel{

    private MouseInputs mouseInputs;
    private int xDeltaPlayer = 100, yDeltaPlayer = 100;
    private int monkeyPosX = 10,monkeyPosY = 10;

    private int xDeltaBannana = 50, yDeltaBannana = 50;
    private int bannanaWidth = 28, bannanaHight =60;
    private int playerWidth = 50, playerHeight = 50;
    private List<Banana> bananas = new ArrayList<>();
    private  int xDir = 1, yDir = 1;
    private int frames =0;
    private long lastCheck = 0;
    private long lastCheckBanana = 0;
    private int playerAmountBanana =0;
    private int bannanaSpawnCap =5;
    private boolean bananaSpawn = false;
    private int monkeyTargetX, monkeyTargetY;
    private static final double speed = 2;
    private double t = 0;

    JProgressBar progressBar;
    JPanel panel;
    JButton levelButton = new JButton();
    JButton bananaButton = new JButton();
    JButton moneyButton = new JButton();
    JButton rebirthButton = new JButton();
    JButton ascendButton = new JButton();
    JLabel levelPoints = new JLabel();
    JLabel bananaPointsText = new JLabel();
    Dimension Dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private UiImplement uiImplement;
    private Level level;
    private BannanaStore bannanaStore;

    public GamePanel() {
        monkeyTargetX = xDeltaPlayer;
        monkeyTargetY = yDeltaPlayer;
        uiImplement = new UiImplement();
        level = new Level();
        bannanaStore = new BannanaStore();

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyBoardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        createBananna(Upgrades.spawnAmount);
        setLayout(null);
        progressBar = new JProgressBar();
        progressBar.setBounds((int) Dimension.getWidth()/2-300, 10, 600, 25);
        progressBar.setStringPainted(true);
        progressBar.setString("XP: " + 0+"/"+progressBar.getMaximum());
        add(progressBar);

        level.levelWindow();
        bannanaStore.bannanaWindow();
        uiImplement.mounyWindow();
        uiImplement.rebirthWindow();
        uiImplement.ascendWindow();


        Font font = new Font("Arial", Font.BOLD, 50);


        levelButton.setBackground(Color.LIGHT_GRAY);
        levelButton.setBounds((int) Dimension.getWidth()-100, 0, 100, 100);
        levelButton.setText("L");
        levelButton.setFont(font);
        levelButton.setForeground(Color.GRAY);
        add(levelButton);
        panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBounds((int) Dimension.getWidth()-100, 0, 100, 100);
        add(panel);



        bananaButton.setBackground(Color.LIGHT_GRAY);
        bananaButton.setBounds((int) Dimension.getWidth()-100, 100, 100, 100);
        bananaButton.setText("B");
        bananaButton.setFont(font);
        bananaButton.setForeground(Color.ORANGE);
        add(bananaButton);
        panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setBounds((int) Dimension.getWidth()-100, 100, 100, 100);
        add(panel);


        moneyButton.setBackground(Color.LIGHT_GRAY);
        moneyButton.setBounds((int) Dimension.getWidth()-100, 200, 100, 100);
        moneyButton.setText("M");
        moneyButton.setFont(font);
        moneyButton.setForeground(Color.RED);
        add(moneyButton);
        panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setBounds((int) Dimension.getWidth()-100, 200, 100, 100);
        add(panel);


        rebirthButton.setBackground(Color.LIGHT_GRAY);
        rebirthButton.setBounds((int) Dimension.getWidth()-100, 300, 100, 100);
        rebirthButton.setText("R");
        rebirthButton.setFont(font);
        rebirthButton.setForeground(Color.PINK);
        add(rebirthButton);
        panel = new JPanel();
        panel.setBackground(Color.PINK);
        panel.setBounds((int) Dimension.getWidth()-100, 300, 100, 100);
        add(panel);


        ascendButton.setBackground(Color.LIGHT_GRAY);
        ascendButton.setBounds((int) Dimension.getWidth()-100, 400, 100, 100);
        ascendButton.setText("A");
        ascendButton.setFont(font);
        ascendButton.setForeground(Color.MAGENTA);
        add(ascendButton);
        panel = new JPanel();
        panel.setBackground(Color.MAGENTA);
        panel.setBounds((int) Dimension.getWidth()-100, 400, 100, 100);
        add(panel);

        levelButton.addActionListener(e -> {
            uiImplement.openLevelWindow(level.getLevelWindow(),bannanaStore.getBannanaWindow(),uiImplement.getMounyWindow(),uiImplement.getRebirthWindow(),uiImplement.getAscendWindow());
        });
        bananaButton.addActionListener(e -> {
            uiImplement.openBannanaWindow(level.getLevelWindow(),bannanaStore.getBannanaWindow(),uiImplement.getMounyWindow(),uiImplement.getRebirthWindow(),uiImplement.getAscendWindow());
        });
        moneyButton.addActionListener(e -> {
            uiImplement.openMounyWindow(level.getLevelWindow(),bannanaStore.getBannanaWindow(),uiImplement.getMounyWindow(),uiImplement.getRebirthWindow(),uiImplement.getAscendWindow());
        });
        rebirthButton.addActionListener(e -> {
            uiImplement.openRebirthWindow(level.getLevelWindow(),bannanaStore.getBannanaWindow(),uiImplement.getMounyWindow(),uiImplement.getRebirthWindow(),uiImplement.getAscendWindow());
        });
        ascendButton.addActionListener(e -> {
            uiImplement.openAscendWindow(level.getLevelWindow(),bannanaStore.getBannanaWindow(),uiImplement.getMounyWindow(),uiImplement.getRebirthWindow(),uiImplement.getAscendWindow());
        });
        levelPoints.setFont(font);
        levelPoints.setBounds((int) Dimension.getWidth()-130,0,500,100);
        levelPoints.setText("0");
        levelPoints.setVisible(true);
        add(levelPoints);

        bananaPointsText.setFont(font);
        bananaPointsText.setBounds((int) Dimension.getWidth()-130,100,500,100);
        bananaPointsText.setText("0");
        bananaPointsText.setVisible(true);
        add(bananaPointsText);

        Timer timer = new Timer(50, e -> {
            t += 0.1;
            if (t > 1) {
                t = 0;}
            repaint();});
        timer.start();
    }
    public void addBannanaMounyText(int text){
        bananaPointsText.setText(String.valueOf(text));
        int width = bananaPointsText.getFontMetrics(bananaPointsText.getFont()).stringWidth(bananaPointsText.getText());
        bananaPointsText.setBounds((int) Dimension.getWidth() - 100 - width, 100, 500, 100);
    }
    public void addLevelPointsText(int text) {
        levelPoints.setText(String.valueOf(text));
        int width = levelPoints.getFontMetrics(levelPoints.getFont()).stringWidth(levelPoints.getText());
        levelPoints.setBounds((int) Dimension.getWidth() - 100 - width, 0, 500, 100);
    }

    public void addXp(){
        int XPADD = progressBar.getValue() + Upgrades.xpGainAmount;
        if(XPADD < progressBar.getMaximum()) {
            progressBar.setValue(XPADD);
            progressBar.setString("XP: " + XPADD+"/"+progressBar.getMaximum());
        }
        else {
            while (XPADD >= progressBar.getMaximum()) {
                XPADD -= progressBar.getMaximum();
                System.out.println(XPADD);
            }
            progressBar.setValue(XPADD);
            progressBar.setMaximum(progressBar.getMaximum() + 25);
            progressBar.setString("XP: " + XPADD + "/" + progressBar.getMaximum());
            level.setXpPoints(level.getXpPoints() + 1000);
            addLevelPointsText(level.getXpPoints());
            }
    }
    public void changeXDelta(int value){
        this.xDeltaPlayer += value;
        repaint();
    }
    public void changeYDelta(int value){
        this.yDeltaPlayer += value;
        repaint();
    }

    public void setPos(int x, int y){
        this.xDeltaPlayer = x;
        this.yDeltaPlayer = y;
        repaint();
    }
    public void changeXDeltaBannana(int value){
        this.xDeltaBannana += value;
        repaint();
    }
    public void changeYDeltaBannana(int value){
        this.yDeltaBannana += value;
        repaint();
    }

    private void setPosBannana(int x, int y){
        this.xDeltaBannana = x;
        this.yDeltaBannana = y;
        repaint();
    }
    public void setMonkeyTargetPosition(int x, int y) {
        monkeyTargetX = x;
        monkeyTargetY = y;
        repaint();
    }

    private void createBananna(int amount){
        Random rn = new Random();
        for (int i = 0; i < amount; i++) {
            int x = rn.nextInt( (int) Dimension.getWidth()-150);
            int y = rn.nextInt((int) Dimension.getHeight()-150);
            int z = rn.nextInt(360);
            bananas.add(new Banana(x, y, z,Upgrades.tierRed,Upgrades.tierGreen,Upgrades.tierBlue));
        }
    }//start at 70 every level up you get plus 37


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Banana banana : bananas) {
            Graphics2D bananaG = (Graphics2D) g.create();
            banana.setRotation(banana.getRotation()+0.1);
            bananaG.rotate(Math.toRadians(banana.getRotation()), banana.getX() + bannanaWidth / 2, banana.getY() + bannanaHight / 2);
            bananaG.fillArc(banana.getX(), banana.getY(), bannanaWidth, bannanaHight, 100, 200);
            bananaG.setColor(new Color(banana.getRed(),banana.getGreen(),banana.getBlue()));
            bananaG.fillArc(banana.getX() +2, banana.getY() +5, bannanaWidth-8, bannanaHight-8, 100, 200);
            bananaG.setColor(Color.BLACK);
            bananaG.dispose();
        }
        Graphics2D playerG = (Graphics2D) g.create();
        if ((monkeyPosY-monkeyTargetY >=2 || monkeyPosX-monkeyTargetX >=2) || (monkeyPosY-monkeyTargetY <=-2|| monkeyPosX-monkeyTargetX <=-2)) {
            double angle = Math.atan2(monkeyPosY - monkeyTargetY, monkeyPosX - monkeyTargetX);
            double deltaX = Math.cos(angle) * -speed;
            double deltaY = Math.sin(angle) * -speed;
            monkeyPosX += deltaX;
            monkeyPosY += deltaY;
        }
        int monkeyX = monkeyPosX;
        int monkeyY = monkeyPosY;

        playerG.fillOval(monkeyX - 2, monkeyY - 2, playerWidth + Upgrades.collectSizeAmount + 4, playerHeight + Upgrades.collectSizeAmount + 4);
        playerG.setColor(new Color(200, 150, 0));
        playerG.fillOval(monkeyX, monkeyY, playerWidth + Upgrades.collectSizeAmount, playerHeight + Upgrades.collectSizeAmount);
        playerG.setColor(new Color(150, 100, 0));
        playerG.fillArc(monkeyX, monkeyY, playerWidth + Upgrades.collectSizeAmount, playerHeight + Upgrades.collectSizeAmount, 0, 180);
        playerG.setColor(new Color(200, 150, 0));
        playerG.fillArc(monkeyX + 1, monkeyY + 15, playerWidth + Upgrades.collectSizeAmount - 25, playerHeight + Upgrades.collectSizeAmount - 25, 0, 180);
        playerG.setColor(new Color(200, 150, 0));
        playerG.fillArc(monkeyX + 24, monkeyY + 15, playerWidth + Upgrades.collectSizeAmount - 25, playerHeight + Upgrades.collectSizeAmount - 25, 0, 180);
        playerG.dispose();

        Graphics2D playerHandG = (Graphics2D) g.create();
        Point pointOne = new Point(monkeyX, monkeyY);
        Point pointTwo = new Point(monkeyX - 20, monkeyY - 30);
        Point pointThree = new Point(monkeyX, monkeyY);
        int x = (int) (Math.pow(1 - t, 3) * pointOne.x + 3 * Math.pow(1 - t, 2) * t * pointTwo.x + 3 * (1 - t) * Math.pow(t, 2) * pointThree.x + Math.pow(t, 3) * pointThree.x);
        int y = (int) (Math.pow(1 - t, 3) * pointOne.y + 3 * Math.pow(1 - t, 2) * t * pointTwo.y + 3 * (1 - t) * Math.pow(t, 2) * pointThree.y + Math.pow(t, 3) * pointThree.y);
        playerHandG.setColor(new Color(150, 100, 0));
        playerHandG.fillOval(x - 10, y + 50, playerWidth / 2+5, playerHeight / 2+5);
        playerHandG.setColor(new Color(200, 150, 0));
        playerHandG.fillOval(x - 9, y + 50, playerWidth / 2, playerHeight / 2);
        playerHandG.dispose();



        isColliding();
        frames++;
        double t = frames;

        if (System.currentTimeMillis() - lastCheck >= 1000) {
            lastCheck = System.currentTimeMillis();
            System.out.println("FPS: " + frames);
            frames = 0;

            Banana aged = null;
            for (Banana banana : bananas){
                if (banana.getRed() > 200) {
                    banana.setRed(Math.max(200, banana.getRed() - 10));
                }
                else if (banana.getRed() < 200) {
                    banana.setRed(Math.min(200, banana.getRed() + 10));
                }
                if (banana.getGreen() > 150) {
                    banana.setGreen(Math.max(150, banana.getGreen() - 10));
                }
                else if (banana.getGreen() < 150) {
                    banana.setGreen(Math.min(150, banana.getGreen() + 10));
                }
                if (banana.getBlue() > 0) {
                    banana.setBlue(Math.max(0, banana.getBlue() - 10));
                }
                if (banana.getRed() == 200 && banana.getGreen() == 150 && banana.getBlue() == 0) {
                    aged = banana;
                }
            }
            removeBanana(aged);
        }
        if (bananaSpawn) {
            if (bananas.size()< bannanaSpawnCap+Upgrades.spawnCapAmount) {
                bananaSpawn = false;

                if (System.currentTimeMillis() - lastCheckBanana >= Upgrades.spawnSpeedMultiply) {
                    lastCheckBanana = System.currentTimeMillis();
                    createBananna(Upgrades.spawnAmount);
                    frames = 0;
                    System.out.println(bananas.size());
                }
            }
        }
        if (fixTextLevel) {
            addLevelPointsText(level.getXpPoints());
            fixTextLevel = false;
        }
        if(fixTextBannana){
            addLevelPointsText(level.getXpPoints());
            fixTextLevel = false;

        }
        levelPoints.setText(String.valueOf(level.getXpPoints()));
        repaint();
    }
    public void moveMonkeyToPosition(int x, int y) {

        repaint();
    }

    private void updateRectangle() {
        System.out.println("hihihhi");
        xDeltaPlayer+=xDir;
        if (xDeltaPlayer > 400 || xDeltaPlayer < 0)
            xDir*=-1;
        yDeltaPlayer+= yDir;
        if (yDeltaPlayer > 400 || yDeltaPlayer < 0)
            yDir *= -1;
    }
    private void isColliding(){
        Rectangle p = new Rectangle(xDeltaPlayer,yDeltaPlayer,playerWidth, playerHeight);
        Banana thisCollides = null;
        for(Banana banana : bananas){
            Rectangle r = new Rectangle(banana.getX(),banana.getY(),bannanaWidth,bannanaHight);
            if (p.intersects(r))
            {
                thisCollides = banana;
                playerAmountBanana += 1;
                addXp();
                addBannanaMounyText(playerAmountBanana);
            }
        }
        removeBanana(thisCollides);
    }
    private void removeBanana(Banana banana){
        bananas.remove(banana);
        bananaSpawn = true;
    }

}
