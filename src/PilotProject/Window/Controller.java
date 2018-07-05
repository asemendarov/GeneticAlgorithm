package PilotProject.Window;

import PilotProject.Sample.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    public Label labelInfo;
    @FXML
    public LineChart<Number, Number> lineChart;
    @FXML
    public Button button;
    @FXML
    public TextArea textResult;
    @FXML
    public TextArea textLog;
    @FXML
    public TextField textTimer;
    @FXML
    public TextField textMax;
    @FXML
    public TextField textMin;

    private SimpleAlgorithm simpleAlgorithm;

    private ScheduledExecutorService timer;
    private boolean algorithmActive;

    @FXML
    public void run(ActionEvent actionEvent) {
        try {
            if (!algorithmActive){
                start();
                algorithmActive = true;
                button.setText("Stop");
            }
            else {
                stop();
                algorithmActive = false;
                button.setText("Start");
            }

        } catch (Exception ex){
            labelInfo.setText(ex.getMessage());
        }
    }

    private void start(){
        if (simpleAlgorithm == null)
            throw new IllegalArgumentException("Вы не обьявлили хромосомы");

        Runnable runnable = () -> {
            try {
                simpleAlgorithm.run();
                updateTextLog(simpleAlgorithm.getListChromosome().toString());

                Chromosome chromosome = simpleAlgorithm.getSortArrayListChromosome().get(3);

                updateTextResult(String.format(
                        "%.03f\t%.03f\t%.03f",
                        chromosome.getGene(0).getGene(),
                        chromosome.getGene(1).getGene(),
                        chromosome.getResult()
                ));

            } catch (Exception ex){
                updateLableText(ex.getMessage());
            }
        };

        timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(runnable, 0, Long.parseLong(textTimer.getText()), TimeUnit.MILLISECONDS);
    }

    private void stop(){
        if (timer != null && !timer.isShutdown()){
            timer.shutdown();

            try {
                timer.awaitTermination(Long.parseLong(textTimer.getText()), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                labelInfo.setText(e.getMessage());
            }
        }
    }

    public void closeRequest() {
        stop();
    }

    private void updateLableText(String text){
        Platform.runLater(() -> {
            labelInfo.setText(text);
        });
    }

    private void updateTextLog(String text){
        Platform.runLater(() -> {
            textLog.setText(String.format("%s\n%s", text, textLog.getText()));
        });
    }

    private int indexResult = -1;
    private void updateTextResult(String text){
        indexResult = indexResult + 1;
        Platform.runLater(() -> {
            textResult.setText(String.format("Шаг %s:\t%s\n%s", indexResult, text, textResult.getText()));
        });
    }

    private int indexLineChar = 0;
    private void updateLineChart(Chromosome chromosome){
        indexLineChar = indexLineChar + 1;
        XYChart.Series series = lineChart.getData().get(0);
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(indexLineChar, chromosome.getResult()));
        });
    }

    public void setSimpleAlgorithm(ListChromosome listChromosome, Controller controller) {
        this.simpleAlgorithm = new SimpleAlgorithm(listChromosome, controller);
    }

    public double getTextMin() {
        return Double.parseDouble(textMin.getText());
    }

    public double getTextMax() {
        return Double.parseDouble(textMax.getText());
    }

    public void cancel(ActionEvent actionEvent) {

    }
}