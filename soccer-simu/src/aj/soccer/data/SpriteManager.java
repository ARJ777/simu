package aj.soccer.data;

import java.util.Collection;

public interface SpriteManager {

    /**
     * Adds a sprite to the manager and renders the image (if visible on-screen).
     *
     * @param sprite - The sprite.
     */
    void addSprite(Sprite sprite);

    /**
     * Adds sprites to the manager and renders their images (if visible on-screen).
     *
     * @param sprites - The sprites.
     */
    void addSprites(Collection<Sprite> sprites);

    /**
     * Removes a sprite from the manager and removes its image (if visible on-screen).
     *
     * @param sprite - The sprite.
     */
    void removeSprite(Sprite sprite);

    /**
     * Removes sprites from the manager and removes their images (if visible on-screen).
     *
     * @param sprites - The sprites.
     */
    void removeSprites(Collection<Sprite> sprites);

}
