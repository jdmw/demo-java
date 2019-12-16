package jd.demo.spring.framework.core.ioc;

import jd.demo.example.common.thingsbysubject.biology.creature.animal.fish.IFish;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.fish.Sole;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.fish.Tuna;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Calf;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Cat;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Dog;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Pig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by huangxia on 2019/12/16.
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
    public IFish Sole(){
        return new Sole();
    }

    @Bean(name = "tuna")
    public IFish Tuna(){
        return new Tuna();
    }


}
