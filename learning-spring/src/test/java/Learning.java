import org.junit.Test;
import toy.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by rainhelper on 2015. 6. 28..
 */
public class Learning {

    @Test
    public void wrongSetBehavior() throws Exception {
        // Given
        final Set<Person> people = new HashSet<>();

        final Person person1 = new Person("Alice", 28);
        final Person person2 = new Person("Bob", 30);

        // When
        final boolean person1Added = people.add(person1);
        final boolean person2Added = people.add(person2);

        final Person person1Again = new Person("Alice", 28);

        final boolean person1AgainAdded = people.add(person1Again);

        // Then
        System.out.println("person1Added : " + person1Added);
        System.out.println("person1AgainAdded : " + person1AgainAdded);

        assertTrue(person1Added && person2Added);
        assertFalse(person1AgainAdded);
        assertEquals(people.size(), 2);
    }

    @Test
    public void testHashCode() throws Exception {
        // Given
        Map<Person, String> persons = new HashMap<>();
        Person person1 = new Person("juno", 29);
        Person person2 = new Person("chul", 30);

        // When
        persons.put(person1, "juno");
        persons.put(person2, "chul");

        // Then
        assertEquals(persons.size(), 2);
        assertEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void testConnectToH2() throws Exception {
        // Given
        final Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;MODE=MySQL");
        final Statement stmt = conn.createStatement();
        stmt.executeUpdate("create table teams (id INTEGER, name VARCHAR)");
        stmt.executeUpdate("insert into teams values (1, 'Red Team')");

        // When
        final ResultSet rs = stmt.executeQuery("select count(*) from teams");

        // Then
        assertTrue(rs.next());
        assertEquals(rs.getInt(1), 1);
    }

    @Test
    public void testReadAndWrite() throws Exception {
        final FileOutputStream fos = new FileOutputStream("test");
        final ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeInt(100);
        oos.writeUTF("write string");

        oos.flush();
        oos.close();
        fos.close();

        final FileInputStream fis = new FileInputStream("test");
        final ObjectInputStream ois = new ObjectInputStream(fis);

        final int number = ois.readInt();
        final String string = ois.readUTF();

        assertEquals("write string", string);
        assertEquals(100, number);
    }
}
