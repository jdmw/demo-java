package jd.demo.example.common.thingsbysubject.biology.creature.animal.bug;


import jd.demo.example.common.thingsbysubject.biology.creature.animal.IAnimal;

class Flea implements IBug{}
class Housefly implements IBug{}
class Honeybee implements IBug{}
class Ladybird implements IBug{}
class Moth implements IBug{}
class Termite implements IBug{}

public interface IBug extends IAnimal{
}
