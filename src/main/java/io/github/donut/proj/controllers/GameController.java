package io.github.donut.proj.controllers;

import io.github.coreutils.proj.enginedata.Board;
import io.github.coreutils.proj.enginedata.Token;
import io.github.coreutils.proj.messages.*;
import io.github.donut.proj.PlayerType.Human;
import io.github.donut.proj.PlayerType.NPCHardMode;
import io.github.donut.proj.callbacks.GlobalAPIManager;
import io.github.donut.proj.common.Player;
import io.github.donut.proj.listener.EventManager;
import io.github.donut.proj.listener.IObserver;
import io.github.donut.proj.listener.ISubject;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static io.github.coreutils.proj.enginedata.Token.*;

/**
 * Game logic controller
 * @author Kord Boniadi
 * @author Brandon Nguyen
 */
public class GameController implements ISubject, IObserver {

    private int singlePlayerRoomID = 0;
    private long startTime;

    /**
     * Container for data to be sent out to IObservers subscribed to this class
     * @see EventManager#notify(ISubject, Object)
     * @author Kord Boniadi
     */
    public static class DrawInfo {
        private final Board updatedBoard;

        public DrawInfo(Board board) {
            this.updatedBoard = board;
        }

        public Board getUpdatedBoard() {
            return updatedBoard;
        }

    }

    /**
     * Container for data to be sent out to IObservers subscribed to this class
     * @see EventManager#notify(ISubject, Object)
     * @author Kord Boniadi
     */
    public static class Results {
        private final Player winner;

        public Results() {
            this(null);
        }
        public Results(Player winner) {
            this.winner = winner;
        }

