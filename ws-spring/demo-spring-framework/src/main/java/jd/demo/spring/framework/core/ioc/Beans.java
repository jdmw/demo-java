package jd.demo.spring.framework.core.ioc;

import static jd.demo.example.common.thingsbysubject.biology.creature.animal.Animals.Mammals.*;

import jd.demo.example.common.thingsbysubject.biology.creature.animal.Animals;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by jdmw on 2019/12/16.
 */
@Configuration
public class Beans {

    @Bean
    public Cat cat(){
        return new Cat();
    }

    @Bean
    public Dog dog(){
        return new Dog();
    }

    @Bean
    public Pig pig(){
        return new Pig();
    }

    @Bean
    public Calf calf(){
        return new Calf();
    }

    @Primary
    @Bean
    public Animals.IFish Sole(){
        return new Animals.Fishes.Sole();
    }

    @Bean(name = "tuna")
    public Animals.IFish Tuna(){
        return new Animals.Fishes.Tuna();
    }


}
