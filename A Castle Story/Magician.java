import greenfoot.*;

public class Magician extends Enemy
{
    private String[] allImages = {"Magician/Run/Magician_F1.png", "Magician/Run/Magician_F2.png", "Magician/Run/Magician_F3.png", "Magician/Run/Magician_F4.png", "Magician/Run/Magician_F5.png", "Magician/Run/Magician_F6.png", "Magician/Run/Magician_F7.png", "Magician/Run/Magician_F8.png"};
    private Animation_Controller movement = new Animation_Controller(0.1, allImages, this);

    private int timeBtwPortals = 240;
    private int portalTimer;

    public Magician()
    {
        setGoldAmount(2);
        setHealth(5);
        setSpeed(1);
        setScore(500);
    }

    public void act() 
    {
        if(update())
        {
            return;
        }

        movement.update();

        movement();
        makePortal();

        portalTimer++;
    }

    public void movement()
    {
        Castle castle = (Castle)getOneIntersectingObject(Castle.class);

        if(castle != null)
        {     
            if(timeBtwAttacks <= 0)
            {
                castle.takeDamage(1);
                timeBtwAttacks = startTimeBtwAttacks;  
            }
        }
        else
        {
            setLocation((int)pos.x + speed, (int)pos.y);
        }

        timeBtwAttacks -= 0.017;
    }

    private void makePortal()
    {
        Enemy enemy = getSecondClosestEnemy();

        if(portalTimer >= timeBtwPortals && enemy != null && enemy.getX() < 850)
        {
            getWorld().addObject(new Portal(), enemy.getX() + 100, enemy.getY());
            getWorld().addObject(new Portal(), 900, enemy.getY());

            portalTimer = 0;
        }
    }
}
