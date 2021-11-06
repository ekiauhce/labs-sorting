import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
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
    
    private JTextField minNField;
    private JTextField maxNField;
    private JTextField deltaNField;
    private JTextField itersField;

    private JPanel buttonsPanel;
    private JPanel chartPanel;

    private void createUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        chartPanel = new XChartPanel<XYChart>(chart);
        add(chartPanel);

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(fieldsPanel);

        JLabel minNLabel = new JLabel("Min N:");
        minNField = new JTextField("1000", 10);
        minNLabel.setLabelFor(minNField);
        fieldsPanel.add(minNLabel);
        fieldsPanel.add(minNField);

        JLabel maxNLabel = new JLabel("Max N:");
        maxNField = new JTextField("15000", 10);
        maxNLabel.setLabelFor(maxNField);
        fieldsPanel.add(maxNLabel);
        fieldsPanel.add(maxNField);
        
        JLabel deltaNLabel = new JLabel("Delta N:");
        deltaNField = new JTextField("500", 10);
        deltaNLabel.setLabelFor(deltaNField);
        fieldsPanel.add(deltaNLabel);
        fieldsPanel.add(deltaNField);

        JLabel itersLabel = new JLabel("Iters:");
        itersField = new JTextField("5", 10);
        itersLabel.setLabelFor(itersField);
        fieldsPanel.add(itersLabel);
        fieldsPanel.add(itersField);

        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        createSortingButton("selection");
        createSortingButton("quick");
        createSortingButton("counting");

        add(buttonsPanel);
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private static Map<String, Consumer<int[]>> nameToMethod = new HashMap<>();
    static {
        nameToMethod.put("selection", Selection::sort);
        nameToMethod.put("counting", Counting::sort);
        nameToMethod.put("quick", Quick::sort);
    }

    private void createSortingButton(String name) {
        JButton jb = new JButton(name);
        jb.addActionListener(e -> {
            Benchmarking bench;
            try {
                bench = new Benchmarking(nameToMethod.get(name), jb.getText(),
                    Integer.parseInt(minNField.getText()),
                    Integer.parseInt(maxNField.getText()),
                    Integer.parseInt(deltaNField.getText()),
                    Integer.parseInt(itersField.getText())
                );
            } catch (RuntimeException ex) {
                bench = new Benchmarking(nameToMethod.get(name), jb.getText());
            }
            bench.run();
            
            updateSeries(jb.getText());
            chartPanel.repaint();
        });
        buttonsPanel.add(jb);
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