import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.DoubleStream;

import javax.swing.*;
import java.awt.*;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

public class Plot extends JFrame {

    private XYChart chart;

    public Plot() {
        super();
        chart = new XYChartBuilder()
            .width(1200).height(800)
            .xAxisTitle("n").yAxisTitle("time, ms")
            .theme(ChartTheme.Matlab)
            .build();

        createUI();
    }
    
    private double[][] loadResults(String name){
        try (Scanner sc = new Scanner(new File(name + "-results.txt"))) {
            
            int resultsCount = sc.nextInt();
            double[][] results = new double[2][resultsCount];
            
            results[0] = DoubleStream.generate(sc::nextDouble)
                .limit(resultsCount)
                .toArray();
            
            results[1] = DoubleStream.generate(sc::nextDouble)
                .limit(resultsCount)
                .toArray();
            
            return results;
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
    
    private void createUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel chartPanel = new XChartPanel<XYChart>(chart);
        add(chartPanel);

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel minNLabel = new JLabel("Min N:");
        JTextField minNField = new JTextField("1000", 10);
        minNLabel.setLabelFor(minNField);
        fieldsPanel.add(minNLabel);
        fieldsPanel.add(minNField);
        add(fieldsPanel);

        JLabel maxNLabel = new JLabel("Max N:");
        JTextField maxNField = new JTextField("15000", 10);
        maxNLabel.setLabelFor(maxNField);
        fieldsPanel.add(maxNLabel);
        fieldsPanel.add(maxNField);
        add(fieldsPanel);
        
        JLabel deltaNLabel = new JLabel("Delta N:");
        JTextField deltaNField = new JTextField("500", 10);
        deltaNLabel.setLabelFor(deltaNField);
        fieldsPanel.add(deltaNLabel);
        fieldsPanel.add(deltaNField);
        add(fieldsPanel);

        JLabel itersLabel = new JLabel("Iters:");
        JTextField itersField = new JTextField("5", 10);
        itersLabel.setLabelFor(itersField);
        fieldsPanel.add(itersLabel);
        fieldsPanel.add(itersField);
        add(fieldsPanel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton jb1 = new JButton("selection");
        jb1.addActionListener(e -> {
            Benchmarking bench;
            try {
                bench = new Benchmarking(Selection::sort, jb1.getText(),
                    Integer.parseInt(minNField.getText()),
                    Integer.parseInt(maxNField.getText()),
                    Integer.parseInt(deltaNField.getText()),
                    Integer.parseInt(itersField.getText())
                );
            } catch (RuntimeException ex) {
                bench = new Benchmarking(Selection::sort, jb1.getText());
            }
            bench.run();
            
            updateSeries(jb1.getText());
            chartPanel.repaint();
        });
        buttonsPanel.add(jb1);
        
        JButton jb2 = new JButton("quick");
        jb2.addActionListener(e -> {
            Benchmarking bench;
            try {
                bench = new Benchmarking(Quick::sort, jb2.getText(),
                    Integer.parseInt(minNField.getText()),
                    Integer.parseInt(maxNField.getText()),
                    Integer.parseInt(deltaNField.getText()),
                    Integer.parseInt(itersField.getText())
                );
            } catch (RuntimeException ex) {
                bench = new Benchmarking(Quick::sort, jb2.getText());
            }
            bench.run();

            updateSeries(jb2.getText());
            chartPanel.repaint();
        });
        buttonsPanel.add(jb2);
    
        add(buttonsPanel);
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private void updateSeries(String name) {
        double[][] results = loadResults(name);
        try {
            chart.updateXYSeries(name, results[0], results[1], null);
        } catch (IllegalArgumentException e) {
            chart.addSeries(name, results[0], results[1]);
        }
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Plot());
    }
}