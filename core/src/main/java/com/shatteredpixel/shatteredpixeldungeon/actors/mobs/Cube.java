package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.badlogic.gdx.utils.Align;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CubeSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.List;

public class Cube extends Mob {

    private final List<Item> itemList = new ArrayList<>();

    {
        spriteClass = CubeSprite.class;

        state = WANDERING;
        alignment = Alignment.NEUTRAL;
        properties.add(Property.INORGANIC);

    }

    public Cube() {
        super();
        for (int i = 0; i < Random.NormalIntRange(1, Dungeon.depth * 3); i++) {
            itemList.add(Generator.random());
            HP = HT = 5 + (itemList.size() * 5);
        }
    }

    @Override
    public boolean act() {
        Heap heap = Dungeon.level.heaps.get( this.pos );
        if (heap != null) {
            itemList.add(heap.pickUp());
        }
        return super.act();
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, itemList.size() * 5 );
    }

    @Override
    public int attackSkill( Char target ) {
        return (Dungeon.depth + itemList.size() * 3);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(0, itemList.size());
    }

    @Override
    protected float attackDelay() {
        return super.attackDelay() + ((float)itemList.size() / 2f);
    }

    @Override
    public void damage( int dmg, Object src ) {

        if (state == WANDERING) {
            alignment = Alignment.ENEMY;
            state = HUNTING;
            if (src instanceof Char) {
                enemy = (Char)src;
            } else if (src instanceof Wand) {
                enemy = Dungeon.hero;
            }
        }

        super.damage( dmg, src );
    }

    @Override
    public void die( Object cause ) {
        EXP = Dungeon.depth + itemList.size();
        dropAllItems();
        super.die(cause);
    }

    private void dropAllItems() {
        for (Item item : itemList) {
            Dungeon.level.drop(item, this.pos).sprite.drop();
        }
    }

}