        public Player getWinner() {
            return winner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Results)) return false;
            Results results = (Results) o;
            return Objects.equals(getWinner(), results.getWinner());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getWinner());
        }

        @Override
        public String toString() {
            return "Results{" +
                    "winner=" + winner +
                    '}';
        }
    }

    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player swap;

    /**
     * Constructor
     * @author Kord Boniadi
     * @author Brandon Nguyen
     */
    public GameController() {
        this(
                new Player("Player1", X),
                new Player("Player2", O),
                new Board()
        );
    }

    /**
     * Constructor
     * @author Utsav Parajuli
     * @param player1 player object instance
     */
    public GameController(Player player1) {
        this(
                player1,
                new Player("Player2", O),
                new Board()
        );
    }


    /**
     * Constructor
     * @param player1 player object instance
     * @param player2 player object instance
     * @author Kord Boniadi
     * @author Brandon Nguyen
     * @author Utsav Parajuli
     */
    public GameController(Player player1, Player player2) {
        this(player1, player2, new Board());
        Random rand = new Random();
        swap = (rand.nextInt(2) == 0) ? this.player1 : this.player2;
    }
    /**
     * Constructor
     * @param player1 player object instance
     * @param player2 player object instance
     * @param board Object instance
     * @author Kord Boniadi
     * @author Brandon Nguyen
     */
    public GameController(Player player1, Player player2, Board board) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * @return board instance
     * @author Kord Boniadi
     * @author Brandon Nguyen
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return player instance
     * @author Brandon Nguyen
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * @return player instance
     * @author Brandon Nguyen
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * @return next player
     * @author Kord Boniadi
     */
    public Player nextPlayer() {
        return swap;
    }


    /**
     * makes initial connections needed for the start
     * of a game
     * @author Kord Boniadi
     */
    public void startGame() {
        EventManager.register(player1, this);
        EventManager.register(player2, this);

        if (player2.getPlayerType() instanceof Human)
            EventManager.notify(this, swap);
        swap.makeMove(this.board);

        startTime = System.currentTimeMillis();
    }

    /**
     * checks for gameOver state
     * @return true or false
     * @author Brandon Nguyen
     */
    public boolean gameOver() {
        return board.isBoardFull() || hasWon(board) == X || hasWon(board) == O;
    }

    /**
     * check if a win state was achieved
     * @param board state of board
     * @return winning Token
     * @author Brandon Nguyen
     */
    public Token hasWon(Board board) {
        Token[][] boardArr = board.getUnderlyingBoard();

        for (int i = 0; i < board.BOARD_ROWS; i++) {
            if (boardArr[i][0] == X && boardArr[i][1] == X && boardArr[i][2] == X) {
                return X;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (boardArr[i][0] == O && boardArr[i][1] == O && boardArr[i][2] == O) {
                return O;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (boardArr[0][j] == X && boardArr[1][j] == X && boardArr[2][j] == X) {
                return X;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (boardArr[0][j] == O && boardArr[1][j] == O && boardArr[2][j] == O) {
                return O;
            }
        }

        if (boardArr[0][0] == X && boardArr[1][1] == X && boardArr[2][2] == X) {
            return X;
        }

        if (boardArr[0][0] == O && boardArr[1][1] == O && boardArr[2][2] == O) {
            return O;
        }

        if (boardArr[0][2] == X && boardArr[1][1] == X && boardArr[2][0] == X) {
            return X;
        }

        if (boardArr[0][2] == O && boardArr[1][1] == O && boardArr[2][0] == O) {
            return O;
        }
        return BLANK;
    }

    /**
     * Checks who has won if any
     * @param board current board state
     * @param player1 instance
     * @param player2 instance
     * @return winning Player instance
     * @author Brandon Nguyen
     */
    public Player whoWon(Board board, Player player1, Player player2) {
        if (hasWon(board) == player1.getPlayerToken())
            return player1;
        return (hasWon(board) == player2.getPlayerToken()) ? player2 : null;
    }

    /**
     * New info is received through this method. Object decoding is needed
     *
     * @param eventType General Object type
     * @author Kord Boniadi
     */
    @Override
    public void update(Object eventType) {
        if (eventType instanceof Player.MoveInfo) {
            Player.MoveInfo info = (Player.MoveInfo) eventType;
            boolean gameOver;
            if (swap == info.getPlayerInstance() && board.getToken(info.getX(), info.getY()) == BLANK) {
                board.updateToken(info.getX(), info.getY(), info.getPlayerInstance().getPlayerToken());
                gameOver = gameOver();

                //** ======== WRITING MOVE ======== **//
                MoveData move = new MoveData(singlePlayerRoomID,
                        (swap.getPlayerType() instanceof Human) ? AppController.getUserName() : "Computer",
                        info.getX(),
                        info.getY(),
                        System.currentTimeMillis());

//                System.out.println("CLIENT:: sending a move message: " + move + "\n");
                GlobalAPIManager.getInstance().send(move, Channels.ROOM_MOVE_SINGLEPLAYER.toString());


                swap = (gameOver) ? null : (swap == player1) ? player2 : player1;

                EventManager.notify(this, new DrawInfo(this.board));


                // GAME OVER LOGIC
                if (!gameOver) {
                    if (player2.getPlayerType() instanceof Human)
                        EventManager.notify(this, swap);
                    swap.makeMove(this.board);
                }
                else {
                    EventManager.notify(this, new GameController.Results(whoWon(board, player1, player2)));
                    // send room data message
                    PlayerData human = AppController.getPlayer();
                    human.setType(PlayerData.PlayerType.HUMAN);
                    PlayerData.PlayerType ai;
//                    human.setPlayerUserName((player1.getPlayerType() instanceof Human) ? player1.getPlayerName() : player2.getPlayerName());

                    // used to determine if player won/lost/tied the AI
                    SinglePlayerRoomData.PlayerResult playerResult;

                    if (player1.getPlayerType() instanceof Human) {
                        // set human to be player 1, and AI to be player 2
                        human.setPlayerUserName(player1.getPlayerName().substring(0, player1.getPlayerName().length()-4));
                        ai = (player2.getPlayerType() instanceof NPCHardMode) ? PlayerData.PlayerType.AI_HARD : PlayerData.PlayerType.AI_EASY;

                        // tie logic
                        if (board.isBoardFull() && hasWon(board).equals(BLANK))     // the game is a tie
                            playerResult = SinglePlayerRoomData.PlayerResult.TIE;
                        else if (player1.equals(whoWon(board, player1, player2)))   // player 1 (human) is winner
                            playerResult = SinglePlayerRoomData.PlayerResult.WIN;
                        else
                            playerResult = SinglePlayerRoomData.PlayerResult.LOSS;  // player 1 (human) is loser

                    }
                    else {
                        // set human to be player 2, and ai to be player 1
                        human.setPlayerUserName(player2.getPlayerName().substring(0, player1.getPlayerName().length()-4));
                        ai = (player1.getPlayerType() instanceof NPCHardMode) ? PlayerData.PlayerType.AI_HARD : PlayerData.PlayerType.AI_EASY;

                        // tie logic
                        if (board.isBoardFull() && hasWon(board).equals(BLANK))     // the game is a tie
                            playerResult = SinglePlayerRoomData.PlayerResult.TIE;
                        else if (player2.equals(whoWon(board, player1, player2)))   // player 2 (human) is winner
                            playerResult = SinglePlayerRoomData.PlayerResult.WIN;
                        else
                            playerResult = SinglePlayerRoomData.PlayerResult.LOSS;  // player 2 (human) is loser
                    }


                    SinglePlayerRoomData room = new SinglePlayerRoomData(
                            singlePlayerRoomID,
                            human,
                            startTime,
                            System.currentTimeMillis(),
                            SinglePlayerRoomData.RequestType.DISCONNECT,
                            true,
                            playerResult,
                            ai
                        );

//                    System.out.println("DEBUG: Sending SPR from client " + room);
                    GlobalAPIManager.getInstance().send(room, Channels.ROOM_SINGLE_PLAYER.toString());
                    }
            }
        }
    }
}
