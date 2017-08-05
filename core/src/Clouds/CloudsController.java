package Clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import Helpers.GameInfo;

/**
 * Created by Darrell Payne on 8/4/17.
 */

public class CloudsController {

    private World world;
    private Array<Cloud> clouds = new Array<Cloud>(); // Resizable array of clouds

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX,maxX;

    public CloudsController(World world){
        this.world = world;

        minX = GameInfo.WIDTH / 2 - 110;
        maxX = GameInfo.WIDTH / 2 + 110;

        createClouds();
        positionClouds();

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

    public void positionClouds(){

        while (clouds.get(0).getCloudName() == "Dark Cloud"){
            clouds.shuffle(); // Make sure the first cloud is not the dark cloud
        }

        float positionY = GameInfo.HEIGHT / 2;
        float tempX = GameInfo.WIDTH / 2;

        for (Cloud c : clouds){
            c.setSpritePosition(tempX,positionY);
            positionY -= DISTANCE_BETWEEN_CLOUDS;
        }
    }

    public void drawClouds(SpriteBatch batch){
        for (Cloud c : clouds){
            batch.draw(c, c.getX() - c.getWidth() / 2, c.getY() - c.getHeight() / 2);
        }
    }

} // Clouds Controller
