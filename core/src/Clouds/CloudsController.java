package Clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import Helpers.GameInfo;
import collectables.Collectable;
import player.Player;

/**
 * Created by Darrell Payne on 8/4/17.
 */

public class CloudsController {

    private World world;
    private Array<Cloud> clouds = new Array<Cloud>(); // Resizable array of clouds
    private Array<Collectable> collectables = new Array<Collectable>();

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX,maxX;
    private Random random = new Random();
    private float lastCloudPositionY;
    private float cameraY;

    public CloudsController(World world){
        this.world = world;

        minX = GameInfo.WIDTH / 2 - 110;
        maxX = GameInfo.WIDTH / 2 + 110;

        createClouds();
        positionClouds(true);

    }

    void createClouds(){
        for (int i = 0; i < 2; i++){ //Create 2 dark clouds
            clouds.add(new Cloud(world, "Dark Cloud"));
        }

        int index = 1;

        for (int i = 0; i < 6; i++){ // Create 2 Cloud 1s, 2 Cloud 2s, 2 Cloud 3s
            clouds.add(new Cloud(world, "Cloud " + index));
            index++;
            if (index == 4){
                index = 1;
            }
        }
        clouds.shuffle();
    }

    public void positionClouds(boolean firstTimeArranging){

        while (clouds.get(0).getCloudName() == "Dark Cloud"){
            clouds.shuffle(); // Make sure the first cloud is not the dark cloud
        }

        float positionY = 0;

        if (firstTimeArranging){
            positionY = GameInfo.HEIGHT / 2;
        } else {
            positionY = lastCloudPositionY;
        }

        int controlX = 0;

        for (Cloud c : clouds){

            if (c.getX() == 0 && c.getY() == 0){ // Position clouds only if they have never been positioned before

                float tempX = 0;

                // Draw clouds alternating on left and right
                if (controlX == 0){
                    tempX = randomBetweenNumbers(maxX - 60, maxX); // Draw cloud on left side
                    controlX = 1;
                }else if (controlX == 1){
                    tempX = randomBetweenNumbers(minX + 60, minX); // Draw cloud on right side
                    controlX = 0;
                }
                c.setSpritePosition(tempX,positionY);
                positionY -= DISTANCE_BETWEEN_CLOUDS;
                lastCloudPositionY = positionY;

                if (!firstTimeArranging && c.getCloudName() != "Dark Cloud"){

                    int rand = random.nextInt(10);
                    if (rand > 1){
                        int randomCollectable = random.nextInt(2); // 0 or 1
                        if (randomCollectable == 0){
                            // spawn a life, if the life count is lower than 2
                            Collectable collectable = new Collectable(world, "Life");
                            collectable.setCollectablePosition(c.getX(), c.getY() + 40);
                            collectables.add(collectable);
                        } else {
                            // spawn a coin
                            Collectable collectable = new Collectable(world, "Coin");
                            collectable.setCollectablePosition(c.getX(), c.getY() + 40);
                            collectables.add(collectable);
                        }

                    }
                }
            }
//            System.out.println("Cloud drawn");
        }
    }

    public void drawClouds(SpriteBatch batch){
        for (Cloud c : clouds){
            batch.draw(c, c.getX() - c.getWidth() / 2, c.getY() - c.getHeight() / 2);
        }
    }

    public void drawCollectables(SpriteBatch batch){

        for (Collectable c : collectables){
            c.updateCollectable();
            batch.draw(c, c.getX(), c.getY());
        }
    }

    public void removeCollectables(){
        for(int i = 0; i < collectables.size; i++){
            if (collectables.get(i).getFixture().getUserData() == "Remove"){
                collectables.get(i).changeFilter();
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
            }
        }
    }

    public void createAndArrangeClouds(){
        for (int i = 0; i < clouds.size; i++){
            if ((clouds.get(i).getY() - GameInfo.HEIGHT / 2 - 20) > cameraY){
                // Cloud is out of bounds, delete it
                clouds.get(i).getTexture().dispose(); // remove the texture of the object
                clouds.removeIndex(i); // remove the object
            }
        }
        if (clouds.size == 4){
            createClouds();
            positionClouds(false);
        }
    }

    public void removeOffScreenCollectables(){
        for (int i = 0; i < collectables.size; i++){
            if ((collectables.get(i).getY() - GameInfo.HEIGHT / 2 - 15) > cameraY){ //Collectable is off screen
                collectables.get(i).getTexture().dispose();
                collectables.removeIndex(i);
                System.out.println("Collectable removed");
            }
        }
    }

    public void setCameraY(float cameraY){
        this.cameraY = cameraY;
    }

    public Player positionThePlayer(Player player){
        player = new Player(world, clouds.get(0).getX(), clouds.get(0).getY() + 78);
        return player;
    }

    private float randomBetweenNumbers(float min, float max){
        return random.nextFloat() * (max - min) + min;
    }


} // Clouds Controller
