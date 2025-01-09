import services.GameManager;
import utils.InitSystem;

public class MonsterGameApp {
    public static void main(String[] args) throws Exception {
        InitSystem initSystem = new InitSystem();
        initSystem.fileCheck();
        GameManager gameManager = new GameManager();
        gameManager.startGame();
    }
}
