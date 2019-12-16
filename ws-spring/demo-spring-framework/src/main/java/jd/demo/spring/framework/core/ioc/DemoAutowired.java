package jd.demo.spring.framework.core.ioc;


import jd.demo.example.common.thingsbysubject.biology.creature.animal.IAnimal;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Cat;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Dog;
import jd.demo.example.common.thingsbysubject.biology.creature.animal.mammal.Pig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DemoAutowired implements CommandLineRunner {

    @Autowired // 等同 @Resource @Inject
    private Cat cat ;
    private Dog dog ;
    private Pig pig;
    @Autowired
    private IAnimal[] iAnimals ;
    @Autowired
    private Set<IAnimal> animalSet ;
    @Autowired
    private List<IAnimal> animalList;
    @Autowired
    private Map<String,IAnimal> stringAnimalMap ;

    @Resource
    public void setDog(Dog dog) {
        this.dog = dog;
    }

    @Autowired
    public void setPig(@Qualifier("pig") Pig pig) {
        this.pig = pig;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(args);
    }
}
