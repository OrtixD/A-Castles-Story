import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.*;

public class Battlefield extends World
{    
    private Wave wave;
    public static int[] towerSpots = new int[7];

    public Castle castle;
    private Door door = new Door(Game.shop);

    private WaveIndicator waveIndicator = new WaveIndicator();

    private List<Tower> towers = new ArrayList<Tower>();
    public Battlefield()
    {       
        super(1200, 800, 1);   

        addObject(new Road(), 600, 400);
        castle = new Castle(wave);
        addObject(castle, 1100, 400);

        castle.addUI();

        addObject(new GoldCounter(), 250, 740);
        addObject(new ScoreCounter(), 900, 790);

        addObject(door, 1064, 400);
        enemyAmounts[0] = 3;

        addObject(waveIndicator, 600, 780);

        prepare();
    }

    public void prepare()
    {
        door.setDestination(Game.shop);
        door.closeDoor();
        waveIndicator.update();
        wave = createWave();
        castle.setWave(wave);
        addObject(Game.player, 950, getHeight()/2);

        removeObjects(towers);

        for(int i = 0; i < towerSpots.length; i++)
        {
            if(towerSpots[i] == 1)
            {
                Tower tower = new Tower1();
                towers.add(tower);
                addObject(tower, 1100, i*100 + 100);
            }
            if(towerSpots[i] == 2)
            {
                Tower tower = new TrapPlacer();
                towers.add(tower);
                addObject(tower, 1100, i*100 + 100);
            }
        }
    }

    Enemy[][] enemiesToSpawn = new Enemy[4][0];
    int[] enemyAmounts = new int[enemiesToSpawn.length];
    Random dice = new Random();
    public Wave createWave()
    {      
        enemiesToSpawn = new Enemy[4][0];
        enemyAmounts = new int[enemiesToSpawn.length];

        for(int i = 0; i < Game.levelCount; i++)
        {
            int toIncrease = dice.nextInt(enemyAmounts.length);
            int amount = dice.nextInt(2) + 1;
            enemyAmounts[toIncrease] += amount;
        }

        for(int i = 0; i < enemyAmounts.length; i++)
        {
            enemiesToSpawn[i] = new Enemy[enemyAmounts[i]];
        }

        for(int i = 0; i < enemiesToSpawn[0].length; i++)
        {
            enemiesToSpawn[0][i] = new Aligator();
        }

        for(int i = 0; i < enemiesToSpawn[1].length; i++)
        {
            enemiesToSpawn[1][i] = new Bomber();
        }

        for(int i = 0; i < enemiesToSpawn[2].length; i++)
        {
            enemiesToSpawn[2][i] = new Trebuchet();
        }

        for(int i = 0; i < enemiesToSpawn[3].length; i++)
        {
            enemiesToSpawn[3][i] = new Giant();
        }

        return new Wave(enemiesToSpawn);
    }
}
