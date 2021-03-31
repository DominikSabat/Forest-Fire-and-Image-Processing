import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame{
    private DataManager dm;
    private JPanel mainPanel;
    private JPanel buttonPanel1;

    private JCanvasPanel canvasPanel;

    private JButton reverseBut;

    private JButton brighterBut;
    private JButton darkerBut;
    private JButton binarBut;

    private JButton dolnoPrzepustBut;
    private JButton gornoPrzepustBut;
    private JButton gaussBut;

    private JButton dilatationBut;
    private JButton erosionBut;

    private JButton openBut;
    private JButton closeBut;

    private JTextField binariseField;
    private JTextField brightnessField;

    private JButton borderBut1;
    private JButton borderBut2;
    private JButton borderBut3;

    ///////////////////////////////////////
    private JPanel buttonPanel2;

    private JButton generateBut;
    private JButton animateBut;

    private JTextField minField;
    private JTextField maxField;

    public MainWindow (String title) {
        super(title);
        dm = new DataManager();

        //==============================================================================================
        try {
            BufferedImage bg = ImageIO.read(new File("darkbmp.bmp"));
            dm.img = bg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //===============================================================================================
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        reverseBut = new JButton("Przywroc"); reverseBut.setBackground(Color.WHITE);

        brighterBut = new JButton("Rozjasnij"); brighterBut.setBackground(Color.LIGHT_GRAY);
        darkerBut = new JButton("Sciemnij"); darkerBut.setBackground(Color.GRAY);
        binarBut = new JButton("Binaryzuj"); binarBut.setBackground(Color.BLACK); binarBut.setForeground(Color.WHITE);

        dolnoPrzepustBut = new JButton("DolnoPrzepustowy"); dolnoPrzepustBut.setBackground(Color.GREEN);
        gornoPrzepustBut = new JButton("GornoPrzepustowy"); gornoPrzepustBut.setBackground(Color.GREEN);
        gaussBut = new JButton("Gauss"); gaussBut.setBackground(Color.GREEN);

        dilatationBut=new JButton("Dylatacja"); dilatationBut.setBackground(Color.CYAN.darker());
        erosionBut=new JButton("Erozja"); erosionBut.setBackground(Color.ORANGE.darker());

        openBut =new JButton("Morf Otwarcie"); openBut.setBackground(Color.WHITE.brighter());openBut.setForeground(Color.BLACK.darker());
        closeBut =new JButton("Morf Zamkniecie"); closeBut.setBackground(Color.BLACK.darker());closeBut.setForeground(Color.WHITE.brighter());

        binariseField = new JTextField("75");
        binariseField.setBorder(new TitledBorder("Wartosc binaryzacji"));

        brightnessField = new JTextField("50");
        brightnessField.setBorder(new TitledBorder("Zmiana Jasnosci"));

        borderBut1 = new JButton(); borderBut1.setEnabled(false);
        borderBut2 = new JButton(); borderBut2.setEnabled(false);
        borderBut3 = new JButton(); borderBut3.setEnabled(false);

        ////////////////////////////////

        generateBut=new JButton("Generuj Las"); generateBut.setBackground(Color.GREEN.darker().darker());
        animateBut=new JButton("Podpal Las"); animateBut.setBackground(Color.RED.darker());

        minField=new JTextField("30");
        minField.setBorder(new TitledBorder("Minimalna mozliwa wilgoc"));
        maxField=new JTextField("80");
        maxField.setBorder(new TitledBorder("Maksymalna mozliwa wilgoc"));


        buttonPanel1 = new JPanel();
        buttonPanel1.setLayout(new GridLayout(8,2));

        buttonPanel1.add(binariseField);
        buttonPanel1.add(binarBut);

        buttonPanel1.add(brightnessField);
        buttonPanel1.add(brighterBut);
        buttonPanel1.add(borderBut1);
        buttonPanel1.add(darkerBut);

        buttonPanel1.add(dolnoPrzepustBut);

        buttonPanel1.add(gornoPrzepustBut);
        buttonPanel1.add(gaussBut);

        buttonPanel1.add(borderBut2);
        buttonPanel1.add(borderBut3);

        buttonPanel1.add(reverseBut);

        buttonPanel1.add(erosionBut);
        buttonPanel1.add(dilatationBut);

        buttonPanel1.add(openBut);
        buttonPanel1.add(closeBut);


        buttonPanel1.setPreferredSize(new Dimension(300,0));
        /////////////////////////////////////////////////////////////
        buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(2,2));

        buttonPanel2.add(minField);
        buttonPanel2.add(maxField);

        buttonPanel2.add(generateBut);
        buttonPanel2.add(animateBut);


        canvasPanel = new JCanvasPanel(dm);

        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        mainPanel.add(BorderLayout.EAST, buttonPanel1);
        mainPanel.add(BorderLayout.SOUTH,buttonPanel2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);

        binarBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.binarization(Integer.parseInt(binariseField.getText()));
                canvasPanel.repaint();
            }
        });

        brighterBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.makeBrighter(Integer.parseInt(brightnessField.getText()));
                canvasPanel.repaint();
            }
        });

        darkerBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.makeDarker(Integer.parseInt(brightnessField.getText()));
                canvasPanel.repaint();
            }
        });

        dolnoPrzepustBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.lowPass();
                canvasPanel.repaint();
            }
        });

        gornoPrzepustBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.highPass();
                canvasPanel.repaint();
            }
        });

        gaussBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.gaussianFilter();
                canvasPanel.repaint();
            }
        });

        dilatationBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.dilatation();
                canvasPanel.repaint();
            }
        });

        erosionBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.erosion();
                canvasPanel.repaint();
            }
        });

        openBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.erosion();
                util.erosion();

                util.dilatation();

                canvasPanel.repaint();
            }
        });

        closeBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.dilatation();
                util.dilatation();

                util.erosion();

                canvasPanel.repaint();
            }
        });

        reverseBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.reverse();
                canvasPanel.repaint();
            }
        });

        generateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                util.binarization(Integer.parseInt(binariseField.getText()));

                util.dilatation();
                util.dilatation();

                util.erosion();

                util.generateForest(Integer.parseInt(minField.getText()),Integer.parseInt(maxField.getText()));
                canvasPanel.repaint();
            }
        });

        animateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utility util = new Utility(dm);

                int tempMin=Integer.parseInt(minField.getText());
                int tempMax=Integer.parseInt(maxField.getText());

                final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        util.animateFire();

                    }
                }, 0, 60, TimeUnit.MILLISECONDS);



                final ScheduledExecutorService executorService2 = Executors.newSingleThreadScheduledExecutor();
                executorService2.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        canvasPanel.repaint();
                        if(Integer.parseInt(minField.getText())!=tempMin || Integer.parseInt(maxField.getText())!=tempMax)  {

                            executorService.shutdown();
                            executorService2.shutdown();
                        }
                    }
                }, 0, 60, TimeUnit.MILLISECONDS);

            }
        });

    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow("Pozar lasu");
        mw.setVisible(true);
        mw.canvasPanel.repaint();

        Utility util = new Utility(mw.dm);
        util.Initial();
    }
}
