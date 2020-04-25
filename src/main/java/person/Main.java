package person;

import com.github.javafaker.Faker;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Locale;

public class Main {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");


    /*    private static void randomPerson(){

            EntityManager em = emf.createEntityManager();

            Person person = new Person();

            Faker faker = new Faker();

            Address address = new Address();

            em.getTransaction().begin();
            em.persist(address);

            address.country = faker.address().country();
            //System.out.println(address.country);
            address.state = faker.address().state();
            address.city = faker.address().city();
            address.streetAddress = faker.address().streetAddress();
            address.zip = faker.address().zipCode();
            em.getTransaction().commit();
            System.out.println(address);


            String profession = faker.company().profession();

            String dob = faker.date().toString();

            String email = faker.internet().emailAddress();

            String name = faker.name().fullName();

            String gender = faker.options().option();
            em.close();
    }*/

    private static Faker faker = new Faker(new Locale("en"));

    private static Person randomPerson() {
        Person person = Person.builder()
                .profession(faker.company().profession())
                //.dob(faker.date().toString())
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .gender(faker.options().option())
                .country(faker.address().country())
                .state(faker.address().state())
                .city(faker.address().city())
                .streetAddress(faker.address().streetAddress())
                .zip(faker.address().zipCode())
                .build();
        //java.util.Date dob;
        //java.time.LocalDate localDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return person;
    }


    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            Person person = randomPerson();
        }
        //randomPerson().forEach(log::info);

        emf.close();
    }

}
