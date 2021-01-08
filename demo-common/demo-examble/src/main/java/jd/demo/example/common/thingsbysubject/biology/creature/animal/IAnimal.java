package jd.demo.example.common.thingsbysubject.biology.creature.animal;

import jd.demo.example.common.thingsbysubject.biology.creature.ICreature;

/**
 * Created by jdmw on 2019/12/16.
 */
public interface IAnimal extends ICreature{
    boolean canFly();
    boolean canSwim();
    boolean canWalk();
    boolean canSpeak();
    boolean haveMilk();
}
