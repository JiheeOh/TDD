package main.append.mockito;

public class Game {
    private GameNumGen gameNumGen;
    public Game(GameNumGen gameNumGen) {
        this.gameNumGen = gameNumGen;
    }

    public void init(GameLevel gameLevel) {
        gameNumGen.generate(gameLevel);
    }
}
