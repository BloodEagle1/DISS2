package com.company;

import org.jfree.chart.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GUI implements ISimDelegat {
    private JButton btnStart;
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JTextField jtfPocetMinibusov;
    private JTextField jtfPocetPracovnikov;
    private JTextField jtfPocetReplikacii;
    private JLabel jlRepStatPriemCasVSysteme;
    private JLabel jlRepStatPriemRadObsluhy;
    private JLabel jlRepStatPriemCasVRadeNaObsluhu;
    private JLabel jlRepStatPriemObsadenostPracovnikov;
    private JPanel jpReplikacie;
    private JPanel jpReplikacia;
    private JPanel jpZavislostMinibusov;
    private JPanel jpZavislostPracovnikov;
    private JLabel jlVelkostRaduTerminal1;
    private JLabel jlVelkostRaduTerminal2;
    private JLabel jlVelkostRaduPozicovna;
    private JLabel jlVolnyPracovnici;
    private JLabel jlPocetZakVstup;
    private JLabel jlVybavenyZak;
    private JButton btnStartReplikacia;
    private JLabel jlStatPriemCasVSysteme;
    private JLabel jlStatPriemRadObsluha;
    private JLabel jlStatPriemCasVRadeObsluha;
    private JLabel jlStatPriemObsadenostPrac;
    private JTable jtMinibusy;
    private JLabel jlStatPriemRadTerminal1;
    private JLabel jlStatPriemRadTerminal2;
    private JLabel jlRepStatPriemRadTerminal1;
    private JLabel jlRepStatPriemRadTerminal2;
    private JSlider sliderRychlost;
    private JSlider sliderFrekvencia;
    private JLabel jlIntervalSpolahlivosti;
    private JLabel jlRepStatPriemCasVRadeTerminal1;
    private JLabel jlRepStatPriemCasVRadeTerminal2;
    private JLabel jlStatPriemCasVRadeTerminal1;
    private JLabel jlStatPriemCasVRadeTerminal2;
    private JLabel jlCas;
    private JButton btnPauza;
    private JFreeChart lineChart;
    private XYSeries series;
    private JFreeChart lineChart2;
    private XYSeries series2;
    private double spodna1;
    private double spodna2;
    private double horna1;
    private double horna2;
    private DefaultTableModel tableModel;
    private SimpleDateFormat df;
    private Calendar cal;
    private Pozicovna pozicovna;
    private boolean zastavenie = false;

    public GUI() {
        series = new XYSeries("zavislostMinibusy");
        series2 = new XYSeries("zavislostPracovnici");
        this.spodna1 = Double.MAX_VALUE;
        this.spodna2 = Double.MAX_VALUE;
        this.horna1 = Double.MIN_VALUE;
        this.horna2 = Double.MIN_VALUE;
        String col[] = {"Minibus", "Predosla zastavka", "Aktualna zastavka", "Pocet cestujucich"};
        tableModel = new DefaultTableModel(col, 0);
        jtMinibusy.setModel(tableModel);



        jpZavislostMinibusov.setLayout(new java.awt.BorderLayout());
        XYSeriesCollection dataset = new XYSeriesCollection();
        lineChart = ChartFactory.createXYLineChart("zavislost minibusov", "pocet minibusov", "priemerny cas v systeme", dataset);
        dataset.addSeries(series);
        ChartPanel CP = new ChartPanel(lineChart);
        CP.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                if (!jtfPocetPracovnikov.getText().equals("") && !jtfPocetReplikacii.getText().equals("")) {
                    series.clear();
                    for (int i = 2; i < 20; i++) {
                        Pozicovna pozicovna = new Pozicovna((double) (Integer.parseInt(jtfPocetReplikacii.getText())) * 31 * 24 * 60 * 60, Integer.parseInt(jtfPocetPracovnikov.getText()), i + 1);
                        pozicovna.registrujDelegata(GUI.this);
                        pozicovna.spust();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Nespravne parametre");
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {

            }

        });
        jpZavislostMinibusov.add(CP, BorderLayout.CENTER);
        jpZavislostMinibusov.validate();


        jpZavislostPracovnikov.setLayout(new java.awt.BorderLayout());
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        lineChart2 = ChartFactory.createXYLineChart("zavislost pracovnikov", "pocet pracovnikov", "priemerny cas v systeme", dataset1);
        dataset1.addSeries(series2);
        ChartPanel CP1 = new ChartPanel(lineChart2);
        CP1.addChartMouseListener(new ChartMouseListener() {

            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                if (!jtfPocetMinibusov.getText().equals("") && !jtfPocetReplikacii.getText().equals("")) {
                    series2.clear();
                    for (int i = 3; i < 20; i++) {
                        Pozicovna pozicovna = new Pozicovna((double) (Integer.parseInt(jtfPocetReplikacii.getText())) * 31 * 24 * 60 * 60, i + 1, Integer.parseInt(jtfPocetMinibusov.getText()));
                        pozicovna.registrujDelegata(GUI.this);
                        pozicovna.spust();
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Nespravne parametre");
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {

            }
        });
        jpZavislostPracovnikov.add(CP1, BorderLayout.CENTER);
        jpZavislostPracovnikov.validate();

        btnStart.addActionListener(e -> {
            if (!jtfPocetMinibusov.getText().equals("") && !jtfPocetPracovnikov.getText().equals("") && !jtfPocetReplikacii.getText().equals("")) {
                int pocetMinibusov = Integer.parseInt(jtfPocetMinibusov.getText());
                int pocetPracovnikov = Integer.parseInt(jtfPocetPracovnikov.getText());
                int pocetReplikacii = Integer.parseInt(jtfPocetReplikacii.getText());

                jlRepStatPriemCasVRadeNaObsluhu.setText("");
                jlRepStatPriemRadObsluhy.setText("");
                jlRepStatPriemCasVSysteme.setText("");
                jlRepStatPriemObsadenostPracovnikov.setText("");
                jlRepStatPriemRadTerminal1.setText("");
                jlRepStatPriemRadTerminal2.setText("");
                jlIntervalSpolahlivosti.setText("< , >");
                jlRepStatPriemCasVRadeTerminal1.setText("");
                jlRepStatPriemCasVRadeTerminal2.setText("");

//                for (int i = 16; i < 21; i++) {
//                    for (int j = 5; j < 21; j++) {
//                        Pozicovna pozicovna = new Pozicovna((double) pocetReplikacii * 31 * 24 * 60 * 60, i, j);
//                        pozicovna.registrujDelegata(this);
//                        pozicovna.spust();
//                    }
//                }

                Pozicovna pozicovna = new Pozicovna((double) pocetReplikacii * 31 * 24 * 60 * 60, pocetPracovnikov, pocetMinibusov);
                pozicovna.registrujDelegata(this);
                pozicovna.spust();
            } else {
                JOptionPane.showMessageDialog(panel, "Nespravne parametre");
            }
        });
        btnStartReplikacia.addActionListener(e -> {
            if (!jtfPocetMinibusov.getText().equals("") && !jtfPocetPracovnikov.getText().equals("")) {
                int pocetMinibusov = Integer.parseInt(jtfPocetMinibusov.getText());
                int pocetPracovnikov = Integer.parseInt(jtfPocetPracovnikov.getText());

                jlVelkostRaduTerminal1.setText("");
                jlVelkostRaduTerminal2.setText("");
                jlVelkostRaduPozicovna.setText("");
                jlVolnyPracovnici.setText("");
                jlPocetZakVstup.setText("");
                jlVybavenyZak.setText("");
                jlStatPriemCasVSysteme.setText("");
                jlStatPriemRadObsluha.setText("");
                jlStatPriemCasVRadeObsluha.setText("");
                jlStatPriemObsadenostPrac.setText("");
                jlStatPriemCasVRadeTerminal1.setText("");
                jlStatPriemCasVRadeTerminal2.setText("");


                tableModel = new DefaultTableModel(col, 0);
                jtMinibusy.setModel(tableModel);

                for (int i = 0; i < pocetMinibusov; i++) {
                    Object[] data = {"Minibus" + i, "", ""};
                    tableModel.addRow(data);
                }
                try {
                    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cal = Calendar.getInstance();
                    String myTime = "0000-00-00 00:00:00";
                    Date d = df.parse(myTime);
                    cal.setTime(d);
                    String newTime = df.format(cal.getTime());
                    jlCas.setText(newTime);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }


                sliderRychlost.setValue(1000);
                sliderFrekvencia.setValue(1);
                pozicovna = new Pozicovna(31 * 24 * 60 * 60, pocetPracovnikov, pocetMinibusov);

                pozicovna.registrujDelegata(this);
                pozicovna.spust();
            } else {
                JOptionPane.showMessageDialog(panel, "Nespravne parametre");
            }
        });
        btnPauza.addActionListener(e -> {
            if (!zastavenie){
                zastavenie = true;
                pozicovna.zastav();
                btnPauza.setText("pokracuj");
            }else {
                zastavenie = false;
                pozicovna.pokracuj();
                btnPauza.setText("pauza");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1100, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void vypisVysledkyReplikacii(Pozicovna pozicovna) {
        double priemCasVSysteme = pozicovna.repStatCasuVSysteme();
        jlRepStatPriemCasVSysteme.setText((Math.round(priemCasVSysteme * 10000d) / 10000d) + " min");
        jlRepStatPriemRadObsluhy.setText((Math.round(pozicovna.repStatZakVRadeNaObsluhu() * 10000d) / 10000d) + "");
        jlRepStatPriemCasVRadeNaObsluhu.setText((Math.round(pozicovna.repStatCasVRadeObsluha() * 10000d) / 10000d) + " min");
        jlRepStatPriemObsadenostPracovnikov.setText((Math.round(pozicovna.repStatObsadenostPrac() * 10000d) / 10000d) + "");
        jlRepStatPriemRadTerminal1.setText((Math.round(pozicovna.repStatZakVRadeTerminal1() * 10000d) / 10000d) + "");
        jlRepStatPriemRadTerminal2.setText((Math.round(pozicovna.repStatZakVRadeTerminal2() * 10000d) / 10000d) + "");
        jlIntervalSpolahlivosti.setText(pozicovna.vypocitajInterval());
        jlRepStatPriemCasVRadeTerminal1.setText((Math.round(pozicovna.repStatCasVRadeTerminal1() * 10000d) / 10000d) + " min");
        jlRepStatPriemCasVRadeTerminal2.setText((Math.round(pozicovna.repStatCasVRadeTerminal2() * 10000d) / 10000d) + " min");
    }

    @Override
    public void refresh(SimJadro sim) {
        Pozicovna pozicovna = (Pozicovna) sim;
        JPanel activePanel = (JPanel) tabbedPane1.getSelectedComponent();
        if (activePanel == jpReplikacia) {
            pozicovna.setRychlost((double) sliderRychlost.getValue());
            pozicovna.setFrekvencia((double) sliderFrekvencia.getValue());
            vypisVysledkyVCase(pozicovna);
        } else if (activePanel == jpReplikacie) {
            vypisVysledkyReplikacii(pozicovna);
        } else if (activePanel == jpZavislostMinibusov) {
            vykresliGraf(pozicovna.getMinibusy(), pozicovna.repStatCasuVSysteme());
        } else if (activePanel == jpZavislostPracovnikov) {
            vykresliGraf2(pozicovna.getPracovnici(), pozicovna.repStatCasuVSysteme());
        }

    }

    private void vypisVysledkyVCase(Pozicovna pozicovna) {
        jlVelkostRaduTerminal1.setText(pozicovna.getPriorFrontZakTerminal1().size() + "");
        jlVelkostRaduTerminal2.setText(pozicovna.getPriorFrontZakTerminal2().size() + "");
        jlVelkostRaduPozicovna.setText(pozicovna.getPriorFrontZakPozicovna().size() + "");
        jlVolnyPracovnici.setText(pozicovna.getVolnyPracovnici() + "");
        jlPocetZakVstup.setText(pozicovna.getZakVstup() + "");
        jlVybavenyZak.setText(pozicovna.getZakSystemu() + "");
        jlStatPriemCasVSysteme.setText((Math.round(pozicovna.statCasuVSysteme() * 10000d) / 10000d) + " min");
        jlStatPriemRadObsluha.setText((Math.round(pozicovna.statZakVRadeObsluha() * 10000d) / 10000d) + "");
        jlStatPriemCasVRadeObsluha.setText((Math.round(pozicovna.statCasuVRadeObsluha() * 10000d) / 10000d) + " min");
        jlStatPriemObsadenostPrac.setText((Math.round(pozicovna.statObsadenostPrac() * 10000d) / 10000d) + "");
        jlStatPriemRadTerminal1.setText((Math.round(pozicovna.statZakVRadeTerminal1() * 10000d) / 10000d) + "");
        jlStatPriemRadTerminal2.setText((Math.round(pozicovna.statZakVRadeTerminal2() * 10000d) / 10000d) + "");
        jlStatPriemCasVRadeTerminal1.setText((Math.round(pozicovna.statCasuVRadeTerminal1() * 10000d) / 10000d) + " min");
        jlStatPriemCasVRadeTerminal2.setText((Math.round(pozicovna.statCasuVRadeTerminal2() * 10000d) / 10000d) + " min");

        Minibus[] minibusy = pozicovna.getMinibusyArray();

        for (int i = 0; i < minibusy.length; i++) {
            tableModel.setValueAt(minibusy[i].getPredoslaZastavka(), i, 1);
            tableModel.setValueAt(minibusy[i].getAktualnaZastavka(), i, 2);
            tableModel.setValueAt(minibusy[i].getPriorFrontCestujuci().size(), i, 3);
        }

        cal.set(0,0,0,0,0,0);
        cal.add(Calendar.SECOND, (int)(pozicovna.getAktualnyCas()));
        String newTime = df.format(cal.getTime());
        jlCas.setText(newTime);

//        for (int i = model.getRowCount() -1; i >= 0; i--) {
//            Object[] row = getNewRow();
//            model.setValueAt(row[0], i, 0);
//            model.setValueAt(row[1], i, 1);
//        }
    }

    private void vykresliGraf(int minibusy, double vysledok) {
        if (vysledok < spodna1) {
            spodna1 = vysledok - 0.000001;
        }
        if (vysledok > horna1) {
            horna1 = vysledok + 0.000001;
        }
        this.lineChart.getXYPlot().getRangeAxis().setRange(spodna1, horna1);
        this.series.add(minibusy, vysledok);
    }

    private void vykresliGraf2(int pracovnici, double vysledok) {
        if (vysledok < spodna2) {
            spodna2 = vysledok - 0.000001;
        }
        if (vysledok > horna2) {
            horna2 = vysledok + 0.000001;
        }
        this.lineChart2.getXYPlot().getRangeAxis().setRange(spodna2, horna2);
        this.series2.add(pracovnici, vysledok);
    }
}
