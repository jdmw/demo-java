package jd.demo.example.common.thingsbysubject.biology.creature.animal;


public class Animals {

    public interface IAmphibian extends IAnimal {
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return true ;};
        default boolean canSpeak(){return true ;}
        default boolean haveMilk(){return true ;}
    }
    public interface IBird extends IAnimal {
        default boolean canFly()  {return true ;}
        default boolean canSwim() {return false ;};
        default boolean canWalk() {return true ;};
        default boolean canSpeak(){return true ;}
        default boolean haveMilk(){return false ;}
    }
    public interface Insect extends IAnimal{
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return false ;};
        default boolean canWalk() {return true ;};
        default boolean canSpeak(){return false ;}
        default boolean haveMilk(){return false ;}
    }
    public interface IBug extends Insect{
        default boolean canFly()  {return true ;}
    }
    public interface IFish {
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return false ;};
        default boolean canSpeak(){return false ;}
        default boolean haveMilk(){return false ;}
    }
    public interface Invertebrate extends IAnimal {
        default boolean haveMilk(){return false ;}
    }
    public interface UnderWaterAnimal extends Invertebrate{
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return false ;};
        default boolean canSpeak(){return false ;}
        default boolean haveMilk(){return false ;}
    }

    public interface IWorm extends Invertebrate{
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return false ;};
        default boolean canSpeak(){return false ;}
        default boolean haveMilk(){return false ;}
    }

    public interface IReptile extends IAnimal {
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return true ;};
        default boolean canSpeak(){return false ;}
        default boolean haveMilk(){return false ;}
    }

    public interface ISnake extends IReptile {
    }


    public interface IMammal extends IAnimal {
        default boolean canFly()  {return false ;}
        default boolean canSwim() {return true ;};
        default boolean canWalk() {return true ;};
        default boolean canSpeak(){return true ;}
        default boolean haveMilk(){return true ;}
    }
    public interface SwimmingMammal extends IMammal {
        default boolean canSwim() {return false ;};
    }
    
    public static class Amphibians{
        public static class Frog implements IAmphibian {}
        public static class Toad implements IAmphibian {}
        public static class Newt implements IAmphibian {}
    }
    public static class Birds{
        public static class Chicken implements IBird{}
        public static class Duck implements IBird{}
        public static class Goose implements IBird{}
        public static class Turkey implements IBird{}
        public static class Crow implements IBird{}
        public static class Sparrow implements IBird{}
        public static class Dove implements IBird{}
        public static class Penguin implements IBird{}
    }
    public static class Bugs{
        public static class Flea implements IBug {}
        public static class Housefly implements IBug {}
        public static class Honeybee implements IBug {}
        public static class Ladybird implements IBug {}
        public static class Moth implements IBug {}
        public static class Termite implements IBug {}
    }

    public static class Invertebrates{
        public static class Clam implements Invertebrate,UnderWaterAnimal{}
        public static class Crab implements Invertebrate,UnderWaterAnimal{}
        public static class Crayfish implements Invertebrate,UnderWaterAnimal{}
        public static class Lobster implements Invertebrate,UnderWaterAnimal{}
        public static class Starfish implements Invertebrate,UnderWaterAnimal{}
    }
    public static class Fishes{
        public static class Carp implements IFish {}
        public static class Catfish implements IFish {}
        public static class Dogfish implements IFish {}
        //public static class Seahorse implements IFish {}
        public static class Sole implements IFish {}
        public static class Tuna implements IFish {}
    }

    public static class Worms{
        public static class Earthworm implements IWorm{}
        public static class Silkworm implements IWorm{}
        public static class Woodworm implements IWorm{}
        public static class Glowworm implements IWorm{
            public boolean canFly()  {return true ;}
        }
    }
    public static class Mammals{
        public static class Bear implements IMammal{} 
        public static class Buffalo implements IMammal{} 
        public static class Calf implements IMammal{} 
        public static class Cat implements IMammal{} 
        public static class Dog implements IMammal{} 
        public static class Giraffe implements IMammal{} 
        public static class Lion implements IMammal{} 
        public static class Pig implements IMammal{} 
        public static class Tiger implements IMammal{}
        public static class Boar implements IMammal{}
        public static class Cavy implements IMammal{}
        public static class Cheetah implements IMammal{}
        public static class Deer implements IMammal{}
        public static class Dolphin implements IMammal{}
        public static class Elephant implements IMammal{}
        public static class Goat implements IMammal{}
        public static class Mole implements IMammal{}
    }

    public static class Reptiles{
        public class crocodile implements IReptile { }
        public class Tortoise implements IReptile { }
    }

}
