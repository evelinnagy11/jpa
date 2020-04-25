package person;

import com.github.javafaker.Faker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.Locale;
import java.util.*;

import static person.Person.Gender.FEMALE;
import static person.Person.Gender.MALE;

public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");


    private static Faker faker = new Faker(new Locale("en"));

    private static Person randomPerson() {
        Person person = Person.builder()
                .profession(faker.company().profession())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(Person.Gender.class))
                //.address.country(faker.address().country())
                //.address.state(faker.address().state())
                //.address.city(faker.address().city())
                //.address.streetAddress(faker.address().streetAddress())
                //.address.zip(faker.address().zipCode())
                .build();

        //public <E extends Enum<E>> E option(Class<E> Gender)


        return person;
    }


    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            Person person = randomPerson();
            System.out.println(person);
        }

        emf.close();
    }

}
