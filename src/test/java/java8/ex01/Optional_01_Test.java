package java8.ex01;

import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Exercice 01 - Optional
 */
public class Optional_01_Test {

    class NotFountException extends RuntimeException {}


    // tag::findMethod[]
    <T> Optional<T> find(List<T> list, Predicate<T> predicate) {
        Optional<T> result = null;

        for (T p : list) {
            if (predicate.test(p)) {
                result = Optional.of(p);
                return result;
            }
        }

        return Optional.empty();
    }
    // end::findMethod[]


    @Test
    public void test_optional_found() throws Exception {

        List<Person> personList = Data.buildPersonList(100);

        Optional<Person> result = find(personList, (person)->person.getAge()==10);

        assertThat(result, instanceOf(Optional.class));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), instanceOf(Person.class));
        assertThat(result.get(), hasProperty("firstname", is("first_10")));
        assertThat(result.get(), hasProperty("age", is(10)));
    }

    @Test
    public void test_optional_notfound() throws Exception {


        List<Person> personList = Data.buildPersonList(100);

        Optional<Person> result = find(personList, (person)->person.getAge()==400);

        assertThat(result, instanceOf(Optional.class));
        assertThat(result.isPresent(), is(false));
    }

    @Test(expected = NotFountException.class)
    public void test_optional_notfound_throw_exception() throws Exception {


        List<Person> personList = Data.buildPersonList(100);

        Optional<Person> result = find(personList, (person)->person.getAge()==10 && person.getFirstname().equals("last_10"));
        result.orElseThrow(() -> new NotFountException());
    }

    @Test
    public void test_optional_notfound_with_default_value() throws Exception {


        List<Person> personList = Data.buildPersonList(100);

        Person defaultValue = new Person();
        defaultValue.setFirstname("DEFAULT");
        defaultValue.setLastname("DEFAULT");

        Person result = find(personList, (person)->person.getAge()==400).orElse(defaultValue);

        assertThat(result, notNullValue());
        assertThat(result, hasProperty("firstname", is("DEFAULT")));
        assertThat(result, hasProperty("lastname", is("DEFAULT")));
    }


}
