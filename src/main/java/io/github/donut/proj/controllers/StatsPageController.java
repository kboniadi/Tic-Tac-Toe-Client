package io.github.donut.proj.controllers;

import io.github.API.MessagingAPI;
import io.github.donut.proj.callbacks.AuthorizationCallback;
import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import lombok.Setter;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Objects;

public class StatsPageController extends AbstractController implements Initializable, ISubject {

    @FXML
    private Label statsPageTitle;
    @FXML
    private Label winsLabel;
    @FXML
    private Label lossesLabel;
    @FXML
    private Label tiesLabel;
    @FXML
    private Label winLossRatioLabel;
    @FXML
    private Label totalGamesPlayedLabel;

    @FXML
    private BorderPane statsPage;

    @FXML
    private ImageView backButton;

    @Setter
    private MessagingAPI api = null;                                   //instance of api
    @Setter
    private AuthorizationCallback ac;

    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));

    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

    @FXML
    public void initialize() {
        statsPageTitle.setText       ("STATS PAGE");
        winsLabel.setText            ("WINS");
        lossesLabel.setText          ("LOSSES");
        tiesLabel.setText            ("TIES");
        winLossRatioLabel.setText    ("WIN PERCENTAGE");
        totalGamesPlayedLabel.setText("TOTAL GAMES");
    }

    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
    //    stage.setScene(AppController.getScenes().get(SceneName.LOGIN_PAGE).getScene(ControllerFactory.getController(SceneName.LOGIN_PAGE), false));

        if (api != null)
            api.removeEventListener(ac);
    }

    public void onBackButtonEnter(MouseEvent mouseEvent) {
        backButton.setImage(backButtonHover);
    }

    public void onBackButtonExit(MouseEvent mouseEvent) {
        backButton.setImage(backButtonIdle);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);
    }
}
 