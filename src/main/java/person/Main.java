package person;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.Locale;

@Log4j2
public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");


    private static Faker faker = new Faker(new Locale("en"));

    private static Address randomAddress() {
        return Address.builder()
                .country(faker.address().country())
                .state(faker.address().state())
                .city(faker.address().city())
                .streetAddress(faker.address().streetAddress())
                .zip(faker.address().zipCode())
                .build();

    }

    private static Person randomPerson() {
        Person person = Person.builder()
                .profession(faker.company().profession())
                .dob(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option(Person.Gender.class))
                .address(randomAddress())
                .build();


        return person;
    }

    private static void generatePerson(int n) {
        randomPerson();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 0; i < n; i++) {
                em.persist(randomPerson());
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    private static void listPersons(){
        EntityManager em = emf.createEntityManager();
        try {
            em.createQuery("SELECT p FROM Person p", Person.class).getResultStream().forEach(log::info);
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

        try{
            int n = args.length > 0 ? Integer.parseInt(args[0]) : 1000;
            generatePerson(n);
            listPersons();
        } finally {
            emf.close();
        }
    }

}
