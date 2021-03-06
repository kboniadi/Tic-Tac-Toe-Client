package io.github.donut.proj.controllers;

import io.github.donut.proj.listener.ISubject;
import io.github.donut.proj.model.SceneName;
import io.github.donut.sounds.EventSounds;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Central hub that connects the main menu to the player history controller, the player stats controller,
 * and the update account controller.
 *
 * @author Joey Campbell
 * @version 0.1
 */
public class ProfilePortalController extends AbstractController implements Initializable, ISubject {

    @FXML
    private ImageView historyButton;

    @FXML
    private ImageView statsButton;

    @FXML
    private ImageView profileSettingsButton;

    @FXML
    private ImageView backButton;

    private final String theme = "theme_2";

    /**
     * Initializes the Profile Portal Controller after its root element
     * has been completely processed.
     * @param location Location
     * @param resources Resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnMouseClicked(this::onBackButtonClick);
        backButton.setOnMouseEntered(this::onBackButtonEnter);
        backButton.setOnMouseExited(this::onBackButtonExit);

        historyButton.setOnMouseClicked(this::onHistoryButtonClick);
        historyButton.setOnMouseEntered(this::onHistoryButtonHover);
        historyButton.setOnMouseExited(this::onHistoryButtonExit);

        statsButton.setOnMouseClicked(this::onStatsButtonClick);
        statsButton.setOnMouseEntered(this::onStatsButtonHover);
        statsButton.setOnMouseExited(this::onStatsButtonExit);

        profileSettingsButton.setOnMouseClicked(this::onPlayerSettingsButtonClick);
        profileSettingsButton.setOnMouseEntered(this::onPlayerSettingsButtonHover);
        profileSettingsButton.setOnMouseExited(this::onPlayerSettingsButtonExit);
    }

    /**
     * Event handler for history button click event
     * @param mouseEvent mouseEvent
     * @author Joey Campbell
     */
    public void onHistoryButtonClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.HISTORY_PAGE).getScene(false, false));
    }

    /**
     * Sets the image on the history button to its hover image
     * @author Joey Campbell
     */
    public void onHistoryButtonHover(MouseEvent mouseEvent) {
        historyButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/history_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the history button to its regular image
     * @param mouseEvent mouseEvent
     * @author Joey Campbell
     */
    public void onHistoryButtonExit(MouseEvent mouseEvent) {
        historyButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/history_button.png"))
        ));
    }

    public void onStatsButtonClick(MouseEvent mouseEvent) {
        // TODO - Handle stats here
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.STATS_PAGE).getScene(false, false));
    }

    /**
     * Sets the image on the stats button to its hover image
     * @param mouseEvent mouseEvent
     * @author Joey Campbell
     */
    public void onStatsButtonHover(MouseEvent mouseEvent) {
        statsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/stats_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the stats button to its regular image
     * @param mouseEvent mouseEvent
     * @author Joey Campbell
     */
    public void onStatsButtonExit(MouseEvent mouseEvent) {
        statsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/stats_button.png"))
        ));
    }

    /**
     * Event handler for the player settings button click event. This method switches the scenes
     * to the player settings page
     * @param mouseEvent mouseEvent
     */
    public void onPlayerSettingsButtonClick(MouseEvent mouseEvent) {
        EventSounds.getInstance().playButtonSound4();
        stage.setScene(AppController.getScenes().get(SceneName.UPDATE_ACCOUNT_PAGE).getScene(false, false));
    }

    /**
     * Sets the image on the player settings button to its hover image
     * @author Joey Campbell
     */
    public void onPlayerSettingsButtonHover(MouseEvent mouseEvent) {
        profileSettingsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/profile_settings_button_hover.png"))
        ));
    }

    /**
     * Sets the image on the player settings button to its regular image
     * @author Joey Campbell
     */
    public void onPlayerSettingsButtonExit(MouseEvent mouseEvent) {
        profileSettingsButton.setImage(new Image(Objects.requireNonNull(
                getClass().
                getClassLoader().
                getResourceAsStream("io/github/donut/proj/images/" + theme + "/profile_settings_button.png"))
        ));
    }

    /**
     * Event handler for back button idle effect
     * @author Kord Boniadi
     */
    private final Image backButtonIdle = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow.png")
    ));

    /**
     * Event handler for back button hover effect
     * @author Kord Boniadi
     */
    private final Image backButtonHover = new Image(Objects.requireNonNull(
            getClass().
                    getClassLoader().
                    getResourceAsStream("io/github/donut/proj/images/common/back_arrow_hover.png")
    ));

    /**
     * Event handler for back button hover effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonEnter(MouseEvent mouseEvent) {
        backButton.setImage(backButtonHover);
    }

    /**
     * Event handler for back button idle effect
     *
     * @author Kord Boniadi
     */
    public void onBackButtonExit(MouseEvent actionEvent) {
        backButton.setImage(backButtonIdle);
    }

    /**
     * Event handler for back button
     *
     * @param actionEvent mouse event
     * @author Kord Boniadi
     */
    public void onBackButtonClick(MouseEvent actionEvent) {
        EventSounds.getInstance().playButtonSound1();
        stage.setScene(AppController.getScenes().get(SceneName.Main).getScene(false));
    }
}
