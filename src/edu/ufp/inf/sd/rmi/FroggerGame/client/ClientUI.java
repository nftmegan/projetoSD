package edu.ufp.inf.sd.rmi.FroggerGame.client;

import edu.ufp.inf.sd.rmi.FroggerGame.server.GamePartyRI;
import edu.ufp.inf.sd.rmi.FroggerGame.server.SessionRI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;

/*
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;
*/

public class ClientUI extends JFrame {
    private final CardLayout cl = new CardLayout();

    private static ClientUI frame;

    private final JPanel contentPane;
    private final JPanel panels = new JPanel(cl);
    private final Border border = BorderFactory.createEmptyBorder(200, 200, 200, 200);

    private static FroggerClientImpl froggerClientImpl;

    private static HashMap<Integer, GamePartyRI> gameParties = new HashMap<>();

    private String userName = "unnamed";

    ClientUI() {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setPreferredSize(new Dimension(400, 600));
        setContentPane(contentPane);

        DrawUI();

        cl.show(panels, "Login Panel");
    }

    public static void UpdateUI() {
        frame.DrawUI();

        System.out.println("UPODATING UI");

        frame.cl.show(frame.panels, "Lobby");
    }

    public void DrawUI() {
        contentPane.removeAll();

        LoginPage();
        RegisterPage();
        GameBrowser();
        Lobby();

        contentPane.add(panels);

        contentPane.revalidate();
        contentPane.repaint();
    }

