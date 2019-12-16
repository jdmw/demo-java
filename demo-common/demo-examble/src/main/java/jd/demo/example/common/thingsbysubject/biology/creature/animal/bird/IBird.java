package jd.demo.example.common.thingsbysubject.biology.creature.animal.bird;

import jd.demo.example.common.thingsbysubject.biology.creature.animal.IAnimal;

class Chicken implements IBird{}
class Duck implements IBird{}
class Goose implements IBird{}
class Turkey implements IBird{}

class Crow implements IBird{}
class Sparrow implements IBird{}
class Dove implements IBird{}
class Penguin implements IBird{}


public interface IBird extends IAnimal {
}
