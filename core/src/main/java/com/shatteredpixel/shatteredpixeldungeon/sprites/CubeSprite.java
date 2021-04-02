package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class CubeSprite extends MobSprite {

    public CubeSprite() {
        super();

        texture( Assets.Sprites.CUBE);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 3, true );
        idle.frames( frames, 0 );

        run = new Animation( 3, true );
        run.frames( frames, 0 );

        attack = new Animation( 3, false );
        attack.frames( frames, 0 );

        die = new Animation( 3, false );
        die.frames( frames, 0 );

        play(idle);
    }
}
