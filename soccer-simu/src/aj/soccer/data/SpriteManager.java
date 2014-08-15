package aj.soccer.data;

public interface SpriteManager {

    /**
     * Adds a sprite to the manager and renders the image (if visible on-screen).
     *
     * @param sprite - The sprite.
     */
    void addSprite(Sprite sprite);

    /**
     * Removes a sprite from the manager and removes its image (if visible on-screen).
     *
     * @param sprite - The sprite.
     */
    void removeSprite(Sprite sprite);

}