    public void LoginPage () {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBorder(border);
        p.setBackground(Color.DARK_GRAY);
        p.setSize(400,600);//400 width and 500 height

        Font font = new Font("Courier", Font.BOLD,15);
        Font font2 = new Font("Courier", Font.PLAIN,12);

        JLabel l1 = new JLabel("Login");
        l1.setBounds(p.getWidth() / 4,25, p.getWidth() / 2,30);
        l1.setFont(font);
        l1.setForeground(Color.ORANGE);

        JLabel l2 = new JLabel("Username:");
        l2.setBounds(p.getWidth() / 4,75, p.getWidth() / 2,30);
        l2.setFont(font2);
        l2.setForeground(Color.YELLOW);

        JTextField t1 = new JTextField("");
        t1.setBounds(p.getWidth() / 4,100, p.getWidth() / 2,30);
        t1.setFont(font2);

        JLabel l3 = new JLabel("Password:");
        l3.setBounds(p.getWidth() / 4,125, p.getWidth() / 2,30);
        l3.setFont(font2);
        l3.setForeground(Color.YELLOW);

        JTextField t2 = new JTextField("");
        t2.setBounds(p.getWidth() / 4,150, p.getWidth() / 2,30);
        t2.setFont(font2);

        JButton b1 = new JButton("Login");//creating instance of JButton
        b1.setBounds(p.getWidth() / 4,200,p.getWidth() / 2, 30);
        b1.setFont(font2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LOGIN IN");

                froggerClientImpl.loginRequest(t1.getText(), t2.getText());

                if(froggerClientImpl.getSession() != null) {
                    System.out.println("LOGED IN");
                    cl.show(panels, "Game Browser");
                }
                else {
                    System.out.println("FAILED");
                }
            }
        });

        JButton b2 = new JButton("Register");//creating instance of JButton
        b2.setBounds(p.getWidth() - 120,20,100, 30);
        b2.setFont(font2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.next(panels);
            }
        });

        p.add(b1); p.add(b2);
        p.add(t1); p.add(t2);
        p.add(l1); p.add(l2); p.add(l3);

        panels.add(p, "Login Panel");
    }

    public void RegisterPage () {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBorder(border);
        p.setBackground(Color.DARK_GRAY);
        p.setSize(400,600);//400 width and 500 height

        Font font = new Font("Courier", Font.BOLD,15);
        Font font2 = new Font("Courier", Font.PLAIN,12);

        JLabel l1 = new JLabel("Register");
        l1.setBounds(p.getWidth() / 4,25, p.getWidth() / 2,30);
        l1.setFont(font);
        l1.setForeground(Color.ORANGE);

        JLabel l2 = new JLabel("Username:");
        l2.setBounds(p.getWidth() / 4,75, p.getWidth() / 2,30);
        l2.setFont(font2);
        l2.setForeground(Color.YELLOW);

        JTextField t1 = new JTextField("");
        t1.setBounds(p.getWidth() / 4,100, p.getWidth() / 2,30);
        t1.setFont(font2);

        JLabel l3 = new JLabel("Password:");
        l3.setBounds(p.getWidth() / 4,125, p.getWidth() / 2,30);
        l3.setFont(font2);
        l3.setForeground(Color.YELLOW);

        JTextField t2 = new JTextField("");
        t2.setBounds(p.getWidth() / 4,150, p.getWidth() / 2,30);
        t2.setFont(font2);

        JLabel l4 = new JLabel("Repeat password:");
        l4.setBounds(p.getWidth() / 4,175, p.getWidth() / 2,30);
        l4.setFont(font2);
        l4.setForeground(Color.YELLOW);

        JTextField t3 = new JTextField("");
        t3.setBounds(p.getWidth() / 4,200, p.getWidth() / 2,30);
        t3.setFont(font2);

        JButton b1 = new JButton("Register");//creating instance of JButton
        b1.setBounds(p.getWidth() / 4,250,p.getWidth() / 2, 30);
        b1.setFont(font2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("REGISGTERING");

                froggerClientImpl.registerRequest(t1.getText(), t2.getText());
                if(froggerClientImpl.getSession() != null) {
                    System.out.println("REGISTERED AND LOGED IN");
                    userName = t1.getText();

                    DrawUI();

                    cl.show(panels, "Game Browser");
                }
                else {
                    System.out.println("FAILED");
                }
            }
        });

        JButton b2 = new JButton("Login");//creating instance of JButton
        b2.setBounds(p.getWidth() - 120,20,100, 30);
        b2.setFont(font2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.next(panels);
            }
        });

        p.add(b1); p.add(b2);
        p.add(t1); p.add(t2); p.add(t3);
        p.add(l1); p.add(l2); p.add(l3); p.add(l4);

        panels.add(p, "Register Panel");
    }

    public JPanel GameBrowser () {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBorder(border);
        p.setBackground(Color.DARK_GRAY);
        p.setSize(400,600);//400 width and 500 height

        Font font = new Font("Courier", Font.BOLD,15);
        Font font2 = new Font("Courier", Font.PLAIN,12);

        JLabel l1 = new JLabel("GAME BROWSER, " + userName);
        l1.setBounds(p.getWidth() / 4,25, p.getWidth() / 2,30);
        l1.setFont(font);
        l1.setForeground(Color.ORANGE);

        JButton b3 = new JButton("Logout");//creating instance of JButton
        b3.setBounds(p.getWidth() - 120,20,100, 30);
        b3.setFont(font2);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                froggerClientImpl.logoutRequest();
                cl.show(panels, "Login Panel");
            }
        });

        JButton b5 = new JButton("Refresh");//creating instance of JButton
        b5.setBounds(p.getWidth() - 120,70,100, 30);
        b5.setFont(font2);

        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameParties = froggerClientImpl.getGameParties();

                DrawUI();

                cl.show(panels, "Game Browser");
            }
        });

        JButton b2 = new JButton("Create Party");//creating instance of JButton
        b2.setBounds(p.getWidth() / 4,100,p.getWidth() / 2, 30);
        b2.setFont(font2);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Creating PARTY");

                boolean success = froggerClientImpl.createGamePartyRequest();
                if(success) {
                    System.out.println("SUCCESSFULY CREATED A MATCH");
                    cl.show(panels, "Lobby");
                }
                else {
                    System.out.println("CREATING A GAME FAILED");
                }
            }
        });

        int startingY = 150;
        if(gameParties != null) {
            for(int i = 0; i < gameParties.size(); i++) {
                JButton b = new JButton("Join Party ID:" + i + 1);//creating instance of JButton
                b.setBounds(p.getWidth() / 4,startingY + i * 25,p.getWidth() / 2, 30);
                b.setFont(font2);

                int finalI = i;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("JOINING EXISTING GAME");

                        boolean success = false;
                        try {
                            success = froggerClientImpl.joinGamePartyRequest(gameParties.get(finalI+1).getId());
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        if(success) {
                            System.out.println("SUCCESSFULY JOINED A MATCH");
                            //DrawUI();
                            cl.show(panels, "Lobby");
                        }
                        else {
                            System.out.println("JOINING THE GAME FAILED");
                        }
                    }
                });

                p.add(b);
            }
        }

        p.add(l1);
        p.add(b2); p.add(b3); p.add(b5);

        panels.add(p, "Game Browser");

        return p;
    }

    public JPanel Lobby () {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBorder(border);
        p.setBackground(Color.DARK_GRAY);
        p.setSize(400,600);//400 width and 500 height

        Font font = new Font("Courier", Font.BOLD,15);
        Font font2 = new Font("Courier", Font.PLAIN,12);

        JLabel l1 = new JLabel("LOBBY");
        l1.setBounds(p.getWidth() / 4,25, p.getWidth() / 2,30);
        l1.setFont(font);
        l1.setForeground(Color.ORANGE);

        JButton b1 = new JButton("Exit");//creating instance of JButton
        b1.setBounds(p.getWidth() - 120,20,100, 30);
        b1.setFont(font2);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                froggerClientImpl.leaveGamePartyRequest();
                cl.show(panels, "Game Browser");
            }
        });

        int startingY = 150;
        if(froggerClientImpl.getLobbyInfo() != null) {
            for(int i = 0; i < froggerClientImpl.getLobbyInfo().size(); i++) {
                JLabel l = new JLabel(i + 1 + ": " + froggerClientImpl.getLobbyInfo().get(i));//creating instance of JButton
                l.setBounds(p.getWidth() / 4, startingY + i * 25,p.getWidth() / 2, 30);
                l.setFont(font2);
                l.setForeground(Color.YELLOW);

                p.add(l);
            }
        }

        if(froggerClientImpl.isPartyOwnerRequest()) {
            JButton b2 = new JButton("START GAME");//creating instance of JButton
            b2.setBounds(p.getWidth() / 4, p.getHeight() - 55,p.getWidth() / 2, 30);
            b2.setFont(font2);

            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    froggerClientImpl.startGameRequest();
                }
            });

            p.add(b2);
        }

        p.add(l1);
        p.add(b1);

        panels.add(p, "Lobby");

        return p;
    }

    public static void startUI(FroggerClientImpl _froggerClientImpl) {
        froggerClientImpl = _froggerClientImpl;

        frame = null;
        frame = new ClientUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}